package de.htwg.se.djokajkaeppeler.model

case class Game(var grid: Grid, var player: (Player, Player)) {
  def this(grid:Grid, player1: String, player2: String) = this(grid, (Player(player1, CellStatus.BLACK), Player(player2, CellStatus.WHITE)))
  var playerAtTurn : Player = player._1
  def nextTurn : Unit = playerAtTurn = {
    player = player.swap
    player._1
  }
}
