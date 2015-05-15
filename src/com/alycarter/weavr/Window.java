package com.alycarter.weavr;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private final int BOX_SIZE = 17;
	private final int SECTION_SPACING = 10;
	
	private final int OFFSET = -30;
	
	private Loom loom;
	private BufferedImage display;
	private ReDrawListener reDrawListener;

	private JPanel buttonPanel;
	JButton addThread;
	JButton addLift;
	JButton clear;
	
	private JPanel patternPanel;
	private WeaveDisplay weaveDisplay;
	
	public Window(String name) {
		super(name);
	}

	public void initialize(Loom loom)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(720, 720);
		setResizable(false);
		patternPanel = new JPanel(null);
		patternPanel.setLocation(0, 30);
		patternPanel.setSize(720, 690);
		add(patternPanel);
		buttonPanel = new JPanel(null);
		buttonPanel.setLocation(0, 0);
		buttonPanel.setSize(720, 30);
		add(buttonPanel);
		addControls();
		this.loom = loom;
		setLayout(null);
		reDrawListener = new ReDrawListener();
		createTieUpBoxes();
		for(int i = 0; i < 1; i++)
		{
			addThreadPattern();
		}
		for(int i = 0; i < 1; i++)
		{
			addLiftPattern();
		}
		weaveDisplay = new WeaveDisplay();
		patternPanel.add(weaveDisplay);
		repositionDisplay();
	}
	
	private void addControls()
	{
		addThread = new JButton("Add Thread");
		addLift = new JButton("Add Lift");
		buttonPanel.add(addLift);
		buttonPanel.add(addThread);
		addLift.setSize(100, 20);
		addThread.setSize(100, 20);
		addLift.setLocation(0, 0);
		addThread.setLocation(110, 0);
		addLift.addActionListener(new ButtonListener());
		addThread.addActionListener(new ButtonListener());
	}
	
	private void createTieUpBoxes()
	{
		for(int x = 0; x < loom.getShafts(); x++)
		{
			for(int y = 0; y < loom.getShafts(); y++)
			{
				JCheckBox box = new JCheckBox();
				box.setSize(BOX_SIZE, BOX_SIZE);
				box.setLocation(getWidth() - (x * box.getWidth()) + OFFSET, y * box.getHeight());
				patternPanel.add(box);
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
			box.setSize(BOX_SIZE, BOX_SIZE);
			box.setLocation(getWidth() - (loom.getShafts() * BOX_SIZE) + OFFSET - SECTION_SPACING - ((loom.getThreadPatternCount() - 1) * box.getWidth()), ( i * box.getHeight()));
			patternPanel.add(box);
			box.addActionListener(reDrawListener);
			loom.setThread(box, loom.getThreadPatternCount() - 1, i);
		}
		loom.shiftThreadPattern();
	}
	
	private void addLiftPattern()
	{
		loom.appendLiftPattern();
		for(int i = 0; i < loom.getShafts(); i++)
		{
			JCheckBox box = new JCheckBox();
			box.setSize(BOX_SIZE, BOX_SIZE);
			box.setLocation(getWidth() - ( i * box.getHeight()) + OFFSET, (loom.getShafts() * BOX_SIZE) + SECTION_SPACING + ((loom.getLiftPatternCount() -1) * box.getWidth()));
			patternPanel.add(box);
			box.addActionListener(reDrawListener);
			loom.setLift(box, i, loom.getLiftPatternCount() - 1);
		}
		loom.shiftLiftPattern();
	}
	
	public void reDraw()
	{
		display = new BufferedImage(loom.getThreadPatternCount(), loom.getLiftPatternCount(), BufferedImage.TYPE_INT_ARGB);
		loom.weave(display);
		repositionDisplay();
		repaint();
	}
	
	private void repositionDisplay()
	{
		weaveDisplay.setSize(loom.getThreadPatternCount() * BOX_SIZE, loom.getLiftPatternCount() * BOX_SIZE);
		int pos = getWidth() - weaveDisplay.getWidth() - (loom.getShafts() * BOX_SIZE) + SECTION_SPACING;
		weaveDisplay.setLocation(pos + OFFSET,  (loom.getShafts() * BOX_SIZE) + SECTION_SPACING);
		
		
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
			super.paintComponent(g);
			g.clearRect(0, 0, getWidth(), getHeight());
			g.drawImage(display, getWidth(), 0, -getWidth(), getHeight(), null);
		}
		
	}
	
	class ButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == addLift)
			{
				addLiftPattern();
			}
			if(e.getSource() == addThread)
			{
				addThreadPattern();
			}
			reDraw();
		}
		
	}
}
