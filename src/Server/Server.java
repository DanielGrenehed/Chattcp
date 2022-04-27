package Server;

import Message.MessageConnectionHandler;
import Message.MessageServerProtocol;

public class Server implements Runnable {

    private MessageConnectionHandler connection_handler;
    private ConnectionServer server;
    private Protocol protocol;
    private Thread handler_thread;

    public Server() {
        protocol = new MessageServerProtocol();
        connection_handler = new MessageConnectionHandler(protocol);

        ObjectBuffer buffer = new ObjectBuffer();
        connection_handler.addObjectReceiver(buffer);
        connection_handler.addConnectionListener(buffer);
    }

    public boolean isRunning() {
        return server.isRunning();
    }

    public void start(int port) {
        server = new ConnectionServer(port, connection_handler);
        server.start();
        handler_thread = new Thread(this);
        handler_thread.start();
    }

    public void stop() {
        server.collapse();
    }

    @Override
    public void run() {
        while (server.isRunning()) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            connection_handler.emptyQueue();
        }
    }

    public void setServerConnectionListener(ConnectionListener listener) {
        server.setConnectionListener(listener);
    }


    public static void main(String[] args) {
        for (String s: args) System.out.println(s);
        int port = 12354;
        if (args.length > 0) port = Integer.parseInt(args[0]);

        Server m = new Server();
        m.start(port);
    }
}
