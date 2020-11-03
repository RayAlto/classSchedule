package top.rayalto.classSchedule.components;

import java.awt.CardLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import top.rayalto.classSchedule.dataTypes.UserConfig;
import top.rayalto.classSchedule.database.DatabaseEntity;
import top.rayalto.classSchedule.tables.ClassTable;
import top.rayalto.classSchedule.tables.ClassmateTable;
import top.rayalto.classSchedule.tables.DepartmentTable;
import top.rayalto.classSchedule.tables.ExamModeTable;
import top.rayalto.classSchedule.tables.LessonTable;
import top.rayalto.classSchedule.tables.LessonTypeTable;
import top.rayalto.classSchedule.tables.RoomTable;
import top.rayalto.classSchedule.tables.TeacherTable;

public class CorePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public static final String SCHEDULE_PANEL = "schedulePanel";
    public static final String LESSON_PANEL = "lessonPanel";
    public static final String CLASSMATE_PANEL = "classmatePanel";
    public static final String TEACHER_PANEL = "teacherPanel";
    public static final String CLASS_PANEL = "classPanel";
    public static final String DEPARTMENT_PANEL = "departmentPanel";
    public static final String ROOM_PANEL = "roomPanel";
    public static final String LESSON_TYPE_PANEL = "lessonTypePanel";
    public static final String EXAM_MODE_PANEL = "examModePanel";
    public static final String USER_PANEL = "userPanel";

    private CardLayout card = new CardLayout();

    private SchedulePanel schedulePanel = new SchedulePanel();

    private LessonTable lessonTable = new LessonTable();
    private JPanel lessonListPanel = new JPanel(new GridLayout(1, 0));
    private JScrollPane lessonScrollPane = new JScrollPane(lessonTable);

    private ClassmateTable classmateTable = new ClassmateTable();
    private JPanel classmateListPanel = new JPanel(new GridLayout(1, 0));
    private JScrollPane classmateScrollPane = new JScrollPane(classmateTable);

    private TeacherTable teacherTable = new TeacherTable();
    private JPanel teacherListPanel = new JPanel(new GridLayout(1, 0));
    private JScrollPane teacherScrollPane = new JScrollPane(teacherTable);

    private ClassTable classTable = new ClassTable();
    private JPanel classListPanel = new JPanel(new GridLayout(1, 0));
    private JScrollPane classScrollPane = new JScrollPane(classTable);

    private DepartmentTable departmentTable = new DepartmentTable();
    private JPanel departmentListPanel = new JPanel(new GridLayout(1, 0));
    private JScrollPane departmentScrollPane = new JScrollPane(departmentTable);

    private RoomTable roomTable = new RoomTable();
    private JPanel roomListPanel = new JPanel(new GridLayout(1, 0));
    private JScrollPane roomScrollPane = new JScrollPane(roomTable);

    private LessonTypeTable lessonTypeTable = new LessonTypeTable();
    private JPanel lessonTypeListPanel = new JPanel(new GridLayout(1, 0));
    private JScrollPane lessonTypeScrollPane = new JScrollPane(lessonTypeTable);

    private ExamModeTable examModeTable = new ExamModeTable();
    private JPanel examModeListPanel = new JPanel(new GridLayout(1, 0));
    private JScrollPane examModeScrollPane = new JScrollPane(examModeTable);

    private UserPanel userPanel = new UserPanel();

    private void initialize() {
        setLayout(card);
        add(schedulePanel, SCHEDULE_PANEL);
        lessonListPanel.add(lessonScrollPane);
        add(lessonListPanel, LESSON_PANEL);
        classmateListPanel.add(classmateScrollPane);
        add(classmateListPanel, CLASSMATE_PANEL);
        teacherListPanel.add(teacherScrollPane);
        add(teacherListPanel, TEACHER_PANEL);
        classListPanel.add(classScrollPane);
        add(classListPanel, CLASS_PANEL);
        departmentListPanel.add(departmentScrollPane);
        add(departmentListPanel, DEPARTMENT_PANEL);
        roomListPanel.add(roomScrollPane);
        add(roomListPanel, ROOM_PANEL);
        lessonTypeListPanel.add(lessonTypeScrollPane);
        add(lessonTypeListPanel, LESSON_TYPE_PANEL);
        examModeListPanel.add(examModeScrollPane);
        add(examModeListPanel, EXAM_MODE_PANEL);
        add(userPanel, USER_PANEL);
    }

    public CorePanel() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initialize();
            }
        });
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                lessonTable.addRows(DatabaseEntity.getLessons());
                return null;
            }
        }.execute();
        new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() {
                classmateTable.addRows(DatabaseEntity.getClassmates());
                return null;
            }
        }.execute();
        new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() {
                teacherTable.addRows(DatabaseEntity.getTeachers());
                return null;
            }
        }.execute();
        new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() {
                classTable.addRows(DatabaseEntity.getClasses());
                return null;
            }
        }.execute();
        new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() {
                departmentTable.addRows(DatabaseEntity.getDepartments());
                return null;
            }
        }.execute();
        new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() {
                roomTable.addRows(DatabaseEntity.getRooms());
                return null;
            }
        }.execute();
        new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() {
                lessonTypeTable.addRows(DatabaseEntity.getLessonTypes());
                return null;
            }
        }.execute();
        new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() {
                examModeTable.addRows(DatabaseEntity.getExamModes());
                return null;
            }
        }.execute();
        new SwingWorker<Void,Void>(){
            @Override
            protected Void doInBackground() {
                userPanel.setUser(DatabaseEntity.getUser(UserConfig.getConfig("user.username")));
                return null;
            }
        }.execute();
        // repaint();
    }

    public void showTab(String name) {
        card.show(this, name);
    }

    public void showSchedulesBetweenDate(String startDate, String endDate) {
        schedulePanel.useDate(startDate, endDate);
    }

    public String getScheduleStartDate() {
        return schedulePanel.getStartDate();
    }

    public String getScheduleEndDate() {
        return schedulePanel.getEndDate();
    }
}
