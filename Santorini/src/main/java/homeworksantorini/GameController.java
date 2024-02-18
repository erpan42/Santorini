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
        this.currentPlayer = this.players[0]; // Start with Player 1
    }

    public void startGame() {
        // Initialize the board, if not already done in the constructor
        this.grid = new Grid();
        
        // Player 1 places both workers first
        System.out.println("Player 1, place your workers:");
        for (int i = 0; i < 2; i++) {
            placeWorkerInitialPosition(players[0], i); // Place each of Player 1's workers
        }

        // Player 2 places both workers next
        System.out.println("Player 2, place your workers:");
        for (int i = 0; i < 2; i++) {
            placeWorkerInitialPosition(players[1], i); // Place each of Player 2's workers
        }

        // Set the starting player
        this.currentPlayer = players[0]; // Player 1 starts the game by default
        System.out.println("All workers placed. Player " + currentPlayer.getId() + " starts the game.");
    }

    private void placeWorkerInitialPosition(Player player, int workerIndex) {
        // Placeholder for player's choice of initial position for their worker
        // This would be replaced by actual player input
        boolean isPlaced = false;
        while (isPlaced == false) {
            // Prompt the player to select a position for their worker
            // For now, I'll just use example positions (0, 0) and (1, 1) for Player 1
            // and (3, 3) and (4, 4) for Player 2
            final int[][] positionsForPlayer1 = {{0, 0}, {1, 1}}; // Example positions for Player 1's workers
            final int[][] positionsForPlayer2 = {{3, 3}, {4, 4}}; // Example positions for Player 2's workers

            int x = player.getId().equals(players[0].getId()) ? positionsForPlayer1[workerIndex][0] : positionsForPlayer2[workerIndex][0];
            int y = player.getId().equals(players[0].getId()) ? positionsForPlayer1[workerIndex][1] : positionsForPlayer2[workerIndex][1];
            
            Cell cell = this.grid.getCell(x, y);
            Worker worker = player.getWorkers().get(workerIndex);

            if (cell != null && !cell.hasWorker()) {
                worker.setPosition(cell); // Set the worker's position
                cell.setWorker(worker); // Ensure the cell knows it has this worker
                System.out.println("Player: " + player.getId() + " placed a worker at (" + x + ", " + y + ").");
                isPlaced = true;
            } else {
                System.out.println("Invalid position selected by Player: " + player.getId() + " for worker " + (workerIndex + 1) + ".");
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

    private void startBuild(Worker worker, Cell targetBuildCell) {
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
    
    private boolean checkWinCondition(Worker worker) {
        return worker.getPosition().getTowerLevel() == WIN_CONDITION_TOWER_LEVEL;
    }

    private void changeTurn() {
        // Toggle between players[0] and players[1]
        currentPlayer = (currentPlayer == players[0]) ? players[1] : players[0];
        System.out.println("It's now Player: " + currentPlayer.getId() + "'s turn.");
    }  

    private void endGame(Player winner) {
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

}

