import java.io.*;
import java.net.*;
import javax.swing.*;

public class MazeClientApp extends JFrame {
    private MazeGenerator mazeGenerator;
    private int playerX;
    private int playerY;

    public MazeClientApp(String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            InputStream in = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(in);
            mazeGenerator = (MazeGenerator) ois.readObject();
            playerX = (int)(Math.random() * mazeGenerator.getWidth());
            playerY = (int)(Math.random() * mazeGenerator.getHeight());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error connecting to server: " + e);
        }

        setTitle("Maze");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        MazePanel mazePanel = new MazePanel(mazeGenerator, playerX, playerY);
        add(mazePanel);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (playerX > 0 && !mazeGenerator.getCells()[playerX][playerY].west) {
                        playerX--;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (playerX < mazeGenerator.getWidth() - 1 && !mazeGenerator.getCells()[playerX][playerY].east) {
                        playerX++;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (playerY > 0 && !mazeGenerator.getCells()[playerX][playerY].north) {
                        playerY--;
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (playerY < mazeGenerator.getHeight() - 1 && !mazeGenerator.getCells()[playerX][playerY].south) {
                        playerY++;
                    }
                }

                if (playerX == 0 || playerX == mazeGenerator.getWidth() - 1 || playerY == 0 || playerY == mazeGenerator.getHeight() - 1) {
                    JOptionPane.showMessageDialog(null, "You Win!");
                }
                repaint();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        new MazeClientApp(host, port);
    }
}
