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
      "and can't be converted to a territory and reverse" in {
        emptyCell.toTeri should be(Cell(CellStatus.EMPTY))
        emptyCell.toTeriReverse should be(Cell(CellStatus.EMPTY))
      }
      "and can't be converted to alive and reverse" in {
        emptyCell.toAlive should be(Cell(CellStatus.EMPTY))
        emptyCell.toAliveAndTerReverse should be(Cell(CellStatus.EMPTY))
      }
      "and can't be converted to dead and reverse" in {
        emptyCell.toDead should be(Cell(CellStatus.EMPTY))
        emptyCell.toDeadOrReverse should be(Cell(CellStatus.EMPTY))
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
    "can be marked dead" should {
      val deadCell = Cell(CellStatus.BLACK_MARKED_DEAD)
      "is dead" in {
        deadCell.isDead should be(true)
      }
    }
    "can be alive" should {
      val aliveCell = Cell(CellStatus.WHITE)
      "is dead" in {
        aliveCell.isAlive should be(true)
      }
    }
    ",every cell ," should {
      val emptyCell = Cell(CellStatus.EMPTY)
      val whiteCell = Cell(CellStatus.WHITE)
      val blackCell = Cell(CellStatus.BLACK)
      val whiteTeriCell = Cell(CellStatus.WHITE_TERI)
      val blackTeriCell = Cell(CellStatus.BLACK_TERI)
      val whiteMarkedDeadCell = Cell(CellStatus.WHITE_MARKED_DEAD)
      val blackMarkedDeadCell = Cell(CellStatus.BLACK_MARKED_DEAD)
      "has a reverse" in {
        emptyCell.reverse should be(Cell(CellStatus.EMPTY))
        whiteCell.reverse should be(Cell(CellStatus.BLACK))
        blackCell.reverse should be(Cell(CellStatus.WHITE))
        whiteTeriCell.reverse should be(Cell(CellStatus.BLACK_TERI))
        blackTeriCell.reverse should be(Cell(CellStatus.WHITE_TERI))
        whiteMarkedDeadCell.reverse should be(Cell(CellStatus.BLACK_MARKED_DEAD))
        blackMarkedDeadCell.reverse should be(Cell(CellStatus.WHITE_MARKED_DEAD))
      }
    }
  }

}
