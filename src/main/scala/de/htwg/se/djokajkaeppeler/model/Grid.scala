package de.htwg.se.djokajkaeppeler.model

case class Grid(cells:Vector[Vector[CellStatus.Value]]) {
  def this(size:Int, filling:CellStatus.Value) = this(Vector.tabulate(size, size) {(row, col) => filling})
  val size:Int = cells.size
  def getCell(row:Int, col:Int) :CellStatus.Value = cells (row)(col)
  def fill(filling:CellStatus.Value):Grid = copy(Vector.tabulate(size, size) {(row, col) => filling})
  def setCell(row:Int, col:Int, cell:CellStatus.Value):Grid = copy(cells.updated(row, cells(row).updated(col, cell)))
}
