package top.rayalto.classSchedule.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import top.rayalto.classSchedule.Sources;

public class SideBar extends JPanel {

    private static final long serialVersionUID = 1L;

    public JLabel sideBarBanner = new JLabel(Sources.SIDEBAR_BANNER_UNFOLF_IMAGE);
    public JPanel buttonsPanel = new JPanel(new GridLayout(11, 0));
    public JScrollPane scrollButtonsPanel = new JScrollPane(buttonsPanel);
    public SideBarButton homeButton = new SideBarButton("主　　页");
    public SideBarButton scheduleButton = new SideBarButton("课程时间");
    public SideBarButton lessonButton = new SideBarButton("课程列表");
    public SideBarButton classmateButton = new SideBarButton("同学列表");
    public SideBarButton teacherButton = new SideBarButton("教师列表");
    public SideBarButton classButton = new SideBarButton("班级列表");
    public SideBarButton departmentButton = new SideBarButton("学院列表");
    public SideBarButton roomButton = new SideBarButton("教室列表");
    public SideBarButton lessonTypeButton = new SideBarButton("课程类型");
    public SideBarButton examModeButton = new SideBarButton("考试模式");
    public SideBarButton userButton = new SideBarButton("用户信息");

    private boolean _folded = false;

    private void initialize(){
        setBackground(Color.DARK_GRAY);
        buttonsPanel.setBackground(Color.DARK_GRAY);
        scrollButtonsPanel.setBackground(Color.DARK_GRAY);
        setLayout(null);
        homeButton.setIcon(Sources.SIDEBAR_HOME_ICON);
        scheduleButton.setIcon(Sources.SIDEBAR_SCHEDULE_ICON_IMAGE);
        lessonButton.setIcon(Sources.SIDEBAR_LESSON_ICON_IMAGE);
        classmateButton.setIcon(Sources.SIDEBAR_CLASSMATE_ICON_IMAGE);
        teacherButton.setIcon(Sources.SIDEBAR_TEACHER_ICON_IMAGE);
        classButton.setIcon(Sources.SIDEBAR_CLASS_ICON_IMAGE);
        departmentButton.setIcon(Sources.SIDEBAR_DEPARTMENT_ICON_IMAGE);
        roomButton.setIcon(Sources.SIDEBAR_ROOM_ICON_IMAGE);
        lessonTypeButton.setIcon(Sources.SIDEBAR_LESSON_TYPE_ICON_IMAGE);
        examModeButton.setIcon(Sources.SIDEBAR_EXAM_MODE_ICON_IMAGE);
        userButton.setIcon(Sources.SIDEBAR_USER_ICON_IMAGE);
        sideBarBanner.setToolTipText("鼠标左键单击可以收起/展开侧边栏");
        homeButton.setToolTipText("主页");
        scheduleButton.setToolTipText("课程表，可以查看课程的安排");
        lessonButton.setToolTipText("课程列表，可以查看你所选的所有课程的信息");
        classmateButton.setToolTipText("同学列表，可以查看你在课堂中可以遇见的所有同学");
        teacherButton.setToolTipText("教师列表，可以查看你在课堂中可以预见的所有老师");
        classButton.setToolTipText("班级列表，可以查看你可以遇见的所有同学所在的所有班级");
        departmentButton.setToolTipText("学院列表，可以查看你可以遇见的所有同学所在的所有学院");
        roomButton.setToolTipText("教室列表，可以查看你所选的所有课程上课教室");
        lessonTypeButton.setToolTipText("课程类型列表，可以查看你所选的所有课程的所有类型");
        examModeButton.setToolTipText("考试模式列表，可以查看你所选课程的所有考试模式");
        userButton.setToolTipText("用户信息，可以查看你的信息");
        add(sideBarBanner);
        sideBarBanner.setLocation(0, 0);
        sideBarBanner.setSize(230, 50);
        buttonsPanel.add(homeButton);
        buttonsPanel.add(scheduleButton);
        buttonsPanel.add(lessonButton);
        buttonsPanel.add(classmateButton);
        buttonsPanel.add(teacherButton);
        buttonsPanel.add(classButton);
        buttonsPanel.add(departmentButton);
        buttonsPanel.add(roomButton);
        buttonsPanel.add(lessonTypeButton);
        buttonsPanel.add(examModeButton);
        buttonsPanel.add(userButton);
        scrollButtonsPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollButtonsPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollButtonsPanel);
        scrollButtonsPanel.setLocation(0, 50);
    }

    public SideBar() {
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                initialize();
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension sideBarSize = getSize();
                sideBarBanner.setSize(sideBarSize.width, 50);
                scrollButtonsPanel.setSize(sideBarSize.width, sideBarSize.height > 702 ? 652 : sideBarSize.height - 50);
            }
        });
    }

    public boolean isFolded() {
        return _folded;
    }

    public void fold(boolean fold) {
        if (fold && !_folded) {
            sideBarBanner.setIcon(Sources.SIDEBAR_BANNER_FOLF_IMAGE);
            hideButtonText(true);
            _folded = true;
        } else if (!fold && _folded) {
            sideBarBanner.setIcon(Sources.SIDEBAR_BANNER_UNFOLF_IMAGE);
            hideButtonText(false);
            _folded = false;
        }
        Dimension sideBarSize = getSize();
        sideBarBanner.setSize(sideBarSize.width, 50);
        scrollButtonsPanel.setSize(sideBarSize.width, sideBarSize.height > 702 ? 652 : sideBarSize.height - 50);
    }

    public void hideButtonText(boolean hide) {
        if (hide) {
            homeButton.setText(null);
            scheduleButton.setText(null);
            lessonButton.setText(null);
            classmateButton.setText(null);
            teacherButton.setText(null);
            classButton.setText(null);
            departmentButton.setText(null);
            roomButton.setText(null);
            lessonTypeButton.setText(null);
            examModeButton.setText(null);
            userButton.setText(null);
        } else {
            homeButton.setText("主　　页");
            scheduleButton.setText("课程时间");
            lessonButton.setText("课程列表");
            classmateButton.setText("同学列表");
            teacherButton.setText("教师列表");
            classButton.setText("班级列表");
            departmentButton.setText("学院列表");
            roomButton.setText("教室列表");
            lessonTypeButton.setText("课程类型");
            examModeButton.setText("考试模式");
            userButton.setText("用户信息");
        }
    }
}