# Santorini Game

## Starting a Game
To start the Santorini game, follow these steps:

## Backend Compilation:
Navigate to the `s24-hw3-santorini-erpan42/Santorini` directory.
Run the command `mvn compile` to compile the backend code.

## Starting the Backend Server:
After compiling the backend, navigate back to the `s24-hw3-santorini-erpan42` directory using the command `cd ..`.
Run the following command to start the main method and run the server:
`/opt/homebrew/Cellar/openjdk/21.0.1/libexec/openjdk.jdk/Contents/Home/bin/java @/var/folders/1p/02vt8fjs6f9\_dn7slcsq9qnm0000gn/T/cp\_9wedx158jh0y415xhj2x5z2ap.argfile homeworksantorini.Main`

## Frontend Setup:
Open another terminal and navigate to the `s24-hw3-santorini-erpan42/Santorini/frontend` directory using the command `cd Santorini/frontend`.
Run the command `npm install` to install the necessary dependencies for the frontend.

## Starting the Frontend:
After the installation is complete, run the command `npm start` to start the frontend web application.
The game should now be accessible in your web browser.

## Playing the Game
The Santorini game I implemented has a user-friendly interface that is easily playable by one who knows the game rules

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