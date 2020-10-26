package top.rayalto.classSchedule.dataTypes;

public class SchoolClass {
    public int id;
    public String code;
    public String className;
    public String grade;
    public int studentCount;

    public SchoolClass() {
    }

    public SchoolClass(int id, String code, String className, String grade, int studentCount) {
        this.id = id;
        this.code = code;
        this.className = className;
        this.grade = grade;
        this.studentCount = studentCount;
    }
}