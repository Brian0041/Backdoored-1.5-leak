package com.backdoored.hacks.ui.swing;

import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import java.awt.Component;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.util.Iterator;
import com.backdoored.hacks.BaseHack;
import com.backdoored.event.BackdooredChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import javax.swing.JScrollBar;
import com.backdoored.hacks.ui.Swing;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import java.awt.Dimension;
import net.minecraftforge.common.MinecraftForge;
import com.backdoored.Globals;
import com.backdoored.commands.Command;
import java.awt.Container;
import java.awt.Toolkit;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JFrame;

public class SwingImpl
{
    private JFrame frame;
    private JTextArea chat;
    private JTextField input;
    private JTextArea modulelist;
    private JPanel mainPanel;
    private JSplitPane splitPane;
    private JPanel consolePanel;
    private JPanel moduleListPanel;
    private JScrollPane consoleScrollPane;
    
    public SwingImpl() {
        super();
        this.$$$setupUI$$$();
        this.frame = new JFrame("SwingImpl");
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.frame.setSize(screenSize.width / 2, screenSize.height / 2);
        this.frame.setContentPane(this.mainPanel);
        this.frame.pack();
        this.frame.setVisible(true);
        this.input.addActionListener(e -> {
            if (this.input.getText().startsWith(Command.commandPrefix)) {
                Command.processCommand(this.input.getText());
            }
            else {
                Globals.mc.field_71439_g.func_71165_d(this.input.getText());
            }
            this.input.setText("");
            return;
        });
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onChat(final ClientChatReceivedEvent event) {
        if (Swing.chat.getValBoolean()) {
            this.chat.append(event.getMessage().func_150260_c() + "\n");
        }
        final JScrollBar sb = this.consoleScrollPane.getVerticalScrollBar();
        sb.setValue(sb.getMaximum());
    }
    
    @SubscribeEvent
    public void onBackdooredChat(final BackdooredChatEvent event) {
        this.chat.append(event.txt.func_150260_c() + "\n");
        final JScrollBar sb = this.consoleScrollPane.getVerticalScrollBar();
        sb.setValue(sb.getMaximum());
    }
    
    public void onUpdate() {
        final StringBuilder finalTxt = new StringBuilder();
        for (final BaseHack hack : Globals.hacks) {
            if (hack.getEnabled()) {
                finalTxt.append(hack.name).append("\n");
            }
        }
        this.modulelist.setText(finalTxt.toString());
    }
    
    public void close() {
        this.frame.setVisible(false);
        this.frame.dispose();
    }
    
    private void $$$setupUI$$$() {
        (this.mainPanel = new JPanel()).setLayout(new BorderLayout(0, 0));
        this.mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        this.splitPane = new JSplitPane();
        this.mainPanel.add(this.splitPane, "Center");
        (this.consolePanel = new JPanel()).setLayout(new BorderLayout(0, 0));
        this.splitPane.setLeftComponent(this.consolePanel);
        this.input = new JTextField();
        this.consolePanel.add(this.input, "South");
        (this.consoleScrollPane = new JScrollPane()).setHorizontalScrollBarPolicy(31);
        this.consoleScrollPane.setVerticalScrollBarPolicy(20);
        this.consolePanel.add(this.consoleScrollPane, "Center");
        (this.chat = new JTextArea()).setLineWrap(true);
        this.chat.setText("");
        this.consoleScrollPane.setViewportView(this.chat);
        (this.moduleListPanel = new JPanel()).setLayout(new BorderLayout(0, 0));
        this.splitPane.setRightComponent(this.moduleListPanel);
        this.modulelist = new JTextArea();
        this.moduleListPanel.add(this.modulelist, "Center");
    }
    
    public JComponent $$$getRootComponent$$$() {
        return this.mainPanel;
    }
    
    private /* synthetic */ void lambda$new$0(final ActionEvent e) {
        if (this.input.getText().startsWith(Command.commandPrefix)) {
            Command.processCommand(this.input.getText());
        }
        else {
            Globals.mc.field_71439_g.func_71165_d(this.input.getText());
        }
        this.input.setText("");
    }
}
