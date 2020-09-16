public class StartClient {

    private static final String HOST = "localhost";
    private static final int PORT = 4004;

    public static void main(String[] args) {
        boolean debugModEnable = true;

        Client client = new Client(HOST, PORT, debugModEnable);
        client.authorization();
        System.out.println("Client was closed...");
    }
}