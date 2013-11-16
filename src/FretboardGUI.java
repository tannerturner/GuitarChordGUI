import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import java.io.*;

public class FretboardGUI extends JFrame implements ActionListener {
	private JToggleButton[][] frets=new JToggleButton[6][13];
	private JPanel result=new JPanel(new FlowLayout());
	private JLabel chordDisplay=new JLabel();
	private MouseListener ml=new MouseListener();
	private LabelListener ll=new LabelListener();
	private ButtonGroup[] strings=new ButtonGroup[6];
	private Chord chord=new Chord();
	private Note note;
	private Font lFont=new Font("Arial", Font.PLAIN, 32);
	private String[] notes={"C","C#/Db","D","D#/Eb","E","F",
						  "F#/Gb","G","G#/Ab","A","A#/Bb","B"},
					 commands={"c","cs","d","ds","e","f",
			                   "fs","g","gs","a","as","b"};
	
	public FretboardGUI() {
		super("WHAT CHOURD IT BE?!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel board=new JPanel(new GridLayout(6,13));
		board.setBackground(Color.LIGHT_GRAY);
		JLabel[] stringLabels=new JLabel[6];
		stringLabels[0]=new JLabel("  E");
		stringLabels[1]=new JLabel("  A");
		stringLabels[2]=new JLabel("  D");
		stringLabels[3]=new JLabel("  G");
		stringLabels[4]=new JLabel("  B");
		stringLabels[5]=new JLabel("  E");
		for(int i=5; i>=0; i--) {
			strings[i]=new ButtonGroup();
			stringLabels[i].setFont(lFont);
			board.add(stringLabels[i]);
			for(int j=0; j<frets[i].length; j++) {
				frets[i][j]=new JToggleButton();
				if(j==0) {
					frets[i][j].setBackground(Color.DARK_GRAY);
					frets[i][j].setForeground(Color.WHITE);
				} else
					frets[i][j].setBackground(Color.WHITE);
				frets[i][j].setPreferredSize(new Dimension(70,60));
				frets[i][j].addMouseListener(ml);
				frets[i][j].setName(i+1+""+j);
				frets[i][j].setText(String.valueOf(j));
				frets[i][j].setActionCommand(i+1+""+j);
				strings[i].add(frets[i][j]);
				board.add(frets[i][j]);
			}
		}
		add(board, BorderLayout.CENTER);
		
		chordDisplay.setFont(new Font("Arial", Font.BOLD, 18));
		chordDisplay.setForeground(Color.BLACK);
		result.add(chordDisplay);
		result.setBackground(Color.LIGHT_GRAY);
		add(result, BorderLayout.SOUTH);
		
		JMenuBar bar=new JMenuBar();
		JMenu mainMenu=new JMenu("Display Chord");
		
		JMenu majorMenu=new JMenu("Major");
		JMenuItem[] majors=new JMenuItem[12];
		for(int i=0; i<majors.length; i++) {
			majors[i]=new JMenuItem(notes[i]);
			majors[i].addActionListener(new MajorListener());
			majors[i].setActionCommand(commands[i]);
			majorMenu.add(majors[i]);
		}
		
		JMenu minorMenu=new JMenu("Minor");
		JMenuItem[] minors=new JMenuItem[12];
		for(int i=0; i<majors.length; i++) {
			minors[i]=new JMenuItem(notes[i]);
			minors[i].addActionListener(new MinorListener());
			minors[i].setActionCommand(commands[i]);
			minorMenu.add(minors[i]);
		}
		
		JMenuItem rst=new JMenuItem("Reset");
		rst.setPreferredSize(new Dimension(10,20));
		rst.setActionCommand("r");
		rst.addActionListener(this);  //////////////////WATCH OUT/////////////////////////
		JMenuItem play=new JMenuItem("                                                    "
			   +"                                                                         "
			   +"                                                                         "
			   +"                                                                        ");
		play.setActionCommand("p");  ////////////////////////////////////////////////////
		play.addActionListener(this);
		
		mainMenu.add(majorMenu);
		mainMenu.add(minorMenu);
		bar.add(mainMenu);
		bar.add(rst);
		bar.add(play);
		setJMenuBar(bar);
		
		pack();
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public void pressButton(int s, int f) {
		note=new Note(frets[s][f].getName());
		frets[s][f].setText(note.getNote());
		frets[s][f].setActionCommand(note.getNote());
		frets[s][f].doClick();
		chord.addNote(note.getNote());
		
		if(f==0)
			frets[s][f].setForeground(Color.BLACK);
		
		if(chord.isChord()) {
			chordDisplay.setText(chord.getChord());
			chordDisplay.addMouseListener(ll);
			for(int i=0; i<frets.length; i++) 
				for(int j=0; j<frets[i].length; j++)
					if(frets[i][j].isSelected())
						playSound(frets[i][j].getName());
		}
	}
	
	public void reset() {
		chord=new Chord();
		chordDisplay.setText("");
		chordDisplay.removeMouseListener(ll);
		for(int i=0; i<frets.length; i++) {
			strings[i].clearSelection();
			for(int j=0; j<frets[i].length; j++) {
				frets[i][j].setText(String.valueOf(j));
				frets[i][j].setActionCommand(frets[i][j].getName());
				if(j!=0)
					frets[i][j].setBackground(Color.WHITE);
				else {
					frets[i][j].setBackground(Color.DARK_GRAY);
					frets[i][j].setForeground(Color.WHITE);
				}
			}
		}
	}
	
	public void playSound(String file) {
		try {
		    AudioInputStream stream=AudioSystem.getAudioInputStream(
		    		                new File(file+".wav"));
		    AudioFormat format=stream.getFormat();
		    DataLine.Info info=new DataLine.Info(Clip.class, format);
		    Clip clip=(Clip)AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();
		} catch (Exception e) {
		    System.out.println(e.getMessage());
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		char source=e.getActionCommand().charAt(0);
		if(source=='r')
			reset();
		else {
			for(int i=0; i<frets.length; i++) 
				for(int j=0; j<frets[i].length; j++)
					if(frets[i][j].isSelected())
						playSound(frets[i][j].getName());
		}
	}

	//MouseListener inner class/////////////////////////////////////////////////////////////
	private class MouseListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			JToggleButton source=(JToggleButton)e.getComponent();
			int string=Integer.parseInt(source.getName().substring(0,1))-1;
			int fret=Integer.parseInt(source.getName().substring(1));
			if(!source.getActionCommand().matches(".*\\d.*")) { //if this button is selected
				strings[string].clearSelection();
				source.setActionCommand(source.getName());
				chord.removeNote(note.getNote());
				
				if(fret!=0)
					source.setBackground(Color.WHITE);
				else {
					source.setBackground(Color.DARK_GRAY);
					source.setForeground(Color.WHITE);
				}
				
				if(chord.isChord()) {
					chordDisplay.setText(chord.getChord());
					chordDisplay.addMouseListener(ll);
					for(int i=0; i<frets.length; i++) 
						for(int j=0; j<frets[i].length; j++)
							if(frets[i][j].isSelected())
								playSound(frets[i][j].getName());
				} else {
					chordDisplay.setText("");
					chordDisplay.removeMouseListener(ll);
				}
			} else {											//if it's not selected
				for(int i=0; i<frets[string].length; i++) {
					if(!frets[string][i].getActionCommand().matches(".*\\d.*")) {
						chord.removeNote(frets[string][i].getText());
						frets[string][i].setText(frets[string][i].getName().substring(1));
						frets[string][i].setActionCommand(source.getName());
						if(i!=0)
							frets[string][i].setBackground(Color.WHITE);
						else {
							frets[string][i].setBackground(Color.DARK_GRAY);
							frets[string][i].setForeground(Color.WHITE);
						}
					}	//if there's a button on the row selected, reset it and remove it from chord
				}
				source.setActionCommand(note.getNote());
				chord.addNote(note.getNote());
				playSound(source.getName());
				
				if(fret==0)
					source.setForeground(Color.BLACK);
				
				if(chord.isChord()) {
					chordDisplay.setText(chord.getChord());
					chordDisplay.addMouseListener(ll);
					for(int i=0; i<frets.length; i++) 
						for(int j=0; j<frets[i].length; j++)
							if(frets[i][j].isSelected())
								playSound(frets[i][j].getName());
				} else {
					chordDisplay.setText("");
					chordDisplay.removeMouseListener(ll);
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
			JToggleButton source=(JToggleButton)e.getComponent();
			note=new Note(source.getName());
			source.setText(note.getNote());
			playSound(source.getName());
		}

		public void mouseExited(MouseEvent e) {
			JToggleButton source=(JToggleButton)e.getComponent();
			if(source.isSelected())
				return;
			source.setText(source.getName().substring(1));
			
		}
	} //end of MouseListener inner class//////////////////////////////////////////////////////////////
	
	//LabelListener inner class////////////////////////////////////////////////////////////
		private class LabelListener extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				if(chord.isChord()) {
					chordDisplay.setText(chord.getChord());
					for(int i=0; i<frets.length; i++) 
						for(int j=0; j<frets[i].length; j++)
							if(frets[i][j].isSelected())
								playSound(frets[i][j].getName());
				}
			}
			
			public void mouseEntered(MouseEvent e) {
				chordDisplay.setForeground(Color.WHITE);
				chordDisplay.setFont(new Font("Arial", Font.BOLD|Font.ITALIC, 18));
			}
			
			public void mouseExited(MouseEvent e) {
				chordDisplay.setForeground(Color.BLACK);
				chordDisplay.setFont(new Font("Arial", Font.BOLD, 18));
			}
		} //end of LabelListener inner class////////////////////////////////////////////////////
	
	//MajorListener inner class///////////////////////////////////////////////////////////////////////
	private class MajorListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			reset();
			String source=e.getActionCommand();
			switch(source) {
				case "c": pressButton(1,3); 
						  pressButton(2,2);
					      pressButton(3,0);
					      pressButton(4,1);
						  pressButton(5,0);
						  break;
				case "cs": pressButton(1,4); 
				  		   pressButton(2,3);
				  		   pressButton(3,1);
				  		   pressButton(4,2);
				  		   pressButton(5,1);
				  		   break;
				case "d": pressButton(2,0);
						  pressButton(3,2);
						  pressButton(4,3);
						  pressButton(5,2);
						  break;
				case "ds": pressButton(2,1);
				  		   pressButton(3,3);
				  		   pressButton(4,4);
				  		   pressButton(5,3);
				  		   break;
				case "e": pressButton(0,0);
						  pressButton(1,2);
						  pressButton(2,2);
						  pressButton(3,1);
						  pressButton(4,0);
						  pressButton(5,0);
						  break;
				case "f": pressButton(2,3);
						  pressButton(3,2);
						  pressButton(4,1);
						  pressButton(5,1);
						  break;
				case "fs": pressButton(0,2);
				  		   pressButton(1,4);
				  		   pressButton(2,4);
				  		   pressButton(3,3);
				  		   pressButton(4,2);
				  		   pressButton(5,2);
				  		   break;
				case "g": pressButton(0,3);
		  		   		  pressButton(1,2);
		  		   		  pressButton(2,0);
		  		   		  pressButton(3,0);
		  		   		  pressButton(4,3);
		  		   		  pressButton(5,3);
		  		   		  break;
				case "gs": pressButton(0,4);
		  		   		   pressButton(1,6);
		  		   		   pressButton(2,6);
		  		   		   pressButton(3,5);
		  		   		   pressButton(4,4);
		  		   		   pressButton(5,4);
		  		   		   break;
				case "a": pressButton(1,0); 
				  		  pressButton(2,2);
				  		  pressButton(3,2);
				  		  pressButton(4,2);
				  		  pressButton(5,0);
				  		  break;
				case "as": pressButton(0,6);
		   		   		   pressButton(1,8);
		   		   		   pressButton(2,8);
		   		   		   pressButton(3,7);
		   		   		   pressButton(4,6);
		   		   		   pressButton(5,6);
		   		   		   break;
				case "b": pressButton(0,7);
						  pressButton(1,9);
						  pressButton(2,9);
						  pressButton(3,8);
						  pressButton(4,7);
						  pressButton(5,7);
						  break;
				default: System.exit(0);
			}
		}
	} //end of MajorListener inner class//////////////////////////////////////////////////////////////
	
	//MinorListener inner class///////////////////////////////////////////////////////////////////////
	private class MinorListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			reset();
			String source=e.getActionCommand();
			switch(source) {
				case "c": pressButton(1,3); 
				  		  pressButton(2,5);
				  		  pressButton(3,5);
				  		  pressButton(4,4);
				  		  pressButton(5,3);
				  		  break;
				case "cs": pressButton(1,4); 
						   pressButton(2,6);
						   pressButton(3,6);
						   pressButton(4,5);
						   pressButton(5,4);
						   break;
				case "d": pressButton(2,0);
				   		  pressButton(3,2);
				   		  pressButton(4,3);
				   		  pressButton(5,1);
				   		  break;
				case "ds": pressButton(1,6); 
				   		   pressButton(2,8);
				   		   pressButton(3,8);
				   		   pressButton(4,7);
				   		   pressButton(5,6);
				   		   break;
				case "e": pressButton(0,0);
					   	  pressButton(1,2);
					   	  pressButton(2,2);
					   	  pressButton(3,0);
					   	  pressButton(4,0);
					   	  pressButton(5,0);
					   	  break;
				case "f": pressButton(0,1);
			   	  		  pressButton(1,3);
			   	  		  pressButton(2,3);
			   	  		  pressButton(3,1);
			   	  		  pressButton(4,1);
			   	  		  pressButton(5,1);
			   	  		  break;
				case "fs": pressButton(0,2);
	   	  		  		   pressButton(1,4);
	   	  		  		   pressButton(2,4);
	   	  		  		   pressButton(3,2);
	   	  		  		   pressButton(4,2);
	   	  		  		   pressButton(5,2);
	   	  		  		   break;
				case "g": pressButton(0,3);
						  pressButton(1,5);
						  pressButton(2,5);
						  pressButton(3,3);
						  pressButton(4,3);
						  pressButton(5,3);
						  break;
				case "gs": pressButton(0,4);
						   pressButton(1,6);
						   pressButton(2,6);
						   pressButton(3,4);
						   pressButton(4,4);
						   pressButton(5,4);
						   break;
				case "a": pressButton(1,0); 
						  pressButton(2,2);
						  pressButton(3,2);
						  pressButton(4,1);
						  pressButton(5,0);
						  break;
				case "as": pressButton(1,1); 
				  		   pressButton(2,3);
				  		   pressButton(3,3);
				  		   pressButton(4,2);
				  		   pressButton(5,1);
				  		   break;
				case "b": pressButton(1,2); 
						  pressButton(2,4);
						  pressButton(3,4);
						  pressButton(4,3);
						  pressButton(5,2);
						  break;
				default: System.exit(0);
			}
		}
	} //end of MinorListener inner class//////////////////////////////////////////////////////////////
	
	public static void main(String[] args) {
		new FretboardGUI();
	}
}
