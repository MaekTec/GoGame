package de.htwg.se.djokajkaeppeler.aview

import de.htwg.se.djokajkaeppeler.model._

class Tui {
  def processInputLine(input: String, grid:Grid):Grid = {
    input match {
      case "q" => grid
      case "n" => new Grid(11)
      case _ => {
        input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: column :: value :: Nil => grid.set(row, column, intToCell(value))
          case _ => grid
        }
      }
    }
  }

  def intToCell(v: Int): Cell = {
    v match {
      case 0 => new Cell(CellStatus.EMPTY)
      case 1 => new Cell(CellStatus.BLACK)
      case 2 => new Cell(CellStatus.WHITE)
      case _ => new Cell(CellStatus.EMPTY)
    }
  }
}
