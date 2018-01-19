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

  //contents = new Button("skip!")

  val panel = new FlowPanel{

    val button = new Button("skip!")

    //resizable = false

    resizable = true
    contents += button
    listenTo(button)

  }


  contents = new BorderPanel {
    add(panel, BorderPanel.Position.North)

  }


  visible = true
}