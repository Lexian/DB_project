package GUI;

import DB.DBQueries;
import DB.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;


/**
 * Created by danieljunker on 15.07.15.
 */
public class CreateGUI extends JFrame {
    private JPanel formPanel;
    private JTabbedPane tpCrud;
    private JTextField tfBandName;
    private JButton btnSaveBand;
    private JButton btnCancelBand;
    private JButton btnSaveAlbum;
    private JButton btnCancelAlbumButton;
    private JButton btnSaveSong;
    private JButton btnCancelSong;
    private JButton btnSaveTour;
    private JButton btnCancelTour;
    private JTextField tfSongName;
    private JComboBox cbAlbumName;
    private JTextField tfAlbumName;
    private JTextField tfReleaseDate;
    private JTextField tfTourName;
    private JTextField tfTourBegin;
    private JTextField tfTourEnd;
    private JTextField tfTourStop;
    private JButton hinzufügenButton;
    private JList tfScheduleList;
    private JComboBox cbChooseGenre;
    private JList listGenre;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox cbBand;
    private JComboBox cbGenre;
    private JButton addToListButton;
    private JFormattedTextField ftfDurMin;
    private JFormattedTextField ftfDurSek;


    // manually declared
    private ActionListener CancelListener;
    private JDBC mydb;
    private DBQueries sql;

    public CreateGUI(JDBC db) {



        this.mydb = db;
        this.sql = new DBQueries();





        CancelListener = e -> {
        this.dispose();
            new MainGui(db);
        };

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        this.setContentPane(formPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Neue Band erstellen");

        pack();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setBackground(Color.darkGray);

        this.setVisible(true);

        btnCancelAlbumButton.addActionListener(CancelListener);
        btnCancelBand.addActionListener(CancelListener);
        btnCancelSong.addActionListener(CancelListener);
        btnCancelTour.addActionListener(CancelListener);


        tfBandName.addInputMethodListener(new InputMethodListener() {
            @Override
            public void inputMethodTextChanged(InputMethodEvent event) {
                db.executeQuery("");
            }

            @Override
            public void caretPositionChanged(InputMethodEvent event) {

            }
        });


        addToListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        btnSaveSong.addActionListener(e -> {

//           db.executeQuery(sql.InsertSong(cbBand));
        });
    }
}
