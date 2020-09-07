import java.io.*;
import java.net.Socket;

public class Client implements IClient {
    private static String authorizationTeg = "a";
    private static String loginTeg = "l";
    private static String messageTeg = "m";

    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedReader in;
    private BufferedWriter out;

    public Client(Socket clientSocket, BufferedReader reader, BufferedReader in, BufferedWriter out) {
        this.clientSocket = clientSocket;
        this.reader = reader;
        this.in = in;
        this.out = out;
    }

    @Override
    public void authorization() throws IOException {
        System.out.println("Enter your login");
        String authorization = reader.readLine();

        System.out.println(authorization);
        authorization = Wrapper.wrap(authorizationTeg, Wrapper.wrap(loginTeg, authorization));

        out.write(authorization + "\n");
        out.flush();
        String answer = in.readLine();
        answer = Parser.parse(authorizationTeg,answer);
        System.out.println(answer);
        if (answer.equalsIgnoreCase("ok")) {
            this.messaging();
        } else {
            this.authorization();
        }
    }

    @Override
    public void messaging() throws IOException {

        while (clientSocket.isConnected()) {
            System.out.println("Write message");
            String word = reader.readLine();
            if (word.equalsIgnoreCase("exit")) {
                return;
            }
            word = Wrapper.wrap(messageTeg, word);

            out.write(word + "\n");
            out.flush();

            new Thread(()->{
                String serverWord = null;
                try {
                    serverWord = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                serverWord = Parser.parse(messageTeg, serverWord);
                System.out.println(serverWord);}).start();
        }
    }
}