package de.htwg.se.djokajkaeppeler.controller

import de.htwg.se.djokajkaeppeler.model._
import de.htwg.se.djokajkaeppeler.util.Observable

class Controller(var game:Game) extends Observable{
  def createEmptyGrid(size: Int, player: (String, String)):Unit = {
    val grid = new Grid(size)
    game = Game(grid, (Player(player._1, CellStatus.BLACK), Player(player._2, CellStatus.WHITE)))
    notifyObservers
  }

  def gridToString: String = game.grid.toString
  def playerAtTurnToString: String = game.playerAtTurn.name

  def turn(row: Int, col: Int): Unit = {
    if (row >= 0 && row < game.grid.size && col >= 0 && col < game.grid.size && !game.grid.cellIsSet(row, col)) {
      game.grid = game.grid.set(row, col, Cell(game.playerAtTurn.cellstatus))
      game.nextTurn
      notifyObservers
    }
  }

  def set(row: Int, col: Int, value: Int):Unit = {
    game.grid = game.grid.set(row, col, intToCell(value))
    notifyObservers
  }

  def intToCell(v: Int): Cell = {
    v match {
      case 0 => Cell(CellStatus.EMPTY)
      case 1 => Cell(CellStatus.BLACK)
      case 2 => Cell(CellStatus.WHITE)
      case _ => Cell(CellStatus.EMPTY)
    }
  }

}
