package top.rayalto.classSchedule;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public SideBar sideBar = new SideBar();

    public JButton foldButton = new JButton("fold sidebar");

    public MainFrame() {
        setLayout(new GridLayout(0, 2));
        add(sideBar);
        add(foldButton);
        foldButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sideBar.isFolded()) {
                    sideBar.setFold(false);
                    foldButton.setText("fold sidebar");
                } else {
                    sideBar.setFold(true);
                    foldButton.setText("unfold sidebar");
                }
            }
        });

        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
