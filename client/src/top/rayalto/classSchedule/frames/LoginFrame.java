package top.rayalto.classSchedule.frames;

import top.rayalto.classSchedule.Sources;

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

    public LoginFrame(String title) {
        super(title);
        setLayout(null);
        setSize(loginFrameSize);
        add(loginBannerLabel);
        loginBannerLabel.setBounds(0, 0, 500, 100);
        add(usernameLabel);
        usernameLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        usernameLabel.setBounds(81, 100, 48, 30);
        add(passwordLabel);
        passwordLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        passwordLabel.setBounds(81, 130, 48, 30);
        add(usernameTextField);
        usernameTextField.setFont(Sources.NOTO_SANS_MONO_FONT);
        usernameTextField.setBounds(129, 103, 200, 24);
        usernameTextField.setToolTipText("学校教务系统账号的用户名，也就是学号，一般是10位数字。");
        add(passwordTextField);
        passwordLabel.setFont(Sources.NOTO_SANS_MONO_FONT);
        passwordTextField.setBounds(129, 133, 200, 24);
        passwordTextField.setToolTipText("学校教务系统账号的密码，一般是自己设置的。");
        add(rememberUsernameCheckBox);
        rememberUsernameCheckBox.setFont(Sources.NOTO_SANS_MONO_FONT);
        rememberUsernameCheckBox.setBounds(333, 103, 86, 24);
        add(rememberPasswordCheckBox);
        rememberPasswordCheckBox.setFont(Sources.NOTO_SANS_MONO_FONT);
        rememberPasswordCheckBox.setBounds(333, 133, 86, 24);
        add(loginButton);
        loginButton.setFont(Sources.NOTO_SANS_MONO_FONT);
        loginButton.setBounds(125, 170, 250, 50);
        setLocation((screenSize.width - loginFrameSize.width) / 2, (screenSize.height - loginFrameSize.height) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void waitForConnectionPool(boolean waitFor) {
        if (waitFor) {
            loginButton.setEnabled(false);
            loginButton.setText("正在建立连接 ...");
        } else {
            loginButton.setEnabled(true);
            loginButton.setText("登陆");
        }
    }
}
