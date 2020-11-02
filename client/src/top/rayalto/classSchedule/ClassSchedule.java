package top.rayalto.classSchedule;

import javax.swing.UIManager;

import top.rayalto.classSchedule.frames.LoginFrame;

public class ClassSchedule {

    public ClassSchedule() {
        new LoginFrame();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {
        }
        new ClassSchedule();
    }
}