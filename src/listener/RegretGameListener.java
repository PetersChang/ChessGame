package listener;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import chess.ChessTable;
import ui.ChessTableUI;

/**
 * Handle regret game event
 */
public class RegretGameListener extends ChessListener {
	
	public RegretGameListener(ChessTable chessTable, ChessTableUI chessTableUI, Graphics graphics) {
		super(chessTable, chessTableUI, graphics);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// if the game is not running, cannot regert
		if (!chessTable.isGame()) {
			JOptionPane.showMessageDialog(null, "Game not started");
			return;
		}
		
		// if chess list has pieces, remove and repaint
		if (chessTable.list.size() > 0) {
			Point p = new Point();
			p = chessTable.list.remove(chessTable.list.size() - 1);
			chessTable.chesses[(int) p.getX()][(int) p.getY()] = 0;
			chessTableUI.paint(graphics);
			if (chessTable.count == 1) {
				chessTable.count--;
			} else if (chessTable.count == 0) {
				chessTable.count++;
			}
		} else {
			JOptionPane.showMessageDialog(null, "No chess pieces at present");
		}
	}
}
