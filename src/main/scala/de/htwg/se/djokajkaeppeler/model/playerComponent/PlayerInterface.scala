package de.htwg.se.djokajkaeppeler.model.playerComponent

import de.htwg.se.djokajkaeppeler.model.gridComponent.CellInterface
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.Cell

trait PlayerInterface {
  def name: String
  def cellstatus: CellInterface
}

trait PlayerFactory {
  def create(name: String, cellInterface: CellInterface): PlayerInterface
}
