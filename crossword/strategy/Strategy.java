package strategy;

import Board.Board;
import crossword.Crossword;
import dictionary.CwEntry;
import myexception.EmptyListException;

abstract public class Strategy {
	abstract public CwEntry findEntry(Crossword cw) throws EmptyListException;
	
	abstract public void updateBoard(Board b, CwEntry e);

}
