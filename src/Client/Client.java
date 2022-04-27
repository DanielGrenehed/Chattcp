package Client;

import Server.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements ConnectionListener, ObjectReceiver {

    private Connection connection;
    private Socket socket;
    private String hostname;
    private List<ObjectReceiver> receivers;
    private List<ConnectionListener> connection_listeners;
    private Server localhost_server;

    public Client() {
        receivers = new ArrayList<>();
        connection_listeners = new ArrayList<>();
        hostname = "localhost";
        socket = null;
    }

    public void setup(String hostname, int port) {
        try {
            this.hostname = hostname;
            startLocalServer(port);
            socket = new Socket(hostname, port);
            connection = new Connection(socket);
            connection.setConnectionListener(this);
            connection.setObjectReceiver(this);
            connection.start();
        } catch (IOException e) {
            onDisconnect(null);
            throw new RuntimeException(e);
        }
    }

    private void startLocalServer(int port) {
        if (hostname.equals("localhost") || hostname.equals("127.0.0.1")) {
            try {
                localhost_server = new Server();
                localhost_server.start(port);
            } catch (Exception e) {
                localhost_server = null;
            }
        }
    }

    private void stopLocalServer() {
        if (localhost_server != null) {

            localhost_server.stop();
            localhost_server = null;
        }
    }

    public void disconnect() {
        connection.disconnect();
        stopLocalServer();
    }

    public void send(Object o) {
        connection.send(o);
    }

    public void addConnectionListener(ConnectionListener cl) {
        connection_listeners.add(cl);
    }

    public void removeConnectionListener(ConnectionListener cl) {
        connection_listeners.remove(cl);
    }

    public void addObjectReceiver(ObjectReceiver or) {
        receivers.add(or);
    }

    public void removeObjectReceiver(ObjectReceiver or) {
        receivers.remove(or);
    }
    @Override
    public void onConnect(Connection con) {
        for (ConnectionListener cl: connection_listeners) cl.onConnect(con);
    }

    @Override
    public void onDisconnect(Connection con) {
        for (ConnectionListener cl: connection_listeners) cl.onDisconnect(con);
    }

    @Override
    public void onObjectReceived(Object o) {
        if (o instanceof ConnectionObjectWrapper) {
            for (ObjectReceiver or: receivers) or.onObjectReceived(((ConnectionObjectWrapper) o).getObject());
        }
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return socket != null ? socket.getPort() : 12354;
    }
}
