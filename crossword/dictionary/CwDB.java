package dictionary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class CwDB {
	protected LinkedList<Entry> dict;
	String path="";
	public CwDB(String filename)
	{
		dict = new LinkedList<>();
		path=filename;
		createDB(path);
	};
	public void add(String word, String clue)
	{
		Entry e = new Entry(word, clue);
		dict.add(e);
	};
	public Entry get(String word)
	{
		Entry e=null;
		Iterator<Entry> i = dict.iterator();
		while(i.hasNext()){
			if((e=i.next()).getWord().equals(word)){
				return e;
			}
		}
		return null;
	};
	public void remove(String word)
	{
		Iterator<Entry> i = dict.iterator();
		int k;
		for(k=0; k<dict.size(); k++)
			if(i.next().getWord().equals(word)){
				break;
		}
		if(k<dict.size()) dict.remove(k); 
	};
	public void saveDB(String filename)
	{
		File f = new File(filename);
		FileWriter w=null;
		BufferedWriter b=null; 
		try {
			w = new FileWriter(f);
			b = new BufferedWriter(w);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Entry e;
		Iterator<Entry> i = dict.iterator();
		while(i.hasNext()){
			e=i.next();
			try {
				b.write(e.getWord());
				b.write(System.getProperty( "line.separator" ));
				b.write(e.getClue());
				b.write(System.getProperty( "line.separator" ));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}
		try {
			b.close();
			w.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	};
	public int getSize()
	{
		return dict.size();
	};
	protected void createDB(String filename)
	{
		File f = new File(filename);
		FileReader w=null;
		try {
			w = new FileReader(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader b = new BufferedReader(w);
		String s;
		try {
			while((s=b.readLine())!=null){
				add(s, b.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			b.close();
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
}
