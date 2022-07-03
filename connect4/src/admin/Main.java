/* 
 * 
 * GitHub Token: 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 
 * 
 */

package admin;

public class Main {
	public static void main(String[] args) {
		Const.setup();
		Game game = new Game();
		game.playAlone(0);
		//game.watchCpuVsCpu();
		
		/*
		long startTime = System.currentTimeMillis();
		
		for(int i = 0; i < 10000000; i++) {
			game = new Game();
			game.watchCpuVsCpu();
		}
		
		long endTime = System.currentTimeMillis();
		System.out.println("処理時間：" + (endTime - startTime) + " ms");
		*/
	}
}
