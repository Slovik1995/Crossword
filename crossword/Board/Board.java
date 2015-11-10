package Board;

import java.util.LinkedList;

public class Board {
	private BoardCell[][] board=null;
	
	public Board(int h, int w){
		board = new BoardCell[h][w];
		for(int i=0; i<h; i++){
			for(int j=0; j<w; j++){
				board[i][j] = new BoardCell(i,j);
			}
		}
	}
	public int getWidth(){
		if(board!=null) return board[0].length;
		return 0;
	};
	public int getHeight(){
		if(board!=null) return board.length;
		return 0;
	};
	public BoardCell getCell(int y, int x){
		return board[y][x];
	}; 
	public void setCell(int x, int y, BoardCell c){
		board[x][y].setConntent(c.getContent());
	};
	public LinkedList<BoardCell> getStartCells(){
		LinkedList<BoardCell> cellList = new LinkedList<>();
		for(int i=0; i<getHeight(); i++)
			for(int j=0; j<getWidth(); j++)
			{
				if((board[i][j].getVStart()==true) || (board[i][j].getHStart()==true))
					cellList.add(board[i][j]);
			}

		return cellList;
	
	};
	public String createPattern(int fromy, int fromx, int toy, int tox){
		if(fromx>tox){
			int tem=fromx;
			fromx=tox;
			tox=tem;
		}
		if(fromy>toy){
			int tem=fromy;
			fromy=toy;
			toy=tem;
		}
		if((!(fromx==tox || fromy==toy)) || ((fromx==tox)&&(fromy==toy))) return null;

		if(fromx<0 || fromy<0 || tox>=getWidth() || toy>= getHeight())
			return null;

		int howManyLetters = (tox-fromx)>(toy-fromy)? tox-fromx : toy-fromy;
		howManyLetters++;
		String regex="";
		if(fromy==toy){
			int distance = fromx + howManyLetters;
			if(distance>getWidth())
				return null;
			for(int k=fromx; k<fromx+howManyLetters; k++){
				if(board[fromy][k].getContent().equals("")){
					regex+="[\\w]";
				}
				else{
					regex+=""+board[fromy][k].getContent();
				}
			}
		}
		else{
			int distance = fromy + howManyLetters;
			if(distance>getHeight())
				return null;
			for(int k=fromy; k<fromy+howManyLetters; k++){
				if(board[k][fromx].getContent().equals("")){
					regex+="[\\w]";
				}
				else{
					regex+=""+board[k][fromx].getContent();
				}
			}
		}

	return regex;
		
	};
}
