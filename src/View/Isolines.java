package View;


import java.awt.Graphics2D;

import Model.Model;

public class Isolines 
{
	private int k, m;
	private Model model;
	
	public Isolines(Model model)
	{
		k = model.sizeX;
		m = model.sizeY;
		this.model = model;
	}
	
	public void marchingCube(Graphics2D g, double z)
	{
		String znakversh;
		double du = ((Model.u1 - Model.u0) / k);
		double dv = ((Model.v1 - Model.v0) / m);
		
		double f1, f2, f3, f4;
		
		for(int i = 0; i < k; ++i)
			for(int j = 0; j < m; ++j)
			{
				double maxi=i+1;
				double maxj=j+1;
				if (i == k - 1) 
				{
					maxi = i + 0.99;
				}
				if (j == m - 1)
				{
					maxj = j + 0.99;
				}
				znakversh = "";
				f1 = model.values[(int)(i * du  + Model.u0)][(int)(j * dv  + Model.v0)];
				f2 = model.values[(int)((maxi) * du  + Model.u0)][(int)(j * dv  + Model.v0)];
				f3 = model.values[(int)((maxi) * du  + Model.u0)][(int)((maxj) * dv  + Model.v0)];
				f4 = model.values[(int)(i * du  + Model.u0)][(int)((maxj) * dv  + Model.v0)];
				znakversh = getSign(f1, z);
				znakversh += getSign(f2, z);
				znakversh += getSign(f3, z);	
				znakversh += getSign(f4, z);
				
				switch (znakversh)
				{
				case "0001" : case "1110" : 
				{
					g.drawLine((int)((i + (z - f4) / (f3 - f4)) * du), (int)((maxj) * dv), (int)(i * du ), (int)((j + (z - f1) / (f4 - f1)) * dv));
					break;
				}
				case "0010" : case "1101" : 
				{
					g.drawLine((int)((i + (z - f4) / (f3 - f4)) * du), (int)((maxj) * dv),(int)( (maxi) * du), (int)((j + (z - f2) / (f3 - f2)) * dv));
					break;
				}
				case "0100" : case "1011" : 
				{
					g.drawLine((int)((i + (z - f1) / (f2 - f1)) * du), (int)(j * dv), (int)((maxi) * du), (int)((j + (z - f2) / (f3 - f2)) * dv));
					break;
				}
				case "1000" : case "0111" : 
				{
					g.drawLine((int)((i + (z - f1) / (f2 - f1)) * du), (int)(j * dv),(int)(i * du), (int)((j + (z - f1) / (f4 - f1)) * dv));
					break;
				}
				case "1001" : case "0110" :
				{
					g.drawLine((int)((i + (z - f1) / (f2 - f1)) * du), (int)(j * dv), (int)((i + (z - f4) / (f3 - f4)) * du), (int)((maxj) * dv));
					break;
				}
				case "0011" : case "1100" :
				{
					g.drawLine((int)(i * du), (int)((j + (z - f1) / (f4 - f1)) * dv), (int)((maxi) * du), (int)((j + (z - f2) / (f3 - f2)) * dv));
					break;
				}
				case "0101" : 
				{
					if (model.values[(int)((i + 0.5) * du  + Model.u0)][(int)((j + 0.5) * dv  + Model.v0)] > z)
					{
						g.drawLine((int)((i + (z - f1) / (f2 - f1)) * du), (int)(j * dv),(int)(i * du), (int)((j + (z - f1) / (f4 - f1)) * dv));
						g.drawLine((int)((i + (z - f4) / (f3 - f4)) * du), (int)((maxj) * dv),(int)( (maxi) * du), (int)((j + (z - f2) / (f3 - f2)) * dv));
					}
					else
					{
						g.drawLine((int)((i + (z - f1) / (f2 - f1)) * du), (int)(j * dv), (int)((maxi) * du), (int)((j + (z - f2) / (f3 - f2)) * dv));
						g.drawLine((int)((i + (z - f4) / (f3 - f4)) * du), (int)((maxj) * dv), (int)(i * du ), (int)((j + (z - f1) / (f4 - f1)) * dv));
					}
					break;
				}
				case "1010" :
				{
					if (model.values[(int)((i + 0.5) * du  + Model.u0)][(int)((j + 0.5) * dv  + Model.v0)] > z)
					{
						g.drawLine((int)((i + (z - f1) / (f2 - f1)) * du), (int)(j * dv), (int)((maxi) * du), (int)((j + (z - f2) / (f3 - f2)) * dv));
						g.drawLine((int)((i + (z - f4) / (f3 - f4)) * du), (int)((maxj) * dv), (int)(i * du ), (int)((j + (z - f1) / (f4 - f1)) * dv));
					}
					else
					{
						g.drawLine((int)((i + (z - f1) / (f2 - f1)) * du), (int)(j * dv),(int)(i * du), (int)((j + (z - f1) / (f4 - f1)) * dv));
						g.drawLine((int)((i + (z - f4) / (f3 - f4)) * du), (int)((maxj) * dv),(int)( (maxi) * du), (int)((j + (z - f2) / (f3 - f2)) * dv));
					}
					break;
				}
				default :
				{
					break;
				}
				}
			}
	}
	
	private String getSign (double f, double z)
	{
		return f < z ? "0" : "1";
	}
}