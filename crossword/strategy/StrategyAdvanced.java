package strategy;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import Board.Board;
import Board.BoardCell;
import dictionary.CwEntry;
import dictionary.CwEntry.Direction;
import dictionary.Entry;
import myexception.EmptyListException;
import crossword.Crossword;

 public class StrategyAdvanced extends Strategy{
	static int index=0;	
	static int keywordLength=0;
	static Entry entryKey=null;
	
	public CwEntry findEntry(Crossword cw) throws EmptyListException
	{
		if(cw.getCrosswordWidth()==0){
			cw.setCrosswordHeight(20);
			cw.setCrosswordWidth(20);
		}
		
		Random rand = new Random();
		int directionInt = Math.abs(rand.nextInt())%2;
		CwEntry.Direction direction = directionInt==0 ? CwEntry.Direction.HORIZ : CwEntry.Direction.VERT; 
		int posX1,posX2,posY1,posY2;
		
		if(cw.getEntries().size()==0){
			if(direction==CwEntry.Direction.HORIZ)
			{
				posX1 = (Math.abs(rand.nextInt())%(cw.getCrosswordWidth()/3));
				posX2 = posX1 + Math.abs(rand.nextInt())%(cw.getCrosswordWidth()-posX1-1);
				posY1 = posY2 = (Math.abs(rand.nextInt())%(cw.getCrosswordHeight()/3));
			}
			else
			{
				posY1 = (Math.abs(rand.nextInt())%(cw.getCrosswordHeight()/3));
				posY2 = posY1 + Math.abs(rand.nextInt())%(cw.getCrosswordHeight()-posY1-1);
				posX1 = posX2 = (Math.abs(rand.nextInt())%(cw.getCrosswordWidth()/3));
			}
			String pattern = cw.getBoardCopy().createPattern(posY1, posX1, posY2, posX2);
			if(pattern==null)
				return null;
			Entry e = cw.getCwDB().getRandom(pattern);
			CwEntry cwentry = null;
			cwentry = new CwEntry(e.getWord(), e.getClue(), posX1,posY1,direction);
	        if((cwentry.getX()+cwentry.getWord().length()>cw.getCrosswordWidth())
	        	||(cwentry.getY()+cwentry.getWord().length()>cw.getCrosswordHeight()))
	        	return null;
			return cwentry;
		}
		else{
				CwEntry cwentry = cw.getEntries().get(Math.abs(rand.nextInt())%cw.getEntries().size());
				if(cwentry.getDir()==CwEntry.Direction.HORIZ)
				{
					LinkedList<BoardCell> xcells= new LinkedList<>();
					for(int i=cwentry.getX(); i<cwentry.getX()+cwentry.getWord().length(); i++)
						if(cw.getBoardCopy().getCell(cwentry.getY(), i).getVInner()==true)
							xcells.add(cw.getBoardCopy().getCell(cwentry.getY(), i));
					if(xcells.size()==0)
						return null;
					posX1 = posX2 = xcells.get(Math.abs(rand.nextInt())%xcells.size()).getX();

					LinkedList<BoardCell> ycells= new LinkedList<>();
					for(int i=0; i<=cwentry.getY(); i++)
						if(cw.getBoardCopy().getCell(i, posX1).getVStart()==true)
							ycells.add(cw.getBoardCopy().getCell(i, posX1));
					if(ycells.size()==0)
						return null;
					posY1 = ycells.get(Math.abs(rand.nextInt())%(ycells.size())).getY();

					ycells= new LinkedList<>();
					for(int i=cwentry.getY(); i<cw.getCrosswordHeight(); i++)
						if(cw.getBoardCopy().getCell(i, posX1).getVEnd()==true)
							ycells.add(cw.getBoardCopy().getCell(i, posX1));
					if(ycells.size()==0)
						return null;

					posY2 = ycells.get(Math.abs(rand.nextInt())%(ycells.size())).getY();
					if(posY1==posY2)
						return null;
					
					boolean matches=true;
					for(int i=0; i<=posY2-posY1; i++)
					{ 
							if(cw.getBoardCopy().getCell(posY1+i, posX1).getVInner()==false)
								matches = false;
							if(matches==false)
							return null;							
					}
				}
				else
				{

					LinkedList<BoardCell> ycells= new LinkedList<>();
					for(int i=cwentry.getY(); i<cwentry.getY()+cwentry.getWord().length(); i++)
						if(cw.getBoardCopy().getCell(i, cwentry.getX()).getHInner()==true){
							ycells.add(cw.getBoardCopy().getCell(i, cwentry.getX()));
						}
					if(ycells.size()==0)
						return null;
					posY1 = posY2 = ycells.get(Math.abs(rand.nextInt())%ycells.size()).getY();
		

					LinkedList<BoardCell> xcells= new LinkedList<>();
					for(int i=0; i<=cwentry.getX(); i++)
						if(cw.getBoardCopy().getCell(posY1, i).getHStart()==true)
							xcells.add(cw.getBoardCopy().getCell(posY1, i));
					if(xcells.size()==0)
						return null;
					posX1 = xcells.get(Math.abs(rand.nextInt())%(xcells.size())).getX();

					xcells= new LinkedList<>();
					for(int i=cwentry.getX(); i<cw.getCrosswordWidth(); i++)
						if(cw.getBoardCopy().getCell(posY1, i).getHEnd()==true)
							xcells.add(cw.getBoardCopy().getCell(posY1, i));
					if(xcells.size()==0)
						return null;
					posX2 = xcells.get(Math.abs(rand.nextInt())%(xcells.size())).getX();
					if(posX1==posX2)
						return null;

					boolean matches=true;
					for(int i=0; i<=posX2-posX1; i++)
					{ 
							if(cw.getBoardCopy().getCell(posY1, posX1+i).getHInner()==false)
								matches = false;
							if(matches==false)
							return null;
							
					}
				}	
			
				String pattern = cw.getBoardCopy().createPattern(posY1, posX1, posY2, posX2);
				
				if((pattern==null)||(pattern.matches("(\\[\\\\\\w\\])*")))
					return null;
				Entry e = cw.getCwDB().getRandom(pattern);
				if(e==null)
					return null;
				if(cwentry.getDir()==CwEntry.Direction.HORIZ)
					direction=CwEntry.Direction.VERT;
				else direction = CwEntry.Direction.HORIZ;
				CwEntry cwentry2 = new CwEntry(e.getWord(), e.getClue(), posX1,posY1,direction);
				if((cwentry2.getX()+cwentry2.getWord().length()>cw.getCrosswordWidth())
			        	||(cwentry2.getY()+cwentry2.getWord().length()>cw.getCrosswordHeight()))
			        	return null;
				return cwentry2;
		}
		
		
		
		/*
		
		LinkedList<BoardCell> freeCells = cw.getBoardCopy().getStartCells();
		
		LinkedList<BoardCell> freeDirectionCells = new LinkedList<>();
		
		
		
		/////////////
		/////////////
		if(direction==CwEntry.Direction.HORIZ)
		{
			for(int i=0; i<freeCells.size(); i++)
				if((freeCells.get(i).getY()<cw.getCrosswordHeight())&&(freeCells.get(i).getX()<cw.getCrosswordWidth()-1)&&(freeCells.get(i).getHStart()==true))
					freeDirectionCells.add(freeCells.get(i));
			if(freeDirectionCells.size()==0) return null;
			int k = (Math.abs(rand.nextInt())+1)%freeDirectionCells.size();
			
			posX1 = freeDirectionCells.get(k).getX();
			posY1 = posY2 = freeDirectionCells.get(k).getY();
			posX2 = ((Math.abs(rand.nextInt())+1)%(cw.getCrosswordWidth()-(posX1+1)))+posX1+1;
		}
		else
		{
			for(int i=0; i<freeCells.size(); i++)
				if((freeCells.get(i).getY()<cw.getCrosswordHeight()-1)&&(freeCells.get(i).getX()<cw.getCrosswordWidth())&&(freeCells.get(i).getVStart()==true))
					freeDirectionCells.add(freeCells.get(i));
			if(freeDirectionCells.size()==0) return null;
			int k = (Math.abs(rand.nextInt())+1)%freeDirectionCells.size();
			
			posY1 = freeDirectionCells.get(k).getY();
			posX1 = posX2 = freeDirectionCells.get(k).getX();
			posY2 = ((Math.abs(rand.nextInt())+1)%(cw.getCrosswordHeight()-(posY1+1)))+posY1+1;
		}
		
		if(direction==CwEntry.Direction.HORIZ)
		{			if(posX2==posX1+1) return null;           //!!!!!!!!!!!!11
			if(cw.getBoardCopy().getCell(posY1, posX1).getHStart()==false)
				return null;
			if(cw.getBoardCopy().getCell(posY2, posX2).getHEnd()==false)
				return null;
			
			for(int i=1; i<posX2-posX1; i++)
			{
				if(cw.getBoardCopy().getCell(posY1, posX1+i).getHInner()==false)
					return null;
			}
			boolean makesCross = false;
			if(cw.getEntries().size()>0)
			for(int i=0; i<=posX2-posX1; i++)
			{ 
					if((cw.getBoardCopy().getCell(posY1, posX1+i).getVStart()==false)
						||(cw.getBoardCopy().getCell(posY1, posX1+i).getVInner()==false)
						||(cw.getBoardCopy().getCell(posY1, posX1+i).getVEnd()==false))
							makesCross=true;
					if(makesCross==false)
					return null;
					
			}
			
		}
		else
		{
			if(posY2==posY1+1) return null;  //!!!!!!!!!!!!!!!!!!11
			if(cw.getBoardCopy().getCell(posY1, posX1).getVStart()==false)
				return null;
			if(cw.getBoardCopy().getCell(posY2, posX2).getVEnd()==false)
				return null;
			
			for(int i=1; i<posY2-posY1; i++)
			{
				if(cw.getBoardCopy().getCell(posY1+i, posX1).getVInner()==false)
					return null;
			}
			 boolean makesCross = false;
			 if(cw.getEntries().size()>0)
			 for(int i=0; i<=posY2-posY1; i++)
			{
					if((cw.getBoardCopy().getCell(posY1+i, posX1).getHStart()==false)
						||(cw.getBoardCopy().getCell(posY1+i, posX1).getHInner()==false)
						||(cw.getBoardCopy().getCell(posY1+i, posX1).getHEnd()==false))
							makesCross=true;
					if(makesCross==false)
					return null;
				
			}
		}

		String pattern = cw.getBoardCopy().createPattern(posY1, posX1, posY2, posX2);
         if(pattern==null)return null;
         System.out.println(pattern+"    <<<<<"+posX1+" "+posY1+" "+posX2+" "+posY2);
		 LinkedList<Entry> listOfAll = cw.getCwDB().findAll(pattern);
		 if(listOfAll.size()==0) throw new EmptyListException();
		 
		 int i = Math.abs(rand.nextInt()%listOfAll.size());

		Entry entry = listOfAll.get(i);
		
		CwEntry cwentry = null;
		cwentry = new CwEntry(entry.getWord(), entry.getClue(), posX1,posY1,direction);
		
		return cwentry;
		/*
		if(entryKey==null){
			entryKey = cw.getCwDB().getRandomLengthFirstSimple(cw.getCrosswordHeight());

			keywordLength = entryKey.getWord().length(); 
			cwentry = new CwEntry(entryKey.getWord(), entryKey.getClue(), 0,0,Direction.VERT);
		}
		else{
			if(index<keywordLength){
				char ch = entryKey.getWord().charAt(index);
				String regex = ch + "[\\w]*";
				try{
				entry = cw.getCwDB().getRandom(regex);
				}
				catch(EmptyListException e){
					throw e;
				}
				////????????
				cwentry = new CwEntry(entry.getWord(), entry.getClue(), 0,index,Direction.HORIZ);
				index++;
			}
			else{
				index=0;
				keywordLength=0;
				entryKey=null;
			}
		}
		return cwentry;
		*/
		
	};
	
	public void updateBoard(Board b, CwEntry e)
	{
		if(e.getDir()==CwEntry.Direction.HORIZ){
			for(int i=0; i<e.getWord().length(); i++)
			{
				String s = ""+e.getWord().charAt(i);
				b.getCell(e.getY(), e.getX()+i).setConntent(s);
				b.getCell(e.getY(), e.getX()+i).setData(false, e.getDir(), 'S');
				b.getCell(e.getY(), e.getX()+i).setData(false, e.getDir(), 'I');
				b.getCell(e.getY(), e.getX()+i).setData(false, e.getDir(), 'E');
				
				
				if(e.getY()>0)
				{
					b.getCell(e.getY()-1, e.getX()+i).setData(false, e.getDir(), 'S');
					b.getCell(e.getY()-1, e.getX()+i).setData(false, e.getDir(), 'I');
					b.getCell(e.getY()-1, e.getX()+i).setData(false, e.getDir(), 'E');
					b.getCell(e.getY()-1, e.getX()+i).setData(false, CwEntry.Direction.VERT, 'E');
		
				}
				if(e.getY()<b.getHeight()-1)
				{
					b.getCell(e.getY()+1, e.getX()+i).setData(false, e.getDir(), 'S');
					b.getCell(e.getY()+1, e.getX()+i).setData(false, e.getDir(), 'I');
					b.getCell(e.getY()+1, e.getX()+i).setData(false, e.getDir(), 'E');
					b.getCell(e.getY()+1, e.getX()+i).setData(false, CwEntry.Direction.VERT, 'S');
				}
			}
			if(e.getX()>0)
			{
				b.getCell(e.getY(), e.getX()-1).setData(false, e.getDir(), 'S');
				b.getCell(e.getY(), e.getX()-1).setData(false, e.getDir(), 'I');
				b.getCell(e.getY(), e.getX()-1).setData(false, e.getDir(), 'E');
				b.getCell(e.getY(), e.getX()-1).setData(false, CwEntry.Direction.VERT, 'S');
				b.getCell(e.getY(), e.getX()-1).setData(false, CwEntry.Direction.VERT, 'I');
				b.getCell(e.getY(), e.getX()-1).setData(false, CwEntry.Direction.VERT, 'E');
			}
			if(e.getX()+e.getWord().length()<b.getWidth())
			{
				b.getCell(e.getY(), e.getX()+e.getWord().length()).setData(false, e.getDir(), 'S');
				b.getCell(e.getY(), e.getX()+e.getWord().length()).setData(false, e.getDir(), 'I');
				b.getCell(e.getY(), e.getX()+e.getWord().length()).setData(false, e.getDir(), 'E');
				b.getCell(e.getY(), e.getX()+e.getWord().length()).setData(false, CwEntry.Direction.VERT, 'S');
				b.getCell(e.getY(), e.getX()+e.getWord().length()).setData(false, CwEntry.Direction.VERT, 'I');
				b.getCell(e.getY(), e.getX()+e.getWord().length()).setData(false, CwEntry.Direction.VERT, 'E');
			}
				
		}
		else{
			for(int i=0; i<e.getWord().length(); i++)
			{
				String s = ""+e.getWord().charAt(i);
				b.getCell(e.getY()+i, e.getX()).setConntent(s);
				b.getCell(e.getY()+i, e.getX()).setData(false, e.getDir(), 'S');
				b.getCell(e.getY()+i, e.getX()).setData(false, e.getDir(), 'I');
				b.getCell(e.getY()+i, e.getX()).setData(false, e.getDir(), 'E');
				
				if(e.getX()>0)
				{
					b.getCell(e.getY()+i, e.getX()-1).setData(false, e.getDir(), 'S');
					b.getCell(e.getY()+i, e.getX()-1).setData(false, e.getDir(), 'I');
					b.getCell(e.getY()+i, e.getX()-1).setData(false, e.getDir(), 'E');
					b.getCell(e.getY()+i, e.getX()-1).setData(false, CwEntry.Direction.HORIZ, 'E');
		
				}
				if(e.getX()<b.getWidth()-1)
				{
					
					b.getCell(e.getY()+i, e.getX()+1).setData(false, e.getDir(), 'S');
					b.getCell(e.getY()+i, e.getX()+1).setData(false, e.getDir(), 'I');
					b.getCell(e.getY()+i, e.getX()+1).setData(false, e.getDir(), 'E');
					b.getCell(e.getY()+i, e.getX()+1).setData(false, CwEntry.Direction.HORIZ, 'S');
				}
			}
			if(e.getY()>0)
			{
				b.getCell(e.getY()-1, e.getX()).setData(false, e.getDir(), 'S');
				b.getCell(e.getY()-1, e.getX()).setData(false, e.getDir(), 'I');
				b.getCell(e.getY()-1, e.getX()).setData(false, e.getDir(), 'E');
				b.getCell(e.getY()-1, e.getX()).setData(false, CwEntry.Direction.HORIZ, 'S');
				b.getCell(e.getY()-1, e.getX()).setData(false, CwEntry.Direction.HORIZ, 'I');
				b.getCell(e.getY()-1, e.getX()).setData(false, CwEntry.Direction.HORIZ, 'E');
			}
			if(e.getY()+e.getWord().length()<b.getHeight())
			{
				b.getCell(e.getY()+e.getWord().length(), e.getX()).setData(false, e.getDir(), 'S');
				b.getCell(e.getY()+e.getWord().length(), e.getX()).setData(false, e.getDir(), 'I');
				b.getCell(e.getY()+e.getWord().length(), e.getX()).setData(false, e.getDir(), 'E');
				b.getCell(e.getY()+e.getWord().length(), e.getX()).setData(false, CwEntry.Direction.HORIZ, 'S');
				b.getCell(e.getY()+e.getWord().length(), e.getX()).setData(false, CwEntry.Direction.HORIZ, 'I');
				b.getCell(e.getY()+e.getWord().length(), e.getX()).setData(false, CwEntry.Direction.HORIZ, 'E');
			}
			
		}
	};
}
