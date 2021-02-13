package main.tabs;

import main.PigFarmUI;
import main.PigTableModel;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Fodder {
    private String[] columnNames;
    private List<Object[]> allFodderData;
    private PigTableModel fodderTableModel;

    public JPanel mainPanel;
    private JTable fodderTable;
    private JPanel UpdateFodderPanel;
    private JLabel updateLabel;
    private JLabel fodderTypeLabel;
    private JComboBox fodderTypeField;
    private JLabel amountLabel;
    private JFormattedTextField amountField;
    private JButton updateButton;
    private JLabel resultLabel;

    public Fodder()
    {
        updateButton.addActionListener(e -> updateAmount());
    }

    private void updateAmount(){
        String fodderType = ((String)fodderTypeField.getSelectedItem()).trim();
        String additionAmount = amountField.getText().trim();

        if(fodderType.isEmpty() || additionAmount.isEmpty()){
            updateLabel.setText("Invalid Requirement!");
            return;
        }

        int numAddition = Integer.parseInt(additionAmount);

        try{
            PreparedStatement ppst = PigFarmUI.con.prepareStatement("UPDATE FODDERTYPE SET STOCK=STOCK+" + numAddition + " WHERE FODDERNAME='" + fodderType + "'");
            ppst.executeUpdate();
            resultLabel.setText(fodderType + " is updated!");
        }catch (SQLException e){
            e.printStackTrace();
        }
        refreshFodderData();
    }

    private void refreshFodderData() {
        allFodderData = getAllFodder();
        fodderTableModel.updateData(allFodderData);
        fodderTable.updateUI();
    }

    private List<Object[]> getAllFodder(){
        List<Object[]> data = new ArrayList<>();
        try{
            Statement statement = PigFarmUI.con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM FODDERTYPE");
            String fodderName;
            int qualityRating;
            int stock;


            while (resultSet.next()) {
                fodderName = resultSet.getString(1);
                qualityRating = resultSet.getInt(2);
                stock = resultSet.getInt(3);

                Object[] dataRow = {fodderName, qualityRating, stock};
                data.add(dataRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private void createUIComponents() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        amountField = new JFormattedTextField(numberFormat);

        columnNames = new String[]{"fodderName", "qualityRating", "stock"};
        allFodderData = getAllFodder();

        fodderTableModel = new PigTableModel(columnNames, allFodderData);
        fodderTable = new JTable(fodderTableModel);
    }

    public void onTabSwitch() {
        refreshFodderData();
    }

}
