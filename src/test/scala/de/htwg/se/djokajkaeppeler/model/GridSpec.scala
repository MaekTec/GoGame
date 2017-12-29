package de.htwg.se.djokajkaeppeler.model

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner

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
        val testGrid = Grid(Matrix[Cell](Vector(Vector(new Cell, new Cell), Vector(new Cell, new Cell))))
      }
    }
    "created properly but empty" should {
      val tinygrid = new Grid(1)
      val smallGrid = new Grid(4)
      val normalGrid = new Grid(9)
      val awkwardGrid = new Grid(2)
      "give access to its Cells" in {
        tinygrid.cell(0, 0) should be(new Cell)
        smallGrid.cell(0, 0) should be(new Cell)
        smallGrid.cell(0, 1) should be(new Cell)
        smallGrid.cell(1, 0) should be(new Cell)
        smallGrid.cell(1, 1) should be(new Cell)
      }
      "allow to set individual Cells and remain immutable" in {
        val changedGrid = smallGrid.set(0, 0, Cell(CellStatus.WHITE))
        changedGrid.cell(0, 0) should be(Cell(CellStatus.WHITE))
        smallGrid.cell(0, 0) should be(Cell(CellStatus.EMPTY))
      }
    }
  }
}
