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
    case CellStatus.WHITE_MARKED_DEAD => Cell(CellStatus.BLACK_MARKED_DEAD)
    case CellStatus.BLACK_MARKED_DEAD => Cell(CellStatus.WHITE_MARKED_DEAD)
  }

  def toTeri: Cell = status match {
    case CellStatus.WHITE => Cell(CellStatus.WHITE_TERI)
    case CellStatus.BLACK => Cell(CellStatus.BLACK_TERI)
    case _ => Cell(status)
  }

  def toTeriReverse: Cell = status match {
    case CellStatus.WHITE_TERI => Cell(CellStatus.WHITE)
    case CellStatus.BLACK_TERI => Cell(CellStatus.BLACK)
    case _ => Cell(status)
  }

  def toDead: Cell = status match {
    case CellStatus.WHITE => Cell(CellStatus.WHITE_MARKED_DEAD)
    case CellStatus.BLACK => Cell(CellStatus.BLACK_MARKED_DEAD)
    case _ => Cell(status)
  }

  def toAlive: Cell = status match {
    case CellStatus.WHITE_MARKED_DEAD => Cell(CellStatus.WHITE)
    case CellStatus.BLACK_MARKED_DEAD => Cell(CellStatus.BLACK)
    case _ => Cell(status)
  }

  def toDeadOrReverse: Cell = status match {
    case CellStatus.WHITE => Cell(CellStatus.WHITE_MARKED_DEAD)
    case CellStatus.BLACK => Cell(CellStatus.BLACK_MARKED_DEAD)
    case CellStatus.WHITE_MARKED_DEAD => Cell(CellStatus.WHITE)
    case CellStatus.BLACK_MARKED_DEAD => Cell(CellStatus.BLACK)
    case _ => Cell(status)
  }

  def toAliveAndTerReverse: Cell = toAlive.toTeriReverse

  override def toString: String = {
    this match {
      case Cell(CellStatus.EMPTY) => "o"
      case Cell(CellStatus.BLACK) => "b"
      case Cell(CellStatus.WHITE) => "w"
      case Cell(CellStatus.BLACK_TERI) => "B"
      case Cell(CellStatus.WHITE_TERI) => "W"
      case Cell(CellStatus.BLACK_MARKED_DEAD) => "D"
      case Cell(CellStatus.WHITE_MARKED_DEAD) => "D"
    }
  }
}

object CellStatus extends Enumeration {
  val EMPTY, BLACK, WHITE, BLACK_TERI, WHITE_TERI, BLACK_MARKED_DEAD, WHITE_MARKED_DEAD = Value
}