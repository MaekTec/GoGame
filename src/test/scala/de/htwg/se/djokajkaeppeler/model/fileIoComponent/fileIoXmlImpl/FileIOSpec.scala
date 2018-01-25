package de.htwg.se.djokajkaeppeler.model.fileIoComponent.fileIoXmlImpl

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}
import com.google.inject.name.Names
import com.google.inject.{AbstractModule, Guice, Injector}
import de.htwg.se.djokajkaeppeler.Go.defaultsize
import de.htwg.se.djokajkaeppeler.GoModule
import de.htwg.se.djokajkaeppeler.controller.controllerComponent.ControllerFactory
import de.htwg.se.djokajkaeppeler.model.fileIoComponent.FileIOInterface
import de.htwg.se.djokajkaeppeler.model.gridComponent.CellFactory
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{CellStatus, Grid}
import de.htwg.se.djokajkaeppeler.model.playerComponent.PlayerFactory
import net.codingwell.scalaguice.InjectorExtensions._


@RunWith(classOf[JUnitRunner])
class FileIOSpec extends WordSpec with Matchers {
  "A Json file" when {
    val injector = Guice.createInjector(new GoModule)
    val fileIo = new FileIO
    var controller = Guice.createInjector(new GoModule).instance[ControllerFactory].create(
      new Grid(defaultsize),
      (Guice.createInjector(new GoModule).instance[PlayerFactory].create("Player 1",
        Guice.createInjector(new GoModule).instance[CellFactory].create(CellStatus.BLACK))
        , Guice.createInjector(new GoModule).instance[PlayerFactory].create("Player 2",
        Guice.createInjector(new GoModule).instance[CellFactory].create(CellStatus.WHITE))))

    "new game tahts empty  " should {
      fileIo.save(controller.grid, controller.gameStatus, controller.asGame._2)
    }
    "be the same if loaded" in {
      fileIo.load match {
        case Some(s) => s should be((controller.grid, controller.gameStatus, controller.asGame._2))
        case None => false should be(true)
      }


    }


  }


}
