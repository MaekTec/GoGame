package de.htwg.se.djokajkaeppeler.controller

import de.htwg.se.djokajkaeppeler.model.{Cell, CellStatus, Grid, Player}
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers{
  "A Controller" when {
    "with a empty Grid" should {
      val smallGrid = new Grid(4)
      val controller = new Controller(smallGrid, "Player 1", "Player 2")
      controller.createEmptyGrid(3, ("Player 3", "Player 4"))
      val player1 = Player("Player 3", Cell(CellStatus.BLACK))
      val player2 = Player("Player 4", Cell(CellStatus.WHITE))
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
