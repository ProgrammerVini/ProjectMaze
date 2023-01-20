import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MazeGUIApp extends JFrame {
    private MazeGenerator mazeGenerator;
    private int playerX;
    private int playerY;

    public MazeGUIApp() {
        mazeGenerator = new MazeGenerator(20, 20);
        mazeGenerator.generate();
        playerX = (int)(Math.random() * mazeGenerator.getWidth());
        playerY = (int)(Math.random() * mazeGenerator.getHeight());

        setTitle("Maze");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        MazePanel mazePanel = new MazePanel(mazeGenerator);
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
        new MazeGUIApp();
    }
}

class MazePanel extends JPanel {
    private MazeGenerator mazeGenerator;
    private int playerX;
    private int playerY;

    public MazePanel(MazeGenerator mazeGenerator, int playerX, int playerY) {
        this.mazeGenerator = mazeGenerator;
        this.playerX = playerX;
        this.playerY = playerY;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int cellWidth = width / mazeGenerator.getWidth();
        int cellHeight = height / mazeGenerator.getHeight();

        for (int x = 0; x < mazeGenerator.getWidth(); x++) {
            for (int y = 0; y < mazeGenerator.getHeight(); y++) {
                int x1 = x * cellWidth;
                int y1 = y * cellHeight;
                Cell cell = mazeGenerator.getCells()[x][y];

                if (cell.north) {
                    g.drawLine(x1, y1, x1 + cellWidth, y1);
                }
                if (cell.south) {
                    g.drawLine(x1, y1 + cellHeight, x1 + cellWidth, y1 + cellHeight);
                }
                if (cell.west) {
                    g.drawLine(x1, y1, x1, y1 + cellHeight);
                }
                if (cell.east) {
                    g.drawLine(x1 + cellWidth, y1, x1 + cellWidth, y1 + cellHeight);
                }
            }
        }

        g.setColor(Color.RED);
        g.fillOval(playerX * cellWidth + cellWidth / 4, playerY * cellHeight + cellHeight / 4, cellWidth / 2, cellHeight / 2);
    }
}
