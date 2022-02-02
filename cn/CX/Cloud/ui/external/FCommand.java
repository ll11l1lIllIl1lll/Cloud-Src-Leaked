/*
 * Decompiled with CFR 0.152.
 */
package cn.CX.Cloud.ui.external;

import cn.CX.Cloud.Cloud;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class FCommand
extends JFrame {
    private JPanel contentPane;
    public String[] data = new String[]{"Cloud"};
    public static FCommand frame = new FCommand();

    public FCommand() {
        this.setBounds(100, 100, 180, 150);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(this.contentPane);
        this.contentPane.setLayout(null);
        final JList<String> list = new JList<String>(this.data);
        list.setSelectionMode(0);
        list.setBounds(10, 10, 140, 40);
        this.contentPane.add(list);
        JButton Loadbt = new JButton("Load");
        Loadbt.setBounds(10, 60, 140, 25);
        this.contentPane.add(Loadbt);
        Loadbt.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                Cloud.Ranks1 = FCommand.this.data[list.getSelectedIndex()];
                Cloud Class2 = new Cloud();
                Class2.NativesLoader(true);
                frame.setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){

            @Override
            public void run() {
                try {
                    frame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

