import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MazeController extends JFrame {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private MazeModel mazeModel;
    private MazeView mazeView;

    public MazeController() {
    mazeModel = new MazeModel(20, 20);
    int playerX = (int)(Math.random() * mazeModel.getWidth());
    int playerY = (int)(Math.random() * mazeModel.getHeight());
    mazeView = new MazeView(mazeModel, playerX, playerY);
    
    int cellWidth = WINDOW_WIDTH / mazeModel.getWidth();
    int cellHeight = WINDOW_HEIGHT / mazeModel.getHeight();

    setTitle("Labyrinthe");
    setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);

    add(mazeView);
    addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            //handle arrow key input
	    if (e.getKeyCode() == KeyEvent.VK_UP) {
		if (!mazeModel.getCells()[playerX][playerY].hasNorthWall) {
		    mazeView.playerYPosition--;
		}
	    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
		if (!mazeModel.getCells()[playerX][playerY].hasSouthWall) {
		    mazeView.playerYPosition++;
		}
	    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		if (!mazeModel.getCells()[playerX][playerY].hasWestWall) {
		    mazeView.playerXPosition--;
		}
	    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		if (!mazeModel.getCells()[playerX][playerY].hasEastWall) {
		    mazeView.playerXPosition++;
		}
	    }
	    //repaint the view
	    repaint();
	}
	});

    setVisible(true);
    }

    public static void main(String[] args) {
	new MazeController();
    }
}

