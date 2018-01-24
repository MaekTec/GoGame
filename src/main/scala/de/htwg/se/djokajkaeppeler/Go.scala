package de.htwg.se.djokajkaeppeler

import net.codingwell.scalaguice.InjectorExtensions._
import com.google.inject.Guice
import de.htwg.se.djokajkaeppeler.aview._
import de.htwg.se.djokajkaeppeler.controller.controllerComponent.{ControllerFactory, ControllerInterface, Played}
import de.htwg.se.djokajkaeppeler.model.gridComponent.CellFactory
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{CellStatus, Grid}
import de.htwg.se.djokajkaeppeler.model.playerComponent.PlayerFactory
import de.htwg.se.go.aview.gui.SwingGui

import scala.io.StdIn.readLine

object Go {
  val defaultsize=11

  //var controller = Guice.createInjector(new GoModule).instance[ControllerFactory].create(new Grid(defaultsize), "Player 1", "Player 2") //.asInstanceOf[ControllerInterface]
  var controller = Guice.createInjector(new GoModule).instance[ControllerFactory].create(
    new Grid(defaultsize),
    (Guice.createInjector(new GoModule).instance[PlayerFactory].create("Player 1",
      Guice.createInjector(new GoModule).instance[CellFactory].create(CellStatus.BLACK))
    , Guice.createInjector(new GoModule).instance[PlayerFactory].create("Player 2",
    Guice.createInjector(new GoModule).instance[CellFactory].create(CellStatus.WHITE))))

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
