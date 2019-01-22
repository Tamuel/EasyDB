package GUI.Component;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

import Resource.ColorR;

public class SimpleSelectImageButton extends SimpleImageButton implements ActionListener {

	/** Other buttons to unselected */
	private ArrayList<SimpleSelectImageButton> buttons;
	
	/** Decide this button is selected or not */
	private boolean selected = false;
	
	
	public SimpleSelectImageButton(ArrayList<SimpleSelectImageButton> buttons, ImageIcon base,
			int iconWidth, int iconHeight, int width, int height) {
		super(base, iconWidth, iconHeight, width, height);
		this.buttons = buttons;
		this.addActionListener(this);
	}
	
	public SimpleSelectImageButton(ArrayList<SimpleSelectImageButton> buttons, ImageIcon base,
			ImageIcon pressed, int iconWidth, int iconHeight, int width, int height) {
		super(base, pressed, iconWidth, iconHeight, width, height);
		this.buttons = buttons;
		this.addActionListener(this);
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelect(boolean isSelected) {
		this.selected = isSelected;
	}
	
	public void setSelected() {
		setSelect(true);
		setIcon(
				new ImageIcon(
						getPressedIcon().getImage()
						.getScaledInstance(
								getIconWidth(),
								getIconHeight(),
								Image.SCALE_SMOOTH)
								)
				);
	}
	
	public void setUnSelected() {
		setSelect(false);
		setIcon(
				new ImageIcon(
						getBaseIcon().getImage()
						.getScaledInstance(
								getIconWidth(),
								getIconHeight(),
								Image.SCALE_SMOOTH)
								)
				);
	}
	
	public ArrayList<SimpleSelectImageButton> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<SimpleSelectImageButton> buttons) {
		this.buttons = buttons;
	}
	
	public void unSelectAll() {
		for(SimpleSelectImageButton temp : buttons)
			temp.setUnSelected();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		/** When select this button, another buttons are unselected */
		for(SimpleSelectImageButton temp : buttons) {
			if(temp.equals(this))
				temp.setSelected();
			else
				temp.setUnSelected();
		}
	}
}
