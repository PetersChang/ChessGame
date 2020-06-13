package listener;

import java.awt.Graphics;
import java.awt.event.ActionEvent;

import chess.ChessTable;
import config.Config;
import ui.ChessTableUI;

/**
 * Handle the restart event
 */
public class RestartListener extends ChessListener {

	public RestartListener(ChessTable chessTable, ChessTableUI chessTableUI, Graphics graphics) {
		super(chessTable, chessTableUI, graphics);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// initial the chess valie
		for (int m = 0; m < Config.ROWS_NUM; m++) {
			for (int n = 0; n < Config.COLUMNS_NUM; n++) {
				chessTable.chesses[m][n] = 0;
			}
		}
		
		// repaint the chess UI
		chessTableUI.paint(this.graphics);
		chessTable.count = 0;
		chessTable.setGame(true);
		chessTable.setName("Restart");
	}

}
