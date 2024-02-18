package homeworksantorini;


public class GameController {
    private Player[] players;
    private Player currentPlayer;
    private Grid grid;

    private static final int WIN_CONDITION_TOWER_LEVEL = 3;

    public GameController(String player1Id, String player2Id, Grid grid) {
        this.grid = grid;
        this.players = new Player[2];
        this.players[0] = new Player(player1Id, grid);
        this.players[1] = new Player(player2Id, grid);
        this.currentPlayer = this.players[0]; // Player 1 starts
    }

    public void startGameWithPositions(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        System.out.println("Starting game and placing workers for both players.");

        placeWorkerInitial(players[0], x1, y1, x2, y2);
        placeWorkerInitial(players[1], x3, y3, x4, y4);

        System.out.println("All workers placed. Player " + currentPlayer.getId() + " starts the game.");
    }

    private void placeWorkerInitial(Player player, int x1, int y1, int x2, int y2) {
        int[][] positions = {{x1, y1}, {x2, y2}};
        for (int i = 0; i < player.getWorkers().size(); i++) {
            while (true) {
                Cell cell = grid.getCell(positions[i][0], positions[i][1]);
                if (cell != null && !cell.hasWorker()) {
                    Worker worker = player.getWorkers().get(i);
                    worker.setPosition(cell);
                    cell.setWorker(worker);
                    System.out.println("Player " + player.getId() + " placed worker " + (i + 1) +
                         " at (" + positions[i][0] + ", " + positions[i][1] + ").");
                    break; // Break the loop if placement is successful
                } else {
                    System.out.println("Error: Initial position for worker " + (i + 1) + " of Player " 
                        + player.getId() + " is invalid or occupied. Please choose a different position.");
                    // Simulate obtaining new positions (prompt for user input again)
                    // positions[i][0] = // new x coordinate;
                    // positions[i][1] = // new y coordinate;
                }
            }
        }
    }
    // This method is called when a player selects a worker and a cell to move to
    // Will be implemented later on via UI or CLI
    public void startTurn(Worker worker, Cell targetMoveCell) {
        boolean isMoved = false;
        while (isMoved == false) {
            if (currentPlayer.getWorkers().contains(worker)) {
                boolean moveSuccessful = currentPlayer.moveWorker(worker, targetMoveCell);
                if (moveSuccessful) {
                    // Check if the player has won
                    if (checkWinCondition(worker)) {
                        endGame(currentPlayer);
                    } else {
                        isMoved = true;
                        System.out.println("Move successful. Now select a cell to build.");
                        // prompt the player to select a cell to build on, for now ill just put targetMoveCell
                        startBuild(worker, targetMoveCell);
                    }
                } else {
                    System.out.println("Move was not successful. Please try again.");
                    // Handle unsuccessful move (e.g., prompt the player to select a different cell).
                }
            } else {
                System.out.println("This worker does not belong to the current player.");
            }
        }

    }

    public void startBuild(Worker worker, Cell targetBuildCell) {
        boolean isBuilt = false;
        while (isBuilt == false) {
            boolean buildSuccessful = currentPlayer.buildWithWorker(worker, targetBuildCell);
            if (buildSuccessful) {
                isBuilt = true;
                System.out.println("Build successful. Ending turn.");
                changeTurn(); // End the current player's turn and switch to the next player
            } else {
                System.out.println("Build was not successful. Please try again.");
            }
        }

    }
    
    public boolean checkWinCondition(Worker worker) {
        return worker.getPosition().getTowerLevel() == WIN_CONDITION_TOWER_LEVEL;
    }

    public void changeTurn() {
        // Toggle between players[0] and players[1]
        currentPlayer = (currentPlayer == players[0]) ? players[1] : players[0];
        System.out.println("It's now Player: " + currentPlayer.getId() + "'s turn.");
    }  

    public void endGame(Player winner) {
        // Announce the winner
        System.out.println("Game Over. The winner is Player: " + winner.getId());

        // Clean up the game state if necessary
        this.grid = null; // Reset the grid
        for (Player player : players) {
            for (Worker worker : player.getWorkers()) {
                worker.setPosition(null); // Reset workers' positions
            }
        }
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Player[] getPlayers() {
        return this.players;
    }
}

