package GUI;

import DB.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by danieljunker on 16.07.15.
 */
public class Login extends JFrame {
    private JPanel mainPanel;
    private JTextField tfDBServer;
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;

    private JDBC db;

    public Login() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("MusikDB - Login");
        this.setBackground(Color.darkGray);

        pack();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setVisible(true);




        btnLogin.addActionListener(e -> {
            //jdbc:postgresql://db.f4.htw-berlin.de:5432/_s0544321__beleg_junker_yarin
            db = new JDBC(tfDBServer.getText(),tfUsername.getText(),pfPassword.getPassword());
            if (db.getIsConnected()){
                JOptionPane.showMessageDialog(null, "Verbindung Erfolgreich!");
                this.dispose();
                new MainGui(db);
            }else {
                JOptionPane.showMessageDialog(null, "Verbindung konnte nicht hergestellt werden.");
                this.dispose();
                new Login();
            }
        });
    }
}
