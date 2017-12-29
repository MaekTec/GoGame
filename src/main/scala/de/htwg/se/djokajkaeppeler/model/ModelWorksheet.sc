import de.htwg.se.djokajkaeppeler.model.{CellStatus, Grid}

val grid = new Grid(9, CellStatus.EMPTY)
grid.setCell(0, 0, CellStatus.BLACK)
grid.getCell(0, 1).equals(CellStatus.EMPTY)
