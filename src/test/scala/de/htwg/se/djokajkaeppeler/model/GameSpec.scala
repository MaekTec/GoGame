package de.htwg.se.djokajkaeppeler.model

import de.htwg.se.djokajkaeppeler.controller.Controller
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GameSpec extends WordSpec with Matchers {

  "When a Game is new " should {
    var game = new Controller(new Grid(11), (Player("A", Cell(CellStatus.BLACK)), Player("B", Cell(CellStatus.WHITE))))
    "have Player one to Turn" in {
      game.playerAtTurn should be(Player("A", Cell(CellStatus.BLACK)))
    }
    "When the Player trys to set without freedoms" in {
      game.grid = game.grid.set(0,1, Cell(CellStatus.BLACK))
      game.grid = game.grid.set(1,0, Cell(CellStatus.BLACK))
      game.grid = game.grid.set(2,1, Cell(CellStatus.BLACK))
      game.grid = game.grid.set(1,2, Cell(CellStatus.BLACK))

      game.grid = game.grid.set(1,1, Cell(CellStatus.WHITE))

      game.grid.checkIfMoveIsValid(1,1,Cell(CellStatus.WHITE)) should be(false)
    }
   "When the player try to set wothout freedoms but with a hit" in {
     game.grid = game.grid.set(1,1, Cell(CellStatus.EMPTY))

     game.grid = game.grid.set(0,0, Cell(CellStatus.BLACK))
     game.grid = game.grid.set(0,1, Cell(CellStatus.BLACK))
     game.grid = game.grid.set(0,2, Cell(CellStatus.BLACK))
     game.grid = game.grid.set(1,0, Cell(CellStatus.BLACK))
     game.grid = game.grid.set(2,1, Cell(CellStatus.BLACK))
     game.grid = game.grid.set(1,2, Cell(CellStatus.BLACK))
     game.grid = game.grid.set(2,0, Cell(CellStatus.BLACK))
     game.grid = game.grid.set(2,2, Cell(CellStatus.BLACK))

     game.grid = game.grid.set(0,3, Cell(CellStatus.WHITE))
     game.grid = game.grid.set(1,3, Cell(CellStatus.WHITE))
     game.grid = game.grid.set(2,3, Cell(CellStatus.WHITE))
     game.grid = game.grid.set(3,0, Cell(CellStatus.WHITE))
     game.grid = game.grid.set(3,1, Cell(CellStatus.WHITE))
     game.grid = game.grid.set(3,2, Cell(CellStatus.WHITE))

     game.grid = game.grid.set(1,1, Cell(CellStatus.WHITE))

     game.grid.checkIfMoveIsValid(1,1,Cell(CellStatus.WHITE)) should be (true)
   }
   "A Turn on a not Empry Field " in {
     game.turn(1,1) should be (None)
   }
   "A Turn on a Valid Field " in {
     game = new Controller(new Grid(3), (Player("A", Cell(CellStatus.BLACK)), Player("B", Cell(CellStatus.WHITE))))
     val testGame = new Controller(new Grid(3), (Player("A", Cell(CellStatus.BLACK)), Player("B", Cell(CellStatus.WHITE))))
     testGame.grid = testGame.grid.set(2,2, Cell(CellStatus.BLACK))
     testGame.player = (game.player._2, game.player._1)
     game.turn(2,2) should be(Some(testGame))
   }
   /*"A game Skiped to times in a row shoud be over" in {
     game.skipTurn() match {
       case Some(newGame) => {
         game = newGame
       }
       case None =>
     }
     game.skipTurn() match {
       case Some(newGame) => {
         game = newGame
       }
       case None =>
     }
     game.gameOver should be (true)
   }*/
    "Making another Skip" in {
      game.skipTurn() should be (None)
    }
    "Making a other move" in {
      game.turn(0,0) should be (None)
    }

  }
}

