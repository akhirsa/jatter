import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    private static Socket clientSocket; // socket for incoming connection
    private static ServerSocket server; // server socket
    private static final int PORT = 4004; // port for server


    public static void main(String[] args) {
        try {
            server = new ServerSocket(PORT);
            System.out.println("The server is running !");

            // Waiting for incoming connection
            //

            while (true) {

                // Accept incoming connection as client socket and
                // pass it to separate thread
                //

                clientSocket = server.accept();
                if (clientSocket != null) {
                    MessageHandler messageHandler = new MessageHandler(clientSocket);
                    Thread thread = new Thread(messageHandler);
                    thread.start();
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}