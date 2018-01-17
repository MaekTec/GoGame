package de.htwg.se.djokajkaeppeler.aview.gui

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.djokajkaeppeler.controller._
import scala.io.Source._

class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: Controller) extends Frame {

  listenTo(controller)





}