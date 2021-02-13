package main.tabs;

import main.PigFarmUI;
import main.PigTableModel;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pigstys {
    private String[] columnNames;
    private List<Object[]> allStyData;
    private PigTableModel styTableModel;

    private List<Object[]> employees;
    private List<Object[]> toFedStys;

    public JPanel mainPanel;
    private JTable styTable;
    private JPanel address;
    private JPanel styCostPanel;
    private JPanel employeeListPanel;
    private JLabel employeeListLabel;
    private JLabel styIDPriceLabel;
    private JLabel styPriceLabel;
    private JLabel styID4employeeLabel;
    private JButton checkPriceButton;
    private JButton checkEmployeeButton;
    private JPanel toFedPanel;
    private JLabel toFedLabel;
    private JLabel timeLabel;
    private JTextField dateField;
    private JButton checkStyButton;
    private JLabel employResultLabel;
    private JTextField selectedStyIDField;
    private JLabel fedResultLabel;
    private JTextField styIDPriceField;
    private JLabel priceResultLabel;
    private JButton refreshToStyButton;


    public Pigstys()
    {
        refreshToStyButton.addActionListener(e -> refreshTabletoSty());
        checkEmployeeButton.addActionListener(e -> refreshTabletoEmployee());
        checkStyButton.addActionListener(e -> refreshTabletoFed());
        checkPriceButton.addActionListener(e -> totalPrice());
    }

    private void totalPrice(){
        String selectedID = styIDPriceField.getText().trim();
        if(selectedID.isEmpty()){
            priceResultLabel.setText("Invalid Requirement!");
            return;
        }
        try{
            String sql = "SELECT sum(PP.MARKETPRICE) from PIG P, PIGPRICEMAPPING PP " +
                    "where PIGID in (select DISTINCT P.PIGID from PIGSTYMAPPING PM,PIG P " +
                    "where PM.STYID = '"+selectedID+"'AND PM.BREED = P.BREED AND PM.SEX = P.SEX " +
                    "AND PM.HEALTHSTATUS = P.HEALTHSTATUS) and P.HEALTHSTATUS =PP.HEALTHSTATUS " +
                    "and P.WEIGHT = PP.WEIGHT AND P.AGE = PP.AGE AND P.BREED=PP.BREED AND P.SEX = PP.SEX ";

            Statement st = PigFarmUI.con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int total;
            if(!rs.next()){
                priceResultLabel.setText("Nothing Obtained");
            }
            total = rs.getInt(1);
            priceResultLabel.setText(String.valueOf(total));
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void refreshTabletoEmployee(){
        //styTableModel.fireTableStructureChanged();
        String[] newNames = new String[]{"employee ID", "Employee Name"};
        employees = getAllEmployee();

        styTableModel = new PigTableModel(newNames, employees);
        styTable.setModel(styTableModel);

//        styTableModel.setColumnNames(newNames);
//        styTableModel.updateData(employees);

        styTable.updateUI();
        //System.out.print(styTableModel.getColumnName(1));
    }

    private void refreshTabletoSty(){
        columnNames = new String[]{"styID", "buildingMaterial", "stySize", "address", "#ofPigs"};
        allStyData = getAllSty();

        styTableModel = new PigTableModel(columnNames, allStyData);
        styTable.setModel(styTableModel);
        styTable.updateUI();
    }

    private List<Object[]> getAlltoFed(){
        List<Object[]> dt = new ArrayList<>();
        String lastDate = dateField.getText().trim();

        if(lastDate.isEmpty()){
            fedResultLabel.setText("Invalid Requirement!");
            return Collections.emptyList();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        java.util.Date textFieldAsDate = null;

        try {
            sdf.parse(lastDate);
            //System.out.print(textFieldAsDate);
        } catch (ParseException pe) {
            fedResultLabel.setText("incorrect Format");
            return Collections.emptyList();
        }

        try{
            String sql = "select STYID, ADDRESS from PIGSTY where " +
                    "STYID in (select STYID from (select STYID, MAX(LASTFEDDATE) as " +
                    "LASTFEDDATE from FEEDS group by STYID) " +
                    "where LASTFEDDATE <= '" + lastDate + "')";

            Statement statement = PigFarmUI.con.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            String styID;
            String address;

            while (rs.next()){
                styID = rs.getString(1);
                address = rs.getString(2);

                Object[] tofed = {styID, address};
                dt.add(tofed);
            }
            if (dt.size() == 0) {
                fedResultLabel.setText("not found!");
                return Collections.emptyList();
            }
            fedResultLabel.setText("There are the Hungry Sties!");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return dt;
    }



    private void refreshTabletoFed(){
        String[] newNames = new String[]{"sty ID", "Address"};
        toFedStys = getAlltoFed();

        styTableModel = new PigTableModel(newNames, toFedStys);
        styTable.setModel(styTableModel);

        styTable.updateUI();
    }

    private List<Object[]> getAllEmployee(){
        List<Object[]> data = new ArrayList<>();
        String selectedID = selectedStyIDField.getText().trim();

        if(selectedID.isEmpty()){
            employResultLabel.setText("Invalid Requirement!");
            return Collections.emptyList();
        }

        try{
            String sql = "select E.EMPLOYEEID, E.ENAME " +
                    "from CLEANS S, FEEDS D, EMPLOYEE E " +
                    "WHERE (S.STYID = '"+selectedID+"' OR D.STYID='"+selectedID+"') " +
                    "AND (S.EMPLOYEEID=E.EMPLOYEEID OR D.EMPLOYEEID=E.EMPLOYEEID)";

            Statement statement = PigFarmUI.con.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            String employeeID;
            String eName;
            while (resultSet.next()){
                employeeID = resultSet.getString(1);
                eName = resultSet.getString(2);

                Object[] employee = {employeeID, eName};
                data.add(employee);
            }
            if (data.size() == 0) {
                employResultLabel.setText("not found!");
                return Collections.emptyList();
            }
            employResultLabel.setText("There are the Employees!");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return data;
    }

    private List<Object[]> getAllSty(){
        List<Object[]> data = new ArrayList<>();
        List<Object[]> countData = new ArrayList<>();

        try{
            Statement stmt1 = PigFarmUI.con.createStatement();
            ResultSet resultSet1 = stmt1.executeQuery("SELECT * FROM PIGSTY ORDER By STYID");
            Statement stmt2 = PigFarmUI.con.createStatement();
            ResultSet resultSet2 = stmt2.executeQuery("SELECT S.STYID, COUNT (distinct P.PIGID) " +
                    "from PIGSTYMAPPING S,PIG P " +
                    "where S.BREED=P.BREED AND S.HEALTHSTATUS=P.HEALTHSTATUS AND S.SEX=P.SEX Group BY S.STYID");

            String styID1;
            String buildingMaterial;
            int stySize;
            String address;
            String styID2;
            int numPigs;

            while (resultSet2.next()){
                styID2 = resultSet2.getString(1);
                numPigs = resultSet2.getInt(2);

                Object[] countRow = {styID2, numPigs};
                countData.add(countRow);
            }

            while (resultSet1.next()){
                styID1 = resultSet1.getString(1);
                buildingMaterial = resultSet1.getString(2);
                stySize = resultSet1.getInt(3);
                address = resultSet1.getString(4);
                numPigs = 0;

                for(int i=0; i<countData.size(); i++){
                    Object[] row = countData.get(i);
                    if(styID1.equals(String.valueOf(row[0]))){
                        numPigs = (int) row[1];
                    }
                }

                Object[] dataRow = {styID1, buildingMaterial, stySize, address, numPigs};
                data.add(dataRow);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    private void createUIComponents() {
        columnNames = new String[]{"styID", "buildingMaterial", "stySize", "address", "#ofPigs"};
        allStyData = getAllSty();
        styTableModel = new PigTableModel(columnNames, allStyData);
        styTable = new JTable(styTableModel);
    }

    public void onTabSwitch() {
        refreshTabletoSty();
    }

}
