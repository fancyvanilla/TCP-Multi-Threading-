import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        int maxClients = 10;
        int port = 8080;
        ExecutorService executorService = Executors.newFixedThreadPool(maxClients);
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());

            String chaine = (String) input.readObject();
            System.out.println("Received from client: " + chaine);
            Thread.sleep(30000);

            StringBuilder reversed = new StringBuilder(chaine);
            reversed = reversed.reverse();
            String result = reversed.toString();
            output.writeObject(result);
            output.flush();
            clientSocket.close();

        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }
}
