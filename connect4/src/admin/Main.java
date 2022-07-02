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
		game.watchCpuVsCpu();
		//game.playTogether();
	}
}
