package Client;

import Message.Message;
import Message.MessageFactory;
import Message.MessageReceiver;

public class MessageCreator implements TextInputHandler {

    private MessageFactory factory;
    private MessageReceiver receiver;

    public MessageCreator(MessageFactory factory, MessageReceiver receiver) {
        this.factory = factory;
        this.receiver = receiver;
    }
    @Override
    public void onInput(String str) {
        receiver.onMessageReceived(factory.createMessage(str));
    }
}
