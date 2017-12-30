package de.htwg.se.djokajkaeppeler.aview

import de.htwg.se.djokajkaeppeler.controller.Controller
import de.htwg.se.djokajkaeppeler.model._
import de.htwg.se.djokajkaeppeler.util.Observer

class Tui(controller: Controller) extends Observer {

  controller.add(this)
  val size = 11

  def processInputLine(input: String): Unit = {
    input match {
      case "q" =>
      case "n" => controller.createEmptyGrid(size)
      case _ => input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: column :: value :: Nil => controller.set(row, column, intToCell(value))
          case _ =>
        }
    }
  }

  def intToCell(v: Int): Cell = {
    v match {
      case 0 => Cell(CellStatus.EMPTY)
      case 1 => Cell(CellStatus.BLACK)
      case 2 => Cell(CellStatus.WHITE)
      case _ => Cell(CellStatus.EMPTY)
    }
  }

  override def update: Unit = println(controller.gridToString)
}
