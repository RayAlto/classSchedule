package top.rayalto.classSchedule.frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import top.rayalto.classSchedule.Sources;
import top.rayalto.classSchedule.dataTypes.UserConfig;
import top.rayalto.classSchedule.database.DatabaseEntity;

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
        rememberUsernameCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    UserConfig.setConfig("login.rememberUsername", "true");
                    System.out.println("set remember user name: true");
                } else {
                    UserConfig.setConfig("login.rememberUsername", "false");
                    System.out.println("set remember user name: false");
                }
                UserConfig.save();
            }
        });
        rememberPasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    UserConfig.setConfig("login.rememberPassword", "true");
                    System.out.println("set remember password: true");
                } else {
                    UserConfig.setConfig("login.rememberPassword", "false");
                    System.out.println("set remember password: false");
                }
                UserConfig.save();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (DatabaseEntity.login(usernameTextField.getText(),
                        String.valueOf(passwordTextField.getPassword()))) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "欢迎使用", "登陆成功", JOptionPane.INFORMATION_MESSAGE);
                    UserConfig.setConfig("user.username", usernameTextField.getText());
                    UserConfig.setConfig("user.password", String.valueOf(passwordTextField.getPassword()));
                    UserConfig.save();
                    UserConfig.logged = true;
                    dispatchEvent(new WindowEvent(LoginFrame.this, WindowEvent.WINDOW_CLOSING));
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "学号和密码不匹配或连接池中没有可用连接", "登陆失败",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                synchronized (UserConfig.loginLock) {
                    setVisible(false);
                    UserConfig.loginLock.notify();
                }
            }
        });
        setLocation((screenSize.width - loginFrameSize.width) / 2, (screenSize.height - loginFrameSize.height) / 2);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                loginButton.setEnabled(false);
                loginButton.setText("正在建立连接 ...");
                DatabaseEntity.initializeConnectionPool();
                return null;
            }

            @Override
            protected void done() {
                loginButton.setEnabled(true);
                loginButton.setText("登陆");
            }
        }.execute();
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                rememberUsernameCheckBox.setEnabled(false);
                rememberPasswordCheckBox.setEnabled(false);
                UserConfig.initializeUserConfig();
                return null;
            }

            @Override
            protected void done() {
                if (UserConfig.getConfig("login.rememberUsername") != null
                        && UserConfig.getConfig("login.rememberUsername").equals("true")) {
                    usernameTextField.setText(UserConfig.getConfig("user.username"));
                    rememberUsernameCheckBox.setSelected(true);
                    rememberUsernameCheckBox.setEnabled(true);
                }
                if (UserConfig.getConfig("login.rememberUsername") != null
                        && UserConfig.getConfig("login.rememberUsername").equals("true")) {
                    passwordTextField.setText(UserConfig.getConfig("user.password"));
                    rememberPasswordCheckBox.setSelected(true);
                    rememberPasswordCheckBox.setEnabled(true);
                }
            }
        }.execute();
    }
}