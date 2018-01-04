package de.htwg.se.djokajkaeppeler.model

case class Game(var grid: Grid, var player: (Player, Player)) {
  def this(grid:Grid, player1: String, player2: String) = this(grid, (Player(player1, Cell(CellStatus.BLACK)), Player(player2, Cell(CellStatus.WHITE))))

  var playerAtTurn : Player = player._1

  def turn(row: Int, col: Int): Option[Game] = {
    if (rowColIsValid(row, col) && !grid.cellIsSet(row, col)) {
      var newGame = copy(grid.set(row, col, playerAtTurn.cellstatus), player)
      val check = newGame.checkForHits(row, col)
      println("Test: " + check)
      check match {
        case Some(c) => c.foreach(rc => newGame = copy(newGame.grid.set(rc._1, rc._2, Cell(CellStatus.EMPTY)), player))
        case None =>
      }
      //check.foreach(rc => newGame = newGame.removeSelfAndAllNeighbors(rc._1, rc._2, playerAtTurn.cellstatus.reverse, Set.empty))
      Some(copy(newGame.grid, newGame.player.swap))
    } else {
      None
    }
  }

  def clearCells(cells: Set[(Int, Int)]): Unit = {
    cells.foreach(rc => grid.set(rc._1, rc._2, Cell(CellStatus.EMPTY)))
  }
  /*def removeSelfAndAllNeighbors(row: Int, col: Int, cell: Cell, visited: Set[(Int, Int)]): Game = {
    if (visited.contains((row, col))) {
      this
    } else {
      val visitedNew = visited + ((row, col))
      var gameNew = copy(grid.set(row, col, Cell(CellStatus.EMPTY)), player)
      gameNew.getCellsAround(row, col)
        .filter(rc => rowColIsValid(rc._1, rc._2))
        .filter(rc => grid.cell(rc._1, rc._2) == cell)
        .foreach(rc => gameNew = removeSelfAndAllNeighbors(rc._1, rc._2, cell, visitedNew))
      gameNew
    }

  }*/

  def checkForHits(row: Int, col: Int): Option[Set[(Int, Int)]] = {
    var cells: Set[(Int, Int)] = Set.empty
    for (cell <- getCellsAround(row, col)
      .filter(rc => rowColIsValid(rc._1, rc._2))
      .filter(rc => grid.cell(rc._1, rc._2) == playerAtTurn.cellstatus.reverse)) {
      checkIfCellHasFreedoms(cell._1, cell._2, playerAtTurn.cellstatus.reverse, Set.empty) match {
        case None => return None
        case Some(c) => cells ++= c
      }
    }
    Some(cells)
  }

  // TODO better visited Set
  def checkIfCellHasFreedoms(row: Int, col: Int, cell: Cell, visited: Set[(Int, Int)]): Option[Set[(Int, Int)]] = {
    println("rc:" + row + col + ":" + grid.cell(row, col).status + " CellStatus:" + cell.status)
    //if (visited.contains((row, col))) {
    //  false //false
    //} else {
      val visitedNew = visited + ((row, col))
      grid.cell(row, col).status match {
        case CellStatus.EMPTY => None
        case cell.status =>
          var cells: Set[(Int, Int)] = Set((row, col))
          println("1:" + getCellsAround(row, col))
          println("2:" + getCellsAround(row, col).filter(rc => rowColIsValid(rc._1, rc._2)))
          println("3:" + getCellsAround(row, col).filter(rc => rowColIsValid(rc._1, rc._2)).filter(rc => !visitedNew.contains(rc._1, rc._2)))
          for (cellA <- getCellsAround(row, col).filter(rc => rowColIsValid(rc._1, rc._2)).filter(rc => !visitedNew.contains(rc._1, rc._2))) {
            println("checked: " + cellA)
            checkIfCellHasFreedoms(cellA._1, cellA._2, cell, visitedNew) match {
              case None => return None
              case Some(s) => cells ++= s
            }
          }
          Some(cells)

          //.filter(rc => checkIfCellHasFreedoms(rc._1, rc._2, cell, visitedNew) match {
          //  case None => return None
          //  case Some(s) =>
          //})
          //None
        case _ => Some(Set.empty)
      }
    //}
  }

  def getCellsAround(row: Int, col: Int): List[(Int, Int)] = (row - 1, col) :: (row, col - 1) :: (row + 1, col) :: (row, col + 1) :: Nil

  def rowColIsValid(row: Int, col: Int): Boolean = row >= 0 && row < grid.size && col >= 0 && col < grid.size

  def skipTurn(): Game = {
    copy(grid, player.swap)
  }
}
