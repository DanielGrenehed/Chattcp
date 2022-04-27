package Message;

import Server.Connection;
import Server.ConnectionObjectWrapper;
import Server.Protocol;
import Server.SetNameRequest;

import java.util.Date;

public class MessageServerProtocol implements Protocol {

    private Object CompleteMessage(Message m, Connection c) {
        if (m.getSource().equals("")) m = new Message(m.getMessage(), c.getIdentifier(), m.getCreationDate());
        if (m.getCreationDate() == null) m = new Message(m.getMessage(), m.getSource(), new Date());
        return m;
    }
    @Override
    public Object processInput(Object o) {
        if (o instanceof ConnectionObjectWrapper) {
            ConnectionObjectWrapper wobj = (ConnectionObjectWrapper) o;
            if (wobj.getObject() instanceof Message) { return CompleteMessage((Message) wobj.getObject(), wobj.getConnection()); }
            else if (wobj.getObject() instanceof String) { return new Message((String) wobj.getObject(), wobj.getConnection().getIdentifier(), new Date()); }
            else if (wobj.getObject() instanceof SetNameRequest) {
                SetNameRequest request = (SetNameRequest) wobj.getObject();
                wobj.getConnection().setIdentifier(request.getName());
            }
        }
        return null;
    }
}
