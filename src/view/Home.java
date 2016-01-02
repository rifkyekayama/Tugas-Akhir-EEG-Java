package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mysql.MySQL_Connector;

public class Home extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JPanel mainPanel, panelAwal, panelDashboard, panelKelolaDataLatih, 
						 panelPelatihanSistem, panelPengujianSistem;
	public JButton buttonAwal;
	public JLabel labelJudul;
	
	public Home() throws SQLException{
		super("Identifikasi Kondisi Rileks - 3411121013 Rifky Ekayama");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(1200, 650);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultLookAndFeelDecorated(false);
		
		MySQL_Connector koneksi = new MySQL_Connector();
		if(koneksi.getKoneksi() != null){
			mainPanel = new JPanel(new CardLayout());
			mainPanel.add(getPanelHome(), "panelAwal");
			mainPanel.add(new Dashboard(), "panelDashboard");
			mainPanel.add(new KelolaDataLatih(), "panelKelolaDataLatih");
			mainPanel.add(new PelatihanSistem(), "panelPelatihanSistem");
			mainPanel.add(new PengujianSistem(), "panelPengujianSistem");
			add(mainPanel);
		}
	}
	
	public JPanel getPanelHome(){
		panelAwal = new JPanel();
		panelAwal.setLayout(null);
		panelAwal.setBackground(new Color(210, 214, 222));
		
		JPanel panelJudulAwal = new JPanel();
		panelJudulAwal.setLayout(new GridBagLayout());
		panelJudulAwal.setBackground(new Color(210, 214, 222));
		panelJudulAwal.setBounds((getWidth()/4)-190, (getHeight()/2)-210, 1010, 100);
		
		String judul = "Identifikasi Kondisi Rileks Berdasarkan Sinyal Elektroensephalogram";
		String judul2 = "Menggunakan Ekstraksi Wavelet dan Learning Vector Quantization";
		labelJudul = new JLabel("<html><center>"+judul+"<br>"+judul2+"</center><html>");
		labelJudul.setFont(labelJudul.getFont().deriveFont(Font.BOLD, 25f));
		labelJudul.setHorizontalAlignment(SwingConstants.CENTER);
		labelJudul.setForeground(new Color(68, 68, 77));
		labelJudul.setBounds((getWidth()/4)-190, (getHeight()/2)-210, 1200, 100);
		panelJudulAwal.add(labelJudul);
		
		JPanel panelButtonAwal = new JPanel();
		panelButtonAwal.setLayout(null);
		panelButtonAwal.setBackground(Color.white);
		panelButtonAwal.setBounds((getWidth()/4)-170, (getHeight()/2)-80, 970, 70);
		
		buttonAwal = new JButton("Masuk ke Sistem");
		buttonAwal.setForeground(Color.white);
		buttonAwal.setBackground(new Color(54, 127, 169));
		buttonAwal.setBounds(15, 15, 940, 40);
		buttonAwal.setBorderPainted(false);
		buttonAwal.setActionCommand("btnMasukSistem");
		buttonAwal.addActionListener(new ButtonController());
		buttonAwal.addMouseListener(new MouseController());
		panelButtonAwal.add(buttonAwal);
		
		panelAwal.add(panelJudulAwal);
		panelAwal.add(panelButtonAwal);
		
		return panelAwal;
	}
	
	public static void changeCard(String cardName){
		((CardLayout) mainPanel.getLayout()).show(mainPanel, cardName);
	}
	
	class MouseController implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	class ButtonController implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("btnMasukSistem")){
				changeCard("panelDashboard");
			}
		}
	}
}
