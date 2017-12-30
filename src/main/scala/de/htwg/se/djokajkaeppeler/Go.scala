package de.htwg.se.djokajkaeppeler

import de.htwg.se.djokajkaeppeler.model._
import de.htwg.se.djokajkaeppeler.aview._

import scala.io.StdIn.readLine



object Go {
  var grid = new Grid(9)
  val tui = new Tui

  def main(args: Array[String]): Unit = {
    println("Eingabeformat: row colum [0-2]")
    println("0 => Empty")
    println("1 => Black")
    println("2 => White")
    println("z.B.: 1 3 1")


    var input: String = ""

    do {
      println("Grid : " + grid.toString)
      input = readLine()
      grid = tui.processInputLine(input, grid)
    } while (input != "q")
  }
}
