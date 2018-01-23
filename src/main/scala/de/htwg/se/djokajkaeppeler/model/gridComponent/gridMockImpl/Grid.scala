package de.htwg.se.djokajkaeppeler.model.gridComponent.gridMockImpl

import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{Cell, CellStatus}
import de.htwg.se.djokajkaeppeler.model.gridComponent.{CellInterface, GridInterface}

class Grid(var size:Int) extends GridInterface {

  size=1

  def cellAt(row:Int, col:Int):CellInterface = EmptyCell

  def set(row:Int, col:Int, value:CellInterface): GridInterface = this

  def cellIsSet(row:Int, col:Int):Boolean = false

  def getCellsAround(row: Int, col: Int): List[(Int, Int)] = List.empty

  def rowColIsValid(row: Int, col: Int): Boolean = true

  def getSetFilled(row: Int, col: Int, cellToSearch: CellInterface): (Set[(Int, Int)], Set[CellInterface]) = (Set.empty, Set.empty)

  def checkIfMoveIsValid(row: Int, col: Int, cell: CellInterface): Boolean = true

  def checkForHits(row: Int, col: Int,cell: CellInterface): Option[Set[(Int, Int)]] = None

  def checkIfCellHasFreedoms(row: Int, col: Int, cell: CellInterface, visited: Set[(Int, Int)]): Option[Set[(Int, Int)]] = None

  def markOrUnmarkDeadGroup(row: Int, col: Int) : GridInterface = this

  def allDeathCellsToAliveAndTeriReverse() : GridInterface = this

  def removeAllDeadCells() : GridInterface = this
}

object EmptyCell extends CellInterface {
  def status: CellStatus.Value = CellStatus.EMPTY
  def isSet: Boolean = false
  def isDead: Boolean = false
  def isAlive: Boolean = false

  def reverse: CellInterface = Cell(CellStatus.EMPTY)
  def toTeri: CellInterface = Cell(CellStatus.EMPTY)
  def toTeriReverse: CellInterface = Cell(CellStatus.EMPTY)
  def toDead: CellInterface = Cell(CellStatus.EMPTY)
  def toAlive: CellInterface = Cell(CellStatus.EMPTY)
  def toDeadOrReverse: CellInterface = Cell(CellStatus.EMPTY)
  def toAliveAndTerReverse: CellInterface = Cell(CellStatus.EMPTY)
}
