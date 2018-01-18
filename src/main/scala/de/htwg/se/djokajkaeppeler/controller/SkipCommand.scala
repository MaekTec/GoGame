package de.htwg.se.djokajkaeppeler.controller

import de.htwg.se.djokajkaeppeler.controller.GameStatus._
import de.htwg.se.djokajkaeppeler.model.{Evaluation, Grid, Player}
import de.htwg.se.djokajkaeppeler.util.Command

class SkipCommand (controller: Controller) extends Command{
  var memento: (Grid, (Player, Player)) = (controller.grid, controller.player)
  override def doStep: Unit =   {
    memento = (controller.grid, controller.player)

    controller.gameStatus match {
      case GAME_OVER =>
      case SKIPPED => {
        //val eval = Evaluation(controller.grid)
        controller.grid = controller.grid.evaluate()
        controller.gameStatus = IN_EVALUATION_MARK
      }
      case IN_EVALUATION_MARK => {
        controller.setNextPlayer
        controller.gameStatus = IN_EVALUATION_CONFIRM_OR_MARK
      }
      case IN_EVALUATION_CONFIRM_OR_MARK => {
        controller.gameStatus = PLAYOUT_OR_GAME_OVER
      }
      case PLAYOUT_OR_GAME_OVER => {
        controller.gameStatus = GAME_OVER
      }
      case _ => {
        controller.setNextPlayer
        controller.gameStatus = SKIPPED
      }
    }
  }

  override def undoStep: Unit = {
    val new_memento = (controller.grid, controller.player)
    controller.grid = memento._1
    controller.player = memento._2
    memento = new_memento
  }

  override def redoStep: Unit = {
    val new_memento = (controller.grid, controller.player)
    controller.grid = memento._1
    controller.player = memento._2
    memento = new_memento
  }
}
