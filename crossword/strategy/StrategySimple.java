package strategy;

import Board.Board;
import dictionary.CwEntry;
import dictionary.CwEntry.Direction;
import dictionary.Entry;
import myexception.EmptyListException;
import crossword.Crossword;

public class StrategySimple extends Strategy{
	static int index=0;	
	static int keywordLength=0;
	static Entry entryKey=null;
	public CwEntry findEntry(Crossword cw)
	{
		Entry entry=null;
		
		CwEntry cwentry=null;
		if(entryKey==null){
			try{
			entryKey = cw.getCwDB().getRandom(cw.getCrosswordHeight());
			}catch(EmptyListException e){
				entryKey=null;
				index=0;
				return null;
			}
			keywordLength = entryKey.getWord().length(); 
			cwentry = new CwEntry(entryKey.getWord(), entryKey.getClue(), 0,0,Direction.VERT);
		}
		else{
			if(index<keywordLength){
				char ch = entryKey.getWord().charAt(index);
				String regex = ch + "[\\w]*";
				try{
					int counter=0;
					while(counter<500)
					{
						entry = cw.getCwDB().getRandom(regex);
						if(entry==null)
						{
							entryKey=null;
							index=0;
							return null;
						}
						if(entry.getWord().length()<=cw.getCrosswordWidth())
							break;
						counter++;
					}
					if(counter>=500){
						entryKey=null;
						index=0;
						return null;
					}
				}
				catch(EmptyListException e){
					entryKey=null;
					index=0;
					return null;
				}
				cwentry = new CwEntry(entry.getWord(), entry.getClue(), 0,index,Direction.HORIZ);
				index++;
			}
			if(index==keywordLength){
				index=0;
				keywordLength=0;
				entryKey=null;
			}
		}
		System.out.println(cwentry.getWord()+ " "+index);
		return cwentry;
	};
	
	public void updateBoard(Board b, CwEntry e)
	{
		if(e.getDir()==CwEntry.Direction.HORIZ){
			for(int i=0; i<e.getWord().length(); i++)
			{
				String s = ""+e.getWord().charAt(i);
				b.getCell(e.getY(), i).setConntent(s);
			}
		}
	};
}
