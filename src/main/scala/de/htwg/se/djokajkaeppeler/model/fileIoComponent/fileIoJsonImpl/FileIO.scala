package de.htwg.se.djokajkaeppeler.model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import de.htwg.se.djokajkaeppeler.controller.GameStatus.GameStatus
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.djokajkaeppeler.GoModule
import de.htwg.se.djokajkaeppeler.controller.GameStatus
import de.htwg.se.djokajkaeppeler.model.fileIoComponent.FileIOInterface
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.CellStatus
import de.htwg.se.djokajkaeppeler.model.gridComponent.{CellFactory, CellInterface, GridFactory, GridInterface}
import de.htwg.se.djokajkaeppeler.model.playerComponent.{PlayerFactory, PlayerInterface}
import play.api.libs.json._

import scala.io.Source

class FileIO extends FileIOInterface {

  override def load: Option[(GridInterface,GameStatus,(PlayerInterface,PlayerInterface))] = {

    val source: String = Source.fromFile("Go.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val size = (json \ "grid" \ "size").get.toString.toInt

    val playerOneName = (json \ "playerOne").get.toString.drop(1).dropRight(1).trim
    val playerTwoName = (json \ "playerTwo").get.toString.drop(1).dropRight(1).trim

    val playerOneColor = CellStatus.fromString((json \ "playerOneCellstatus").get.toString.drop(1).dropRight(1).trim) match {
      case Some(playerOneColorFromString) => playerOneColorFromString
      case None => return None
    }

    val playerTwoColor = CellStatus.fromString((json \ "playerTwoCellstatus").get.toString.drop(1).dropRight(1).trim) match {
      case Some(playerTwoColorFromString) => playerTwoColorFromString
      case None => return None
    }


    val gameStatus = GameStatus.fromString((json \ "state").get.toString.drop(1).dropRight(1).trim) match{
      case Some(gameStatusFromString) => (gameStatusFromString)
      case None    =>  return None
    }


    val injector = Guice.createInjector(new GoModule)
    var grid = injector.instance[GridFactory].create(size)
    val player1 = injector.instance[PlayerFactory].create(playerOneName, injector.instance[CellFactory].create(playerOneColor))
    val player2 = injector.instance[PlayerFactory].create(playerTwoName, injector.instance[CellFactory].create(playerTwoColor))



    val jsV: JsValue = Json.parse("" + (json \\ "cells").head + "")
    val cellNodes = jsV.validate[List[JsValue]].get
    for (cell <- cellNodes) {
      val row: Int = (cell \ "row").get.toString.toInt
      val col: Int = (cell \ "col").get.toString.toInt

      val value = CellStatus.fromString((cell \ "cellstatus").get.toString.drop(1).dropRight(1)) match {
        case Some(cellValFromString) => (cellValFromString)
        case None => return None
      }
      grid = grid.set(row, col, injector.instance[CellFactory].create(value))

    }

      Some((grid,gameStatus,(player1,player2)))
  }

  override def save(grid : GridInterface, state: GameStatus, player: (PlayerInterface,PlayerInterface)): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("Go.json"))
    pw.write(Json.prettyPrint(controllerToJson(grid, state, player)))
    pw.close
  }

  def controllerToJson(grid : GridInterface, state: GameStatus, player: (PlayerInterface,PlayerInterface)):JsObject = {
    Json.obj(
      "state" -> JsString(state.toString),
      "playerOne" -> JsString(player._1.name),
      "playerTwo" -> JsString(player._2.name),
      "playerOneCellstatus" -> JsString(player._1.cellstatus.status.toString),
      "playerTwoCellstatus" -> JsString(player._2.cellstatus.status.toString),
      "grid" -> Json.obj(
        "size" -> JsNumber(grid.size),
        "cells" -> Json.toJson(
          for {
            row <- 0 until grid.size;
            col <- 0 until grid.size
          } yield {
            cellToJson(row, col, grid.cellAt(row,col))
          }
        )
      )

    )
  }


  def cellToJson(row: Int, col: Int, cell: CellInterface): JsObject ={
    Json.obj(
      "cellstatus" -> JsString(cell.status.toString),
      "row" -> JsNumber(row),
      "col" -> JsNumber(col)
    )

  }


}
