package crossword;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import myexception.EmptyListException;
import saved.CwBrowser;
public class Draw extends JFrame{
	private Crossword crossword;
	CwBrowser browser;
	Draw refer;
	JPanel pane1, pane2,pane3;
	LinkedList<Tile> tiles;
	
	public void setCrossword(Crossword c){
		crossword = c;
	}
	public Draw(CwBrowser b){
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
            	if((value<3) || (value>19) || ((browser.getMode()=='S')&&(value>12))) 
            		value = -1;
            	crossword.setCrosswordHeight(value);
            }
         });
    	JSpinner scroll2 = new JSpinner();
    	scroll2.setValue(crossword.getCrosswordWidth());
    	scroll2.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	int value = (int)((JSpinner)e.getSource()).getValue();
            	if((value<3) || (value>19) || ((browser.getMode()=='S')&&(value>12))) 
            		value = -1;
            	crossword.setCrosswordWidth(value);
            }
         });
    	JButton generuj = new JButton("Generuj");
    	
    	generuj.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e){	
    			if((crossword.getCrosswordWidth()<=1)||(crossword.getCrosswordHeight()<=1))
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
    				for(Tile t : tiles)
    					t.setText(t.hidden);
    				rozwiaz.setText("Ukryj");
    			}
    			else
    			{
    				for(Tile t : tiles)
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
	panel.setLayout(new GridLayout(crossword.getEntries().get(0).getWord().length(), crossword.getBoardCopy().getWidth()+1));
	for(int i=0; i<crossword.getEntries().get(0).getWord().length(); i++)
		for(int j=-1; j<crossword.getBoardCopy().getWidth(); j++){
			if(j==-1) {
				Tile t = new Tile(""+(i+1), j);
				panel.add(t);
				tiles.add(t);
			}
			else {
				Tile t = new Tile(crossword.getBoardCopy().getCell(i, j).getContent(),j);
				panel.add(t);
				tiles.add(t);
			}
		}

	JPanel horizontally = new JPanel();
	pane3 = horizontally;
	JTextArea area = new JTextArea();
	
	area.append("POZIOMO\n");
	for(int i=1; i<crossword.getEntries().size(); i++){
	    area.append(""+i+". "+crossword.getEntries().get(i).getClue()+"\n" );
	}
	area.setEditable(false);
    horizontally.add(area);
    
	this.setSize(1300, 725);
	this.add(panel);
	 this.add(horizontally);
	
	this.setVisible(true);
    }
    private void clean(){
    	pane1.setVisible(false);
		pane2.setVisible(false);
		pane3.setVisible(false);
		refer.remove(pane1);
		refer.remove(pane2);
		refer.remove(pane3);
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
class Tile extends JButton{
	String hidden;
	public Tile(String s,int i){
	super(""); 

	hidden=s;
	if(i==-1){
		this.setOpaque(false);
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
		super.setText(s);
	}
	if(s.equals(""))
		this.setVisible(false);
	if((i==0)&&(!s.equals("")))
		super.setBackground(Color.RED);
	
	this.addMouseListener(new MouseAdapter(){
		public void mouseClicked(MouseEvent e) {
			((JButton)e.getSource()).setText(((Tile)e.getSource()).hidden);
		}       
	});
	}
	
}