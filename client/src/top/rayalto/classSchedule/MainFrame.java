package top.rayalto.classSchedule;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    private Dimension mainFrameSize = new Dimension(900, 739);
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public SideBar sideBar = new SideBar();
    public JButton foldButton = new JButton("fold sidebar");

    public MainFrame(String title) {
        setLayout(null);
        setSize(mainFrameSize);
        setLocation((screenSize.width - mainFrameSize.width) / 2, (screenSize.height - mainFrameSize.height) / 2);
        add(sideBar);
        sideBar.setBounds(0, 0, 230, 702);
        add(foldButton);
        foldButton.setLocation(230, 0);
        foldButton.setSize(foldButton.getPreferredSize());
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension frameSize = getSize();
                int sideBarWidth = sideBar.isFolded() ? 75 : 230;
                sideBar.setSize(sideBarWidth, frameSize.height - 37);
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
            sideBar.setSize(75, sideBar.getSize().height);
            sideBar.fold(true);
        }
    }
}
