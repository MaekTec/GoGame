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

  def fromString(s: String): Option[GameStatus] = s.trim match {
    case "NEXT_PLAYER" =>
      Some(NEXT_PLAYER)
    case "MOVE_NOT_VALID" =>
      Some(MOVE_NOT_VALID)
    case "SKIPPED" =>
      Some(SKIPPED)
    case "IN_EVALUATION_MARK" =>
      Some(IN_EVALUATION_MARK)
    case "IN_EVALUATION_CONFIRM_OR_MARK" =>
      Some(IN_EVALUATION_CONFIRM_OR_MARK)
    case "PLAYOUT_OR_GAME_OVER" =>
      Some(PLAYOUT_OR_GAME_OVER)
    case "GAME_OVER" =>
      Some(GAME_OVER)
    case _ =>
      None
  }


  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }
}
