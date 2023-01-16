//Client

import java.io.*;
import java.net.*;

public class MazeClient {
    public static void main(String[] args) throws IOException {
// Se connecte au serveur sur le port 9000
        Socket socket = new Socket("localhost", 9000);
        System.out.println("Connecté au serveur");

        // Crée les flux d'entrée et de sortie
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Envoie les mouvements au serveur
        while (true) {
            // Détecte les mouvements du joueur (par exemple en utilisant les flèches du clavier)
            // ...

            // Envoie le mouvement au serveur
            out.println(movement);

            // Met à jour la position du joueur en fonction de la réponse du serveur
            // ...
        }
    }
}
