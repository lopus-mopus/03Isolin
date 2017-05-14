package Model;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable
{
	public double x0 = -10;
	public double y0 = -12;
	public double x1 = 10;
	public double y1 = 8;
	
	public static int u0 = 0;
	public static int v0 = 0;
	public static int u1 = 600;
	public static int v1 = 600;
	
	public int num = 4;
	public int sizeX = 50;
	public int sizeY = 50;
	
	public double[][] values;
	public Color[][] colors;
	private double maxZ, minZ;
	public double[] layers;
	public Color[] palette;
	public double coefX, coefY;
	public Color[][] paletteColors;
	public double[][] paletteValues;
	public Color[][] interpolationColor;
	public Color[][] interpolationMainFunc;
	public String statusText = null;
	public boolean aboutFlag = false;
	public String msg = null;
	public Color lineColor = new Color(0, 0, 0);
	public boolean newField = false;
	public ArrayList<Double> isolines;
	public double tempLine;
	public int draw = 0;   
	
	public Model()
	{
		palette = new Color[num + 1];
		palette[0] = new Color(255, 0, 0);
		palette[1] = new Color(66, 170, 255);
		palette[2] = new Color(0, 128, 0);
		palette[3] = new Color(255, 165, 0);
		palette[4] = new Color(255, 110, 0);
		
		newValues();
		setChanged();
		notifyObservers();
	}
	
	public double mainFunc(double x, double y)
	{
		double z;
		
		z = + y* x + Math.pow((y + Math.pow(Math.abs(x), 2.0 / 3.0)), 2);
		
		return z;
	}
	
	private double paletteFunc(double y)
	{
		double z;
		
		z = minZ + y * (maxZ - minZ) / (y1-y0);
		return z;
	}
	
	public void findMaxMin()
	{
		double max = values[0][0];
		double min = values[0][0];
		
		for(int i = 0; i < values.length; ++i)
		{
			for(int j = 0; j < values[i].length; ++j)
			{
				if(values[i][j] > max)
				{
					max = values[i][j];
				}
				
				if(values[i][j] < min)
				{
					min = values[i][j];
				}
			}
		}
		
		maxZ = max;
		minZ = min;
	}
	
	private void setLayers()
	{
		for(int i = 0; i < layers.length; ++i)
		{
			layers[i] = minZ + (i + 1) * (maxZ - minZ) / (num + 1);
			isolines.add(layers[i]);
		}
	}
	
	private Color[][] defineColors(Color[][] c, double[][] val)
	{
		for(int i = 0; i < c.length; ++i)
			for(int j = 0; j < c[i].length; ++j)
			{
				c[i][j] = palette[num];
				for (int k = 0; k < layers.length; ++k)
					if (val[i][j] <= layers[k])
					{
						c[i][j] = palette[k];
						break;
					}
			}
		
		return c;
	}
	
	public void getValue(int x, int y)
	{
		isolines.add(values[x][y]);		
		
		setChanged();
		notifyObservers();
	}
	private void setSizeField()
	{
		int countX = (int)((u1 - u0) / sizeX);
		int countY = (int)((v1 - v0) / sizeY);
		
		u1 = countX * sizeX;
		v1 = countY * sizeY;
	}
	//
	public void interpolationMode()
	{
		for(int i = 0; i < interpolationColor.length; ++i)
			for(int j = 0; j < interpolationColor[i].length; ++j)
			{
				double step = (v1- v0) / num;
				int layer = (int)(j / step);
				
				if(layer >= num)
				{
					layer --;
				}
				
				int r = (int)(palette[layer].getRed() * ((layer + 1) * step - j) / (step) + palette[layer + 1].getRed() * (j - layer * step) / (step));
				int g = (int)(palette[layer].getGreen() * ((layer + 1) * step - j) / (step) + palette[layer + 1].getGreen() * (j - layer * step) / (step));
				int b = (int)(palette[layer].getBlue() * ((layer + 1) * step - j) / (step) + palette[layer + 1].getBlue() * (j - layer * step) / (step));
				interpolationColor[i][j] = new Color(r, g, b);
			}
		
		for(int i = 0; i < values.length; ++i)
			for(int j = 0; j < values.length; ++j)
				for(int l = 0; l < v1 - v0; ++l)
					if (values[i][j] <= paletteValues[0][l])
					{
						interpolationMainFunc[i][j] = interpolationColor[0][l];
						break;
					}
	}
	
	public void setStatusText(String str)
	{
		statusText = str;
		
		setChanged();
		notifyObservers();
	}
	
	
	public void setMsg(String msgError)
	{
		msg = msgError;
		
		setChanged();
		notifyObservers();
	}
	
	public void about()
	{
		aboutFlag = true;

		setChanged();
		notifyObservers();
	}
	
	public void changed()
	{
		newField = true;
		
		setChanged();
		notifyObservers();
	}
	
	public void newSettings(int width, int height, double x0, double y0, double x1, double y1)
	{
		u1 = 600;
		v1 = 600;
		sizeX = width;
		sizeY = height;
		
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
		
		newValues();
		
		newField = true;
		
		setChanged();
		notifyObservers();
	}
	
	private void newValues()
	{
		setSizeField();
		values = new double[u1 - u0][v1 - v0];
		
		coefX = (x1 - x0) / (u1 - u0 - 1);
		coefY = (y1 - y0) / (v1 - v0 - 1);
		
		for(int i = 0; i < u1 - u0; ++i)
		{
			for(int j = 0; j < v1 - v0; ++j)
			{
				
				values[i][j] = mainFunc(coefX * i + x0, coefY * j + y0);
			}
		}
		
		findMaxMin();
		
		layers = new double[num];
		
		isolines = new ArrayList<>();
		setLayers();
			
		colors = new Color[u1 - u0][v1 - v0];
		colors = defineColors(colors, values);
		paletteColors = new Color[76][v1 - v0];
		paletteValues = new double[76][v1 - v0];
		
		for(int i = 0; i < paletteValues.length; ++i)
			for(int j = 0; j < paletteValues[i].length; ++j)
				paletteValues[i][j] = paletteFunc(coefY * j);
		paletteColors = defineColors(paletteColors, paletteValues);
		
		interpolationColor = new Color[76][v1 - v0];
		interpolationMainFunc = new Color[u1 - u0][v1 - v0];
		
		interpolationMode();
		
	}
}