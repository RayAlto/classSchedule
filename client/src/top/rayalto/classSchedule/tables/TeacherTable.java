package top.rayalto.classSchedule.tables;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.Teacher;

public class TeacherTable extends JTable {
    private static final long serialVersionUID = 1L;

    private DefaultTableModel tableModel = (DefaultTableModel) getModel();
    private TableColumnModel tableColumnModel = getColumnModel();

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public TeacherTable() {
        setFont(Sources.NOTO_SANS_MONO_FONT);
        getTableHeader().setFont(Sources.NOTO_SANS_MONO_FONT);
        tableModel.addColumn("ID");
        tableModel.addColumn("代码");
        tableModel.addColumn("雇佣类型");
        tableModel.addColumn("姓名");
        tableModel.addColumn("年龄");
        tableModel.addColumn("所属学院ID");
        tableModel.addColumn("所属学院名");
        tableModel.addColumn("职位");
        tableColumnModel.getColumn(0).setPreferredWidth(40);
        tableColumnModel.getColumn(1).setPreferredWidth(90);
        tableColumnModel.getColumn(2).setPreferredWidth(75);
        tableColumnModel.getColumn(3).setPreferredWidth(90);
        tableColumnModel.getColumn(4).setPreferredWidth(30);
        tableColumnModel.getColumn(5).setPreferredWidth(75);
        tableColumnModel.getColumn(6).setPreferredWidth(105);
        tableColumnModel.getColumn(7).setPreferredWidth(60);
    }

    public TeacherTable(List<Teacher> teachers) {
        this();
        for (Teacher teacher : teachers) {
            addRow(teacher);
        }
    }

    public void addRow(Teacher teacher) {
        tableModel.addRow(new Object[] { String.valueOf(teacher.id), teacher.code, teacher.hireType,
                teacher.teacherName, String.valueOf(teacher.age), String.valueOf(teacher.departmentId),
                teacher.departmentName, teacher.title });
    }

    public void addRows(List<Teacher> teachers) {
        for (Teacher teacher : teachers) {
            addRow(teacher);
        }
    }

    public void insertRow(Teacher teacher, int index) {
        tableModel.insertRow(index,
                new Object[] { String.valueOf(teacher.id), teacher.code, teacher.hireType, teacher.teacherName,
                        String.valueOf(teacher.age), String.valueOf(teacher.departmentId), teacher.departmentName,
                        teacher.title });
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