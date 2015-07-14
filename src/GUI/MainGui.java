package GUI;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * Created by danieljunker on 14.07.15.
 */
public class MainGui extends JFrame{
    private JTabbedPane tabbedPane1;
    private JButton createButton;
    private JButton deleteButton;
    private JList list1;
    private JTextField txtSearch;
    private JList list2;
    private AbstractBorder brdrLeft ;

    public MainGui() {
        super("Test");
        pack();


    }
}
