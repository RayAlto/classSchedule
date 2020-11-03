package top.rayalto.classSchedule.components;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.User;

public class UserPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private User user = null;

    private JLabel codeLabel = new JLabel("学号:");
    private JLabel passwordHashLabel = new JLabel("密码哈希:");
    private JLabel realNameLabel = new JLabel("真实姓名:");
    private JLabel genderLabel = new JLabel("性别:");
    private JLabel classLabel = new JLabel("所在班级:");
    private JLabel majorLabel = new JLabel("所在专业:");
    private JLabel departmentLabel = new JLabel("所在学院:");
    private JLabel emailLabel = new JLabel("电子邮箱:");
    private JLabel homeAddressLabel = new JLabel("家庭地址:");
    private JLabel mobilePhoneLabel = new JLabel("手机号码:");

    private JTextField codeTextField = new JTextField();
    private JTextField passwordHashTextField = new JTextField();
    private JTextField realNameTextField = new JTextField();
    private JTextField genderTextField = new JTextField();
    private JTextField classTextField = new JTextField();
    private JTextField majorTextField = new JTextField();
    private JTextField departmentTextField = new JTextField();
    private JTextField emailTextField = new JTextField();
    private JTextField homeAddressTextField = new JTextField();
    private JTextField mobilePhoneTextField = new JTextField();

    private void initialize(User user) {
        codeLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        passwordHashLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        realNameLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        genderLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        classLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        majorLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        departmentLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        emailLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        homeAddressLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        mobilePhoneLabel.setFont(Sources.NOTO_SANS_MONO_FONT);

        codeTextField.setEditable(false);
        passwordHashTextField.setEditable(false);
        realNameTextField.setEditable(false);
        genderTextField.setEditable(false);
        classTextField.setEditable(false);
        majorTextField.setEditable(false);
        departmentTextField.setEditable(false);
        emailTextField.setEditable(false);
        homeAddressTextField.setEditable(false);
        mobilePhoneTextField.setEditable(false);

        codeTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        passwordHashTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        realNameTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        genderTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        classTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        majorTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        departmentTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        emailTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        homeAddressTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        mobilePhoneTextField.setFont(Sources.NOTO_SANS_MONO_FONT);

        setLayout(null);
        add(codeLabel);
        codeLabel.setBounds(5, 10, 75, 20);
        add(passwordHashLabel);
        passwordHashLabel.setBounds(5, 40, 75, 20);
        add(realNameLabel);
        realNameLabel.setBounds(5, 70, 75, 20);
        add(genderLabel);
        genderLabel.setBounds(5, 100, 75, 20);
        add(classLabel);
        classLabel.setBounds(5, 130, 75, 20);
        add(majorLabel);
        majorLabel.setBounds(5, 160, 75, 20);
        add(departmentLabel);
        departmentLabel.setBounds(5, 190, 75, 20);
        add(emailLabel);
        emailLabel.setBounds(5, 220, 75, 20);
        add(homeAddressLabel);
        homeAddressLabel.setBounds(5, 250, 75, 20);
        add(mobilePhoneLabel);
        mobilePhoneLabel.setBounds(5, 280, 75, 20);

        add(codeTextField);
        codeTextField.setBounds(85, 5, 500, 30);
        add(passwordHashTextField);
        passwordHashTextField.setBounds(85, 35, 500, 30);
        add(realNameTextField);
        realNameTextField.setBounds(85, 65, 500, 30);
        add(genderTextField);
        genderTextField.setBounds(85, 95, 500, 30);
        add(classTextField);
        classTextField.setBounds(85, 125, 500, 30);
        add(majorTextField);
        majorTextField.setBounds(85, 155, 500, 30);
        add(departmentTextField);
        departmentTextField.setBounds(85, 185, 500, 30);
        add(emailTextField);
        emailTextField.setBounds(85, 215, 500, 30);
        add(homeAddressTextField);
        homeAddressTextField.setBounds(85, 245, 500, 30);
        add(mobilePhoneTextField);
        mobilePhoneTextField.setBounds(85, 275, 500, 30);

        if (user != null) {
            setUser(user);
            updateUserInfo();
        }
    }

    public UserPanel() {
        this(null);
    }

    public UserPanel(User user) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initialize(user);
            }
        });
    }

    public void updateUserInfo() {
        codeTextField.setText(user.code);
        passwordHashTextField.setText(user.passwordHash);
        realNameTextField.setText(user.realName);
        genderTextField.setText(user.gender);
        classTextField.setText(user.className);
        majorTextField.setText(user.majorName);
        departmentTextField.setText(user.departmentName);
        emailTextField.setText(user.email);
        homeAddressTextField.setText(user.homeAddress);
        mobilePhoneTextField.setText(user.mobilePhone);
    }

    public void setUser(User user) {
        this.user = user;
        updateUserInfo();
    }
}
