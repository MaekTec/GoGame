package de.htwg.se.djokajkaeppeler.model

trait GridEvaluationStrategyTemplate {

  def countPoints(grid: Grid): (Grid, Int, Int)

  def evaluate(grid: Grid): Grid = {
    var territories: Map[Cell, Set[Set[(Int, Int)]]] = Map()
    var inTerritories: Set[(Int, Int)] = Set()

    for(r <- 0 until grid.size) {
      for(c <- 0 until grid.size) {
        if (!inTerritories.contains((r, c))) {
          inTerritories += ((r, c))
          var currentCell = grid.cellAt(r, c)
          var (territory, edges) = grid.getSetFilled(r, c, currentCell)
          inTerritories ++= territory
          currentCell.status match {
            case CellStatus.EMPTY | CellStatus.WHITE_TERI | CellStatus.BLACK_TERI =>
              if (edges.size == 1) {
                territories ++= addOrReplaceToMap(territories, territory, edges.toList.head.toTeri)
              } else {
                territories ++= addOrReplaceToMap(territories, territory, Cell(CellStatus.EMPTY))
              }
            case CellStatus.BLACK =>
              territories ++= addOrReplaceToMap(territories, territory, Cell(CellStatus.BLACK))
            case CellStatus.WHITE =>
              territories ++= addOrReplaceToMap(territories, territory, Cell(CellStatus.WHITE))
          }
        }
      }
    }
    mapToGrid(territories, grid.size)
  }

  private def mapToGrid(territories: Map[Cell, Set[Set[(Int, Int)]]], size: Int): Grid = {
    var gridNew = new Grid(size)
    territories.keys.foreach{ t =>
      territories.get(t).toSeq.flatten.flatten.foreach{ c =>
        gridNew = gridNew.set(c._1, c._2, t)
      }
    }
    gridNew
  }

  private def addOrReplaceToMap(territories : Map[Cell, Set[Set[(Int, Int)]]], territory: Set[(Int, Int)], cell: Cell)
  : Map[Cell, Set[Set[(Int, Int)]]] = {
    var territoriesNew : Map[Cell, Set[Set[(Int, Int)]]] = Map()
    territories.get(cell) match {
      case Some(t) => territoriesNew += (cell -> (t + territory))
      case None => territoriesNew += (cell -> Set(territory))
    }
    territoriesNew
  }
}
