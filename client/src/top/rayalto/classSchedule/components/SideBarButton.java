package top.rayalto.classSchedule.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.SwingUtilities;

import top.rayalto.classSchedule.Sources;

public class SideBarButton extends JButton {

    private static final long serialVersionUID = 1L;

    private Color hoverBackgroundColor = Color.GRAY;
    private Color pressedBackgroundColor = Color.LIGHT_GRAY;

    private void initialize(){
        setBackground(Color.DARK_GRAY);
        setForeground(Color.WHITE);
        setIconTextGap(45);
        setFont(Sources.NOTO_SANS_MONO_FONT);
    }

    public SideBarButton() {
        this(null);
    }

    public SideBarButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                initialize();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedBackgroundColor);
        } else if (getModel().isRollover()) {
            g.setColor(hoverBackgroundColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setPressedBackgroundColor(Color pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
    }
}
