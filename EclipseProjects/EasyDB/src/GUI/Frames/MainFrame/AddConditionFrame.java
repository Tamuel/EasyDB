package GUI.Frames.MainFrame;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import GUI.Component.SimpleButton;
import GUI.Component.SimpleJFrame;
import GUI.Component.SimpleLabel;
import GUI.Component.SimpleScrollPane;
import GUI.Component.SimpleSelectButton;
import GUI.Component.SimpleTextField;
import GUI.Component.SimpleToggleButton;
import Resource.ColorR;
import Resource.ConditionOperation;
import Resource.DBProvider;
import Resource.DimenR;
import Resource.StringR;

public class AddConditionFrame extends SimpleJFrame {
	private ArrayList<SimpleSelectButton> tableAttributesButtons;
	private ArrayList<SimpleSelectButton> conditionOperationButtons;
	
	private ArrayList<String> tableAttributes;
	
	private int selectedAttribute = -1;
	private int selectedOperation = -1;

	public AddConditionFrame(String frameName, TableEditPanel tableEditPanel, String tableName, int width, int height) {
		super(frameName, width, height);
		tableAttributesButtons = new ArrayList<SimpleSelectButton>();
		conditionOperationButtons = new ArrayList<SimpleSelectButton>();
		
		tableAttributes = DBProvider.getInstance().getAttributesNames();
		
		JPanel wantToSeeAttributePanel = new JPanel();
		wantToSeeAttributePanel.setLayout(null);
		wantToSeeAttributePanel.setBackground(ColorR.WHITE);
		wantToSeeAttributePanel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorR.LIGHT_GRAY)
				);
		wantToSeeAttributePanel.setSize(
				this.getWidth(),
				this.getHeight() / 3
				);
		wantToSeeAttributePanel.setLocation(
				0,
				0
				);
		this.add(wantToSeeAttributePanel);
		
		SimpleLabel wantToSeeLabel = new SimpleLabel(StringR.CONDITION_COLUMN);
		wantToSeeLabel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.LIGHT_GRAY)
				);
		wantToSeeLabel.setSize(
				this.getWidth() / 5,
				this.getHeight() / 3
				);
		wantToSeeLabel.setLocation(
				0,
				0
				);
		wantToSeeAttributePanel.add(wantToSeeLabel);
		
		JPanel attributesPanel = new JPanel();
		attributesPanel.setLayout(null);
		attributesPanel.setBackground(ColorR.WHITE);
		attributesPanel.setSize(
				this.getWidth() * 4 / 5,
				this.getHeight() / 3
				);
		attributesPanel.setPreferredSize(
					new Dimension(
							this.getWidth() * 4 / 5,
							this.getHeight() / 3
					)
				);
		attributesPanel.setLocation(0, 0);
	
		int yPos = DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
		int numberOfButton = 4;
		for(int i = 0; i < tableAttributes.size(); i++) {
			SimpleSelectButton newButton = new SimpleSelectButton(
					tableAttributesButtons,
					BorderFactory.createLineBorder(ColorR.LIGHT_GRAY, 1),
					tableAttributes.get(i),
					(attributesPanel.getWidth() - DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN * (numberOfButton + 1)) / numberOfButton,
					DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT
					);
			newButton.setBackground(ColorR.WHITE);
			newButton.setForeground(ColorR.DARK_GRAY);
			newButton.setUnSelected();
			
			final int index = i;
			
			newButton.setUnSelected();
			newButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectedAttribute = index;
				}
				
			});
			
			newButton.setLocation(
					(i % numberOfButton) * newButton.getWidth() + (i % numberOfButton + 1) * DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN,
					yPos
					);
			
			if(i % numberOfButton == numberOfButton - 1)
				yPos += DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT + DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
			
			attributesPanel.add(newButton);
			tableAttributesButtons.add(newButton);
		}
		yPos += DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT + DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
		
		if(attributesPanel.getHeight() < yPos) {
			attributesPanel.setSize(
					DimenR.TABLE_LIST_PANEL_WIDTH / 2,
					yPos
					);
			attributesPanel.setPreferredSize(
						new Dimension(
							DimenR.TABLE_LIST_PANEL_WIDTH / 2,
							yPos
						)
					);
		}
		
		SimpleScrollPane attributeScrollPane = new SimpleScrollPane(attributesPanel);
		attributeScrollPane.setSize(
				this.getWidth() * 4 / 5,
				this.getHeight() / 3
				);
		attributeScrollPane.setLocation(
				wantToSeeLabel.getWidth(),
				0
				);
		attributeScrollPane.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorR.LIGHT_GRAY)
				);
		wantToSeeAttributePanel.add(attributeScrollPane);
		
		
		JPanel conOperationPanel = new JPanel();
		conOperationPanel.setLayout(null);
		conOperationPanel.setBackground(ColorR.WHITE);
		conOperationPanel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorR.LIGHT_GRAY)
				);
		conOperationPanel.setSize(
				this.getWidth(),
				this.getHeight() / 3
				);
		conOperationPanel.setLocation(
				0,
				wantToSeeAttributePanel.getHeight()
				);
		
		
		SimpleLabel conditionLabel = new SimpleLabel(StringR.CONDITION);
		conditionLabel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.LIGHT_GRAY)
				);
		conditionLabel.setSize(
				this.getWidth() / 5,
				this.getHeight() / 3
				);
		conditionLabel.setLocation(
				0,
				0
				);
		conOperationPanel.add(conditionLabel);
		
		
		JPanel conOperationButtonPanel = new JPanel();
		conOperationButtonPanel.setLayout(null);
		conOperationButtonPanel.setBackground(ColorR.WHITE);
		conOperationButtonPanel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorR.LIGHT_GRAY)
				);
		conOperationButtonPanel.setSize(
				this.getWidth() * 4 / 5,
				this.getHeight() / 3
				);
		conOperationButtonPanel.setLocation(
				conditionLabel.getWidth(),
				0
				);
		
		
		yPos = DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
		numberOfButton = 2;
		for(int i = 0; i < ConditionOperation.values().length; i++) {
			SimpleSelectButton newButton = new SimpleSelectButton(
					conditionOperationButtons,
					BorderFactory.createLineBorder(ColorR.LIGHT_GRAY, 1),
					ConditionOperation.values()[i].getString(),
					(conOperationButtonPanel.getWidth() - DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN * (numberOfButton + 1)) / numberOfButton,
					DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT
					);
			newButton.setBackground(ColorR.WHITE);
			newButton.setForeground(ColorR.DARK_GRAY);
			newButton.setUnSelected();
			
			final int index = i;
			
			newButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					selectedOperation = index;
				}
				
			});
			
			newButton.setLocation(
					(i % numberOfButton) * newButton.getWidth() + (i % numberOfButton + 1) * DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN,
					yPos
					);
			
			if(i % numberOfButton == numberOfButton - 1)
				yPos += DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT + DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
			
			conOperationButtonPanel.add(newButton);
			conditionOperationButtons.add(newButton);
		}
		yPos += DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT + DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
		
		if(conOperationButtonPanel.getHeight() < yPos) {
			conOperationButtonPanel.setSize(
					DimenR.TABLE_LIST_PANEL_WIDTH / 2,
					yPos
					);
			conOperationButtonPanel.setPreferredSize(
						new Dimension(
							DimenR.TABLE_LIST_PANEL_WIDTH / 2,
							yPos
						)
					);
		}
		
		SimpleScrollPane conditionOperationScrollPane = new SimpleScrollPane(conOperationButtonPanel);
		conditionOperationScrollPane.setSize(
				this.getWidth() * 4 / 5,
				this.getHeight() / 3
				);
		conditionOperationScrollPane.setLocation(
				wantToSeeLabel.getWidth(),
				0
				);
		conditionOperationScrollPane.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.LIGHT_GRAY)
				);
		conOperationPanel.add(conditionOperationScrollPane);
		
		

		JPanel valuePanel = new JPanel();
		valuePanel.setLayout(null);
		valuePanel.setBackground(ColorR.WHITE);
		valuePanel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorR.LIGHT_GRAY)
				);
		valuePanel.setSize(
				this.getWidth(),
				this.getHeight() / 6
				);
		valuePanel.setLocation(
				0,
				conOperationPanel.getY() + conOperationPanel.getHeight()
				);
		
		
		SimpleLabel valueLabel = new SimpleLabel(StringR.VALUE);
		valueLabel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.LIGHT_GRAY)
				);
		valueLabel.setSize(
				this.getWidth() / 5,
				this.getHeight() / 6
				);
		valueLabel.setLocation(
				0,
				0
				);
		valuePanel.add(valueLabel);
		
		
		SimpleTextField valueTextField = new SimpleTextField(StringR.VALUE);
		valueTextField.setLayout(null);
		valueTextField.setBackground(ColorR.WHITE);
		valueTextField.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorR.LIGHT_GRAY)
				);
		valueTextField.setSize(
				this.getWidth() * 4 / 5,
				this.getHeight() / 6
				);
		valueTextField.setLocation(
				valueLabel.getWidth(),
				0
				);
		valuePanel.add(valueTextField);
		
		SimpleButton addConditionButton = new SimpleButton(StringR.ADD_CONDITION);
		addConditionButton.setBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.DARK_GRAY)
				);
		addConditionButton.setSize(
				this.getWidth() / 2,
				this.getHeight() / 6
				);
		addConditionButton.setLocation(
				0,
				valuePanel.getHeight() + valuePanel.getY()
				);
		addConditionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(selectedAttribute != -1 && selectedOperation != -1 && !valueTextField.getText().toString().equals("")) {
					try {
						Integer.parseInt(valueTextField.getText().toString());
						tableEditPanel.makeCondition(
								tableAttributes.get(selectedAttribute) +
								" " +
								ConditionOperation.values()[selectedOperation].getOperation() + " " +
								valueTextField.getText().toString()
								);
					} catch (Exception e) {
						tableEditPanel.makeCondition(
								tableAttributes.get(selectedAttribute) +
								" " +
								ConditionOperation.values()[selectedOperation].getOperation() + " " +
								"'" + valueTextField.getText().toString() + "'"
								);
					}
					dispose();
				}
			}
			
		});
		add(addConditionButton);
		
		SimpleButton cancelButton = new SimpleButton(StringR.CANCEL);
		cancelButton.setSize(
				this.getWidth() / 2,
				this.getHeight() / 6
				);
		cancelButton.setLocation(
				this.getWidth() / 2,
				valuePanel.getHeight() + valuePanel.getY()
				);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
			
		});
		add(cancelButton);
		

		add(wantToSeeAttributePanel);
		add(conOperationPanel);
		add(valuePanel);
		
	}

}
