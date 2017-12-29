package de.htwg.se.djokajkaeppeler.model

case class Cell(status: CellStatus.Value) {
  def this() = this(CellStatus.EMPTY)
  def isSet: Boolean = status != CellStatus.EMPTY
}

object CellStatus extends Enumeration {
  val EMPTY, BLACK, WHITE = Value
}