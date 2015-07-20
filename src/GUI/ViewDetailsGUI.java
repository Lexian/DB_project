package GUI;

import DB.DBQueries;
import DB.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by danieljunker on 19.07.15.
 */
public class ViewDetailsGUI extends JFrame {
    private JPanel mainPanel;
    private JList listDetails;
    private JScrollPane spDetails;
    private JButton btnCloseForm;
    private JButton btnDelete;
    private DBQueries sql = new DBQueries();


    public ViewDetailsGUI(DefaultListModel model, String tableName, JDBC db, String table, String band_id) {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Details:"+ tableName);

        pack();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setBackground(Color.darkGray);


        listDetails.setModel(model);


        this.setVisible(true);

        btnCloseForm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] precut = listDetails.getSelectedValue().toString().split("\\|");
                String column = "name";
                if(table =="song")
                column="title";

                delete(precut[0].trim(), table,db,model,tableName, band_id,column);
            }
        });
    }

    private void delete(String s, String table,JDBC dbDel,DefaultListModel model, String tableName,  String band_id, String column) {



        dbDel.executeQuery(sql.deleteDS(table, column ,s));



        this.dispose();

    }

    private void closeWindow() {
        this.dispose();
    }
}
