# Santorini Game

## Starting a Game
To start the Santorini game, follow these steps:

## Backend Compilation:
Navigate to the `s24-hw3-santorini-erpan42/Santorini` directory.
Run the command `mvn compile` to compile the backend code.

## Starting the Backend Server:
After compiling the backend, navigate back to the `s24-hw3-santorini-erpan42/Santorini/src/main/java/homeworksantorini/Main.java` and run main to run the backend server

## Frontend Setup:
Open another terminal and navigate to the `s24-hw3-santorini-erpan42/frontend` directory.
Run the command `npm install` to install the necessary dependencies for the frontend.

## Starting the Frontend:
After the installation is complete, run the command `npm start` to start the frontend web application.
The game should now be accessible in your web browser.

## Playing the Game
The Santorini game I implemented has a user-friendly interface that is easily playable by one who knows the game rules

First, players can select one of the God cards to use that will alter some of the game rules (Rules attached below), players can choose to play with No God power (Also defaulted to no God). Demeter and Hephaestus can skip their second build, there will be a skip button on left indicating the optional build.
The current player's turn is displayed at the top of the screen, indicating which player should make the next move.
The game board represents a 5x5 grid, where each cell can contain workers and tower blocks.
Workers are represented by numbers (1 for Player 1 and 2 for Player 2) on the game board.
Tower blocks are represented by square brackets [], with the number of brackets indicating the height of the tower.
A dome is represented by a 0 inside the square brackets, indicating a completed tower.
Players can click on a cell to select a worker, move a worker, or build a tower block, depending on the current game state.
If a player attempts an invalid action, such as moving to an occupied cell or building on a completed tower, an error message will be displayed in the top right corner of the screen. The player can then redo the unfinished action according to the game rules.
When a player wins the game by moving their worker to the third level of a tower, a popup message will appear in the middle of the screen, announcing the winner and pausing the game.
To start a new game, click the "New Game" button located in the top left corner of the screen.

### Additional Notes
The game follows the standard rules of Santorini, where players take turns moving their workers and building tower blocks to reach the winning condition of moving a worker to the third level of a tower.
The game supports two players, and the turns alternate between Player 1 and Player 2.
The error messages displayed on the screen provide guidance on invalid actions and help players adhere to the game rules.
The "New Game" button allows players to reset the game and start a new session without the need to restart the frontend or backend.

### God Powers
## Demeter: 
Your Worker may build one additional time, but not on the same space. (UI hint: You will likely need a way to indicate that the player wants to skip the optional second build, e.g., with a "pass" button or by clicking on the worker's current location)

## Hephaestus: 
Your Worker may build one additional block (not dome) on top of your first block. (UI hint: You will likely need a way to indicate that the player wants to skip the optional second build, e.g., with a "pass" button or by clicking on the worker's current location)

## Minotaur: 
Your Worker may move into an opponent Worker’s space, if their Worker can be forced one space straight backwards to an unoccupied space at any level.

## Pan: 
You also win if your Worker moves down two or more levels.
