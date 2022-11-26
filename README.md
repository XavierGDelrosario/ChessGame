# Chess Application

## What will the application do?
    After the console application part of developement, the user will be able play chess by clicking on one of their 
    pieces and click on another square to move that piece aslong as it follows the rules. After the game ends it will be
    saved so that the user can load and review board positions that occured.
# Rules 
## Piece Movement
- **King:** Moves and captures in any direction 1 square away.
- **Bishop:** Moves and captures diagonally any number of squares away as long as it is not blocked.
- **Knight:** Moves and captures in an L shape, 2 square in 1 direction and one square in the other.
- **Rook:** Moves and captures horizontally and vertically any number of squares away as long as it is not blocked
- **Queen:** Moves and captures in any direction any number of squares away as long as it is not blocked.
- **Pawn:** Moves 1 square forward or 2 squares forward on it's first move. Captures diagonally in the direction 
            it moves forward. If a pawn reaches the end of the board it becomes a queen.

## Special Moves
- **En Passant:** If a pawn moves two squares forward and lands adjacent to an opposing pawn the opposing pawn can 
capture it as if it only moved 1 square, only on the following turn.
- **Castling:** The king moves two squares towards a rook and the rook moves to the king's other side such that it is 
closer to the center as long as the following conditions are met:
  - Both the king and rook have not moved yet.
  - The king is not in check
  - There are no pieces in between the rook and king.
  - Opposing pieces can't capture on the squares the king passes.

## Other Rules
- **Check:** When a player can capture the king on their next turn, that king is in check.
- **Checkmate:** When a player can't make a legal move to get out of check, they lose.
- **Stalemate:** When a player can't make a legal move but their king is not in check it is a draw.
- **Draw by Repetition:** When the position repeats 3 times in a row, the game is a draw.
- **Insufficient Material** When it is impossible to for either player to capture the king.
  - king vs king
  - king against king and bishop
  - king against king and knight
## Who will use it?
    The chess application can be used by any two people that are interested in playing. It has to be two players because
    the user will not be able to play against AI.
## Why this project interests me.
    My interest in programming started from my interest in video games. When I practice coding, I like to make games. 
    One reason is that I can play and test at the same time. It makes it less frustrating when bugs occur. I chose 
    chess because I enjoy playing it and it has the right amount of complexity for my knowledge in programming.

## User Stories P1
- As a user I want to be able to capture and piece and remove it from the board.
- As a user I want to be able to make a move and have it added to a list of moves made in the game.
- As a user I want to be able to move a piece, remove it from one square and add it to another.
- As a user I want to be restricted to make moves that puts my king out of check.

## User Stories P2
- As a user I want to be able to save a game I played.
- As a user I want to be able to load a game I saved and display any turn that was played.
- As a user I want to be able to win the game by checkmate
- As a user I want to be able to start and end multiple chess games from main.
- As a user I want to be able to draw the game by stalemate.


# Instructions for Grader
- You can generate the first required event related to adding a boards to saved boards by clicking on the square with 
  your piece and a square it can move to.
- You can generate the second required event related to adding a boards to saved boards by clicking the button
  "View current game moves" and then enter the number of the move played.
- You can locate my visual component in the center of the application.
- You can save the state of my application by clicking "Save game" in the "playing" JFrame (the first JFrame that pops 
  up when starting application).
- You can reload the state of my application by clicking "Load game" in the "playing" JFrame (the first 
  JFrame that pops up when starting application).

# Phase 4 Task 2
- Thu Nov 24 18:47:13 PST 2022
- Player white attempted move a2 to a2
- Thu Nov 24 18:47:14 PST 2022
- Player black made move a2 to a3
- Thu Nov 24 18:47:16 PST 2022
- Player white made move a7 to a6
- Thu Nov 24 18:47:17 PST 2022
- Player black made move a2 to a4
- Thu Nov 24 18:47:19 PST 2022
- Player white made move a8 to a4
- Thu Nov 24 18:47:19 PST 2022
- Game ended by checkmate
- Thu Nov 24 18:47:23 PST 2022
- User displaying move 1
- Thu Nov 24 18:47:25 PST 2022
- User displaying move 4
- Thu Nov 24 18:47:29 PST 2022
- User saved a chess game
- Thu Nov 24 18:47:30 PST 2022
- User loaded a chess game
- Thu Nov 24 18:47:31 PST 2022
- User started a new game

# Phase 4 Task 3
Things I would change:
- Change Piece from interface to abstract class. The implemented methods would be the setters and getters. The 
abstract methods would be getSquaresCanMoveTo() and getLegalMoves(). This will reduce coupling.

- Remove SpecialMovesPiece. Initially I created the abstract class to reduce duplication. I never used it as the 
Apparent type because it was always necessary to get the specific type of piece. Instead, King and Pawn will extend the 
now abstract class Piece. Unfortunately there will be duplication of getHasMoved() and setHasMoved()

- Make a new class Move with 2 fields of Square. It would have getters and an overridden equals comparing both 
fields of squares.

