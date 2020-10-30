package top.rayalto.classSchedule.dataTypes;

import java.util.List;

public class ScheduleDetail {
    public Schedule scheduleInfo;
    public Lesson lessonInfo;
    public List<SchoolClass> classes;
    public List<Teacher> teachers;
    public Room roomInfo;
    public List<Classmate> classmates;
    public LessonType lessonTypeInfo;
    public Department departmentInfo;
    public ExamMode examModeInfo;

    public ScheduleDetail() {
    }

    public ScheduleDetail(Schedule scheduleInfo, Room roomInfo, Lesson lessonInfo, LessonType lessonTypeInfo,
            Department departmentInfo, ExamMode examModeInfo, List<SchoolClass> classes, List<Teacher> teachers,
            List<Classmate> classmates) {
        this.scheduleInfo = scheduleInfo;
        this.lessonInfo = lessonInfo;
        this.classes = classes;
        this.teachers = teachers;
        this.roomInfo = roomInfo;
        this.classmates = classmates;
        this.lessonTypeInfo = lessonTypeInfo;
        this.departmentInfo = departmentInfo;
        this.examModeInfo = examModeInfo;
    }
}