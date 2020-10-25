package top.rayalto.classSchedule.dataTypes;

import java.sql.Date;
import java.sql.Time;

public class Schedule {
    public int lessonId;
    public int periods;
    public Date startDate;
    public Time startTime;
    public Time endTime;
    public int startWeekday;
    public int weekIndex;
    public int roomId;
}