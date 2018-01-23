package de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl

import de.htwg.se.djokajkaeppeler.model.gridComponent.GridInterface

class GridEvaluationChineseStrategy extends GridEvaluationStrategyTemplate {

  override def countPoints(grid: GridInterface): (GridInterface, Int, Int) = {
    val gridToCount = evaluate(grid.removeAllDeadCells())
    var whitePoints = 0
    var blackPoints = 0
    for {
      row <- 0 until grid.size
      col <- 0 until grid.size
    } gridToCount.cellAt(row, col).status match {
      case CellStatus.BLACK | CellStatus.BLACK_TERI => blackPoints += 1
      case CellStatus.WHITE | CellStatus.WHITE_TERI => whitePoints += 1
      case _ =>
    }
    (gridToCount, blackPoints, whitePoints)
  }

}
