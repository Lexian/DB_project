package GUI;

import DB.DBQueries;
import DB.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;


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
    private JTextField tfTourName;
    private JButton btnAddCity;
    private JList tfScheduleList;
    private JComboBox cbChooseGenre;
    private JList listGenre;
    private JComboBox cbAlbumBand;
    private JComboBox cbTourBand;
    private JComboBox cbBand;
    private JComboBox cbGenre;
    private JButton btnaAddToSong;
    private JFormattedTextField ftfDurMin;
    private JFormattedTextField ftfDurSek;
    private JTextField tfDay;
    private JTextField tfMonth;
    private JTextField tfYear;
    private JComboBox cbTourCity;
    private JTextField tfTourBeginDay;
    private JTextField tfTourBeginYear;
    private JTextField tfTourBeginMonth;
    private JTextField tfTourEndDay;
    private JTextField tfTourEndYear;
    private JTextField tfTourEndMonth;
    // Datum Album

    private JFormattedTextField ftfReleas;


    // manually declared
    private ActionListener CancelListener;
    private JDBC mydb;
    private DBQueries sql;

    public CreateGUI(JDBC db) {


        this.mydb = db;
        this.sql = new DBQueries();

// TODO :  Muss eventuell in einem Thread ausgeführt werden!
        /* Füllen der Genre Box*/
        ResultSet rsGenre = mydb.executeQuery(sql.getElements("genre", "name"));
        try {
            while (rsGenre.next())
                cbGenre.addItem(rsGenre.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        /* Füllen der Band Boxen*/
        ResultSet rsBand = mydb.executeQuery(sql.getElements("band", "name"));
        try {
            while (rsBand.next())
                cbBand.addItem(rsBand.getString("name"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rsALbumBand = mydb.executeQuery(sql.getElements("band", "name"));
        try {
            while (rsALbumBand.next())
                cbAlbumBand.addItem(rsALbumBand.getString("name"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rsTourBand = mydb.executeQuery(sql.getElements("band", "name"));
        try {
            while (rsTourBand.next())
                cbTourBand.addItem(rsTourBand.getString("name"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rsTourCity = mydb.executeQuery(sql.getElements("cities", "name","country","country_id"));
        try {
            while (rsTourCity.next()) {
                String tmp = rsTourCity.getString(1) +" (" +rsTourCity.getString(2)+" )";
                cbTourCity.addItem(tmp);
               // cbTourCity.addItem(rsTourCity.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DefaultListModel lm = new DefaultListModel();
        btnAddCity.addActionListener(e -> {
            lm.addElement(cbTourCity.getSelectedItem().toString());
            tfScheduleList.setModel(lm);
        });


        /* Füllen der Album Box*/
        ResultSet rsAlbum = mydb.executeQuery(sql.getElements("album", "name", "band_id", "band", cbBand.getSelectedItem().toString()));
        try {
            while (rsAlbum.next())
                cbAlbumName.addItem(rsAlbum.getString("name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

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





        DefaultListModel listModel = new DefaultListModel();
        btnaAddToSong.addActionListener(e -> {

            listModel.addElement(cbGenre.getSelectedItem().toString());
            listGenre.setModel(listModel);
        });
        cbBand.addItemListener(e -> {
            cbAlbumName.removeAllItems();
            ResultSet rsAlbum1 = mydb.executeQuery(sql.getElements("album", "name", "band_id", "band", cbBand.getSelectedItem().toString()));
            try {
                while (rsAlbum1.next())
                    cbAlbumName.addItem(rsAlbum1.getString("name"));
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });
/*Safe Safes a Song*/
        btnSaveSong.addActionListener(e -> {
            String album_id = getIDString("album_id", "album","name" ,cbAlbumName.getSelectedItem().toString());
            String band_id = getIDString("band_id","band","name",cbBand.getSelectedItem().toString());
            int duration = Integer.parseInt(ftfDurMin.getText()) * 60 + Integer.parseInt(ftfDurSek.getText());

            db.executeSaveQuery(sql.insertSong(tfSongName.getText(), Integer.toString(duration), band_id, album_id));

            String song_id = getIDString("song_id", "song","title", tfSongName.getText());
            ListModel model = listGenre.getModel();
            for (int i = 0;i < model.getSize();i++){
             String genre_id = getIDString("genre_id", "genre","name" ,model.getElementAt(i).toString());
             db.executeQuery(sql.insertItemsToDB("belongs_to", "genre_id", "song_id", genre_id, song_id));


            }

            reload();
        });


        btnSaveBand.addActionListener(e -> {
                db.executeSaveQuery(sql.insertBand(tfBandName.getText()));
            reload();
        });

/*Safe the Album*/
        btnSaveAlbum.addActionListener(e -> {
            Calendar cal = Calendar.getInstance();
            int year =Integer.parseInt(tfYear.getText());
            int month =Integer.parseInt(tfMonth.getText());
            int date =Integer.parseInt(tfDay.getText());
            cal.set(year, month, date);



            String tmpBandID = null;
            Date myDate = new Date(cal.get(1)-1900,cal.get(2)-1,cal.get(5));

            ResultSet rsBandID =db.executeQuery(sql.getID("band_id", "band","name", cbAlbumBand.getSelectedItem().toString()));
            try {
                rsBandID.next();
                tmpBandID = rsBandID.getString("band_id");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            db.executeSaveQuery(sql.insertAlbum(tfAlbumName.getText(), myDate, tmpBandID));

            reload();
        });
/*Save Tour !!!*/
        btnSaveTour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Calendar calStart = Calendar.getInstance();
                int yearBeg =Integer.parseInt(tfTourBeginYear.getText());
                int monthBeg =Integer.parseInt(tfTourBeginMonth.getText());
                int dateBeg =Integer.parseInt(tfTourBeginDay.getText());
                calStart.set(yearBeg, monthBeg, dateBeg);

                Date begin = new Date(calStart.get(1)-1900,calStart.get(2)-1,calStart.get(5));


                Calendar calEnd = Calendar.getInstance();
                int yearEnd =Integer.parseInt(tfTourEndYear.getText());
                int monthEnd =Integer.parseInt(tfTourEndMonth.getText());
                int dateEnd =Integer.parseInt(tfTourEndDay.getText());
                calEnd.set(yearEnd, monthEnd, dateEnd);

                Date end = new Date(calEnd.get(1)-1900,calEnd.get(2)-1,calEnd.get(5));


                String band_id = getIDString("band_id","band","name",cbTourBand.getSelectedItem().toString());


               db.executeSaveQuery(sql.insertTour(tfTourName.getText(),begin,end,band_id));

                String tour_id = getIDString("tour_id", "tour", "name", tfTourName.getText());
                ListModel model = tfScheduleList.getModel();
                for (int i = 0;i < model.getSize();i++){
                    String[] tmp = model.getElementAt(i).toString().split("\\(");

                    String city_id = getIDString("city_id", "cities","name" ,tmp[0].trim());
                    db.executeQuery(sql.insertItemsToDB("held_in", "tour_id", "city_id", tour_id, city_id));


                }

                reload();

            }
        });
    }

    private String getIDString( String column, String table, String searchRequest,String condition) {
        ResultSet rs = mydb.executeQuery(sql.getID(column,table, searchRequest,condition));
        try {
            rs.next();
            return rs.getString(column);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    private void reload() {
        this.dispose();
        new CreateGUI(mydb);
    }
}
