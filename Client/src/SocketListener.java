import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class SocketListener extends Thread {
    private final Socket clientSocket;
    private final BufferedReader in;
    private final String messageTag;
    private final boolean debugModEnable;

    public SocketListener(Socket clientSocket, BufferedReader in, String messageTag, boolean debugModEnable) {
        this.clientSocket = clientSocket;
        this.in = in;
        this.messageTag = messageTag;
        this.debugModEnable = debugModEnable;
    }

    @Override
    public void run() {
        String serverWord;
        while (!clientSocket.isClosed()) {

            try {
                serverWord = in.readLine();
                if (serverWord != null)
                    serverWord = Wrapper.unwrap(messageTag, serverWord);
                System.out.println(serverWord);

            } catch (IOException e) {
                if (debugModEnable)
                    System.err.println(e);

            }
        }
    }
}
