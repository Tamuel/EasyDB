package GUI.Frames.MainFrame;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import GUI.Component.SimpleImageButton;
import GUI.Component.SimpleJFrame;
import Resource.ColorR;
import Resource.DimenR;
import Resource.StringR;

/**
 * Panel for show List Edit menus
 * like add table, delete table etc.
 * @author DongKyu
 *
 */
public class EditMenuPanel extends JPanel {
	
	private SimpleJFrame context;
	
	private SimpleImageButton addTableButton;
	private SimpleImageButton selectTableButton;
	private SimpleImageButton deleteTableButton;
	
	public EditMenuPanel(SimpleJFrame context) {
		setLayout(null);
		this.context = context;

		this.setBackground(ColorR.WHITE);
		this.setSize(
				DimenR.TABLE_LIST_PANEL_WIDTH,
				DimenR.EDIT_MENU_PANEL_HEIGHT
				);
	}
	
	public void makeTableEditMenu() {
		removeAll();
		this.removeAll();
		
		repaint();
	}
	
	public void makeTableListMenu() {
		removeAll();

		if(addTableButton == null) {
			addTableButton = new SimpleImageButton(
					new ImageIcon("src/Image/add_button.png"),
					new ImageIcon("src/Image/add_button_pressed.png"),
					DimenR.EDIT_MENU_BUTTON_SIZE,
					DimenR.EDIT_MENU_BUTTON_SIZE,
					DimenR.EDIT_MENU_BUTTON_SIZE,
					DimenR.EDIT_MENU_BUTTON_SIZE
					);
			addTableButton.setLocation(
					DimenR.MENU_BUTTON_MARGIN,
					(DimenR.EDIT_MENU_PANEL_HEIGHT - DimenR.EDIT_MENU_BUTTON_SIZE) / 2
					);
			addTableButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					new AddTableFrame(
							StringR.ADD_TABLE,
							DimenR.ADD_TABLE_FRAME_WIDTH,
							DimenR.ADD_TABLE_FRAME_HEIGHT
							);
				}
			});
		}
		
		if(selectTableButton == null) {
			selectTableButton = new SimpleImageButton(
					new ImageIcon("src/Image/select_button.png"),
					new ImageIcon("src/Image/select_button_pressed.png"),
					DimenR.EDIT_MENU_BUTTON_SIZE,
					DimenR.EDIT_MENU_BUTTON_SIZE,
					DimenR.EDIT_MENU_BUTTON_SIZE,
					DimenR.EDIT_MENU_BUTTON_SIZE
					);
			selectTableButton.setLocation(
					addTableButton.getX() + DimenR.MENU_BUTTON_MARGIN + DimenR.EDIT_MENU_BUTTON_SIZE,
					(DimenR.EDIT_MENU_PANEL_HEIGHT - DimenR.EDIT_MENU_BUTTON_SIZE) / 2
					);
			selectTableButton.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					((TableListPanel)((MainFrame)context)
							.getMiddleScrollPane().getViewport().getView())
							.toggleSelectIcon();
					
					if(deleteTableButton.isVisible())
						deleteTableButton.setVisible(false);
					else
						deleteTableButton.setVisible(true);
				}
				
			});
		}
		
		if(deleteTableButton == null){
			deleteTableButton = new SimpleImageButton(
					new ImageIcon("src/Image/delete_button.png"),
					DimenR.EDIT_MENU_BUTTON_SIZE,
					DimenR.EDIT_MENU_BUTTON_SIZE,
					DimenR.EDIT_MENU_BUTTON_SIZE,
					DimenR.EDIT_MENU_BUTTON_SIZE
					);
			deleteTableButton.setLocation(
					selectTableButton.getX() + DimenR.MENU_BUTTON_MARGIN + DimenR.EDIT_MENU_BUTTON_SIZE,
					(DimenR.EDIT_MENU_PANEL_HEIGHT - DimenR.EDIT_MENU_BUTTON_SIZE) / 2
					);
			deleteTableButton.addActionListener(new ActionListener() {
	
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(((MainFrame)context).getMiddleScrollPane().getViewport().getView().toString());
					
					((TableListPanel)((MainFrame)context)
							.getMiddleScrollPane().getViewport().getView())
							.removeSelectedTables();
					
					deleteTableButton.setVisible(false);
				}
				
			});
		}

		deleteTableButton.setVisible(false);
		add(addTableButton);
		add(selectTableButton);
		add(deleteTableButton);
		
		this.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.DARK_GRAY)
				);
		repaint();
	}

}
