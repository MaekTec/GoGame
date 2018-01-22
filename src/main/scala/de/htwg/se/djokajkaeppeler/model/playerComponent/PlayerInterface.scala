package de.htwg.se.djokajkaeppeler.model.playerComponent

import de.htwg.se.djokajkaeppeler.model.gridComponent.CellInterface

trait PlayerInterface {
  def name: String
  def cellstatus: CellInterface
}
