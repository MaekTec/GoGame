package de.htwg.se.djokajkaeppeler.controller

import de.htwg.se.djokajkaeppeler.controller.GameStatus.GameStatus
import de.htwg.se.djokajkaeppeler.model._
import de.htwg.se.djokajkaeppeler.util.{Observable, UndoManager}
import de.htwg.se.djokajkaeppeler.controller.GameStatus._

class Controller(var game:Game) extends Observable{

  var gameStatus: GameStatus = IDLE

  private val undoManager = new UndoManager

  def createEmptyGrid(size: Int, player: (String, String)):Unit = {
    val grid = new Grid(size)
    game = new Game(grid, (Player(player._1, Cell(CellStatus.BLACK)), Player(player._2, Cell(CellStatus.WHITE))))
    notifyObservers
  }

  def gridToString: String = game.grid.toString
  def playerAtTurnToString: String = game.playerAtTurn.name

  def turn(row: Int, col: Int): Unit = {
    undoManager.doStep(new TurnCommand(row, col, this))
    notifyObservers
  }

  def skipTurn(): Unit = {
    undoManager.doStep(new SkipCommand(this))
    notifyObservers
  }

  def set(row: Int, col: Int, value: Int):Unit = {
    undoManager.doStep(new SetCommand(row, col, value, this))
    notifyObservers
  }

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
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

}
