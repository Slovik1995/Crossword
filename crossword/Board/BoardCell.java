package Board;

import dictionary.CwEntry;

public class BoardCell {
	private String letter="";
	private boolean enabled;
	private boolean VStart=true, VInner=true, VEnd=true, 
					HStart=true, HInner=true, HEnd=true;
	private int X,Y;
	public BoardCell(int y, int x){
		Y=y;
		X=x;
	}
	public int getX(){
		return X;
	}
	public int getY(){
		return Y;
	}
	public void setConntent(String content){
		letter=content;
	}
	public String getContent(){
		return letter;
	}
	public boolean getEnable(){
		return enabled;
	}
	public boolean getVStart(){
		return VStart;
	}
	public boolean getVInner(){
		return VInner;
	}
	public boolean getVEnd(){
		return VEnd;
	}
	public boolean getHStart(){
		return HStart;
	}
	public boolean getHInner(){
		return HInner;
	}
	public boolean getHEnd(){
		return HEnd;
	}

	public void setData(boolean enable, CwEntry.Direction d, char sie){
		enabled=enable;
		if(d.toString().equals("HORIZ")){
			if(sie=='S')
				HStart=false;
			else if(sie=='I')
				HInner=false;
			else HEnd=false;
		}
		else if(d.toString().equals("VERT")) {
			if(sie=='S')
				VStart=false;
			else if(sie=='I')
				VInner=false;
			else VEnd=false;
		}
	}
}
