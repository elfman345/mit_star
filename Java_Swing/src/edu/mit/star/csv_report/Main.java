package edu.mit.star.csv_report;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Main extends JFrame implements ActionListener
{
	//fields
	private static final long serialVersionUID = 1L;
	JTextArea input;
	JTextArea report;
	JButton calculate;
    private List<String> events = null;
    private String content = null;
    private String[] process = null;
    private String[] compute = null;
    private String[] results = null;
    private String[][] twoD;
    private Iterator<String> itr;

	@Override
	public void addNotify()
	{
		super.addNotify();
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.PAGE_AXIS));
		input = new JTextArea(12, 40);
		input.setBorder(BorderFactory.createTitledBorder("Input"));
		calculate = new JButton("Calculate");
		calculate.setAlignmentX(Component.CENTER_ALIGNMENT);
		calculate.addActionListener(this);
		report = new JTextArea(12, 40);
		report.setBorder(BorderFactory.createTitledBorder("Report"));
		c.add(input);
		c.add(calculate);
		c.add(report);
	}

	public static void main(String[] args)
	{
		try
		{
			SwingUtilities.invokeAndWait(new Runnable()
			{
				
				@Override
				public void run()
				{
					Main m = (new Main());
					m.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					m.pack();
					m.setVisible(true);
				}
			});
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{		
		//helpers
		twoD = new String[10][3];
		String compare = null;
		String result = null;
		double avg = 0;
		int locations = 0;
		int count = 0;
		int i = 0;
		
		//process strings by splitting on first new line, then comma
		content = input.getText();
		process = content.split("\n");
		for(i = 0; i < process.length; i++)
		{
			compare = process[i].toString();
			compute = compare.split(",");
			for(int j = 0; j < compute.length; j++)
			{
				//fill 2D array
				twoD[i][j] = compute[j];
			}
		}
		results = new String[process.length];
		// walkthrough 2D array -- comparisons O(n^2)
		while(count < process.length)
		{
			for(i = 0; i < process.length; i++)
			{
				if(count == 0 && avg == 0)
				{
					compare = twoD[i][0].toString();
					avg = Double.parseDouble((twoD[i][1].toString()));
					locations++;
				}				
				else if(twoD[i][0].contains(compare))
				{
					if(avg == 0)
					{
						avg = Double.parseDouble(twoD[i][1]);
					}
					else
					{	
						avg = Math.ceil(((avg + Double.parseDouble(twoD[i][1].toString()))/2));
					}
					locations++;
				}
				else {}
			}
			result = compare + " avg. min.: " + Integer.toString(((int)avg)) + " # of events: " + Integer.toString(locations);
			results[count] = result;
			count++;	
			avg = 0;
			locations = 0;
			if(count < process.length)
			{
				compare = twoD[count][0].toString();
			}
		}
		events = Arrays.asList(results);		
		//remove duplicates
		HashSet<String> hashSet = new HashSet<String>(events);
		ArrayList<String> events = new ArrayList<String>(hashSet) ;
		Collections.sort(events);
		//Display results
		itr = events.iterator();		
		while(itr.hasNext())
		{
			report.append(itr.next().toString() + "\n");
		}
	}
}