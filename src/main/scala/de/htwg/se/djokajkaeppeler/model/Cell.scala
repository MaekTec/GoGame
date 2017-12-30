package de.htwg.se.djokajkaeppeler.model

case class Cell(status: CellStatus.Value) {
  def this() = this(CellStatus.EMPTY)
  def isSet: Boolean = status != CellStatus.EMPTY

  override def toString: String = {
    this match {
      case Cell(CellStatus.EMPTY) => "o"
      case Cell(CellStatus.BLACK) => "b"
      case Cell(CellStatus.WHITE) => "w"
    }
  }
}

object CellStatus extends Enumeration {
  val EMPTY, BLACK, WHITE = Value
}