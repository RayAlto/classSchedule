package top.rayalto.classSchedule.tables;

import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.Lesson;

public class LessonTable extends JTable {
    private static final long serialVersionUID = 1L;

    private DefaultTableModel tableModel = (DefaultTableModel) getModel();
    private TableColumnModel tableColumnModel = getColumnModel();

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    private void initialize(List<Lesson> lessons) {
        setFont(Sources.NOTO_SANS_MONO_FONT);
        getTableHeader().setFont(Sources.NOTO_SANS_MONO_FONT);
        tableModel.addColumn("ID");
        tableModel.addColumn("教学班代码");
        tableModel.addColumn("课程代码");
        tableModel.addColumn("课程类型ID");
        tableModel.addColumn("排课标志");
        tableModel.addColumn("中文名");
        tableModel.addColumn("英文名");
        tableModel.addColumn("开课学院ID");
        tableModel.addColumn("总学时");
        tableModel.addColumn("学生数");
        tableModel.addColumn("课程安排");
        tableModel.addColumn("考试模式ID");
        tableColumnModel.getColumn(0).setPreferredWidth(60);
        tableColumnModel.getColumn(1).setPreferredWidth(105);
        tableColumnModel.getColumn(2).setPreferredWidth(75);
        tableColumnModel.getColumn(3).setPreferredWidth(75);
        tableColumnModel.getColumn(4).setPreferredWidth(60);
        tableColumnModel.getColumn(5).setPreferredWidth(100);
        tableColumnModel.getColumn(6).setPreferredWidth(100);
        tableColumnModel.getColumn(7).setPreferredWidth(75);
        tableColumnModel.getColumn(8).setPreferredWidth(45);
        tableColumnModel.getColumn(9).setPreferredWidth(45);
        tableColumnModel.getColumn(10).setPreferredWidth(100);
        tableColumnModel.getColumn(11).setPreferredWidth(75);
        if (lessons != null) {
            for (Lesson lesson : lessons) {
                addRow(lesson);
            }
        }
    }

    public LessonTable() {
        this(null);
    }

    public LessonTable(List<Lesson> lessons) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initialize(lessons);
            }
        });
    }

    public void addRow(Lesson lesson) {
        tableModel.addRow(new Object[] { String.valueOf(lesson.id), lesson.classCode, lesson.code,
                String.valueOf(lesson.typeId), lesson.flag, lesson.nameZh, lesson.nameEn,
                String.valueOf(lesson.departmentId), String.valueOf(lesson.periodTotal),
                String.valueOf(lesson.studentCount), lesson.scheduleText, String.valueOf(lesson.examModeId) });
    }

    public void addRows(List<Lesson> lessons) {
        for (Lesson lesson : lessons) {
            addRow(lesson);
        }
    }

    public void insertRow(Lesson lesson, int index) {
        tableModel.insertRow(index,
                new Object[] { String.valueOf(lesson.id), lesson.classCode, lesson.code, String.valueOf(lesson.typeId),
                        lesson.flag, lesson.nameZh, lesson.nameEn, String.valueOf(lesson.departmentId),
                        String.valueOf(lesson.periodTotal), String.valueOf(lesson.studentCount), lesson.scheduleText,
                        String.valueOf(lesson.examModeId) });
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