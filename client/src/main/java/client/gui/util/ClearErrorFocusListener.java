package client.gui.util;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ClearErrorFocusListener implements FocusListener {

    private JLabel errorLabel;

    public ClearErrorFocusListener(JLabel errorLabel){
        this.errorLabel = errorLabel;

    }

    @Override
    public void focusGained(FocusEvent e) {
        errorLabel.setText("");
    }

    @Override
    public void focusLost(FocusEvent e) {

    }
}