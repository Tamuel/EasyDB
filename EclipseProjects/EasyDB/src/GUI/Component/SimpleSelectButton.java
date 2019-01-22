package GUI.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import Resource.ColorR;

/**
 * This is for selectable button
 * when select one button, another buttons are unselected
 * @author DongKyu
 *
 */
public class SimpleSelectButton extends SimpleButton implements ActionListener {

	/** Other buttons to unselected */
	private ArrayList<SimpleSelectButton> buttons;
	
	/** Set base border */
	private Border baseBorder;
	
	/** Decide this button is selected or not */
	private boolean selected = false;
	
	public SimpleSelectButton(ArrayList<SimpleSelectButton> buttons, Border baseBorder) {
		super();
		custom2();
		setBaseBorder(baseBorder);
		setButtons(buttons);
	}
	
	public SimpleSelectButton(ArrayList<SimpleSelectButton> buttons, Border baseBorder,
			String text) {
		super(text);
		custom2();
		setBaseBorder(baseBorder);
		setButtons(buttons);
	}
	
	public SimpleSelectButton(ArrayList<SimpleSelectButton> buttons, Border baseBorder,
			String text, int width, int height) {
		super(text, width, height);
		custom2();
		setBaseBorder(baseBorder);
		setButtons(buttons);
	}
	
	public SimpleSelectButton(ArrayList<SimpleSelectButton> buttons, Border baseBorder,
			String text1, String text2, int width, int height) {
		super(text1, text2, width, height);
		custom2();
		setBaseBorder(baseBorder);
		setButtons(buttons);
	}
	
	public SimpleSelectButton(ArrayList<SimpleSelectButton> buttons, Border baseBorder,
			String text1, String text2, String text3, int width, int height) {
		super(text1, text2, text3, width, height);
		custom2();
		setBaseBorder(baseBorder);
		setButtons(buttons);
	}

	public void custom2() {
		this.addActionListener(this);
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelect(boolean isSelected) {
		this.selected = isSelected;
	}

	public Border getBaseBorder() {
		return baseBorder;
	}

	public void setBaseBorder(Border border) {
		setBorder(border);
		this.baseBorder = border;
	}

	public void setSelected() {
		setSelect(true);
		setBorder(BorderFactory.createLineBorder(ColorR.DARK_GRAY, 3));
	}
	
	public void setUnSelected() {
		setSelect(false);
		setBorder(baseBorder);
	}

	
	public ArrayList<SimpleSelectButton> getButtons() {
		return buttons;
	}

	public void setButtons(ArrayList<SimpleSelectButton> buttons) {
		this.buttons = buttons;
	}
	
	public void unSelectAll() {
		for(SimpleSelectButton temp : buttons)
			temp.setUnSelected();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		/** When select this button, another buttons are unselected */
		for(SimpleSelectButton temp : buttons) {
			if(temp.equals(this))
				temp.setSelected();
			else
				temp.setUnSelected();
		}
	}
}
