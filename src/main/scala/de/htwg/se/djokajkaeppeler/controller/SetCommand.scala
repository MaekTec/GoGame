package de.htwg.se.djokajkaeppeler.controller

import de.htwg.se.djokajkaeppeler.model.{Cell, CellStatus}
import de.htwg.se.djokajkaeppeler.util.Command


class SetCommand(row:Int, col: Int, value:Int, controller: Controller) extends Command {
  override def doStep: Unit =   controller.grid = controller.grid.set(row, col, intToCell(value))

  override def undoStep: Unit = controller.grid = controller.grid.set(row, col, Cell(CellStatus.EMPTY))

  override def redoStep: Unit = controller.grid = controller.grid.set(row, col, intToCell(value))

  def intToCell(v: Int): Cell = {
    v match {
      case 0 => Cell(CellStatus.EMPTY)
      case 1 => Cell(CellStatus.BLACK)
      case 2 => Cell(CellStatus.WHITE)
    }
  }
}

