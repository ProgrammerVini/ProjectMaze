/****
code qui met en place un serveur et des clients qui communiquent entre eux en utilisant les sockets en Java. Vous devrez l'adapter à vos besoins et à votre architecture de jeu de labyrinthe.
****/






//Serveur :


import java.io.*;
import java.net.*;

public class MazeServer {
    public static void main(String[] args) throws IOException {
        // Écoute sur le port 9000
        ServerSocket serverSocket = new ServerSocket(9000);
        System.out.println("Serveur en écoute sur le port 9000");

        // Attends les connexions des clients
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connecté : " + clientSocket.getInetAddress());

            // Crée un thread dédié pour gérer la communication avec ce client
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            clientHandler.start();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            // Crée les flux d'entrée et de sortie
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Lit les mouvements envoyés par le client et met à jour sa position
            while (true) {
                String input = in.readLine();
                if (input == null || input.equals("QUIT")) {
                    // Le client a fermé la connexion
                    break;
                }

                // Met à jour la position du client
                // ...

                // Envoie la nouvelle position du client à tous les autres clients
                // ...
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
