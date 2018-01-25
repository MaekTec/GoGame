package de.htwg.se.djokajkaeppeler.controller.controllerComponent.controllerBaseImpl

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import de.htwg.se.djokajkaeppeler.controller.GameStatus

@RunWith(classOf[JUnitRunner])
class GameStatusSpec extends WordSpec with Matchers {
  "A Gamestatus " when { "used" should {
    "transform string"  in {
      GameStatus.fromString("") should be(None)
      GameStatus.fromString("NEXT_PLAYER ") should be(Some(GameStatus.NEXT_PLAYER))
      GameStatus.fromString(" MOVE_NOT_VALID") should be(Some(GameStatus.MOVE_NOT_VALID))
      GameStatus.fromString("SKIPPED  ") should be(Some(GameStatus.SKIPPED))
      GameStatus.fromString("IN_EVALUATION_MARK ") should be(Some(GameStatus.IN_EVALUATION_MARK))
      GameStatus.fromString("IN_EVALUATION_CONFIRM_OR_MARK ") should be(Some(GameStatus.IN_EVALUATION_CONFIRM_OR_MARK))
      GameStatus.fromString("GAME_OVER ") should be(Some(GameStatus.GAME_OVER))
      GameStatus.fromString("PLAYOUT_OR_GAME_OVER") should be(Some(GameStatus.PLAYOUT_OR_GAME_OVER))
    }

  }}
}
