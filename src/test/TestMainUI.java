package test;

import java.awt.Graphics;

import chess.ChessTable;
import ui.ChessTableUI;

/**
 * Test the Main function
 */
public class TestMainUI {
	// Main
	public static void main(String[] args) {
		try {
			// create UI
			ChessTableUI chessTableUI = new ChessTableUI();
			
			// Get the graphics object
			Graphics graphics = chessTableUI.getGraphics();
			
			// create chess table
			ChessTable chessTable = new ChessTable(graphics);
			chessTableUI.setChessTable(chessTable);
			
			// show window
			chessTableUI.start();
			
			// sleep three seconds
			Thread.sleep(3000);
			
			// clost the window
			chessTableUI.close();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
