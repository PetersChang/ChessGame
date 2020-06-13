package chess;
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
 * Main class for chess game.
 */
public class Main {
	
	// Main function
	public static void main(String args[]) {
		// create ui object
		ChessTableUI chessTableUI = new ChessTableUI();
		// get graphics object
		Graphics graphics = chessTableUI.getGraphics();
		// create chess table
		ChessTable chessTable = new ChessTable(graphics);
		chessTableUI.setChessTable(chessTable);
		
		// create database
		Database db = new Database(chessTable);
		
		// create listener objects
		RestartListener restartListener = new RestartListener(chessTable, chessTableUI, graphics);
		StartListener startListener = new StartListener(chessTable, chessTableUI, graphics);
		AdmitDefeatListener admitDefeatListener = new AdmitDefeatListener(chessTable, chessTableUI, graphics);
		RegretGameListener regretGameListener = new RegretGameListener(chessTable, chessTableUI, graphics);
		LoadGameListener loadGameListener = new LoadGameListener(chessTable, chessTableUI, graphics, db); 
		ArchiveGameListener archiveGameListener = new ArchiveGameListener(chessTable, chessTableUI, graphics, db);
		
		// UI set listeners
		chessTableUI.setRestartListener(restartListener);
		chessTableUI.setStartListener(startListener);
		chessTableUI.setAdmitDefeatListener(admitDefeatListener);
		chessTableUI.setRegretGameListener(regretGameListener);
		chessTableUI.setLoadGameListener(loadGameListener);
		chessTableUI.setArchiveGameListener(archiveGameListener);
		
		// start UI
		chessTableUI.start();
	}
}
