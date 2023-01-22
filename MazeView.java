import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MazeView extends JPanel {
    public static final int CELL_SIZE = 20;
    private MazeModel mazeModel;
    private int playerXPosition;
    private int playerYPosition;

    public MazeView(MazeModel mazeModel, int playerX, int playerY) {
        this.mazeModel = mazeModel;
        this.playerXPosition = playerX;
        this.playerYPosition = playerY;
    }

    public void setPlayerXPosition(int playerX) {
        this.playerXPosition = playerX;
    }

    public void setPlayerYPosition(int playerY) {
        this.playerYPosition = playerY;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int cellWidth = width / mazeModel.getWidth();
        int cellHeight = height / mazeModel.getHeight();

        for (int x = 0; x < mazeModel.getWidth(); x++) {
            for (int y = 0; y < mazeModel.getHeight(); y++) {
                int x1 = x * cellWidth;
                int y1 = y * cellHeight;
                Cell cell= mazeModel.getCells()[x][y];
		
		if (x == mazeModel.getStartX() && y == mazeModel.getStartY()) {
                    g.setColor(Color.GREEN);
                    g.fillRect(x1, y1, cellWidth, cellHeight);
                } else if (x == mazeModel.getEndXPosition() && y == mazeModel.getEndYPosition()) {
                    g.setColor(Color.RED);
		    g.fillRect(x1, y1, cellWidth, cellHeight);
		} else {
		    if (cell.hasNorthWall) {
			g.drawLine(x1, y1, x1 + cellWidth, y1);
		    }
		    if (cell.hasSouthWall) {
			g.drawLine(x1, y1 + cellHeight, x1 + cellWidth, y1 + cellHeight);
		    }
		    if (cell.hasWestWall) {
			g.drawLine(x1, y1, x1, y1 + cellHeight);
		    }
		    if (cell.hasEastWall) {
			g.drawLine(x1 + cellWidth, y1, x1 + cellWidth, y1 + cellHeight);
		    }
		}
	    }
	}
	g.setColor(Color.BLUE);
	g.fillOval(playerXPosition * cellWidth + cellWidth / 4, playerYPosition * cellHeight + cellHeight / 4, cellWidth / 2, cellHeight / 2);
    }

}


