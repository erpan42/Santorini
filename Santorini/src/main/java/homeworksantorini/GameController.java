package homeworksantorini;


public class GameController {
    private Player[] players;
    private Player currentPlayer;
    private Grid grid;

    public GameController(String player1Id, String player2Id, Grid grid) {
        this.grid = grid;
        this.players = new Player[2];
        this.players[0] = new Player(player1Id, grid);
        this.players[1] = new Player(player2Id, grid);
        this.currentPlayer = this.players[0]; // Player 1 starts
    }
    
    public boolean setInitialWorkerPosition(Player player, int[] position1, int[] position2) {
        if (position1[0] == position2[0] && position1[1] == position2[1]) {
            System.out.println("Error: Worker positions cannot be the same.");
            return false;
        } else if (grid.getCell(position1[0], position1[1]) == null || grid.getCell(position2[0], position2[1]) == null) {
            System.out.println("Error: Worker positions are invalid.");
            return false;
        } else if (grid.getCell(position1[0], position1[1]).hasWorker() || grid.getCell(position2[0], position2[1]).hasWorker()) {
            System.out.println("Error: Worker positions are already occupied.");
            return false;
        } else {
            // Place the workers on the grid
            player.getWorker1().setPosition(grid.getCell(position1[0], position1[1]));
            player.getWorker2().setPosition(grid.getCell(position2[0], position2[1]));
            System.out.println("Workers placed successfully.");
            return true;
        }
    }

    public void checkGameStatus() {
        for (Player player : players) {
            if (player.checkWinCondition()) {
                endGame(player);
                break; // Exit the loop as the game ends when a player wins
            }
        }
    }

    public void changeTurn() {
        // Toggle between players[0] and players[1]
        currentPlayer = (currentPlayer == players[0]) ? players[1] : players[0];
    }  

    public void endGame(Player winner) {
        // Announce the winner
        // System.out.println("Game Over. The winner is Player: " + winner.getId());

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

