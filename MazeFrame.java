import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
        // Traitement des évènements de touche appuyée
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Traitement des évènements de touche relâchée
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Traitement des évènements de touche tapée
    }
}

/*
On ajoute le gestionnaire d'évènements comme écouteur d'évènements de la fenêtre du labyrinthe (MazeFrame) afin qu'il puisse détecter les mouvements du joueur. On  le fait en utilisant la
méthode addKeyListener de la classe JFrame :
*/

MazeFrame frame = new MazeFrame();
frame.addKeyListener(new KeyboardHandler());


/* On met en place un labyrinthe avec une interface graphique et gérer les mouvements du joueur à l'aide d'un gestionnaire d'évènements qui implémente l'interface KeyListener :
 */


public class MazeFrame extends JFrame {
    public MazeFrame() {
        setTitle("Maze");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Maze maze = new Maze(20, 20);
        maze.generate();

        add(new MazeView(maze.cells));
        addKeyListener(new KeyboardHandler());
    }
}

class MazeView extends JPanel {
    private Cell[][] cells;
    private int playerX = x;
    private int playerY = y;

    public MazeView(Cell[][] cells) {
        this.cells = cells;
        playerX = 1;
        playerY = 1;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellSize = 30;
        int offset = 10;

        // Dessine les murs du labyrinthe
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (cells[i][j].north) {
                    g.drawLine(offset + j * cellSize, offset + i * cellSize, offset + (j + 1) * cellSize, offset + i * cellSize);
                }
                if (cells[i][j].south) {
                    g.drawLine(offset + j * cellSize, offset + (i + 1) * cellSize, offset + (j + 1) * cellSize, offset + (i + 1) * cellSize);
                }
                if (cells[i][j].west) {
                    g.drawLine(offset + j * cellSize, offset + i * cellSize, offset + j * cellSize, offset + (i + 1) * cellSize);
                }
                if (cells[i][j].east) {
                    g.drawLine(offset + (j + 1) * cellSize, offset + i * cellSize, offset + (j + 1) * cellSize, offset + (i + 1) * cellSize);
                }
		g.setColor(Color.WHITE);
		g.fillRect(offset + j * cellSize, offset + i * cellSize, cellSize, cellSize);
            }
        }

        // Dessine le joueur
        g.setColor(Color.RED);
        g.fillOval(offset + playerX * cellSize - cellSize / 2, offset + playerY * cellSize - cellSize / 2, cellSize, cellSize);
    }



	/*
Pour mettre à jour la position du joueur, on utilise les variables playerX et playerY dans le gestionnaire d'évènements qui gère les mouvements du joueur. Par exemple, dans le gestionnaire d'évènements qui implémente l'interface KeyListener, on peut  mettre à jour la position du joueur en fonction des touches du clavier appuyées par le joueur et appeler la méthode repaint pour mettre à jour l'affichage du labyrinthe :
	*/

	@Override
public void keyPressed(KeyEvent e) {
    // Met à jour la direction du joueur en fonction de la touche du clavier appuyée
    if (e.getKeyCode() == KeyEvent.VK_UP) {
        direction = "haut";
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        direction = "bas";
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        direction = "gauche";
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        direction = "droite";
    }

    // Calcule la nouvelle position du joueur en fonction de sa direction et de la distance à parcourir
    int dx = 0;
    int dy = 0;
    if (direction.equals("haut")) {
        dy = -1;
    } else if (direction.equals("bas")) {
        dy = 1;
    } else if (direction.equals("gauche")) {
        dx = -1;
    } else if (direction.equals("droite")) {
        dx = 1;
    }
    int newX = x + dx;
    int newY = y + dy;
// Vérifie s'il y a un mur à la nouvelle position
if (maze[newY][newX] == 1) {
    // Il y a un mur, on ne met pas à jour la position du joueur
    return;
}

// Met à jour la position du joueur
x = newX;
y = newY;

// Appelle la méthode repaint pour mettre à jour l'affichage du labyrinthe
repaint();
}

