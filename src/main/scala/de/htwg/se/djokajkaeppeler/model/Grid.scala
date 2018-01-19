package de.htwg.se.djokajkaeppeler.model

import scala.math.sqrt
import scala.collection.mutable.Stack

case class Grid(private val cells:Matrix[Cell]) {
  def this(size:Int) = this(new Matrix[Cell](size, Cell(CellStatus.EMPTY)))

  val size:Int = cells.size

  def cellAt(row:Int, col:Int):Cell = cells.cell(row, col)
  def set(row:Int, col:Int, value:Cell):Grid = copy(cells.replaceCell(row, col, value))
  def cellIsSet(row:Int, col:Int):Boolean = cells.cell(row, col).isSet

  /*
  def indexToRowCol(index: Int): (Int, Int) = {
    val r = index / size
    val c = index % size
    (r, c)
  }
*/

  def getCellsAround(row: Int, col: Int): List[(Int, Int)] = (row - 1, col) :: (row, col - 1) :: (row + 1, col) :: (row, col + 1) :: Nil

  def rowColIsValid(row: Int, col: Int): Boolean = row >= 0 && row < size && col >= 0 && col < size

  def getSetFilled(row: Int, col: Int, cellToSearch: Cell): (Set[(Int, Int)], Set[Cell]) = {
    var filled: Set[(Int, Int)] = Set()
    var visited: Set[(Int, Int)] = Set((row, col))
    var onEdges: Set[Cell] = Set()

    val stack = Stack((row, col))
    while(stack.nonEmpty) {
      val c = stack.pop()
      cellAt(c._1, c._2).status match {
        case cellToSearch.status =>
          filled += c
          visited += c
          stack.pushAll(getCellsAround(c._1, c._2).filter(rc => rowColIsValid(rc._1, rc._2)).filter(rc => !visited.contains(rc._1, rc._2)))
        case _ => visited += c
          onEdges += cellAt(c._1, c._2)
      }
    }
    (filled, onEdges)
  }

  def checkIfMoveIsValid(row: Int, col: Int, cell: Cell): Boolean = checkSuicideBan(row, col, cell)

  // Check if a Cell has freedoms, if not the move is not valid because this would be suicide
  def checkSuicideBan(row: Int,  col: Int, cell: Cell): Boolean = {
    checkForHits(row,col,cell) match {
      case None => {
        checkIfCellHasFreedoms(row, col, cell, Set.empty) match {
          case None => true //Has freedoms
          case Some(cells) =>
            cells.isEmpty //if empty -> has freedoms, else set of stones with no freedoms
        }
      }
      case Some(c) => true
    }
  }

  // Checks if player beat the other player, so the other players stones has no freeedoms anymore
  def checkForHits(row: Int, col: Int,cell: Cell): Option[Set[(Int, Int)]] = {
    val cellStatusReversed = cell.reverse
    var cells: Set[(Int, Int)] = Set.empty
    for (cell <- getCellsAround(row, col)
      .filter(rc => rowColIsValid(rc._1, rc._2))
      .filter(rc => cellAt(rc._1, rc._2) == cellStatusReversed)) {
      checkIfCellHasFreedoms(cell._1, cell._2, cellStatusReversed, Set.empty) match {
        case None => return None
        case Some(c) => cells ++= c
      }
    }
    if (cells.isEmpty) {
      None
    } else {
      Some(cells)
    }
  }

  // TODO better visited Set
  // returns Cells with no freedoms, or none if the cell has freedoms
  def checkIfCellHasFreedoms(row: Int, col: Int, cell: Cell, visited: Set[(Int, Int)]): Option[Set[(Int, Int)]] = {
    val visitedNew = visited + ((row, col))
    cellAt(row, col).status match {
      case CellStatus.EMPTY => None
      case cell.status =>
        var cells: Set[(Int, Int)] = Set((row, col))
        for (cellA <- getCellsAround(row, col).filter(rc => rowColIsValid(rc._1, rc._2)).filter(rc => !visitedNew.contains(rc._1, rc._2))) {
          checkIfCellHasFreedoms(cellA._1, cellA._2, cell, visitedNew) match {
            case None => return None
            case Some(s) => cells ++= s
          }
        }
        Some(cells)
      case _ => Some(Set.empty)
    }
  }

  def evaluate(): Grid = {
    var territories: Map[Cell, Set[Set[(Int, Int)]]] = Map()
    var inTerritories: Set[(Int, Int)] = Set()

    for(r <- 0 until size) {
      for(c <- 0 until size) {
        if (!inTerritories.contains((r, c))) {
          inTerritories += ((r, c))
          var currentCell = cellAt(r, c)
          var (territory, edges) = getSetFilled(r, c, currentCell)
          inTerritories ++= territory
          currentCell.status match {
            case CellStatus.EMPTY | CellStatus.WHITE_TERI | CellStatus.BLACK_TERI =>
              if (edges.size == 1) {
                territories ++= addOrReplaceToMap(territories, territory, edges.toList.head.toTeri)
              } else {
                territories ++= addOrReplaceToMap(territories, territory, Cell(CellStatus.EMPTY))
              }
            case CellStatus.BLACK =>
              territories ++= addOrReplaceToMap(territories, territory, Cell(CellStatus.BLACK))
            case CellStatus.WHITE =>
              territories ++= addOrReplaceToMap(territories, territory, Cell(CellStatus.WHITE))
          }
        }
      }
    }
    mapToGrid(territories)
  }

  private def mapToGrid(territories: Map[Cell, Set[Set[(Int, Int)]]]): Grid = {
    var gridNew = new Grid(size)
    territories.keys.foreach{ t =>
      territories.get(t).toSeq.flatten.flatten.foreach{ c =>
        gridNew = gridNew.set(c._1, c._2, t)
      }
    }
    gridNew
  }

  private def addOrReplaceToMap(territories : Map[Cell, Set[Set[(Int, Int)]]], territory: Set[(Int, Int)], cell: Cell)
  : Map[Cell, Set[Set[(Int, Int)]]] = {
    var territoriesNew : Map[Cell, Set[Set[(Int, Int)]]] = Map()
    territories.get(cell) match {
      case Some(t) => territoriesNew += (cell -> (t + territory))
      case None => territoriesNew += (cell -> Set(territory))
    }
    territoriesNew
  }

  def markOrUnmarkDeadGroup(row: Int, col: Int) : Grid = {
    var (group, _) = getSetFilled(row, col, cellAt(row, col))
    var gridNew = this
    group.foreach { c =>
      gridNew = gridNew.set(c._1, c._2, cellAt(row, col).toDeadOrReverse)
    }
    gridNew
  }

  /*def changeAllCells(f: Cell => Cell): Grid = {
    var gridNew = this
    for {
      row <- 0 until size
      col <- 0 until size;
    } gridNew = gridNew.set(row, col, f(cellAt(row, col)))
    gridNew
  }*/

  def allDeathCellsToAliveAndTeriReverse() : Grid = {
    var gridNew = this
    for {
      row <- 0 until size
      col <- 0 until size
    } gridNew = gridNew.set(row, col, cellAt(row, col).toAliveAndTerReverse)
    gridNew
  }

  def countPoints(): (Grid, Int, Int) = {
    val gridToCount = removeAllDeadCells().evaluate()
    var whitePoints = 0
    var blackPoints = 0
    for {
      row <- 0 until size
      col <- 0 until size
    } gridToCount.cellAt(row, col).status match {
      case CellStatus.BLACK | CellStatus.BLACK_TERI => blackPoints += 1
      case CellStatus.WHITE | CellStatus.WHITE_TERI => whitePoints += 1
      case _ =>
    }
    (gridToCount, blackPoints, whitePoints)
  }

  def removeAllDeadCells() : Grid = {
    var gridNew = this
    for {
      row <- 0 until size
      col <- 0 until size
    } gridNew = gridNew.set(row, col, cellAt(row, col).toAlive)
    gridNew
  }

  override def toString: String = {
    val lineseparator = "|" + "---+" * (size-1) + "---|\n"
    val line = "| x "* size + "|\n"
    var box = "\n" + (lineseparator + line) * size + lineseparator
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("x", cellAt(row, col).toString)
    box
  }

}