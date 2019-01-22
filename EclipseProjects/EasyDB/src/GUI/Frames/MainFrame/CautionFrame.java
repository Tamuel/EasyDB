package GUI.Frames.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

import GUI.Component.SimpleButton;
import GUI.Component.SimpleJFrame;
import GUI.Component.SimpleLabel;
import GUI.Component.SimpleTextArea;
import Resource.DimenR;
import Resource.StringR;

public class CautionFrame extends SimpleJFrame{

	private SimpleTextArea cautionLabel;
	private SimpleButton okayButton;
	
	public CautionFrame(String frameName, String cautionMsg, int width, int height) {
		super(frameName, width, height);
		
		cautionLabel = new SimpleTextArea(cautionMsg);
		cautionLabel.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
		cautionLabel.setSize(width - 100, height - 30);
		cautionLabel.setLocation(50, 30);
		
		okayButton = new SimpleButton(StringR.OKAY);
		okayButton.setSize(DimenR.BUTTON_WIDTH, DimenR.BUTTON_HEIGHT);
		okayButton.setLocation(width / 2 - okayButton.getWidth() / 2, (int)(height - DimenR.BUTTON_HEIGHT * 1.2));
		okayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
			
		});
		
		add(okayButton);
		add(cautionLabel);
	}
}
