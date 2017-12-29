package de.htwg.se.djokajkaeppeler.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CellSpec extends WordSpec with Matchers{

  "A Cell" when {
    "is empty " should {
      val emptyCell = new Cell
      "have value CellStatus.EMPTY" in {
        emptyCell.status should be(CellStatus.EMPTY)
      }
      "not be set" in {
        emptyCell.isSet should be(false)
      }
    }
    "set to black value" should {
      val blackCell = Cell(CellStatus.BLACK)
      "return that status" in {
        blackCell.status should be(CellStatus.BLACK)
      }
      "be set" in {
        blackCell.isSet should be(true)
      }
    }
  }

}
