package de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl

import de.htwg.se.djokajkaeppeler.model.gridComponent.CellInterface
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class GridSpec extends WordSpec with Matchers {
  "A Grid is the playingfield of Go. A Grid" when {
    "to be constucted" should {
      "be created with the length of its edges as size. Pracitcally relevant are size 1, 4, 9, 11 and 19" in {
        val tinygrid = new Grid(1)
        val verysmallGrid = new Grid(9)
        val smallGrid = new Grid(11)
        val normalGrid = new Grid(19)
        val awkwardGrid = new Grid(4)
      }
      "for test purposes only created with a Matrix of Cells" in {
        val awkwardGrid = Grid(new Matrix(4, new Cell))
        val testGrid = Grid(Matrix[CellInterface](Vector(Vector(new Cell, new Cell), Vector(new Cell, new Cell))))
      }
    }
    "created properly but empty" should {
      val tinygrid = new Grid(1)
      val smallGrid = new Grid(4)
      val normalGrid = new Grid(9)
      val awkwardGrid = new Grid(2)
      "give access to its Cells" in {
        tinygrid.cellAt(0, 0) should be(new Cell)
        smallGrid.cellAt(0, 0) should be(new Cell)
        smallGrid.cellAt(0, 1) should be(new Cell)
        smallGrid.cellAt(1, 0) should be(new Cell)
        smallGrid.cellAt(1, 1) should be(new Cell)
      }
      "allow to set individual Cells and remain immutable" in {
        val changedGrid = smallGrid.set(0, 0, Cell(CellStatus.WHITE))
        changedGrid.cellAt(0, 0) should be(Cell(CellStatus.WHITE))
        smallGrid.cellAt(0, 0) should be(Cell(CellStatus.EMPTY))
      }
    }
    "prefilled with values CellStatus.WHITE and CellStatus.BLACK alternately" should {
      val tinyGrid = Grid(new Matrix[CellInterface](Vector(Vector(Cell(CellStatus.WHITE)))))
      val smallGrid = Grid(new Matrix[CellInterface](Vector(Vector(Cell(CellStatus.WHITE), Cell(CellStatus.BLACK)), Vector(Cell(CellStatus.WHITE), Cell(CellStatus.BLACK)))))
      "have the right values in the right places" in {
        smallGrid.cellAt(0, 0) should be(Cell(CellStatus.WHITE))
        smallGrid.cellAt(0, 1) should be(Cell(CellStatus.BLACK))
        smallGrid.cellAt(1, 0) should be(Cell(CellStatus.WHITE))
        smallGrid.cellAt(1, 1) should be(Cell(CellStatus.BLACK))
      }
      "can search for cells which are connected to each other" in {
        smallGrid.getSetFilled(0, 0, Cell(CellStatus.WHITE))  should be((Set((0, 0), (1, 0)), Set(Cell(CellStatus.BLACK))))
      }
      "can be marked as a dead group" in {
        smallGrid.markOrUnmarkDeadGroup(0, 0) should be(Grid(new Matrix[CellInterface](Vector(Vector(Cell(CellStatus.WHITE_MARKED_DEAD), Cell(CellStatus.BLACK)), Vector(Cell(CellStatus.WHITE_MARKED_DEAD), Cell(CellStatus.BLACK))))))
      }
    }
    "prefilled with 1 to n on the diagonal" should {
      val normalGrid = new Grid(9)
      val diagonalGrid = normalGrid.set(0, 0, Cell(CellStatus.BLACK)).set(1, 1, Cell(CellStatus.BLACK)).set(2, 2, Cell(CellStatus.BLACK))
        .set(3, 3, Cell(CellStatus.BLACK)).set(4, 4, Cell(CellStatus.BLACK)).set(5, 5, Cell(CellStatus.BLACK)).set(6, 6, Cell(CellStatus.BLACK)).set(7, 7, Cell(CellStatus.BLACK))
          .set(8, 8, Cell(CellStatus.BLACK))
      "have blocks with the right cells" in {
        diagonalGrid.cellAt(0, 0) should be(Cell(CellStatus.BLACK))
        diagonalGrid.cellAt(0, 4) should be(Cell(CellStatus.EMPTY))
        diagonalGrid.cellAt(0, 8) should be(Cell(CellStatus.EMPTY))
        diagonalGrid.cellAt(4, 0) should be(Cell(CellStatus.EMPTY))
        diagonalGrid.cellAt(4, 4) should be(Cell(CellStatus.BLACK))
        diagonalGrid.cellAt(4, 8) should be(Cell(CellStatus.EMPTY))
        diagonalGrid.cellAt(8, 0) should be(Cell(CellStatus.EMPTY))
        diagonalGrid.cellAt(8, 4) should be(Cell(CellStatus.EMPTY))
        diagonalGrid.cellAt(8, 8) should be(Cell(CellStatus.BLACK))
      }
    }
    "prefilled with dead and teritory stones alternaly" should {
      val smallGrid = Grid(new Matrix[CellInterface](Vector(Vector(Cell(CellStatus.WHITE_TERI), Cell(CellStatus.BLACK_MARKED_DEAD)), Vector(Cell(CellStatus.WHITE_TERI), Cell(CellStatus.BLACK_MARKED_DEAD)))))
      "can be made alive and teri reverse" in {
        smallGrid.allDeathCellsToAliveAndTeriReverse() should be(Grid(new Matrix[CellInterface](Vector(Vector(Cell(CellStatus.WHITE), Cell(CellStatus.BLACK)), Vector(Cell(CellStatus.WHITE), Cell(CellStatus.BLACK))))))
      }
      "can make all dead stones to alive" in {
        smallGrid.removeAllDeadCells() should be(Grid(new Matrix[CellInterface](Vector(Vector(Cell(CellStatus.WHITE_TERI), Cell(CellStatus.EMPTY)), Vector(Cell(CellStatus.WHITE_TERI), Cell(CellStatus.EMPTY))))))
      }
    }
    /*"prefilled at 1,1" should {
      var normalGrid = new Grid(9).set(1, 1, Cell(CellStatus.WHITE))
      "asdf" in {
        normalGrid.checkIfMoveIsValid(1, 1, Cell(CellStatus.BLACK)) should be(false)
      }
    }*/
  }
}
