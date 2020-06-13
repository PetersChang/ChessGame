package listener;

import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import chess.ChessTable;
import db.Database;
import ui.ChessTableUI;

/**
 * Handle the archive game event 
 */
public class ArchiveGameListener extends ChessListener {

	// database
	private Database db;
	
	public ArchiveGameListener(ChessTable chessTable, ChessTableUI chessTableUI, Graphics graphics, Database db) {
		super(chessTable, chessTableUI, graphics);
		this.db = db;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		// if game is not running, can not archive game
		if (!chessTable.isGame()) {
			JOptionPane.showMessageDialog(null, "Game not started");
			return;
		}
		
		db.archiveGame();
		JOptionPane.showMessageDialog(null, "Archive game success!");
	}

}
