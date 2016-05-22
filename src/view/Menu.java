package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Menu extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JPanel panelHeaderJudul, panelHeaderJudul2, panelSidebar,
				  panelMainNavigation, panelFooter;
	public JLabel lblJudulAtas, titlePage, lblAvaHeader, lblNamaHeader, lblAvaSidebar,
				  lblNamaSidebar, lblNimSidebar, lblMainNavigation,
				  lblFooterCopyright, lblFooterNama, lblFooterKelas;
	public JButton btnDashboard, btnKelolaDataLatih, btnEkstraksiWavelet, btnPelatihan, btnPengujian, btnBantuan, btnExit;
	
	public Menu(String title){
		setSize(1200,650);
		setLayout(null);
		setBackground(new Color(236, 240, 245));
		
		panelHeaderJudul = new JPanel();
		panelHeaderJudul.setLayout(new GridBagLayout());
		panelHeaderJudul.setBounds(0, 0, 270, 50);
		panelHeaderJudul.setBackground(new Color(53, 124, 165));
		
		lblJudulAtas = new JLabel("<html><center><font size='5'><span>Identifikasi</span> Rileks</font></html>");
		lblJudulAtas.setForeground(Color.white);
		panelHeaderJudul.add(lblJudulAtas);
		
		panelHeaderJudul2 = new JPanel();
		panelHeaderJudul2.setLayout(null);
		panelHeaderJudul2.setBounds(0, 0, 1200, 50);
		panelHeaderJudul2.setBackground(new Color(60, 141, 188));
		
		titlePage = new JLabel(title);
		titlePage.setFont(getFont().deriveFont(Font.BOLD, 25f));
		titlePage.setForeground(Color.white);
		titlePage.setBounds(280, (panelHeaderJudul2.getHeight()/2)-25, 400, 50);
		panelHeaderJudul2.add(titlePage);
		
		lblAvaHeader = new JLabel(new ImageIcon(getClass().getResource("/resource/avaHeader.png")));
		lblAvaHeader.setBounds(panelHeaderJudul2.getWidth()-200, -25, 110, 100);
		panelHeaderJudul2.add(lblAvaHeader);
		
		lblNamaHeader = new JLabel("Rifky Ekayama");
		lblNamaHeader.setForeground(Color.white);
		lblNamaHeader.setBounds(panelHeaderJudul2.getWidth()-120, -25, 110, 100);
		panelHeaderJudul2.add(lblNamaHeader);
		
		panelSidebar = new JPanel();
		panelSidebar.setLayout(null);
		panelSidebar.setBackground(new Color(34, 45, 50));
		panelSidebar.setBounds(0, 0, 270, 650);
		
		lblAvaSidebar = new JLabel(new ImageIcon(getClass().getResource("/resource/avaSidebar.png")));
		lblAvaSidebar.setBounds(15, 35, 50, 100);
		panelSidebar.add(lblAvaSidebar);
		
		lblNamaSidebar = new JLabel("Rifky Ekayama");
		lblNamaSidebar.setForeground(Color.white);
		lblNamaSidebar.setBounds(80, 25, 110, 100);
		panelSidebar.add(lblNamaSidebar);
		
		lblNimSidebar = new JLabel("3411121013");
		lblNimSidebar.setForeground(Color.white);
		lblNimSidebar.setBounds(80, 45, 110, 100);
		panelSidebar.add(lblNimSidebar);
		
		panelMainNavigation = new JPanel();
		panelMainNavigation.setLayout(null);
		panelMainNavigation.setBackground(new Color(26, 34, 38));
		panelMainNavigation.setBounds(0, 120, 270, 50);
		
		lblMainNavigation = new JLabel("Main Navigation");
		lblMainNavigation.setForeground(new Color(70, 93, 106));
		lblMainNavigation.setBounds(15, -25, 120, 100);
		panelMainNavigation.add(lblMainNavigation);
		
		btnDashboard = new JButton(" Dashboard", new ImageIcon(getClass().getResource("/resource/iconDashboard.png")));
		btnDashboard.setHorizontalAlignment(SwingConstants.LEFT);
		btnDashboard.setForeground(Color.white);
		btnDashboard.setBackground(new Color(34, 45, 50));
		btnDashboard.setBounds(0, 170, 270, 50);
		btnDashboard.setActionCommand("btnDashboard");
		btnDashboard.setBorderPainted(false);
		btnDashboard.addMouseListener(new MouseController());
		btnDashboard.addActionListener(new ButtonController());
		panelSidebar.add(btnDashboard);
		
		btnKelolaDataLatih = new JButton(" Data Latih", new ImageIcon(getClass().getResource("/resource/iconKelolaDataLatih.png")));
		btnKelolaDataLatih.setHorizontalAlignment(SwingConstants.LEFT);
		btnKelolaDataLatih.setForeground(Color.white);
		btnKelolaDataLatih.setBackground(new Color(34, 45, 50));
		btnKelolaDataLatih.setBounds(0, 220, 270, 50);
		btnKelolaDataLatih.setActionCommand("btnKelolaDataLatih");
		btnKelolaDataLatih.setBorderPainted(false);
		btnKelolaDataLatih.addMouseListener(new MouseController());
		btnKelolaDataLatih.addActionListener(new ButtonController());
		panelSidebar.add(btnKelolaDataLatih);
		
		btnEkstraksiWavelet = new JButton(" Ekstraksi Wavelet", new ImageIcon(getClass().getResource("/resource/iconWavelet.png")));
		btnEkstraksiWavelet.setHorizontalAlignment(SwingConstants.LEFT);
		btnEkstraksiWavelet.setForeground(Color.white);
		btnEkstraksiWavelet.setBackground(new Color(34, 45, 50));
		btnEkstraksiWavelet.setBounds(0, 270, 270, 50);
		btnEkstraksiWavelet.setActionCommand("btnEkstraksiWavelet");
		btnEkstraksiWavelet.setBorderPainted(false);
		btnEkstraksiWavelet.addMouseListener(new MouseController());
		btnEkstraksiWavelet.addActionListener(new ButtonController());
		panelSidebar.add(btnEkstraksiWavelet);
		
		btnPelatihan = new JButton(" Pelatihan LVQ", new ImageIcon(getClass().getResource("/resource/iconPelatihan.png")));
		btnPelatihan.setForeground(Color.white);
		btnPelatihan.setHorizontalAlignment(SwingConstants.LEFT);
		btnPelatihan.setBackground(new Color(34, 45, 50));
		btnPelatihan.setBounds(0, 320, 270, 50);
		btnPelatihan.setActionCommand("btnPelatihan");
		btnPelatihan.setBorderPainted(false);
		btnPelatihan.addMouseListener(new MouseController());
		btnPelatihan.addActionListener(new ButtonController());
		panelSidebar.add(btnPelatihan);
		
		btnPengujian = new JButton(" Identifikasi Rileks", new ImageIcon(getClass().getResource("/resource/iconIdentifikasi.png")));
		btnPengujian.setForeground(Color.white);
		btnPengujian.setHorizontalAlignment(SwingConstants.LEFT);
		btnPengujian.setBackground(new Color(34, 45, 50));
		btnPengujian.setBounds(0, 370, 270, 50);
		btnPengujian.setActionCommand("btnPengujian");
		btnPengujian.setBorderPainted(false);
		btnPengujian.addMouseListener(new MouseController());
		btnPengujian.addActionListener(new ButtonController());
		panelSidebar.add(btnPengujian);
		
		btnBantuan = new JButton(" Bantuan", new ImageIcon(getClass().getResource("/resource/iconHelp.png")));
		btnBantuan.setHorizontalAlignment(SwingConstants.LEFT);
		btnBantuan.setForeground(Color.white);
		btnBantuan.setBackground(new Color(34, 45, 50));
		btnBantuan.setBounds(0, 420, 270, 50);
		btnBantuan.setActionCommand("btnBantuan");
		btnBantuan.setBorderPainted(false);
		btnBantuan.addMouseListener(new MouseController());
		btnBantuan.addActionListener(new ButtonController());
		panelSidebar.add(btnBantuan);
		
		btnExit = new JButton(" Keluar", new ImageIcon(getClass().getResource("/resource/iconExit.png")));
		btnExit.setHorizontalAlignment(SwingConstants.LEFT);
		btnExit.setForeground(Color.white);
		btnExit.setBackground(new Color(34, 45, 50));
		btnExit.setBounds(0, 470, 270, 50);
		btnExit.setActionCommand("btnExit");
		btnExit.setBorderPainted(false);
		btnExit.addMouseListener(new MouseController());
		btnExit.addActionListener(new ButtonController());
		panelSidebar.add(btnExit);
		
		panelFooter = new JPanel();
		panelFooter.setLayout(null);
		panelFooter.setBackground(Color.white);
		panelFooter.setBounds(0, this.getHeight()-50, 1200, 50);
		
		lblFooterCopyright = new JLabel("Copyright \u00a9 2016");
		lblFooterCopyright.setBounds(280, (panelFooter.getHeight()/2)-25, 150, 50);
		panelFooter.add(lblFooterCopyright);
		
		lblFooterNama = new JLabel("<html><b>Rifky Ekayama</b></html>");
		lblFooterNama.setForeground(new Color(60, 137, 185));
		lblFooterNama.setBounds(410, (panelFooter.getHeight()/2)-25, 150, 50);
		panelFooter.add(lblFooterNama);
		
		lblFooterKelas = new JLabel("Informatika Unjani 2012");
		lblFooterKelas.setBounds(panelFooter.getWidth()-180, (panelFooter.getHeight()/2)-25, 200, 50);
		panelFooter.add(lblFooterKelas);
		
		add(panelHeaderJudul);
		add(panelHeaderJudul2);
		add(panelMainNavigation);
		add(panelSidebar);
		add(panelFooter);
	}
	
	public void setTitle(String title){
		titlePage.setText(title);
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
			if(e.getActionCommand().equals("btnDashboard")){
				ViewController.changeCard("panelDashboard");
			}else if(e.getActionCommand().equals("btnKelolaDataLatih")){
				ViewController.changeCard("panelKelolaDataLatih");
			}else if(e.getActionCommand().equals("btnEkstraksiWavelet")){
				ViewController.changeCard("panelEkstraksiWavelet");
			}else if(e.getActionCommand().equals("btnEkstraksiWavelet")){
				ViewController.changeCard("panelEkstraksiWavelet");
			}else if(e.getActionCommand().equals("btnPelatihan")){
				ViewController.changeCard("panelPelatihanSistem");
			}else if(e.getActionCommand().equals("btnPengujian")){
				ViewController.changeCard("panelPengujianSistem");
			}else if(e.getActionCommand().equals("btnBantuan")){
				ViewController.changeCard("panelBantuan");
			}else if(e.getActionCommand().equals("btnExit")){
				int dialogResult = JOptionPane.showConfirmDialog(null, "Apakah yakin akan keluar?", "Konfirmasi Keluar", JOptionPane.YES_NO_OPTION);
				if(dialogResult == JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
		}
	}
}
