package Server;

public interface ConnectionListener {

    void onConnect(Connection con);
    void onDisconnect(Connection con);
}
