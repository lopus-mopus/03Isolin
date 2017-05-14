package View;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import Controller.Controller;
import Model.Model;

public class View extends JPanel implements Observer
{
	private static final long serialVersionUID = 1L;
	private Model model;
	Controller controller = null;
	private JMenuBar menuBar;
	protected JToolBar toolBar;
	
	public static JFrame frame;
	JPanel panel;
	BufferedImage img;
	Graphics2D g2;
	boolean field = false;
	int paletteX;
	JPanel statusPanel;
	private static JLabel statusBarText;
	private boolean aboutMe = false;
	private int frameY = 700;
	private int frameX = 900;
	
	public View(Model model, Controller controller)
	{
		this.model = model;
		this.controller = controller;
		
		frame = new JFrame("Изолинии");
		panel = new JPanel();
		panel.setMinimumSize(new Dimension(frameX, frameY));
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		panel.add(this);
		
		toolBar = controller.createToolBar();
		menuBar = controller.createMenuBar();
		
		statusPanel = new JPanel();
		statusBarText = new JLabel("Готово ");
		statusPanel.add(statusBarText);	
		
		toolBar.setRollover(true);
		frame.add(toolBar, BorderLayout.PAGE_START);
		
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frame.add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(frame.getWidth(), 20));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		
		img = new BufferedImage(frameX, frameY, BufferedImage.TYPE_INT_RGB);
		frame.setJMenuBar(menuBar);
		frame.add(panel);
		panel.addMouseListener(controller);
		panel.addMouseMotionListener(controller);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(frameX, frameY));
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void update (Observable obs, Object obj)
	{
		repaint();
		
		if(model.msg != null)
		{
			JFrame msgFrame = new JFrame();
			JOptionPane optionPane = new JOptionPane(model.msg, JOptionPane.PLAIN_MESSAGE, JOptionPane.CLOSED_OPTION);
			
			JDialog dialog = optionPane.createDialog(msgFrame, "Сообщение об ошибке ");
			dialog.setLocationRelativeTo(frame);
			dialog.setVisible(true);
			model.setMsg(null);
		}
		
		if(model.aboutFlag && !aboutMe)
		{
			JFrame aboutFrame = new JFrame();
			aboutMe = true;
			
			Box mainBox = Box.createHorizontalBox();
			final ImageIcon icon = new ImageIcon("C:/Users/437/Desktop/war/03Isolin/src/resources/i.jpg");
			JLabel aboutIcon = new JLabel(icon);
			mainBox.add(aboutIcon);
			mainBox.add(Box.createHorizontalStrut(12));
			JDialog dialog = new JDialog(aboutFrame, "О программе ", true);
			dialog.setSize(515, 555);
			
			Box textBox = Box.createVerticalBox();
			textBox.add(new JLabel("<html>Изолинии<br> Автор: Елисеев Иван<br><br> ФИТ 2017<br> группа 14204</html>"));
			mainBox.setBorder(new EmptyBorder(12,12,0,12));
			
			mainBox.add(textBox);
			dialog.add(mainBox);
			
			JPanel buttonsPanel = new JPanel();
			
			
			JButton okButton = new JButton("OK");
			buttonsPanel.add(okButton);
			okButton.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					dialog.setVisible(false);
				}
			});
			dialog.add(buttonsPanel, BorderLayout.PAGE_END);
			dialog.setLocationRelativeTo(frame);
			dialog.setVisible(true);
			aboutMe = false;
			model.aboutFlag = false;
		}
		
		if(model.newField)
		{
			field = false;
			model.newField = false;
		}
		
		if(model.statusText != null)
			statusBarText.setText(model.statusText);
	}
	
	public void paint(Graphics g)
	{	
		if(!field)
		{
			g.clearRect(0, 0, frameX, frameY);
			
			g2 = img.createGraphics();
			
			g2.setColor(Color.WHITE);
			g2.fillRect(0, 0, frameX, frameY);
			
			if(Controller.interpolation)
			{
				if(Controller.map)
					drawPole(model.interpolationMainFunc);
			}
			else
			{
				if(Controller.map)
					drawPole(model.colors);
			}
			
			
			if(Controller.grid)
				drawGrid();
			
					
			field = true;
		}
			
		if(Controller.isolines)
		{
			g2.setColor(model.lineColor);
			Isolines line = new Isolines(model);
			
			for(int i = 0; i < model.isolines.size(); ++i)
			{
				line.marchingCube(g2, model.isolines.get(i));
			}
			if(model.draw == 1)
			{
				line.marchingCube(g2, model.tempLine);
				model.isolines.add(model.tempLine);
			}
			else if(model.draw == 2)
				line.marchingCube(g2, model.tempLine);
			
			model.draw = 0;
		}
				
		g.drawImage(img, 0, 0, this);
	}
	
	private void drawPole(Color[][] c)
	{
		int width = Model.u1 - Model.u0;
		int height = Model.v1 - Model.v0;
		for(int i = 0; i < width; ++i)
			for(int j = 0; j < height; ++j)
				img.setRGB(i, j, c[i][j].getRGB());
	}
	
	private void drawGrid()
	{
		double stepX = (Model.u1- Model.u0) / model.sizeX;
		double stepY = (Model.v1- Model.v0) / model.sizeY;
		
		for(int i = 0; i < model.sizeX - 1; ++i)
		{
			for(int j = 0; j < model.sizeY - 1; ++j)
			{
				g2.setColor(Color.BLACK);
				g2.drawLine((int)((i + 1) * stepX), Model.v0, (int)((i + 1) * stepX), Model.v1 - 1);
				g2.drawLine(Model.u0, (int)((j + 1) * stepY), Model.u1 - 1, (int)((j + 1) * stepY));
			}
		}
	}
}
