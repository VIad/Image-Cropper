import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class DisplayPanel extends JPanel {
	
	private Application	app		= Application.getApp();
	
	private ImageLoader	loader	= app.getImageLoader();
	
	private Cropper		crop	= app.getCrop();
	
	@Override
	protected void paintComponent(Graphics graphics)
	{
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		super.paintComponent(g);
		this.setBackground(Color.LIGHT_GRAY);
		
		if(loader.getImage() != null)
			g.drawImage(loader.getImage(), 0, 0, null);
		
		if(crop.getRect() != null)
		{
			g.setColor(new Color(0,0,0,200));
			g.fillRect(0, 0, app.getFrame().getSize().width, app.getFrame().getSize().height);
			Color old = g.getColor();
			g.setColor(new Color(255,255,255,100));
			g.fill(crop.getRect());
			g.setColor(old);
		}

		
		
		this.repaint();
	}
	
}
