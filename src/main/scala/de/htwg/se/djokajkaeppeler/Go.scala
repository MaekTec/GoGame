package de.htwg.se.djokajkaeppeler

import de.htwg.se.djokajkaeppeler.model._
import de.htwg.se.djokajkaeppeler.aview._
import de.htwg.se.djokajkaeppeler.controller.Controller

import scala.io.StdIn.readLine



object Go {
  var controller = new Controller(new Grid(11))
  val tui = new Tui(controller)

  def main(args: Array[String]): Unit = {
    println("Eingabeformat: row colum [0-2]")
    println("0 => Empty")
    println("1 => Black")
    println("2 => White")
    println("z.B.: 1 3 1")

    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}
