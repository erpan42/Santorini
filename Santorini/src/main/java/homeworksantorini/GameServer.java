package homeworksantorini;

import java.io.IOException;
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

    private Response createResponse(Response.Status status, String mimeType, String content) {
        Response response = newFixedLengthResponse(status, mimeType, content);
        return addCORSHeaders(response);
    }

    private Response addCORSHeaders(Response response) {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.addHeader("Access-Control-Max-Age", "600");
        return response;
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