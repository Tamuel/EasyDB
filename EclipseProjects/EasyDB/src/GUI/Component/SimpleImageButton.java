package GUI.Component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import Resource.ColorR;
import Resource.DimenR;

public class SimpleImageButton extends JButton {
	private ImageIcon baseIcon;
	private ImageIcon pressedIcon;
	
	private int iconWidth;
	private int iconHeight;

	public SimpleImageButton(ImageIcon base, int iconWidth, int iconHeight, int width, int height) {
		super();
		baseIcon = base;
		pressedIcon = base;
		this.iconWidth = iconWidth;
		this.iconHeight = iconHeight;
		this.setSize(width, height);
		custom();
	}
	
	public SimpleImageButton(ImageIcon base, ImageIcon pressed, int iconWidth, int iconHeight, int width, int height) {
		super();
		baseIcon = base;
		pressedIcon = pressed;
		this.iconWidth = iconWidth;
		this.iconHeight = iconHeight;
		this.setSize(width, height);
		custom();
	}
	
	private void custom() {
		this.setOpaque(true);
		this.setForeground(null);
		this.setBackground(null);
		this.setMargin(new Insets(0, 0, 0, 0));
		Border empty = new EmptyBorder(2, 2, 2, 2);
		this.setBorder(empty);
		this.setFocusPainted(false);
        super.setContentAreaFilled(false);
        
		setIcon(
				new ImageIcon(
						baseIcon.getImage()
						.getScaledInstance(
								iconWidth,
								iconHeight,
								Image.SCALE_SMOOTH)
								)
				);
		
		setPressedIcon(
				new ImageIcon(
					pressedIcon.getImage()
					.getScaledInstance(
							iconWidth,
							iconHeight,
							Image.SCALE_SMOOTH)
							)
				);
	}

	public ImageIcon getBaseIcon() {
		return baseIcon;
	}

	public void setBaseIcon(ImageIcon baseIcon) {
		this.baseIcon = baseIcon;
	}

	public ImageIcon getPressedIcon() {
		return pressedIcon;
	}

	public void setPressedIcon(ImageIcon pressedIcon) {
		this.pressedIcon = pressedIcon;
	}

	public int getIconWidth() {
		return iconWidth;
	}

	public void setIconWidth(int iconWidth) {
		this.iconWidth = iconWidth;
	}

	public int getIconHeight() {
		return iconHeight;
	}

	public void setIconHeight(int iconHeight) {
		this.iconHeight = iconHeight;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
        	g.setColor(new Color(0, 0, 0, 0));
        } else if (getModel().isRollover()) {
            g.setColor(new Color(0, 0, 0, 0));
        } else {
            g.setColor(new Color(0, 0, 0, 0));
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
