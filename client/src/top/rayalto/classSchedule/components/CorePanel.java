package top.rayalto.classSchedule.components;

import javax.swing.JPanel;

import java.awt.CardLayout;

public class CorePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private SchedulePanel schedulePanel = new SchedulePanel();
    private final String SCHEDULE_PANEL = "schedulePanel";

    public CorePanel() {
        setLayout(new CardLayout());
        add(schedulePanel, SCHEDULE_PANEL);
    }
}
