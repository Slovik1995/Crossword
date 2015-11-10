package dictionary;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import myexception.EmptyListException;

public class InteliCwDB extends CwDB{
	public InteliCwDB(String filename)
	{
		super(filename);
	};
	public LinkedList<Entry> findAll(String pattern) throws EmptyListException
	{
		LinkedList<Entry> list = new LinkedList<>();
		Pattern p = Pattern.compile(pattern);
		Matcher m=null;
		Entry e=null;
	
		Iterator<Entry> i = dict.iterator();
		while(i.hasNext()){
			m=p.matcher((e=i.next()).getWord());
			if(m.matches())
				list.add(e);
		}
		if(list.size()==0) throw new EmptyListException();
		return list;
	};
	public Entry getRandom()
	{
		Random rand = new Random();
		Iterator<Entry> i;
		
			i = dict.iterator();
			int limit = Math.abs(rand.nextInt()%dict.size());
		for(int k=0; k<limit; k++){
			i.next();
		}
		return i.next();	
	};
	public Entry getRandomFirstSimple()
	{
		String checkUpperLetter;
		Entry e=null;
		Random rand = new Random();
		Iterator<Entry> i;
		do{
			i = dict.iterator();
			int limit = Math.abs(rand.nextInt()%dict.size());
		for(int k=0; k<limit; k++){
			i.next();
		}
		e=i.next();
		checkUpperLetter = e.getWord().substring(0, 1);
		} while((checkUpperLetter.matches("[QWERTYUIOPLKJHGFDSAZXCVBNMÄ„Ä�Ă“ĹšĹ�Ĺ»ĹąÄ†Ĺ�][\\w]*"))||e.getWord().toLowerCase().matches("[\\w]*[Ä™ĂłÄ…Ĺ›Ĺ‚ĹĽĹşÄ‡Ĺ„vxqy][\\w]*||[\\w]*[Ä™ĂłÄ…Ĺ›Ĺ‚ĹĽĹşÄ‡Ĺ„vxqy][\\w]*[Ä™ĂłÄ…Ĺ›Ĺ‚ĹĽĹşÄ‡Ĺ„vxqy][\\w]*||[\\w]*[Ä™ĂłÄ…Ĺ›Ĺ‚ĹĽĹşÄ‡Ĺ„vxqy][\\w]*[Ä™ĂłÄ…Ĺ›Ĺ‚ĹĽĹşÄ‡Ĺ„vxqy][\\w]*[Ä™ĂłÄ…Ĺ›Ĺ‚ĹĽĹşÄ‡Ĺ„vxqy][\\w]*"));
	
		return e;	
	};

	
	public Entry getRandomLengthFirstSimple(int length)
	{
		if(length==0) return getRandomFirstSimple();
		String checkUpperLetter;
		LinkedList<Entry> list = new LinkedList<>();
		Entry e = null;
		Iterator<Entry> i = dict.iterator();
		while(i.hasNext()){
			if((e=i.next()).getWord().length()==length)
				list.add(e);
		}
		if(list.size()==0) return null;
		Random rand = new Random();
		do{
			i=list.iterator();
			int limit = Math.abs(rand.nextInt()%list.size());
			for(int k=0; k<limit; k++)
				i.next();
			e=i.next();
			checkUpperLetter = e.getWord().substring(0, 1);
		}while((checkUpperLetter.matches("[QWERTYUIOPLKJHGFDSAZXCVBNMÄ„Ä�Ă“ĹšĹ�Ĺ»ĹąÄ†Ĺ�][\\w]*"))||e.getWord().toLowerCase().matches("[\\w]*[Ä™ĂłÄ…Ĺ›Ĺ‚ĹĽĹşÄ‡Ĺ„vxqy][\\w]*||[\\w]*[Ä™ĂłÄ…Ĺ›Ĺ‚ĹĽĹşÄ‡Ĺ„vxqy][\\w]*[Ä™ĂłÄ…Ĺ›Ĺ‚ĹĽĹşÄ‡Ĺ„vxqy][\\w]*||[\\w]*[Ä™ĂłÄ…Ĺ›Ĺ‚ĹĽĹşÄ‡Ĺ„vxqy][\\w]*[Ä™ĂłÄ…Ĺ›Ĺ‚ĹĽĹşÄ‡Ĺ„vxqy][\\w]*[Ä™ĂłÄ…Ĺ›Ĺ‚ĹĽĹşÄ‡Ĺ„vxqy][\\w]*"));
	
		return e;
	}
	public Entry getRandom(int length) throws EmptyListException
	{
		LinkedList<Entry> list = new LinkedList<>();
		Entry e = null;
		Iterator<Entry> i = dict.iterator();
		while(i.hasNext()){
			if((e=i.next()).getWord().length()==length)
				list.add(e);
		}
		if(list.size()==0) throw new EmptyListException();
		i=list.iterator();
		Random rand = new Random();
			int limit = Math.abs(rand.nextInt()%list.size());
			for(int k=0; k<limit; k++)
				i.next();
			return i.next();
	};
	public Entry getRandom(String pattern) throws EmptyListException
	{
		LinkedList<Entry> list = new LinkedList<>();
		Pattern p = Pattern.compile(pattern);
		Matcher m=null;
		Entry e=null;
		Iterator<Entry> i = dict.iterator();
		while(i.hasNext()){
			m=p.matcher((e=i.next()).getWord());
			if(m.matches())
				list.add(e);
		}
		if(list.size()==0) throw new EmptyListException();
		
		Random rand = new Random();
		int limit = Math.abs(rand.nextInt()%list.size());
		i=list.iterator();
		for(int k=0; k<limit; k++)
			i.next();
		return i.next();
	};
	@Override
	public void add(String word, String clue)
	{
		Entry e = new Entry(word, clue);
		int i;
		for(i=0; i<dict.size(); i++)
			if(dict.get(i).getWord().toLowerCase().compareTo(e.getWord().toLowerCase())>0){
				dict.add(i, e);
				return;
			}
		dict.addLast(e);
		
	};
}
