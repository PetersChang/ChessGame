package listener;

import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import chess.ChessTable;
import db.Database;
import ui.ChessTableUI;

/**
 * Handle the load game event
 *
 */
public class LoadGameListener extends ChessListener {
	
	// database
	private Database db;
	
	public LoadGameListener(ChessTable chessTable, ChessTableUI chessTableUI, Graphics graphics, Database db) {
		super(chessTable, chessTableUI, graphics);
		this.db = db;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// if game is running, cannot load game from database
		if (chessTable.isGame()) {
			JOptionPane.showMessageDialog(null, "Game already started");
			return;
		}
		
		// load the game
		db.loadGame();
		// repaint the chess table UI
		chessTableUI.paint(graphics, chessTable.chesses);
		chessTable.hash();
		chessTable.setGame(true);
		chessTable.setName("Load Game");
	}

}
