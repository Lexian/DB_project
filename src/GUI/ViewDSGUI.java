package GUI;

import DB.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

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
    private JTable Bandname;

    private ActionListener allListener;
    private JDBC db;

    public ViewDSGUI(JDBC db) {

        this.db = db;

        allListener = e -> {
          switch (e.getActionCommand()){
              case "Abbrechen":{
                  this.dispose();
                  new MainGui(db);
                  break;
              }
              case "Editieren":{

                  break;
              }
              case "Löschen": {
                  //TODO
                  break;
              }
              case "Suchen":{
                  //TODO
                  break;
              }

              default:
                  break;
          }
        };

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Eine Band suchen!");

        pack();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setBackground(Color.darkGray);

        this.setVisible(true);


        btnCancel.addActionListener(allListener);
        btnDelete.addActionListener(allListener);
        btnEdit.addActionListener(allListener);
        btnSearch.addActionListener(allListener);

        try {
            tfSearch.addInputMethodListener(new InputMethodListener() {
                @Override
                public void inputMethodTextChanged(InputMethodEvent event) {

                }

                @Override
                public void caretPositionChanged(InputMethodEvent event) {

                }
            });
        }catch (NullPointerException e){
            System.out.println("scheisse, nix drinne " + e);
        }
    }
}
