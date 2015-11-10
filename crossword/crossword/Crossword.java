package crossword;
import java.util.Iterator;
import java.util.LinkedList;

import Board.Board;
import dictionary.CwEntry;
import dictionary.Entry;
import dictionary.InteliCwDB;
import myexception.EmptyListException;
import strategy.Strategy;
import strategy.StrategySimple;

public class Crossword {
		private LinkedList<CwEntry> entries;
		private String filePath;
		private Board b;
		private InteliCwDB cwdb;
		private final long ID;
		private int heigthOfTheCrossword;
		private int widthOfTheCrossword;
		
		public void setCrosswordWidth(int w){
			widthOfTheCrossword = w;
		}
		public int getCrosswordWidth(){
			return widthOfTheCrossword;
		}
		public int getCrosswordHeight(){
			return heigthOfTheCrossword;
		}
		public void setCrosswordHeight(int h){
			heigthOfTheCrossword = h;
		}
		public String getFilePath(){
			return filePath;
		}
		public LinkedList<CwEntry> getEntries(){
			return entries;
		} 
		public long getID(){
			return ID;
		}
		public Crossword(int h, int w, String file, long id){
         b = new Board(h,w);
         cwdb = new InteliCwDB(file);
         entries = new LinkedList<>();
         ID=id;
         filePath=file;
		};
		public Crossword(int h, int w, String file){
	         b = new Board(h,w);
	         cwdb = new InteliCwDB(file);
	         entries = new LinkedList<>();
	         ID=-1;
	         filePath=file;
			};
		//\\\\\\\\\\\\\\\\\\do usuniecia
		public void writeAll(){
		Iterator<CwEntry> i = getROEntryIter();
		while(i.hasNext()){
			System.out.println(i.next().getWord());
		}
		}
		//\\\\\\\\\\\\\\\\\\\\\\\
		
		private class Iterator<CwEntry>{
			java.util.Iterator<dictionary.CwEntry> i = entries.iterator();
			public boolean hasNext(){
				return i.hasNext();
				}
			public dictionary.CwEntry next(){
				dictionary.CwEntry e = i.next();
				dictionary.CwEntry copy = new dictionary.CwEntry(e.getWord(), e.getClue(), e.getX(), e.getY(), e.getDir());
				return copy;
			}
		}
		public Iterator<CwEntry> getROEntryIter(){	
			return new Iterator<CwEntry>();
		};
		public Board getBoardCopy()
		{
			/*Board boa = new Board(b.getHeight(),b.getWidth());
			for(int i=0; i<b.getHeight(); i++)
				for(int j=0; j<b.getWidth(); j++){
					boa.getCell(i, j).setConntent(b.getCell(i, j).getContent());
					...
				}
			return boa;*/
			return b;
		};
		public InteliCwDB getCwDB()
		{
			return cwdb;
		};
		public void setCwDB(InteliCwDB cwdb)
		{
			this.cwdb=cwdb;
		};
		public boolean contains(String word)
		{
			java.util.Iterator<dictionary.CwEntry> i = entries.iterator();
			while(i.hasNext())
				if(i.next().getWord().equals(word))
					return true;
			return false;
		};
		public final void addCwEntry(CwEntry cwe, Strategy s){
			  entries.add(cwe);

			  s.updateBoard(b,cwe);
			}
		public final void generate(Strategy s) throws EmptyListException{
			if(s instanceof StrategySimple)
				{
					boolean exit;
					do{
						exit=false;
						CwEntry e = null;

						for(int i=0; i<=getCrosswordHeight(); i++)
						{
							e = s.findEntry(this);
							if(e==null){
								break;
							}
							addCwEntry(e,s);
						}
						for(int i=1; i<entries.size(); i++)
						{
							if(entries.get(i).getWord().length()==getCrosswordWidth())
								exit=true;
						}
						if((exit==false)||(e==null))
						{
							exit=false;
							entries = new LinkedList<>();
							b = new Board(b.getHeight(), b.getWidth());
						}
					}while(exit==false);
				}
			else{
				b = new Board(b.getHeight(), b.getWidth());
				long counter=0;
				CwEntry e = null;
				int words = getCrosswordHeight() > getCrosswordWidth() ? getCrosswordWidth() : getCrosswordHeight(); 
				if(words<=6) 
					words=words-1;
				else if((words>=9)&&(words<=12))
					words=words+1;
				else if(words>12)
					words=words+3;
				
				for(int i=0; i<words; i++)
				{
					if(counter>2000){
						entries = new LinkedList<>();
						b = new Board(b.getHeight(), b.getWidth());
						i=0;
						counter=0;
					}
						
					try{
						e = s.findEntry(this);
						if(e!=null)
							{
							addCwEntry(e,s);
							}
						else i--;
						}
					catch(EmptyListException k)
					{
						i--;
					}
					counter++;
					
				}
			}
		}
}
