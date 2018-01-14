package de.htwg.se.djokajkaeppeler.model

import scala.math.sqrt
import scala.collection.mutable.Stack

case class Grid(private val cells:Matrix[Cell]) {
  def this(size:Int) = this(new Matrix[Cell](size, Cell(CellStatus.EMPTY)))

  val size:Int = cells.size

  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
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
      cell(c._1, c._2).status match {
        case cellToSearch.status =>
          filled += c
          visited += c
          stack.pushAll(getCellsAround(c._1, c._2).filter(rc => rowColIsValid(rc._1, rc._2)).filter(rc => !visited.contains(rc._1, rc._2)))
        case _ => visited += c
          onEdges += cell(c._1, c._2)
      }
    }
    (filled, onEdges)
  }

  override def toString: String = {
    val lineseparator = "|" + "---+" * (size-1) + "---|\n"
    val line = "| x "* size + "|\n"
    var box = "\n" + (lineseparator + line) * size + lineseparator
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("x", cell(row, col).toString)
    box
  }

}