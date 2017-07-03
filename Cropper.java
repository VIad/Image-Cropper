import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Cropper {
	
	private Rectangle	rect;
	
	private Point		pressPoint, releasePoint;
	
	private Application	app;
	
	public Cropper(Application app)
	{
		this.app = app;
	}
	
	public void setRect(Rectangle rect)
	{
		this.rect = rect;
	}
	
	public void setRect(int x, int y, int width, int height)
	{
		this.rect = new Rectangle(x, y, width, height);
	}
	
	public void setPressPoint(Point pressPoint)
	{
		this.pressPoint = pressPoint;
	}
	
	public void setReleasePoint(Point releasePoint)
	{
		this.releasePoint = releasePoint;
	}
	
	public void createRect()
	{
		int width = this.releasePoint.x - this.pressPoint.x;
		int height = this.releasePoint.y - this.pressPoint.y;
		rect = new Rectangle(pressPoint.x, pressPoint.y, width, height);
	}
	
	public Rectangle getRect()
	{
		return rect;
	}
	
	public void onRelease()
	{
		if(this.rect.width < 10 || this.rect.height < 10)
		{
			this.rect = null;
			app.set(false);
			
		}
	}
	
	public void save()
	{
		if(this.rect != null)
		{
			
			Object [] options = {
					"Save",
					"Don't save"
			};
			
			ImageIcon ic = new ImageIcon(app.getImageLoader().getSubImage(this.rect.x, this.rect.y, this.rect.width, this.rect.height));
			int c = JOptionPane.showOptionDialog(null, null, "Preview", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, ic, options, options[0]);
			if(c == JOptionPane.OK_OPTION)
			{
				JFileChooser chooser = new JFileChooser();
				int ch = chooser.showSaveDialog(null);
				if(ch == JFileChooser.APPROVE_OPTION)
				{
					BufferedImage toSave = app.getImageLoader().getSubImage(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
					try
					{
						ImageIO.write(toSave, "PNG", new File(chooser.getSelectedFile().getAbsolutePath() + ".PNG"));
					}
					catch(IOException e)
					{}
				}
			} else
			{
				
			}
		}
	}
	
}
