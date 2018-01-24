package de.htwg.se.djokajkaeppeler.model.fileIoComponent.fileIoXmlImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import de.htwg.se.djokajkaeppeler.Go.defaultsize
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.djokajkaeppeler.GoModule
import de.htwg.se.djokajkaeppeler.controller.controllerComponent.{ControllerFactory, ControllerInterface}
import de.htwg.se.djokajkaeppeler.model.fileIoComponent.FileIOInterface
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{CellStatus, Grid}
import de.htwg.se.djokajkaeppeler.model.gridComponent.{CellFactory, GridInterface}
import de.htwg.se.djokajkaeppeler.model.playerComponent.PlayerFactory

import scala.xml.{NodeSeq, PrettyPrinter}

class FileIO extends FileIOInterface {

  override def load: GridInterface = {
    var grid: GridInterface = null
    val file = scala.xml.XML.loadFile("grid.xml")
    val sizeAttr = (file \\ "grid" \ "@size")
    val size = sizeAttr.text.toInt
    val injector = Guice.createInjector(new GoModule).instance[ControllerFactory].create(
      new Grid(size),
      (Guice.createInjector(new GoModule).instance[PlayerFactory].create("Player 1",
        Guice.createInjector(new GoModule).instance[CellFactory].create(CellStatus.BLACK))
        , Guice.createInjector(new GoModule).instance[PlayerFactory].create("Player 2",
        Guice.createInjector(new GoModule).instance[CellFactory].create(CellStatus.WHITE))))

    val cellNodes = (file \\ "cell")
    for (cell <- cellNodes) {
      val row: Int = (cell \ "@row").text.toInt
      val col: Int = (cell \ "@col").text.toInt
      val value: Int = cell.text.trim.toInt
      grid = grid.set(row, col)
      val given = (cell \ "@given").text.toBoolean
      val showCandidates = (cell \ "@showCandidates").text.toBoolean
      if (given) grid = grid.setGiven(row, col, value)
      if (showCandidates) grid = grid.setShowCandidates(row, col)
    }
    grid
  }

  def save(controller: ControllerInterface): Unit = {
    scala.xml.XML.save("Go.xml", controllerToXml(controller))
  }

  def saveXML(controller: ControllerInterface): Unit = {
    scala.xml.XML.save("grid.xml", controllerToXml(controller))
  }

  def saveString(controller: ControllerInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("Go.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gridToXml(grid))
    pw.write(xml)
    pw.close
  }
  def gridToXml(grid: GridInterface) = {
    <grid size={ grid.size.toString }>
      {
        for {
          row <- 0 until grid.size
          col <- 0 until grid.size
        } yield cellToXml(grid, row, col)
      }
    </grid>
  }



}
