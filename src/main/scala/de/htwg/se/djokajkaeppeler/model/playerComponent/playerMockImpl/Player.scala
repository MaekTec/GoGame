package de.htwg.se.djokajkaeppeler.model.playerComponent.playerMockImpl

import de.htwg.se.djokajkaeppeler.model.gridComponent.CellInterface
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{Cell, CellStatus}
import de.htwg.se.djokajkaeppeler.model.playerComponent.PlayerInterface

class Player extends PlayerInterface{
  def name: String = "Player 1"
  def cellstatus: CellInterface = Cell(CellStatus.BLACK)
}
