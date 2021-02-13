package main;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PigTableModel extends AbstractTableModel {

    private String[] columnNames;
    private List<Object[]> data;

    public PigTableModel(String[] cols, List<Object[]> data)
    {
        this.columnNames = cols;
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    public void setColumnNames(String[] columnNames){this.columnNames = columnNames;}

    public void updateData(List<Object[]> data) {
        this.data = data;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public boolean isCellEditable(int row, int col)
    { return false; }
}