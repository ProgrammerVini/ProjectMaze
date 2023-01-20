import java.io.*;
import java.net.*;

public class MazeServer {
    private int port;
    private MazeGenerator mazeGenerator;

    public MazeServer(int port, MazeGenerator mazeGenerator) {
        this.port = port;
        this.mazeGenerator = mazeGenerator;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    OutputStream out = clientSocket.getOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(out);
                    oos.writeObject(mazeGenerator);
                } catch (IOException e) {
                    System.err.println("Error handling client: " + e);
                }
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e);
        }
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        MazeGenerator mazeGenerator = new MazeGenerator(20, 20);
        mazeGenerator.generate();
        MazeServer server = new MazeServer(port, mazeGenerator);
        server.start();
    }
}
