package de.htwg.se.djokajkaeppeler.model

case class Grid(private val cells:Matrix[Cell]) {
  def this(size:Int) = this(new Matrix[Cell](size, Cell(CellStatus.EMPTY)))
  val size:Int = cells.size
  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
  def set(row:Int, col:Int, value:Cell):Grid = copy(cells.replaceCell(row, col, value))

}