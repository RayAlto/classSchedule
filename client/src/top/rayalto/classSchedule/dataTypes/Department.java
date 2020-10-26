package top.rayalto.classSchedule.dataTypes;

public class Department {
    public int id;
    public String code;
    public String departmentName;

    public Department() {
    }

    public Department(int id, String code, String departmentName) {
        this.id = id;
        this.code = code;
        this.departmentName = departmentName;
    }
}