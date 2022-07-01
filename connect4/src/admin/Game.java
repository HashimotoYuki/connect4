package admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import computer.*;

public class Game {
	private BoardModel board;
	
	public Game() {
		board = new BoardModel();
	}
	
	public void playAlone(int userId) {
		Cpu cpu = new CpuRandom(userId^1);
		
		board.printBoardForDebug();
		if(userId == 0) {
			while(true) {
				if(handleUserTurn(0)) break;
				board.printBoardForDebug();
				if(handleCpuTurn(cpu)) break;
				board.printBoardForDebug();
			}
		} else {
			while(true) {
				if(handleCpuTurn(cpu)) break;
				board.printBoardForDebug();
				if(handleUserTurn(1)) break;
				board.printBoardForDebug();
			}
		}
		board.printBoardForDebug();
	}
	
	public void playTogether() {
		board.printBoardForDebug();
		while(true) {
			if(handleUserTurn(0)) break;
			board.printBoardForDebug();
			if(handleUserTurn(1)) break;
			board.printBoardForDebug();
		}
		board.printBoardForDebug();
	}
	
	public void watchCpuVsCpu() {
		Cpu cpu0 = new CpuRandom(0);
		Cpu cpu1 = new CpuRandom(1);
		board.printBoardForDebug();
		while(true) {
			if(handleCpuTurn(cpu0)) break;
			board.printBoardForDebug();
			if(handleCpuTurn(cpu1)) break;
			board.printBoardForDebug();
		}
		board.printBoardForDebug();
		
	}
	
	/* 勝敗が決まればtrueを返す */
	private boolean handleUserTurn(int playerId) {
		int inputCol;
		while(true) {
			System.out.print("col: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String buf = null;
			try {
				buf = br.readLine();
			} catch(IOException e){
				e.printStackTrace();
				System.exit(1);
			}
			try {
				inputCol = Integer.parseInt(buf);
			} catch (NumberFormatException e) {
				continue;
			}
			if(0 <= inputCol && inputCol <= 6 && !board.isColFilled(inputCol)) break;
		}
		board.dropPiece(playerId, inputCol);
		
		return (board.isAligned(playerId) || board.isBoardFilled());
	}
	
	/* 勝敗が決まればtrueを返す */
	private boolean handleCpuTurn(Cpu cpu) {
		board.dropPiece(cpu.getPlayerId(), cpu.decideCol(board));
		return (board.isAligned(cpu.getPlayerId()) || board.isBoardFilled());
	}
}