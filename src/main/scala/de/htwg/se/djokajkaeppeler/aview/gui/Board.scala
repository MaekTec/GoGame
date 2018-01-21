package de.htwg.se.djokajkaeppeler.aview.gui

import java.awt.geom.{Ellipse2D, Line2D}

import de.htwg.se.djokajkaeppeler.controller._

import scala.swing._
import scala.swing.{Component, Dimension}
import java.awt.{BasicStroke, Color, Graphics2D, Toolkit}

import de.htwg.se.djokajkaeppeler.model.gridComponent.gridBaseImpl.CellStatus
import de.htwg.se.go.aview.gui.SwingGui



class Board(val controller: Controller, var componentSize: Dimension) extends Component{

  componentSize.setSize((componentSize.height * 0.9) toInt, (componentSize.height* 0.9) toInt)
  preferredSize = new Dimension(componentSize.width , componentSize.height)

  override def paintComponent(g : Graphics2D) {
    //println(self.getSize)
    //println()

    //this.preferredSize = new Dimension((windowSize.height * 0.7*0.9).toInt , (windowSize.height * 0.7*0.9).toInt)

    listenTo(controller)
    val board = controller.grid
    val fields = board.size - 1



    g.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
      java.awt.RenderingHints.VALUE_ANTIALIAS_ON)
    g.setColor(new Color(220, 179, 92))

    g.fillRect(0, 0, preferredSize.width ,preferredSize.height)
    val squareSide = (preferredSize.height) - 50 min (preferredSize.width) - 50
    val wid = squareSide / fields
    val x0 = (preferredSize.width - squareSide)/2
    val y0 = (preferredSize.height - squareSide)/2
    g.setColor(Color.BLACK)
    // vertical lines
    for (x <- 0 to fields)
      g.draw(new Line2D.Double(x0 + x * wid, y0, x0 + x * wid, y0 + squareSide))
    // horizontal linesö
    for (y <- 0 to fields)
      g.draw(new Line2D.Double(x0, y0 + y * wid, x0 + squareSide, y0 + y * wid))
    g.setStroke(new BasicStroke(fields))

    for {
      row <- 0 until board.size
      col <- 0 until board.size
    }  if(board.cellIsSet(row,col)) {
      board.cellAt(row,col).status match {
        case CellStatus.BLACK => g.setColor(Color.BLACK)
        case CellStatus.WHITE => g.setColor(Color.WHITE)
        case _ =>

      }
      g.fillOval(col*wid,row*wid, 50, 50)
      //g.draw(new Ellipse2D.Double(col*wid,row*wid,  50,  50))
    }

  }
}