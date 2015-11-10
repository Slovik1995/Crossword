package saved;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import crossword.Crossword;

import dictionary.CwEntry;

public class CWriter implements Writer{
	String path;
	public CWriter(String path){
		this.path=path;
	};
	public void write(Crossword c) throws IOException{
		String mypath = path + "\\"+ getUniqueID() + ".txt"; 
		File f = new File(mypath);
		FileWriter w = new FileWriter(f);
		w.write(Integer.toString(c.getCrosswordHeight()));
		w.write(System.getProperty( "line.separator" ));
		w.write(Integer.toString(c.getCrosswordWidth()));
		w.write(System.getProperty( "line.separator" ));
		w.write(Integer.toString(c.getBoardCopy().getHeight()));
		w.write(System.getProperty( "line.separator" ));
		w.write(Integer.toString(c.getBoardCopy().getWidth()));
		w.write(System.getProperty( "line.separator" ));
		w.write(c.getFilePath());
		w.write(System.getProperty( "line.separator" ));
		
		for(int i=0 ;i<c.getBoardCopy().getHeight(); i++)
			for(int j=0; j<c.getBoardCopy().getWidth(); j++){
				if(c.getBoardCopy().getCell(i, j).getContent().equals("")){
					w.write("@");
				}
				else{
					w.write(c.getBoardCopy().getCell(i, j).getContent());
				}
				w.write(System.getProperty( "line.separator" ));
			}
	//	w.write("###"); 
	//	w.write(System.getProperty( "line.separator" ));
		String mode="S";
		boolean horiz=false;
		boolean vert=false;
		LinkedList<CwEntry> list = c.getEntries();
		for(int i=0; i<list.size(); i++){
			if(list.get(i).getDir()==CwEntry.Direction.HORIZ)
				horiz=true;
			else vert=true;
			if((horiz==true)&&(vert==true))
			{
				mode="A";
				break;
			}
		}
		w.write(mode);
		w.write(System.getProperty( "line.separator" ));
		for(int i=0; i<list.size(); i++){
		CwEntry e = list.get(i);
		w.write(e.getWord());
		w.write(System.getProperty( "line.separator" ));
		w.write(e.getClue());
		w.write(System.getProperty( "line.separator" ));
		w.write(Integer.toString(e.getX()));
		w.write(System.getProperty( "line.separator" ));
		w.write(Integer.toString(e.getY()));
		w.write(System.getProperty( "line.separator" ));
		w.write(e.getDir().toString());
		w.write(System.getProperty( "line.separator" ));
		}
		w.flush();
		w.close();
		
	};	
	private long getUniqueID(){
		Date d = new Date();
		long time = d.getTime();
		return time;
	}
}
