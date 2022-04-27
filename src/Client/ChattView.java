package Client;

import Message.Message;
import Message.MessageReceiver;
import Message.MessageFactory;
import Server.Connection;
import Server.ConnectionListener;
import Server.SetNameRequest;

import javax.swing.*;
import java.awt.*;

public class ChattView extends JPanel implements MessageReceiver, ConnectionListener {

    private Console console;
    private InputField field;
    private ConnectionPanel top_panel;


    private MessageCreator creator;
    private Client client;

    public ChattView(Client client) {
        this.client = client;
        client.addConnectionListener(this);
        console = new Console();
        client.addObjectReceiver(new ReceiverAdapter(console));
        creator = new MessageCreator(new MessageFactory(""), this);
        field = new InputField(creator);

        top_panel = new ConnectionPanel(client);
        setLayout(new BorderLayout());
        add(top_panel, BorderLayout.PAGE_START);
        add(console, BorderLayout.CENTER);
        add(field, BorderLayout.PAGE_END);
    }

    @Override
    public void onMessageReceived(Message message) {
        if (message.getMessage().startsWith("|name")) {
            client.send(new SetNameRequest(message.getMessage().substring(6)));
        } else {
        client.send(message);}
    }

    @Override
    public void onConnect(Connection con) {

    }

    @Override
    public void onDisconnect(Connection con) {
        console.clear();
    }
}
