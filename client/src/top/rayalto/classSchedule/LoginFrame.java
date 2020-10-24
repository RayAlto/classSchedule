package top.rayalto.classSchedule;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private Dimension loginFrameSize = new Dimension(500, 270);
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public JLabel loginBannerLabel = new JLabel(Sources.LOGIN_BANNER_IMAGE);
    public JLabel usernameLabel = new JLabel("学号：");
    public JLabel passwordLabel = new JLabel("密码：");
    public JTextField usernameTextField = new JTextField();
    public JPasswordField passwordTextField = new JPasswordField();
    public JCheckBox rememberUsernameCheckBox = new JCheckBox("记住学号");
    public JCheckBox rememberPasswordCheckBox = new JCheckBox("记住密码");
    public JButton loginButton = new JButton("登陆");

    public LoginFrame(String title, UserConfig userConfig) {
        super(title);
        setLayout(null);
        setSize(loginFrameSize);
        add(loginBannerLabel);
        loginBannerLabel.setBounds(0, 0, 500, 100);
        add(usernameLabel);
        usernameLabel.setBounds(81, 100, 48, 30);
        add(passwordLabel);
        passwordLabel.setBounds(81, 130, 48, 30);
        add(usernameTextField);
        usernameTextField.setBounds(129, 103, 200, 24);
        usernameTextField.setToolTipText("学校教务系统账号的用户名，也就是学号，一般是10位数字。");
        add(passwordTextField);
        passwordTextField.setBounds(129, 133, 200, 24);
        passwordTextField.setToolTipText("学校教务系统账号的密码，一般是自己设置的。");
        add(rememberUsernameCheckBox);
        rememberUsernameCheckBox.setBounds(333, 103, 86, 24);
        add(rememberPasswordCheckBox);
        rememberPasswordCheckBox.setBounds(333, 133, 86, 24);
        add(loginButton);
        loginButton.setBounds(125, 170, 250, 50);
        setLocation((screenSize.width - loginFrameSize.width) / 2, (screenSize.height - loginFrameSize.height) / 2);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
