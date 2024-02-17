package homeworksantorini;

public class GameController {
    private Player[] players;
    private Player currentPlayer;
    private Grid grid;

    private static final int WIN_CONDITION_TOWER_LEVEL = 3;

    public GameController(String player1Id, String player2Id) {
        this.grid = new Grid();
        this.players = new Player[2];
        this.players[0] = new Player(player1Id);
        this.players[1] = new Player(player2Id);
        this.currentPlayer = this.players[0]; // Player 1 starts
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
        final int[][] positionsForPlayer1 = {{0, 0}, {1, 1}}; // Example positions for Player 1's workers
        final int[][] positionsForPlayer2 = {{3, 3}, {4, 4}}; // Example positions for Player 2's workers

        int x = player.getId().equals(players[0].getId()) ? positionsForPlayer1[workerIndex][0] : positionsForPlayer2[workerIndex][0];
        int y = player.getId().equals(players[0].getId()) ? positionsForPlayer1[workerIndex][1] : positionsForPlayer2[workerIndex][1];
        
        Cell cell = this.grid.getCell(x, y);
        Worker worker = player.getWorkers().get(workerIndex);

        if (cell != null && !cell.hasWorker()) {
            worker.setPosition(cell); // Set the worker's position
            cell.setWorker(worker); // Ensure the cell knows it has this worker
            System.out.println("Player " + player.getId() + " placed a worker at (" + x + ", " + y + ").");
        } else {
            System.out.println("Invalid position selected by Player " + player.getId() + " for worker " + (workerIndex + 1) + ".");
            // In a real application, you might loop or prompt again for a valid position
        }
    }


    public boolean checkWinCondition() {
        for (Player player : players) {
            for (Worker worker : player.getWorkers()) {
                if (worker.getPosition().getTowerLevel() == WIN_CONDITION_TOWER_LEVEL && !worker.getPosition().hasDome()) {
                    return true; // Win condition met
                }
            }
        }
        return false; // No winner yet
    }

    public void changeTurn() {
        currentPlayer = (currentPlayer == players[0]) ? players[1] : players[0];
        System.out.println("It's now Player " + currentPlayer.getId() + "'s turn.");
    }    

    public void endGame(Player winner) {
        // Announce the winner
        System.out.println("Game Over. The winner is Player " + winner.getId());

        // Clean up the game state if necessary
        this.grid = null; // Reset the grid
        for (Player player : players) {
            for (Worker worker : player.getWorkers()) {
                worker.setPosition(null); // Reset workers' positions
            }
        }
    }

    // Additional methods for gameplay, such as moving workers and building towers, can be added here.
}

