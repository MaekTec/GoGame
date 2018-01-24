package de.htwg.se.djokajkaeppeler.model.fileIoComponent

import de.htwg.se.djokajkaeppeler.controller.controllerComponent.ControllerInterface
import de.htwg.se.djokajkaeppeler.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.djokajkaeppeler.model.gridComponent.GridInterface

trait FileIOInterface {

  def load: GridInterface
  def save(controller: ControllerInterface): Unit

}
