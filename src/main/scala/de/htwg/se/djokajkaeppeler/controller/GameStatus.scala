package de.htwg.se.djokajkaeppeler.controller

object GameStatus extends Enumeration {
  type GameStatus = Value
  val NEXT_PLAYER, MOVE_NOT_VALID, SKIPPED,
  IN_EVALUATION_MARK, IN_EVALUATION_CONFIRM_OR_MARK, PLAYOUT_OR_GAME_OVER,
  GAME_OVER = Value

  val map = Map[GameStatus, String](
    NEXT_PLAYER -> " is at turn",
    MOVE_NOT_VALID ->"This move isn't valid",
    SKIPPED ->"Player skipped",
    GAME_OVER ->"Game over. You can start an new one.",
    IN_EVALUATION_MARK ->"In evaluation. Mark dead stones!",
    IN_EVALUATION_CONFIRM_OR_MARK -> "Confirm or mark again.",
    PLAYOUT_OR_GAME_OVER -> "Skip or play out."
  )

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }
}
