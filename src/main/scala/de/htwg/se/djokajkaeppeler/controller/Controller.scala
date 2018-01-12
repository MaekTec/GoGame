package de.htwg.se.djokajkaeppeler.controller

import de.htwg.se.djokajkaeppeler.model._
import de.htwg.se.djokajkaeppeler.util.Observable

class Controller(var game:Game) extends Observable{
  def createEmptyGrid(size: Int, player: (String, String)):Unit = {
    val grid = new Grid(size)
    game = new Game(grid, (Player(player._1, Cell(CellStatus.BLACK)), Player(player._2, Cell(CellStatus.WHITE))))
    notifyObservers
  }

  def gridToString: String = game.grid.toString
  def playerAtTurnToString: String = game.playerAtTurn.name

  def turn(row: Int, col: Int): Unit = {
    game.turn(row, col) match {
      case Some(newGame) => {
        game = newGame
        notifyObservers
      }
      case None =>
    }
  }

  def skipTurn(): Unit = {
    game.skipTurn() match {
      case Some(newGame) => {
        game = newGame
        notifyObservers
      }
      case None =>
    }
  }

  def set(row: Int, col: Int, value: Int):Unit = {
    game.grid = game.grid.set(row, col, intToCell(value))
    notifyObservers
  }

  def toParseInts(c: String):String = {
    c match {
      case "b" => "1"
      case "B" => "1"
      case "w" => "2"
      case "W" => "2"
      case "e" => "0"
      case "E" => "0"
      case something => something
    }
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
