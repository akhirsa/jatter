import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MessageHandler implements Runnable {

    // reading/writing streams for socket
    //
    private Sender postman;
    private BufferedReader in;
    private BufferedWriter out;

    // Regular expressions for processing client's requests
    //

    private static final String authRegex = "<a>.*</a>";

    Socket clientSocket;
    String clientLogin;

    // Constructor, where we will catch client's connection, and use it later
    // inside thread's main function
    //

    public MessageHandler(Socket socket, Sender postman) {
        this.clientSocket = socket;
        this.postman = postman;
        clientLogin = "anonymous"; // If client not authenticated - his name is "anonymous"
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void clientLeft(){
        System.out.println("Client left\n");
        postman.removeClient(this);
        try{
        in.close();
        out.close();
        clientSocket.close();
        } catch (IOException e){
            System.out.println(clientLogin + " got on balls :(\n");
        }
    }
    // Thread's main function
    //

    @Override
    public void run() {
        try {
            // Initialising buffered streams for client
            //
            // This loop oriented only to reading data from client and sending back simple messages
            //

            String word;
            while ((word = in.readLine()) != null) {

                // Check: if client want to authenticate on server - it's data should match authRegex
                // regular expression
                //
                if (word == null || word.isEmpty()) continue;

                if (word.equalsIgnoreCase("exit")) {
                    break;
                }

                if (word.matches(authRegex)) {
                    clientLogin = word.replace("<a>", "").replace("</a>", "");
                    out.write("ok\n");
                    out.flush();
                    continue;
                }
                // In this case (when data is just simple message),
                // we should just send back data sent by client
                //
                postman.sendMessage("<<< " + clientLogin + " >>>" + ": " + word + "\n");
                out.flush();
            }
            clientLeft();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public String getLogin() {
        return clientLogin;
    }

    public void sendMsg(String msg) {
        try {
            out.write(msg);
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}