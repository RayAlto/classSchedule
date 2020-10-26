package top.rayalto.classSchedule.dataTypes;

public class Classmate {
    public String code;
    public String classmateName;
    public String gender;
    public int classId;
    public String className;
    public String majorName;
    public int departmentId;
    public String departmentName;

    public Classmate() {
    }

    public Classmate(String code, String classmateName, String gender, int classId, String className, String majorName,
            int departmentId, String departmentName) {
        this.code = code;
        this.classmateName = classmateName;
        this.gender = gender;
        this.classId = classId;
        this.className = className;
        this.majorName = majorName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }
}