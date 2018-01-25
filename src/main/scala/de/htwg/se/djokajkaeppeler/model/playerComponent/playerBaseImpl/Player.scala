package de.htwg.se.djokajkaeppeler.model.playerComponent.playerBaseImpl

import com.google.inject.Inject
import com.google.inject.assistedinject.Assisted
import de.htwg.se.djokajkaeppeler.model.gridComponent.CellInterface
import de.htwg.se.djokajkaeppeler.model.playerComponent.PlayerInterface

case class Player @Inject() (@Assisted name: String, @Assisted cellstatus: CellInterface
                            ) extends PlayerInterface {
   override def toString:String = name
}
