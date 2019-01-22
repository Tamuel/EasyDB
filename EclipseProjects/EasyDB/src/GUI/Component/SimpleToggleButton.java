package GUI.Component;

import java.awt.event.ActionEvent;

import javax.swing.border.Border;

public class SimpleToggleButton extends SimpleSelectButton {

	public SimpleToggleButton(Border border, String arg) {
		super(null, border, arg);
	}
	
	public SimpleToggleButton(Border border, String arg, int width, int height) {
		super(null, border, arg, width, height);
	}
	
	public SimpleToggleButton(Border border, String arg0, String arg1, int width, int height) {
		super(null, border, arg0, arg1, width, height);
	}
	
	public SimpleToggleButton(Border border, String arg0, String arg1, String arg2, int width, int height) {
		super(null, border, arg0, arg1, arg2, width, height);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!isSelected())
			setSelected();
		else
			setUnSelected();
	}
}