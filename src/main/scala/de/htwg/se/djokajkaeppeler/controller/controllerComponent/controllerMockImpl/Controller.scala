package de.htwg.se.djokajkaeppeler.controller.controllerComponent.controllerMockImpl

import de.htwg.se.djokajkaeppeler.controller.GameStatus
import de.htwg.se.djokajkaeppeler.controller.GameStatus.GameStatus
import de.htwg.se.djokajkaeppeler.controller.controllerComponent.ControllerInterface
import de.htwg.se.djokajkaeppeler.model.gridComponent.GridInterface
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{Cell, CellStatus, GridEvaluationChineseStrategy, GridEvaluationStrategyTemplate}
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridMockImpl.Grid
import de.htwg.se.djokajkaeppeler.model.playerComponent.PlayerInterface
import de.htwg.se.djokajkaeppeler.model.playerComponent.playerBaseImpl.Player

class Controller(var grid: GridInterface, var player: (PlayerInterface, PlayerInterface)) extends ControllerInterface{

  grid = new Grid(1)
  player = (Player("Player 1", Cell(CellStatus.BLACK)), Player("Player 2", Cell(CellStatus.WHITE)))

  def gameStatus: GameStatus = GameStatus.NEXT_PLAYER

  def gridEvaluationStrategy: GridEvaluationStrategyTemplate = new GridEvaluationChineseStrategy

  def asGame: (GridInterface, (PlayerInterface, PlayerInterface)) = (grid, player)

  def playerAtTurn : PlayerInterface = player._1

  def playerNotAtTurn : PlayerInterface = player._2

  def setNextPlayer : Unit = {}

  def createEmptyGrid(size: Int, player: (String, String)):Unit = {}

  def gridToString: String = grid.toString

  def playerAtTurnToString: String = player._1.toString

  def playerNotAtTurnToString: String = player._2.toString

  def statusToString: String = GameStatus.message(gameStatus)

  def turn(row: Int, col: Int): Unit = {}

  def skipTurn(): Unit = {}

  def set(row: Int, col: Int, value: Int):Unit = {}

  def undo: Unit = {}

  def redo: Unit = {}

  def toParseInts(c: String):String = ""

  def save: Unit = {}

  def load: Unit = {}


}
