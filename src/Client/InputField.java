package Client;

import javax.swing.*;

public class InputField extends JTextField {

    public InputField(TextInputHandler handler) {
        addActionListener(e -> {
            handler.onInput(getText());
            setText("");
        });
    }
}
