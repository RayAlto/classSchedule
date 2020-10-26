package top.rayalto.classSchedule.dataTypes;

public class Teacher {
    public int id;
    public String code;
    public String hireType;
    public String teacherName;
    public int age;
    public int departmentId;
    public String departmentName;
    public String title;

    public Teacher() {
    }

    public Teacher(int id, String code, String hireType, String teacherName, int age, int departmentId,
            String departmentName, String title) {
        this.id = id;
        this.code = code;
        this.hireType = hireType;
        this.teacherName = teacherName;
        this.age = age;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.title = title;
    }
}