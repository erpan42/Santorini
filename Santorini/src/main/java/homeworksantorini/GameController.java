package homeworksantorini;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Player[] players;
    private Player currentPlayer;
    private Grid grid;
    private Player winner;
    private final int maxWorkers = 4;
    private int placedWorkers;
    private List<ResponseMessage> messages;

    public GameController(String player1Id, String player2Id, Grid grid) throws IOException {
        this.grid = new Grid();
        this.players = new Player[2];
        this.players[0] = new Player(player1Id);
        this.players[1] = new Player(player2Id);
        this.currentPlayer = this.players[0]; // Player 1 starts
        this.placedWorkers = 0;
        this.messages = new ArrayList<>();
    }

    public int getPlacedWorkers() {
        return placedWorkers;
    }

    /**
     * Resets the game state to the initial state.
     */
    public void resetGame() {
        grid = new Grid();
        for (Player player : players) {
            for (Worker worker : player.getWorkers()) {
                worker.setPosition(null);
            }
            player.setGodCard(null); // Reset the selected god card for each player
        }
        placedWorkers = 0;
        currentPlayer = players[0];
        winner = null;
    }

    /**
     * Skips the second build action for the current player.
     */
    public void skipSecondBuild() {
        currentPlayer.getGodCard().resetBuildState();
        changeTurn();
    }

    /**
     * Sets the winner of the game.
     *
     * @param player The player who has won the game.
     */
    private void setWinner(Player player) {
        winner = player;
    }
    
    /**
     * Sets the initial position of a worker for a player.
     *
     * @param player   The player whose worker's position is being set.
     * @param position The coordinates of the worker's position.
     */
    public void setInitialWorkerPosition(Player player, int[] position) {
        if (placedWorkers >= maxWorkers) {
            ResponseMessage responseMessage = new ResponseMessage("Error: All workers have been placed.");
            messages.add(responseMessage);
            return;
        }

        if (player != currentPlayer) {
            ResponseMessage responseMessage = new ResponseMessage("Error: It's not the current player's turn to place a worker.");
            messages.add(responseMessage);
            System.out.println("Error: It's not the current player's turn to place a worker.");
            return;
        }

        if (grid.getCell(position[0], position[1]) == null) {
            ResponseMessage responseMessage = new ResponseMessage("Error: Worker position is invalid.");
            messages.add(responseMessage);
            System.out.println("Error: Worker position is invalid.");
            return;
        } else if (grid.getCell(position[0], position[1]).hasWorker()) {
            ResponseMessage responseMessage = new ResponseMessage("Error: Worker position is already occupied.");
            messages.add(responseMessage);
            System.out.println("Error: Worker position is already occupied.");
            return;
        } else {
            Worker worker = player.getUnplacedWorker();
            if (worker != null) {
                Cell cell = grid.getCell(position[0], position[1]);
                if (cell != null) {
                    worker.setPosition(cell);
                    placedWorkers++;
                    System.out.println("Worker placed successfully at (" + position[0] + ", " + position[1] + ")");
        
                    if (placedWorkers == 2 || placedWorkers == maxWorkers) {
                        changeTurn();
                    }
                } else {
                    ResponseMessage responseMessage = new ResponseMessage("Error: Invalid cell position.");
                    messages.add(responseMessage);
                    System.out.println("Error: Invalid cell position.");
                }
            }
        }
    }
    
    public Player getWinner() {
        return winner;
    }

    /**
     * Selects a worker for the current player based on the provided coordinates.
     *
     * @param x The x-coordinate of the worker's position.
     * @param y The y-coordinate of the worker's position.
     * @return true if a worker is selected successfully, false otherwise.
     */
    public boolean selectWorkerForCurrentPlayer(int x, int y) {
        boolean selected = currentPlayer.selectWorkerByCoordinates(x, y);
        if (selected) {
            return selected;
        }
        ResponseMessage responseMessage = new ResponseMessage("Invalid worker selection. Please try again.");
        messages.add(responseMessage);
        System.out.println("Invalid worker selection. Please try again.");
        return false;
    }

    /**
     * Handles a move action for the current player's selected worker.
     *
     * @param x      The x-coordinate of the target cell.
     * @param y      The y-coordinate of the target cell.
     * @return true if the move is successful, false otherwise.
     */
    public boolean playerMove(int x, int y) {
        Cell targetCell = grid.getCell(x, y);
        if (currentPlayer.getSelectedWorker() != null && targetCell != null) {
            boolean moveSuccessful = currentPlayer.moveWorker(grid, currentPlayer.getSelectedWorker(), targetCell);
            System.out.println("Player move - Selected Worker: " + currentPlayer.getSelectedWorker());
            System.out.println("Player move - Target Cell: " + targetCell);
            if (moveSuccessful) {
                GodCard godCard = currentPlayer.getGodCard();
                if (godCard != null) {
                    godCard.afterMove(grid, currentPlayer.getSelectedWorker(), targetCell);
                }
                checkGameStatus();
                return moveSuccessful;
            }
        }
        ResponseMessage responseMessage = new ResponseMessage("Invalid move. Please try again.");
        messages.add(responseMessage);
        System.out.println("Invalid move. Please try again.");
        return false;
    }

    /**
     * Handles a build action for the current player's selected worker.
     *
     * @param x The x-coordinate of the target cell.
     * @param y The y-coordinate of the target cell.
     * @return true if the build is successful, false otherwise.
     */
    public boolean playerBuild(int x, int y) {
        System.out.println("Received build request at: (" + x + ", " + y + ")");
        Cell targetCell = grid.getCell(x, y);
        if (currentPlayer.getSelectedWorker() != null && targetCell != null && !targetCell.hasDome()) {
            // System.out.println("Selected worker: " + currentPlayer.getSelectedWorker());
            // System.out.println("Target cell: " + targetCell);
            GodCard godCard = currentPlayer.getGodCard();
            if (godCard == null || godCard.canBuild(grid, currentPlayer.getSelectedWorker(), targetCell)) {
                System.out.println("God card allows build");
                boolean buildSuccessful = currentPlayer.buildWithWorker(grid, currentPlayer.getSelectedWorker(), targetCell);
                if (buildSuccessful) {
                    System.out.println("Build successful");
                    if (godCard != null) {
                        godCard.afterBuild(grid, currentPlayer.getSelectedWorker(), targetCell);
                    }
                    checkGameStatus();
                    
                    if (godCard != null && godCard.isSecondBuildAllowed()) {
                        currentPlayer.setSecondBuildAvailable(true);
                        System.out.println("Second build available for player: " + currentPlayer.getId());
                        return buildSuccessful;
                    }
                    
                    currentPlayer.setSelectedWorker(null);
                    changeTurn();
                    return buildSuccessful;
                }
            } else {
                System.out.println("God card does not allow build");
            }
        } else {
            System.out.println("Invalid build conditions");
        }
        ResponseMessage responseMessage = new ResponseMessage("Invalid build. Please try again.");
        messages.add(responseMessage);
        System.out.println("Invalid build. Please try again.");
        return false;
    }

    /**
     * Checks the game status to determine if any player has won the game.
     * If a player has won, the game is ended by calling the endGame() method.
     */
    public void checkGameStatus() {
        System.out.println("Checking game status");
        for (Player player : players) {
            if (player.checkWinCondition()) {
                setWinner(player);
                break; // Exit the loop as the game ends when a player wins
            }
        }
    }

    /**
     * Changes the turn to the next player.
     */
    public void changeTurn() {
        // Toggle between players[0] and players[1]
        currentPlayer = (currentPlayer == players[0]) ? players[1] : players[0];

        GodCard godCard = currentPlayer.getGodCard();
        if (godCard != null) {
            godCard.resetBuildState();
        }
    }  

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    public Grid getGrid() {
        return this.grid;
    }

    public List<ResponseMessage> getMessages() {
        return messages;
    }

    public void clearMessages() {
        messages.clear();
    }

}

