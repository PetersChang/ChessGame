package test;

import chess.ChessTable;
import config.Config;

/**
 * Test chess table funcions
 *
 */
public class TestChessTable {
	
	// Main
	public static void main(String[] args) {
		// create new table
		ChessTable chessTable = new ChessTable(null);
		chessTable.hash();
		
		// print chess table chesses
		for (int i = 0; i < Config.ROWS_NUM; i++) {
			for (int j = 0; j < Config.COLUMNS_NUM; j++) {
				System.out.print(chessTable.chesses[i][j] + " ");
			}
			System.out.println();
		}
		
		// print chess table chess value
		for (int i = 0; i < Config.ROWS_NUM; i++) {
			for (int j = 0; j < Config.COLUMNS_NUM; j++) {
				System.out.print(chessTable.chessValue[i][j] + " ");
			}
			System.out.println();
		}
	}
}
