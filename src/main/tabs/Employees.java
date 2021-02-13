package main.tabs;

import main.PigFarmUI;
import main.PigTableModel;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Employees {
//    private List<Object[]> showEmployeeData;
    private String[] columnNames;
    private List<Object[]> allEmployeeData;
    private PigTableModel employeeTableModel;

    public JPanel mainPanel;
    private JTable employeeTable;
    private JLabel findVLabel;
    private JComboBox foddertypeField;
    private JLabel resultVLabel;
    private JButton findButton;


//    private String[] colNames = new String[]{"Employee ID", "Employee Name", "Phone number", "Area of Expertise"};


    public Employees() {
//        performButton.addActionListener(e -> refreshEmployeeData());
        findButton.addActionListener(e -> findV());
    }

    private void findV(){
        String fodderType = ((String) foddertypeField.getSelectedItem()).trim();

        if(fodderType.isEmpty()){
            resultVLabel.setText("Invalid Requirement!");
            return;
        }


        try{
            String sql = "select DISTINCT E.EPHONENUMBER,E.EMPLOYEEID,E.ENAME,V.AREAOFEXPERTISE " +
                    "from EMPLOYEE E,PIG P,PIGSTYMAPPING S,MEDICALRECORD M,CREATES C, BREEDFODDER BF, VETERINARIAN V " +
                    "where BF.BREED = S.BREED AND BF.FODDERNAME = '"+fodderType+"'  " +
                    "AND M.PIGID=P.PIGID AND P.BREED = S.BREED AND C.PIGID = P.PIGID AND C.EMPLOYEEID = E.EMPLOYEEID AND V.EMPLOYEEID = C.EMPLOYEEID";

            Statement stmt = PigFarmUI.con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String pnum =null;
            String name =null;

            while (rs.next()){
                pnum = rs.getString(1);
                name = rs.getString(3);
            }
            String resultText = "The Veterinarian is "+name+"whose phone number is "+pnum+".";
            resultVLabel.setText(resultText);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


//    private List<Object[]> showEmployee() {
//        List<Object[]> data = new ArrayList<>();
//        String styID = (String)styIDComboBox.getSelectedItem();
//        String eName;
//        String employeeID;
//        String phone;
//        String area;
//
//        try {
//            Statement statement1 = PigFarmUI.con.createStatement();
//            //ResultSet resultSet1 = statement1.executeQuery("SELECT E.EMPLOYEEID, E.ENAME FROM CLEANS S,FEEDS D, EMPLOYEE E"
//              //              + " WHERE ((S.STYID ='" + styID + "'OR D.STYID = '" + styID +"')AND (S.EMPLOYEEID=E.EMPLOYEEID OR D.EMPLOYEEID=E.EMPLOYEEID))");
//
//            ResultSet resultSet1 = statement1.executeQuery("SELECT DISTINCT E.EPHONENUMBER FROM EMPLOYEE E,PIG P,PIGSTYMAPPING S,MEDICALRECORD M,CREATES C"
//                    + " WHERE S.STYID = '" + styID + "'AND P.BREED = S.BREED AND P.SEX = S.SEX AND P.HEALTHSTATUS = S.HEALTHSTATUS AND C.PIGID = P.PIGID AND C.EMPLOYEEID = E.EMPLOYEEID");
//            while (resultSet1.next()) {
//                employeeID  = resultSet1.getString(1);
//                eName = resultSet1.getString(2);
//                phone = resultSet1.getString(3);
//                area = resultSet1.getString(4);
//                System.out.println(eName + ", " + employeeID + ", " + phone + ", " + area);
//                Object[] dataRow = {employeeID, eName, phone, area};
//                data.add(dataRow);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return data;
//    }
//
    private void refreshEmployee() {
        allEmployeeData = getAllEmployees();
        employeeTableModel.updateData(allEmployeeData);
        employeeTable.updateUI();
    }

    private List<Object[]> getAllEmployees() {
        List<Object[]> data = new ArrayList<>();

        try {
            Statement statement = PigFarmUI.con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Employee");

            String employeeID;
            String eName;
            String ePhoneNumber;
            int currentlivingCost;
            String address;

            while (resultSet.next()) {
                employeeID = resultSet.getString(1);
                eName = resultSet.getString(2);
                ePhoneNumber = resultSet.getString(3);
                currentlivingCost = resultSet.getInt(4);
                address = resultSet.getString(5);

                Object[] dataRow = {employeeID, eName, ePhoneNumber, currentlivingCost, address};
                data.add(dataRow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    private void createUIComponents() {
//        NumberFormat numberFormat = NumberFormat.getNumberInstance();
//        numberFormat.setGroupingUsed(false);

        columnNames = new String[]{"Employee ID", "Employee Name", "Phone", "Living Cost", "Address"};
        allEmployeeData = getAllEmployees();
        employeeTableModel = new PigTableModel(columnNames, allEmployeeData);
        employeeTable = new JTable(employeeTableModel);

//        styEmplTable = new JTable(pigTableModel);

    }

    public void onTabSwitch() {
        refreshEmployee();
    }

}