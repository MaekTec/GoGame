package de.htwg.se.djokajkaeppeler.model.playerComponent

import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.Cell

case class Player(name: String, cellstatus: Cell) {
   override def toString:String = name
}
