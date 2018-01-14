package de.htwg.se.djokajkaeppeler.model

import scala.math.sqrt

case class Grid(private val cells:Matrix[Cell]) {
  def this(size:Int) = this(new Matrix[Cell](size, Cell(CellStatus.EMPTY)))

  val size:Int = cells.size

  def cell(row:Int, col:Int):Cell = cells.cell(row, col)
  def set(row:Int, col:Int, value:Cell):Grid = copy(cells.replaceCell(row, col, value))
  def cellIsSet(row:Int, col:Int):Boolean = cells.cell(row, col).isSet

  /*
  def indexToRowCol(index: Int): (Int, Int) = {
    val r = index / size
    val c = index % size
    (r, c)
  }
*/

  override def toString: String = {
    val lineseparator = "|" + "---+" * (size-1) + "---|\n"
    val line = "| x "* size + "|\n"
    var box = "\n" + (lineseparator + line) * size + lineseparator
    for {
      row <- 0 until size
      col <- 0 until size
    } box = box.replaceFirst("x", cell(row, col).toString)
    box
  }

}