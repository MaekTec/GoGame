package de.htwg.se.djokajkaeppeler.model

case class Cell(status: CellStatus.Value) {
  def this() = this(CellStatus.EMPTY)

  def isSet: Boolean = status != CellStatus.EMPTY

  def reverse: Cell = status match {
    case CellStatus.EMPTY => Cell(status)
    case CellStatus.WHITE => Cell(CellStatus.BLACK)
    case CellStatus.BLACK => Cell(CellStatus.WHITE)
  }

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