package top.rayalto.classSchedule;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class SideBar extends JPanel {

    private static final long serialVersionUID = 1L;

    public JLabel sideBarBanner = new JLabel(Sources.SIDEBAR_BANNER_UNFOLF_IMAGE);
    public JPanel buttonsPanel = new JPanel(new GridLayout(13, 0));
    public JScrollPane scrollButtonsPanel = new JScrollPane(buttonsPanel);
    public JButton button1 = new JButton("button1");
    public JButton button2 = new JButton("button2");
    public JButton button3 = new JButton("button3");
    public JButton button4 = new JButton("button4");
    public JButton button5 = new JButton("button5");
    public JButton button6 = new JButton("button6");
    public JButton button7 = new JButton("button7");
    public JButton button8 = new JButton("button8");
    public JButton button9 = new JButton("button9");
    public JButton button10 = new JButton("button10");
    public JButton button11 = new JButton("button11");
    public JButton button12 = new JButton("button12");
    public JButton button13 = new JButton("button13");

    private boolean _folded = false;

    public SideBar() {
        setLayout(null);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension sideBarSize = getSize();
                sideBarBanner.setBounds(0, 0, sideBarSize.width, 50);
                scrollButtonsPanel.setBounds(0, 50, sideBarSize.width, sideBarSize.height - 50);
            }
        });
        setBackground(Color.BLUE);
        add(sideBarBanner);
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
        add(scrollButtonsPanel);
    }

    public boolean isFolded() {
        return _folded;
    }

    public void setFold(boolean fold) {
        if (fold && !_folded) {
            sideBarBanner.setIcon(Sources.SIDEBAR_BANNER_FOLF_IMAGE);
            _folded = true;
        } else if (!fold && _folded) {
            sideBarBanner.setIcon(Sources.SIDEBAR_BANNER_UNFOLF_IMAGE);
            _folded = false;
        }
    }
}
