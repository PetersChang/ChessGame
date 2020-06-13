package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import chess.ChessTable;
import config.Config;

/**
 * Connect the database, store the chess information into the database, 
 * and read out the chess information from the database
 */
public class Database {
	
	/**
	 * Database connection
	 */
	private Connection connection;
	
	/**
	 * Chess table
	 */
	private ChessTable chessTable;
	
	/**
	 * Constructor  
	 * @param chessTable chess table
	 */
	public Database(ChessTable chessTable) {

		this.chessTable = chessTable;

		// initial dataset connection
		try {
			connection = DriverManager.getConnection("jdbc:derby:game;create=true;user=test;password=test");
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("Unable to initialize database");
		}
		
		boolean existTable = false;
		
		// create table and initial table
		try {
			Statement statement = connection.createStatement();
			// create table
			String sql = "create table chesses(x int, y int, value int)";
			statement.execute(sql);
			// close statement
			statement.close();
		} catch (SQLException e) {
			System.out.println("Table chesses already exists");
			existTable = true;
		}
		
		if(!existTable) {
			initialChess();
		}
	}
	
	/**
	 * Get the connection
	 * @return database connection
	 */
	public Connection getConnection() {
		return connection;
	}
	
	/**
	 * Initial chess data in database
	 */
	private void initialChess() {
		try {
			Statement statement = connection.createStatement();
			// store the chess data to database
			for (int i = 0; i < Config.ROWS_NUM; i++) {
				for (int j = 0; j < Config.COLUMNS_NUM; j++) {
					String sql = "insert into chesses(x, y, value) values (" + i + ", " + j + ", " + chessTable.chesses[i][j] + ")";
					statement.execute(sql);
				}
			}
			connection.commit();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Unable to create database");
		}
	}
	
	/**
	 * Archive game
	 */
	public void archiveGame() {
		try {
			Statement statement = connection.createStatement();
			// // store the chess data to database
			for (int i = 0; i < Config.ROWS_NUM; i++) {
				for (int j = 0; j < Config.COLUMNS_NUM; j++) {
					int value = chessTable.chesses[i][j];
					String Sql = "update chesses set value=" + value + " where x=" + i + " and y=" + j;
					statement.execute(Sql);
				}
			}
			connection.commit();
			statement.close();
		} catch (SQLException e) {
			System.out.println("Unable to create database");
		}
	}
	
	/**
	 * Load the chess from dataset
	 */
	public void loadGame() {
        try {
        	Statement statement = connection.createStatement();
            // load game
            String sql = "select * from chesses";
            ResultSet rs = statement.executeQuery(sql);
            // traversal assignment array
			while (rs.next()) {
			    int i= rs.getInt(1);
			    int j= rs.getInt(2);
			    int value= rs.getInt(3);
			    chessTable.chesses[i][j] = value;
			}
			statement.close();
	        connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
