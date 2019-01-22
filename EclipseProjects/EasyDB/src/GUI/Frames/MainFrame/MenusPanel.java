package GUI.Frames.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import GUI.Component.SimpleJFrame;
import GUI.Component.SimpleSelectButton;
import Resource.ColorR;
import Resource.DBProvider;
import Resource.DimenR;
import Resource.Menus;
import Resource.StringR;

/**
 * This is Menu Panel of Main Frame
 * which show menus
 * @author DongKyu
 */
public class MenusPanel extends JPanel {
	
	private ArrayList<SimpleSelectButton> menuButtons;

	public MenusPanel(SimpleJFrame context) {
		setLayout(null);
		menuButtons = new ArrayList<SimpleSelectButton>();
		
		for(int i = 0; i < 1; i++) {
			SimpleSelectButton newButton = new SimpleSelectButton(
					menuButtons,
					null,
					Menus.values()[i].getString(),
					DimenR.MENU_PANEL_WIDTH - DimenR.MENU_BUTTON_MARGIN * 2,
					DimenR.BUTTON_HEIGHT
					);
			
			ActionListener listener = null;
			
			switch(Menus.values()[i]) {
			case TABLES:
				listener = new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						DBProvider.getInstance().getTableNamesQuery();
						((MainFrame)context).getMiddleScrollPane().setViewportView(new TableListPanel(context));
						((MainFrame)context).getEditMenuPanel().makeTableListMenu();
					}
				};
				break;
				
			case DIAGRAM:
				break;
			}
			
			newButton.addActionListener(listener);
			
			newButton.setLocation(
					DimenR.MENU_BUTTON_MARGIN,
					i * DimenR.BUTTON_HEIGHT + (i + 1) * DimenR.MENU_BUTTON_MARGIN
					);
			
			menuButtons.add(newButton);
			add(newButton);
		}
		
		
		this.setSize(DimenR.MENU_PANEL_WIDTH, context.getHeight());
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.DARK_GRAY));
		this.setBackground(ColorR.MORE_LIGHT_GRAY);
	}
	
}
