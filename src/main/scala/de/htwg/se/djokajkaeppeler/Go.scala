package de.htwg.se.djokajkaeppeler

import de.htwg.se.djokajkaeppeler.model._
import de.htwg.se.djokajkaeppeler.aview._
import de.htwg.se.djokajkaeppeler.controller.Controller

import scala.io.StdIn.readLine

object Go {
  var controller = new Controller(new Grid(11), "Player 1", "Player 2")
  val tui = new Tui(controller)

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
