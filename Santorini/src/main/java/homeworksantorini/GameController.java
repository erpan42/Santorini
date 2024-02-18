package homeworksantorini;


public class GameController {
    private Player[] players;
    private Player currentPlayer;
    private Grid grid;

    private static final int WIN_CONDITION_TOWER_LEVEL = 3;
    private static final int[][] INITIAL_POSITIONS_PLAYER1 = {{0, 0}, {1, 1}};
    private static final int[][] INITIAL_POSITIONS_PLAYER2 = {{3, 3}, {4, 4}};

    public GameController(String player1Id, String player2Id, Grid grid) {
        this.grid = grid;
        this.players = new Player[2];
        this.players[0] = new Player(player1Id, grid);
        this.players[1] = new Player(player2Id, grid);
        this.currentPlayer = this.players[0]; // Player 1 starts
    }

    public void startGame() {
        System.out.println("Starting game and placing workers for both players.");

        placeWorkersInitial(players[0], INITIAL_POSITIONS_PLAYER1);
        placeWorkersInitial(players[1], INITIAL_POSITIONS_PLAYER2);

        System.out.println("All workers placed. Player " + currentPlayer.getId() + " starts the game.");
    }

    private void placeWorkersInitial(Player player, int[][] initialPositions) {
        for (int i = 0; i < player.getWorkers().size(); i++) {
            Cell cell = grid.getCell(initialPositions[i][0], initialPositions[i][1]);
            if (cell != null && !cell.hasWorker()) {
                Worker worker = player.getWorkers().get(i);
                worker.setPosition(cell); // Set the worker's position
                cell.setWorker(worker); // Ensure the cell knows it has this worker
                System.out.println("Player " + player.getId() + " placed a worker at (" + initialPositions[i][0] + ", " + initialPositions[i][1] + ").");
            } else {
                System.out.println("Error: Initial position for worker " + (i + 1) + " of Player " + player.getId() + " is invalid or occupied.");
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

