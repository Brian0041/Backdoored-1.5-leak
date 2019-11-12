package com.backdoored.gui;

import java.awt.AWTEvent;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.JPanel;
import java.util.function.Consumer;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class TextFieldNative extends JFrame implements ActionListener
{
    private JTextField textField;
    private JFrame frame;
    private JButton button;
    private JLabel label;
    private Consumer<String> callback;
    private String btnText;
    
    public TextFieldNative(final String titleTxt, final String labelTxt, final String buttonTxt, final Consumer<String> callback) {
        super();
        this.callback = callback;
        this.btnText = buttonTxt;
        this.frame = new JFrame(titleTxt);
        this.label = new JLabel(labelTxt);
        (this.button = new JButton(buttonTxt)).addActionListener(this);
        this.textField = new JTextField(16);
        final JPanel p = new JPanel();
        p.add(this.textField);
        p.add(this.button);
        p.add(this.label);
        this.frame.add(p);
        this.frame.setSize(300, 300);
        this.frame.setVisible(true);
        this.frame.setAlwaysOnTop(true);
    }
    
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getActionCommand().equals(this.btnText)) {
            this.callback.accept(this.textField.getText());
            this.frame.dispatchEvent(new WindowEvent(this.frame, 201));
        }
    }
}
