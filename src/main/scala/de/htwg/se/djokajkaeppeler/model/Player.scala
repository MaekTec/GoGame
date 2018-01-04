package de.htwg.se.djokajkaeppeler.model

case class Player(name: String, cellstatus: Cell) {
   override def toString:String = name
}
