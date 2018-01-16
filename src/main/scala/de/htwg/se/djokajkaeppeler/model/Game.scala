package de.htwg.se.djokajkaeppeler.model

case class Game(var grid: Grid, var player: (Player, Player),var skiped: Boolean, var gameOver: Boolean = false) {
  def this(grid: Grid, player: (Player, Player)){
    this(grid,player,false)
  }
  var playerAtTurn : Player = player._1

  def this(grid:Grid, player1: String, player2: String) = this(grid, (Player(player1, Cell(CellStatus.BLACK)), Player(player2, Cell(CellStatus.WHITE))))

  def turn(row: Int, col: Int): Option[Game] = {
    if(gameOver) {
      println("The Game is over please start a new Game")
      print(new Evaluation(grid).evaluate())
      return None
    }
    skiped = false
    if (grid.rowColIsValid(row, col) && !grid.cellIsSet(row, col)) {
      var newGame = copy(grid.set(row, col, playerAtTurn.cellstatus), player)
      if (newGame.grid.checkIfMoveIsValid(row, col, playerAtTurn.cellstatus)) {
          newGame.grid.checkForHits(row, col, playerAtTurn.cellstatus) match {
          case Some(c) => c.foreach(rc => newGame = copy(newGame.grid.set(rc._1, rc._2, Cell(CellStatus.EMPTY)), player))
          case None =>
        }
        Some(copy(newGame.grid, newGame.player.swap))
      } else {
        None
      }
    } else {
      None
    }
  }

  def skipTurn(): Option[Game] = {
    if(gameOver) {
      //println("The Game is over please start a new Game")
      return None
    }
    if (skiped) {
      //println("Game over")
      gameOver = true
      return None
    } else
      Some(copy(grid, player.swap,true))
  }
}
