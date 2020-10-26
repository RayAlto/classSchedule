package top.rayalto.classSchedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import top.rayalto.classSchedule.dataTypes.UserConfig;
import top.rayalto.classSchedule.database.DatabaseEntity;
import top.rayalto.classSchedule.frames.LoginFrame;
import top.rayalto.classSchedule.frames.MainFrame;

public class ClassSchedule {
    private UserConfig userConfig = null;

    private LoginFrame loginFrame = null;
    private Object loginLock = new Object();
    private boolean logged = false;

    private MainFrame mainFrame = null;

    public ClassSchedule() {
        userConfig = new UserConfig();
        loginFrame = new LoginFrame("登陆");
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                loginFrame.waitForConnectionPool(true);
                DatabaseEntity.initializeConnectionPool();
                return null;
            }

            @Override
            protected void done() {
                loginFrame.waitForConnectionPool(false);
            }
        }.execute();
        loginFrame.rememberUsernameCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    userConfig.setConfig("login.rememberUsername", "true");
                    System.out.println("set remember user name: true");
                } else {
                    userConfig.setConfig("login.rememberUsername", "false");
                    System.out.println("set remember user name: false");
                }
                userConfig.save();
            }
        });
        loginFrame.rememberPasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    userConfig.setConfig("login.rememberPassword", "true");
                    System.out.println("set remember password: true");
                } else {
                    userConfig.setConfig("login.rememberPassword", "false");
                    System.out.println("set remember password: false");
                }
                userConfig.save();
            }
        });
        if (userConfig.getConfig("login.rememberUsername") != null
                && userConfig.getConfig("login.rememberUsername").equals("true")) {
            loginFrame.usernameTextField.setText(userConfig.getConfig("user.username"));
            loginFrame.rememberUsernameCheckBox.setSelected(true);
        }
        if (userConfig.getConfig("login.rememberUsername") != null
                && userConfig.getConfig("login.rememberUsername").equals("true")) {
            loginFrame.passwordTextField.setText(userConfig.getConfig("user.password"));
            loginFrame.rememberPasswordCheckBox.setSelected(true);
        }
        loginFrame.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (DatabaseEntity.login(loginFrame.usernameTextField.getText(),
                        String.valueOf(loginFrame.passwordTextField.getPassword()))) {
                    JOptionPane.showMessageDialog(loginFrame, "欢迎使用", "登陆成功", JOptionPane.INFORMATION_MESSAGE);
                    userConfig.setConfig("user.username", loginFrame.usernameTextField.getText());
                    userConfig.setConfig("user.password", String.valueOf(loginFrame.passwordTextField.getPassword()));
                    userConfig.save();
                    logged = true;
                    loginFrame.dispatchEvent(new WindowEvent(loginFrame, WindowEvent.WINDOW_CLOSING));
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "学号和密码不匹配或连接池中没有可用连接", "登陆失败", JOptionPane.ERROR_MESSAGE);
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
        if (!logged) {
            System.out.println("not logged in, about to exit");
            System.exit(0);
        }
        mainFrame = new MainFrame("Class Schedule");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        new ClassSchedule();
    }
}
