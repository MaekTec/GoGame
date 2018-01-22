package de.htwg.se.djokajkaeppeler.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.djokajkaeppeler.model.gridComponent.CellInterface
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{Cell, CellStatus}
import de.htwg.se.djokajkaeppeler.util.Command


class SetCommand(row:Int, col: Int, value:Int, controller: Controller) extends Command {
  override def doStep: Unit =   controller.grid = controller.grid.set(row, col, intToCell(value))

  override def undoStep: Unit = controller.grid = controller.grid.set(row, col, Cell(CellStatus.EMPTY))

  override def redoStep: Unit = controller.grid = controller.grid.set(row, col, intToCell(value))

  def intToCell(v: Int): CellInterface = {
    v match {
      case 0 => Cell(CellStatus.EMPTY)
      case 1 => Cell(CellStatus.BLACK)
      case 2 => Cell(CellStatus.WHITE)
    }
  }
}

