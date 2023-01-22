
public class LabyrinthModel {
    private int[][] grid; // 2D array to store the walls and paths of the labyrinth
    private int startRow; // starting row of the player
    private int startCol; // starting column of the player

    public LabyrinthModel() {
        // Initialize the grid and generate the labyrinth using the Hunt and Kill method
        ...
    }
    // Getters and setters for the grid, startRow, and startCol
    ...
}


public class LabyrinthModel {
    private int width;
    private int height;
    private Cell[][] cells;
    private Random random;

    public LabyrinthModel(int width, int height) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell();
            }
        }
        random = new Random();
    }

    public void generate() {
        // "Kill" phase
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                    continue;
                }

                if (x % 2 == 0 && y % 2 == 0) {
                    int sides = 0;
                    if (x > 0 && !cells[x - 1][y].visited) {
                        sides++;
                    }
                    if (x < width - 1 && !cells[x + 1][y].visited) {
                        sides++;
                    }
                    if (y > 0 && !cells[x][y - 1].visited) {
                        sides++;
                    }
                    if (y < height - 1 && !cells[x][y + 1].visited) {
                        sides++;
                    }

                    if (sides > 0) {
                        List<Integer> neighbors = new ArrayList<>();
                        if (x > 0 && !cells[x - 1][y].visited) {
                            neighbors.add(0);
                        }
                        if (x < width - 1 && !cells[x + 1][y].visited) {
                            neighbors.add(1);
                        }
                        if (y > 0 && !cells[x][y - 1].visited) {
                            neighbors.add(2);
                        }
                        if (y < height - 1 && !cells[x][y + 1].visited) {
                            neighbors.add(3);
                        }

                        int index = random.nextInt(neighbors.size());
                        int direction = neighbors.get(index);

                        if (direction == 0) {
                            cells[x - 1][y].south = false;
			    cells[x][y].north = false;
                            cells[x - 1][y].visited = true;
                            cells[x][y].visited = true;
                        } else if (direction == 1) {
                            cells[x + 1][y].north = false;
                            cells[x][y].south = false;
                            cells[x + 1][y].visited = true;
                            cells[x][y].visited = true;
                        } else if (direction == 2) {
                            cells[x][y - 1].east = false;
                            cells[x][y].west = false;
                            cells[x][y - 1].visited = true;
                            cells[x][y].visited = true;
                        } else if (direction == 3) {
                            cells[x][y + 1].west = false;
                            cells[x][y].east = false;
                            cells[x][y + 1].visited = true;
                            cells[x][y].visited = true;
                        }
                    }
                }
            }
        }
        
        // "Hunt" phase
        boolean foundUnvisited = true;
        while (foundUnvisited) {
            foundUnvisited = false;
            for (int x = 1; x < width - 1; x++) {
                for (int y = 1; y < height - 1; y++) {
                    if (!cells[x][y].visited) {
                        foundUnvisited = true;
                        List<Wall> neighbors = new ArrayList<>();
                        if (x > 1 && cells[x - 1][y].visited) {
                            neighbors.add(new Wall(x, y, true));
                        }
                        if (x < width - 2 && cells[x + 1][y].visited) {
                            neighbors.add(new Wall(x + 1, y, true));
                        }
                        if (y > 1 && cells[x][y - 1].visited) {
                            neighbors.add(new Wall(x, y, false));
                        }
                        if (y < height - 2 && cells[x][y + 1].visited) {
                            neighbors.add(new Wall(x, y + 1, false));
                        }

                        if (neighbors.size() > 0) {
                            int index = random.nextInt(neighbors.size());
                            Wall wall = neighbors.get(index);
                            if (wall.isVertical) {
                                cells[wall.x][wall.y].west = false;
                                cells[wall.x - 1][wall.y].east = false;
                            } else {
                                cells[wall.x][wall.y].north = false;
                                cells[wall.x][wall.y - 1].south = false;
                            }
                            cells[x][y].visited = true;
                        }
                    }
                }
            }
        }
    }
    
    public void printMaze(){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (cells[x][y].north) {
		    System.out.print("+---");
                } else {
                    System.out.print("+   ");
                }
            }
            System.out.println("+");

            for (int x = 0; x < width; x++) {
                if (cells[x][y].west) {
                    System.out.print("|   ");
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println("|");
        }

        for (int x = 0; x < width; x++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }
}
