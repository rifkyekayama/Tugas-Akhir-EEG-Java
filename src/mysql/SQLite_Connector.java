package mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class SQLite_Connector {
	
	private Connection connect;
	private String driverName = "org.sqlite.JDBC";
	private String dbName = "eeg.db";
	private String sql = null;
	private Statement stmt = null;
	
	public SQLite_Connector() throws SQLException {
		connect = getKoneksi();
		createDatabase();
	}
	
	public Connection getKoneksi() throws SQLException {
		if(connect == null){
			try {
				Class.forName(driverName);
				try {
					connect = DriverManager.getConnection("jdbc:sqlite:"+dbName);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Class Driver Tidak Ditemukan, Terjadi Kesalahan Pada : " + e, "Peringatan", JOptionPane.WARNING_MESSAGE);
				System.exit(0);
			}
		}
		return connect;
	}
	
	public void createDatabase(){
		try {
			stmt = connect.createStatement();
			DatabaseMetaData dbm = connect.getMetaData();
			
			ResultSet tabelDataLatih = dbm.getTables(null, null, "data_latih", null);
			if(tabelDataLatih.next() == false){
				sql = "CREATE TABLE data_latih "+
					  "(id INTEGER PRIMARY 	KEY    	AUTOINCREMENT," +
					  "data_eeg			LONGTEXT," +
					  "kelas			INTEGER," +
					  "naracoba			INTEGER," +
					  "sampling_rate	INTEGER," +
					  "kanal			VARCHAR(5))";
				stmt.executeUpdate(sql);
			}

			ResultSet tabelKoefisienBobot = dbm.getTables(null, null, "koefisien_bobot", null);
			if(tabelKoefisienBobot.next() == false){
				sql = "CREATE TABLE koefisien_bobot "+
						  "(id INTEGER PRIMARY 	KEY	AUTOINCREMENT," +
						  "w1			LONGTEXT," +
						  "w2			LONGTEXT)";
				stmt.executeUpdate(sql);
			}
			stmt.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.err.println(e1.getClass().getName() + ": " + e1.getMessage());
		}
	}
}
