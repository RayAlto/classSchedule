package top.rayalto.classSchedule;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.UIManager;

import top.rayalto.classSchedule.dataTypes.UserConfig;
import top.rayalto.classSchedule.frames.LoginFrame;
import top.rayalto.classSchedule.frames.MainFrame;

public class ClassSchedule {

    private LoginFrame loginFrame = null;
    private MainFrame mainFrame = null;

    public ClassSchedule() {
        loginFrame = new LoginFrame("登陆");
        loginFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                synchronized (UserConfig.loginLock) {
                    loginFrame.setVisible(false);
                    UserConfig.loginLock.notify();
                }
            }
        });
        Thread loginThread = new Thread() {
            @Override
            public void run() {
                synchronized (UserConfig.loginLock) {
                    while (loginFrame.isVisible()) {
                        try {
                            UserConfig.loginLock.wait();
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
        } catch (InterruptedException ignore) {
        }
        if (!UserConfig.logged) {
            System.out.println("not logged in, about to exit");
            System.exit(0);
        }
        mainFrame = new MainFrame("Class Schedule");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {
        }
        new ClassSchedule();
    }
}