package de.htwg.se.go.aview.gui

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.djokajkaeppeler.controller._
import scala.io.Source._

class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: Controller) extends Frame {

  listenTo(controller)

  title = "HTWG Go"


  def top = new MainFrame {
    title = "Hello, World!"
    contents = new Button {
      text = "Click Me!"
    }
  }

  visible = true
}