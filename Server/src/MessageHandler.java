import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MessageHandler implements Runnable {

    // reading/writing streams for socket
    //

    private static BufferedReader in;
    private static BufferedWriter out;

    // Regular expressions for processing client's requests
    //

    private static final String authRegex = "<a>.*</a>";

    Socket clientSocket;
    String clientLogin;

    // Constructor, where we will catch client's connection, and use it later
    // inside thread's main function
    //

    public MessageHandler(Socket socket) {
        this.clientSocket = socket;
        clientLogin = "anonymous"; // If client not authenticated - his name is "anonymous"
    }

    // Thread's main function
    //

    @Override
    public void run() {
        try {
            // Initialising buffered streams for client
            //

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            // This loop oriented only to reading data from client and sending back simple messages
            //

            while (true) {
                String word = in.readLine();

                // Check: if client want to authenticate on server - it's data should match authRegex
                // regular expression
                //

                if (word.matches(authRegex)) {
                    clientLogin = word.replace("<a>", "").replace("</a>", "");
                    out.write("Your authorization : " + clientLogin + "\n");
                } else if (word.equalsIgnoreCase("exit")) {

                    // This part uses only if client sent "exit" word. In this case we should
                    // disconnect client
                    //

                    out.write("Client left\n");
                    out.flush();

                    in.close();
                    out.close();
                    clientSocket.close();
                    break;
                } else {
                    // In this case (when data is just simple message),
                    // we should just send back data sent by client
                    //

                    out.write("<<< " + clientLogin + " >>>" + ": " + word + "\n");
                }
                out.flush();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}