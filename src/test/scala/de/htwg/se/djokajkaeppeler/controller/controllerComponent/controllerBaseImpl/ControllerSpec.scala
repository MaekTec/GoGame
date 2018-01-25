package de.htwg.se.djokajkaeppeler.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.djokajkaeppeler.controller.GameStatus
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{Cell, CellStatus, Grid}
import de.htwg.se.djokajkaeppeler.model.playerComponent.playerBaseImpl.Player
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers{
  "A Controller" when {
    "with a empty Grid" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(smallGrid, "Player 1", "Player 2")
      controller.createEmptyGrid(3, ("Player 3", "Player 4"))
      val player1 = Player("Player 3", Cell(CellStatus.BLACK))
      val player2 = Player("Player 4", Cell(CellStatus.WHITE))
      "has two Players and one at turn" in {
        controller.playerAtTurn should be(player1)
        controller.playerAtTurnToString should be("Player 3")
        controller.playerNotAtTurn should be(player2)
        controller.playerNotAtTurnToString should be("Player 4")
      }

      "has empty Cells" in {
        controller.grid.cellAt(0, 0).isSet should be(false)
        controller.grid.cellAt(0, 1).isSet should be(false)
        controller.grid.cellAt(2, 1).isSet should be(false)
        controller.grid.cellAt(1, 1).isSet should be(false)
      }
      "as a game" in {
        controller.asGame should be (new Grid(3), (Player("Player 3", Cell(CellStatus.BLACK)),
          Player("Player 4", Cell(CellStatus.WHITE))))
      }
      "handle undo/redo of a turn" in {
        controller.grid.cellAt(0, 0).isSet should be(false)
        controller.turn(0, 0)
        controller.grid.cellAt(0, 0).isSet should be(true)
        controller.undo
        controller.grid.cellAt(0, 0).isSet should be(false)
        controller.redo
        controller.grid.cellAt(0, 0).isSet should be(true)
        controller.undo
      }
      "handle undo/redo of a set" in {
        controller.grid.cellAt(1, 1) should be(Cell(CellStatus.EMPTY))
        controller.set(1, 1, 1)
        controller.grid.cellAt(1, 1) should be(Cell(CellStatus.BLACK))
        controller.undo
        controller.grid.cellAt(1, 1) should be(Cell(CellStatus.EMPTY))
        controller.redo
        controller.grid.cellAt(1, 1)should be(Cell(CellStatus.BLACK))
      }
      "handle undo/redo of a skip" in {
        controller.playerAtTurn should be(player1)
        controller.skipTurn
        controller.playerAtTurn should be(player2)
        controller.undo
        controller.playerAtTurn should be(player1)
        controller.redo
        controller.playerAtTurn should be(player2)
      }
    }
    "when a player does a turn at the begin of a game" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(smallGrid, "Player 1", "Player 2")
      controller.turn(1, 1)
      "has empty Cells an one filled with black" in {
        controller.grid.cellAt(0, 0).isSet should be(false)
        controller.grid.cellAt(0, 1).isSet should be(false)
        controller.grid.cellAt(2, 1).isSet should be(false)
        controller.grid.cellAt(1, 1).isSet should be(true)
        controller.grid.cellAt(1, 1).status should be(CellStatus.BLACK)
      }
      "the game status is set to NEXT_PLAYER" in {
        controller.gameStatus should be(GameStatus.NEXT_PLAYER)
        controller.statusToString should be ("Player 2 is at turn")
      }
      "the player at turn is the other player after the first turn" in {
        controller.playerAtTurn should be(Player("Player 2", Cell(CellStatus.WHITE)))
      }
    }
    "when a player does an invalid move" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(smallGrid, "Player 1", "Player 2")
      controller.turn(1, 1)
      controller.turn(1, 1)
      "the game status is set to MOVE_NOT_VALID" in {
        controller.gameStatus should be(GameStatus.MOVE_NOT_VALID)
      }
    }
    "when a player skips" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(smallGrid, "Player 1", "Player 2")
      controller.turn(1, 1)
      controller.skipTurn()
      "the game status is set to SKIPPED" in {
        controller.gameStatus should be(GameStatus.SKIPPED)
      }
      "the other player is at turn" in {
        controller.playerAtTurn should be(Player("Player 1", Cell(CellStatus.BLACK)))
      }
    }
    "when both players skip" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(smallGrid, "Player 1", "Player 2")
      controller.skipTurn()
      controller.skipTurn()
      "the game status is set to SKIPPED" in {
        controller.gameStatus should be(GameStatus.IN_EVALUATION_MARK)
      }
      "the player is at turn" in {
        controller.playerAtTurn should be(Player("Player 2", Cell(CellStatus.WHITE)))
      }
      "and a player skips again" in {
        controller.skipTurn()
        controller.gameStatus should be(GameStatus.IN_EVALUATION_CONFIRM_OR_MARK)
      }
      "and again" in {
        controller.skipTurn()
        controller.gameStatus should be(GameStatus.PLAYOUT_OR_GAME_OVER)
      }
      "and again. The game is over" in {
        controller.skipTurn()
        controller.gameStatus should be(GameStatus.GAME_OVER)
        controller.statusToString should be("Draw, nobody won with 0 to 0 Points")
      }
    }
    "when a player turns in PLAYOUT_OR_GAME_OVER" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(smallGrid, "Player 1", "Player 2")
      controller.skipTurn()
      controller.skipTurn()
      controller.skipTurn()
      controller.skipTurn()
      "the game status is PLAYOUT_OR_GAME_OVER" in {
        controller.gameStatus should be(GameStatus.PLAYOUT_OR_GAME_OVER)
      }
      "the player can playout the game by making a turn again" in {
        controller.turn(0, 0)
        controller.gameStatus should be(GameStatus.NEXT_PLAYER)
      }
    }
    "when a player turns in IN_EVALUTION_MASK" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(smallGrid, "Player 1", "Player 2")
      controller.skipTurn()
      controller.skipTurn()
      "the game status is IN_EVALUTION_MASK" in {
        controller.gameStatus should be(GameStatus.IN_EVALUATION_MARK)
      }
      "the game status does'nt change because the player can mark dead stones" in {
        controller.turn(0, 0)
        controller.gameStatus should be(GameStatus.IN_EVALUATION_MARK)
      }
    }
    "when player white wins the game" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(smallGrid, "Player 1", "Player 2")
      controller.turn(0, 0)
      controller.skipTurn()
      controller.skipTurn()
      controller.skipTurn()
      controller.skipTurn()
      controller.skipTurn()
      "the game status is GAME_OVER" in {
        controller.gameStatus should be(GameStatus.GAME_OVER)
        controller.statusToString should be("Player 1 won the game with 16 to 0 Points")
      }
    }
    "when player black wins the game" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(smallGrid, "Player 1", "Player 2")
      controller.skipTurn()
      controller.turn(1, 0)
      controller.turn(0, 0)
      controller.turn(0, 1)
      controller.skipTurn()
      controller.skipTurn()
      controller.skipTurn()
      controller.skipTurn()
      controller.skipTurn()
      "the game status is GAME_OVER" in {
        controller.gameStatus should be(GameStatus.GAME_OVER)
        controller.statusToString should be("Player 2 won the game with 0 to 16 Points")
      }
    }
    "when a player tries to kill self, the move isn't vaild" should {
      val smallGrid = new Grid(2)
      val controller = new Controller(smallGrid, "Player 1", "Player 2")
      controller.turn(0, 1)
      controller.turn(1, 1)
      controller.turn(1, 0)
      controller.turn(0, 0)
      "has empty Cells an one filled with black" in {
        controller.grid.cellAt(0, 0).isSet should be(false)
        controller.grid.cellAt(0, 1).isSet should be(true)
        controller.grid.cellAt(1, 0).isSet should be(true)
        controller.grid.cellAt(1, 1).isSet should be(false)
        controller.gameStatus should be(GameStatus.MOVE_NOT_VALID)
      }
    }
    "when a Cell ist hardly set" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(smallGrid, "Player 1", "Player 2")
      controller.turn(1, 1)
      controller.set(1, 1, 2)
      controller.set(0, 0, 2)
      controller.set(0, 0, 0)
      "with WHITE the cell is set" in {
        controller.grid.cellAt(1, 1) should be(Cell(CellStatus.WHITE))
        controller.grid.cellAt(0, 0) should be(Cell(CellStatus.EMPTY))
      }
    }
  }
}
