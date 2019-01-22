package GUI.Frames.MainFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import GUI.Component.SimpleButton;
import GUI.Component.SimpleImageButton;
import GUI.Component.SimpleJFrame;
import GUI.Component.SimpleLabel;
import GUI.Component.SimpleScrollPane;
import GUI.Component.SimpleSelectButton;
import GUI.Component.SimpleTextField;
import GUI.Component.SimpleToggleButton;
import Resource.ColorR;
import Resource.DBProvider;
import Resource.DimenR;
import Resource.SQLoperation;
import Resource.StringR;
import Resource.TableOperation;

public class TableEditPanel extends JPanel {
	private ArrayList<SimpleSelectButton> TableOperationButtons;
	private ArrayList<SimpleSelectButton> SQLoperationButtons;
	private ArrayList<SimpleLabel> tableAttributesLabel;
	private ArrayList<SimpleToggleButton> tableAttributesButtons;
	private ArrayList<SimpleTextField> setValueTextFields;

	/** Panel for show table attributes */
	private SimpleScrollPane tableAttributesScrollPane;
	/** Panel for show table attributes */
	private JPanel tableAttributesPanel;
	/** Panel for table edit button like seek data, delete data etc */
	private JPanel tableEditButtonPanel;
	/** Panel for SQL operation buttons */
	private JPanel sqlOperationPanel;
	/** Panel for SQL operation result */
	private JPanel sqlResultPanel;
	/** Panel for SQL condition */
	private JPanel conditionListPanel;
	/** Label for show table name */
	private SimpleLabel tableNameLabel;
	/** Label for show SQL operation */
	private SimpleTextField sqlOperationTextField;
	
	/** It show current selected table operation */
	private TableOperation selectedTableOperation;
	/** It show current table name */
	private String tableName;
	/** It show selected columns */
	private String columnString = "*";
	/** Array for condition */
	private ArrayList<String> conditions;
	/** String for all condition */
	private String conditionString = "";
	/** String for new values */
	private String newValues = "";
	
	private ArrayList<String> tableAttributes;
	
	public TableEditPanel(SimpleJFrame context, String tableName) {
		setLayout(null);
		this.tableName = tableName;
		int height = context.getHeight() - DimenR.EDIT_MENU_PANEL_HEIGHT;
		tableAttributes = new ArrayList<String>();
		conditions = new ArrayList<String>();
		setValueTextFields = new ArrayList<SimpleTextField>();
		tableAttributesButtons = new ArrayList<SimpleToggleButton>();
		
		tableAttributes.addAll(DBProvider.getInstance().getAttributesNames());
		
		tableNameLabel = new SimpleLabel(tableName);
		tableNameLabel.setSize(
				DimenR.TABLE_LIST_PANEL_WIDTH,
				DimenR.TABLE_NAME_LABEL_HEIGHT
				);
		tableNameLabel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorR.DARK_GRAY)
				);
		add(tableNameLabel);
		
		tableAttributesPanel = new JPanel();
		tableAttributesPanel.setLayout(null);
		tableAttributesPanel.setSize(
				DimenR.TABLE_LIST_PANEL_WIDTH / 2,
				DimenR.SQL_OP_BUTTON_HEIGHT * 2 + DimenR.SQL_OP_BUTTON_MARGIN * 3 - 1
				);
		tableAttributesPanel.setPreferredSize(
					new Dimension(
						DimenR.TABLE_LIST_PANEL_WIDTH / 2,
						DimenR.SQL_OP_BUTTON_HEIGHT * 2 + DimenR.SQL_OP_BUTTON_MARGIN * 3 - 1
					)
				);
		tableAttributesPanel.setBackground(ColorR.WHITE);
		
		tableAttributesLabel = new ArrayList<SimpleLabel>();

		
		int yPos = 0;
		for(int i = 0; i < tableAttributes.size(); i++) {
			SimpleLabel newLabel = new SimpleLabel(tableAttributes.get(i));
			newLabel.setSize(
					DimenR.TABLE_LIST_PANEL_WIDTH / 4,
					DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT
					);
			newLabel.setLocation((i % 2) * newLabel.getWidth(), yPos);
			
			if(i % 2 == 1)
				yPos += DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT;
			
			tableAttributesLabel.add(newLabel);
			tableAttributesPanel.add(newLabel);
		}
		yPos += DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT;
		
		if(tableAttributesPanel.getHeight() < yPos) {
			tableAttributesPanel.setSize(
					DimenR.TABLE_LIST_PANEL_WIDTH / 2,
					yPos
					);
			tableAttributesPanel.setPreferredSize(
						new Dimension(
							DimenR.TABLE_LIST_PANEL_WIDTH / 2,
							yPos
						)
					);
		}
			
		

		tableAttributesScrollPane = new SimpleScrollPane(tableAttributesPanel);
		tableAttributesScrollPane.setSize(
				DimenR.TABLE_LIST_PANEL_WIDTH / 2,
				DimenR.SQL_OP_BUTTON_HEIGHT * 2 + DimenR.SQL_OP_BUTTON_MARGIN * 3
				);
		tableAttributesScrollPane.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorR.DARK_GRAY)
				);
		tableAttributesScrollPane.setLocation(
				0,
				DimenR.TABLE_NAME_LABEL_HEIGHT
				);
		
		tableEditButtonPanel = new JPanel();
		tableEditButtonPanel.setLayout(null);
		tableEditButtonPanel.setSize(
				DimenR.TABLE_LIST_PANEL_WIDTH / 2,
				DimenR.SQL_OP_BUTTON_HEIGHT * 2 + DimenR.SQL_OP_BUTTON_MARGIN * 3
				);
		tableEditButtonPanel.setLocation(
				DimenR.TABLE_LIST_PANEL_WIDTH / 2,
				DimenR.TABLE_NAME_LABEL_HEIGHT
				);
		tableEditButtonPanel.setBackground(ColorR.WHITE);
		tableEditButtonPanel.setBorder(
				BorderFactory.createMatteBorder(0, 1, 1, 1, ColorR.DARK_GRAY)
				);

		TableOperationButtons = new ArrayList<SimpleSelectButton>();
		yPos = tableNameLabel.getX() +  DimenR.SQL_OP_BUTTON_MARGIN;
		int buttonWidth = (DimenR.TABLE_LIST_PANEL_WIDTH - DimenR.SQL_OP_BUTTON_MARGIN * 5) / 4;
		int numberOfButtonInRow = 2;
		for(int i = 0; i < TableOperation.values().length; i++) {
			SimpleSelectButton newButton = new SimpleSelectButton(
					TableOperationButtons,
					BorderFactory.createLineBorder(ColorR.GRAY, 1),
					TableOperation.values()[i].getString(),
					buttonWidth,
					DimenR.SQL_OP_BUTTON_HEIGHT
					);
			
			newButton.setBackground(ColorR.WHITE);
			newButton.setForeground(Color.DARK_GRAY);
			
			ActionListener listener = null;
			final int index = i;
			switch(TableOperation.values()[i]) {
			case ADD_DATA:
				listener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						resetData();
						selectedTableOperation = TableOperation.values()[index];
						drawAddDataOperation();
						showSQLOperation();
					}
				};
				break;
			case DELETE_DATA:
				listener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						resetData();
						selectedTableOperation = TableOperation.values()[index];
						drawDeleteDataOperation();
						showSQLOperation();
					}
				};
				break;
			case SEEK_DATA:
				listener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						resetData();
						selectedTableOperation = TableOperation.values()[index];
						drawSeekDataOperation();
						showSQLOperation();
					}
				};
				break;
			case UPDATE_DATA:
				listener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						resetData();
						selectedTableOperation = TableOperation.values()[index];
						drawUpdateDataOperation();
						showSQLOperation();
					}
				};
				break;
			
			}
			
			
			newButton.addActionListener(listener);
			
			if(i != 0 && i % numberOfButtonInRow == 0)
				yPos += DimenR.SQL_OP_BUTTON_HEIGHT + DimenR.SQL_OP_BUTTON_MARGIN;
			
			newButton.setLocation(
					(i % numberOfButtonInRow + 1) * DimenR.SQL_OP_BUTTON_MARGIN +
					(i % numberOfButtonInRow) * buttonWidth,
					yPos
					);
			
			TableOperationButtons.add(newButton);
			tableEditButtonPanel.add(newButton);
		}

		sqlOperationTextField = new SimpleTextField("RESULT");
		sqlOperationTextField.setSize(
				DimenR.TABLE_LIST_PANEL_WIDTH - DimenR.SQL_OP_BUTTON_MARGIN * 2,
				DimenR.TABLE_NAME_LABEL_HEIGHT
				);
		sqlOperationTextField.setLocation(
				DimenR.SQL_OP_BUTTON_MARGIN,
				DimenR.SQL_OP_BUTTON_MARGIN
				);
		sqlOperationTextField.setBorder(
				BorderFactory.createMatteBorder(1, 1, 1, 1, ColorR.LIGHT_GRAY)
				);
		
		sqlOperationPanel = new JPanel();
		sqlOperationPanel.setLayout(null);
		sqlOperationPanel.setSize(
				DimenR.TABLE_LIST_PANEL_WIDTH,
				context.getHeight() * 2 / 5
				);
		sqlOperationPanel.setLocation(
				0,
				tableEditButtonPanel.getY() + tableEditButtonPanel.getHeight()
				);
		sqlOperationPanel.setBackground(ColorR.WHITE);
		sqlOperationPanel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.DARK_GRAY)
				);
		
		sqlResultPanel = new JPanel();
		sqlResultPanel.setLayout(null);
		sqlResultPanel.setSize(
				DimenR.TABLE_LIST_PANEL_WIDTH,
				context.getHeight() * 1 / 5
				);
		sqlResultPanel.setLocation(0, sqlOperationPanel.getY() + sqlOperationPanel.getHeight());
		sqlResultPanel.setBackground(ColorR.WHITE);
		sqlResultPanel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.DARK_GRAY)
				);
		sqlResultPanel.add(sqlOperationTextField);
		
		SQLoperationButtons = new ArrayList<SimpleSelectButton>();

		
		yPos += DimenR.SQL_OP_BUTTON_HEIGHT + DimenR.SQL_OP_BUTTON_MARGIN;
		if(yPos < height)
			yPos = height;
		
		SimpleButton excuteQueryButton = new SimpleButton(StringR.EXCUTE);
		excuteQueryButton.setSize(
				sqlResultPanel.getWidth() / 4,
				DimenR.SQL_OP_BUTTON_HEIGHT
				);
		excuteQueryButton.setLocation(
				DimenR.SQL_OP_BUTTON_MARGIN,
				sqlResultPanel.getY() + sqlResultPanel.getHeight() + DimenR.SQL_OP_BUTTON_MARGIN
				);
		excuteQueryButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch(selectedTableOperation) {
				case ADD_DATA:
					DBProvider.getInstance().insertQuery(
							sqlOperationTextField.getText().toString()
							);
					break;
					
				case DELETE_DATA:
					DBProvider.getInstance().deleteQuery(
							sqlOperationTextField.getText().toString()
							);
					break;
					
				case SEEK_DATA:
					DBProvider.getInstance().selectQuery(
							sqlOperationTextField.getText().toString(),
							columnString
							);
					((MainFrame)context).getTablePanel().setViewportView(
							new TableViewPanel(
									context,
									tableName,
									DBProvider.getInstance().getCurrentColumnNumber()
									)
							);
					((MainFrame)context).getTablePanel().repaint();
					break;
					
				case UPDATE_DATA:
					DBProvider.getInstance().updateQuery(
							sqlOperationTextField.getText().toString()
							);
					break;
				}
			}
			
		});

		add(excuteQueryButton);
		add(tableAttributesScrollPane);
		add(tableEditButtonPanel);
		add(sqlOperationPanel);
		add(sqlResultPanel);
		
		
		this.setSize(
				DimenR.TABLE_LIST_PANEL_WIDTH,
				yPos
				);
		this.setPreferredSize(
				new Dimension(
						DimenR.TABLE_LIST_PANEL_WIDTH,
						yPos
						)
				);
		this.setBackground(ColorR.WHITE);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.DARK_GRAY));
	}
	
	public void resetData() {
		conditionString = "";
		conditions.clear();
		columnString = "*";
		setValueTextFields.clear();
		newValues = "";
	}
	
	public void showSQLOperation() {
		String sqlOperation = "";
		switch(selectedTableOperation) {
		case ADD_DATA:
			sqlOperation += "INSERT INTO " + tableName + newValues;
			break;
			
		case DELETE_DATA:
			sqlOperation += "DELETE FROM " + tableName + conditionString;
			break;
			
		case SEEK_DATA:
			sqlOperation += "SELECT " + columnString + " FROM " + tableName + conditionString;
			break;
			
		case UPDATE_DATA:
			sqlOperation += "UPDATE " + tableName + newValues + conditionString;
			break;
			
		default:
			break;
		}
		sqlOperationTextField.setText(sqlOperation);
	}
	
	public void drawSeekDataOperation() {
		sqlOperationPanel.removeAll();
		
		JPanel wantTdoSeeAttributePanel = new JPanel();
		wantTdoSeeAttributePanel.setLayout(null);
		wantTdoSeeAttributePanel.setBackground(ColorR.WHITE);
		wantTdoSeeAttributePanel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, ColorR.DARK_GRAY)
				);
		wantTdoSeeAttributePanel.setSize(
				sqlOperationPanel.getWidth(),
				sqlOperationPanel.getHeight() / 2
				);
		wantTdoSeeAttributePanel.setLocation(
				0,
				0
				);
		sqlOperationPanel.add(wantTdoSeeAttributePanel);
		
		SimpleLabel wantToSeeLabel = new SimpleLabel(StringR.WANT_TO_SEEK_COLUMN);
		wantToSeeLabel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.LIGHT_GRAY)
				);
		wantToSeeLabel.setSize(
				sqlOperationPanel.getWidth() / 5,
				sqlOperationPanel.getHeight() / 2
				);
		wantToSeeLabel.setLocation(
				0,
				0
				);
		wantTdoSeeAttributePanel.add(wantToSeeLabel);
		
		JPanel attributesPanel = new JPanel();
		attributesPanel.setLayout(null);
		attributesPanel.setBackground(ColorR.WHITE);
		attributesPanel.setSize(
				sqlOperationPanel.getWidth() * 4 / 5 - 1,
				sqlOperationPanel.getHeight() / 2
				);
		attributesPanel.setPreferredSize(
					new Dimension(
							sqlOperationPanel.getWidth() * 4 / 5 - 1,
							sqlOperationPanel.getHeight() / 2
					)
				);
		attributesPanel.setLocation(0, 0);
		
		tableAttributesButtons.clear();
		int yPos = DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
		int numberOfButton = 4;

		ArrayList<String> tempAttributes = new ArrayList<String>();
		tempAttributes.add(StringR.ALL_COLUMN);
		tempAttributes.addAll(tableAttributes);
		for(int i = 0; i < tempAttributes.size(); i++) {
			SimpleToggleButton newButton = new SimpleToggleButton(
					BorderFactory.createLineBorder(ColorR.LIGHT_GRAY, 1),
					tempAttributes.get(i),
					(attributesPanel.getWidth() - DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN * (numberOfButton + 1)) / numberOfButton,
					DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT
					);
			newButton.setBackground(ColorR.WHITE);
			newButton.setForeground(ColorR.DARK_GRAY);
			
			final int index = i;
			if(i == 0) {
				newButton.setSelected();
				newButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						for(int j = 1; j < tableAttributesButtons.size(); j++)
							tableAttributesButtons.get(j).setUnSelected();
						columnString = getColumnsString(index);
						showSQLOperation();
					}
					
				});
			} else
				newButton.addActionListener(new ActionListener() {
	
					@Override
					public void actionPerformed(ActionEvent arg0) {
						tableAttributesButtons.get(0).setUnSelected();
						columnString = getColumnsString(index);
						showSQLOperation();
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
					sqlOperationPanel.getWidth() * 4 / 5 - 1,
					yPos
					);
			attributesPanel.setPreferredSize(
						new Dimension(
								sqlOperationPanel.getWidth() * 4 / 5 - 1,
								yPos
						)
					);
		}
		
		SimpleScrollPane attributeScrollPane = new SimpleScrollPane(attributesPanel);
		attributeScrollPane.setSize(
				sqlOperationPanel.getWidth() * 4 / 5,
				sqlOperationPanel.getHeight() / 2
				);
		attributeScrollPane.setLocation(
				wantToSeeLabel.getWidth(),
				0
				);
		attributeScrollPane.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.DARK_GRAY)
				);
		wantTdoSeeAttributePanel.add(attributeScrollPane);
		
		JPanel conditionPanel = new JPanel();
		conditionPanel.setLayout(null);
		conditionPanel.setBackground(ColorR.WHITE);
		conditionPanel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.DARK_GRAY)
				);
		conditionPanel.setSize(
				sqlOperationPanel.getWidth(),
				sqlOperationPanel.getHeight() * 1 / 2
				);
		conditionPanel.setPreferredSize(
					new Dimension(
							sqlOperationPanel.getWidth(),
							sqlOperationPanel.getHeight() * 1 / 2
					)
				);
		conditionPanel.setLocation(
				0,
				sqlOperationPanel.getHeight() * 1 / 2
				);
		
		sqlOperationPanel.add(conditionPanel);
		
		SimpleLabel conditionLabel = new SimpleLabel(StringR.CONDITION);
		conditionLabel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.LIGHT_GRAY)
				);
		conditionLabel.setSize(
				sqlOperationPanel.getWidth() / 5,
				sqlOperationPanel.getHeight() * 1 / 2 * 2 / 3
				);
		conditionLabel.setLocation(
				0,
				0
				);
		conditionPanel.add(conditionLabel);
		
		SimpleButton addConditionButton = new SimpleButton(StringR.ADD_CONDITION);
		addConditionButton.setBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.LIGHT_GRAY)
				);
		addConditionButton.setSize(
				sqlOperationPanel.getWidth() / 5,
				sqlOperationPanel.getHeight() * 1 / 2 * 1 / 3
				);
		addConditionButton.setLocation(
				0,
				conditionLabel.getHeight()
				);
		addConditionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddConditionFrame(
						StringR.ADD_CONDITION,
						TableEditPanel.this,
						tableName,
						DimenR.ADD_CONDITION_FRAME_WIDTH,
						DimenR.ADD_CONDITION_FRAME_HEIGHT
						);
			}
			
		});
		
		conditionListPanel = new JPanel();
		conditionListPanel.setLayout(null);
		conditionListPanel.setBackground(ColorR.WHITE);
		conditionListPanel.setSize(
				sqlOperationPanel.getWidth() * 4 / 5 - 1,
				sqlOperationPanel.getHeight() * 1 / 2
				);
		conditionListPanel.setPreferredSize(
					new Dimension(
							sqlOperationPanel.getWidth() * 4 / 5 - 1,
							sqlOperationPanel.getHeight() * 1 / 2
					)
				);
		conditionListPanel.setLocation(
				0,
				0
				);
		
		SimpleScrollPane coditionScrollPane = new SimpleScrollPane(conditionListPanel);
		coditionScrollPane.setBackground(ColorR.WHITE);
		coditionScrollPane.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.DARK_GRAY)
				);
		coditionScrollPane.setSize(
				sqlOperationPanel.getWidth() * 4 / 5,
				sqlOperationPanel.getHeight() * 1 / 2
				);
		coditionScrollPane.setLocation(
				conditionLabel.getWidth(),
				0
				);
		
		conditionPanel.add(addConditionButton);
		conditionPanel.add(coditionScrollPane);
		
		sqlOperationPanel.repaint();
	}
	
	public String getColumnsString(int index) {
		String selectedColumn = "";
		if(index == 0 && !tableAttributesButtons.get(0).isSelected())
			return "*";
		
		for(int i = 1; i < tableAttributesButtons.size(); i++)
			if((i == index && !tableAttributesButtons.get(index).isSelected()) || (i != index && tableAttributesButtons.get(i).isSelected()))
				selectedColumn += tableAttributesButtons.get(i).getText() + ", ";
		try{
			selectedColumn = selectedColumn.substring(0, selectedColumn.length() - 2);
		} catch(Exception e) {
			
		}
		return selectedColumn;
	}
	
	public void drawAddDataOperation() {
		sqlOperationPanel.removeAll();
		
		JPanel addDataPanel = new JPanel();
		addDataPanel.setLayout(null);
		addDataPanel.setBackground(ColorR.WHITE);
		addDataPanel.setSize(
				sqlOperationPanel.getWidth(),
				sqlOperationPanel.getHeight()
				);
		addDataPanel.setPreferredSize(
					new Dimension(
							sqlOperationPanel.getWidth() * 4 / 5,
							sqlOperationPanel.getHeight()
					)
				);
		addDataPanel.setLocation(0, 0);
		
		SimpleLabel newValueLabel = new SimpleLabel(StringR.SET_NEW_VALUE);
		newValueLabel.setBackground(ColorR.WHITE);
		newValueLabel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.DARK_GRAY)
				);
		newValueLabel.setSize(
				sqlOperationPanel.getWidth() * 1 / 5,
				sqlOperationPanel.getHeight() * 4 / 5
				);
		newValueLabel.setLocation(0, 0);
		addDataPanel.add(newValueLabel);
		
		SimpleButton addValueButton = new SimpleButton(StringR.OKAY);
		addValueButton.setSize(
				sqlOperationPanel.getWidth() * 1 / 5,
				sqlOperationPanel.getHeight() * 1 / 5
				);
		addValueButton.setLocation(0, newValueLabel.getHeight());
		addValueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				newValues = "";
				for(int i = 0; i < setValueTextFields.size(); i++) {
					if(i == 0)
						newValues = " VALUES(";
					String tempString = setValueTextFields.get(i).getText().toString();
					try{
						Integer.parseInt(tempString);
						newValues += tempString + ", ";
					} catch(Exception e) {
						if(!tempString.equals(""))
							newValues += "'" + tempString + "'" + ", ";
						else
							newValues += "null" + ", ";
					}
				}
				newValues = newValues.substring(0, newValues.length() - 2);
				newValues +=")";
				
				showSQLOperation();
			}
			
		});
		addDataPanel.add(addValueButton);
		
		JPanel attributeSetButtonPanel = new JPanel();
		attributeSetButtonPanel.setLayout(null);
		attributeSetButtonPanel.setBackground(ColorR.WHITE);
		attributeSetButtonPanel.setSize(
				sqlOperationPanel.getWidth() * 4 / 5 - 1,
				sqlOperationPanel.getHeight()
				);
		attributeSetButtonPanel.setPreferredSize(
					new Dimension(
							sqlOperationPanel.getWidth() * 4 / 5 - 1,
							sqlOperationPanel.getHeight()
					)
				);
		attributeSetButtonPanel.setLocation(0, 0);
		
		SimpleScrollPane attributeScrollPane = new SimpleScrollPane(attributeSetButtonPanel);
		attributeScrollPane.setBackground(ColorR.WHITE);
		attributeScrollPane.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, Color.DARK_GRAY)
				);
		attributeScrollPane.setSize(
				sqlOperationPanel.getWidth() * 4 / 5,
				sqlOperationPanel.getHeight()
				);
		attributeScrollPane.setLocation(newValueLabel.getWidth(), 0);
		addDataPanel.add(attributeScrollPane);
		
		tableAttributesButtons.clear();
		int yPos = DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
		int numberOfButton = 2;
		for(int i = 0; i < tableAttributes.size(); i++) {
			SimpleTextField newTextField = new SimpleTextField(tableAttributes.get(i));
			newTextField.setSize(
						(attributeSetButtonPanel.getWidth() - DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN * (numberOfButton + 1)) / numberOfButton,
						DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT
					);
			newTextField.setBorder(
					BorderFactory.createLineBorder(ColorR.LIGHT_GRAY, 1)
					);
			newTextField.setBackground(ColorR.WHITE);
			newTextField.setForeground(ColorR.DARK_GRAY);
			
			final int index = i;
				
			newTextField.setLocation(
					(i % numberOfButton) * newTextField.getWidth() + (i % numberOfButton + 1) * DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN,
					yPos
					);
			
			if(i % numberOfButton == numberOfButton - 1)
				yPos += DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT + DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
			
			attributeSetButtonPanel.add(newTextField);
			setValueTextFields.add(newTextField);
		}
		yPos += DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT + DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
		
		if(attributeSetButtonPanel.getHeight() < yPos) {
			attributeSetButtonPanel.setSize(
					sqlOperationPanel.getWidth() * 4 / 5 - 1,
					yPos
					);
			attributeSetButtonPanel.setPreferredSize(
						new Dimension(
								sqlOperationPanel.getWidth() * 4 / 5 - 1,
								yPos
						)
					);
		}
		
		sqlOperationPanel.add(addDataPanel);
		sqlOperationPanel.repaint();
	}

	public void drawDeleteDataOperation() {
		sqlOperationPanel.removeAll();
		
		JPanel conditionPanel = new JPanel();
		conditionPanel.setLayout(null);
		conditionPanel.setBackground(ColorR.WHITE);
		conditionPanel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.DARK_GRAY)
				);
		conditionPanel.setSize(
				sqlOperationPanel.getWidth(),
				sqlOperationPanel.getHeight()
				);
		conditionPanel.setPreferredSize(
					new Dimension(
							sqlOperationPanel.getWidth(),
							sqlOperationPanel.getHeight()
					)
				);
		conditionPanel.setLocation(
				0,
				0
				);
		
		sqlOperationPanel.add(conditionPanel);
		
		SimpleLabel conditionLabel = new SimpleLabel(StringR.CONDITION);
		conditionLabel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.LIGHT_GRAY)
				);
		conditionLabel.setSize(
				sqlOperationPanel.getWidth() / 5,
				sqlOperationPanel.getHeight() * 2 / 3
				);
		conditionLabel.setLocation(
				0,
				0
				);
		conditionPanel.add(conditionLabel);
		
		SimpleButton addConditionButton = new SimpleButton(StringR.ADD_CONDITION);
		addConditionButton.setBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.LIGHT_GRAY)
				);
		addConditionButton.setSize(
				sqlOperationPanel.getWidth() / 5,
				sqlOperationPanel.getHeight() * 1 / 3
				);
		addConditionButton.setLocation(
				0,
				conditionLabel.getHeight()
				);
		addConditionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddConditionFrame(
						StringR.ADD_CONDITION,
						TableEditPanel.this,
						tableName,
						DimenR.ADD_CONDITION_FRAME_WIDTH,
						DimenR.ADD_CONDITION_FRAME_HEIGHT
						);
			}
			
		});
		
		conditionListPanel = new JPanel();
		conditionListPanel.setLayout(null);
		conditionListPanel.setBackground(ColorR.WHITE);
		conditionListPanel.setSize(
				sqlOperationPanel.getWidth() * 4 / 5 - 1,
				sqlOperationPanel.getHeight()
				);
		conditionListPanel.setPreferredSize(
					new Dimension(
							sqlOperationPanel.getWidth() * 4 / 5 - 1,
							sqlOperationPanel.getHeight()
					)
				);
		conditionListPanel.setLocation(
				0,
				0
				);
		
		SimpleScrollPane coditionScrollPane = new SimpleScrollPane(conditionListPanel);
		coditionScrollPane.setBackground(ColorR.WHITE);
		coditionScrollPane.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.DARK_GRAY)
				);
		coditionScrollPane.setSize(
				sqlOperationPanel.getWidth() * 4 / 5,
				sqlOperationPanel.getHeight()
				);
		coditionScrollPane.setLocation(
				conditionLabel.getWidth(),
				0
				);
		
		conditionPanel.add(addConditionButton);
		conditionPanel.add(coditionScrollPane);
		
		sqlOperationPanel.repaint();
	}

	public void drawUpdateDataOperation() {
		sqlOperationPanel.removeAll();

		JPanel addDataPanel = new JPanel();
		addDataPanel.setLayout(null);
		addDataPanel.setBackground(ColorR.WHITE);
		addDataPanel.setSize(
				sqlOperationPanel.getWidth(),
				sqlOperationPanel.getHeight() / 2
				);
		addDataPanel.setPreferredSize(
					new Dimension(
							sqlOperationPanel.getWidth() * 4 / 5,
							sqlOperationPanel.getHeight() / 2
					)
				);
		addDataPanel.setLocation(0, 0);
		
		SimpleLabel newValueLabel = new SimpleLabel(StringR.SET_NEW_VALUE);
		newValueLabel.setBackground(ColorR.WHITE);
		newValueLabel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.LIGHT_GRAY)
				);
		newValueLabel.setSize(
				sqlOperationPanel.getWidth() * 1 / 5,
				sqlOperationPanel.getHeight() * 4 / 5 / 2
				);
		newValueLabel.setLocation(0, 0);
		addDataPanel.add(newValueLabel);
				
		SimpleButton addValueButton = new SimpleButton(StringR.OKAY);
		addValueButton.setSize(
				sqlOperationPanel.getWidth() * 1 / 5,
				sqlOperationPanel.getHeight() * 1 / 5 / 2
				);
		addValueButton.setLocation(0, newValueLabel.getHeight());
		addValueButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				newValues = "";
				for(int i = 0; i < setValueTextFields.size(); i++) {
					if(i == 0)
						newValues = " SET ";

					String tempString = setValueTextFields.get(i).getText().toString();
					
					if(!tempString.equals(""))
						newValues += tableAttributes.get(i) + " = ";
					try{
						Integer.parseInt(tempString);
						newValues += tempString + ", ";
					} catch(Exception e) {
						if(!tempString.equals(""))
							newValues += "'" + tempString + "'" + ", ";
					}
				}
				newValues = newValues.substring(0, newValues.length() - 2);
				
				showSQLOperation();
			}
			
		});
		addDataPanel.add(addValueButton);
		
		JPanel attributeSetButtonPanel = new JPanel();
		attributeSetButtonPanel.setLayout(null);
		attributeSetButtonPanel.setBackground(ColorR.WHITE);
		attributeSetButtonPanel.setSize(
				sqlOperationPanel.getWidth() * 4 / 5 - 1,
				sqlOperationPanel.getHeight() / 2
				);
		attributeSetButtonPanel.setPreferredSize(
					new Dimension(
							sqlOperationPanel.getWidth() * 4 / 5 - 1,
							sqlOperationPanel.getHeight() / 2
					)
				);
		attributeSetButtonPanel.setLocation(0, 0);
		
		
		
		SimpleScrollPane attributeScrollPane = new SimpleScrollPane(attributeSetButtonPanel);
		attributeScrollPane.setBackground(ColorR.WHITE);
		attributeScrollPane.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, Color.DARK_GRAY)
				);
		attributeScrollPane.setSize(
				sqlOperationPanel.getWidth() * 4 / 5,
				sqlOperationPanel.getHeight() / 2
				);
		attributeScrollPane.setLocation(newValueLabel.getWidth(), 0);
		addDataPanel.add(attributeScrollPane);
		
		
		tableAttributesButtons.clear();
		int yPos = DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
		int numberOfButton = 2;
		for(int i = 0; i < tableAttributes.size(); i++) {
			SimpleTextField newTextField = new SimpleTextField(tableAttributes.get(i));
			newTextField.setSize(
						(attributeSetButtonPanel.getWidth() - DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN * (numberOfButton + 1)) / numberOfButton,
						DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT
					);
			newTextField.setBorder(
					BorderFactory.createLineBorder(ColorR.LIGHT_GRAY, 1)
					);
			newTextField.setBackground(ColorR.WHITE);
			newTextField.setForeground(ColorR.DARK_GRAY);
			
			final int index = i;
				
			newTextField.setLocation(
					(i % numberOfButton) * newTextField.getWidth() + (i % numberOfButton + 1) * DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN,
					yPos
					);
			
			if(i % numberOfButton == numberOfButton - 1)
				yPos += DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT + DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
			
			attributeSetButtonPanel.add(newTextField);
			setValueTextFields.add(newTextField);
		}
		yPos += DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT + DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
		
		if(attributeSetButtonPanel.getHeight() < yPos) {
			attributeSetButtonPanel.setSize(
					sqlOperationPanel.getWidth() * 4 / 5 - 1,
					yPos
					);
			attributeSetButtonPanel.setPreferredSize(
						new Dimension(
								sqlOperationPanel.getWidth() * 4 / 5 - 1,
								yPos
						)
					);
		}
		
		sqlOperationPanel.add(addDataPanel);
		
		
		
		
		JPanel conditionPanel = new JPanel();
		conditionPanel.setLayout(null);
		conditionPanel.setBackground(ColorR.WHITE);
		conditionPanel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.DARK_GRAY)
				);
		conditionPanel.setSize(
				sqlOperationPanel.getWidth(),
				sqlOperationPanel.getHeight() / 2
				);
		conditionPanel.setPreferredSize(
					new Dimension(
							sqlOperationPanel.getWidth(),
							sqlOperationPanel.getHeight() / 2
					)
				);
		conditionPanel.setLocation(
				0,
				sqlOperationPanel.getHeight() / 2
				);
		sqlOperationPanel.add(conditionPanel);
		
		
		SimpleLabel conditionLabel = new SimpleLabel(StringR.CONDITION);
		conditionLabel.setBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.LIGHT_GRAY)
				);
		conditionLabel.setSize(
				sqlOperationPanel.getWidth() / 5,
				sqlOperationPanel.getHeight() * 2 / 3 / 2
				);
		conditionLabel.setLocation(
				0,
				0
				);
		conditionPanel.add(conditionLabel);
		
		SimpleButton addConditionButton = new SimpleButton(StringR.ADD_CONDITION);
		addConditionButton.setBorder(
				BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.LIGHT_GRAY)
				);
		addConditionButton.setSize(
				sqlOperationPanel.getWidth() / 5,
				sqlOperationPanel.getHeight() * 1 / 3 / 2
				);
		addConditionButton.setLocation(
				0,
				conditionLabel.getHeight()
				);
		addConditionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new AddConditionFrame(
						StringR.ADD_CONDITION,
						TableEditPanel.this,
						tableName,
						DimenR.ADD_CONDITION_FRAME_WIDTH,
						DimenR.ADD_CONDITION_FRAME_HEIGHT
						);
			}
		});
		
		conditionListPanel = new JPanel();
		conditionListPanel.setLayout(null);
		conditionListPanel.setBackground(ColorR.WHITE);
		conditionListPanel.setSize(
				sqlOperationPanel.getWidth() * 4 / 5 - 1,
				sqlOperationPanel.getHeight() / 2
				);
		conditionListPanel.setPreferredSize(
					new Dimension(
							sqlOperationPanel.getWidth() * 4 / 5 - 1,
							sqlOperationPanel.getHeight() / 2
					)
				);
		conditionListPanel.setLocation(
				0,
				0
				);
		
		SimpleScrollPane coditionScrollPane = new SimpleScrollPane(conditionListPanel);
		coditionScrollPane.setBackground(ColorR.WHITE);
		coditionScrollPane.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.DARK_GRAY)
				);
		coditionScrollPane.setSize(
				sqlOperationPanel.getWidth() * 4 / 5,
				sqlOperationPanel.getHeight() / 2
				);
		coditionScrollPane.setLocation(
				conditionLabel.getWidth(),
				0
				);
		
		conditionPanel.add(addConditionButton);
		conditionPanel.add(coditionScrollPane);
		
		sqlOperationPanel.repaint();
	}
	
	public void makeCondition(String newCondition) {
		if(!newCondition.equals(""))
			conditions.add(newCondition);
		
		if(conditions.size() == 0)
			conditionString = "";
		else
			conditionString = " WHERE ";
		conditionListPanel.removeAll();
		int yPos = DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
		for(int i = 0; i < conditions.size(); i++) {
			String temp = conditions.get(i);
			SimpleLabel conditionLabel = new SimpleLabel(temp);
			conditionLabel.setBorder(
					BorderFactory.createLineBorder(ColorR.LIGHT_GRAY, 1)
					);
			conditionLabel.setSize(
					(conditionListPanel.getWidth() - DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN * 2) * 4 / 5,
					DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT
					);
			conditionLabel.setLocation(
					DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN,
					yPos
					);
			
			SimpleImageButton conditionDeleteButton = new SimpleImageButton(
					new ImageIcon("src/Image/delete_button.png"),
					(conditionListPanel.getWidth() - DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN * 2) * 1 / 15,
					(conditionListPanel.getWidth() - DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN * 2) * 1 / 15,
					(conditionListPanel.getWidth() - DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN * 2) * 1 / 15,
					(conditionListPanel.getWidth() - DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN * 2) * 1 / 15
					);
			conditionDeleteButton.setLocation(
					conditionLabel.getX() + conditionLabel.getWidth() + DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN,
					conditionLabel.getY()
					);
			final int index = i;
			conditionDeleteButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					conditions.remove(index);
					makeCondition("");
				}
				
			});
			
			yPos += DimenR.TABLE_ATTRIBUTE_LABEL_HEIGHT + DimenR.TABLE_ATTRIBUTE_BUTTON_MARGIN;
			conditionString += temp + " AND ";
			conditionListPanel.add(conditionLabel);
			conditionListPanel.add(conditionDeleteButton);
		}
		if(conditionListPanel.getHeight() < yPos) {
			conditionListPanel.setSize(
					sqlOperationPanel.getWidth() * 4 / 5 - 1,
					yPos
					);
			conditionListPanel.setPreferredSize(
					new Dimension(
							sqlOperationPanel.getWidth() * 4 / 5 - 1,
							yPos
					)
				);
		}
		conditionListPanel.repaint();
		
		if(conditions.size() >= 1)
			conditionString = conditionString.substring(0, conditionString.length() - 5) + " ";
		showSQLOperation();
	}
}
