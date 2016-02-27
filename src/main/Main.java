package main;

import java.sql.SQLException;

import javax.swing.UIManager;

import mysql.SQLite_Connector;
import view.*;

public class Main {
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
                	SQLite_Connector koneksi = new SQLite_Connector();
                	koneksi.createDatabase();
					new Home().setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}
}
