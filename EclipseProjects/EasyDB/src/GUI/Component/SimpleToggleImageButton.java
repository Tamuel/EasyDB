package GUI.Component;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class SimpleToggleImageButton extends SimpleSelectImageButton {

	public SimpleToggleImageButton(ImageIcon base, int iconWidth, int iconHeight, int width, int height) {
		super(null, base, iconWidth, iconHeight, width, height);
	}
	
	public SimpleToggleImageButton(ImageIcon base, ImageIcon pressed, int iconWidth, int iconHeight, int width, int height) {
		super(null, base, pressed, iconWidth, iconHeight, width, height);
	}
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!isSelected())
			setSelected();
		else
			setUnSelected();
	}
}
