package Server;

public class ConnectionObjectWrapper {

    private Connection connection;
    private Object object;

    public ConnectionObjectWrapper(Connection c, Object o) {
        connection = c;
        object = o;
    }

    public Connection getConnection() {
        return connection;
    }

    public Object getObject() {
        return object;
    }
}
