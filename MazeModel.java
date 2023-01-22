import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.PriorityQueue;
import java.util.Collections;
    
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

    public boolean hasWall(int direction) {
        if (direction == Direction.NORTH) {
            return hasNorthWall;
        } else if (direction == Direction.EAST) {
            return hasEastWall;
        } else if (direction == Direction.SOUTH) {
            return hasSouthWall;
        } else if (direction == Direction.WEST) {
            return hasWestWall;
        } else {
            return false;
        }
    }
}



public class MazeModel {
    private int width;
    private int height;
    private Cell[][] cells;
    private int playerX;
    private int playerY;
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

	startXPosition = random.nextInt(width);
	startYPosition = random.nextInt(height);
	endXPosition = random.nextInt(width);
	endYPosition = random.nextInt(height);

	playerXPosition = startXPosition;
	playerYPosition = startYPosition;

	depthFirstSearch(playerXPosition, playerYPosition);
    }

    private void depthFirstSearch(int x, int y) {
	cells[x][y].isVisited = true;

	List<Integer> directions = new ArrayList<>();
	directions.add(Direction.NORTH);
	directions.add(Direction.EAST);
	directions.add(Direction.SOUTH);
	directions.add(Direction.WEST);
	Collections.shuffle(directions);

	for (int direction : directions) {
	    int newX = x;
	    int newY = y;

	    if (direction == Direction.NORTH) {
		newY--;
	    } else if (direction == Direction.EAST) {
		newX++;
	    } else if (direction == Direction.SOUTH) {
		newY++;
	    } else if (direction == Direction.WEST) {
		newX--;
	    }

	    if (newX >= 0 && newX < width && newY >= 0 && newY < height && !cells[newX][newY].isVisited) {
		if (direction == Direction.NORTH) {
		    cells[x][y].hasNorthWall = false;
		    cells[newX][newY].hasSouthWall = false;
		} else if (direction == Direction.EAST) {
		    cells[x][y].hasEastWall = false;
		    cells[newX][newY].hasWestWall = false;
		} else if (direction == Direction.SOUTH) {
		    cells[x][y].hasSouthWall = false;
		    cells[newX][newY].hasNorthWall = false;
		} else if (direction == Direction.WEST) {
		    cells[x][y].hasWestWall = false;
		    cells[newX][newY].hasEastWall = false;
		}
		depthFirstSearch(newX, newY);
	    }
	}
    }



    /*

    
    private void generate() {
	cells = new Cell[width][height];
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		cells[x][y] = new Cell();
	    }
	}
	// "Hunt" phase
	int currentX = 1 + random.nextInt(width - 2);
	int currentY = 1 + random.nextInt(height - 2);
	cells[currentX][currentY].isVisited = true;
	startXPosition = currentX;
	startYPosition = currentY;
	while(true){
	    List<Integer> directions = new ArrayList<>();
	    if(currentX > 1 && !cells[currentX-2][currentY].isVisited)
		directions.add(Direction.WEST);
	    if(currentX < width-2 && !cells[currentX+2][currentY].isVisited)
		directions.add(Direction.EAST);
	    if(currentY > 1 && !cells[currentX][currentY-2].isVisited)
		directions.add(Direction.NORTH);
	    if(currentY < height-2 && !cells[currentX][currentY+2].isVisited)
		directions.add(Direction.SOUTH);
	    if(directions.size() == 0) break;
	    int dir = directions.get(random.nextInt(directions.size()));
	    if(dir == Direction.NORTH) {
		cells[currentX][currentY-1].isVisited = true;
		cells[currentX][currentY-1].hasNorthWall = false;
		cells[currentX][currentY-2].isVisited = true;
		currentY -= 2;
	    }
	    if(dir == Direction.SOUTH) {
		cells[currentX][currentY+1].isVisited = true;
		cells[currentX][currentY+1].hasSouthWall = false;
		cells[currentX][currentY+2].isVisited = true;
		currentY += 2;
	    }
	    if(dir == Direction.WEST) {
		cells[currentX-1][currentY].isVisited = true;
		cells[currentX-1][currentY].hasWestWall = false;
		cells[currentX-2][currentY].isVisited = true;
		currentX -= 2;
	    }
	    if(dir == Direction.EAST) {
		cells[currentX+1][currentY].isVisited = true;
		cells[currentX+1][currentY].hasEastWall = false;
		cells[currentX+2][currentY].isVisited = true;
		currentX += 2;
	    }
	}
	// "Kill" phase
	for (int x = 1; x < width - 1; x++) {
	    for (int y = 1; y < height - 1; y++) {
		if (!cells[x][y].isVisited) {
		    currentX = x;
		    currentY = y;
		    while (true) {
			List<Integer> directions = new ArrayList<>();
			if (currentX > 1 && cells[currentX - 1][currentY].isVisited)
			    directions.add(Direction.WEST);
			if (currentX < width - 2 && cells[currentX + 1][currentY].isVisited)
			    directions.add(Direction.EAST);
			if (currentY > 1 && cells[currentX][currentY - 1].isVisited)
			    directions.add(Direction.NORTH);
			if (currentY < height - 2 && cells[currentX][currentY + 1].isVisited)
			    directions.add(Direction.SOUTH);
			if (directions.size() == 0) break;
			int dir = directions.get(random.nextInt(directions.size()));
			if (dir == Direction.NORTH) {
			    cells[currentX][currentY].hasNorthWall = false;
			    currentY--;
			}
			if (dir == Direction.SOUTH) {
			    cells[currentX][currentY].hasSouthWall = false;
			    currentY++;
			}
			if (dir == Direction.WEST) {
			    cells[currentX][currentY].hasWestWall = false;
			    currentX--;
			}
			if (dir == Direction.EAST) {
			    cells[currentX][currentY].hasEastWall = false;
			    currentX++;
			}
			cells[currentX][currentY].isVisited = true;
		    }
		}
	    }
	}
	// Randomly remove wall on border to create entry and exit
	int entryWall = random.nextInt(4);
	if (entryWall == 0) {
	    cells[1][random.nextInt(height - 2) + 1].hasWestWall = false;
	    startXPosition = 0;
	    startYPosition = random.nextInt(height - 2) + 1;
	} else if (entryWall == 1) {
	    cells[random.nextInt(width - 2) + 1][1].hasNorthWall = false;
	    startXPosition = random.nextInt(width - 2) + 1;
	    startYPosition = 0;
	} else if (entryWall == 2) {
	    cells[width - 2][random.nextInt(height - 2) + 1].hasEastWall = false;
	    startXPosition = width - 1;
	    startYPosition = random.nextInt(height - 2) + 1;
	} else {
	    cells[random.nextInt(width - 2) + 1][height - 2].hasSouthWall = false;
	    startXPosition = random.nextInt(width - 2) + 1;
	    startYPosition = height - 1;
	}

        int exitWall = random.nextInt(4);
	while (exitWall == entryWall) {
	    exitWall = random.nextInt(4);
	}
	if (exitWall == 0) {
	    cells[width - 2][random.nextInt(height - 2) + 1].hasEastWall = false;
	    endXPosition = width - 1;
	    endYPosition = random.nextInt(height - 2) + 1;
	} else if (exitWall == 1) {
	    cells[random.nextInt(width - 2) + 1][height - 2].hasSouthWall = false;
	    endXPosition = random.nextInt(width - 2) + 1;
	    endYPosition = height - 1;
	} else if (exitWall == 2) {
	    cells[1][random.nextInt(height - 2) + 1].hasWestWall = false;
	    endXPosition = 0;
	    endYPosition = random.nextInt(height - 2) + 1;
	} else if (exitWall == 3) {
	    cells[random.nextInt(width - 2) + 1][1].hasNorthWall = false;
	    endXPosition = random.nextInt(width - 2) + 1;
	    endYPosition = 0;
	}
	/*Il faut ajouter les positions de départ et d'arrivée du joueur :*/
	/*
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
	*/
    //    }



    /*

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
    /*
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
    
    */
    
    /*
    private void generate() {
	cells = new Cell[width][height];
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		cells[x][y] = new Cell();
	    }
	}

	// Choisir une case de départ au hasard
	int x = random.nextInt(width);
	int y = random.nextInt(height);
	startXPosition = x;
	startYPosition = y;
	cells[x][y].isVisited = true;

	// Boucle principale pour la génération du labyrinthe
	while (true) {
	    // Chercher une case non visitée adjacente
	    ArrayList<Integer> directions = new ArrayList<Integer>();
	    if (y > 0 && !cells[x][y-1].isVisited) {
		directions.add(Direction.NORTH);
	    }
	    if (x < width-1 && !cells[x+1][y].isVisited) {
		directions.add(Direction.EAST);
	    }
	    if (y < height-1 && !cells[x][y+1].isVisited) {
		directions.add(Direction.SOUTH);
	    }
	    if (x > 0 && !cells[x-1][y].isVisited) {
		directions.add(Direction.WEST);
	    }

	    if (directions.size() > 0) {
		int direction = directions.get(random.nextInt(directions.size()));
		if (direction == Direction.NORTH) {
		    cells[x][y].hasNorthWall = false;
		    cells[x][y-1].hasSouthWall = false;
		    y--;
		} else if (direction == Direction.EAST) {
		    cells[x][y].hasEastWall = false;
		    cells[x+1][y].hasWestWall = false;
		    x++;
		} else if (direction == Direction.SOUTH) {
		    cells[x][y].hasSouthWall = false;
		    cells[x][y+1].hasNorthWall = false;
		    y++;
		} else if (direction == Direction.WEST) {
		    cells[x][y].hasWestWall = false;
		    cells[x-1][y].hasEastWall = false;
		    x--;
		}
		cells[x][y].isVisited = true;
	    } else {
		// Choisir une case visitée au hasard
		ArrayList<Integer> unvisitedX = new ArrayList<Integer>();
		ArrayList<Integer> unvisitedY = new ArrayList<Integer>();
		for (int i = 0; i < width; i++) {
		    for (int j = 0; j < height; j++) {
			if (cells[i][j].isVisited) {
			    unvisitedX.add(i);
			    unvisitedY.add(j);
			}
		    }
		}
		if(!unvisitedX.isEmpty()) {
		    int randomIndex = random.nextInt(unvisitedX.size());
		    int randomUnvisitedX = unvisitedX.get(randomIndex);
		    int randomUnvisitedY = unvisitedY.get(randomIndex);
		    // Marquer la case comme visitée
		    cells[randomUnvisitedX][randomUnvisitedY].isVisited = true;
		    // Continuer à parcourir à partir de cette case jusqu'à ce qu'il n'y ait plus de cases non visitées
		    int randomVisitedX = randomUnvisitedX;
		    int randomVisitedY = randomUnvisitedY;
		    while (true) {
			ArrayList<Integer> possibleDirections = new ArrayList<Integer>();
			if (randomVisitedX > 0 && !cells[randomVisitedX - 1][randomVisitedY].isVisited) {
			    possibleDirections.add(Direction.WEST);
			}
			if (randomVisitedX < width - 1 && !cells[randomVisitedX + 1][randomVisitedY].isVisited) {
			    possibleDirections.add(Direction.EAST);
			}
			if (randomVisitedY > 0 && !cells[randomVisitedX][randomVisitedY - 1].isVisited) {
			    possibleDirections.add(Direction.NORTH);
			}
			if (randomVisitedY < height - 1 && !cells[randomVisitedX][randomVisitedY + 1].isVisited) {
			    possibleDirections.add(Direction.SOUTH);
			}
			if (!possibleDirections.isEmpty()) {
			    int randomDirection = possibleDirections.get(random.nextInt(possibleDirections.size()));
			    if (randomDirection == Direction.NORTH) {
				cells[randomVisitedX][randomVisitedY].hasNorthWall = false;
				cells[randomVisitedX][randomVisitedY - 1].hasSouthWall = false;
				randomVisitedY--;
			    } else if (randomDirection == Direction.EAST) {
				cells[randomVisitedX][randomVisitedY].hasEastWall = false;
				cells[randomVisitedX + 1][randomVisitedY].hasWestWall = false;
				randomVisitedX++;
			    } else if (randomDirection == Direction.SOUTH) {
				cells[randomVisitedX][randomVisitedY].hasSouthWall = false;
				cells[randomVisitedX][randomVisitedY + 1].hasNorthWall = false;
				randomVisitedY++;
			    } else if (randomDirection == Direction.WEST) {
				cells[randomVisitedX][randomVisitedY].hasWestWall = false;
				cells[randomVisitedX - 1][randomVisitedY].hasEastWall = false;
				randomVisitedX--;
			    }
			    cells[randomVisitedX][randomVisitedY].isVisited = true;
			    unvisitedX.remove(Integer.valueOf(randomVisitedX));
			    unvisitedY.remove(Integer.valueOf(randomVisitedY));
			} else {
			    if (!unvisitedX.isEmpty()) {
				//int randomIndex = random.nextInt(unvisitedX.size());
				randomVisitedX = unvisitedX.get(randomIndex);
				randomVisitedY = unvisitedY.get(randomIndex);
				cells[randomVisitedX][randomVisitedY].isVisited = true;
				unvisitedX.remove(Integer.valueOf(randomVisitedX));
				unvisitedY.remove(Integer.valueOf(randomVisitedY));
			    } else {
				break;
			    }
			}
		    }
		}
	    }
	

	    /*Il faut ajouter les positions de départ et d'arrivée du joueur :*/
    /*
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

*/
    
    public void handleKeyInput(KeyEvent event) {
	switch (event.getKeyCode()) {
	case KeyEvent.VK_UP:
	    if (!cells[playerX][playerY].hasNorthWall) {
		playerY--;
	    }
	    break;
	case KeyEvent.VK_DOWN:
	    if (!cells[playerX][playerY].hasSouthWall) {
		playerY++;
	    }
	    break;
	case KeyEvent.VK_LEFT:
	    if (!cells[playerX][playerY].hasWestWall) {
		playerX--;
	    }
	    break;
	case KeyEvent.VK_RIGHT:
	    if (!cells[playerX][playerY].hasEastWall) {
		playerX++;
	    }
	    break;
	}
	if(playerX == width-2 && playerY == height-2){
	    System.out.println("Congratulations! You have reached the exit!");
	}
    }

    public boolean isPlayerAtExit(int x, int y) {
        return x == endXPosition && y == endYPosition;
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

    public void setEndXPosition(int endX) {
        this.endXPosition = endXPosition;
    }

    public void setEndYPosition(int endY) {
        this.endYPosition = endYPosition;
    }

    public int getEndXPosition() {
        return endXPosition;
    }

    public int getEndYPosition() {
        return endYPosition;
    }
    
    public int getStartX() {
        return startXPosition;
    }
    
    public int getStartY() {
        return startYPosition;
    }

    public boolean isConnected() {
        boolean[][] visited = new boolean[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                visited[x][y] = false;
            }
        }
        dfs(visited, startXPosition, startYPosition);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!visited[x][y]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void dfs(boolean[][] visited, int x, int y) {
        visited[x][y] = true;
        if (!cells[x][y].hasNorthWall && y > 0 && !visited[x][y-1]) {
            dfs(visited, x, y-1);
        }
        if (!cells[x][y].hasEastWall && x < width-1 && !visited[x+1][y]) {
            dfs(visited, x+1, y);
        }
        if (!cells[x][y].hasSouthWall && y < height-1 && !visited[x][y+1]) {
            dfs(visited, x, y+1);
        }
        if (!cells[x][y].hasWestWall && x > 0 && !visited[x-1][y]) {
            dfs(visited, x-1, y);
        }
    }


    public boolean isPathExist() {
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};
        PriorityQueue<Node> queue = new PriorityQueue<>();
        boolean[][] visited = new boolean[width][height];
        int[][] distances = new int[width][height];
        for (int i = 0; i < width; i++) {
	    for (int j = 0; j < height; j++) {
		visited[i][j] = false;
		distances[i][j] = Integer.MAX_VALUE;
	    }
	}

	queue.add(new Node(startXPosition, startYPosition, 0));
	distances[startXPosition][startYPosition] = 0;
	while (!queue.isEmpty()) {
	    Node current = queue.poll();
	    if (current.x == endXPosition && current.y == endYPosition) {
		return true;
	    }
	    if (visited[current.x][current.y]) {
		continue;
	    }
	    visited[current.x][current.y] = true;
	    for (int i = 0; i < 4; i++) {
		int newX = current.x + dx[i];
		int newY = current.y + dy[i];
		if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
		    if (!cells[current.x][current.y].hasWall(i) && distances[newX][newY] > current.cost + 1) {
			distances[newX][newY] = current.cost + 1;
			queue.add(new Node(newX, newY, distances[newX][newY]));
		    }
		}
	    }
	}
	return false;
    }
    
    private class Node implements Comparable<Node> {
	int x, y, cost;

	Node(int x, int y, int cost) {
	    this.x = x;
	    this.y = y;
	    this.cost = cost;
	}
	public int compareTo(Node other) {
	    return this.cost - other.cost;
	}
    }
    
    public static void main(String[] args) {
	MazeModel maze = new MazeModel(20, 20);

	// Vérifier si le labyrinthe est connecté
	if (maze.isConnected()) {
	    System.out.println("Le labyrinthe est connecté");
	} else {
	    System.out.println("Le labyrinthe n'est pas connecté");
	}

	// Vérifier l'existence d'un chemin depuis le début jusqu'à la fin
	if (maze.isPathExist()) {
	    System.out.println("Il existe un chemin depuis le début jusqu'à la fin");
	} else {
	    System.out.println("Il n'existe pas de chemin depuis le début jusqu'à la fin");
	}

    }
    
}















    
