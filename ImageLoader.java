import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageLoader {
	
	private BufferedImage		image;
	
	private Application			app;
	
	
	public ImageLoader(Application app)
	{
		image = null;
		this.app = app;
	}
	
	public void loadImage(String path)
	{
		try
		{
			image = ImageIO.read(new File(path));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadImage(BufferedImage image)
	{
		this.image = image;
		app.onImageSelected(image.getWidth(), image.getHeight());
	}
	
	public void loadImageFileChooser()
	{
		JFileChooser filechooser = new JFileChooser();
		filechooser.setFileFilter(new FileNameExtensionFilter("IMAGE FILES", "JPG", "PNG", "JPEG", "GIF"));
		int c = filechooser.showOpenDialog(null);
		if(c == JFileChooser.APPROVE_OPTION)
			try
			{
				image = ImageIO.read(filechooser.getSelectedFile());
				app.onImageSelected(image.getWidth(), image.getHeight());
				app.setImageOpened(true);
			}
			catch(Exception e)
			{}
	}
	
	public void save(BufferedImage img, String path)
	{
		try
		{
			ImageIO.write(img, "PNG", new File(path));
		}
		catch(IOException e)
		{}
	}
	
	public void save(BufferedImage img, int pos)
	{
		try
		{
			ImageIO.write(img, "PNG", new File("C:\\CropShot\\Screenshots\\Screenshot_" + pos+".PNG"));
		}
		catch(IOException e)
		{}
	}
	
	public void save(BufferedImage img)
	{
		JFileChooser chooser = new JFileChooser();
		int n = chooser.showSaveDialog(null);
		if(n == JFileChooser.APPROVE_OPTION)
			try
			{
				ImageIO.write(img, "PNG", new File(chooser.getSelectedFile().getAbsolutePath() + ".PNG"));
			}
			catch(IOException e)
			{}
	}
	
	public BufferedImage getImage()
	{
		return image;
	}
	
	public Image getImageAbs()
	{
		return (Image) image;
	}
	
	public BufferedImage getSubImage(int x, int y, int w, int h)
	{
		return image.getSubimage(x, y, w, h);
	}
	
	public void reset()
	{
		image = null;
	}
	
}
