/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.command;

import cn.CX.Cloud.Cloud;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class FCommand
extends JFrame {
    private JPanel contentPane;

    public FCommand() {
        this.setTitle("Command Frame Fix By Zenwix");
        this.setBounds(100, 100, 450, 300);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JTextArea textArea = new JTextArea();
        textArea.setBackground(Color.WHITE);
        textArea.setToolTipText("Fill in commands");
        textArea.setBounds(0, 39, 434, 24);
        this.contentPane.add(textArea);
        JButton btnNewButton = new JButton("Run");
        btnNewButton.setBounds(10, 74, 103, 25);
        this.contentPane.add(btnNewButton);
        btnNewButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Command system has been opened");
                try {
                    String message = textArea.getText();
                    Cloud.instance.commandManager.execute(message);
                }
                catch (Exception var5) {
                    var5.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    FCommand frame = new FCommand();
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

