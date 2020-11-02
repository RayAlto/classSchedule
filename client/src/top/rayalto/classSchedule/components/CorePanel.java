package top.rayalto.classSchedule.components;

import javax.swing.JPanel;

import java.awt.CardLayout;

public class CorePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public static final String SCHEDULE_PANEL = "schedulePanel";

    private CardLayout card = new CardLayout();

    private SchedulePanel schedulePanel = new SchedulePanel();

    public CorePanel() {
        setLayout(card);
        add(schedulePanel, SCHEDULE_PANEL);
    }

    public void showTab(String name) {
        card.show(this, name);
    }

    public void showSchedulesBetweenDate(String startDate, String endDate) {
        schedulePanel.useDate(startDate, endDate);
    }
}
