import java.io.IOException;
import java.util.LinkedList;
import crossword.Crossword;
import crossword.Draw;
import dictionary.CwEntry;
import dictionary.InteliCwDB;
import myexception.EmptyListException;
import saved.CReader;
import saved.CWriter;
import saved.CwBrowser;
import strategy.StrategyAdvanced;
import strategy.StrategySimple;

public class Main {
public static void main(String[] arg) throws IOException{
//	Crossword crossword = new Crossword(30,30,"C:\\Users\\Jack\\Desktop\\slowa44.txt");
//	InteliCwDB db = new InteliCwDB("C:\\Users\\Jack\\Desktop\\slowa44.txt");
	//db.createDB("C:\\Users\\Jack\\Desktop\\slowa.txt");
	StrategySimple strategy = new StrategySimple(); 
//	crossword.generate(strategy);

	
	
//	StrategyAdvanced strategy = new StrategyAdvanced();
	CwBrowser browser = new CwBrowser("C:\\Users\\Jack\\Desktop\\slowa44.txt",
			"C:\\Users\\Jack\\Desktop\\krzyzowki");
	try {
		browser.generate();
	} catch (EmptyListException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	
//	String s ="[\\w][\\w]";
	//System.out.println(s.matches("(\\[\\\\\\w\\])*"));
	
	
	
	
	
	//	CWriter zapis = new CWriter("C:\\Users\\Jack\\Desktop\\XYZ");
//write(crossword);
//	CReader r = new CReader("C:\\Users\\Jack\\Desktop\\XYZ");
//	Crossword word = r.getAllCws();
//	Draw draw2 = new Draw(crossword);
//	draw2.paint();
}
}
