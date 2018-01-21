package de.htwg.se.djokajkaeppeler.controller

import scala.swing.event.Event

class Played extends Event
case class GridSizeChanged(newSize: Int) extends Event
