package admin;

public class BoardModel {
	private long[] piecePos = new long[2];
	
	public BoardModel() {
		piecePos[0] = 0;
		piecePos[1] = 0;
	}
	
	public BoardModel(BoardModel original) {
		piecePos[0] = original.getPiecePos(0);
		piecePos[1] = original.getPiecePos(1);
	}
	
	public void dropPiece(int playerId, int col) {
		piecePos[playerId] |=  ((piecePos[0]|piecePos[1]) & Const.MASK_COLS[col]) + Const.LSB_COLS[col];
	}
	
	public boolean isColFilled(int col) {
		if(((piecePos[0]|piecePos[1]) & Const.MASK_COLS[col]) == Const.MASK_COLS[col]) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isBoardFilled() {
		if((piecePos[0]|piecePos[1]) == 0x003f3f3f3f3f3f3fL) {
			return true;
		} else {
			return false;
		}
	}
	
	public int countColPieces(int col) {
		long colBits = (piecePos[0]|piecePos[1]) & Const.MASK_COLS[col];
		colBits = (colBits & 0x5555555555555555L) + (colBits>>1 & 0x5555555555555555L);
		colBits = (colBits & 0x3333333333333333L) + (colBits>>2 & 0x3333333333333333L);
		colBits = (colBits & 0x0f0f0f0f0f0f0f0fL) + (colBits>>4 & 0x0f0f0f0f0f0f0f0fL);
		colBits = (colBits & 0x00ff00ff00ff00ffL) + (colBits>>8 & 0x00ff00ff00ff00ffL);
		colBits = (colBits & 0x0000ffff0000ffffL) + (colBits>>16 & 0x0000ffff0000ffffL);
		colBits = (colBits & 0x00000000ffffffffL) + (colBits>>32 & 0x00000000ffffffffL);
		return (int)colBits;
	}
	
	public boolean isAligned(int playerId) {
		for(long mask : Const.MASK_LINES) {
			if((piecePos[playerId] & mask) == mask) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isAlignedAfterDrop(int playerId, int col) {
		for(long mask : Const.MASK_POSSIBLE_LINES[col]) {
			if((piecePos[playerId] & mask) == mask) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isAlignedAfterDropOld(int playerId, int col) {
		for(long mask : Const.MASK_POSSIBLE_LINES_OLD[col][countColPieces(col)-1]) {
			if((piecePos[playerId] & mask) == mask) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isSymmetry() {
		if((piecePos[0] & Const.MASK_COLS[0]) == (piecePos[0] & Const.MASK_COLS[6]) &&
			(piecePos[1] & Const.MASK_COLS[0]) == (piecePos[1] & Const.MASK_COLS[6]) &&
			(piecePos[0] & Const.MASK_COLS[1]) == (piecePos[0] & Const.MASK_COLS[5]) &&
			(piecePos[1] & Const.MASK_COLS[1]) == (piecePos[1] & Const.MASK_COLS[5]) &&
			(piecePos[0] & Const.MASK_COLS[2]) == (piecePos[0] & Const.MASK_COLS[4]) &&
			(piecePos[1] & Const.MASK_COLS[2]) == (piecePos[1] & Const.MASK_COLS[4])
		){
			return true;
		} else {
			return false;
		}
	}
	
	public void printBoardForDebug() {
		System.out.println("-------------");
		for(int i = 0; i < Const.NUM_ROWS; i++) {
			long bitMask = 0x0000000000000020L >> i;
			for(int j = 0; j < Const.NUM_COLS; j++) {
				if((piecePos[0] & bitMask) != 0L) {
					System.out.print("o ");
				} else if((piecePos[1] & bitMask) != 0L) {
					System.out.print("x ");
				} else {
					System.out.print("  ");
				}
				bitMask <<= 8;
			}
			System.out.println();
		}
		System.out.println("-------------\n0 1 2 3 4 5 6\n");
	}
	
	public long getPiecePos(int playerId) {
		return piecePos[playerId];
	}
}
