package GUI.Frames.SplashFrame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import GUI.Component.SimpleJFrame;
import Resource.ColorR;
import Resource.DimenR;

public class DrawSplashImagePanel extends JPanel {
	private BufferedImage icon;
	
	public DrawSplashImagePanel(SimpleJFrame context) {
		super();
		setLayout(null);
		
		try {
			icon = ImageIO.read(new File("src/Image/easyDB.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setSize(context.getWidth(), context.getHeight());
		this.setBackground(ColorR.DARK_GRAY);
	}
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D)g).setRenderingHint(
        		RenderingHints.KEY_ANTIALIASING,
        		RenderingHints.VALUE_ANTIALIAS_ON
        		);

        g.drawImage(
        		icon,
        		0,
        		0,
        		this);
    }
}
