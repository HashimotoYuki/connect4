package computer;

import java.util.Random;

import admin.BoardModel;
import admin.Const;

public class CpuRandom extends Cpu {
	public CpuRandom(int playerId) {
		super(playerId);
	}

	@Override
	public int decideCol(BoardModel board) {	
		Random rand = new Random();
		int col;
		do {
			col = rand.nextInt(Const.NUM_COLS);
		} while(board.isColFilled(col));
		return col;
	}
}
