package crossword;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import dictionary.CwEntry;
import myexception.EmptyListException;
import saved.CwBrowser;
public class Draw2 extends JFrame{
	private Crossword crossword;
	CwBrowser browser;
	Draw2 refer;
	JPanel pane1, pane2,cluePanel;
	LinkedList<Tile2> tiles;
	
	public void setCrossword(Crossword c){
		crossword = c;
	}
	public Draw2(CwBrowser b){
		browser = b;
		refer=this;
	}

    public void paint(){
    	tiles = new LinkedList<>();
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	this.setLayout(new FlowLayout());
    	JPanel panel1 = new JPanel();
    	JPanel panel2 = new JPanel();
    	JPanel panel3 = new JPanel();
    	JCheckBox mode = new JCheckBox("Advanced");
    	mode.setSelected(true);
    	JLabel wysokosc = new JLabel("wysokość");
    	JLabel szerokosc = new JLabel("szerokość");
    	JSpinner scroll1 = new JSpinner();
    	scroll1.setValue(crossword.getCrosswordHeight());
    	mode.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			if(mode.isSelected())
    				browser.setMode('A');
    			else browser.setMode('S');
    		}
    	});
    	scroll1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	int value = (int)((JSpinner)e.getSource()).getValue();
            	if((value<3)||(value>19)) 
            		value = -1;
            	crossword.setCrosswordHeight(value);
            }
         });
    	JSpinner scroll2 = new JSpinner();
    	scroll2.setValue(crossword.getCrosswordWidth());
    	scroll2.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	int value = (int)((JSpinner)e.getSource()).getValue();
            	if((value<3)||(value>19)) 
            		value = -1;
            	crossword.setCrosswordWidth(value);
            }
         });
    	JButton generuj = new JButton("Generuj");
    	
    	generuj.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){	
    			if((crossword.getCrosswordWidth()<=0)||(crossword.getCrosswordHeight()<=0)||((browser.getMode()=='S')&&((crossword.getCrosswordWidth()>12)||(crossword.getCrosswordHeight()>12))))
    			{
    				JFrame message = new JFrame();
    				JOptionPane.showMessageDialog(message, "Podaj prawidłowe wymiary.", "Złe wymiary", JOptionPane.INFORMATION_MESSAGE);
    				return;
    			}
    			clean();
    			try {
					browser.generate();
				} catch (EmptyListException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    			
    		}
    	});
    	JPanel statusPanel = new JPanel();
    	pane1=statusPanel;
    	statusPanel.setLayout(new GridLayout(1,3));
    	statusPanel.setSize(1300,100);      //!!!!!!!!!!!!!!!!!!!
    	panel1.add(mode);
    	panel1.add(wysokosc);
    	panel1.add(scroll1);
    	panel1.add(szerokosc);
    	panel1.add(scroll2);
    	panel1.add(generuj);
    	
    	JTextField path2 = new JTextField();
    	path2.setText("Ścieżka...");

    	
    	JButton dots = new JButton("...");
    	
    	dots.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			search();
    		}
    	});
    	JButton wczytaj = new JButton("Wczytaj");
    	wczytaj.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			
    			String s = path2.getText();
    			if(!s.endsWith(".txt"))
    				return;
    			String str="";
    			for(int i=0; i<s.length(); i++){
    				str+=s.charAt(i);
    				if(s.charAt(i)=='\\')
    					str+='\\';
    			}
    			clean();
    			try {
					browser.read(str);
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    	});
    	panel2.add(path2);
    	panel2.add(dots);
    	panel2.add(wczytaj);
    	
    	JButton drukuj = new JButton("PDF");
    	drukuj.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			browser.savePDF(refer);
    		}
    	});
    	JButton zapisz = new JButton("Zapisz");
    	zapisz.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			try {
					browser.save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    		}
    	});
    	JButton rozwiaz = new JButton("Rozwiąż");
    	rozwiaz.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){
    			if(rozwiaz.getText().equals("Rozwiąż"))
    			{
    				for(Tile2 t : tiles)
    					t.setText(t.hidden);
    				rozwiaz.setText("Ukryj");
    			}
    			else
    			{
    				for(Tile2 t : tiles)
    				{
    					if(!t.hidden.isEmpty())
    					{
    						Character c = t.hidden.charAt(0);
    						if(!c.isDigit(c))
    							t.setText(" ");
    					}
    				}
    				rozwiaz.setText("Rozwiąż");
    			}
    		}
    	});
    	panel3.add(drukuj);
    	panel3.add(zapisz);
    	panel3.add(rozwiaz);
    	
    	panel1.setBorder(BorderFactory.createTitledBorder("nowa krzyżówka"));
    	panel2.setBorder(BorderFactory.createTitledBorder("z pliku"));
    	panel3.setBorder(BorderFactory.createTitledBorder("kontrola"));
    	    	
    	statusPanel.add(panel1);
    	statusPanel.add(panel2);
    	statusPanel.add(panel3);

    	this.add(statusPanel);

   
	JPanel panel = new JPanel();
	pane2=panel;
	panel.setLayout(new GridLayout(crossword.getCrosswordHeight()+1, crossword.getCrosswordWidth()+1));
	for(int i=0; i<crossword.getCrosswordHeight()+1; i++)
		for(int j=0; j<crossword.getCrosswordWidth()+1; j++){
			{
				if((i>0)&&(j>0))
				{
					Tile2 t = new Tile2(crossword.getBoardCopy().getCell(i-1, j-1).getContent());
					panel.add(t);
					tiles.add(t);	
				}
				else
				{
					Tile2 t = new Tile2("");
					panel.add(t);
					tiles.add(t);
				}
			}
		}
	for(int i=0; i<crossword.getEntries().size(); i++)
	{
		int x = crossword.getEntries().get(i).getX()+1;
		int y = crossword.getEntries().get(i).getY()+1;
		int position;
		if(crossword.getEntries().get(i).getDir()==CwEntry.Direction.HORIZ)
			position = y*(crossword.getCrosswordWidth()+1)+x-1;
		else position = (y-1)*(crossword.getCrosswordWidth()+1)+x;
		System.out.println(position);
		tiles.get(position).settile(Integer.toString(i+1));
	}

	JPanel horizontally = new JPanel();
	
	JTextArea area = new JTextArea();
	
	area.append("POZIOMO\n");
	for(int i=0; i<crossword.getEntries().size(); i++){
		if(crossword.getEntries().get(i).getDir()==CwEntry.Direction.HORIZ)
			area.append(""+(i+1)+". "+crossword.getEntries().get(i).getClue()+"\n" );
	}
	area.setEditable(false);
    horizontally.add(area);
    
    JPanel vertically = new JPanel();
	
	JTextArea area2 = new JTextArea();
	
	area2.append("PIONOWO\n");
	for(int i=0; i<crossword.getEntries().size(); i++){
		if(crossword.getEntries().get(i).getDir()==CwEntry.Direction.VERT)
			area2.append(""+(i+1)+". "+crossword.getEntries().get(i).getClue()+"\n" );
	}
	area2.setEditable(false);
    vertically.add(area2);
    
	this.setSize(1366, 725);
	this.add(panel);
	cluePanel = new JPanel();
	cluePanel.add(horizontally);
	cluePanel.add(vertically);
	 this.add(cluePanel);
	this.setVisible(true);
    }
    private void clean(){
    	pane1.setVisible(false);
		pane2.setVisible(false);
		cluePanel.setVisible(false);
		
		refer.remove(pane1);
		refer.remove(pane2);
		refer.remove(cluePanel);

    }
	public void search(){
		JFrame f = new JFrame();
		f.setSize(200, 474);
		DefaultMutableTreeNode directory = new DefaultMutableTreeNode("Crosswords");
	  JTree tree = new JTree(directory);
	  tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	  File file = new File(browser.getSaveFile());
	  for(File m : file.listFiles()){
		  if(m.getName().endsWith(".txt")){
			  DefaultMutableTreeNode node = new DefaultMutableTreeNode(m.getName());
			  directory.add(node);
		  }
	  }
	  JPanel p = new JPanel();
	  JButton b = new JButton("Wybierz");
	  b.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e){
			  DefaultMutableTreeNode node2 = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			  String numberOfFile = (String)node2.getUserObject();
			  
				  String fullPath = browser.getSaveFile()+"\\"+numberOfFile;
				  clean();
				try {
					browser.read(fullPath);
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			  f.dispose();
		  }
	  });
	  JScrollPane scroll = new JScrollPane(tree);

      Dimension d = scroll.getPreferredSize();
      d.width = 180;
      scroll.setPreferredSize(d);
       
      p.add(scroll);
       p.add(b);
      f.add(p);
       f.setResizable(false);
      f.setVisible(true);
	}
	
}
class Tile2 extends JButton{
	String hidden;
	public Tile2(String s){
	super(""); 

	hidden=s;
	if(s.equals(""))
		this.setVisible(false);
	this.addMouseListener(new MouseAdapter(){
		public void mouseClicked(MouseEvent e) {
			((JButton)e.getSource()).setText(((Tile2)e.getSource()).hidden);
		}       
	});
	
	}
	public void settile(String str){
		this.setText(str);
		hidden=str;
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		this.setVisible(true);
	}
	
   	   
	
	
}