package GUI.Frames.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.Component.SimpleButton;
import GUI.Component.SimpleJFrame;
import GUI.Component.SimpleTextField;
import Resource.DBProvider;
import Resource.DimenR;
import Resource.StringR;

public class AddColFrame extends SimpleJFrame {
	
	/** Text field for input table name */
	private SimpleTextField colNameField;
	private SimpleTextField colTypeField;
	/** Button for add table */
	private SimpleButton addButton;
	/** Button for cancel */
	private SimpleButton cancelButton;

	public AddColFrame(String table_name, String frameName, int width, int height) {
		
		super(frameName, width * 2 + 5, height);
		width = width * 2 + 5;
		colNameField = new SimpleTextField("데이터 이름");
		colNameField.setSize(
				((width-5) - DimenR.ADD_TABLE_MARGIN * 2)/2,
				DimenR.BUTTON_HEIGHT
				);
		colNameField.setLocation(
				DimenR.ADD_TABLE_MARGIN,
				DimenR.ADD_TABLE_MARGIN
				);
		
		colTypeField = new SimpleTextField("데이터 타입");
		colTypeField.setSize(
				((width-5) - DimenR.ADD_TABLE_MARGIN * 2)/2,
				DimenR.BUTTON_HEIGHT
				);
		colTypeField.setLocation(
				DimenR.ADD_TABLE_MARGIN +((width-5) - DimenR.ADD_TABLE_MARGIN * 2)/2+5,
				DimenR.ADD_TABLE_MARGIN
				);
		
		
	
		addButton = new SimpleButton(StringR.ADD);
		addButton.setSize(
				(width - DimenR.ADD_TABLE_MARGIN * 3) / 2,
				DimenR.BUTTON_HEIGHT
				);
		addButton.setLocation(
				DimenR.ADD_TABLE_MARGIN,
				colNameField.getY() + DimenR.BUTTON_HEIGHT + DimenR.ADD_TABLE_MARGIN
				);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO - Add new table operation here
				
				String sql = "ALTER TABLE " + table_name+" ADD ("+colNameField.getText()+" "+colTypeField.getText()+")";
				
				DBProvider.getInstance().createQuery(
						sql
						);
				colNameField.setText("데이터 이름");
				colTypeField.setText("데이터 타입");
				
			}
		});
		
		
		cancelButton = new SimpleButton("추가 완료");
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
		
		add(colNameField);
		add(colTypeField);
		add(addButton);
		add(cancelButton);
		
	}

}
