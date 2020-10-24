package top.rayalto.classSchedule;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class SideBar extends JPanel {

    private static final long serialVersionUID = 1L;

    public JLabel sideBarBanner = new JLabel(Sources.SIDEBAR_BANNER_UNFOLF_IMAGE);
    public JPanel buttonsPanel = new JPanel(new GridLayout(14, 0));
    public JScrollPane scrollButtonsPanel = new JScrollPane(buttonsPanel);
    public SideBarButton homeButton = new SideBarButton("homeButton");
    public SideBarButton button1 = new SideBarButton("button1");
    public SideBarButton button2 = new SideBarButton("button2");
    public SideBarButton button3 = new SideBarButton("button3");
    public SideBarButton button4 = new SideBarButton("button4");
    public SideBarButton button5 = new SideBarButton("button5");
    public SideBarButton button6 = new SideBarButton("button6");
    public SideBarButton button7 = new SideBarButton("button7");
    public SideBarButton button8 = new SideBarButton("button8");
    public SideBarButton button9 = new SideBarButton("button9");
    public SideBarButton button10 = new SideBarButton("button10");
    public SideBarButton button11 = new SideBarButton("button11");
    public SideBarButton button12 = new SideBarButton("button12");
    public SideBarButton button13 = new SideBarButton("button13");

    private boolean _folded = false;

    public SideBar() {
        setBackground(Color.DARK_GRAY);
        buttonsPanel.setBackground(Color.DARK_GRAY);
        scrollButtonsPanel.setBackground(Color.DARK_GRAY);
        setLayout(null);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension sideBarSize = getSize();
                sideBarBanner.setSize(sideBarSize.width, 50);
                scrollButtonsPanel.setSize(sideBarSize.width, sideBarSize.height > 500 ? 440 : sideBarSize.height - 50);
            }
        });
        add(sideBarBanner);
        sideBarBanner.setLocation(0, 0);
        sideBarBanner.setSize(230, 50);
        buttonsPanel.add(homeButton);
        buttonsPanel.add(button1);
        buttonsPanel.add(button2);
        buttonsPanel.add(button3);
        buttonsPanel.add(button4);
        buttonsPanel.add(button5);
        buttonsPanel.add(button6);
        buttonsPanel.add(button7);
        buttonsPanel.add(button8);
        buttonsPanel.add(button9);
        buttonsPanel.add(button10);
        buttonsPanel.add(button11);
        buttonsPanel.add(button12);
        buttonsPanel.add(button13);
        scrollButtonsPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollButtonsPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollButtonsPanel);
        scrollButtonsPanel.setLocation(0, 50);
    }

    public boolean isFolded() {
        return _folded;
    }

    public void fold(boolean fold) {
        if (fold && !_folded) {
            sideBarBanner.setIcon(Sources.SIDEBAR_BANNER_FOLF_IMAGE);
            _folded = true;
        } else if (!fold && _folded) {
            sideBarBanner.setIcon(Sources.SIDEBAR_BANNER_UNFOLF_IMAGE);
            _folded = false;
        }
        Dimension sideBarSize = getSize();
        sideBarBanner.setSize(sideBarSize.width, 50);
        scrollButtonsPanel.setSize(sideBarSize.width, sideBarSize.height > 500 ? 440 : sideBarSize.height - 50);
    }
}
