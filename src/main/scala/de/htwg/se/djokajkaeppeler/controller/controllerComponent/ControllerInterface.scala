package de.htwg.se.djokajkaeppeler.controller.controllerComponent

import de.htwg.se.djokajkaeppeler.controller.GameStatus.GameStatus
import de.htwg.se.djokajkaeppeler.model.gridComponent.GridInterface
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.GridEvaluationStrategyTemplate
import de.htwg.se.djokajkaeppeler.model.playerComponent.PlayerInterface

import scala.swing.Publisher

trait ControllerInterface extends Publisher{
  def grid: GridInterface
  def gameStatus: GameStatus
  def gridEvaluationStrategy: GridEvaluationStrategyTemplate
  def asGame: (GridInterface, (PlayerInterface, PlayerInterface))
  def playerAtTurn : PlayerInterface
  def setNextPlayer : Unit
  def createEmptyGrid(size: Int, player: (String, String)):Unit
  def gridToString: String
  def playerAtTurnToString: String
  def statusToString: String
  def turn(row: Int, col: Int): Unit
  def skipTurn(): Unit
  def set(row: Int, col: Int, value: Int):Unit
  def undo: Unit
  def redo: Unit
  def toParseInts(c: String):String
}

trait ControllerFactory {
  def create(grid: GridInterface, player: (PlayerInterface, PlayerInterface)): ControllerInterface
  //def create(grid: GridInterface, player1: String, player2: String): ControllerInterface
}

import scala.swing.event.Event

class Played extends Event
case class GridSizeChanged(newSize: Int) extends Event
