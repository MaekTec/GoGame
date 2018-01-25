package de.htwg.se.djokajkaeppeler.controller.controllerComponent.controllerBaseImpl

import com.google.inject.assistedinject.{Assisted, AssistedInject}
import net.codingwell.scalaguice.InjectorExtensions._
import com.google.inject.{Guice, Inject}
import de.htwg.se.djokajkaeppeler.GoModule
import de.htwg.se.djokajkaeppeler.model.fileIoComponent._
import de.htwg.se.djokajkaeppeler.controller.GameStatus
import de.htwg.se.djokajkaeppeler.controller.GameStatus.{GameStatus, _}
import de.htwg.se.djokajkaeppeler.controller.controllerComponent.{ControllerInterface, Played}
import de.htwg.se.djokajkaeppeler.model.gridComponent.{CellFactory, GridFactory, GridInterface}
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{Cell, CellStatus, GridEvaluationChineseStrategy}
import de.htwg.se.djokajkaeppeler.model.playerComponent.playerBaseImpl.Player
import de.htwg.se.djokajkaeppeler.model.playerComponent.{PlayerFactory, PlayerInterface}
import de.htwg.se.djokajkaeppeler.util.UndoManager

import scala.swing.Publisher

class Controller  @AssistedInject() (@Assisted var grid: GridInterface, @Assisted var player: (PlayerInterface, PlayerInterface))
  extends ControllerInterface with Publisher{

  def this(grid:GridInterface, player1: String, player2: String) = this(grid, (Player(player1, Cell(CellStatus.BLACK)),
    Player(player2, Cell(CellStatus.WHITE))))

  /*@AssistedInject def this(@Assisted grid:GridInterface, @Assisted player1: String, @Assisted player2: String) =
    this(grid, (Guice.createInjector(new GoModule).instance[PlayerFactory].create(player1,
      Guice.createInjector(new GoModule).instance[CellFactory].create(CellStatus.BLACK)),
      Guice.createInjector(new GoModule).instance[PlayerFactory].create(player2, Guice.createInjector(new GoModule)
        .instance[CellFactory].create(CellStatus.WHITE))))*/

  //var evaluationGridRequest: Option[Grid] = None
  var gameStatus: GameStatus = NEXT_PLAYER
  var scoreBlack = 0
  var scoreWhite = 0
  private val undoManager = new UndoManager
  val gridEvaluationStrategy = new GridEvaluationChineseStrategy
  val injector = Guice.createInjector(new GoModule)
  val fileIo = injector.instance[FileIOInterface]

  def asGame: (GridInterface, (PlayerInterface, PlayerInterface)) = (grid, player)

  def playerAtTurn : PlayerInterface = player._1
  def playerNotAtTurn: PlayerInterface = player._2
  def setNextPlayer : Unit = player = player.swap

  def createEmptyGrid(size: Int, player: (String, String)):Unit = {
    val grid = injector.instance[GridFactory].create(size)
    this.grid = grid
    this.player = (injector.instance[PlayerFactory].create(player._1, injector.instance[CellFactory].create(CellStatus.BLACK)),
      injector.instance[PlayerFactory].create(player._2, injector.instance[CellFactory].create(CellStatus.WHITE)))
    gameStatus = NEXT_PLAYER
    publish(new Played)
  }

  def gridToString: String = grid.toString
  def playerAtTurnToString: String = playerAtTurn.name
  def playerNotAtTurnToString: String = playerNotAtTurn.toString

  def statusToString: String = {
    gameStatus match {
      case NEXT_PLAYER => playerAtTurnToString + GameStatus.message(gameStatus)
      case GAME_OVER =>
        var (playerWhite, playerBlack) = if(player._1.cellstatus.status == CellStatus.WHITE) {
          (player._1, player._2)
        } else {
          (player._2, player._1)
        }
        var winningString = if(scoreWhite > scoreBlack) {
          playerWhite + " won the game"
        } else if(scoreWhite < scoreBlack) {
          playerBlack + " won the game"
        } else {
          "Draw, nobody won"
        }
        winningString + " with " + scoreBlack + " to " + scoreWhite + " Points"
      case _ => GameStatus.message(gameStatus)
    }
  }

  def turn(row: Int, col: Int): Unit = {
    undoManager.doStep(new TurnCommand(row, col, this))
    publish(new Played)
  }

  def skipTurn(): Unit = {
    undoManager.doStep(new SkipCommand(this))
    publish(new Played)
  }

  def set(row: Int, col: Int, value: Int):Unit = {
    undoManager.doStep(new SetCommand(row, col, value, this))
    publish(new Played)
  }

  def undo: Unit = {
    undoManager.undoStep
    publish(new Played)
  }

  def redo: Unit = {
    undoManager.redoStep
    publish(new Played)
  }

  def save: Unit = {
    fileIo.save(grid,gameStatus,player)
    publish(new Played)
  }

  def load: Unit = {
    val gridOption = fileIo.load
    gridOption match {
      case None => {}
      case Some(game) => {
        grid = game._1
        gameStatus = game._2
        player = game._3
      }
    }
    publish(new Played)
  }



  def toParseInts(c: String):String = {
    c match {
      case "b" => "1"
      case "B" => "1"
      case "w" => "2"
      case "W" => "2"
      case "e" => "0"
      case "E" => "0"
      case something => something
    }
  }


}
