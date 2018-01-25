[![Build Status](https://travis-ci.org/MaekTec/GoGame.svg?branch=dev)](https://travis-ci.org/MaekTec/GoGame) - [![Coverage Status](https://coveralls.io/repos/github/MaekTec/GoGame/badge.svg?branch=dev)](https://coveralls.io/github/MaekTec/GoGame?branch=master) - Dev
  
[![Build Status](https://travis-ci.org/MaekTec/GoGame.svg?branch=dev-maektec)](https://travis-ci.org/MaekTec/GoGame) - [![Coverage Status](https://coveralls.io/repos/github/MaekTec/GoGame/badge.svg?branch=dev-maektec)](https://coveralls.io/github/MaekTec/GoGame?branch=dev-maektec) - Markus
  
[![Build Status](https://travis-ci.org/MaekTec/GoGame.svg?branch=dev-flobolo)](https://travis-ci.org/MaekTec/GoGame) -   [![Coverage Status](https://coveralls.io/repos/github/MaekTec/GoGame/badge.svg?branch=dev-flobolo)](https://coveralls.io/github/MaekTec/GoGame?branch=dev-flobolo) - Florian
  


Go in Scala
=========================

This is a implementation of Go in Scala for the SE the
class Software Engineering at the University of Applied Science HTWG Konstanz, Germany. (WS 17/18)

## Goals of Project
* learning Scala
* learing Git
* Tests
* Srum
* TUI and GUI
* MVC Architecture
* Continious Integretion with Travis CI
* Design Patterns
    * Observer Pattern (for MVC)
    * Command Pattern (do, undo and redo)
    * Strategy Pattern (for different rules)
    * Factory Pattern
* Components and Interfaces
* Dependency Injection
* FILE IO, Serialization in XML and JSON

## Go Rules
* https://senseis.xmp.net/?ChineseRules (the rules we implemented)
* https://senseis.xmp.net/?Scoring (All rules)
* https://senseis.xmp.net/?ComputerGoAlgorithms (famous algorithms for Go problems)
* https://www.brettspielnetz.de/spielregeln/go.php (in German, but not sure which rules)

## Game Instructions
See the Chinese rules link above for rules.

* Start a new game
  * 'n 11' for a 11 * 11 Grid
  * Options -> Size 11 * 11
  
* Make a turn
  * '1 1' for example places a stone at row 1 and column 1
  * Click on a crossing
  
* Skip a turn
  * 's'
  * Skip
  
* Undo/Redo
  * 'z' and 'y'
  * Edit -> Undo/Redo
  
* Quit
  * 'q'
  
When two players skip the evaluation start. Every player can mark dead stones or confirm that all dead stones are marked or play the game out.
  
  
