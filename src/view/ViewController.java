package view;

import java.awt.CardLayout;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Main;
import mysql.Database;

public class ViewController extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JPanel mainPanel;
	
	private static Dashboard dashboard = new Dashboard();
	private static KelolaDataLatih kelolaDataLatih = new KelolaDataLatih();
	private static Ekstraksi ekstraksi = new Ekstraksi();
	private static Pelatihan pelatihanLVQ = new Pelatihan();
	private static Identifikasi identifikasi = new Identifikasi();
	private static Bantuan bantuan = new Bantuan();
	private static Database database = new Database();
	
	public ViewController() throws SQLException{
		super("Identifikasi Kondisi Rileks - 3411121013 Rifky Ekayama");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(1200, 675);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultLookAndFeelDecorated(false);
		
		if(Main.koneksi != null){
			mainPanel = new JPanel(new CardLayout());
			mainPanel.add(dashboard, "panelDashboard");
			mainPanel.add(kelolaDataLatih, "panelKelolaDataLatih");
			mainPanel.add(ekstraksi, "panelEkstraksiWavelet");
			mainPanel.add(pelatihanLVQ, "panelPelatihanSistem");
			mainPanel.add(identifikasi, "panelPengujianSistem");
			mainPanel.add(bantuan, "panelBantuan");
			add(mainPanel);
		}
	}
	
	public static void refreshAllElement(){
		dashboard.lblJumNaracoba.setText(Integer.toString(database.getJumNaracoba()));
		dashboard.lblJumSegmentasi.setText(Integer.toString(database.getJumSegmentasi()));
		dashboard.lblJumRileks.setText(Integer.toString(database.getJumRileks()));
		dashboard.lblJumNonRileks.setText(Integer.toString(database.getJumNonRileks()));
		kelolaDataLatih.updateTableDataLatih();
		kelolaDataLatih.updateStatusAlat();
		kelolaDataLatih.updateStatusKanal();
		kelolaDataLatih.updateCmbEditNaracoba();
		ekstraksi.updateTabelEkstraksiWavelet();
		ekstraksi.updateCmbNaracoba();
		identifikasi.updateStatusAlat();
		identifikasi.updateStatusKanal();
	}
	
	public static void changeCard(String cardName){
		((CardLayout) mainPanel.getLayout()).show(mainPanel, cardName);
	}
}
