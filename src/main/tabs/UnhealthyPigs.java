package main.tabs;

import main.PigFarmUI;
import main.PigTableModel;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UnhealthyPigs {

    private String[] columnNames;
    private List<Object[]> allUnhealthyPigsData;
    private PigTableModel unhealthyPigModel;

    public JPanel mainPanel;
    private JTable unhealthyTable;
    private JButton createViewButton;
    private JTextField styIDField;

    public UnhealthyPigs()
    {
        createViewButton.addActionListener(e -> {
            String styID = styIDField.getText().trim();
            // If styID is empty, get all unhealthy pigs, regardless of where they live.
            try {
                if (styID.isEmpty()) {
                    Statement s = PigFarmUI.con.createStatement();
                    s.execute("CREATE OR REPLACE VIEW unhealthypig AS " +
                            "SELECT pigID, sex, age, breed " +
                            "FROM pig WHERE HEALTHSTATUS='Unhealthy'");
                    allUnhealthyPigsData = getAllUnhealthyPigs();
                    unhealthyPigModel.updateData(allUnhealthyPigsData);
                    unhealthyTable.updateUI();
                } else {
                    Statement s = PigFarmUI.con.createStatement();
                    s.execute("CREATE OR REPLACE VIEW unhealthypig AS " +
                            "SELECT pigID, sex, age, breed " +
                            "FROM (SELECT p.PIGID, p.SEX, p.AGE, p.BREED, p.HEALTHSTATUS, " +
                            "psm.STYID FROM Pig p INNER JOIN PIGSTYMAPPING psm ON p.SEX = psm.sex " +
                            "AND p.BREED = psm.BREED AND p.HEALTHSTATUS = psm.HEALTHSTATUS)" +
                            "WHERE HEALTHSTATUS='Unhealthy' AND STYID='" + styID + "'");
                    allUnhealthyPigsData = getAllUnhealthyPigs();
                    unhealthyPigModel.updateData(allUnhealthyPigsData);
                    unhealthyTable.updateUI();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }

    private List<Object[]> getAllUnhealthyPigs()
    {
        List<Object[]> data = new ArrayList<>();
        try {
            Statement statement = PigFarmUI.con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM unhealthypig");

            String pigID;
            String sex;
            int age;
            String breed;

            while (resultSet.next()) {
                pigID = resultSet.getString(1);
                sex = resultSet.getString(2);
                age = resultSet.getInt(3);
                breed = resultSet.getString(4);

                Object[] dataRow = {pigID, sex, age, breed};
                data.add(dataRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void refreshUnhealthyPigData() {
//        try {
//            Statement s = PigFarmUI.con.createStatement();
//            s.execute("DROP VIEW unhealthypig");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        allUnhealthyPigsData = new ArrayList<>();
        unhealthyPigModel.updateData(allUnhealthyPigsData);
        unhealthyTable.updateUI();
    }

    private void createUIComponents() {
        columnNames = new String[]{"Pig ID", "Sex", "Age", "Breed"};
        allUnhealthyPigsData = new ArrayList<>();
        unhealthyPigModel = new PigTableModel(columnNames, allUnhealthyPigsData);
        unhealthyTable = new JTable(unhealthyPigModel);
    }

    public void onTabSwitch() {
        refreshUnhealthyPigData();
    }
}
