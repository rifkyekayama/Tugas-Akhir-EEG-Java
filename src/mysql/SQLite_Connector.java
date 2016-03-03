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
		createTableDataLatih();
		createTableKoefisienBobot();
		createTableWavelet();
	}
	
	public void createTableDataLatih(){
		try {
			connect = getKoneksi();
			stmt = connect.createStatement();
			DatabaseMetaData dbm = connect.getMetaData();
			
			ResultSet tabelDataLatih = dbm.getTables(null, null, "data_latih", null);
			if(tabelDataLatih.next() == false){
				sql = "CREATE TABLE data_latih "+
					  "(id INTEGER PRIMARY 	KEY    	AUTOINCREMENT," +
					  "data_eeg			TEXT," +
					  "kelas			INTEGER," +
					  "naracoba			INTEGER," +
					  "sampling_rate	INTEGER," +
					  "kanal			TEXT)";
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createTableWavelet(){
		try {
			connect = getKoneksi();
			stmt = connect.createStatement();
			DatabaseMetaData dbm = connect.getMetaData();
			
			ResultSet tabelWavelet = dbm.getTables(null, null, "wavelet", null);
			if(tabelWavelet.next() == false){
				sql = "CREATE TABLE wavelet "+
					  "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
					  "gel_alfa 		TEXT," +
					  "gel_beta			TEXT," +
					  "gel_teta			TEXT)";
				stmt.executeQuery(sql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createTableKoefisienBobot(){
		try {
			connect = getKoneksi();
			stmt = connect.createStatement();
			DatabaseMetaData dbm = connect.getMetaData();
			
			ResultSet tabelKoefisienBobot = dbm.getTables(null, null, "koefisien_bobot", null);
			if(tabelKoefisienBobot.next() == false){
				sql = "CREATE TABLE koefisien_bobot "+
						  "(id INTEGER PRIMARY 	KEY	AUTOINCREMENT," +
						  "w1			TEXT," +
						  "w2			TEXT)";
				stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
