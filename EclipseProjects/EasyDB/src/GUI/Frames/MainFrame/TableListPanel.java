package GUI.Frames.MainFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import GUI.Component.SimpleJFrame;
import GUI.Component.SimpleSelectButton;
import GUI.Component.SimpleToggleImageButton;
import Resource.ColorR;
import Resource.DBProvider;
import Resource.DimenR;
import Resource.Menus;
import Resource.StringR;

/**
 * Panel for show table lists
 * @author DongKyu
 *
 */
public class TableListPanel extends JPanel {
	private ArrayList<SimpleSelectButton> tableListButtons;
	private ArrayList<SimpleToggleImageButton> selectButtons;
	
	private SimpleJFrame context;
	
	private boolean selectIconOn = false;
	
	public TableListPanel(SimpleJFrame context) {
		setLayout(null);
		tableListButtons = new ArrayList<SimpleSelectButton>();
		selectButtons = new ArrayList<SimpleToggleImageButton>();
		
		this.context = context;
		
		repaintTableList();
		
		this.setBackground(ColorR.WHITE);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.DARK_GRAY));
	}
	
	public void toggleSelectIcon() {
		if(!selectIconOn) {
			for(int i = 0; i < tableListButtons.size(); i++){
				SimpleToggleImageButton newButton = new SimpleToggleImageButton(
						new ImageIcon("src/Image/non_selected_button.png"),
						new ImageIcon("src/Image/selected_button.png"),
						DimenR.TABLE_LIST_SELECT_BUTTON_SIZE,
						DimenR.TABLE_LIST_SELECT_BUTTON_SIZE,
						DimenR.TABLE_LIST_SELECT_BUTTON_SIZE,
						DimenR.TABLE_LIST_SELECT_BUTTON_SIZE
						);
				newButton.setLocation(
						tableListButtons.get(i).getLocation().x - DimenR.TABLE_LIST_SELECT_BUTTON_SIZE / 2,
						tableListButtons.get(i).getLocation().y - DimenR.TABLE_LIST_SELECT_BUTTON_SIZE / 2
						);
				
				selectButtons.add(newButton);
				add(newButton);
				setComponentZOrder(newButton, 0);
			}
			repaint();
			selectIconOn = true;
		} else {
			for(int i = 0; i < tableListButtons.size(); i++)
				remove(selectButtons.get(i));
			selectButtons.clear();
			repaint();
			selectIconOn = false;
		}
	}
	
	/**
	 * Remove selected table from DB
	 * and repaint table list
	 */
	public void removeSelectedTables() {
		if(selectIconOn) {
			
			ArrayList<String> dropTableNames = new ArrayList<String>();
			
			for(int i = 0; i < selectButtons.size(); i++) {
				if(selectButtons.get(i).isSelected()) {
					dropTableNames.add(tableListButtons.get(i).getText().toString());
					System.out.println(tableListButtons.get(i).getText().toString());
				}
			}
			
			for(String tempTableName : dropTableNames)
				DBProvider.getInstance().dropTableQuery(tempTableName);

			toggleSelectIcon();
			repaintTableList();
			selectIconOn = false;
		}
	}
	
	public void repaintTableList() {
		for(int i = 0; i < tableListButtons.size(); i++)
			remove(tableListButtons.get(i));
		tableListButtons.clear();
		
		/**
		 * Button String for show Table name and attributes for user
		 * we should get Table information from DataBase
		 */
		DBProvider.getInstance().getTableNamesQuery();
		ArrayList<String> buttonNames = DBProvider.getInstance().getTableNames();
		
		int yPos = DimenR.TABLE_LIST_BUTTON_MARGIN;
		for(int i = 0; i < buttonNames.size(); i++) {
			SimpleSelectButton newButton = new SimpleSelectButton(
					tableListButtons,
					BorderFactory.createLineBorder(ColorR.GRAY, 1),
					buttonNames.get(i),
					DimenR.TABLE_LIST_BUTTON_WIDTH,
					DimenR.TABLE_LIST_BUTTON_HEIGHT
					);
			
			newButton.setBackground(ColorR.WHITE);
			newButton.setForeground(Color.DARK_GRAY);
			
			ActionListener listener = null;

			final String frameN = buttonNames.get(i);
			listener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					DBProvider.getInstance().getTableAttrubutesQuery(frameN);
					((MainFrame)context).getEditMenuPanel().makeTableEditMenu();
					((MainFrame)context).getMiddleScrollPane().setViewportView(new TableEditPanel(context, frameN));
				}
			};
			
			newButton.addActionListener(listener);
			
			if(i != 0 && i % 4 == 0)
				yPos += DimenR.TABLE_LIST_BUTTON_HEIGHT + DimenR.TABLE_LIST_BUTTON_MARGIN;
			
			newButton.setLocation(
					(i % 4 + 1) * DimenR.TABLE_LIST_BUTTON_MARGIN +
					(i % 4) * DimenR.TABLE_LIST_BUTTON_WIDTH,
					yPos
					);
			
			tableListButtons.add(newButton);
			add(newButton);
		}
		
		yPos += DimenR.TABLE_LIST_BUTTON_HEIGHT + DimenR.TABLE_LIST_BUTTON_MARGIN;
		if(yPos < context.getHeight() - DimenR.EDIT_MENU_PANEL_HEIGHT)
			yPos = context.getHeight() - DimenR.EDIT_MENU_PANEL_HEIGHT;
		
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
		
		repaint();
	}
}
