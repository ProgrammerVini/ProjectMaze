import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MazeController extends JFrame {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private int playerX;
    private int playerY;
    private MazeModel mazeModel;
    private MazeView mazeView;

    public MazeController() {
        mazeModel = new MazeModel(20, 20);
        playerX = mazeModel.getStartX(); //(int)(Math.random() * mazeModel.getWidth());
        playerY = mazeModel.getStartY();//(int)(Math.random() * mazeModel.getHeight());
        mazeView = new MazeView(mazeModel, playerX, playerY);
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
			if (playerY > 0 && !mazeModel.getCells()[playerX][playerY].hasNorthWall) {
			    playerY--;
			    mazeView.setPlayerYPosition(playerY);
			}
		    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (playerY < mazeModel.getHeight()-1 && !mazeModel.getCells()[playerX][playerY].hasSouthWall) {
			    playerY++;
			    mazeView.setPlayerYPosition(playerY);
			}
		    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX > 0 && !mazeModel.getCells()[playerX][playerY].hasWestWall) {
			    playerX--;
			    mazeView.setPlayerXPosition(playerX);
			}
		    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX < mazeModel.getWidth()-1 && !mazeModel.getCells()[playerX][playerY].hasEastWall) {
			    playerX++;
			    mazeView.setPlayerXPosition(playerX);
			}
		    }
		    if (playerX == mazeModel.getEndXPosition() && playerY == mazeModel.getEndYPosition()) {
			JOptionPane.showMessageDialog(MazeController.this, "Vous avez gagné !");
		    } else {
			//repaint the view
			revalidate();
			repaint();
		    }
		}
	    });

	setVisible(true);
    }

    public static void main(String[] args) {
	new MazeController();
    }
}

