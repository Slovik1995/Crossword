package saved;

import java.io.IOException;

import crossword.Crossword;

public interface Writer {
	public void write(Crossword c) throws IOException;	
}
