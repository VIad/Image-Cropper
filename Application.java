import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Application extends MouseAdapter implements ActionListener , KeyListener {
	
	private JFrame				frame;
	
	private JButton				btnLoadImage;
	
	private JPanel				menuPanel;
	
	private JToggleButton		toggleRectCreate;
	
	private DisplayPanel		displayPanel;
	
	private ImageLoader			imageLoader;
	
	private Cropper				crop;
	
	private static Application	app;
	
	private boolean				creatingRect;
	
	private boolean				imageOpened;
	
	public static void main(String [] args)
	{
		app = new Application();
		app.initialize();
	}
	
	public static Application getApp()
	{
		return app;
	}
	
	private void initialize()
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
		{}
		instantiate();
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(menuPanel, BorderLayout.SOUTH);
		frame.setResizable(false);
		frame.addKeyListener(this);
		
		btnLoadImage.setToolTipText("Loads Image via file chooser dialog");
		btnLoadImage.addActionListener(this);
		btnLoadImage.setFocusable(false);
		toggleRectCreate.setFocusable(false);
		menuPanel.add(btnLoadImage);
		
		menuPanel.add(toggleRectCreate);
		toggleRectCreate.addActionListener(this);
		set(false);
		
		frame.getContentPane().add(displayPanel, BorderLayout.CENTER);
		frame.setBounds(100, 100, 450, 413);
		frame.setLocationRelativeTo(null);
		displayPanel.addMouseListener(this);
		displayPanel.addMouseMotionListener(this);
		displayPanel.addKeyListener(this);
		info();
	}
	
	private void instantiate()
	{
		frame = new JFrame("Cropper");
		creatingRect = false;
		imageOpened = false;
		menuPanel = new JPanel();
		menuPanel.setBackground(Color.LIGHT_GRAY.darker());
		btnLoadImage = new JButton("Load Image");
		toggleRectCreate = new JToggleButton("Create rect");
		crop = new Cropper(getApp());
		imageLoader = new ImageLoader(getApp());
		displayPanel = new DisplayPanel();
	}
	
	public void onImageSelected(int width, int height)
	{
		frame.setSize(width + 7, height + 40);
		frame.setLocationRelativeTo(null);
	}
	
	public ImageLoader getImageLoader()
	{
		return imageLoader;
	}
	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if(event.getSource() == btnLoadImage)
		{
			getImageLoader().loadImageFileChooser();
		}
		
		if(event.getSource() == toggleRectCreate)
		{
			if(toggleRectCreate.isSelected())
			{
				toggleRectCreate.setText("Cancel");
			} else
				toggleRectCreate.setText("Create rect");
			creatingRect = !creatingRect;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		if(creatingRect && imageOpened)
		{
			crop.setPressPoint(e.getPoint());
		}	
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		crop.setRect(null);
		set(false);
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(creatingRect && imageOpened)
		{
			crop.setReleasePoint(e.getPoint());
			crop.createRect();
			crop.onRelease();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if(creatingRect && imageOpened)
		{
			crop.setReleasePoint(e.getPoint());
			crop.createRect();
		}
		if(crop.getRect() != null)
		{
			set(true);
		}
		if(!creatingRect && imageOpened)
		{
			crop.getRect().setLocation(e.getPoint());
		}
	}
	
	@Override
	public void keyPressed(KeyEvent event)
	{
		if(event.getKeyCode() == KeyEvent.VK_S && event.isShiftDown())
		{
			crop.save();
		}
		if(event.getKeyCode() == KeyEvent.VK_R && event.isShiftDown())
		{
			crop.setRect(null);
			if(toggleRectCreate.isSelected())
				toggleRectCreate.doClick();
			getImageLoader().reset();
			imageOpened = false;
			creatingRect = false;
			frame.setBounds(100, 100, 450, 413);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e)
	{}
	
	@Override
	public void keyTyped(KeyEvent e)
	{}
	
	public Cropper getCrop()
	{
		return this.crop;
	}
	
	public JFrame getFrame()
	{
		return frame;
	}
	
	public void set(boolean bool)
	{}
	
	public void setImageOpened(boolean imageOpened)
	{
		this.imageOpened = imageOpened;
	}
	
	private void info()
	{
		JOptionPane.showMessageDialog(null, "To save selection : Shift + S");
	}
}
