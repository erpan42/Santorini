package homeworksantorini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fi.iki.elonen.NanoHTTPD;

public class GameServer extends NanoHTTPD {

    private GameController gameController;
    private Gson gson;

    public GameServer(int port, GameController gameController) {
        super(port);
        this.gameController = gameController;
        this.gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        // Create Gson instance with Expose annotation
        this.gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create();
    }

    /**
     * Creates a response with the specified status, MIME type, and content.
     *
     * @param status   The status of the response.
     * @param mimeType The MIME type of the response.
     * @param content  The content of the response.
     * @return The response with the specified status, MIME type, and content.
     */
    private Response createResponse(Response.Status status, String mimeType, String content) {
        Response response = newFixedLengthResponse(status, mimeType, content);
        return addCORSHeaders(response);
    }

    /**
     * Adds the CORS headers to the response.
     *
     * @param response The response to add the CORS headers to.
     * @return The response with the CORS headers added.
     */
    private Response addCORSHeaders(Response response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "600");
        return response;
    }

    private GodCard createGodCard(String godCardName) {
        switch (godCardName) {
            case "Demeter":
                return new DemeterGodCard();
            case "Hephaestus":
                return new HephaestusGodCard();
            case "Minotaur":
                //return new MinotaurGodCard();
            case "Pan":
                //return new PanGodCard();
            default:
                return new NoGodCard();
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Map<String, List<String>> parameters = session.getParameters();
        System.out.println("Received request: " + uri);
        Method method = session.getMethod();
        
        if (method == Method.OPTIONS) {
            // Handle preflight request
            Response response = newFixedLengthResponse(Response.Status.OK, MIME_PLAINTEXT, "");
            response = addCORSHeaders(response);
            return response;
        }
    
        // Handle the game requests
        if (uri.equals("/api/game/start") && session.getMethod().equals(Method.POST)) {
            String player1Id = parameters.containsKey("player1Id") ? parameters.get("player1Id").get(0) : "Player 1";
            String player2Id = parameters.containsKey("player2Id") ? parameters.get("player2Id").get(0) : "Player 2";
            try {
                gameController = new GameController(player1Id, player2Id, new Grid());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (uri.equals("/api/game/place-worker") && session.getMethod().equals(Method.POST)) {
            String rowParam = parameters.getOrDefault("row", List.of("")).get(0);
            String colParam = parameters.getOrDefault("col", List.of("")).get(0);
            int row = !rowParam.isEmpty() ? Integer.parseInt(rowParam) : -1;
            int col = !colParam.isEmpty() ? Integer.parseInt(colParam) : -1;
            System.out.println("Received place-worker request with row: " + row + ", col: " + col);
        
            if (row >= 0 && col >= 0) {
                if (gameController.getCurrentPlayer() != null) {
                    gameController.setInitialWorkerPosition(gameController.getCurrentPlayer(), new int[]{row, col});
                } else {
                    System.out.println("Error: Current player is null.");
                    return createResponse(Response.Status.BAD_REQUEST, "application/json", "{\"error\": \"Current player is null\"}");
                }
            } else {
                System.out.println("Error: Invalid row or column.");
                return createResponse(Response.Status.BAD_REQUEST, "application/json", "{\"error\": \"Invalid row or column\"}");
            }
        } else if (uri.equals("/api/game/select-worker") && session.getMethod().equals(Method.POST)) {
            int row = parameters.containsKey("row") ? Integer.parseInt(parameters.get("row").get(0)) : 0;
            int col = parameters.containsKey("col") ? Integer.parseInt(parameters.get("col").get(0)) : 0;
            gameController.selectWorkerForCurrentPlayer(row, col);
        } else if (uri.equals("/api/game/move-worker") && session.getMethod().equals(Method.POST)) {
            int toRow = parameters.containsKey("toRow") ? Integer.parseInt(parameters.get("toRow").get(0)) : 0;
            int toCol = parameters.containsKey("toCol") ? Integer.parseInt(parameters.get("toCol").get(0)) : 0;
            gameController.playerMove(toRow, toCol);
            gameController.checkGameStatus();
        } else if (uri.equals("/api/game/build") && session.getMethod().equals(Method.POST)) {
            int row = parameters.containsKey("row") ? Integer.parseInt(parameters.get("row").get(0)) : 0;
            int col = parameters.containsKey("col") ? Integer.parseInt(parameters.get("col").get(0)) : 0;
            gameController.playerBuild(row, col);
        } else if (uri.equals("/api/game/reset") && session.getMethod().equals(Method.POST)) {
            gameController.resetGame();
        } else if (uri.equals("/api/game/skip-second-build") && session.getMethod().equals(Method.POST)) {
            gameController.skipSecondBuild();
        } else if (uri.equals("/api/game/select-god-card") && session.getMethod().equals(Method.POST)) {
            try {
              String playerId = session.getParameters().get("playerId").get(0);
              String godCardName = session.getParameters().get("godCardName").get(0);
          
              System.out.println("Received god card selection: " + playerId + ", " + godCardName);
          
              Player player = Arrays.stream(gameController.getPlayers())
                .filter(p -> p.getId().equals(playerId))
                .findFirst()
                .orElse(null);
          
              if (player != null) {
                GodCard godCard = createGodCard(godCardName);
                player.setGodCard(godCard);
                System.out.println("Set god card for player: " + player.getId() + ", God card: " + godCard.getClass().getSimpleName());
              } else {
                System.out.println("Player not found: " + playerId);
                return createResponse(Response.Status.BAD_REQUEST, "application/json", "{\"error\": \"Player not found\"}");
              }
            } catch (Exception e) {
              e.printStackTrace();
              return createResponse(Response.Status.BAD_REQUEST, "application/json", "{\"error\": \"Invalid request\"}");
            }
        }
    
        // Prepare the response data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("currentPlayer", gameController.getCurrentPlayer().toSerializableFormat());
        responseData.put("players", Arrays.stream(gameController.getPlayers())
                .map(player -> player.toSerializableFormat())
                .collect(Collectors.toList()));
        responseData.put("grid", gameController.getGrid().toSerializableFormat());
        responseData.put("winner", gameController.getWinner() != null ? gameController.getWinner().toSerializableFormat() : null);
        responseData.put("numPlacedWorkers", gameController.getPlacedWorkers());
        responseData.put("messages", gameController.getMessages().stream()
            .map(ResponseMessage::getMessage)
            .collect(Collectors.toList()));
        gameController.clearMessages();


        // Return the response with the game state
        System.out.println("Sending response: " + responseData);
        return createResponse(Response.Status.OK, "application/json", gson.toJson(responseData));
    }
}