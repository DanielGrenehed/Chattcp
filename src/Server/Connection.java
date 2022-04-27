package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection implements Runnable{

    Socket socket;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    Thread listener_thread;

    ObjectReceiver output;
    ConnectionListener listener;
    private String identifier;

    public Connection(Socket socket) {
        identifier = "";
        this.socket = socket;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setIdentifier(String name) {
        identifier = name;
    }

    public void setObjectReceiver(ObjectReceiver or) {
        output = or;
    }

    public void setConnectionListener(ConnectionListener cl) {
        listener = cl;
    }

    public void start() {
        listener_thread = new Thread(this);
        listener_thread.start();
    }

    @Override
    public void run() {
        if (socket.isConnected()) listener.onConnect(this);
        while (!socket.isClosed()) {
            try {
                output.onObjectReceived(new ConnectionObjectWrapper(this, ois.readObject()));
            } catch (IOException e) {
                disconnect();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        listener.onDisconnect(this);
    }


    public void send(Object o) {
        try {
            oos.writeObject(o);
        } catch (IOException e) {
            disconnect();
        }
    }

    public void disconnect() {
        try {
            oos.close();
            ois.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getIdentifier() {
        return (identifier == "") ? socket.getRemoteSocketAddress().toString() : identifier;
    }

    public boolean isConnected() {
        return (socket.isConnected()) && listener_thread.isAlive();
    }
    /*
    *   On object received, ObjectReceiver:onObjectReceived
    *
    * */



}
