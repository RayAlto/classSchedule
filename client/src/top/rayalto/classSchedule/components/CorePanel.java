package top.rayalto.classSchedule.components;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.Lesson;
import top.rayalto.classSchedule.database.DatabaseEntity;
import top.rayalto.classSchedule.tables.LessonTable;

import java.awt.CardLayout;
import java.awt.GridLayout;

public class CorePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final String SCHEDULE_PANEL = "schedulePanel";
    public static final String LESSON_PANEL = "lessonPanel";

    private CardLayout card = new CardLayout();

    private SchedulePanel schedulePanel = new SchedulePanel();

    private LessonTable lessonTable = new LessonTable(DatabaseEntity.getLessons());
    private JPanel lessonListPanel = new JPanel(new GridLayout(1, 0));
    private JScrollPane lessonScrollPane = new JScrollPane(lessonTable);

    public CorePanel() {
        setLayout(card);
        add(schedulePanel, SCHEDULE_PANEL);
        lessonListPanel.add(lessonScrollPane);
        add(lessonListPanel, LESSON_PANEL);
    }

    public void showTab(String name) {
        card.show(this, name);
    }

    public void showSchedulesBetweenDate(String startDate, String endDate) {
        schedulePanel.useDate(startDate, endDate);
    }
}
