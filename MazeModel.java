import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.input.KeyEvent;
    
class Direction {
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;
}

class Cell {
    boolean hasNorthWall;
    boolean hasSouthWall;
    boolean hasEastWall;
    boolean hasWestWall;
    boolean isVisited;

    public Cell() {
        hasNorthWall = true;
        hasSouthWall = true;
        hasEastWall = true;
        hasWestWall = true;
        isVisited = false;
    }
}



public class MazeModel {
    private int width;
    private int height;
    private Cell[][] cells;
    private int playerXPosition;
    private int playerYPosition;
    private int startXPosition;
    private int startYPosition;
    private int endXPosition;
    private int endYPosition;
    private Random random;

    public MazeModel(int width, int height) {
	this.width = width;
	this.height = height;
	random = new Random();
	generate();
    }

    private void generate() {
	cells = new Cell[width][height];
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		cells[x][y] = new Cell();
	    }
	}

	// "Kill" phase
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
		    continue;
		}

		if (x % 2 == 0 && y % 2 == 0) {
		    int sides = 0;
		    if (x > 0 && !cells[x - 1][y].isVisited) {
			sides++;
		    }
		    if (x < width - 1 && !cells[x + 1][y].isVisited) {
			sides++;
		    }
		    if (y > 0 && !cells[x][y - 1].isVisited) {
			sides++;
		    }
		    if (y < height - 1 && !cells[x][y + 1].isVisited) {
			sides++;
		    }


		    if (sides > 0) {
			List<Integer> neighbors = new ArrayList<>();
			if (x > 0 && !cells[x - 1][y].isVisited) {
			    neighbors.add(Direction.NORTH);
			}
			if (x < width - 1 && !cells[x + 1][y].isVisited) {
			    neighbors.add(Direction.SOUTH);
			}
			if (y > 0 && !cells[x][y - 1].isVisited) {
			    neighbors.add(Direction.WEST);
			}
			if (y < height - 1 && !cells[x][y + 1].isVisited) {
			    neighbors.add(Direction.EAST);
			}

			int index = random.nextInt(neighbors.size());
			int direction = neighbors.get(index);

			if (direction == Direction.NORTH) {
			    cells[x - 1][y].hasSouthWall = false;
			    cells[x][y].hasNorthWall = false;
			    cells[x - 1][y].isVisited = true;
			    cells[x][y].isVisited = true;
			} else if (direction == Direction.SOUTH) {
			    cells[x + 1][y].hasNorthWall = false;
			    cells[x][y].hasSouthWall = false;
			    cells[x + 1][y].isVisited = true;
			    cells[x][y].isVisited = true;
			} else if (direction == Direction.WEST) {
			    cells[x][y - 1].hasEastWall = false;
			    cells[x][y].hasWestWall = false;
			    cells[x][y - 1].isVisited = true;
			    cells[x][y].isVisited = true;
			} else if (direction == Direction.EAST) {
			    cells[x][y + 1].hasWestWall = false;
			    cells[x][y].hasEastWall = false;
			    cells[x][y + 1].isVisited = true;
			    cells[x][y].isVisited = true;
			}
		    }
		}
	    }
	}
	// "Hunt" phase
	for (int x = 1; x < width - 1; x++) {
	    for (int y = 1; y < height - 1; y++) {
		if (!cells[x][y].isVisited) {
		    int startX = x;
		    int startY = y;
		    while (cells[startX][startY].isVisited) {
			startX = random.nextInt(width);
			startY = random.nextInt(height);
		    }
		    int currentX = startX;
		    int currentY = startY;
		    while (!cells[currentX][currentY].isVisited) {
			cells[currentX][currentY].isVisited = true;
			List<Integer> neighbors = new ArrayList<>();
			if (currentX > 0 && cells[currentX - 1][currentY].isVisited) {
			    neighbors.add(Direction.NORTH);
			}
			if (currentX < width - 1 && cells[currentX + 1][currentY].isVisited) {
			    neighbors.add(Direction.SOUTH);
			}
			if (currentY > 0 && cells[currentX][currentY - 1].isVisited) {
			    neighbors.add(Direction.WEST);
			}
			if (currentY < height - 1 && cells[currentX][currentY + 1].isVisited) {
			    neighbors.add(Direction.EAST);
			}
			if (neighbors.size() > 0) {
			    int direction = neighbors.get(random.nextInt(neighbors.size()));
			    if (direction == Direction.NORTH) {
				cells[currentX][currentY].hasNorthWall = false;
				cells[currentX - 1][currentY].hasSouthWall = false;
				currentX--;
			    } else if (direction == Direction.SOUTH) {
				cells[currentX][currentY].hasSouthWall = false;
				cells[currentX + 1][currentY].hasNorthWall = false;
				currentX++;
			    } else if (direction == Direction.WEST) {
				cells[currentX][currentY].hasWestWall = false;
				cells[currentX][currentY - 1].hasEastWall = false;
				currentY--;
			    } else if (direction == Direction.EAST) {
				cells[currentX][currentY].hasEastWall = false;
				cells[currentX][currentY + 1].hasWestWall = false;
				currentY++;
			    }
			}
		    }
		}
	    }
	}
	/*Il faut ajouter les positions de départ et d'arrivée du joueur :*/
	startXPosition = random.nextInt(width);
	startYPosition = random.nextInt(height);

	while (cells[startXPosition][startYPosition].hasNorthWall || cells[startXPosition][startYPosition].hasWestWall) {
	    startXPosition = random.nextInt(width);
	    startYPosition = random.nextInt(height);
	}

	endXPosition = random.nextInt(width);
	endYPosition = random.nextInt(height);

	while (cells[endXPosition][endYPosition].hasSouthWall || cells[endXPosition][endYPosition].hasEastWall) {
	    endXPosition = random.nextInt(width);
	    endYPosition = random.nextInt(height);
	}

	playerXPosition = startXPosition;
	playerYPosition = startYPosition;
    }
}

public void handleKeyInput(KeyEvent event) {
    switch (event.getCode()) {
    case UP:
	if (!cells[playerX][playerY].north) {
	    playerY--;
	}
	break;
    case DOWN:
	if (!cells[playerX][playerY].south) {
	    playerY++;
	}
	break;
    case LEFT:
	if (!cells[playerX][playerY].west) {
	    playerX--;
	}
	break;
    case RIGHT:
	if (!cells[playerX][playerY].east) {
	    playerX++;
	}
	break;
    }
    if(playerX == width-2 && playerY == height-2){
	System.out.println("Congratulations! You have reached the exit!");
    }



    public int getPlayerX() {
	return playerX;
    }

    public int getPlayerY() {
	return playerY;
    }


    public int getWidth() {
	return width;
    }

    public int getHeight() {
	return height;
    }

    public Cell[][] getCells() {
	return cells;
    }

    public boolean hasWall(int x, int y, Direction direction) {
	switch (direction) {
	case NORTH:
	    return cells[x][y].north;
	case SOUTH:
	    return cells[x][y].south;
	case EAST:
	    return cells[x][y].east;
	case WEST:
	    return cells[x][y].west;
	}
	return false;
    }

}

    
