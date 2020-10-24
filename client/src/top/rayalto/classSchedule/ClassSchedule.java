package top.rayalto.classSchedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class ClassSchedule {
    private UserConfig userConfig = null;
    private DatabaseEntity databaseEntity = new DatabaseEntity();

    private Object loginLock = new Object();

    public ClassSchedule() {
        userConfig = new UserConfig();
        LoginFrame loginFrame = new LoginFrame("登陆", userConfig);
        loginFrame.rememberUsernameCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    userConfig.setConfig("login.rememberUsername", "true");
                } else {
                    userConfig.setConfig("login.rememberUsername", "false");
                }
                userConfig.save();
            }
        });
        loginFrame.rememberPasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    userConfig.setConfig("login.rememberPassword", "true");
                } else {
                    userConfig.setConfig("login.rememberPassword", "false");
                }
                userConfig.save();
            }
        });
        if (userConfig.getConfig("login.rememberUsername").equals("true")) {
            loginFrame.usernameTextField.setText(userConfig.getConfig("user.username"));
            loginFrame.rememberUsernameCheckBox.setSelected(true);
        }
        if (userConfig.getConfig("login.rememberUsername").equals("true")) {
            loginFrame.passwordTextField.setText(userConfig.getConfig("user.password"));
            loginFrame.rememberPasswordCheckBox.setSelected(true);
        }
        loginFrame.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (databaseEntity.login(loginFrame.usernameTextField.getText(),
                        String.valueOf(loginFrame.passwordTextField.getPassword()))) {
                    JOptionPane.showMessageDialog(loginFrame, "欢迎使用", "登陆成功", JOptionPane.INFORMATION_MESSAGE);
                    userConfig.setConfig("user.username", loginFrame.usernameTextField.getText());
                    userConfig.setConfig("user.password", String.valueOf(loginFrame.passwordTextField.getPassword()));
                    userConfig.save();
                    loginFrame.dispatchEvent(new WindowEvent(loginFrame, WindowEvent.WINDOW_CLOSING));
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "学号和密码不匹配", "登陆失败", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        loginFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                synchronized (loginLock) {
                    loginFrame.setVisible(false);
                    loginLock.notify();
                }
            }
        });

        Thread loginThread = new Thread() {
            @Override
            public void run() {
                synchronized (loginLock) {
                    while (loginFrame.isVisible()) {
                        try {
                            loginLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        loginThread.start();
        try {
            loginThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("时间开始流动");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        // new ClassSchedule();
        new MainFrame();
    }
}
