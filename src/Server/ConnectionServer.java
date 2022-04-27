package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class ConnectionServer implements Runnable {

    ServerSocket socket;
    ConnectionHandler handler;
    ConnectionListener listener;
    Thread server_thread;

    public ConnectionServer(int port, ConnectionHandler handler) {
        this.handler = handler;
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setConnectionListener(ConnectionListener listener) {
        this.listener = listener;
    }

    public void start() {
        server_thread = new Thread(this);
        server_thread.start();
    }

    @Override
    public void run() {
        if (!socket.isClosed() && listener != null) listener.onConnect(null);
        while (!socket.isClosed()) {
            try {
                handler.createConnection(socket.accept());
            } catch (IOException e) {
                //throw new RuntimeException(e);
            }
        }
        if (listener != null) listener.onDisconnect(null);
    }

    public void stop() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void collapse() {

        handler.disconnectAll();
        stop();
    }

    public boolean isRunning() {
        return (!socket.isClosed()) && server_thread.isAlive();
    }
}
