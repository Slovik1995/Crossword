package saved;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import crossword.Crossword;
import dictionary.CwEntry;
import strategy.StrategyAdvanced;
import strategy.StrategySimple;

public class CReader implements Reader{
	private String path="";
	public LinkedList<Crossword> crosswordList=null;
	
	public CReader(String path){
		this.path=path;
	}
	
	public LinkedList<Crossword> getAllCws() throws NumberFormatException, IOException{

		crosswordList = new LinkedList<>();

		File dir = new File(path);
		FileReader r=null;
		BufferedReader b=null;
		Crossword crossword=null;
		int height, width, crosswordheight, crosswordwidth;
		String path;

		for( File f : dir.listFiles()){
			String name = f.getName();
			if(name.endsWith(".txt"))
				name = name.substring(0, name.length()-4);

			r = new FileReader(f);
			b= new BufferedReader(r);		
			
			crosswordheight = Integer.parseInt(b.readLine());
			crosswordwidth = Integer.parseInt(b.readLine());
		    height = Integer.parseInt(b.readLine());
			width = Integer.parseInt(b.readLine());
			path = b.readLine();
			crossword = new Crossword(height, width, path, Long.parseLong(name));
			crossword.setCrosswordHeight(crosswordheight);
			crossword.setCrosswordWidth(crosswordwidth);
			
			String sign;
			
			for(int i=0; i<height; i++)
				for(int j=0; j<width; j++){
					sign = b.readLine();
					if(sign.equals("@"))
						sign="";
					crossword.getBoardCopy().getCell(i, j).setConntent(sign);
				}
			String line, word, clue, direction,mode;
			
			int posX, posY;
			CwEntry.Direction d;
			CwEntry e;
				
            mode = b.readLine();
			if(mode.equals("S")){
				StrategySimple strategy = new StrategySimple();
				while((line=b.readLine())!=null)
				{
					word=line;
					clue=b.readLine();
					posX=Integer.parseInt(b.readLine());
					posY=Integer.parseInt(b.readLine());
					direction=b.readLine();
					if(direction.equals("VERT"))
						d=CwEntry.Direction.VERT;
					else d=CwEntry.Direction.HORIZ;
					e = new CwEntry(word,clue,posX,posY,d);
					crossword.addCwEntry(e, strategy);
				}
			}
			else{
				StrategyAdvanced strategy = new StrategyAdvanced();
				while((line=b.readLine())!=null)
				{
					word=line;
					clue=b.readLine();
					posX=Integer.parseInt(b.readLine());
					posY=Integer.parseInt(b.readLine());
					direction=b.readLine();
					if(direction.equals("VERT"))
						d=CwEntry.Direction.VERT;
					else d=CwEntry.Direction.HORIZ;
					e = new CwEntry(word,clue,posX,posY,d);
					crossword.addCwEntry(e, strategy);
				}
			}
			crosswordList.add(crossword);
		}
        b.close();
        r.close();
		return crosswordList;
	};
}
