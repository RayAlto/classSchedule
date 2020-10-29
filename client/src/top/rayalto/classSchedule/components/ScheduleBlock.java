package top.rayalto.classSchedule.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTextArea;

import top.rayalto.classSchedule.Sources;

class DarkTextArea extends JTextArea {
    private static final long serialVersionUID = 1L;

    public DarkTextArea() {
        this(null);
    }

    public DarkTextArea(String text) {
        super(text);
        setOpaque(false);
        setForeground(Color.DARK_GRAY);
        setFont(Sources.NOTO_SANS_MONO_FONT);
        setEditable(false);
        setLineWrap(true);
    }

    @Override
    public FontMetrics getFontMetrics(Font font) {
        return new FontMetrics(font) {
            private static final long serialVersionUID = 1L;
            @Override
            public int getHeight() {
                return font.getSize();
            }
        };
    }
}

public class ScheduleBlock extends JButton {
    private static final long serialVersionUID = 1L;

    private final MouseAdapter CLICK_AT_BUTTON = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                for (ActionListener a : ScheduleBlock.this.getActionListeners()) {
                    a.actionPerformed(new ActionEvent(ScheduleBlock.this, ActionEvent.ACTION_PERFORMED, null));
                }
            }
        }
    };
    private DarkTextArea lessonNameTextArea = new DarkTextArea();
    private DarkTextArea roomNameTextArea = new DarkTextArea();
    private DarkTextArea timeInfoTextArea = new DarkTextArea();

    public ScheduleBlock(String lessonName, String roomName, String timeInfo) {
        setBorder(null);
        setContentAreaFilled(false);
        lessonNameTextArea.setText(lessonName);
        roomNameTextArea.setText(roomName);
        timeInfoTextArea.setText(timeInfo);
        setLayout(new GridLayout(3, 0, 0, 0));
        add(lessonNameTextArea);
        add(roomNameTextArea);
        add(timeInfoTextArea);
        lessonNameTextArea.addMouseListener(CLICK_AT_BUTTON);
        roomNameTextArea.addMouseListener(CLICK_AT_BUTTON);
        timeInfoTextArea.addMouseListener(CLICK_AT_BUTTON);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint background = new GradientPaint(0, 0, new Color(218, 226, 248), 0, getHeight(),
                new Color(214, 164, 164));
        g2d.setPaint(background);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        super.paintComponent(g);
    }
}
