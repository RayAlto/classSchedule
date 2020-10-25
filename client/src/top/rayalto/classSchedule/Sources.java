package top.rayalto.classSchedule;

import java.awt.Font;
import java.awt.FontFormatException;

import java.io.IOException;

import javax.swing.ImageIcon;

public class Sources {
    public static final ImageIcon LOGIN_BANNER_IMAGE = new ImageIcon(
            Sources.class.getResource("/res/images/loginBanner.png"));
    public static final ImageIcon SIDEBAR_BANNER_UNFOLF_IMAGE = new ImageIcon(
            Sources.class.getResource("/res/images/sidebarBannerUnfold.png"));
    public static final ImageIcon SIDEBAR_BANNER_FOLF_IMAGE = new ImageIcon(
            Sources.class.getResource("/res/images/sidebarBannerFold.png"));
    public static final ImageIcon SIDEBAR_HOME_ICON = new ImageIcon(
            Sources.class.getResource("/res/images/homeIcon.png"));
    public static final ImageIcon SIDEBAR_CLASS_ICON_IMAGE = new ImageIcon(
            Sources.class.getResource("/res/images/classIcon.png"));
    public static final ImageIcon SIDEBAR_CLASSMATE_ICON_IMAGE = new ImageIcon(
            Sources.class.getResource("/res/images/classmateIcon.png"));
    public static final ImageIcon SIDEBAR_DEPARTMENT_ICON_IMAGE = new ImageIcon(
            Sources.class.getResource("/res/images/departmentIcon.png"));
    public static final ImageIcon SIDEBAR_EXAM_MODE_ICON_IMAGE = new ImageIcon(
            Sources.class.getResource("/res/images/examModeIcon.png"));
    public static final ImageIcon SIDEBAR_LESSON_ICON_IMAGE = new ImageIcon(
            Sources.class.getResource("/res/images/lessonIcon.png"));
    public static final ImageIcon SIDEBAR_LESSON_TYPE_ICON_IMAGE = new ImageIcon(
            Sources.class.getResource("/res/images/lessonTypeIcon.png"));
    public static final ImageIcon SIDEBAR_ROOM_ICON_IMAGE = new ImageIcon(
            Sources.class.getResource("/res/images/roomIcon.png"));
    public static final ImageIcon SIDEBAR_SCHEDULE_ICON_IMAGE = new ImageIcon(
            Sources.class.getResource("/res/images/scheduleIcon.png"));
    public static final ImageIcon SIDEBAR_TEACHER_ICON_IMAGE = new ImageIcon(
            Sources.class.getResource("/res/images/teacherIcon.png"));
    public static final ImageIcon SIDEBAR_USER_ICON_IMAGE = new ImageIcon(
            Sources.class.getResource("/res/images/userIcon.png"));
    private static Font NOTO_SANS_MONO_FONT = null;

    private static boolean _created = false;

    public static final Font NOTO_SANS_MONO_FONT() {
        if (!_created) {
            try {
                NOTO_SANS_MONO_FONT = Font.createFont(Font.TRUETYPE_FONT,
                        Sources.class.getResourceAsStream("/res/fonts/NotoSansMonoCJKsc-Regular.otf"));
                NOTO_SANS_MONO_FONT = NOTO_SANS_MONO_FONT.deriveFont(15.0f);
            } catch (FontFormatException | IOException e) {
                e.printStackTrace();
            }
        }
        return NOTO_SANS_MONO_FONT;
    }
}
