package top.rayalto.classSchedule.tables;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.SchoolClass;

public class ClassTable extends JTable {
    private static final long serialVersionUID = 1L;

    private DefaultTableModel tableModel = (DefaultTableModel) getModel();
    private TableColumnModel tableColumnModel = getColumnModel();

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public ClassTable() {
        setFont(Sources.NOTO_SANS_MONO_FONT);
        getTableHeader().setFont(Sources.NOTO_SANS_MONO_FONT);
        tableModel.addColumn("ID");
        tableModel.addColumn("代码");
        tableModel.addColumn("名称");
        tableModel.addColumn("年级");
        tableModel.addColumn("学生数");
        tableColumnModel.getColumn(0).setPreferredWidth(40);
        tableColumnModel.getColumn(1).setPreferredWidth(105);
        tableColumnModel.getColumn(2).setPreferredWidth(150);
        tableColumnModel.getColumn(3).setPreferredWidth(40);
        tableColumnModel.getColumn(4).setPreferredWidth(40);
    }

    public ClassTable(List<SchoolClass> schoolClasses) {
        this();
        for (SchoolClass schoolclass : schoolClasses) {
            addRow(schoolclass);
        }
    }

    public void addRow(SchoolClass schoolClass) {
        tableModel.addRow(new Object[] { String.valueOf(schoolClass.id), schoolClass.code, schoolClass.className,
                schoolClass.grade, String.valueOf(schoolClass.studentCount) });
    }

    public void addRows(List<SchoolClass> schoolClasses) {
        for (SchoolClass schoolclass : schoolClasses) {
            addRow(schoolclass);
        }
    }

    public void insertRow(SchoolClass schoolClass, int index) {
        tableModel.insertRow(index, new Object[] { String.valueOf(schoolClass.id), schoolClass.code,
                schoolClass.className, schoolClass.grade, String.valueOf(schoolClass.studentCount) });
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