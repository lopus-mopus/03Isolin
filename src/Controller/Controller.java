package Controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javazoom.jl.player.advanced.*;
import java.io.*;
import javazoom.jl.player.*;

import Model.Model;

public class Controller implements MouseListener, MouseMotionListener
{
	public static Model model;
	JFrame mainFrame;
	public static boolean map = true;
	public static boolean grid = false;
	public static boolean interpolation = false;
	public static boolean isolines = true;
	AdvancedPlayer ap;
	
	public Controller(Model model)
	{
		Controller.model = model;
	}
	
	
	public void mouseReleased(MouseEvent e) 
	{}
	
	public void mousePressed(MouseEvent e) 
	{}
	
	public void mouseExited(MouseEvent e) 
	{}
	
	public void mouseEntered(MouseEvent e) 
	{}
	
	public void mouseClicked(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY();
		
		if((x < Model.u1 - Model.u0) && (y < Model.v1 - Model.v0))
		{
			model.getValue(x, y);
		}
	}
	
	public void mouseDragged(MouseEvent e) 
	{
	}
	
	public void mouseMoved(MouseEvent e) 
	{
	}
		
	public JMenuBar createMenuBar()
	{
		JMenuBar menu = new JMenuBar();
		
		JMenu fileMenu = new JMenu("Файл ");
		menu.add(fileMenu);
		
		JMenuItem fileMenuItem = new JMenuItem("Загрузить игру ");
		
		fileMenu.add(fileMenuItem);
		
		JMenuItem exitMenuItem = new JMenuItem("Выйти ");
		exitMenuItem.addActionListener(exitAL);
		fileMenu.add(exitMenuItem);
		
		JMenu modesMenu = new JMenu("Режимы ");
		menu.add(modesMenu);
		
		JMenuItem gridMenuItem = new JMenuItem("Вкл/выкл сетку ");
		gridMenuItem.addActionListener(gridAL);
		modesMenu.add(gridMenuItem);
		
		JMenuItem interpolationMenuItem = new JMenuItem("Вкл/выкл интерполяцию ");
		interpolationMenuItem.addActionListener(interpolationAL);
		modesMenu.add(interpolationMenuItem);
		
		JMenuItem mapMenuItem = new JMenuItem("Вкл/выкл цветную карту ");
		mapMenuItem.addActionListener(mapAL);
		modesMenu.add(mapMenuItem);
		
		JMenuItem isolinesMenuItem = new JMenuItem("Вкл/выкл изолинии ");
		isolinesMenuItem.addActionListener(isolinesAL);//
		modesMenu.add(isolinesMenuItem);
		
		JMenu paramsMenu = new JMenu("Настройки ");
		menu.add(paramsMenu);
		
		JMenuItem optionsMenuItem = new JMenuItem("Параметры ");
		optionsMenuItem.addActionListener(optionsAL);
		paramsMenu.add(optionsMenuItem);
		
		JMenu helpMenu = new JMenu("Помощь");
		menu.add(helpMenu);
		
		JMenuItem aboutMenuItem = new JMenuItem("О программе");
		aboutMenuItem.addActionListener(aboutAL);
		helpMenu.add(aboutMenuItem);
		
		return menu;
	}
	
	public JToolBar createToolBar()
	{
		JToolBar tbar = new JToolBar();
		
		tbar.setFloatable(false);
		
		JButton fileButton = (new ActionButton(new ImageIcon("C:/Users/437/Desktop/war/03Isolin/src/resources/open.png"), "Открыть ", "Открыть параметры портрета из файла ", null)).newButton();
		tbar.add(fileButton);
		
		JButton mapButton = (new ActionButton(new ImageIcon("C:/Users/437/Desktop/war/03Isolin/src/resources//map.png"), "Цветная карта ", "Вкл/выкл цветной карты ", mapAL)).newButton();
		tbar.add(mapButton);
		
		JButton gridButton = (new ActionButton(new ImageIcon("C:/Users/437/Desktop/war/03Isolin/src/resources//grid.png"), "Сетка ", "Вкл/выкл сетки", gridAL)).newButton();
		tbar.add(gridButton);
		
		JButton interpolationButton = (new ActionButton(new ImageIcon("C:/Users/437/Desktop/war/03Isolin/src/resources//interpolation.png"), "интерполяция ", "Вкл/выкл режима интерполяции цветов ", interpolationAL)).newButton();
		tbar.add(interpolationButton);
		
		JButton isolinesButton = (new ActionButton(new ImageIcon("C:/Users/437/Desktop/war/03Isolin/src/resources//isolines.png"), "Изолинии", "Вкл/выкл карты из изолиний ", isolinesAL)).newButton();
		tbar.add(isolinesButton);
				
		JButton optionsButton = (new ActionButton(new ImageIcon("C:/Users/437/Desktop/war/03Isolin/src/resources//options.png"), "Параметры ", "изменение параметров задачи ", optionsAL)).newButton();
		tbar.add(optionsButton);
		
		JButton aboutButton = (new ActionButton(new ImageIcon("C:/Users/437/Desktop/war/03Isolin/src/resources//about.png"), "О программе ", "информация об авторе и версии программы ", aboutAL)).newButton();
		tbar.add(aboutButton);
		
		JButton exitButton = (new ActionButton(new ImageIcon("C:/Users/437/Desktop/war/03Isolin/src/resources//exit.png"), "Выход", "Выход из приложения" , exitAL)).newButton();
		tbar.add(exitButton);
		
        return tbar;
	}
	
	
	private ActionListener mapAL = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			map = map ? false : true;
			
			model.changed();
		}
	};
	
	private ActionListener gridAL = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			grid = grid ? false : true;
			
			model.changed();
		}
	};
	
	private ActionListener interpolationAL = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			interpolation = interpolation ? false : true;
			
			model.changed();
		}
	};
	
	private ActionListener isolinesAL = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			isolines = isolines ? false : true;
			
			model.changed();
		}
	};
	
	private ActionListener optionsAL = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			Options options = new Options(mainFrame);
			options.checkOptions();
		}
	};
	
	private ActionListener aboutAL = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			model.about();
		}
	};
	
	private ActionListener exitAL = new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			int answer = 0;
			
			
			answer = JOptionPane.showConfirmDialog(mainFrame, "Вы действительно хотите выйти из игры? ", "Выход ", JOptionPane.YES_NO_OPTION);
				
			switch(answer)
			{
			case(JOptionPane.YES_OPTION):
			{
				myPlay();
				System.exit(0);
				break;
			}
			case(JOptionPane.NO_OPTION):
			{
				break;
			}
			}
		}
	};
	private void myPlay()
	{
		try
		{
			InputStream is=new FileInputStream("C:\\Users\\437\\Desktop\\war\\03Isolin\\src\\resources\\na.mp3");
			AudioDevice device=new JavaSoundAudioDevice();
			ap=new AdvancedPlayer(is,device);
			ap.play();
			ap.stop();
			ap.close();
		}
		catch(Exception e){}
 
	}
}