package homeworksantorini;

import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class Main {
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        try {
            GameController gameController = new GameController("Player 1", "Player 2", new Grid());
            GameServer server = new GameServer(SERVER_PORT, gameController);
            server.start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            System.out.println("Server started on port " + SERVER_PORT);
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
