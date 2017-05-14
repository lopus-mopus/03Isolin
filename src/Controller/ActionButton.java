package Controller;


import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import Model.Model;

public class ActionButton extends JButton
{
	private static final long serialVersionUID = 1L;
	private ImageIcon icon;
	private String status;
	private String hint;
	private ActionListener al;
	private Model model = Controller.model;
		
	public ActionButton(ImageIcon icon, String hint, String status, ActionListener al)
	{
		this.icon = icon;
		this.hint = hint;
		this.status = status;
		this.al = al;
	}
	
	public JButton newButton()
	{
		JButton button = new JButton(icon);
		button.setToolTipText(hint);
		button.addActionListener(al);
		button.addMouseListener(ml);
		
		return button;
	}
	
	private MouseListener ml = new MouseListener() 
	{
		public void mouseReleased(MouseEvent e) 
		{
		}
		
		public void mousePressed(MouseEvent e) 
		{
		}
		
		public void mouseExited(MouseEvent e) 
		{	
			setStatusBarText("Готово");
		}
		
		public void mouseEntered(MouseEvent e) 
		{
			setStatusBarText(status);
		}
		
		public void mouseClicked(MouseEvent e) 
		{
		}
	};
	
	private void setStatusBarText(String str)
	{
		model.setStatusText(str);
	}	
}