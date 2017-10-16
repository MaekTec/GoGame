package de.htwg.se.djokajkaeppeler

import de.htwg.se.djokajkaeppeler.model.Player

object Go {
  def main(args: Array[String]): Unit = {
    val student = Player("Markus and Florian")
    println("Hello, " + student.name)
  }
}
