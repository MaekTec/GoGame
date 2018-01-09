package de.htwg.se.djokajkaeppeler.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GameSpec extends WordSpec with Matchers {

  "When a Game is new " should {
    val game = new Game(new Grid(11), (Player("A", Cell(CellStatus.BLACK)), Player("B", Cell(CellStatus.WHITE))))
    "have Player one to Turn" in {
      game.playerAtTurn should be(Player("A", Cell(CellStatus.BLACK)))
    }
    "When the Player trys to set without freedoms" in {
      game.grid = game.grid.set(0,1, Cell(CellStatus.BLACK))
      game.grid = game.grid.set(1,0, Cell(CellStatus.BLACK))
      game.grid = game.grid.set(2,1, Cell(CellStatus.BLACK))
      game.grid = game.grid.set(1,2, Cell(CellStatus.BLACK))
      game.playerAtTurn = game.player._2
      println(game)
      game.checkIfMoveIsValid(1,1,Cell(CellStatus.WHITE)) should be(false)
    }


  }
}

