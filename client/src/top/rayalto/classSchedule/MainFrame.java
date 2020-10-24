package top.rayalto.classSchedule;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public SideBar sideBar = new SideBar();
    public JButton foldButton = new JButton("fold sidebar");

    public MainFrame() {
        setLayout(null);
        setSize(500, 537);
        add(sideBar);
        sideBar.setBounds(0, 0, 230, 500);
        add(foldButton);
        foldButton.setLocation(230, 0);
        foldButton.setSize(foldButton.getPreferredSize());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension frameSize = getSize();
                int sideBarWidth = sideBar.isFolded() ? 66 : 230;
                int sideBarHeight = frameSize.height > 537 ? 500 : frameSize.height - 37;
                sideBar.setSize(sideBarWidth, sideBarHeight);
            }
        });
        foldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sideBar.isFolded()) {
                    foldSideBar(false);
                    foldButton.setText("fold sidebar");
                } else {
                    foldSideBar(true);
                    foldButton.setText("unfold sidebar");
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
            sideBar.setSize(66, sideBar.getSize().height);
            sideBar.fold(true);
        }
    }
}
