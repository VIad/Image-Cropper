import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

public class ScreenshotModule {
	
	private BufferedImage	screenshot;
	
	private Application		app;
	
	private Robot			robot;
	
	private Scanner			reader;
	
	private static int		shot_files;
	
	public ScreenshotModule(Application app)
	{
		this.app = app;
		screenshot = null;
	}
	
	public void takeScreenshot()
	{
		app.getFrame().setVisible(false);
		this.screenshot = getScreenshotImage();
		app.getFrame().setVisible(true);
	}
	
	private BufferedImage getScreenshotImage()
	{
		if(robot == null)
		{
			try
			{
				robot = new Robot();
				return robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			}
			catch(AWTException e)
			{}
		}else
			return robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		return null;
	}
	
	public BufferedImage getScreenshot()
	{
		return screenshot;
	}
	
	public void reset()
	{
		screenshot = null;
	}
	
	public void setupFiles()
	{
		File dirs = new File("C:\\CropShot\\Screenshots");
		if(!dirs.exists())
			dirs.mkdirs();
	}
	
	public int currentShotFiles()
	{
		int ret = 0;
		for(final File entry : new File("C:\\CropShot\\Screenshots").listFiles())
		{
			if(entry.isFile())
			{
				ret++;
			}
		}
		return ret;
	}
	
}
