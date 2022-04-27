package Server;

import java.util.ArrayList;
import java.util.List;

public class ObjectBuffer implements ObjectReceiver, ConnectionListener {

    private List<Object> objects;

    public ObjectBuffer() {
        objects = new ArrayList<>();
    }
    @Override
    public void onConnect(Connection con) {
        for (Object o: objects) con.send(o);
        System.out.println("Buffer: " + con.toString());
    }

    @Override
    public void onDisconnect(Connection con) {}

    @Override
    public void onObjectReceived(Object o) {
        if (o != null) {
            objects.add(o);
            System.out.println("Buffered object: " + o.toString());
        }
    }
}
