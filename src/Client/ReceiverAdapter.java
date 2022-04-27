package Client;

import Message.Message;
import Message.MessageReceiver;
import Server.ObjectReceiver;

public class ReceiverAdapter implements ObjectReceiver {

    private MessageReceiver receiver;

    public ReceiverAdapter(MessageReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void onObjectReceived(Object o) {
        if (o instanceof Message) receiver.onMessageReceived((Message) o);
        System.out.println(o);
    }
}
