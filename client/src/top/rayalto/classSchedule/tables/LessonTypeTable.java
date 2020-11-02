package top.rayalto.classSchedule.tables;

import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.LessonType;

public class LessonTypeTable extends JTable {
    private static final long serialVersionUID = 1L;

    private DefaultTableModel tableModel = (DefaultTableModel) getModel();
    private TableColumnModel tableColumnModel = getColumnModel();

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    private void initialize(List<LessonType> lessonTypes) {
        setFont(Sources.NOTO_SANS_MONO_FONT);
        getTableHeader().setFont(Sources.NOTO_SANS_MONO_FONT);
        tableModel.addColumn("ID");
        tableModel.addColumn("代码");
        tableModel.addColumn("课程类型名");
        tableColumnModel.getColumn(0).setPreferredWidth(45);
        tableColumnModel.getColumn(1).setPreferredWidth(45);
        tableColumnModel.getColumn(2).setPreferredWidth(100);
        if (lessonTypes != null) {
            for (LessonType lessonType : lessonTypes) {
                addRow(lessonType);
            }
        }
    }

    public LessonTypeTable() {
        this(null);
    }

    public LessonTypeTable(List<LessonType> lessonTypes) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initialize(lessonTypes);
            }
        });
    }

    public void addRow(LessonType lessonType) {
        tableModel.addRow(new Object[] { String.valueOf(lessonType.id), lessonType.code, lessonType.lessonTypeName });
    }

    public void addRows(List<LessonType> lessonTypes) {
        for (LessonType lessonType : lessonTypes) {
            addRow(lessonType);
        }
    }

    public void insertRow(LessonType lessonType, int index) {
        tableModel.insertRow(index,
                new Object[] { String.valueOf(lessonType.id), lessonType.code, lessonType.lessonTypeName });
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