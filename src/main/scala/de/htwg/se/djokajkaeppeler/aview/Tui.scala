package de.htwg.se.djokajkaeppeler.aview

import de.htwg.se.djokajkaeppeler.controller.Controller
import de.htwg.se.djokajkaeppeler.util.Observer

class Tui(controller: Controller) extends Observer {

  controller.add(this)
  val size = 11
  var players: (String, String) = ("Player 1", "Player 2")

  println("Eingabeformat:")
  println("New game: n [Gridsize] [Player 1 name] [Player 2 name]")
  println("In game: row colum")

  def processInputLine(input: String): Unit = {
    val in = input.split(" ")
    if (in.nonEmpty) {
      in(0) match {
        case "q" =>
        case "n" => {
          var newSize = size
          var player = players
          if (in.length >= 2) {
            newSize = in(1).toInt
            if (in.length >= 4) {
              player = (in(2), in(3))
            }
          }
          controller.createEmptyGrid(newSize, player)
        }
        case "s" => controller.skipTurn()
        case _ => {
          processInputMove(in)
        }
      }
    }
  }

  def processInputMove(in: Array[String]): Unit = {
    in.toList.filter(c => c != " ").map(c => controller.toParseInts(c)).map(c => c.toInt) match {
      case row :: column :: Nil => controller.turn(row, column)
      case row :: column :: value :: Nil => controller.set(row, column, value)
      case _ =>
    }
  }

  override def update: Unit = {
    println(controller.gridToString)
    println(controller.playerAtTurnToString + " is at turn")
  }
}
