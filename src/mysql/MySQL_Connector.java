package mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL_Connector {
	private Connection connect;
	private String driverName = "com.mysql.jdbc.Driver"; // Driver Untuk Koneksi Ke MySQL  
	private String jdbc = "jdbc:mysql://";
	private String host = "localhost:"; // Bisa Menggunakan IP Anda, Cnth : 192.168.100.100  
	private String port = "3306/"; // Port ini port MySQL  
	private String database = "eeg"; // Ini Database yang akan digunakan  
	private String url = jdbc + host + port + database;  
	private String username = "java_eeg"; // username default mysql  
	private String password = "java_eeg";
	public Connection getKoneksi() throws SQLException {
		if (connect == null){
			try{
				Class.forName(driverName);
				System.out.println("Class Driver Ditemukan");
				try{
					connect = DriverManager.getConnection(url, username, password);
					System.out.println("Koneksi Database Sukses");
					}catch (SQLException se){
						System.out.println("Koneksi Database Gagal : " + se);
						System.exit(0);
					}
				}catch (ClassNotFoundException cnfe) {
					System.out.println("Class Driver Tidak Ditemukan, Terjadi Kesalahan Pada : " + cnfe);
					System.exit(0);
				}
		}
		return connect;
	}
}
