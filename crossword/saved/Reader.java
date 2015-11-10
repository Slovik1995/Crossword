package saved;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import crossword.Crossword;

public interface Reader {
	public LinkedList<Crossword> getAllCws() throws FileNotFoundException, NumberFormatException, IOException; 
}
