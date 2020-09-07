import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class StartClient {

    private static final String HOST = "localhost";
    private static final int PORT = 4004;

    public static void main(String[] args) {
        try {
            try (Socket clientSocket = new Socket(HOST, PORT);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            ) {
                Client client = new Client(clientSocket, reader, in, out);
                client.messaging();
            } finally {
                System.out.println("Client was closed...");
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}