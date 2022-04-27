package Client;

import Message.Message;
import Message.MessageReceiver;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.text.SimpleDateFormat;

public class Console extends JPanel implements MessageReceiver {

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
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        int len = text_pane.getDocument().getLength();
        text_pane.setCaretPosition(len);
        text_pane.setCharacterAttributes(aset, false);
        text_pane.replaceSelection(text);
    }

    @Override
    public void onMessageReceived(Message message) {
        // colored time received and source
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd:hh.mm");

        String dateString = format.format( message.getCreationDate()  );
        addText(dateString, Color.BLACK);
        addText(" " +message.getSource() +": ", Color.blue);
        addText(message.getMessage()+"\n", Color.BLACK);
    }

    public void clear() {
        text_pane.setText("");
    }
}
