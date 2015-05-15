package com.alycarter.weavr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JCheckBox;

public class Loom {

	private int shafts;
	
	private JCheckBox[][] tieUp;
	private ArrayList<JCheckBox[]> threads;
	private ArrayList<JCheckBox[]> lifts;
	
	public Loom(int shafts) {
		this.shafts = shafts;
		tieUp = new JCheckBox[shafts][shafts];
		threads = new ArrayList<JCheckBox[]>();
		lifts = new ArrayList<JCheckBox[]>();
	}
	
	public void weave(BufferedImage img)
	{
		for(int y = 0; y < getLiftPatternCount(); y++)
		{
			ArrayList<Integer> threadsLifted = new ArrayList<Integer>();
			for(int x = 0; x < shafts; x++)
			{
				if(lifts.get(y)[x].isSelected())
				{
					getLiftedThreads(threadsLifted, x);
					
				}
			}	
			for(int i = 0; i < threadsLifted.size(); i++)
			{
				img.setRGB(threadsLifted.get(i).intValue(), y, Color.black.getRGB());
				
			}
			
		}
	}
	
	private void getLiftedThreads(ArrayList<Integer> threads, int shaftLifted)
	{
		for(int y = 0; y < shafts; y++)
		{
			if(tieUp[shaftLifted][y].isSelected())
			{
				for(int x = 0; x < getThreadPatternCount(); x++)
				{
					if(this.threads.get(x)[y].isSelected())
					{
						threads.add(x);
					}
					
				}
				
			}
			
		}
		
	}
	
	public void setTieUp(JCheckBox box, int x, int y)
	{
		tieUp[x][y] = box;
	}
	
	public int getShafts()
	{
		return shafts;
	}
	
	public void appendThreadPattern()
	{
		threads.add(new JCheckBox[shafts]);
	}
	
	public void appendLiftPattern()
	{
		lifts.add(new JCheckBox[shafts]);
	}
	
	public int getThreadPatternCount()
	{
		return threads.size();
	}
	
	public int getLiftPatternCount()
	{
		return lifts.size();
	}
	
	public void setThread(JCheckBox box, int x, int y)
	{
		threads.get(x)[y] = box;
	}
	
	public void setLift(JCheckBox box, int x, int y)
	{
		lifts.get(y)[x] = box;
	}
	
	
}
