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

    public Schedule() {
    }

    public Schedule(int lessonId, int periods, Date startDate, Time startTime, Time endTime, int startWeekday,
            int weekIndex, int roomId) {
        this.lessonId = lessonId;
        this.periods = periods;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startWeekday = startWeekday;
        this.weekIndex = weekIndex;
        this.roomId = roomId;
    }

    public String getTimeInfoString() {
        return String.join("~", startTime.toString().substring(0, 5), endTime.toString().substring(0, 5));
    }
}