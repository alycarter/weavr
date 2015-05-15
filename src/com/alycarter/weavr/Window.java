package com.alycarter.weavr;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private Loom loom;
	private BufferedImage display;
	private ReDrawListener reDrawListener;
	private JPanel panel;
	private WeaveDisplay weaveDisplay;
	
	public Window(String name) {
		super(name);
	}

	public void initialize(Loom loom)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(720, 720);
		setResizable(false);
		panel = new JPanel(null);
		panel.setLocation(0, 0);
		panel.setSize(720, 720);
		add(panel);
		this.loom = loom;
		setLayout(null);
		reDrawListener = new ReDrawListener();
		createTieUpBoxes();
		for(int i = 0; i < 20; i++)
		{
			addThreadPattern();
		}
		for(int i = 0; i < 20; i++)
		{
			addLiftPattern();
		}
		weaveDisplay = new WeaveDisplay();
		panel.add(weaveDisplay);
		weaveDisplay.setSize(loom.getThreadPatternCount() * 20, loom.getLiftPatternCount() * 20);
		int pos = (loom.getShafts() * 20) + 40;
		weaveDisplay.setLocation(pos, pos);
	}
	
	private void createTieUpBoxes()
	{
		for(int x = 0; x < loom.getShafts(); x++)
		{
			for(int y = 0; y < loom.getShafts(); y++)
			{
				JCheckBox box = new JCheckBox();
				box.setSize(20, 20);
				box.setLocation(10 + (x * box.getWidth()), 10 + ( y * box.getHeight()));
				panel.add(box);
				box.addActionListener(reDrawListener);
				loom.setTieUp(box, x, y);
			}	
			
		}
		
	}
	
	private void addThreadPattern()
	{
		loom.appendThreadPattern();
		for(int i = 0; i < loom.getShafts(); i++)
		{
			JCheckBox box = new JCheckBox();
			box.setSize(20, 20);
			box.setLocation((loom.getShafts() * 20) + 20 + (loom.getThreadPatternCount() * box.getWidth()), 10 + ( i * box.getHeight()));
			panel.add(box);
			box.addActionListener(reDrawListener);
			loom.setThread(box, loom.getThreadPatternCount() - 1, i);
		}
	}
	
	private void addLiftPattern()
	{
		loom.appendLiftPattern();
		for(int i = 0; i < loom.getShafts(); i++)
		{
			JCheckBox box = new JCheckBox();
			box.setSize(20, 20);
			box.setLocation( 10 + ( i * box.getHeight()), (loom.getShafts() * 20) + 20 + (loom.getLiftPatternCount() * box.getWidth()));
			panel.add(box);
			box.addActionListener(reDrawListener);
			loom.setLift(box, i, loom.getLiftPatternCount() - 1);
		}
	}
	
	public void reDraw()
	{
		display = new BufferedImage(loom.getThreadPatternCount(), loom.getLiftPatternCount(), BufferedImage.TYPE_INT_ARGB);
		loom.weave(display);
		panel.repaint();
	}
	
	class ReDrawListener implements ActionListener
	{	
		public void actionPerformed(ActionEvent e) {
			reDraw();
		}
	}
	
	class WeaveDisplay extends JPanel
	{

		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			g.clearRect(0, 0, getWidth(), getHeight());
			g.drawImage(display, 0, 0, getWidth(), getHeight(), null);
			//g.drawString("hello world", 0, 10);
		}
		
	}
}
