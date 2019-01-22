package GUI.Frames.MainFrame;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import GUI.Component.SimpleJFrame;
import GUI.Component.SimpleLabel;
import GUI.Component.SimpleScrollPane;
import Resource.ColorR;
import Resource.DBProvider;
import Resource.DimenR;

public class TableViewPanel extends JPanel {

	private int slotWidth = 120;
	private int slotHeight = 50;
	
	public TableViewPanel(SimpleJFrame context, String taleName, int columnNumber) {
		setLayout(null);

		int stringNumber = DBProvider.getInstance().getQueryStrings().size();
		
		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(null);
		tablePanel.setBackground(ColorR.WHITE);
		tablePanel.setSize(
				slotWidth * columnNumber,
				slotHeight * (stringNumber / columnNumber)
				);
		tablePanel.setPreferredSize(
					new Dimension(
						slotWidth * columnNumber,
						slotHeight * (stringNumber / columnNumber)
					)
				);
		
		SimpleLabel columnNames[][] = new SimpleLabel[(stringNumber / columnNumber)][columnNumber];
		
		
		int yPos = 0;
		for(int i = 0; i < stringNumber; i++) {
			
			SimpleLabel tempLabel = new SimpleLabel(DBProvider.getInstance().getQueryStrings().get(i));
			tempLabel.setSize(slotWidth, slotHeight);
			tempLabel.setLocation(slotWidth * (i % columnNumber), (int)(i / columnNumber) * slotHeight);
			tempLabel.setBorder(BorderFactory.createLineBorder(ColorR.LIGHT_GRAY, 1));
			
			if(i == stringNumber - 1)
				yPos = (int)(i / columnNumber) * slotHeight;
			
			columnNames[(int)(i / columnNumber)][i % columnNumber] = tempLabel;
			
			tablePanel.add(tempLabel);
		}
		yPos += slotHeight;
		if(tablePanel.getHeight() < yPos) {
			tablePanel.setSize(
					slotWidth * columnNumber,
					yPos
					);
			tablePanel.setPreferredSize(
						new Dimension(
							slotWidth * columnNumber,
							yPos
						)
					);
		}
		
		add(tablePanel);
		
		this.setBackground(ColorR.WHITE);
		this.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.DARK_GRAY)
				);
		this.setLocation(0, 0);
		this.setSize(
				tablePanel.getWidth(),
				tablePanel.getHeight()
				);
		this.setPreferredSize(tablePanel.getSize());
	}
}
