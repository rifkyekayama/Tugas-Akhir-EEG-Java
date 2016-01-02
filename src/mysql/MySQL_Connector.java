package mysql;

import java.awt.GridLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.server.ServerNotActiveException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mysql.fabric.xmlrpc.base.Array;

public class MySQL_Connector {
	private Connection connect;
	private String driverName = "com.mysql.jdbc.Driver"; // Driver Untuk Koneksi Ke MySQL  
	private String jdbc = "jdbc:mysql://";
	private String host; // Bisa Menggunakan IP Anda, Cnth : 192.168.100.100  
	private String port; // Port ini port MySQL  
	private String database; // Ini Database yang akan digunakan  
	//private String url = jdbc + host + port + database;  
	private String username; // username default mysql  
	private String password;
	
	private JTextField txtHost, txtUsername, txtDatabase, txtPort;
	private JPasswordField txtPassword;
	
	public Connection getKoneksi() throws SQLException {
		loadParams();
		String url = this.jdbc + getHost() + ":" + getPort()+ "/" + getDatabase();
		if (connect == null){
			try{
				Class.forName(driverName);
				try{
					connect = DriverManager.getConnection(url, getUsername(), getPassword());
					}catch (SQLException se){
						JOptionPane.showMessageDialog(null, "Koneksi Database Gagal : " + se, "Peringatan", JOptionPane.WARNING_MESSAGE);
						setConfigDatabase();
					}
				}catch (ClassNotFoundException cnfe) {
					JOptionPane.showMessageDialog(null, "Class Driver Tidak Ditemukan, Terjadi Kesalahan Pada : " + cnfe, "Peringatan", JOptionPane.WARNING_MESSAGE);
					System.exit(0);
				}
		}
		return connect;
	}
	
	public void loadParams() {
		Properties props = new Properties();
	    InputStream is = null;

	    try {
	        File f = new File("server.properties");
	        is = new FileInputStream( f );
	    }
	    catch ( Exception e ) { is = null; }
	 
	    try {
	        if ( is == null ) {
	            is = getClass().getResourceAsStream("server.properties");
	        }
	        props.load( is );
	    }
	    catch ( Exception e ) { }
	 
	    this.host = props.getProperty("ServerAddress", "localhost");
	    this.username = props.getProperty("ServerUsername", "java_eeg");
	    this.password = props.getProperty("ServerPassword", "java_eeg");
	    this.port = props.getProperty("ServerPort", "3306");
	    this.database = props.getProperty("ServerDatabase", "eeg");
	}
	
	public void saveParamChanges(String host, String username, String password, String port, String database) {
	    try {
	        Properties props = new Properties();
	        props.setProperty("ServerAddress", host);
	        props.setProperty("ServerUsername", username);
	        props.setProperty("ServerPassword", password);
	        props.setProperty("ServerPort", port);
	        props.setProperty("ServerDatabase", database);
	        
	        File f = new File("server.properties");
	        OutputStream out = new FileOutputStream( f );
	        props.store(out, "This is an optional header comment string");
	    }
	    catch (Exception e ) {
	        e.printStackTrace();
	    }
	}
	
	public void setHost(String host){
		this.host = host;
	}
	
	public String getHost(){
		return this.host;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPort(String port){
		this.port = port;
	}
	
	public String getPort(){
		return this.port;
	}
	
	public void setDatabase(String database){
		this.database = database;
	}
	
	public String getDatabase(){
		return this.database;
	}
	
	@SuppressWarnings("deprecation")
	public void setConfigDatabase(){
		txtHost = new JTextField(this.host);
		txtUsername = new JTextField(this.username);
		txtPassword = new JPasswordField(this.password);
		txtDatabase = new JTextField(this.database);
		txtPort = new JTextField(this.port);
		
		JPanel panelDatabase = new JPanel(new GridLayout(5, 0));
		panelDatabase.add(new JLabel("Host:"));
		panelDatabase.add(txtHost);
		panelDatabase.add(new JLabel("Username:"));
		panelDatabase.add(txtUsername);
		panelDatabase.add(new JLabel("Password:"));
		panelDatabase.add(txtPassword);
		panelDatabase.add(new JLabel("Database:"));
		panelDatabase.add(txtDatabase);
		panelDatabase.add(new JLabel("Port:"));
		panelDatabase.add(txtPort);
		
		int result = JOptionPane.showConfirmDialog(null, panelDatabase, 
               "Setting Konfigurasi Database", JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			setHost(txtHost.getText());
			setUsername(txtUsername.getText());
			setPassword(txtPassword.getText());
			setDatabase(txtDatabase.getText());
			setPort(txtPort.getText());
			saveParamChanges(getHost(), getUsername(), getPassword(), getPort(), getDatabase());
			try {
				if(getKoneksi() == null){
					setConfigDatabase();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(result == JOptionPane.CANCEL_OPTION){
			System.exit(0);
		}
	}
}
