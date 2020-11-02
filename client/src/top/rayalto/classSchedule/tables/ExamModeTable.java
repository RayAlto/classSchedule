package top.rayalto.classSchedule.tables;

import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.ExamMode;

public class ExamModeTable extends JTable {
    private static final long serialVersionUID = 1L;

    private DefaultTableModel tableModel = (DefaultTableModel) getModel();
    private TableColumnModel tableColumnModel = getColumnModel();

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public ExamModeTable() {
        setFont(Sources.NOTO_SANS_MONO_FONT);
        getTableHeader().setFont(Sources.NOTO_SANS_MONO_FONT);
        tableModel.addColumn("ID");
        tableModel.addColumn("代码");
        tableModel.addColumn("考试模式名");
        tableColumnModel.getColumn(0).setPreferredWidth(30);
        tableColumnModel.getColumn(1).setPreferredWidth(30);
        tableColumnModel.getColumn(2).setPreferredWidth(75);
    }

    public ExamModeTable(List<ExamMode> examModes) {
        this();
        for (ExamMode examMode : examModes) {
            addRow(examMode);
        }
    }

    public void addRow(ExamMode examMode) {
        tableModel.addRow(new Object[] { String.valueOf(examMode.id), examMode.code, examMode.examModeName });
    }

    public void addRows(List<ExamMode> examModes) {
        for (ExamMode examMode : examModes) {
            addRow(examMode);
        }
    }

    public void insertRow(ExamMode examMode, int index) {
        tableModel.insertRow(index, new Object[] { String.valueOf(examMode.id), examMode.code, examMode.examModeName });
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