package main.tabs;

import main.PigFarmUI;
import main.PigTableModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PigstyMapping {
    private String[] columnNames;
    private List<Object[]> allMappingData;
    private PigTableModel mappingTableModel;

    public JPanel mainPanel;
    private JTable mappingTable;

    public PigstyMapping(){
        //System.out.print("aa");
    }

    private List<Object[]> getAllMappingData(){
        List<Object[]> data = new ArrayList<>();

        try{
            Statement statement = PigFarmUI.con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM PIGSTYMAPPING");
            String sex;
            String breed;
            String healthStatus;
            String styID;

            while (resultSet.next()) {
                sex = resultSet.getString(1);
                breed = resultSet.getString(2);
                healthStatus = resultSet.getString(3);
                styID = resultSet.getString(4);

                Object[] dataRow = {sex, breed, healthStatus, styID};
                data.add(dataRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private void createUIComponents() {
        columnNames = new String[]{"sex", "breed", "healthStatus", "styID"};
        allMappingData = getAllMappingData();

        mappingTableModel = new PigTableModel(columnNames, allMappingData);
        mappingTable = new JTable(mappingTableModel);

//        System.out.print(allMappingData.size());
    }

    public void onTabSwitch() {
    }

}
