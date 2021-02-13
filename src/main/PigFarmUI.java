package main;

import javax.swing.*;
import java.sql.*;


public class PigFarmUI {

    public static Connection con;

    private static JFrame frame;

    private JPanel mainPanel;
    private JPanel databaseAccess;

    private JButton connectDbBtn;

    private JTextField userTxtField;
    private JLabel userLbl;
    private JPasswordField passTxtField;
    private JLabel passLbl;

    private JLabel typeLbl;
    private JComboBox typeField;

    private JLabel statusLabel;

    public PigFarmUI() {
        connectDbBtn.addActionListener(e -> {
            try {
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                // You may wish to use your own id in case of accidental modification on other people's dataset.
                String user = userTxtField.getText();
                user = "zyzkaren";
                String pass = new String(passTxtField.getPassword());
                pass = "Pigfarm2021";

                con = DriverManager.getConnection(
                        "jdbc:mysql://rm-bp11ma83y432bk3gkuo.mysql.rds.aliyuncs.com:3306/pigfarm", user, pass);
                statusLabel.setText("SUCCESS: connected to database!");

                String employeeType = (String) typeField.getSelectedItem();
                SwingUtilities.invokeLater(() -> {
                    frame.setContentPane(new PigFarmPanel());
                    frame.pack();
                });
            } catch (SQLException ex) {
                statusLabel.setText("ERROR: " + ex);
            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("My Pig Farm");
        frame.setContentPane(new PigFarmUI().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}