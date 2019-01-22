package GUI.Frames.MainFrame;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import GUI.Component.SimpleJFrame;
import GUI.Component.SimpleScrollPane;
import Resource.ColorR;
import Resource.DimenR;

/**
 * This is Main Frame of program
 * which show menus and preview of Table
 * @author DongKyu
 */
public class MainFrame extends SimpleJFrame {
	/** Panel for menus */
	private MenusPanel menusPanel;
	/** Scroll pane for middle panel */
	private SimpleScrollPane middleScrollPane;
	/** Panel for edit menus */
	private EditMenuPanel editMenuPanel;
	
	private SimpleScrollPane tableScrollPane;

	public MainFrame(String frameName, int width, int height) {
		super(frameName, width, height);
		menusPanel = new MenusPanel(this);
		
		editMenuPanel = new EditMenuPanel(this);
		editMenuPanel.setLocation(DimenR.MENU_PANEL_WIDTH, 0);
		
		middleScrollPane = new SimpleScrollPane(null);
		middleScrollPane.setLocation(
				DimenR.MENU_PANEL_WIDTH,
				DimenR.EDIT_MENU_PANEL_HEIGHT
				);
		middleScrollPane.setSize(
				DimenR.TABLE_LIST_PANEL_WIDTH,
				height - DimenR.EDIT_MENU_PANEL_HEIGHT
				);
		middleScrollPane.setBackground(ColorR.WHITE);

		tableScrollPane = new SimpleScrollPane(null);
		tableScrollPane.setBackground(ColorR.WHITE);
		tableScrollPane.setBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 1, ColorR.DARK_GRAY)
				);
		tableScrollPane.setLocation(DimenR.MENU_PANEL_WIDTH + DimenR.TABLE_LIST_PANEL_WIDTH, 0);
		tableScrollPane.setSize(
				getWidth() - DimenR.MENU_PANEL_WIDTH - DimenR.TABLE_LIST_PANEL_WIDTH,
				getHeight()
				);
		
		add(menusPanel);
		add(editMenuPanel);
		add(middleScrollPane);
		add(tableScrollPane);
		repaint();
	}
	
	public SimpleScrollPane getTablePanel() {
		return tableScrollPane;
	}

	public void setTablePanel(SimpleScrollPane tablePanel) {
		this.tableScrollPane = tablePanel;
	}

	public EditMenuPanel getEditMenuPanel() {
		return editMenuPanel;
	}

	public void setEditMenuPanel(EditMenuPanel editMenuPanel) {
		this.editMenuPanel = editMenuPanel;
	}

	public SimpleScrollPane getMiddleScrollPane() {
		return middleScrollPane;
	}
}