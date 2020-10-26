package top.rayalto.classSchedule.dataTypes;

public class LessonType {
    public int id;
    public String code;
    public String lessonTypeName;

    public LessonType() {
    }

    public LessonType(int id, String code, String lessonTypeName) {
        this.id = id;
        this.code = code;
        this.lessonTypeName = lessonTypeName;
    }
}