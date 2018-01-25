package de.htwg.se.djokajkaeppeler.model.fileIoComponent

import de.htwg.se.djokajkaeppeler.controller.GameStatus.GameStatus
import de.htwg.se.djokajkaeppeler.controller.controllerComponent.ControllerInterface
import de.htwg.se.djokajkaeppeler.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.djokajkaeppeler.model.gridComponent.GridInterface
import de.htwg.se.djokajkaeppeler.model.playerComponent.PlayerInterface

trait FileIOInterface {

  def load: Option[(GridInterface,GameStatus,(PlayerInterface,PlayerInterface))]
  def save( grid : GridInterface, state: GameStatus, player: (PlayerInterface,PlayerInterface)): Unit

}
