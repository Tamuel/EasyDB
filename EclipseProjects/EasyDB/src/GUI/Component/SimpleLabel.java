package GUI.Component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Resource.DimenR;

public class SimpleLabel extends JLabel {
	
	private Font font = new Font("¸¼Àº °íµñ", Font.BOLD, DimenR.BIG_FONT);
	private Font font2 = new Font("¸¼Àº °íµñ", Font.PLAIN, DimenR.SMALL_FONT);

	private Color fontColor = new Color(120, 120, 120);
	private Color fontColor2 = new Color(150, 150, 150);
	
	public SimpleLabel(String text) {
		super(text);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		setBigFont();
	}
	
	public SimpleLabel(String text, int width, int height) {
		super(text);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setSize(width, height);
		setBigFont();
	}

	public void setBigFont() {
		this.setFont(font);
		this.setForeground(fontColor);
	}
	
	public void setSmallFont() {
		this.setFont(font2);
		this.setForeground(fontColor2);
	}
}
