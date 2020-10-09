import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    //private static Socket clientSocket; // socket for incoming connection
    private static ServerSocket server; // server socket
    private static final int PORT = 4004; // port for server
    private static final String logPrefix = "[Server] ";
    private static MessageHandler client;

    public static void main(String[] args) {
        try {
            server = new ServerSocket(PORT);
            System.out.println(logPrefix + "The server is running !");
            Sender postman = new Sender();

            // Waiting for incoming connection
            //

            while (true) {

                // Accept incoming connection as client socket and
                // pass it to separate thread
                //

                Socket clientSocket = server.accept();
                if (clientSocket != null) {
                    System.out.println(logPrefix + "Client connected!");
                    client = new MessageHandler(clientSocket, postman);
                    new Thread(client).start();
                    postman.addClient(client);
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}