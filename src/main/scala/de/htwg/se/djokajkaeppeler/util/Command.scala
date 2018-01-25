package de.htwg.se.djokajkaeppeler.util


trait Command {

  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit

}
