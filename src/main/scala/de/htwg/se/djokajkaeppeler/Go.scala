package de.htwg.se.djokajkaeppeler

import de.htwg.se.djokajkaeppeler.aview._
import de.htwg.se.djokajkaeppeler.controller.{Controller, Played}
import de.htwg.se.djokajkaeppeler.model._
import de.htwg.se.go.aview.gui.SwingGui

import scala.io.StdIn.readLine

object Go {
  val defaultsize=11

  var controller = new Controller(new Grid(defaultsize), "Player 1", "Player 2")
  val tui = new Tui(controller)
  val gui = new SwingGui(controller)
  controller.publish(new Played)

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
