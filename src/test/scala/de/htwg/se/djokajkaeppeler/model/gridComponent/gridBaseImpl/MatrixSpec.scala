package de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class MatrixSpec extends WordSpec with Matchers {
  "A Matrix is a tailor-made immutable data type that contains a two-dimentional Vector of Cells. A Matrix" when {
    "empty " should {
      "be created by using a dimention and a sample cell" in {
        val matrix = new Matrix[CellStatus.Value](2, CellStatus.EMPTY)
        matrix.size should be(2)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Matrix(Vector(Vector(CellStatus.EMPTY)))
        testMatrix.size should be(1)
      }

    }
    "filled" should {
      val matrix = new Matrix[CellStatus.Value](2, CellStatus.BLACK)
      "give access to its cells" in {
        matrix.cell(0, 0) should be(CellStatus.BLACK)
      }
      "replace cells and return a new data structure" in {
        val returnedMatrix = matrix.replaceCell(0, 0, CellStatus.WHITE)
        matrix.cell(0, 0) should be(CellStatus.BLACK)
        returnedMatrix.cell(0, 0) should be(CellStatus.WHITE)
      }
      "be filled using fill operation" in {
        val returnedMatrix = matrix.fill(CellStatus.WHITE)
        returnedMatrix.cell(0,0) should be(CellStatus.WHITE)
      }
    }
  }

}
