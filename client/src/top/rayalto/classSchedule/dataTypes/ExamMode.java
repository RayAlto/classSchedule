package top.rayalto.classSchedule.dataTypes;

public class ExamMode {
    public int id;
    public String code;
    public String examModeName;

    public ExamMode() {
    }

    public ExamMode(int id, String code, String examModeName) {
        this.id = id;
        this.code = code;
        this.examModeName = examModeName;
    }
}