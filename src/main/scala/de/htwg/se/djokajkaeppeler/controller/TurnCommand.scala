package de.htwg.se.djokajkaeppeler.controller

import de.htwg.se.djokajkaeppeler.model._
import de.htwg.se.djokajkaeppeler.util.Command
import de.htwg.se.djokajkaeppeler.controller.GameStatus._

class TurnCommand(row: Int, col: Int, controller: Controller) extends Command{
  var memento: (Grid, (Player,Player)) = (controller.grid, controller.player)
  override def doStep: Unit =   {
    memento = (controller.grid, controller.player)

    controller.gameStatus match {
      case IN_EVALUATION_MARK | IN_EVALUATION_CONFIRM_OR_MARK => {
        controller.grid = controller.grid.markOrUnmarkDeadGroup(row, col)
      }
      case GAME_OVER =>
      case _ => {
        if (controller.gameStatus == PLAYOUT_OR_GAME_OVER) {
          controller.grid = controller.grid.allDeathCellsToAliveAndTeriReverse()
        }
        if (controller.grid.rowColIsValid(row, col) && !controller.grid.cellIsSet(row, col)) {
          var newGrid = controller.grid.set(row, col, controller.playerAtTurn.cellstatus)
          if (newGrid.checkIfMoveIsValid(row, col, controller.playerAtTurn.cellstatus)) {
            newGrid.checkForHits(row, col, controller.playerAtTurn.cellstatus) match {
              case Some(c) => c.foreach(rc => newGrid = newGrid.set(rc._1, rc._2, Cell(CellStatus.EMPTY)))
              case None =>
            }
            controller.grid = newGrid
            controller.setNextPlayer
            controller.gameStatus = NEXT_PLAYER
          } else {
            controller.gameStatus = MOVE_NOT_VALID
          }
        } else {
          controller.gameStatus = MOVE_NOT_VALID
        }
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
