package Message;

import Server.*;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MessageConnectionHandler implements ConnectionHandler, ObjectReceiver, ConnectionListener {

    private List<Connection> connections;
    private List<ObjectReceiver> object_listeners;
    private List<ConnectionListener> connection_listeners;
    private Protocol protocol;
    private Queue<Object> queue_out;

    public  MessageConnectionHandler(Protocol p) {
        protocol = p;
        queue_out = new LinkedList<>();
        connections = new ArrayList<>();
        object_listeners = new ArrayList<>();
        connection_listeners = new ArrayList<>();
    }
    @Override
    public void createConnection(Socket s) {
        Connection con = new Connection(s);
        con.setConnectionListener(this);
        con.setObjectReceiver(this);
        con.start();
        connections.add(con);
    }

    @Override
    public void disconnectAll() {
        for (Connection c: connections) c.disconnect();
    }

    @Override
    public void addConnectionListener(ConnectionListener listener) {
        connection_listeners.add(listener);
    }
    @Override
    public void removeConnectionListener(ConnectionListener listener) {
        connection_listeners.remove(listener);
    }

    @Override
    public void addObjectReceiver(ObjectReceiver or) {
        object_listeners.add(or);
    }

    @Override
    public void removeObjectReceiver(ObjectReceiver or) {
        object_listeners.remove(or);
    }

    private void queueObject(Object o) {
        queue_out.add(o);
        for (ObjectReceiver or: object_listeners) or.onObjectReceived(o);
    }
    @Override
    public void onObjectReceived(Object o) {
        queueObject(protocol.processInput(o));
    }

    public void emptyQueue() {
        while (!queue_out.isEmpty()) {
            Object o = queue_out.poll();
            if (o == null) continue;
            System.out.println(o.toString());
            for (Connection c: connections) c.send(o);
        }
    }

    @Override
    public void onConnect(Connection con) {
        System.out.println("Connected: " + con.toString());
        for (ConnectionListener listener: connection_listeners) listener.onConnect(con);
    }

    @Override
    public void onDisconnect(Connection con) {
        connections.remove(con);
        System.out.println("Disconnected: " + con.toString());
        for (ConnectionListener listener: connection_listeners) listener.onDisconnect(con);
    }
}
