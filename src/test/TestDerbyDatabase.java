package test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import chess.ChessTable;
import db.Database;

/**
 * Test the berby database
 *
 */
public class TestDerbyDatabase {
	
	// db
	private Database db;
	
	// chess table
	private ChessTable chessTable;
	
	/**
	 * Initial object and database
	 */
	public void init() {
		chessTable = new ChessTable(null);
		db = new Database(chessTable);
	}
	
	/**
	 * Update the data in database
	 */
	public void update() {
		try {
			Connection connection = db.getConnection();
			Statement statement = connection.createStatement();
			String sql = "update chesses set value = 0 where x = 0 and y = 0";
			statement.execute(sql);
			connection.commit();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Query the all data from database
	 */
	public void query() {
		try {
			Connection connection = db.getConnection();
			Statement statement = connection.createStatement();
			
			String sql = "select * from chesses";
            ResultSet rs = statement.executeQuery(sql);
            //Traversal assignment array
			while (rs.next()) {
			    int i= rs.getInt(1);
			    int j= rs.getInt(2);
			    int value= rs.getInt(3);
			    
			    System.out.println(i + " " + j + " : " + value);
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Main
	public static void main(String[] args) {
		TestDerbyDatabase testDerbyDataset = new TestDerbyDatabase();
		testDerbyDataset.init();
		testDerbyDataset.update();
		testDerbyDataset.query();
	}
	
}
