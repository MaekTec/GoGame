package de.htwg.se.djokajkaeppeler.aview

import de.htwg.se.djokajkaeppeler.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{Cell, CellStatus, Grid}
import de.htwg.se.djokajkaeppeler.model.playerComponent.playerBaseImpl.Player
import de.htwg.se.djokajkaeppeler.model.{CellStatus, Grid}
import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TuiSpec extends WordSpec with Matchers{

  "A Go Tui" should {
    var controller = new Controller(new Grid(11), "Player 1", "Player 2")
    val tui = new Tui(controller)
    "create and empty Sudoku on input 'n'" in {
      tui.processInputLine("n 9")
      controller.asGame should be(new Grid(9), (Player("Player 1", Cell(CellStatus.BLACK)),
        Player("Player 2", Cell(CellStatus.WHITE))))
    }
    "set a cell to Black at 0 0" in {
      tui.processInputLine("0 0")
      controller.grid.cellAt(0,0).status should be(CellStatus.BLACK)
    }
    "set a cell hard to Black at 0 0" in {
      tui.processInputLine("0 0 1")
      controller.grid.cellAt(0,0).status should be(CellStatus.BLACK)
    }
    "white skiped turn in Go on input 's'" in {
      var player = controller.player._1
      tui.processInputLine("s")
      controller.playerAtTurn should not be(player)
    }
    "set Grid size on 3 with player names: Bob and Elly with input 'n 3 Bob Elly'" in {
      tui.processInputLine("n 3 Bob Elly")
      controller.asGame should be(new Grid(3), (Player("Bob", Cell(CellStatus.BLACK)),
        Player("Elly", Cell(CellStatus.WHITE))))
    }
  }

}
