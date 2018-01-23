package de.htwg.se.djokajkaeppeler.model.gridComponent

import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{Cell, CellStatus}

trait GridInterface {
  def cellAt(row:Int, col:Int):CellInterface
  def set(row:Int, col:Int, value:CellInterface): GridInterface
  def cellIsSet(row:Int, col:Int):Boolean
  def getCellsAround(row: Int, col: Int): List[(Int, Int)]
  def rowColIsValid(row: Int, col: Int): Boolean
  def getSetFilled(row: Int, col: Int, cellToSearch: CellInterface): (Set[(Int, Int)], Set[CellInterface])
  def checkIfMoveIsValid(row: Int, col: Int, cell: CellInterface): Boolean
  def checkForHits(row: Int, col: Int,cell: CellInterface): Option[Set[(Int, Int)]]
  def checkIfCellHasFreedoms(row: Int, col: Int, cell: CellInterface, visited: Set[(Int, Int)]): Option[Set[(Int, Int)]]
  def markOrUnmarkDeadGroup(row: Int, col: Int) : GridInterface
  def allDeathCellsToAliveAndTeriReverse() : GridInterface
  def removeAllDeadCells() : GridInterface

  def size:Int
}

trait CellInterface {
  def status: CellStatus.Value
  def isSet: Boolean
  def isDead: Boolean
  def isAlive: Boolean

  def reverse: CellInterface
  def toTeri: CellInterface
  def toTeriReverse: CellInterface
  def toDead: CellInterface
  def toAlive: CellInterface
  def toDeadOrReverse: CellInterface
  def toAliveAndTerReverse: CellInterface
}

