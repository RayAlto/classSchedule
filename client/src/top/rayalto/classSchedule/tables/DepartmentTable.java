package top.rayalto.classSchedule.tables;

import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.Department;

public class DepartmentTable extends JTable {
    private static final long serialVersionUID = 1L;

    private DefaultTableModel tableModel = (DefaultTableModel) getModel();
    private TableColumnModel tableColumnModel = getColumnModel();

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    private void initialize(List<Department> departments) {
        setFont(Sources.NOTO_SANS_MONO_FONT);
        getTableHeader().setFont(Sources.NOTO_SANS_MONO_FONT);
        tableModel.addColumn("学院ID");
        tableModel.addColumn("代码");
        tableModel.addColumn("学院名");
        tableColumnModel.getColumn(0).setPreferredWidth(45);
        tableColumnModel.getColumn(1).setPreferredWidth(45);
        tableColumnModel.getColumn(2).setPreferredWidth(100);
        if (departments != null) {
            for (Department department : departments) {
                addRow(department);
            }
        }
    }

    public DepartmentTable() {
        this(null);
    }

    public DepartmentTable(List<Department> departments) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initialize(departments);
            }
        });
    }

    public void addRow(Department department) {
        tableModel.addRow(new Object[] { String.valueOf(department.id), department.code, department.departmentName });
    }

    public void addRows(List<Department> departments) {
        for (Department department : departments) {
            addRow(department);
        }
    }

    public void insertRow(Department department, int index) {
        tableModel.insertRow(index,
                new Object[] { String.valueOf(department.id), department.code, department.departmentName });
    }

    public void removeRow(int index) {
        tableModel.removeRow(index);
    }

    public void removeAll() {
        tableModel.setRowCount(0);
    }

    public int getColumnCount() {
        return tableModel.getColumnCount();
    }

    public String getColumnName(int index) {
        return tableModel.getColumnName(index);
    }

    public int getRowCount() {
        return tableModel.getRowCount();
    }

    public String getValueAt(int row, int column) {
        return (String) tableModel.getValueAt(row, column);
    }

    public void setValueAt(String newValue, int row, int column) {
        tableModel.setValueAt(newValue, row, column);
    }
}