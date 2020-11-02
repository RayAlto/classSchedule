package top.rayalto.classSchedule.components;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.Lesson;
import top.rayalto.classSchedule.database.DatabaseEntity;

import java.awt.CardLayout;
import java.awt.GridLayout;

public class CorePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final String SCHEDULE_PANEL = "schedulePanel";
    public static final String LESSON_PANEL = "lessonPanel";

    private CardLayout card = new CardLayout();

    private SchedulePanel schedulePanel = new SchedulePanel();

    private JPanel lessonsListPanel = new JPanel(new GridLayout(1, 0));
    private DefaultTableModel lessonsListTableModel = new DefaultTableModel() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable lessonsListTable = new JTable(lessonsListTableModel);
    private JScrollPane lessonsScrollPane = new JScrollPane(lessonsListTable);

    public CorePanel() {
        setLayout(card);
        add(schedulePanel, SCHEDULE_PANEL);
        lessonsListTable.setFont(Sources.NOTO_SANS_MONO_FONT);
        lessonsListTable.getTableHeader().setFont(Sources.NOTO_SANS_MONO_FONT);
        lessonsListPanel.add(lessonsScrollPane);
        lessonsListTableModel.addColumn("ID");
        lessonsListTableModel.addColumn("教学班代码");
        lessonsListTableModel.addColumn("课程代码");
        lessonsListTableModel.addColumn("课程类型ID");
        lessonsListTableModel.addColumn("排课标志");
        lessonsListTableModel.addColumn("中文名");
        lessonsListTableModel.addColumn("英文名");
        lessonsListTableModel.addColumn("开课学院ID");
        lessonsListTableModel.addColumn("总学时");
        lessonsListTableModel.addColumn("学生数");
        lessonsListTableModel.addColumn("课程安排");
        lessonsListTableModel.addColumn("考试模式ID");
        TableColumnModel lessonsColumnModel = lessonsListTable.getColumnModel();
        lessonsColumnModel.getColumn(0).setPreferredWidth(60);
        lessonsColumnModel.getColumn(1).setPreferredWidth(105);
        lessonsColumnModel.getColumn(2).setPreferredWidth(75);
        lessonsColumnModel.getColumn(3).setPreferredWidth(75);
        lessonsColumnModel.getColumn(4).setPreferredWidth(60);
        lessonsColumnModel.getColumn(5).setPreferredWidth(100);
        lessonsColumnModel.getColumn(6).setPreferredWidth(100);
        lessonsColumnModel.getColumn(7).setPreferredWidth(75);
        lessonsColumnModel.getColumn(8).setPreferredWidth(45);
        lessonsColumnModel.getColumn(9).setPreferredWidth(45);
        lessonsColumnModel.getColumn(10).setPreferredWidth(100);
        lessonsColumnModel.getColumn(11).setPreferredWidth(75);
        for (Lesson lesson : DatabaseEntity.getLessons()) {
            lessonsListTableModel.addRow(new Object[] { String.valueOf(lesson.id), lesson.classCode, lesson.code,
                    String.valueOf(lesson.typeId), lesson.flag, lesson.nameZh, lesson.nameEn,
                    String.valueOf(lesson.departmentId), String.valueOf(lesson.periodTotal),
                    String.valueOf(lesson.studentCount), lesson.scheduleText, String.valueOf(lesson.examModeId) });
        }
        add(lessonsListPanel, LESSON_PANEL);
    }

    public void showTab(String name) {
        card.show(this, name);
    }

    public void showSchedulesBetweenDate(String startDate, String endDate) {
        schedulePanel.useDate(startDate, endDate);
    }
}
