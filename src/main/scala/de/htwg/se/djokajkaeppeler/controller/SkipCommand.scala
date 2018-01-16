package de.htwg.se.djokajkaeppeler.controller

import de.htwg.se.djokajkaeppeler.controller.GameStatus._
import de.htwg.se.djokajkaeppeler.model.Game
import de.htwg.se.djokajkaeppeler.util.Command

class SkipCommand (controller: Controller) extends Command{
  var memento: Game = controller.game
  override def doStep: Unit =   {
    memento = controller.game
    controller.game.skipTurn() match {
      case Some(newGame) => {
        controller.game = newGame
        controller.gameStatus = SKIPPED
      }
      case None => controller.gameStatus = GAME_OVER
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
