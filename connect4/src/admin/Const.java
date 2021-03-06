package admin;

import java.util.ArrayList;

public class Const {
	private Const() {
	}
	
	public static void setup() {
		setupMaskPossibleLineOld();
		setupMaskPossibleLine();
	}

	public static final int NUM_COLS = 7;
	public static final int NUM_ROWS = 6;
	
	public static final long[] MASK_COLS = {
		0x000000000000003fL, 0x0000000000003f00L, 0x00000000003f0000L, 0x000000003f000000L, 
		0x0000003f00000000L, 0x00003f0000000000L, 0x003f000000000000L
	};
	public static final long[] LSB_COLS = {
		0x0000000000000001L, 0x0000000000000100L, 0x0000000000010000L, 0x0000000001000000L,
		0x0000000100000000L, 0x0000010000000000L, 0x0001000000000000L
	};
	public static final long[] MASK_LINES = {
		0x000000000000000fL, 0x000000000000001eL, 0x000000000000003cL, 
		0x0000000000000f00L, 0x0000000000001e00L, 0x0000000000003c00L, 
		0x00000000000f0000L, 0x00000000001e0000L, 0x00000000003c0000L, 
		0x000000000f000000L, 0x000000001e000000L, 0x000000003c000000L, 
		0x0000000f00000000L, 0x0000001e00000000L, 0x0000003c00000000L, 
		0x00000f0000000000L, 0x00001e0000000000L, 0x00003c0000000000L, 
		0x000f000000000000L, 0x001e000000000000L, 0x003c000000000000L,
		
		0x0000000001010101L, 0x0000000101010100L, 0x0000010101010000L, 0x0001010101000000L,
		0x0000000002020202L, 0x0000000202020200L, 0x0000020202020000L, 0x0002020202000000L,
		0x0000000004040404L, 0x0000000404040400L, 0x0000040404040000L, 0x0004040404000000L,
		0x0000000008080808L, 0x0000000808080800L, 0x0000080808080000L, 0x0008080808000000L,
		0x0000000010101010L, 0x0000001010101000L, 0x0000101010100000L, 0x0010101010000000L,
		0x0000000020202020L, 0x0000002020202000L, 0x0000202020200000L, 0x0020202020000000L,
		
		0x0000000008040201L, 0x0000000010080402L, 0x0000000020100804L,
		0x0000000804020100L, 0x0000001008040200L, 0x0000002010080400L,
		0x0000080402010000L, 0x0000100804020000L, 0x0000201008040000L,
		0x0008040201000000L, 0x0010080402000000L, 0x0020100804000000L,
		
		0x0000000001020408L, 0x0000000002040810L, 0x0000000004081020L,
		0x0000000102040800L, 0x0000000204081000L, 0x0000000408102000L,
		0x0000010204080000L, 0x0000020408100000L, 0x0000040810200000L,
		0x0001020408000000L, 0x0002040810000000L, 0x0004081020000000L
	};
	
	/*
	 * MASK_POSSIBLE_LINES_LIST[c]:
	 * 	c?????????????????????????????????????????????????????????????????????????????????????????????
	 */
	public static final long[][] MASK_POSSIBLE_LINES = new long[NUM_COLS][];
	
	private static void setupMaskPossibleLine() {
		for(int c = 0; c < NUM_COLS; c++) {
			long possibleDropPos = 0x000000000000003fL << c*8;
			ArrayList<Long> applicableLines = new ArrayList<>();  
			for(long mask : MASK_LINES) {
				if((possibleDropPos & mask) != 0) {
					applicableLines.add(mask);
				}
			}
			MASK_POSSIBLE_LINES[c] = new long[applicableLines.size()];
			for(int i = 0; i < applicableLines.size(); i++) {
				MASK_POSSIBLE_LINES[c][i] = applicableLines.get(i);
			}	
		}
	}

	public static final long[][][] MASK_POSSIBLE_LINES_OLD = new long[NUM_COLS][NUM_ROWS][];
	
	private static void setupMaskPossibleLineOld() {
		for(int c = 0; c < NUM_COLS; c++) {
			for(int r = 0; r < NUM_ROWS; r++) {	
				long dropPos = 0x0000000000000001L << c*8+r;
				ArrayList<Long> applicableLines = new ArrayList<>();  
				for(int i = 0; i < MASK_LINES.length; i++) {
					if((dropPos & MASK_LINES[i]) != 0 && (dropPos<<1 & MASK_LINES[i]) == 0) {
						applicableLines.add(MASK_LINES[i]);
					}
				}
				MASK_POSSIBLE_LINES_OLD[c][r] = new long[applicableLines.size()];
				for(int i = 0; i < applicableLines.size(); i++) {
					MASK_POSSIBLE_LINES_OLD[c][r][i] = applicableLines.get(i);
				}
			}
		}
	}
}
