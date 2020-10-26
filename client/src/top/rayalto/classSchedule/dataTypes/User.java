package top.rayalto.classSchedule.dataTypes;

public class User {
    public String code;
    public String passwordHash;
    public String realName;
    public String gender;
    public String className;
    public String majorName;
    public String departmentName;
    public String email;
    public String homeAddress;
    public String mobilePhone;

    public User() {
    }

    public User(String code, String passwordHash, String realName, String gender, String className, String majorName,
            String departmentName, String email, String homeAddress, String mobilePhone) {
        this.code = code;
        this.passwordHash = passwordHash;
        this.realName = realName;
        this.gender = gender;
        this.className = className;
        this.majorName = majorName;
        this.departmentName = departmentName;
        this.email = email;
        this.homeAddress = homeAddress;
        this.mobilePhone = mobilePhone;
    }
}