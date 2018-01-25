package de.htwg.se.djokajkaeppeler.controller.controllerComponent.controllerBaseImpl

import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.djokajkaeppeler.model.gridComponent.{CellFactory, CellInterface}
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{CellStatus}
import de.htwg.se.djokajkaeppeler.util.Command


class SetCommand(row:Int, col: Int, value:Int, controller: Controller) extends Command {

  override def doStep: Unit =   controller.grid = controller.grid.set(row, col, intToCell(value))

  override def undoStep: Unit = controller.grid = controller.grid.set(row, col, controller.injector.instance[CellFactory].create(CellStatus.EMPTY))

  override def redoStep: Unit = controller.grid = controller.grid.set(row, col, intToCell(value))

  def intToCell(v: Int): CellInterface = {
    v match {
      case 0 => controller.injector.instance[CellFactory].create(CellStatus.EMPTY)
      case 1 => controller.injector.instance[CellFactory].create(CellStatus.BLACK)
      case 2 => controller.injector.instance[CellFactory].create(CellStatus.WHITE)
    }
  }
}

