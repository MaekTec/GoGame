package de.htwg.se.djokajkaeppeler.model

case class Cell(status: CellStatus.Value) {
  def this() = this(CellStatus.EMPTY)

  def isSet: Boolean = status != CellStatus.EMPTY

  def reverse: Cell = status match {
    case CellStatus.EMPTY => Cell(status)
    case CellStatus.WHITE => Cell(CellStatus.BLACK)
    case CellStatus.BLACK => Cell(CellStatus.WHITE)
    case CellStatus.WHITE_TERI => Cell(CellStatus.BLACK_TERI)
    case CellStatus.BLACK_TERI => Cell(CellStatus.WHITE_TERI)
  }

  def toTeri: Cell = status match {
    case CellStatus.WHITE => Cell(CellStatus.WHITE_TERI)
    case CellStatus.BLACK => Cell(CellStatus.BLACK_TERI)
  }

  override def toString: String = {
    this match {
      case Cell(CellStatus.EMPTY) => "o"
      case Cell(CellStatus.BLACK) => "b"
      case Cell(CellStatus.WHITE) => "w"
      case Cell(CellStatus.BLACK_TERI) => "B"
      case Cell(CellStatus.WHITE_TERI) => "W"
    }
  }
}

object CellStatus extends Enumeration {
  val EMPTY, BLACK, WHITE, BLACK_TERI, WHITE_TERI = Value
}