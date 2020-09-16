import java.io.*;
import java.net.Socket;

public class Client implements IClient {
    private static final String authorizationTag = "a";
    private static final String loginTag = "l";
    private static final String messageTag = "m";

    private final boolean debugModEnable;
    private Socket clientSocket;
    private final BufferedReader reader;
    private BufferedReader in;
    private BufferedWriter out;

    public Client(String HOST, int PORT, boolean debugModEnable) {
        this.debugModEnable = debugModEnable;
        reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            clientSocket = new Socket(HOST, PORT);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            if (debugModEnable)
                System.err.println(e);
        }
    }

    @Override
    public void authorization() {
        try {
            System.out.println("Enter your login");
            String authorization = reader.readLine();
            authorization = Wrapper.wrap(authorizationTag, authorization);
            out.write(authorization + "\n");
            out.flush();

            String answer = in.readLine();
            answer = Wrapper.unwrap(authorizationTag, answer);
            if (answer.equalsIgnoreCase("ok")) {
                this.messaging();
            } else {
                this.authorization();
            }
        } catch (IOException e) {
            if (debugModEnable)
                System.err.println(e);
        }
    }

    @Override
    public void messaging() {

        SocketListener socketListener = new SocketListener(clientSocket,
                in,
                messageTag,
                debugModEnable);
        socketListener.setDaemon(true);
        socketListener.start();

        while (clientSocket.isConnected()) {
            try {
                System.out.println("Write message");
                String word;

                word = reader.readLine();

                if (word.equalsIgnoreCase("exit")) {
                    socketListener.interrupt();
                    return;
                }
                word = Wrapper.wrap(messageTag, word);

                out.write(word + "\n");
                out.flush();
            } catch (IOException e) {
                if (debugModEnable)
                    System.err.println(e);
            }
        }
    }
}