package GUI;

import DB.DBQueries;
import DB.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * Created by danieljunker on 15.07.15.
 */
public class ViewDSGUI extends JFrame {
    private JTextField tfSearch;
    private JPanel mainPanel;
    private JButton btnSearch;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnCancel;
    private JButton songsAnzeigenButton;
    private JButton toursAnzeigenButton;
    private JButton albumAnzeigenButton;
    private JButton komplettenDatensatzLöschenButton;
    private JScrollPane spOutput;
    private JList listOutput;
    private JTable Bandname;

    private DBQueries sql;
    private JDBC db;

/*View of Details*/
    private JPanel panel = new JPanel(new GridLayout(0, 1));
    private JScrollPane detailScrollpane;
    private JList detailList;

    public ViewDSGUI(JDBC db) {


        this.db = db;
        sql =  new DBQueries();


        detailList = new JList();
        detailScrollpane = new JScrollPane();

/*laden Aller Bands*/
        DefaultListModel listModelband = new DefaultListModel();
        ResultSet rs = db.executeQuery(sql.getElements("band","name"));
        try {
            while(rs.next()){
                listModelband.addElement(rs.getObject("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listOutput.setModel(listModelband);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Eine Band suchen!");

        pack();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setBackground(Color.darkGray);

        this.setVisible(true);


        songsAnzeigenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                String bandid =  getIDString("band_id", "band","name", listOutput.getSelectedValue().toString());

                new ViewDetailsGUI(getListModel("song","title","duration","genre","genre_id","song_id",bandid,""),"Songs der Band",db, "song",bandid);



            }
        });
        albumAnzeigenButton.addActionListener(e -> {

            String band_id =  getIDString("band_id", "band","name", listOutput.getSelectedValue().toString());


            new ViewDetailsGUI( getListModel("album","name","release_date","song","duration","band_id",band_id, "1,2,3"),"Alben der Band",db, "album",band_id);
        });
        btnCancel.addActionListener(e -> closeWindow());
        toursAnzeigenButton.addActionListener(e -> {
            String band_id =  getIDString("band_id", "band", "name", listOutput.getSelectedValue().toString());
            new ViewDetailsGUI(getListModel("tour","name","begin_date, tour.end_date","cities","name","band_id",band_id, "1,2,3,4"),"TourDaten",db, "tour",band_id );
        });
        komplettenDatensatzLöschenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] precut = listOutput.getSelectedValue().toString().split("\\|");

                deleteAll(precut[0]);


            }
        });
    }

    private void deleteAll(String s) {
        ResultSet rs = db.executeQuery(sql.getID("band_id","band","name",s));
        try {
        rs.next();
        String band_id =rs.getString("band_id");


            db.executeQuery(sql.deleteDS("song", band_id));
            db.executeQuery(sql.deleteDS(" tour ", band_id));
            db.executeQuery(sql.deleteDS("album", band_id));
            db.executeQuery(sql.deleteDS(" band", band_id));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.dispose();
        new ViewDSGUI(db);

    }


    private DefaultListModel getListModel(String table,String column,String column_two,String joinTable,String joinColumn,String condition, String id, String groupOrder) {
        if(table == "song") {
            ResultSet rs = db.executeQuery(sql.getElementsJoinSong(id));

            DefaultListModel tmp = new DefaultListModel();
            int i = 0;
            try {
                    while (rs.next()) {
                        String stringLine = rs.getString("" + column + "") + " | Dauer: ( " + getTime(rs.getString("duration")) + " min)  | Genres: " + rs.getString("array_to_string");
                        tmp.addElement(stringLine);
                    }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return tmp;
        }else if (table == "album"){
            ResultSet rs = db.executeQuery(sql.getElementsJoinAlbumTour(table, column, column_two, joinTable, joinColumn, condition, id, groupOrder));

            DefaultListModel tmp = new DefaultListModel();
            int i = 0;
            try {
                while (rs.next()) {
                    String timeTmp = rs.getString("array_to_string").isEmpty() ? null : rs.getString("array_to_string") ;
                    String stringLine = rs.getString("name") + " | erschien am: " + rs.getString("release_date") + "  | Dauer: " + getTimeWithSplit(timeTmp)+" minuten";

                    tmp.addElement(stringLine);

                }
                return tmp;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if (table == "tour"){
            ResultSet rs = db.executeQuery(sql.getElementsJoinAlbumTour(table, column, column_two, joinTable, joinColumn, condition, id, groupOrder));

            DefaultListModel tmp = new DefaultListModel();

            try {
                while (rs.next()) {
                    String cities = rs.getString("array_to_string");
                    String stringLine = rs.getString("name") + " | Tour start: " + rs.getString("begin_date") + " | Tour Ende: "+rs.getString("end_date")+"  | Stops in: " + (cities)+" ";

                    tmp.addElement(stringLine);

                }
                return tmp;
            } catch (SQLException e) {
                e.printStackTrace();
            }



        }
        return null;
    }

    private String getTimeWithSplit(String timeString) {

        if(timeString == null) {
            return Float.toString((float) 0.0);
        }
        String[] timeTemp = timeString.split(",");
        long sumOf =0;
        for (int i =0;i < timeTemp.length;i++)
            sumOf+=Long.parseLong(timeTemp[i]);
        float calc = TimeUnit.SECONDS.toMinutes(sumOf);
        float seconds = (sumOf % 60);
        return Float.toString(calc + (seconds/100));

    }


    private String getTime(String time) {
        float calc = TimeUnit.SECONDS.toMinutes(Long.parseLong(time));
        float seconds = (Integer.parseInt(time) % 60);
        return Float.toString(calc + (seconds/100));
    }

    private String getIDString( String column, String table, String searchRequest,String condition) {
        ResultSet rs = db.executeQuery(sql.getID(column,table, searchRequest,condition));
        try {
            rs.next();
            return rs.getString(column);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
    private void closeWindow() {
        this.dispose();
        new MainGui(db);
    }




}
