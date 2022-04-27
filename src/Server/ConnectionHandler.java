package Server;

import java.net.Socket;

public interface ConnectionHandler {

    void createConnection(Socket s);
    void disconnectAll();

    void addObjectReceiver(ObjectReceiver or);
    void removeObjectReceiver(ObjectReceiver or);
    void addConnectionListener(ConnectionListener listener);
    void removeConnectionListener(ConnectionListener listener);

}
