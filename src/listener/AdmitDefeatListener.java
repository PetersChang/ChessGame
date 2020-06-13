package listener;

import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import chess.ChessTable;
import ui.ChessTableUI;

/**
 * Handle the admit defeat event 
 */
public class AdmitDefeatListener extends ChessListener {
	public AdmitDefeatListener(ChessTable chessTable, ChessTableUI chessTableUI, Graphics graphics) {
		super(chessTable, chessTableUI, graphics);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// if game is not run, then can not admit
		if (!chessTable.isGame()) {
			JOptionPane.showMessageDialog(null, "Game not started");
			return;
		}
		
		// judge who should win
		if(chessTable.count == 1){
            JOptionPane.showMessageDialog(null, "White chess admit defeat");
        }else if(chessTable.count == 0){
            JOptionPane.showMessageDialog(null, "Black chess admit defeat");
        }
		chessTable.setGame(false);
	}
}
