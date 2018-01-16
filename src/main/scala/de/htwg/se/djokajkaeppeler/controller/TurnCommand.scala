package de.htwg.se.djokajkaeppeler.controller

import de.htwg.se.djokajkaeppeler.model.Game
import de.htwg.se.djokajkaeppeler.util.Command
import de.htwg.se.djokajkaeppeler.controller.GameStatus._

class TurnCommand(row: Int, col: Int, controller: Controller) extends Command{
  var memento: Game = controller.game
  override def doStep: Unit =   {
    memento = controller.game
    controller.game.turn(row, col) match {
      case Some(newGame) => {
        controller.game = newGame
        controller.gameStatus = NEXT_PLAYER
      }
      case None => controller.gameStatus = MOVE_NOT_VALID
    }
  }

  override def undoStep: Unit = {
    val new_memento = controller.game
    controller.game = memento
    memento = new_memento
  }

  override def redoStep: Unit = {
    val new_memento = controller.game
    controller.game = memento
    memento = new_memento
  }
}
