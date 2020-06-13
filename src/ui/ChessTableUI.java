package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import chess.ChessTable;
import config.Config;
import listener.AdmitDefeatListener;
import listener.ArchiveGameListener;
import listener.LoadGameListener;
import listener.RegretGameListener;
import listener.RestartListener;
import listener.StartListener;

/**
 *Chessboard interface, responsible for drawing and updating chessboard and chessboard
 */
public class ChessTableUI extends JPanel {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Chess table object
	 */
	private ChessTable chessTable;

	/**
	 * Main frame
	 */
	private JFrame jFrame;

	// listeners
	private RestartListener restartListener;

	private StartListener startListener;

	private ActionListener admitDefeatListener;

	private ActionListener regretGameListener;

	private ActionListener archivGameListener;

	private ActionListener loadGameListener;
	
	// buttons
	private JButton startButton = new JButton("Start");
	private JButton restartButton = new JButton("Restart");
	private JButton admitDefeatButton = new JButton("Admit Defeat");
	private JButton regretGameButton = new JButton("Regret Game");
	private JButton archiveGameButton = new JButton("Archive Game");
	private JButton loadGameButton = new JButton("Load Game");
	
	// buttons array
	private JButton[] buttons = {restartButton, admitDefeatButton, regretGameButton, startButton, archiveGameButton, loadGameButton};
	
	/**
	 * Constructor
	 */
	public ChessTableUI() {
		this(null);
	}

	/**
	 * Set the chess table to this UI
	 */
	public void setChessTable(ChessTable chessTable) {
		this.chessTable = chessTable;
	}
	
	/**
	 * Close the main windos
	 */
	public void close() {
		jFrame.dispose();
	}
	
	/**
	 * Constructor
	 */
	public ChessTableUI(ChessTable chessTable) {
		this.chessTable = chessTable;
		jFrame = new JFrame();

		// set title
		jFrame.setTitle("Chess Game");

		// JFrame width and height
		jFrame.setSize(900, 670);

		// EXIT_ON_CLOSE,Close the application directly
		jFrame.setDefaultCloseOperation(3);

		// chessboard canvas
		this.setBackground(new Color(213, 176, 146));
		jFrame.add(this, BorderLayout.CENTER);

		jFrame.setVisible(true);
		
		// Operation button
		JPanel jPanel = new JPanel();
		jPanel.setBackground(new Color(100, 100, 100));
		jPanel.setPreferredSize(new Dimension(250, 300));
		
		// set buttons and added to jpanel
		for (JButton jButton: buttons) {
			jButton.setFont(new Font("Song style", Font.BOLD, 15));
			jButton.setPreferredSize(new Dimension(200, 98));
			jPanel.add(jButton);
		}
		
		// add this panel to frame
		jFrame.add(jPanel, BorderLayout.EAST);
	}

	/**
	 * Start game, show window
	 */
	public void start() {
		// set mouse listener
		this.addMouseListener(chessTable);
		
		// set buttons action listener
		startButton.addActionListener(startListener);
		restartButton.addActionListener(restartListener);
		admitDefeatButton.addActionListener(admitDefeatListener);
		regretGameButton.addActionListener(regretGameListener);
		archiveGameButton.addActionListener(archivGameListener);
		loadGameButton.addActionListener(loadGameListener);
	}
	
	/**
	 * Draw/Redraw form
	 */
	public void paint(Graphics g) {
		super.paint(g);
		// Redraw form and draw checkerboard at the same time
		drawChessTable(g);

		// Redraw pieces
		for (int j = 0; j < Config.ROWS_NUM; j++) {
			for (int i = 0; i < Config.COLUMNS_NUM; i++) {
				if (chessTable.chesses[i][j] == 1) {
					g.setColor(Color.BLACK);
					int x = Config.X_0 + Config.SIZE_NUM * i;// Abscissa
					int y = Config.Y_0 + Config.SIZE_NUM * (j);// Ordinate
					g.fillOval(x - Config.CHESS_SIZE_NUM / 2, y - Config.CHESS_SIZE_NUM / 2, Config.CHESS_SIZE_NUM,
							Config.CHESS_SIZE_NUM);
				} else if (chessTable.chesses[i][j] == -1) {
					g.setColor(Color.white);
					int x = Config.X_0 + Config.SIZE_NUM * i;// Abscissa
					int y = Config.Y_0 + Config.SIZE_NUM * (j);// Ordinate
					g.fillOval(x - Config.CHESS_SIZE_NUM / 2, y - Config.CHESS_SIZE_NUM / 2, Config.CHESS_SIZE_NUM,
							Config.CHESS_SIZE_NUM);
				}
			}
		}
	}
	
	/**
	 * Draw/Redraw form
	 */
	public void paint(Graphics g, int[][] chesses) {
		super.paint(g);
		// Redraw form and draw checkerboard at the same time
		drawChessTable(g);
		// Redraw pieces
		for (int j = 0; j < Config.ROWS_NUM; j++) {
			for (int i = 0; i < Config.COLUMNS_NUM; i++) {
				if (chesses[i][j] == 1) {
					g.setColor(Color.BLACK);
					int x = Config.X_0 + Config.SIZE_NUM * i;// Abscissa
					int y = Config.Y_0 + Config.SIZE_NUM * (j);// Ordinate
					g.fillOval(x - Config.CHESS_SIZE_NUM / 2, y - Config.CHESS_SIZE_NUM / 2, Config.CHESS_SIZE_NUM,
							Config.CHESS_SIZE_NUM);
				} else if (chesses[i][j] == -1) {
					g.setColor(Color.white);
					int x = Config.X_0 + Config.SIZE_NUM * i;// Abscissa
					int y = Config.Y_0 + Config.SIZE_NUM * (j);// Ordinate
					g.fillOval(x - Config.CHESS_SIZE_NUM / 2, y - Config.CHESS_SIZE_NUM / 2, Config.CHESS_SIZE_NUM,
							Config.CHESS_SIZE_NUM);
				}
			}
		}
	}

	/**
	 * Graw chess table
	 */
	private void drawChessTable(Graphics g) {
		// Draw a checkerboard line
		for (int i = 0; i < Config.ROWS_NUM; i++) {
			g.drawLine(Config.X_0, Config.Y_0 + i * Config.SIZE_NUM,
					Config.X_0 + (Config.COLUMNS_NUM - 1) * Config.SIZE_NUM, Config.Y_0 + i * Config.SIZE_NUM);
		}
		// Draw the vertical line of chessboard
		for (int j = 0; j < Config.COLUMNS_NUM; j++) {
			g.drawLine(Config.X_0 + j * Config.SIZE_NUM, Config.Y_0, Config.X_0 + j * Config.SIZE_NUM,
					Config.Y_0 + (Config.ROWS_NUM - 1) * Config.SIZE_NUM);
		}
	}
	
	/**
	 * Set action listener
	 */
	public void setRestartListener(RestartListener restartListener) {
		this.restartListener = restartListener;
	}
	
	/**
	 * Set action listener
	 */
	public void setStartListener(StartListener startListener) {
		this.startListener = startListener;
	}

	/**
	 * Set action listener
	 */
	public void setAdmitDefeatListener(AdmitDefeatListener admitDefeatListener) {
		this.admitDefeatListener = admitDefeatListener;
	}

	/**
	 * Set action listener
	 */
	public void setRegretGameListener(RegretGameListener regretGameListener) {
		this.regretGameListener = regretGameListener;
	}

	/**
	 * Set action listener
	 */
	public void setLoadGameListener(LoadGameListener loadGameListener) {
		this.loadGameListener = loadGameListener;
	}

	/**
	 * Set action listener
	 */
	public void setArchiveGameListener(ArchiveGameListener archiveGameListener) {
		this.archivGameListener = archiveGameListener;
	}
	
	/**
	 * Get the button
	 * @return button object
	 */
	public JButton getStartButton() {
		return startButton;
	}

	/**
	 * Get the button
	 * @return button object
	 */
	public JButton getRestartButton() {
		return restartButton;
	}

	/**
	 * Get the button
	 * @return button object
	 */
	public JButton getAdmitDefeatButton() {
		return admitDefeatButton;
	}

	/**
	 * Get the button
	 * @return button object
	 */
	public JButton getRegretGameButton() {
		return regretGameButton;
	}

	/**
	 * Get the button
	 * @return button object
	 */
	public JButton getArchiveGameButton() {
		return archiveGameButton;
	}

	/**
	 * Get the button
	 * @return button object
	 */
	public JButton getLoadGameButton() {
		return loadGameButton;
	}
}
