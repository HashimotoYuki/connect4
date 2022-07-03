package computer;

import admin.BoardModel;
import admin.Const;

public class CpuMinimax extends Cpu {
	private final int INITIAL_DEPTH = 10;
	private final int INF = 999999;
	
	public CpuMinimax(int playerId) {
		super(playerId);
	}

	@Override
	public int decideCol(BoardModel board) {
		int returnCol = -1;
		int maxValue = -INF;
		for(int c = 0; c < Const.NUM_COLS; c++) {
			if(board.isSymmetry() && c == 4) break;
			if(board.isColFilled(c)) continue;
			BoardModel nextBoard = new BoardModel(board);
			nextBoard.dropPiece(playerId, c);
			
			int value;
			if(nextBoard.isAlignedAfterDrop(playerId, c)) {
				return c;
			} else {
				if(INITIAL_DEPTH == 1) {
					value = staticEvaluateBoard(nextBoard);
				} else {
					int numPiece = countPiece(board.getPiecePos(0)|board.getPiecePos(1));
					int depth = INITIAL_DEPTH + (int)Math.pow(1.08, numPiece) - 2;
					value = minimaxEvaluateBoard(nextBoard, playerId^1, depth);
					System.out.println(c+" depth:"+depth+" val: "+value);
				}
			}
			if(value > maxValue) {
				maxValue = value;
				returnCol = c;
			}
		}
		return returnCol;
	}
	
	private int minimaxEvaluateBoard(BoardModel board, int nextPlayerId, int depth) {
		if(nextPlayerId == this.playerId) {
			int maxValue = -INF;
			for(int c = 0; c < Const.NUM_COLS; c++) {
				if(board.isColFilled(c)) continue;
				BoardModel nextBoard = new BoardModel(board);
				nextBoard.dropPiece(nextPlayerId, c);
				
				int value;
				if(nextBoard.isAlignedAfterDrop(nextPlayerId, c)) {
					return 999+depth;
				} else if(nextBoard.isBoardFilled()) {
					return 0;
				} else {
					if(depth == 1) {
						value = staticEvaluateBoard(nextBoard);
					} else {
						value = minimaxEvaluateBoard(nextBoard, nextPlayerId^1, depth-1);
					}
				}
				if(value > maxValue) {
					maxValue = value;
				}
			}
			return maxValue;
			
		} else {
			int minValue = INF;
			for(int c = 0; c < Const.NUM_COLS; c++) {
				if(board.isColFilled(c)) continue;
				BoardModel nextBoard = new BoardModel(board);
				nextBoard.dropPiece(nextPlayerId, c);
				
				int value;
				if(nextBoard.isAlignedAfterDrop(nextPlayerId, c)) {
					return -999-depth;
				} else if(nextBoard.isBoardFilled()) {
					return 0;
				} else {
					if(depth == 1) {
						value = staticEvaluateBoard(nextBoard);
					} else {
						value = minimaxEvaluateBoard(nextBoard, nextPlayerId^1, depth-1);
					}
				}
				if(value < minValue) {
					minValue = value;
				}
			}
			return minValue;
		}
	}
	
	private int staticEvaluateBoard(BoardModel board) {
		int value = 0;
		for(long mask : Const.MASK_LINES) {
			if((board.getPiecePos(playerId^1) & mask) == 0) {
				int numOnLine = countPiece(board.getPiecePos(playerId) & mask);
				if(numOnLine == 3) {
					value += 5;
				} else if(numOnLine == 2) {
					value += 1;
				}
				
			} else if((board.getPiecePos(playerId) & mask) == 0){
				int numOnLine = countPiece(board.getPiecePos(playerId^1) & mask);
				if(numOnLine == 3) {
					value -= 5;
				} else if(numOnLine == 2) {
					value -= 1;
				}
			}
		}
		return value;
	}
	
	private int countPiece(long piecePos) {
		piecePos = (piecePos & 0x5555555555555555L) + (piecePos>>1 & 0x5555555555555555L);
		piecePos = (piecePos & 0x3333333333333333L) + (piecePos>>2 & 0x3333333333333333L);
		piecePos = (piecePos & 0x0f0f0f0f0f0f0f0fL) + (piecePos>>4 & 0x0f0f0f0f0f0f0f0fL);
		piecePos = (piecePos & 0x00ff00ff00ff00ffL) + (piecePos>>8 & 0x00ff00ff00ff00ffL);
		piecePos = (piecePos & 0x0000ffff0000ffffL) + (piecePos>>16 & 0x0000ffff0000ffffL);
		piecePos = (piecePos & 0x00000000ffffffffL) + (piecePos>>32 & 0x00000000ffffffffL);
		return (int)piecePos;
	}
}
