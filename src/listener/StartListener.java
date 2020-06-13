package listener;

import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import chess.ChessTable;
import config.Config;
import ui.ChessTableUI;

/**
 *  Handle the start event
 */
public class StartListener extends ChessListener {

	public StartListener(ChessTable chessTable, ChessTableUI chessTableUI, Graphics graphics) {
		super(chessTable, chessTableUI, graphics);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// check the game whether started
		if (chessTable.isGame()) {
			JOptionPane.showMessageDialog(null, "You've started!");
			return;
		}
		
		// initial the chess value
		for (int m = 0; m < Config.ROWS_NUM; m++) {
			for (int n = 0; n < Config.COLUMNS_NUM; n++) {
				chessTable.chesses[m][n] = 0;
			}
		}
		
		// repaint the UI
		chessTableUI.paint(this.graphics);
		
		chessTable.hash();
		chessTable.count = 0;
		chessTable.setGame(true);
		chessTable.setName("Start");
	}
}
