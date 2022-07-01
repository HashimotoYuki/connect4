package computer;

import admin.BoardModel;

public abstract class Cpu {
	int playerId;
	
	public Cpu(int playerId) {
		this.playerId = playerId;
	}
	
	public int getPlayerId() {
		return playerId;
	}

	abstract public int decideCol(BoardModel board); 
}
