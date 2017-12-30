package de.htwg.se.djokajkaeppeler.controller

import de.htwg.se.djokajkaeppeler.model.{Cell, Grid}
import de.htwg.se.djokajkaeppeler.util.Observable

class Controller(var grid:Grid) extends Observable{
  def createEmptyGrid(size: Int):Unit = {
    grid = new Grid(size)
    notifyObservers
  }

  def gridToString: String = grid.toString

  def set(row: Int, col: Int, value: Cell):Unit = {
    grid = grid.set(row, col, value)
    notifyObservers
  }

}
