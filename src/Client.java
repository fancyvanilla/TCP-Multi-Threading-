import java.io.*;
import java.net.*;

public class Client {
    public static void main(String argv[]) {
        int port = 8080;
        String host="localhost";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter writer = new PrintWriter(System.out, true);

            writer.println("Enter a string:");
            String chaine = reader.readLine();

            InetAddress adr = InetAddress.getByName(host);
            Socket socket = new Socket(adr, port);

            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            output.writeObject(chaine);

            String reversed = (String) input.readObject();
            System.out.println("Received from server: " + reversed);

            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }
}
