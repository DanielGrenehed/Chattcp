package Client;

import javax.swing.*;

public class ChattWindow extends JFrame {

    private ChattView view;

    public ChattWindow(Client client) {
        view = new ChattView(client);
        add(view);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600, 300);
        setVisible(true);
    }

    public static void main(String[] args) {
        Client c = new Client();
        new ChattWindow(c);
        c.setup("localhost", 12354);
    }
}
