package de.htwg.se.djokajkaeppeler.model

import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.{Cell, CellStatus}
import de.htwg.se.djokajkaeppeler.model.playerComponent.Player
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {
  "A Player" when { "new" should {
    val player = Player("Your Name", Cell(CellStatus.BLACK))
    "have a name"  in {
      player.name should be("Your Name")
    }
    "have a nice String representation" in {
      player.toString should be("Your Name")
    }
  }}


}
