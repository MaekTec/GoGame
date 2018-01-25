package de.htwg.se.djokajkaeppeler.model.fileIoComponent.fileIoXmlImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import de.htwg.se.djokajkaeppeler.Go.defaultsize
import com.google.inject.assistedinject.{Assisted, AssistedInject}
import net.codingwell.scalaguice.InjectorExtensions._
import com.google.inject.{Guice, Inject}
import de.htwg.se.djokajkaeppeler.model._
import de.htwg.se.djokajkaeppeler.GoModule
import de.htwg.se.djokajkaeppeler.controller.controllerComponent.{ControllerFactory, ControllerInterface}
import de.htwg.se.djokajkaeppeler.model.fileIoComponent.FileIOInterface
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{CellStatus, Grid}
import de.htwg.se.djokajkaeppeler.model.gridComponent.{CellFactory, GridFactory, GridInterface}
import de.htwg.se.djokajkaeppeler.model.playerComponent.{PlayerFactory, PlayerInterface}
import net.codingwell.scalaguice.InjectorExtensions._
import java.nio.file.Files
import java.nio.file.Paths

import de.htwg.se.djokajkaeppeler.controller.GameStatus
import de.htwg.se.djokajkaeppeler.controller.GameStatus.GameStatus

import scala.xml.{NodeSeq, PrettyPrinter}

class FileIO extends FileIOInterface {

  override def load: Option[(GridInterface,GameStatus,(PlayerInterface,PlayerInterface))] = {
    val file = scala.xml.XML.loadFile("go.xml")
    println(file)
    val size = (file \\ "grid" \ "@size").text.toInt
    val playerOneName = (file \\ "activePlayer").text.trim
    val playerTwoName = (file \\ "otherPlayer").text.trim



    val playerOneColor = CellStatus.fromString((file \\ "activePlayerCellstatus").text.trim) match {
      case Some(playerOneColorFromString) => playerOneColorFromString
      case None => return None
    }

    val playerTwoColor = CellStatus.fromString((file \\ "otherPlayerCellstatus").text.trim) match {
      case Some(playerTwoColorFromString) => playerTwoColorFromString
      case None => return None
    }


    val gameStatus = GameStatus.fromString((file \\ "state").text.trim) match{
       case Some(gameStatusFromString) => (gameStatusFromString)
       case None    =>  return None
    }

    val injector = Guice.createInjector(new GoModule)
    var grid = injector.instance[GridFactory].create(size)
    val player1 = injector.instance[PlayerFactory].create(playerOneName, injector.instance[CellFactory].create(playerOneColor))
    val player2 = injector.instance[PlayerFactory].create(playerTwoName, injector.instance[CellFactory].create(playerTwoColor))


    val cellNodes = (file \\ "cell")
    for (cell <- cellNodes) {
      val row: Int = (cell \ "@row").text.toInt
      val col: Int = (cell \ "@col").text.toInt
      val value = CellStatus.fromString(cell.text.trim) match{
        case Some(cellValFromString) => (cellValFromString)
        case None    =>  return None
      }
      grid = grid.set(row, col, injector.instance[CellFactory].create(value))

    }
    Some((grid,gameStatus,(player1,player2)))
  }



  def save(grid : GridInterface, state: GameStatus,player: (PlayerInterface,PlayerInterface)): Unit = {
    scala.xml.XML.save("Go.xml", controllerToXml(grid, state, player))
  }



  def controllerToXml(grid : GridInterface, state: GameStatus,player: (PlayerInterface,PlayerInterface)) = {
    <go>
      <information>
        <activePlayer>
          { player._1.name }
        </activePlayer>
        <activePlayerCellstatus>
          { player._1.cellstatus.status }
        </activePlayerCellstatus>
        <otherPlayer>
          { player._2.name}
        </otherPlayer>
        <otherPlayerCellstatus>
          { player._2.cellstatus.status}
        </otherPlayerCellstatus>
        <state>
          { state }
        </state>
      </information>{ gridToXml(grid) }
    </go>
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

  def cellToXml(grid:GridInterface, row:Int, col:Int) ={
    <cell row ={row.toString} col={col.toString}>
      {grid.cellAt(row,col).status}
    </cell>
  }
}
