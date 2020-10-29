package top.rayalto.classSchedule.frames;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import top.rayalto.classSchedule.components.CorePanel;
import top.rayalto.classSchedule.components.SideBar;

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
