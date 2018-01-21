package de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl

import de.htwg.se.djokajkaeppeler.model.gridComponent.CellInterface

case class Cell(status: CellStatus.Value) extends CellInterface{
  def this() = this(CellStatus.EMPTY)

  def isSet: Boolean = status != CellStatus.EMPTY
  def isDead: Boolean = status == CellStatus.WHITE_MARKED_DEAD || status == CellStatus.BLACK_MARKED_DEAD
  def isAlive: Boolean = !isDead

  def reverse: CellInterface = status match {
    case CellStatus.EMPTY => Cell(status)
    case CellStatus.WHITE => Cell(CellStatus.BLACK)
    case CellStatus.BLACK => Cell(CellStatus.WHITE)
    case CellStatus.WHITE_TERI => Cell(CellStatus.BLACK_TERI)
    case CellStatus.BLACK_TERI => Cell(CellStatus.WHITE_TERI)
    case CellStatus.WHITE_MARKED_DEAD => Cell(CellStatus.BLACK_MARKED_DEAD)
    case CellStatus.BLACK_MARKED_DEAD => Cell(CellStatus.WHITE_MARKED_DEAD)
  }

  def toTeri: CellInterface = status match {
    case CellStatus.WHITE => Cell(CellStatus.WHITE_TERI)
    case CellStatus.BLACK => Cell(CellStatus.BLACK_TERI)
    case _ => Cell(status)
  }

  def toTeriReverse: CellInterface = status match {
    case CellStatus.WHITE_TERI => Cell(CellStatus.WHITE)
    case CellStatus.BLACK_TERI => Cell(CellStatus.BLACK)
    case _ => Cell(status)
  }

  def toDead: CellInterface = status match {
    case CellStatus.WHITE => Cell(CellStatus.WHITE_MARKED_DEAD)
    case CellStatus.BLACK => Cell(CellStatus.BLACK_MARKED_DEAD)
    case _ => Cell(status)
  }

  def toAlive: CellInterface = status match {
    case CellStatus.WHITE_MARKED_DEAD => Cell(CellStatus.WHITE)
    case CellStatus.BLACK_MARKED_DEAD => Cell(CellStatus.BLACK)
    case _ => Cell(status)
  }

  def toDeadOrReverse: CellInterface = status match {
    case CellStatus.WHITE => Cell(CellStatus.WHITE_MARKED_DEAD)
    case CellStatus.BLACK => Cell(CellStatus.BLACK_MARKED_DEAD)
    case CellStatus.WHITE_MARKED_DEAD => Cell(CellStatus.WHITE)
    case CellStatus.BLACK_MARKED_DEAD => Cell(CellStatus.BLACK)
    case _ => Cell(status)
  }

  def toAliveAndTerReverse: CellInterface = toAlive.toTeriReverse

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