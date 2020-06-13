package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import config.Config;

/**
 * Chessboard object, including some data in the chessboard, 
 * such as the position of each piece in the chessboard, 
 * the weight of the piece, the list of pieces, etc. 
 * It also includes the method of chessboard update and the method of judging the outcome.
 *
 */
public class ChessTable extends MouseAdapter {
	
	/**
	 * Constructor
	 * 
	 * @param g UI graphics
	 */
	public ChessTable(Graphics g) {
		this.g = g;
	}
	
	/**
	 * Chess List, order by time sequence
	 */
	public ArrayList<Point> list = new ArrayList<Point>();
	
	/**
	 * Chess weight, used to judge the chess list score
	 */
	public HashMap<String, Integer> hm = new HashMap<String, Integer>();
	
	/**
	 * UI graphics
	 */
	public Graphics g;
	
	/**
	 * User clicked button name
	 */
	public String name = null;
	
	/**
	 * Game status, start or end
	 */
	public boolean game = false;
	
	/**
	 * some status
	 */
	public int count = 0, r = 0, l = 0, max = 0;

	/**
	 * Chess array, every piece position
	 */
	public int[][] chesses = new int[Config.ROWS_NUM][Config.COLUMNS_NUM];
	
	/**
	 * Chess score, every piece position score
	 */
	public int[][] chessValue = new int[Config.ROWS_NUM][Config.COLUMNS_NUM];

	/**
	 * initial weight table
	 */
	public void hash() {
		hm.put("1", 20);
		hm.put("11", 200);
		hm.put("111", 2000);
		hm.put("1111", 3000);
		hm.put("1-1", 10);
		hm.put("11-1", 100);
		hm.put("111-1", 1000);
		hm.put("1111-1", 2000);
	}
	
	/**
	 * Get the operate name
	 * 
	 * @return the operate name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the operate name 
	 * @param name the operate name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Check the game status
	 * @return true or false
	 */
	public boolean isGame() {
		return game;
	}

	/**
	 * Set game status
	 * @param game true or false
	 */
	public void setGame(boolean game) {
		this.game = game;
	}
	
	/**
	 * Triggered when mouse button is released
	 */
	public void mouseReleased(MouseEvent e) {
		
		// if game end, return
		if (!game) return;
		
		if ("Start".equals(name) || "Restart".equals(name) || "Regret Game".equals(name) || "Archive Game".equals(name)
				|| "Load Game".equals(name)) {
			// Get the location of the cursor when the mouse event occurs
			int x1_num = e.getX();
			int y1_num = e.getY();
			// traverse chessboard by line
			for (int j = 0; j < Config.ROWS_NUM; j++) {
				for (int i = 0; i < Config.COLUMNS_NUM; i++) {
					// get the coordinates of the intersection
					int x_num = Config.X_0 + Config.SIZE_NUM * i;// Abscissa
					int y_num = Config.Y_0 + Config.SIZE_NUM * j;// Ordinate
					// get the line of intersection
					// the error with the center is size / 3
					if (x1_num > x_num - Config.SIZE_NUM / 2 && x1_num < x_num + Config.SIZE_NUM / 2
							&& y1_num > y_num - Config.SIZE_NUM / 2 && y1_num < y_num + Config.SIZE_NUM / 2 && game) {
						if (chesses[i][j] == 0) {
							// people play black chess
							if (count == 0) {
								chesses[i][j] = 1;// black chess is 1
								Point point = new Point();
								point.setLocation(i, j);
								list.add(point);
								g.setColor(Color.black);
								count++;
							}
							// draw pieces
							g.fillOval(x_num - Config.CHESS_SIZE_NUM / 2, y_num - Config.CHESS_SIZE_NUM / 2,
									Config.CHESS_SIZE_NUM, Config.CHESS_SIZE_NUM);
							this.winGame(i, j);
						}
					}
				}
			}
			
			// machine drawing white chess pieces
			// find i j where the value is the largest
			if (count == 1) {
				if (game) {
					this.count();
					for (int j = 0; j < Config.ROWS_NUM; j++) {
						for (int i = 0; i < Config.COLUMNS_NUM; i++) {
							if (chessValue[i][j] > max) {
								max = chessValue[i][j];
								r = i;
								l = j;
							}
						}
					}
					// empty
					for (int i = 0; i < Config.ROWS_NUM; i++) {
						for (int j = 0; j < Config.COLUMNS_NUM; j++) {
							chessValue[i][j] = 0;
						}
					}
					max = 0;
					if (count == 1) {
						g.setColor(Color.white);
						Point point = new Point();
						point.setLocation(r, l);
						list.add(point);
						chesses[r][l] = -1;// white chess is 1
						count--;
					}
					
					g.fillOval(Config.X_0 + Config.SIZE_NUM * r - Config.CHESS_SIZE_NUM / 2,
							Config.Y_0 + Config.SIZE_NUM * l - Config.CHESS_SIZE_NUM / 2, Config.CHESS_SIZE_NUM,
							Config.CHESS_SIZE_NUM);
					this.winGame(r, l);
				}
			} else {
				return;
			}
		}

	}

	/**
	 * When the user next chessboard, calculate the score of each chessboard position 
	 * on the current chessboard, and the position with the highest score should be where the computer should be.
	 */
	public void count() {
		for (int i = 0; i < chesses.length; i++) {
			for (int j = 0; j < chesses[i].length; j++) {
				// judge whether the current is empty
				if (chesses[i][j] == 0) {
					// search the chess situation of the space in eight directions, and calculate the value value
					// define two variables to save the game and the color
					String code = "";
					int color_num = 0;
					// downward direction
					for (int k = i + 1; k < Config.ROWS_NUM; k++) {
						// stop matching if next is empty
						if (chesses[k][j] == 0) {
							break;
						} else {
							// the first chess piece below
							if (color_num == 0) {
								color_num = chesses[k][j];// save color
								code += chesses[k][j];// save chess
							} else if (chesses[k][j] == color_num) {// bottom 2 / 3 / 4...same color pieces
								code += chesses[k][j];// save chess
							} else {// bottom 2 / 3 / 4...different colors
								code += chesses[k][j];// save chess
								break;
							}
						}
					}
					// take the weight of hm according to the code
					Integer value = hm.get(code);
					if (value != null) {
						chessValue[i][j] += value;// value
					}
					code = "";
					color_num = 0;
					// upward direction
					for (int k = i - 1; k >= 0; k--) {
						if (chesses[k][j] == 0) {// stop matching if next is empty
							break;
						} else {
							if (color_num == 0) {// the first chess piece below
								color_num = chesses[k][j];// save color
								code += chesses[k][j];// Save chess
							} else if (chesses[k][j] == color_num) {// bottom 2 / 3 / 4...Same color pieces
								code += chesses[k][j];// save chess
							} else {// bottom 2 / 3 / 4...different colors
								code += chesses[k][j]; // save chess
								break;
							}
						}
					}
					// take the weight of hm according to the code
					Integer value1_num = hm.get(code);
					if (value1_num != null) {
						chessValue[i][j] += value1_num;
					}
					code = "";
					color_num = 0;
					// right direction
					for (int k = j + 1; k < Config.COLUMNS_NUM; k++) {
						if (chesses[i][k] == 0) {// stop matching if next is empty
							break;
						} else {
							if (color_num == 0) {// the first chess piece below
								color_num = chesses[i][k];// Save color
								code += chesses[i][k];// Save chess
							} else if (chesses[i][k] == color_num) {// Bottom 2 / 3 / 4...same color pieces
								code += chesses[i][k];// save chess
							} else {// bottom 2 / 3 / 4...different colors
								code += chesses[i][k];// save chess
								break;
							}
						}
					}
					// take the weight of hm according to the code
					Integer value2_num = hm.get(code);
					if (value2_num != null) {
						chessValue[i][j] += value2_num;
					}
					code = "";
					color_num = 0;
					// left direction
					for (int k = j - 1; k >= 0; k--) {
						if (chesses[i][k] == 0) {// stop matching if next is empty
							break;
						} else {
							if (color_num == 0) {// the first chess piece below
								color_num = chesses[i][k];// save color
								code += chesses[i][k];// save chess
							} else if (chesses[i][k] == color_num) {// Bottom 2 / 3 / 4...Same color pieces
								code += chesses[i][k];// save chess
							} else {// Bottom 2 / 3 / 4...different colors
								code += chesses[i][k];// save chess
								break;
							}
						}
					}
					// take the weight of hm according to the code
					Integer value3_num = hm.get(code);
					if (value3_num != null) {
						chessValue[i][j] += value3_num;
					}
					code = "";
					color_num = 0;
					// left upper direction
					for (int k = j - 1, l = i - 1; k >= 0 && l >= 0; k--, l--) {
						if (chesses[l][k] == 0) {// stop matching if next is empty
							break;
						} else {
							if (color_num == 0) {// the first chess piece below
								color_num = chesses[l][k];// save color
								code += chesses[l][k];// save chess
							} else if (chesses[l][k] == color_num) {// Bottom 2 / 3 / 4...Same color pieces
								code += chesses[l][k];// save chess
							} else {// Bottom 2 / 3 / 4...different colors
								code += chesses[l][k];// save chess
								break;
							}
						}
					}
					// Take the weight of hm according to the code
					Integer value4_num = hm.get(code);
					if (value4_num != null) {
						chessValue[i][j] += value4_num;
					}
					code = "";
					color_num = 0;
					// Left lower direction
					for (int k = j - 1, l = i + 1; k >= 0 && l < Config.ROWS_NUM; k--, l++) {
						if (chesses[l][k] == 0) {// Stop matching if next is empty
							break;
						} else {
							if (color_num == 0) {// The first chess piece below
								color_num = chesses[l][k];// Save color
								code += chesses[l][k];// Save chess
							} else if (chesses[l][k] == color_num) {
								code += chesses[l][k];// Save chess
							} else {// Bottom 2 / 3 / 4...different colors
								code += chesses[l][k];// Save chess
								break;
							}
						}
					}
					// Take the weight of hm according to the code
					Integer value5_num = hm.get(code);
					if (value5_num != null) {
						chessValue[i][j] += value5_num;
					}
					code = "";
					color_num = 0;
					// Lower right direction
					for (int k = j + 1, l = i + 1; k < Config.COLUMNS_NUM && l < Config.ROWS_NUM; k++, l++) {
						if (chesses[l][k] == 0) {// Stop matching if next is empty
							break;
						} else {
							if (color_num == 0) {// The first chess piece below
								color_num = chesses[l][k];// Save color
								code += chesses[l][k];// Save chess
							} else if (chesses[l][k] == color_num) {
								code += chesses[l][k];// Save chess
							} else {// Bottom 2 / 3 / 4...different colors
								code += chesses[l][k];// Save chess
								break;
							}
						}
					}
					// Take the weight of hm according to the code
					Integer value6_num = hm.get(code);
					if (value6_num != null) {
						chessValue[i][j] += value6_num;
					}
					code = "";
					color_num = 0;
					// Upper right direction
					for (int k = j + 1, l = i - 1; k < Config.COLUMNS_NUM && l >= 0; k++, l--) {
						if (chesses[l][k] == 0) {// Stop matching if next is empty
							break;
						} else {
							if (color_num == 0) {// The first chess piece below
								color_num = chesses[l][k];// Save color
								code += chesses[l][k];// Save chess
							} else if (chesses[l][k] == color_num) {
								code += chesses[l][k];// Save chess
							} else {// Bottom 2 / 3 / 4...different colors
								code += chesses[l][k];// Save chess
								break;
							}
						}
					}
					// Take the weight of hm according to the code
					Integer value7_num = hm.get(code);
					if (value7_num != null) {
						chessValue[i][j] += value7_num;
					}
					code = "";
					color_num = 0;
				}
			}
		}
	}
	
	/**
	 * Check the game status
	 * @param i position i
	 * @param j position j
	 */
	private void winGame(int i, int j) {
		if (this.check_Row(i, j) >= 5 || this.check_Column(i, j) >= 5 || this.check_Lower_Right(i, j) >= 5
				|| this.check_Left_Lower(i, j) >= 5) {
			if (count == 1) {
				game = false;
				JOptionPane.showMessageDialog(null, "Sorry, the black side won");
			} else if (count == 0) {
				game = false;
				JOptionPane.showMessageDialog(null, "Sorry, the white side won");
			}
		}
	}

	/**
	 * Oblique left down check
	 * 
	 * @param x position x
	 * @param y position y
	 * @return the count
	 */
	private int check_Left_Lower(int x, int y) {
		int count = 0;
		for (int i = x - 1, j = y + 1; i >= 0 && j < Config.ROWS_NUM; i--, j++) {// Longitudinal down detection
			if (chesses[i][j] == chesses[x][y]) {
				count++;
			} else {
				break;
			}
		}
		for (int i = x, j = y; i < Config.COLUMNS_NUM && j >= 0; i++, j--) {// Vertical up detection
			if (chesses[i][j] == chesses[x][y]) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	/**
	 * Oblique downward inspection
	 * @param x position x
	 * @param y position y
	 * @return the count
	 */
	private int check_Lower_Right(int x, int y) {
		int count = 0;
		for (int i = x + 1, j = y + 1; i < Config.COLUMNS_NUM && j < Config.ROWS_NUM; i++, j++) {// Longitudinal down
																									// detection
			if (chesses[i][j] == chesses[x][y]) {
				count++;
			} else {
				break;
			}
		}
		for (int i = x, j = y; i >= 0 && j >= 0; i--, j--) {// Vertical up detection
			if (chesses[i][j] == chesses[x][y]) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	/**
	 * Longitudinal detection
	 * @param x position x
	 * @param y position y
	 * @return the count
	 */
	private int check_Column(int x, int y) {
		int count = 0;
		for (int i = y + 1; i < Config.ROWS_NUM; i++) {// Longitudinal down detection
			if (chesses[x][i] == chesses[x][y]) {
				count++;
			} else {
				break;
			}
		}
		for (int i = y; i >= 0; i--) {// Vertical up detection
			if (chesses[x][i] == chesses[x][y]) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}

	/**
	 * Lateral detection
	 * @param x position x
	 * @param y position y
	 * @return the count
	 */
	private int check_Row(int x, int y) {
		int count = 0;
		for (int i = x + 1; i < Config.COLUMNS_NUM; i++) {
			if (chesses[x][y] == chesses[i][y]) {
				count++;
			} else {
				break;
			}
		}
		for (int i = x; i >= 0; i--) {
			if (chesses[x][y] == chesses[i][y]) {
				count++;
			} else {
				break;
			}
		}
		return count;
	}
}
