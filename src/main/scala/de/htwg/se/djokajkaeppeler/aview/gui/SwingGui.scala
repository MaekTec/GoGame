package de.htwg.se.go.aview.gui

import java.awt.Toolkit

import de.htwg.se.djokajkaeppeler.aview.gui.Board

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.djokajkaeppeler.controller._
import de.htwg.se.djokajkaeppeler.util.Observer

import scala.swing.event.MouseClicked
import scala.swing.event.MousePressed
import scala.swing.event.MouseReleased
import scala.io.Source._


class CellClicked(val row: Int, val column: Int) extends Event

class SwingGui(controller: Controller) extends Frame {

  def mouseClick(xCordinate: Int, yCordinate: Int, boardDimension: Dimension): Unit ={
    val toleranz = 10
    val gridSize = controller.grid.size
    //println(xCordinate + " " + yCordinate + " dim:" + boardDimension)
    val boardHight = boardDimension.height - 50
    val boardWidth = boardDimension.width - 50
    val x0 = 25
    val y0 = 25
    val lines = controller.grid.size - 1
    val deltaLines = boardHight / lines
    //println(deltaLines)
    for( a <- 0 to lines){
      var pointCol = a* deltaLines + 25
      for(b <- 0 to lines){
        var pointRow = b* deltaLines + 25
        if((xCordinate <=  pointRow  + toleranz  && xCordinate >=  pointRow - toleranz ) && (yCordinate <= pointCol  + toleranz  && yCordinate >=  pointCol -toleranz )) {
          controller.turn(a, b)
        }
      }
    }
  }



  var screeSize = Toolkit.getDefaultToolkit.getScreenSize
  var windowSize = ((screeSize.width min screeSize.height) * 0.7).toInt
  preferredSize = new Dimension(windowSize, windowSize)
  listenTo(controller)


  menuBar = new MenuBar {

    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") { controller.undo })
      contents += new MenuItem(Action("Redo") { controller.redo })
    }
    contents += new Menu("Skip") {
      mnemonic = Key.S
      contents += new MenuItem(Action("Skip") { controller.skipTurn() })
    }
    contents += new Menu("Options") {
      mnemonic = Key.O
      //contents += new MenuItem(Action("Size 1*1") { controller.resize(1) })
      //contents += new MenuItem(Action("Size 4*4") { controller.resize(4) })
      //contents += new MenuItem(Action("Size 9*9") { controller.resize(9) })

    }
  }


  val board = new Board(controller, preferredSize)


  val panel = new FlowPanel(){



    //resizable = false



      //add(skipButton, BorderPanel.Position.South)
      //add(board, BorderPanel.Position.Center)


    //contents += skipButton
    contents += new BoxPanel(Orientation.Vertical) {
      listenTo(this.mouse.clicks)
      contents += board
      reactions += {
        case MousePressed(com, point, _, _, _) =>
          mouseClick(point.x, point.y, this.size)
          board.repaint()
      }
    }


  }






  reactions += {
    case event: GridSizeChanged =>
    case event: Played     => repaint()
  }



  contents = new BorderPanel {
    add(panel, BorderPanel.Position.North)

  }



  title = "HTWG Go"
  resizable = true
  visible = true
}


