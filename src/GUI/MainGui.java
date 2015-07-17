package GUI;

import DB.JDBC;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * Created by danieljunker on 14.07.15.
 */
public class MainGui extends JFrame{
    private JPanel panel;
    private JButton neueBandErstellenButton;
    private JButton suchenButton;
    private JButton programmBeendenButton;
    private JPanel pnBandDetails;
    private AbstractBorder brdrLeft ;
    private AbstractBorder brdrRight;

    private  JDBC db;
    public MainGui(JDBC db) {
        this.db = db;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setContentPane(panel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("MusikDB");
        this.setBackground(Color.darkGray);

        pack();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setVisible(true);

        neueBandErstellenButton.addActionListener(e -> {
            this.dispose();
            new CRU(db);


        });
        suchenButton.addActionListener(e -> {
            this.dispose();
            new ViewDSGUI(db);
        });
    }


}
