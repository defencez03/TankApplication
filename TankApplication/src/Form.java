import javax.swing.*;
import java.awt.event.*;

import Server.Server;
import Client.Client;


public class Form extends JFrame {
    private JPanel contentPane;
    private JButton buttonClient;
    private JButton buttonServer;

    public Form() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonClient);

        buttonClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onClient();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        buttonServer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onServer();
            }
        });
    }

    private void onServer() {
        new Server();
    }

    private void onClient() throws InterruptedException {
        new Client();
    }

    public static void main(String[] args) {
        Form dialog = new Form();
        dialog.pack();
        dialog.setVisible(true);
    }
}
