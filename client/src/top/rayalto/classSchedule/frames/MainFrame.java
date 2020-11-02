package top.rayalto.classSchedule.frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import top.rayalto.classSchedule.components.CorePanel;
import top.rayalto.classSchedule.components.SideBar;
import top.rayalto.classSchedule.database.DatabaseEntity;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private static final Dimension mainFrameSize = new Dimension(900, 739);
    private static final Dimension mainFrameMinimunSize = new Dimension(605, 465);
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private SideBar sideBar = new SideBar();

    private CorePanel corePanel = new CorePanel();

    public MainFrame(String title) {
        setTitle(title);
        setMinimumSize(mainFrameMinimunSize);
        setLayout(null);
        setSize(mainFrameSize);
        setLocation((screenSize.width - mainFrameSize.width) / 2, (screenSize.height - mainFrameSize.height) / 2);
        add(sideBar);
        sideBar.setBounds(0, 0, 230, 702);
        add(corePanel);
        corePanel.setLocation(230, 0);
        corePanel.setSize(670, 702);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension frameSize = getSize();
                int sideBarWidth = sideBar.isFolded() ? 75 : 230;
                sideBar.setSize(sideBarWidth, frameSize.height - 37);
                int corePanelPositionX = sideBar.isFolded() ? 75 : 230;
                corePanel.setBounds(corePanelPositionX, 0, frameSize.width - sideBarWidth, frameSize.height - 37);
            }
        });
        sideBar.sideBarBanner.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // mouse left button
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (sideBar.isFolded())
                        foldSideBar(false);
                    else
                        foldSideBar(true);
                }
                dispatchEvent(new ComponentEvent(MainFrame.this, ComponentEvent.COMPONENT_RESIZED));
            }
        });
        sideBar.homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                corePanel.showTab(CorePanel.SCHEDULE_PANEL);
            }
        });
        sideBar.scheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newDateString;
                boolean conformed = false;
                java.util.Date parsedDate = null;
                while (!conformed) {
                    newDateString = (String) JOptionPane.showInputDialog(MainFrame.this,
                            "输入查询的日期，可以是一周的某一天，\n如果输入的是周末则会自动跳转到下一周\n格式: YYYY-MM-DD", "跳转",
                            JOptionPane.QUESTION_MESSAGE, null, null, "");
                    if (newDateString == null) {
                        return;
                    } else {
                        parsedDate = DatabaseEntity.isValidDateString(newDateString);
                        if (parsedDate != null)
                            conformed = true;
                        else
                            JOptionPane.showMessageDialog(MainFrame.this, "输入日期无效", "解析失败", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (conformed) {
                    String[] weekStartEndDateStrings = DatabaseEntity.getWeekStartEndDateStrings(parsedDate);
                    corePanel.showSchedulesBetweenDate(weekStartEndDateStrings[0], weekStartEndDateStrings[1]);
                }
            }
        });
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void foldSideBar(boolean fold) {
        if (sideBar.isFolded() && !fold) {
            sideBar.setSize(230, sideBar.getSize().height);
            sideBar.fold(false);
        } else if (!sideBar.isFolded() && fold) {
            sideBar.setSize(75, sideBar.getSize().height);
            sideBar.fold(true);
        }
    }
}
