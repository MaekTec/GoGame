package de.htwg.se.djokajkaeppeler.aview

import de.htwg.se.djokajkaeppeler.controller.Controller
import de.htwg.se.djokajkaeppeler.model.{CellStatus, Game, Grid}
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TuiSpec extends WordSpec with Matchers{

  "A Go Tui" should {
    var controller = new Controller(new Game(new Grid(11), "Player 1", "Player 2"))
    val tui = new Tui(controller)
    "create and empty Sudoku on input 'n'" in {
      tui.processInputLine("n")
      controller.game should be(new Game(new Grid(11), "Player 1", "Player 2"))
    }
    "set a cell to Blak at 0 0" in {
      tui.processInputLine("0 0")
      controller.game.grid.cell(0,0).status should be(CellStatus.BLACK)
    }
    "white skiped turn in Go on input 's'" in {
      var player = controller.game.player._1
      tui.processInputLine("s")
      controller.game.playerAtTurn should not be(player)
    }
    "set Grid size on 3 with player names: Bob and Elly with input 'n 3 Bob Elly'" in {
      tui.processInputLine("n 3 Bob Elly")
      controller.game should be(new Game(new Grid(3), "Bob", "Elly"))
    }
  }

}
