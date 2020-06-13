package test;

import chess.ChessTable;
import db.Database;

/**
 * Test archive game and load game function
 */
public class TestArchiveAndLoadFromDataset {
	
	// db
	private Database db;
	
	// chess table
	private ChessTable chessTable;
	
	/**
	 * Connect the dataset
	 */
	public void testConnect() {
		chessTable = new ChessTable(null);
		db = new Database(chessTable);
	}
	
	/**
	 * Test archive game
	 */
	public void testArchiveGame() {
		db.archiveGame();
	}
	
	/**
	 * Test load game
	 */
	public void testLoadGame() {
		db.loadGame();
	}
	
	// Main method
	public static void main(String[] args) {
		TestArchiveAndLoadFromDataset testDerbyDatabase = new TestArchiveAndLoadFromDataset();
		testDerbyDatabase.testConnect();
		testDerbyDatabase.testArchiveGame();
		testDerbyDatabase.testLoadGame();
	}
}
