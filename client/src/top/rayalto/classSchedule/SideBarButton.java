package top.rayalto.classSchedule;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.awt.Font;
import java.awt.FontFormatException;

public class SideBarButton extends JButton {

    private static final long serialVersionUID = 1L;

    private Color hoverBackgroundColor = Color.GRAY;
    private Color pressedBackgroundColor = Color.LIGHT_GRAY;

    public SideBarButton() {
        this(null);
        setBackground(Color.DARK_GRAY);
        setForeground(Color.WHITE);
        setIconTextGap(45);
        setFont(Sources.NOTO_SANS_MONO_FONT());
    }

    public SideBarButton(String text) {
        super(text);
        super.setContentAreaFilled(false);
        setBackground(Color.DARK_GRAY);
        setForeground(Color.WHITE);
        setIconTextGap(45);
        setFont(Sources.NOTO_SANS_MONO_FONT());
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
