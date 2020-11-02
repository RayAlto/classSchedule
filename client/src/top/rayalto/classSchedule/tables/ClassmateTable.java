package top.rayalto.classSchedule.tables;

import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.Classmate;

public class ClassmateTable extends JTable {
    private static final long serialVersionUID = 1L;

    private DefaultTableModel tableModel = (DefaultTableModel) getModel();
    private TableColumnModel tableColumnModel = getColumnModel();

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    private void initialize(List<Classmate> classmates) {
        setFont(Sources.NOTO_SANS_MONO_FONT);
        getTableHeader().setFont(Sources.NOTO_SANS_MONO_FONT);
        tableModel.addColumn("代码");
        tableModel.addColumn("姓名");
        tableModel.addColumn("性别");
        tableModel.addColumn("班级ID");
        tableModel.addColumn("班级名");
        tableModel.addColumn("所属专业名");
        tableModel.addColumn("所属学院ID");
        tableModel.addColumn("所属学院名");
        tableColumnModel.getColumn(0).setPreferredWidth(90);
        tableColumnModel.getColumn(1).setPreferredWidth(90);
        tableColumnModel.getColumn(2).setPreferredWidth(30);
        tableColumnModel.getColumn(3).setPreferredWidth(45);
        tableColumnModel.getColumn(4).setPreferredWidth(100);
        tableColumnModel.getColumn(5).setPreferredWidth(105);
        tableColumnModel.getColumn(6).setPreferredWidth(75);
        tableColumnModel.getColumn(7).setPreferredWidth(105);
        if (classmates != null) {
            for (Classmate classmate : classmates) {
                addRow(classmate);
            }
        }
    }

    public ClassmateTable() {
        this(null);
    }

    public ClassmateTable(List<Classmate> classmates) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initialize(classmates);
            }
        });
    }

    public void addRow(Classmate classmate) {
        tableModel.addRow(new Object[] { classmate.code, classmate.classmateName, classmate.gender,
                String.valueOf(classmate.classId), classmate.className, classmate.majorName,
                String.valueOf(classmate.departmentId), classmate.departmentName });
    }

    public void addRows(List<Classmate> classmates) {
        for (Classmate classmate : classmates) {
            addRow(classmate);
        }
    }

    public void insertRow(Classmate classmate, int index) {
        tableModel.insertRow(index,
                new Object[] { classmate.code, classmate.classmateName, classmate.gender,
                        String.valueOf(classmate.classId), classmate.className, classmate.majorName,
                        String.valueOf(classmate.departmentId), classmate.departmentName });
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