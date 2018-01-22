package de.htwg.se.djokajkaeppeler.model.playerComponent.playerBaseImpl

import de.htwg.se.djokajkaeppeler.model.gridComponent.CellInterface
import de.htwg.se.djokajkaeppeler.model.playerComponent.PlayerInterface

case class Player(name: String, cellstatus: CellInterface) extends PlayerInterface{
   override def toString:String = name
}
