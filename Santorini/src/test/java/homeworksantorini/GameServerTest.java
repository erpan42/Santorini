package homeworksantorini;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.CookieHandler;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.ResponseException;

public class GameServerTest {
    private GameServer gameServer;
    private GameController gameController;
    private final int port = 8080;

    @BeforeEach
    public void setUp() throws Exception {
        gameController = new GameController("player1", "player2", new Grid());
        gameServer = new GameServer(port, gameController);
    }

    @Test
    public void testSelectGodCard() throws IOException {
        String playerId = gameController.getPlayers()[0].getId();
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("playerId", List.of(playerId));
        parameters.put("godCardName", List.of("Demeter"));
        IHTTPSession session = createMockSession("/api/game/select-god-card", NanoHTTPD.Method.POST, parameters);
    
        Response response = gameServer.serve(session);
        assertEquals(NanoHTTPD.Response.Status.OK, response.getStatus());
        assertEquals("application/json", response.getMimeType());
    }

    @Test
    public void testSkipSecondBuild() throws IOException {
        // Set up the game state where the current player has a second build available
        Player currentPlayer = gameController.getCurrentPlayer();
        currentPlayer.setGodCard(new DemeterGodCard());
        currentPlayer.setSecondBuildAvailable(true);
    
        IHTTPSession session = createMockSession("/api/game/skip-second-build", NanoHTTPD.Method.POST, new HashMap<>());
        Response response = gameServer.serve(session);
        assertEquals(NanoHTTPD.Response.Status.OK, response.getStatus());
        assertEquals("application/json", response.getMimeType());
    }

    @Test
    public void testStartGame() throws IOException {
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("player1Id", List.of("Player 1"));
        parameters.put("player2Id", List.of("Player 2"));
        IHTTPSession session = createMockSession("/api/game/start", NanoHTTPD.Method.POST, parameters);

        Response response = gameServer.serve(session);
        assertEquals(NanoHTTPD.Response.Status.OK, response.getStatus());
        assertEquals("application/json", response.getMimeType());
    }

    @Test
    public void testPlaceWorker() throws IOException {
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("row", List.of("0"));
        parameters.put("col", List.of("0"));
        IHTTPSession session = createMockSession("/api/game/place-worker", NanoHTTPD.Method.POST, parameters);

        Response response = gameServer.serve(session);
        assertEquals(NanoHTTPD.Response.Status.OK, response.getStatus());
        assertEquals("application/json", response.getMimeType());
    }

    @Test
    public void testSelectWorker() throws IOException {
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("row", List.of("0"));
        parameters.put("col", List.of("0"));
        IHTTPSession session = createMockSession("/api/game/select-worker", NanoHTTPD.Method.POST, parameters);

        Response response = gameServer.serve(session);
        assertEquals(NanoHTTPD.Response.Status.OK, response.getStatus());
        assertEquals("application/json", response.getMimeType());
    }

    @Test
    public void testMoveWorker() throws IOException {
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("toRow", List.of("1"));
        parameters.put("toCol", List.of("1"));
        IHTTPSession session = createMockSession("/api/game/move-worker", NanoHTTPD.Method.POST, parameters);

        Response response = gameServer.serve(session);
        assertEquals(NanoHTTPD.Response.Status.OK, response.getStatus());
        assertEquals("application/json", response.getMimeType());
    }

    @Test
    public void testBuild() throws IOException {
        Map<String, List<String>> parameters = new HashMap<>();
        parameters.put("row", List.of("0"));
        parameters.put("col", List.of("0"));
        IHTTPSession session = createMockSession("/api/game/build", NanoHTTPD.Method.POST, parameters);

        Response response = gameServer.serve(session);
        assertEquals(NanoHTTPD.Response.Status.OK, response.getStatus());
        assertEquals("application/json", response.getMimeType());
    }

    @Test
    public void testResetGame() throws IOException {
        IHTTPSession session = createMockSession("/api/game/reset", NanoHTTPD.Method.POST, new HashMap<>());

        Response response = gameServer.serve(session);
        assertEquals(NanoHTTPD.Response.Status.OK, response.getStatus());
        assertEquals("application/json", response.getMimeType());
    }

    private IHTTPSession createMockSession(String uri, NanoHTTPD.Method method, Map<String, List<String>> parameters) {
        return new IHTTPSession() {
            @Override
            public void execute() throws IOException {
                // Not needed for testing
            }

            @Override
            public Map<String, List<String>> getParameters() {
                return parameters;
            }

            @Override
            public NanoHTTPD.Method getMethod() {
                return method;
            }

            @Override
            public String getUri() {
                return uri;
            }

            @Override
            public CookieHandler getCookies() {
                throw new UnsupportedOperationException("Unimplemented method 'getCookies'");
            }

            @Override
            public Map<String, String> getHeaders() {
                throw new UnsupportedOperationException("Unimplemented method 'getHeaders'");
            }

            @Override
            public InputStream getInputStream() {
                throw new UnsupportedOperationException("Unimplemented method 'getInputStream'");
            }

            @Override
            public Map<String, String> getParms() {
                throw new UnsupportedOperationException("Unimplemented method 'getParms'");
            }

            @Override
            public String getQueryParameterString() {
                throw new UnsupportedOperationException("Unimplemented method 'getQueryParameterString'");
            }

            @Override
            public void parseBody(Map<String, String> files) throws IOException, ResponseException {
                throw new UnsupportedOperationException("Unimplemented method 'parseBody'");
            }

            @Override
            public String getRemoteIpAddress() {
                throw new UnsupportedOperationException("Unimplemented method 'getRemoteIpAddress'");
            }

            @Override
            public String getRemoteHostName() {
                throw new UnsupportedOperationException("Unimplemented method 'getRemoteHostName'");
            }
        };
    }
}