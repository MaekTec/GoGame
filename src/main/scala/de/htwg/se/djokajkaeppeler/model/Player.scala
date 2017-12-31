package de.htwg.se.djokajkaeppeler.model

case class Player(name: String, cellstatus: CellStatus.Value) {
   override def toString:String = name
}
