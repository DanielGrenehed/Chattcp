package Client;

import Message.Message;
import Message.MessageReceiver;
import Server.ObjectReceiver;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.text.SimpleDateFormat;

public class Console extends JPanel implements ObjectReceiver {

    private JTextPane text_pane;
    private JScrollPane scroll_pane;

    public Console() {
        setLayout(new GridLayout(1,1));
        text_pane = new JTextPane();
        //text_pane.setEditable(false);
        scroll_pane = new JScrollPane(text_pane);
        text_pane.setRequestFocusEnabled(false);
        add(scroll_pane);
    }

    private void addText(String text, Color color) {
        AttributeSet aset = StyleContext.getDefaultStyleContext().addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        
        text_pane.setCaretPosition(text_pane.getDocument().getLength());
        text_pane.setCharacterAttributes(aset, false);
        text_pane.replaceSelection(text);
    }

    public void clear() {
        text_pane.setText("");
    }

    private void addMessage(Message m) {
        addText(new SimpleDateFormat("yyyy-MM-dd:hh.mm").format(m.getCreationDate()), Color.BLACK);
        addText(" " +m.getSource() +": ", Color.blue);
        addText(m.getMessage()+"\n", Color.BLACK);
    }
    @Override
    public void onObjectReceived(Object o) {
        if (o instanceof Message) {
            addMessage((Message)o);
        }
    }
}
