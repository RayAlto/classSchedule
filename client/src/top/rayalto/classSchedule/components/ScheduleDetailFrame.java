package top.rayalto.classSchedule.components;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.Classmate;
import top.rayalto.classSchedule.dataTypes.ScheduleDetail;
import top.rayalto.classSchedule.dataTypes.SchoolClass;
import top.rayalto.classSchedule.dataTypes.Teacher;
import top.rayalto.classSchedule.database.DatabaseEntity;

public class ScheduleDetailFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Dimension SCREENSIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private ScheduleDetail scheduleDetail;

    private JTabbedPane tabbedPane = new JTabbedPane();

    private JPanel basicDetailPanel = new JPanel();

    private JLabel lessonNameLabel = new JLabel("课程名称:");
    private JLabel startDateLabel = new JLabel("日期:");
    private JLabel startTimeLabel = new JLabel("开始时间:");
    private JLabel endTimeLabel = new JLabel("结束时间:");
    private JLabel weekdayLabel = new JLabel("星期:");
    private JLabel roomNameLabel = new JLabel("教室:");
    private JLabel periodsLabel = new JLabel("课时数:");
    private JLabel weekIndexLabel = new JLabel("教学周:");
    private JLabel lessonClassCodeLabel = new JLabel("教学班:");
    private JLabel lessonTypeNameLabel = new JLabel("课程类型:");
    private JLabel examModeNameLabel = new JLabel("考试类型:");
    private JLabel departmentNameLabel = new JLabel("开课学院:");
    private JLabel studentCountLabel = new JLabel("学生总数:");
    private JLabel periodTotalLabel = new JLabel("课时总数:");
    private JLabel scheduleTextLabel = new JLabel("安排:");

    private JTextField lessonNameTextField = new JTextField();
    private JTextField startDateTextField = new JTextField();
    private JTextField startTimeTextField = new JTextField();
    private JTextField endTimeTextField = new JTextField();
    private JTextField weekdayTextField = new JTextField();
    private JTextField roomNameTextField = new JTextField();
    private JTextField periodsTextField = new JTextField();
    private JTextField weekIndexTextField = new JTextField();
    private JTextField lessonClassCodeTextField = new JTextField();
    private JTextField lessonTypeNameTextField = new JTextField();
    private JTextField examModeNameTextField = new JTextField();
    private JTextField departmentNameTextField = new JTextField();
    private JTextField studentCountTextField = new JTextField();
    private JTextField periodTotalTextField = new JTextField();
    private JTextField scheduleTextTextField = new JTextField();

    private JPanel classesListPanel = new JPanel(new GridLayout(1, 0));
    private DefaultTableModel classesListTableModel = new DefaultTableModel() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable classesListTable = new JTable(classesListTableModel);
    private JScrollPane classesListScrollPane = new JScrollPane(classesListTable);

    private JPanel teachersListPanel = new JPanel(new GridLayout(1, 0));
    private DefaultTableModel teachersListTableModel = new DefaultTableModel() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable teachersListTable = new JTable(teachersListTableModel);
    private JScrollPane teachersListScrollPane = new JScrollPane(teachersListTable);

    private JPanel classmatesListPanel = new JPanel(new GridLayout(1, 0));
    private DefaultTableModel classmatesListTableModel = new DefaultTableModel() {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private JTable classmatesListTable = new JTable(classmatesListTableModel);
    private JScrollPane classmatesListScrollPane = new JScrollPane(classmatesListTable);

    public ScheduleDetailFrame(ScheduleDetail scheduleDetail) {
        this.scheduleDetail = scheduleDetail;
        setSize(600, 550);
        setLocation((SCREENSIZE.width - getWidth()) / 2, (SCREENSIZE.height - getHeight()) / 2);
        setResizable(false);

        lessonNameLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        startDateLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        startTimeLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        endTimeLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        weekdayLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        roomNameLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        periodsLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        weekIndexLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        lessonClassCodeLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        lessonTypeNameLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        examModeNameLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        departmentNameLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        studentCountLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        periodTotalLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        scheduleTextLabel.setFont(Sources.NOTO_SANS_MONO_FONT);

        lessonNameTextField.setEditable(false);
        startDateTextField.setEditable(false);
        startTimeTextField.setEditable(false);
        endTimeTextField.setEditable(false);
        weekdayTextField.setEditable(false);
        roomNameTextField.setEditable(false);
        periodsTextField.setEditable(false);
        weekIndexTextField.setEditable(false);
        lessonClassCodeTextField.setEditable(false);
        lessonTypeNameTextField.setEditable(false);
        examModeNameTextField.setEditable(false);
        departmentNameTextField.setEditable(false);
        studentCountTextField.setEditable(false);
        periodTotalTextField.setEditable(false);
        scheduleTextTextField.setEditable(false);

        lessonNameTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        startDateTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        startTimeTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        endTimeTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        weekdayTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        roomNameTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        periodsTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        weekIndexTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        lessonClassCodeTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        lessonTypeNameTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        examModeNameTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        departmentNameTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        studentCountTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        periodTotalTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        scheduleTextTextField.setFont(Sources.NOTO_SANS_MONO_FONT);

        lessonNameTextField.setText(scheduleDetail.lessonInfo.nameZh);
        startDateTextField.setText(scheduleDetail.scheduleInfo.startDate.toString());
        startTimeTextField.setText(scheduleDetail.scheduleInfo.startTime.toString());
        endTimeTextField.setText(scheduleDetail.scheduleInfo.endTime.toString());
        weekdayTextField.setText(DatabaseEntity.index2WeekString.get(scheduleDetail.scheduleInfo.startWeekday));
        roomNameTextField.setText(scheduleDetail.roomInfo.roomName);
        periodsTextField.setText(String.valueOf(scheduleDetail.scheduleInfo.periods));
        weekIndexTextField.setText(String.valueOf(scheduleDetail.scheduleInfo.weekIndex));
        lessonClassCodeTextField.setText(scheduleDetail.lessonInfo.classCode);
        lessonTypeNameTextField.setText(scheduleDetail.lessonTypeInfo.lessonTypeName);
        examModeNameTextField.setText(scheduleDetail.examModeInfo.examModeName);
        departmentNameTextField.setText(scheduleDetail.departmentInfo.departmentName);
        studentCountTextField.setText(String.valueOf(scheduleDetail.lessonInfo.studentCount));
        periodTotalTextField.setText(String.valueOf(scheduleDetail.lessonInfo.periodTotal));
        scheduleTextTextField.setText(scheduleDetail.lessonInfo.scheduleText);

        basicDetailPanel.setLayout(null);
        basicDetailPanel.add(lessonNameLabel);
        lessonNameLabel.setBounds(5, 10, 75, 20);
        basicDetailPanel.add(startDateLabel);
        startDateLabel.setBounds(5, 40, 75, 20);
        basicDetailPanel.add(startTimeLabel);
        startTimeLabel.setBounds(5, 70, 75, 20);
        basicDetailPanel.add(endTimeLabel);
        endTimeLabel.setBounds(5, 100, 75, 20);
        basicDetailPanel.add(weekdayLabel);
        weekdayLabel.setBounds(5, 130, 75, 20);
        basicDetailPanel.add(roomNameLabel);
        roomNameLabel.setBounds(5, 160, 75, 20);
        basicDetailPanel.add(periodsLabel);
        periodsLabel.setBounds(5, 190, 75, 20);
        basicDetailPanel.add(weekIndexLabel);
        weekIndexLabel.setBounds(5, 220, 75, 20);
        basicDetailPanel.add(lessonClassCodeLabel);
        lessonClassCodeLabel.setBounds(5, 250, 75, 20);
        basicDetailPanel.add(lessonTypeNameLabel);
        lessonTypeNameLabel.setBounds(5, 280, 75, 20);
        basicDetailPanel.add(examModeNameLabel);
        examModeNameLabel.setBounds(5, 310, 75, 20);
        basicDetailPanel.add(departmentNameLabel);
        departmentNameLabel.setBounds(5, 340, 75, 20);
        basicDetailPanel.add(studentCountLabel);
        studentCountLabel.setBounds(5, 370, 75, 20);
        basicDetailPanel.add(periodTotalLabel);
        periodTotalLabel.setBounds(5, 400, 75, 20);
        basicDetailPanel.add(scheduleTextLabel);
        scheduleTextLabel.setBounds(5, 430, 75, 20);

        basicDetailPanel.add(lessonNameTextField);
        lessonNameTextField.setBounds(85, 5, 500, 30);
        basicDetailPanel.add(startDateTextField);
        startDateTextField.setBounds(85, 35, 500, 30);
        basicDetailPanel.add(startTimeTextField);
        startTimeTextField.setBounds(85, 65, 500, 30);
        basicDetailPanel.add(endTimeTextField);
        endTimeTextField.setBounds(85, 95, 500, 30);
        basicDetailPanel.add(weekdayTextField);
        weekdayTextField.setBounds(85, 125, 500, 30);
        basicDetailPanel.add(roomNameTextField);
        roomNameTextField.setBounds(85, 155, 500, 30);
        basicDetailPanel.add(periodsTextField);
        periodsTextField.setBounds(85, 185, 500, 30);
        basicDetailPanel.add(weekIndexTextField);
        weekIndexTextField.setBounds(85, 215, 500, 30);
        basicDetailPanel.add(lessonClassCodeTextField);
        lessonClassCodeTextField.setBounds(85, 245, 500, 30);
        basicDetailPanel.add(lessonTypeNameTextField);
        lessonTypeNameTextField.setBounds(85, 275, 500, 30);
        basicDetailPanel.add(examModeNameTextField);
        examModeNameTextField.setBounds(85, 305, 500, 30);
        basicDetailPanel.add(departmentNameTextField);
        departmentNameTextField.setBounds(85, 335, 500, 30);
        basicDetailPanel.add(studentCountTextField);
        studentCountTextField.setBounds(85, 365, 500, 30);
        basicDetailPanel.add(periodTotalTextField);
        periodTotalTextField.setBounds(85, 395, 500, 30);
        basicDetailPanel.add(scheduleTextTextField);
        scheduleTextTextField.setBounds(85, 425, 500, 30);

        tabbedPane.addTab("基本信息", basicDetailPanel);
        JLabel basicTabLabel = new JLabel("基本信息");
        basicTabLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        tabbedPane.setTabComponentAt(0, basicTabLabel);

        classesListPanel.add(classesListScrollPane);
        classesListTable.setFont(Sources.NOTO_SANS_MONO_FONT);
        classesListTable.getTableHeader().setFont(Sources.NOTO_SANS_MONO_FONT);
        classesListTable.setFillsViewportHeight(true);
        classesListTableModel.addColumn("ID");
        classesListTableModel.addColumn("代码");
        classesListTableModel.addColumn("名称");
        classesListTableModel.addColumn("年级");
        classesListTableModel.addColumn("学生数");
        TableColumnModel classesColumnModel = classesListTable.getColumnModel();
        classesColumnModel.getColumn(0).setPreferredWidth(40);
        classesColumnModel.getColumn(1).setPreferredWidth(105);
        classesColumnModel.getColumn(2).setPreferredWidth(150);
        classesColumnModel.getColumn(3).setPreferredWidth(40);
        classesColumnModel.getColumn(4).setPreferredWidth(40);
        for (SchoolClass schoolClass : scheduleDetail.classes) {
            classesListTableModel.addRow(new Object[] { String.valueOf(schoolClass.id), schoolClass.code,
                    schoolClass.className, schoolClass.grade, String.valueOf(schoolClass.studentCount) });
        }

        tabbedPane.add("班级列表", classesListPanel);
        JLabel classesListTabLabel = new JLabel("班级列表");
        classesListTabLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        tabbedPane.setTabComponentAt(0, classesListTabLabel);

        teachersListPanel.add(teachersListScrollPane);
        teachersListTable.setFont(Sources.NOTO_SANS_MONO_FONT);
        teachersListTable.getTableHeader().setFont(Sources.NOTO_SANS_MONO_FONT);
        teachersListTable.setFillsViewportHeight(true);
        teachersListTableModel.addColumn("ID");
        teachersListTableModel.addColumn("代码");
        teachersListTableModel.addColumn("雇佣类型");
        teachersListTableModel.addColumn("姓名");
        teachersListTableModel.addColumn("年龄");
        teachersListTableModel.addColumn("所属学院ID");
        teachersListTableModel.addColumn("所属学院名");
        teachersListTableModel.addColumn("职位");
        TableColumnModel teachersColumnModel = teachersListTable.getColumnModel();
        teachersColumnModel.getColumn(0).setPreferredWidth(40);
        teachersColumnModel.getColumn(1).setPreferredWidth(90);
        teachersColumnModel.getColumn(2).setPreferredWidth(75);
        teachersColumnModel.getColumn(3).setPreferredWidth(90);
        teachersColumnModel.getColumn(4).setPreferredWidth(30);
        teachersColumnModel.getColumn(5).setPreferredWidth(75);
        teachersColumnModel.getColumn(6).setPreferredWidth(105);
        teachersColumnModel.getColumn(7).setPreferredWidth(60);
        for (Teacher teacher : scheduleDetail.teachers) {
            teachersListTableModel.addRow(new Object[] { String.valueOf(teacher.id), teacher.code, teacher.hireType,
                    teacher.teacherName, String.valueOf(teacher.age), String.valueOf(teacher.departmentId),
                    teacher.departmentName, teacher.title });
        }

        tabbedPane.add("教师列表", teachersListPanel);
        JLabel teachersListTabLabel = new JLabel("教师列表");
        teachersListTabLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        tabbedPane.setTabComponentAt(0, teachersListTabLabel);

        classmatesListPanel.add(classmatesListScrollPane);
        classmatesListTable.setFont(Sources.NOTO_SANS_MONO_FONT);
        classmatesListTable.getTableHeader().setFont(Sources.NOTO_SANS_MONO_FONT);
        classmatesListTable.setFillsViewportHeight(true);
        classmatesListTableModel.addColumn("代码");
        classmatesListTableModel.addColumn("姓名");
        classmatesListTableModel.addColumn("性别");
        classmatesListTableModel.addColumn("班级ID");
        classmatesListTableModel.addColumn("班级名");
        classmatesListTableModel.addColumn("所属专业名");
        classmatesListTableModel.addColumn("所属学院ID");
        classmatesListTableModel.addColumn("所属学院名");
        TableColumnModel classmatesColumnModel = classmatesListTable.getColumnModel();
        classmatesColumnModel.getColumn(0).setPreferredWidth(90);
        classmatesColumnModel.getColumn(1).setPreferredWidth(90);
        classmatesColumnModel.getColumn(2).setPreferredWidth(30);
        classmatesColumnModel.getColumn(3).setPreferredWidth(45);
        classmatesColumnModel.getColumn(4).setPreferredWidth(100);
        classmatesColumnModel.getColumn(5).setPreferredWidth(105);
        classmatesColumnModel.getColumn(6).setPreferredWidth(75);
        classmatesColumnModel.getColumn(7).setPreferredWidth(105);
        for (Classmate classmate : scheduleDetail.classmates) {
            classmatesListTableModel.addRow(new Object[] { classmate.code, classmate.classmateName, classmate.gender,
                    String.valueOf(classmate.classId), classmate.className, classmate.majorName,
                    String.valueOf(classmate.departmentId), classmate.departmentName });
        }

        tabbedPane.add("同学列表", classmatesListPanel);
        JLabel classmaatesListTabLabel = new JLabel("同学列表");
        classmaatesListTabLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        tabbedPane.setTabComponentAt(0, classmaatesListTabLabel);

        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (e.getSource() instanceof JTabbedPane) {
                    int frameWidth = 600;
                    switch (tabbedPane.getSelectedIndex()) {
                        case 0:
                            frameWidth = 600;
                            break;
                        case 1:
                            frameWidth = 600;
                            break;
                        case 2:
                            frameWidth = 660;
                            break;
                        case 3:
                            frameWidth = 740;
                            break;
                        default:
                            break;
                    }
                    setSize(frameWidth, getHeight());
                }
            }
        });

        add(tabbedPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
