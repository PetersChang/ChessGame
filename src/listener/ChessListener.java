package listener;

import java.awt.Graphics;
import java.awt.event.ActionListener;

import chess.ChessTable;
import ui.ChessTableUI;

/**
 * Chess listener handler
 *
 */
public abstract class ChessListener implements ActionListener {
	protected ChessTable chessTable;
	protected ChessTableUI chessTableUI;
	protected Graphics graphics;
	
	/**
	 * Constructor
	 * @param chessTable chess table
	 * @param chessTableUI chess UI
	 * @param graphics graphics
	 */
	public ChessListener(ChessTable chessTable, ChessTableUI chessTableUI, Graphics graphics) {
		this.chessTable = chessTable;
		this.chessTableUI = chessTableUI;
		this.graphics = graphics;
	}
}
