package Message;

import java.util.Date;

public class MessageFactory {

    private String source;
    public MessageFactory(String source) {
        this.source = source;
    }

    public Message createMessage(String msg) {
        return new Message(msg, source, new Date());
    }
}
