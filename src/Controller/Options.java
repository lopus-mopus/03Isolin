package Controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Model.Model;

public class Options 
{
	JFrame frame, optionFrame;
	JTextField widthField, heightField, x0Field, x1Field, y0Field, y1Field;
	Model model = Controller.model;
	JButton ok, cancel;
	Set<JTextField> okPress = new LinkedHashSet<JTextField>();
	
	public Options(JFrame frame)
	{
		this.frame = frame;
	}
	
	public void checkOptions()
	{
		optionFrame = new JFrame();
		
		JDialog optionsDialog = new JDialog(optionFrame, "Настройки ", true);
		
		JPanel sizePanel = new JPanel();
		JLabel widthLabel = new JLabel("Ширина ");
		widthField = new JTextField(4);
		widthField.setText(String.valueOf(model.sizeX));
		widthLabel.setLabelFor(widthField);
		JLabel heightLabel = new JLabel("Высота ");
		heightField = new JTextField(4);
		heightField.setText(String.valueOf(model.sizeY));
		heightLabel.setLabelFor(heightField);
		heightField.addKeyListener(new KeyListener() 
		{
			public void keyReleased(KeyEvent arg0) 
			{
				int len = heightField.getText().length();
				
				if(len == 0)
				{
					ok.setEnabled(false);
					okPress.add(heightField);
				}
				if(len > 0)
				{
					okPress.remove(heightField);
					if(okPress.size() == 0) ok.setEnabled(true);
				}
			}
			
			public void keyPressed(KeyEvent arg0) 
			{}
			
			public void keyTyped(KeyEvent e) 
			{
				char a = e.getKeyChar();
				int len = heightField.getText().length();
				
				if((a == '0') && (len == 0)) e.consume();
				else if(!Character.isDigit(a)) e.consume();
			}
		});
		
		Box widthBox = Box.createHorizontalBox();
		widthBox.add(widthLabel);
		widthBox.add(Box.createHorizontalStrut(6));
		widthBox.add(widthField);
		
		Box heightBox = Box.createHorizontalBox();
		heightBox.add(heightLabel);
		heightBox.add(Box.createHorizontalStrut(6));
		heightBox.add(heightField);
		
		Box sizeBox = Box.createVerticalBox();
		
		sizeBox.add(widthBox);
		sizeBox.add(Box.createVerticalStrut(6));
		sizeBox.add(heightBox);
		
		sizePanel.add(sizeBox);
		sizePanel.setBorder(BorderFactory.createTitledBorder("Сетка "));
		
		JLabel x0Label = new JLabel("a = ");
		x0Field = new JTextField(4);
		x0Field.setText(String.valueOf(model.x0));
		x0Label.setLabelFor(x0Field);
		
		JLabel y0Label = new JLabel("b = ");
		y0Field = new JTextField(4);
		y0Field.setText(String.valueOf(model.y0));
		y0Label.setLabelFor(y0Field);
		
		Box startXBox = Box.createHorizontalBox();
		startXBox.add(x0Label);
		startXBox.add(Box.createHorizontalStrut(6));
		startXBox.add(x0Field);
		
		Box startYBox = Box.createHorizontalBox();
		startYBox.add(y0Label);
		startYBox.add(Box.createHorizontalStrut(6));
		startYBox.add(y0Field);
		
		Box startBox = Box.createVerticalBox();
		
		startBox.add(startXBox);
		startBox.add(Box.createVerticalStrut(6));
		startBox.add(startYBox);
		
		JPanel areaPanel = new JPanel();
		JLabel x1Label = new JLabel("c = ");
		x1Field = new JTextField(4);
		x1Field.setText(String.valueOf(model.x1));
		x1Label.setLabelFor(x1Field);
		
		JLabel y1Label = new JLabel("d = ");
		y1Field = new JTextField(4);
		y1Field.setText(String.valueOf(model.y1));
		y1Label.setLabelFor(y1Field);
		
		Box endXBox = Box.createHorizontalBox();
		endXBox.add(x1Label);
		endXBox.add(Box.createHorizontalStrut(6));
		endXBox.add(x1Field);
		
		Box endYBox = Box.createHorizontalBox();
		endYBox.add(y1Label);
		endYBox.add(Box.createHorizontalStrut(6));
		endYBox.add(y1Field);
		
		Box endBox = Box.createVerticalBox();
		
		endBox.add(endXBox);
		endBox.add(Box.createVerticalStrut(6));
		endBox.add(endYBox);
		
		areaPanel.add(startBox);
		areaPanel.add(endBox);
		areaPanel.setBorder(BorderFactory.createTitledBorder("Область определения"));
				
		JPanel buttonsPanel = new JPanel();
		
		ok = new JButton("OK");
		
		cancel = new JButton("Отмена");
        ok.setPreferredSize(cancel.getPreferredSize()); 
		buttonsPanel.add(ok);
		
		ok.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				int w = Integer.parseInt(widthField.getText());
				int h = Integer.parseInt(heightField.getText());
				double x0 = Double.parseDouble(x0Field.getText());
				double x1 = Double.parseDouble(x1Field.getText()); 
				double y0 = Double.parseDouble(y0Field.getText()); 
				double y1 = Double.parseDouble(y1Field.getText()); 
				
				if(w < 2 || w > Model.u1 || h < 2 || h > Model.v1)
				{
					model.setMsg("Неверные значения сетки. ");
				}
				else
				{
					model.newSettings(w, h, x0, y0, x1, y1);
					
					optionFrame.setVisible(false);
				}
			}
		});
		
		buttonsPanel.add(cancel);
		
		cancel.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				optionFrame.setVisible(false);
			}
		});
		
		Box up = Box.createHorizontalBox();
		up.add(sizePanel);
		up.add(Box.createHorizontalStrut(12));
		up.add(areaPanel);
		
		Box mainBox = Box.createVerticalBox();
		mainBox.setBorder(new EmptyBorder(12,12,12,12));
		mainBox.add(up);
		mainBox.add(Box.createVerticalStrut(6));
		mainBox.add(buttonsPanel);
			
		optionsDialog.add(mainBox);
		optionsDialog.setResizable(false);
		optionsDialog.pack();
		optionsDialog.setLocationRelativeTo(frame);
		optionsDialog.setVisible(true);
	}
}
