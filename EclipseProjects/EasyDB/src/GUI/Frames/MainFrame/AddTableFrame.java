package GUI.Frames.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.Component.SimpleButton;
import GUI.Component.SimpleJFrame;
import GUI.Component.SimpleTextField;
import Resource.DBProvider;
import Resource.DimenR;
import Resource.StringR;

public class AddTableFrame extends SimpleJFrame {
	
	/** Text field for input table name */
	private SimpleTextField tableNameField;
	/** Button for add table */
	private SimpleButton addButton;
	/** Button for cancel */
	private SimpleButton cancelButton;

	public AddTableFrame(String frameName, int width, int height) {
		super(frameName, width, height);
		
		tableNameField = new SimpleTextField(StringR.ADD_TABLE_FIELD);
		tableNameField.setSize(
				width - DimenR.ADD_TABLE_MARGIN * 2,
				DimenR.BUTTON_HEIGHT
				);
		tableNameField.setLocation(
				DimenR.ADD_TABLE_MARGIN,
				DimenR.ADD_TABLE_MARGIN
				);
	
		addButton = new SimpleButton(StringR.ADD);
		addButton.setSize(
				(width - DimenR.ADD_TABLE_MARGIN * 3) / 2,
				DimenR.BUTTON_HEIGHT
				);
		addButton.setLocation(
				DimenR.ADD_TABLE_MARGIN,
				tableNameField.getY() + DimenR.BUTTON_HEIGHT + DimenR.ADD_TABLE_MARGIN
				);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO - Add new table operation here
				
				String table_name = tableNameField.getText();
				String sql = "CREATE TABLE " + table_name+"(id integer)";
				
				DBProvider.getInstance().createQuery(
						sql
						);
				
				new AddColFrame(
						table_name,
						StringR.ADD_TABLE,
						DimenR.ADD_TABLE_FRAME_WIDTH,
						DimenR.ADD_TABLE_FRAME_HEIGHT
						);
				
				
				
				dispose();
			}
		});
		
		cancelButton = new SimpleButton(StringR.CANCEL);
		cancelButton.setSize(
				(width - DimenR.ADD_TABLE_MARGIN * 3) / 2,
				DimenR.BUTTON_HEIGHT
				);
		cancelButton.setLocation(
				addButton.getX() + addButton.getWidth() + DimenR.ADD_TABLE_MARGIN,
				addButton.getY()
				);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		add(tableNameField);
		add(addButton);
		add(cancelButton);
		
	}

}

