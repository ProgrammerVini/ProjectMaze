/*******************************************************************
La génération de labyrinthes avec l'algorithme "kill and hunt" consiste à générer un labyrinthe en utilisant deux étapes successives :

"Kill": l'algorithme parcourt le labyrinthe en construisant des murs pour diviser les espaces en petites cellules.

"Hunt": l'algorithme parcourt le labyrinthe en enlevant certains des murs construits pendant la phase "kill" de manière à ce qu'il y ait un chemin unique entre chaque cellule et son voisin.

Voici comment on  implémente cet algorithme en Java avec une interface graphique :

On crée une classe Cell qui représente une cellule du labyrinthe. Chaque cellule devrait avoir quatre booléens indiquant s'il y a un mur ou non sur chaque côté de la cellule (haut, bas, gauche, droite).

On crée une classe Maze qui contient une grille de cellules et qui possède une méthode generate() qui génère le labyrinthe en utilisant l'algorithme "kill and hunt".

On crée une classe MazeView qui hérite de JPanel et qui dessine le labyrinthe en utilisant les données de la grille de cellules.

On crée une classe MazeFrame qui hérite de JFrame et qui affiche le MazeView.

""""""

On crée une classe Cell qui représente une cellule du labyrinthe. Chaque cellule devrait avoir quatre booléens indiquant s'il y a un mur ou non sur chaque côté de la cellule (haut, bas, gauche, droite).

On crée une classe Maze qui contient une grille de cellules et qui possède une méthode generate() qui génère le labyrinthe en utilisant l'algorithme "kill and hunt".

""""""""""""""""
***********************************************************************/



//code qui montre comment dessiner un labyrinthe dans une fenêtre:

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Cell {
    boolean north;
    boolean south;
    boolean east;
    boolean west;
}

class Maze {
    Cell[][] cells;
    int width;
    int height;
    Random random;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        random = new Random();
        cells = new Cell[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public void generate() {
        // "Kill" phase
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || j == 0 || i == width - 1 || j == height - 1) {
                    continue;
                }

                int sides = 0;
                if (i % 2 == 0) {
                    sides++;
                }
                if (j % 2 == 0) {
                    sides++;
                }

                if (sides == 1) {
                    if (i % 2 == 0) {
                        cells[i][j].north = true;
                        cells[i][j - 1].south = true;
                    } else {
                        cells[i][j].west = true;
                        cells[i - 1][j].east = true;
                    }
                }
            }
        }
l'algorithme "hunt" qui est censé parcourir le labyrinthe et enlever certains des murs construits pendant la phase "kill" de manière à ce qu'il y ait un chemin unique entre chaque cellule et son voisin.
        // "Hunt" phase
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i == 0 || j == 0 || i == width - 1 || j == height - 1) {
                    continue;
                }

                int sides = 0;
                if (!cells[i][j].north) {
                    sides++;
                }
                if (!cells[i][j].west) {
                    sides++;
                }

                if (sides == 1) {
    List<Integer> neighbors = new ArrayList<>();
    if (i > 1) {
        neighbors.add(0);
    }
    if (i < width - 2) {
        neighbors.add(1);
    }
    if (j > 1) {
        neighbors.add(2);
    }
    if (j < height - 2) {
        neighbors.add(3);
    }
    int index = random.nextInt(neighbors.size());
    int direction = neighbors.get(index);

    if (direction == 0) {
        cells[i - 1][j].south = false;
        cells[i][j].north = false;
    } else if (direction == 1) {
        cells[i + 1][j].north = false;
        cells[i][j].south = false;
    } else if (direction == 2) {
        cells[i][j - 1].east = false;
        cells[i][j].west = false;
    } else if (direction == 3) {
        cells[i][j + 1].west = false;
        cells[i][j].east = false;
    }
}
