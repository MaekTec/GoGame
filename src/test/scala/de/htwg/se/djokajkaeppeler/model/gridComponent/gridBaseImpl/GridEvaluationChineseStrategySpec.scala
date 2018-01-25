package de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl

import de.htwg.se.djokajkaeppeler.model.gridComponent.CellInterface
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class GridEvaluationChineseStrategySpec extends WordSpec with Matchers {

  "An  GridEvaluationChineseStrategy" should {
    val evaluationStrategy = new GridEvaluationChineseStrategy

    "can evaluate a played Grid" in {
      val playedTinyGrid1 = new Grid(2).set(0, 0, Cell(CellStatus.WHITE)).set(0, 1, Cell(CellStatus.EMPTY)).set(1, 0, Cell(CellStatus.WHITE)).set(1, 1, Cell(CellStatus.EMPTY))
      evaluationStrategy.evaluate(playedTinyGrid1) should be(new Grid(2).set(0, 0, Cell(CellStatus.WHITE)).set(0, 1, Cell(CellStatus.WHITE_TERI)).set(1, 0, Cell(CellStatus.WHITE)).set(1, 1, Cell(CellStatus.WHITE_TERI)))
      val playedTinyGrid2 = new Grid(2).set(0, 0, Cell(CellStatus.BLACK)).set(0, 1, Cell(CellStatus.EMPTY)).set(1, 0, Cell(CellStatus.BLACK)).set(1, 1, Cell(CellStatus.EMPTY))
      evaluationStrategy.evaluate(playedTinyGrid2) should be(new Grid(2).set(0, 0, Cell(CellStatus.BLACK)).set(0, 1, Cell(CellStatus.BLACK_TERI)).set(1, 0, Cell(CellStatus.BLACK)).set(1, 1, Cell(CellStatus.BLACK_TERI)))

      val playedTinyGrid3 = new Grid(2).set(0, 0, Cell(CellStatus.EMPTY)).set(0, 1, Cell(CellStatus.BLACK)).set(1, 0, Cell(CellStatus.WHITE)).set(1, 1, Cell(CellStatus.EMPTY))
      evaluationStrategy.evaluate(playedTinyGrid3) should be(new Grid(2).set(0, 0, Cell(CellStatus.EMPTY)).set(0, 1, Cell(CellStatus.BLACK)).set(1, 0, Cell(CellStatus.WHITE)).set(1, 1, Cell(CellStatus.EMPTY)))
    }

    "can count the points" in {
      val playedTinyGrid3 = new Grid(2).set(0, 0, Cell(CellStatus.EMPTY)).set(0, 1, Cell(CellStatus.BLACK)).set(1, 0, Cell(CellStatus.WHITE)).set(1, 1, Cell(CellStatus.EMPTY))
      evaluationStrategy.countPoints(playedTinyGrid3) should be((playedTinyGrid3, 1, 1))
    }


    /*val preparedTinyGrid = createStrategy.prepare(new Grid(size = 1))
    val preparedSmallGrid = createStrategy.prepare(new Grid(size = 4))
    "have a solved Grid as prepare step" in {
      val preparedTinyGrid = createStrategy.prepare(new Grid(size = 1))
      preparedTinyGrid.solved should be(true)
      preparedSmallGrid.solved should be(true)
    }
    "should correctly count the set cells" in {
      createStrategy.numSetCells(preparedTinyGrid) should be(1)
      createStrategy.numSetCells(preparedSmallGrid) should be(16)

    }
    "should remove a pair of Cells" in {
      val removePairGrid = createStrategy.removePair(preparedSmallGrid, 0, 0, 3, 3)
      removePairGrid.solved should be(false)
      removePairGrid.cell(0, 0).value should be(0)
      removePairGrid.cell(3, 3).value should be(0)
    }
    "should find a symmetric Cell" in {
      createStrategy.symmetricCell(4, 0, 0) should be(3, 3)
      createStrategy.symmetricCell(4, 0, 3) should be(3, 0)
    }
    "solve a Grid with a symmetric solution" in {
      val symmetricGrid = createStrategy.createNewGrid(4)
      symmetricGrid.isSymmetric should be(true)
    }*/
  }
}