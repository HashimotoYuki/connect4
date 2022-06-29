package connect4;

import java.util.Scanner;

public class Game {
	private BoardModel board;
	
	public void start() {
		board = new BoardModel();
		board.printBoardForDebug();
		
		Scanner scanner = new Scanner(System.in);
		int inputCol;
		int currentPlayer = 0;
		while(true) {
			do {
				System.out.print("Col:");
				inputCol = scanner.nextInt();
			} while (inputCol < 0 || 6 < inputCol || board.isColFilled(inputCol));
			
			board.dropPiece(currentPlayer, inputCol);
			board.printBoardForDebug();
			
			if(board.isAligned(currentPlayer) || board.isBoardFilled()) break;
			currentPlayer ^= 1;
		}
		scanner.close();
	}
}
