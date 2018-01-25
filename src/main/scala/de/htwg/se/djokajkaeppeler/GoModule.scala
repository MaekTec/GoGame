package de.htwg.se.djokajkaeppeler

import com.google.inject.AbstractModule
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.htwg.se.djokajkaeppeler.controller.controllerComponent.{ControllerFactory, ControllerInterface, controllerBaseImpl}
import de.htwg.se.djokajkaeppeler.model.gridComponent.{CellFactory, CellInterface, GridFactory, GridInterface}
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{Cell, CellStatus, Grid, Matrix}
import de.htwg.se.djokajkaeppeler.model.playerComponent.{PlayerFactory, PlayerInterface}
import de.htwg.se.djokajkaeppeler.model.playerComponent.playerBaseImpl.Player
import net.codingwell.scalaguice.ScalaModule
import de.htwg.se.djokajkaeppeler.model.fileIoComponent._

class GoModule extends AbstractModule with ScalaModule{

  val defaultSize:Int = 11
  val defaulfPlayerName = ("Player 1", "Player 2")

  override def configure() = {
    install(new FactoryModuleBuilder().implement(classOf[PlayerInterface], classOf[Player]).build(classOf[PlayerFactory]))
    install(new FactoryModuleBuilder().implement(classOf[GridInterface], classOf[Grid]).build(classOf[GridFactory]))
    install(new FactoryModuleBuilder().implement(classOf[CellInterface], classOf[Cell]).build(classOf[CellFactory]))
    install(new FactoryModuleBuilder().implement(classOf[ControllerInterface], classOf[controllerBaseImpl.Controller]).build(classOf[ControllerFactory]))
    bind[CellInterface].to[Cell]

    bind[FileIOInterface].to[fileIoJsonImpl.FileIO]

  }

}
