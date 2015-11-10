package saved;

import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.itextpdf.text.*;



import crossword.Crossword;
import crossword.Draw;
import crossword.Draw2;
import myexception.EmptyListException;
import strategy.Strategy;
import strategy.StrategyAdvanced;
import strategy.StrategySimple;

public class CwBrowser {
    CWriter writer;
    CReader reader;
    Crossword crossword;
    private String path, saveFile;
    Draw d1;
    Draw2 d2;
    Strategy strategySimple;
    Strategy strategyAdvanced;
    LinkedList<Crossword> list=null;
    myPDF mypdf;
    char mode;
    public CwBrowser(String file, String saveFile){
    	path = file;
    	this.saveFile=saveFile;
    	d1= new Draw(this);
    	d2= new Draw2(this);
    	writer = new CWriter(saveFile);
    	mypdf = new myPDF();
    	mode='A';
    	this.strategySimple = new StrategySimple();
    	this.strategyAdvanced = new StrategyAdvanced();
    }
    public void setMode(char c){
    	mode=c;
    }
    public char getMode(){
    	return mode;
    }
    public String getSaveFile(){
    	return saveFile;
    }
	public void generate() throws EmptyListException{
		int height=11, width=11;
		if(crossword!=null) {
			height = crossword.getCrosswordHeight();
			width = crossword.getCrosswordWidth();
		}
		
		crossword = new Crossword(20, 20, path);
		crossword.setCrosswordHeight(height);
		crossword.setCrosswordWidth(width);
		if(mode=='S'){
			d2.setVisible(false);
			d1.setVisible(true);
			crossword.generate(strategySimple);
			d1.setCrossword(crossword);
			d1.paint();
		}
			
		else{
			d2.setVisible(true);
			d1.setVisible(false);
			crossword.generate(strategyAdvanced);
			d2.setCrossword(crossword);
			d2.paint();
		}
	}
	public void read(String readPath) throws NumberFormatException, IOException{
		if(readPath.endsWith(".txt")){

			String mypath1 = readPath.substring(0, readPath.length()-4);
			while(mypath1.charAt(mypath1.length()-1)!='\\')
				mypath1 = mypath1.substring(0, mypath1.length()-1);
			mypath1 = mypath1.substring(0, mypath1.length()-1);

			reader = new CReader(mypath1);
		}
		else return;
		list = reader.getAllCws();
		if(readPath.endsWith(".txt")){
			for(int i=0; i<list.size(); i++){
				if(readPath.contains(Long.toString(list.get(i).getID()))){
					crossword = list.get(i);
					paintCurrentCrossword();
				}
			}
		}
	}
	
	public void save() throws IOException{
		writer.write(crossword);
	}
	private void paintCurrentCrossword(){	
		if(mode=='S'){
			d1.setCrossword(crossword);
			d1.paint();
		}
		else{
			d2.setCrossword(crossword);
			d2.paint();
		}
	}
	public void savePDF(JFrame window){
		try {
			mypdf.makePDF(saveFile, window);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
