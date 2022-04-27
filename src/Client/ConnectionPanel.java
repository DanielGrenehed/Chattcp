package Client;

import Server.Connection;
import Server.ConnectionListener;

import javax.swing.*;
import java.awt.*;

public class ConnectionPanel extends JPanel implements ConnectionListener {

    // host, port, status, connect/disconnect

    private JTextField hostname_field;
    private JTextField port_field;
    private JPanel connected_status_panel;
    private JButton connect_button;

    private boolean connected;

    public ConnectionPanel(Client client) {
        setLayout(new GridLayout(1,2));

        client.addConnectionListener(this);
        hostname_field = new JTextField(client.getHostname());

        port_field = new JTextField(String.valueOf(client.getPort()));
        connected_status_panel = new JPanel();
        connect_button = new JButton("Connect");
        connect_button.addActionListener(e -> {
            if (connected) {
                client.disconnect();
            } else {
                client.setup(hostname_field.getText(), Integer.parseInt(port_field.getText()));
            }
        });

        add(hostname_field);
        add(createSubPanel());
    }

    private JPanel createSubPanel() {
        JPanel sub = new JPanel();
        sub.setLayout(new GridLayout(1, 3));
        sub.add(port_field);
        sub.add(connected_status_panel);
        sub.add(connect_button);
        return sub;
    }

    private void updateGUI() {
        if (connected) {
            connected_status_panel.setBackground(Color.GREEN);
            connect_button.setText("Disconnect");
        } else {
            connected_status_panel.setBackground(Color.RED);
            connect_button.setText("Connect");
        }
    }
    @Override
    public void onConnect(Connection con) {
        connected = true;
        updateGUI();
    }

    @Override
    public void onDisconnect(Connection con) {
        connected = false;
        updateGUI();
    }
}
