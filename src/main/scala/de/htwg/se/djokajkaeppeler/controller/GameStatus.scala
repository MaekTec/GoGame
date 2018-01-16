package de.htwg.se.djokajkaeppeler.controller

object GameStatus extends Enumeration {
  type GameStatus = Value
  val IDLE, NEXT_PLAYER, MOVE_NOT_VALID, SKIPPED, GAME_OVER, IN_EVALUATION  = Value

  val map = Map[GameStatus, String](
    IDLE -> "",
    NEXT_PLAYER -> " is at turn",
    MOVE_NOT_VALID ->"This move isn't valid",
    SKIPPED ->"Player skipped",
    GAME_OVER ->"Game over. You can start an new one.",
    IN_EVALUATION ->"In evaluation")

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }
}
