package top.rayalto.classSchedule.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.ScheduleDetail;
import top.rayalto.classSchedule.database.DatabaseEntity;

public class SchedulePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final Dimension MINIMUN_SIZE = new Dimension(375, 430);
    private final int DAY_BEGIN = 8;
    private final int DAY_END = 22;

    private List<ScheduleBlock> scheduleBlocks = new ArrayList<ScheduleBlock>();

    private static final Map<Integer, String> index2WeekString = new HashMap<Integer, String>() {
        private static final long serialVersionUID = 1L;
        {
            put(0, "星期一");
            put(1, "星期二");
            put(2, "星期三");
            put(3, "星期四");
            put(4, "星期五");
            put(5, "星期六");
            put(6, "星期日");
        }
    };

    public SchedulePanel() {
        setLayout(null);
        setMinimumSize(MINIMUN_SIZE);
        for (ScheduleDetail scheduleDetail : DatabaseEntity.getScheduleDetail("2020-10-26")) {
            scheduleBlocks.add(new ScheduleBlock(scheduleDetail));
        }
        for (ScheduleBlock block : scheduleBlocks) {
            add(block);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Dimension panelSize = getSize();
        Dimension availableSize = new Dimension(panelSize.width - 55, panelSize.height - 41);
        Point startLocation = new Point(5, 30);
        float blockSizeWidth = (float) availableSize.width / 7.0f;
        float blockSizeHeight = (float) availableSize.height / 14.0f;
        float fontLocationY = 35;
        float fontLocationX = 50.0f + (blockSizeWidth - 45.0f) / 2.0f;
        GradientPaint backgroundGradientPaint = new GradientPaint(0f, 0f, new Color(35, 37, 38), panelSize.width, 0,
                new Color(65, 67, 69));
        Graphics2D g2dBackground = (Graphics2D) g.create();
        g2dBackground.setPaint(backgroundGradientPaint);
        g2dBackground.fillRect(0, 0, panelSize.width, panelSize.height);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(Sources.NOTO_SANS_MONO_FONT);
        g2d.setColor(Color.WHITE);
        g2d.drawRect(startLocation.x + 45, startLocation.y, availableSize.width, availableSize.height);
        for (int hourInt = DAY_BEGIN, rowIndex = 0; hourInt <= DAY_END; hourInt++, rowIndex++) {
            String timeString = null;
            if (hourInt < 10)
                timeString = "0" + hourInt + ":00";
            else
                timeString = hourInt + ":00";
            g2d.drawString(timeString, startLocation.x, rowIndex * blockSizeHeight + fontLocationY);
            float lineLocationY = startLocation.y + rowIndex * blockSizeHeight;
            Line2D.Float line = new Line2D.Float(startLocation.x + 45, lineLocationY,
                    startLocation.x + availableSize.width + 45, lineLocationY);
            g2d.draw(line);
        }
        for (int columnIndex = 0; columnIndex < 7; columnIndex++) {
            float lineLocationX = startLocation.x + 45 + columnIndex * blockSizeWidth;
            Line2D.Float line = new Line2D.Float(lineLocationX, startLocation.y, lineLocationX,
                    startLocation.y + availableSize.height);
            g2d.draw(line);
            g2d.drawString(index2WeekString.get(columnIndex), columnIndex * blockSizeWidth + fontLocationX, 25);

        }
        for (ScheduleBlock block : scheduleBlocks) {
            block.setBounds((int) (startLocation.x + 45 + block.getWeekDayValue() * blockSizeWidth),
                    (int) (startLocation.y + availableSize.height * block.getStartTimeValue()), (int) blockSizeWidth,
                    (int) (availableSize.height * block.getDurationValue()));
        }
        paintComponents(g);
    }
}
