package top.rayalto.classSchedule.dataTypes;

public class Lesson {
    public int id;
    public String classCode;
    public String code;
    public int typeId;
    public String flag;
    public String nameZh;
    public String nameEn;
    public int departmentId;
    public int periodTotal;
    public int studentCount;
    public String scheduleText;
    public int examModeId;

    public Lesson() {
    }

    public Lesson(int id, String classCode, String code, int typeId, String flag, String nameZh, String nameEn,
            int departmentId, int periodTotal, int studentCount, String scheduleText, int examModeId) {
        this.id = id;
        this.classCode = classCode;
        this.code = code;
        this.typeId = typeId;
        this.flag = flag;
        this.nameZh = nameZh;
        this.nameEn = nameEn;
        this.departmentId = departmentId;
        this.periodTotal = periodTotal;
        this.studentCount = studentCount;
        this.scheduleText = scheduleText;
        this.examModeId = examModeId;
    }
}