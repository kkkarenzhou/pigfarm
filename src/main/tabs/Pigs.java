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
import java.util.Random;

public class Pigs {

    private String[] columnNames;
    private List<Object[]> allPigsData;
    private PigTableModel pigTableModel;

    public JPanel mainPanel;

    private JTable pigTable;
    private JPanel addPigPanel;
    private JTextArea addUpdateTextArea;

    private JCheckBox pregnantCheckBox;
    //TODO: I guess items in combobox needs manually updated if a new breed is introduced. Same for all other boxes.
    private JComboBox sexField;
    private JComboBox breedField;
    private JComboBox healthStatusField;
    private JFormattedTextField ageField;
    private JFormattedTextField weightField;
    private JTextField pigIDField;

    private JButton addUpdatePigButton;
    private JButton deleteSelectedPigSButton;
    private JLabel statusLabel;

    public Pigs()
    {
        addUpdatePigButton.addActionListener(e -> addOrUpdatePig());
        deleteSelectedPigSButton.addActionListener(e -> deleteSelectedPigs());
    }

    private void deleteSelectedPigs()
    {
        int[] selectedRows = pigTable.getSelectedRows();

        String text = "";
        for (int i : selectedRows) {
            String pigID = (String)pigTable.getValueAt(i,0);
            try {
                PreparedStatement ppst1 = PigFarmUI.con.prepareStatement("DELETE FROM CREATES WHERE pigID='" + pigID + "'");
                ppst1.executeUpdate();
                PreparedStatement ppst2 = PigFarmUI.con.prepareStatement("DELETE FROM PIG WHERE pigID='" + pigID + "'");
                ppst2.executeUpdate();
                text += pigID + ", ";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        statusLabel.setText("Pig(s) of ID: " + text.substring(0, text.length() - 2) + " deleted!");
        refreshPigData();
    }

    private void addOrUpdatePig()
    {
        String pigID = pigIDField.getText().trim();

        boolean isPregnant = pregnantCheckBox.isSelected();
        String sex = (String)sexField.getSelectedItem();
        String breed = (String)breedField.getSelectedItem();
        String healthStatus = (String)healthStatusField.getSelectedItem();
        String ageStr = ageField.getText().trim();
        String weightStr = weightField.getText().trim();

        if (pigID.isEmpty() || sex.isEmpty() || breed.isEmpty() || healthStatus.isEmpty() ||
                ageStr.isEmpty() || weightStr.isEmpty()) {
            statusLabel.setText("Missing/invalid values to add/update a pig!");
            return;
        }

        int age = Integer.parseInt(ageStr);
        int weight = Integer.parseInt(weightStr);

        // Check PigStyMapping - if it doesn't have a home, the pig can't be added or updated!
        try {
            PreparedStatement ps = PigFarmUI.con.prepareStatement("SELECT COUNT(*) FROM PIGSTYMAPPING WHERE" +
                    " BREED='" + breed + "' AND HEALTHSTATUS='" + healthStatus + "' AND SEX='" + sex +
                    "'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            int psmCount = rs.getInt(1);
            if (psmCount == 0) {
                statusLabel.setText("There's no home for the pig! Change your combination of sex, breed, healthStatus!");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Check PigPriceMapping, add tuple if needed.
        try {
            PreparedStatement ps = PigFarmUI.con.prepareStatement("SELECT COUNT(*) FROM PIGPRICEMAPPING WHERE AGE=" + age +
                    " AND BREED='" + breed + "' AND HEALTHSTATUS='" + healthStatus + "' AND SEX='" + sex +
                    "' AND WEIGHT=" + weight);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int ppmCount = rs.getInt(1);
            if (ppmCount == 0) {
                ps = PigFarmUI.con.prepareStatement("INSERT INTO PIGPRICEMAPPING VALUES(?, ?, ?, ?, ?, ?)");
                ps.setString(1, sex);
                ps.setInt(2, age);
                ps.setInt(3, weight);
                ps.setString(4, breed);
                ps.setString(5, healthStatus);

                // Placeholder for a realistic algorithm to calculate the market price of a pig based on its qualities.
                int marketPrice = (2 + new Random().nextInt(weight / 20)) * (healthStatus.equals("Unhealthy") ? 0 : 1);
                ps.setInt(6, marketPrice);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Either add, or update the pig, if pigID exists or does not exist.
        if (isPigIDUnique(pigID)) {
            try {
                PreparedStatement ps = PigFarmUI.con.prepareStatement("INSERT INTO pig VALUES (?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, pigID);
                ps.setString(2, sex);
                ps.setInt(3, age);
                ps.setInt(4, weight);
                ps.setString(5, breed);
                ps.setString(6, isPregnant ? "Y" : "N");
                ps.setString(7, healthStatus);
                ps.executeUpdate();
                statusLabel.setText("Added pig!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                PreparedStatement ps = PigFarmUI.con.prepareStatement("UPDATE pig SET sex=?, age=?, weight=?, breed=?, " +
                        "pregnancy=?, healthStatus=? WHERE pigID='" + pigID + "'");
                ps.setString(1, sex);
                ps.setInt(2, age);
                ps.setInt(3, weight);
                ps.setString(4, breed);
                ps.setString(5, isPregnant ? "Y" : "N");
                ps.setString(6, healthStatus);
                ps.executeUpdate();
                statusLabel.setText("Updated pig!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        refreshPigData();
    }

    private boolean isPigIDUnique(String pigID)
    {
        for (Object[] pig : allPigsData) {
            if (((String)pig[0]).trim().equals(pigID)) {
                return false;
            }
        }
        return true;
    }

    private void refreshPigData() {
        allPigsData = getAllPigs();
        pigTableModel.updateData(allPigsData);
        pigTable.updateUI();
    }

    private List<Object[]> getAllPigs()
    {
        List<Object[]> data = new ArrayList<>();
        try {
            Statement statement = PigFarmUI.con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pig");

            String pigID;
            String sex;
            int age;
            int weight;
            String breed;
            String pregnancy;
            String healthStatus;

            while (resultSet.next()) {
                pigID = resultSet.getString(1);
                sex = resultSet.getString(2);
                age = resultSet.getInt(3);
                weight = resultSet.getInt(4);
                breed = resultSet.getString(5);
                pregnancy = resultSet.getString(6);
                healthStatus = resultSet.getString(7);

                Object[] dataRow = {pigID, sex, age, weight, breed, pregnancy, healthStatus};
                data.add(dataRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    //    pigID        CHAR(11),
    //    sex          CHAR(1),
    //    age          INTEGER,
    //    weight       INTEGER,
    //    breed        CHAR(10),
    //    pregnancy    CHAR(1),
    //    healthStatus CHAR(10),
    private void createUIComponents()
    {
        NumberFormat numFormat = NumberFormat.getNumberInstance();
        numFormat.setGroupingUsed(false);
        ageField = new JFormattedTextField(numFormat);
        weightField = new JFormattedTextField(numFormat);

        columnNames = new String[]{"Pig ID", "Sex", "Age", "Weight", "Breed", "Pregnancy", "Health Status"};

        allPigsData = getAllPigs();

        pigTableModel = new PigTableModel(columnNames, allPigsData);
        pigTable = new JTable(pigTableModel);
    }

    public void onTabSwitch()
    {
        refreshPigData();
    }
}
