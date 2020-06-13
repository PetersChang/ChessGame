package test;

import java.awt.Graphics;

import chess.ChessTable;
import db.Database;
import listener.AdmitDefeatListener;
import listener.ArchiveGameListener;
import listener.LoadGameListener;
import listener.RegretGameListener;
import listener.RestartListener;
import listener.StartListener;
import ui.ChessTableUI;

/**
 * Test the chess UI funtions
 */
public class TestChessUI {
	
	// UI object
	private ChessTableUI chessTableUI;
	
	// initial objects and listeners
	public void init() {
		chessTableUI = new ChessTableUI();
		Graphics graphics = chessTableUI.getGraphics();
		ChessTable chessTable = new ChessTable(graphics);
		chessTableUI.setChessTable(chessTable);
		
		Database db = new Database(chessTable);
		
		// create listener
		RestartListener restartListener = new RestartListener(chessTable, chessTableUI, graphics);
		StartListener startListener = new StartListener(chessTable, chessTableUI, graphics);
		AdmitDefeatListener admitDefeatListener = new AdmitDefeatListener(chessTable, chessTableUI, graphics);
		RegretGameListener regretGameListener = new RegretGameListener(chessTable, chessTableUI, graphics);
		LoadGameListener loadGameListener = new LoadGameListener(chessTable, chessTableUI, graphics, db); 
		ArchiveGameListener archiveGameListener = new ArchiveGameListener(chessTable, chessTableUI, graphics, db);
		
		// set listener
		chessTableUI.setRestartListener(restartListener);
		chessTableUI.setStartListener(startListener);
		chessTableUI.setAdmitDefeatListener(admitDefeatListener);
		chessTableUI.setRegretGameListener(regretGameListener);
		chessTableUI.setLoadGameListener(loadGameListener);
		chessTableUI.setArchiveGameListener(archiveGameListener);
	}
	
	/**
	 * Test fresh chess table
	 */
	public void testFreshChessTable() {
		chessTableUI.start();
		for (int i = 0; i < 3; i++) {
			try {
				Thread.sleep(1000);
				chessTableUI.paint(chessTableUI.getGraphics());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Test load game from database
	 */
	public void testLoadGame() {
		try {
			System.out.println("Test load game.");
			chessTableUI.getLoadGameButton().doClick();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test start function
	 */
	public void testStart() {
		try {
			System.out.println("Test start button.");
			chessTableUI.getStartButton().doClick();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test restart function
	 */
	public void testRestart() {
		try {
			System.out.println("Test restart button.");
			chessTableUI.getRestartButton().doClick();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test defeat function
	 */
	public void testDefeat() {
		try {
			System.out.println("Test defeaet.");
			chessTableUI.getAdmitDefeatButton().doClick();
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	// Main
	public static void main(String[] args) {
		TestChessUI testChessUI = new TestChessUI();
		testChessUI.init();
		testChessUI.testFreshChessTable();
		testChessUI.testLoadGame();
		testChessUI.testStart();
		testChessUI.testRestart();
		testChessUI.testLoadGame();
		testChessUI.testDefeat();
		
		// close the UI
		testChessUI.chessTableUI.close();
	}
}
