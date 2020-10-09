import java.util.ArrayList;
import java.util.List;

public class Sender {

    private List<MessageHandler> listOfClients;
    private final String logPrefix = "[Postman] ";

    public Sender() {
        listOfClients = new ArrayList<>();
        System.out.println(logPrefix + "Initialization done!");
    }

    public void addClient(MessageHandler client) {
        listOfClients.add(client);
        System.out.println(logPrefix + "Added new client!");
        System.out.println(logPrefix + "Clients count: " + getSize());
    }

    public void removeClient(MessageHandler client) {
        listOfClients.remove(client);
        System.out.println(logPrefix + "Removed client!");
        System.out.println(logPrefix + "Clients count: " + getSize());
    }

    public Integer getSize() {
        return listOfClients.size();
    }

    public void sendMessage(String message) {
        String logMessage = logPrefix + "Get message to send: " + message;
        System.out.println(logMessage.trim());
        for (MessageHandler client : listOfClients) {
            System.out.println(logPrefix + "Sending to: " + client.getLogin());
            client.sendMsg(message);
        }
    }
}
