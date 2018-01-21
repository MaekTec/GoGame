package de.htwg.se.djokajkaeppeler.controller

import de.htwg.se.djokajkaeppeler.controller.GameStatus.GameStatus
import de.htwg.se.djokajkaeppeler.model._
import de.htwg.se.djokajkaeppeler.util.{Observable, UndoManager}
import de.htwg.se.djokajkaeppeler.controller.GameStatus._

import scala.swing.Publisher

class Controller(var grid: Grid, var player: (Player, Player)) extends Publisher{

  //var evaluationGridRequest: Option[Grid] = None
  var gameStatus: GameStatus = IDLE
  private val undoManager = new UndoManager
  var gridEvaluationStrategy = new GridEvaluationChineseStrategy

  def this(grid:Grid, player1: String, player2: String) = this(grid, (Player(player1, Cell(CellStatus.BLACK)), Player(player2, Cell(CellStatus.WHITE))))

  def asGame: (Grid, (Player, Player)) = (grid, player)

  def playerAtTurn : Player = player._1
  def setNextPlayer : Unit = player = player.swap

  def createEmptyGrid(size: Int, player: (String, String)):Unit = {
    val grid = new Grid(size)
    this.grid = grid
    this.player = (Player(player._1, Cell(CellStatus.BLACK)), Player(player._2, Cell(CellStatus.WHITE)))
    publish(new Played)
  }

  def gridToString: String = grid.toString
  def playerAtTurnToString: String = playerAtTurn.name

  def turn(row: Int, col: Int): Unit = {
    undoManager.doStep(new TurnCommand(row, col, this))
    publish(new Played)
  }

  def skipTurn(): Unit = {
    undoManager.doStep(new SkipCommand(this))
    publish(new Played)
  }

  def set(row: Int, col: Int, value: Int):Unit = {
    undoManager.doStep(new SetCommand(row, col, value, this))
    publish(new Played)
  }

  def undo: Unit = {
    undoManager.undoStep
    publish(new Played)
  }

  def redo: Unit = {
    undoManager.redoStep
    publish(new Played)
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
