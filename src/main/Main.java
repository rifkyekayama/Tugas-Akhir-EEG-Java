package main;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.UIManager;

import mysql.SQLiteConnector;
import view.*;

public class Main {
	public static Connection konek;
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	SQLiteConnector koneksi = new SQLiteConnector();
                	koneksi.createDatabase();
                	konek = koneksi.getKoneksi();
					new Home().setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}
}
