package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mysql.DatabaseAction;

public class Dashboard extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JLabel lblJumNaracoba, lblJumSegmentasi, lblJumRileks, lblJumNonRileks;
	protected DatabaseAction dbAction;
	
	public Dashboard(){
		setSize(1200, 650);
		setLayout(null);
		add(getContent());
		add(layouts());
		
		dbAction = new DatabaseAction();
		
		lblJumNaracoba.setText(dbAction.getJumNaracoba());
		lblJumSegmentasi.setText(dbAction.getJumSegmentasi());
		lblJumRileks.setText(dbAction.getJumRileks());
		lblJumNonRileks.setText(dbAction.getJumNonRileks());
	}
	
	public JPanel getContent(){
		JPanel panelContent = new JPanel();
		panelContent.setBounds(280, 60, 910, 530);
		panelContent.setLayout(null);
		
		JPanel panelJudul = new JPanel();
		panelJudul.setBackground(Color.white);
		panelJudul.setLayout(new GridBagLayout());
		panelJudul.setBounds(0, 0, panelContent.getWidth(), 100);
		
		JLabel lblJudul = new JLabel("<html><center><b>IDENTIFIKASI KONDISI RILEKS BERDASARKAN SINYAL ELECTROENSEPHALOGRAM MENGGUNAKAN <br> EKSTRAKSI WAVELET DAN LEARNING VECTOR QUANTIZATION</b></center></html>");
		lblJudul.setFont(lblJudul.getFont().deriveFont(16f));
		panelJudul.add(lblJudul);
		
		JPanel panelWidgetNaracoba = new JPanel();
		panelWidgetNaracoba.setBackground(new Color(243, 156, 18));
		panelWidgetNaracoba.setLayout(null);
		panelWidgetNaracoba.setBounds(0, 120, 220, 130);
		
		lblJumNaracoba = new JLabel("0");
		lblJumNaracoba.setFont(lblJumNaracoba.getFont().deriveFont(40f));
		lblJumNaracoba.setForeground(Color.white);
		lblJumNaracoba.setBounds(10, -15, 90, 100);
		panelWidgetNaracoba.add(lblJumNaracoba);
		
		JLabel lblKetNaracoba = new JLabel("Naracoba");
		lblKetNaracoba.setForeground(Color.white);
		lblKetNaracoba.setBounds(10, 60, 100, 30);
		panelWidgetNaracoba.add(lblKetNaracoba);
		
		JLabel iconNaracoba = new JLabel(new ImageIcon(getClass().getResource("/resource/iconWidgetNaracoba.png")));
		iconNaracoba.setBounds(panelWidgetNaracoba.getWidth()-107, 10, 97, 90);
		panelWidgetNaracoba.add(iconNaracoba);
		
		JButton btnNaracoba = new JButton("More Info");
		btnNaracoba.setForeground(Color.white);
		btnNaracoba.setBackground(new Color(221, 140, 41));
		btnNaracoba.setBorderPainted(false);
		btnNaracoba.addMouseListener(new MouseController());
		btnNaracoba.setBounds(0, panelWidgetNaracoba.getHeight()-25, panelWidgetNaracoba.getWidth(), 25);
		panelWidgetNaracoba.add(btnNaracoba);
		
		JPanel panelWidgetSegmentasi = new JPanel();
		panelWidgetSegmentasi.setBackground(new Color(0, 166, 90));
		panelWidgetSegmentasi.setLayout(null);
		panelWidgetSegmentasi.setBounds(230, 120, 220, 130);
		
		lblJumSegmentasi = new JLabel("0");
		lblJumSegmentasi.setFont(lblJumSegmentasi.getFont().deriveFont(40f));
		lblJumSegmentasi.setForeground(Color.white);
		lblJumSegmentasi.setBounds(10, -15, 90, 100);
		panelWidgetSegmentasi.add(lblJumSegmentasi);
		
		JLabel lblKetSegmentasi = new JLabel("Segmentasi Data");
		lblKetSegmentasi.setForeground(Color.white);
		lblKetSegmentasi.setBounds(10, 60, 130, 30);
		panelWidgetSegmentasi.add(lblKetSegmentasi);
		
		JLabel iconSegmentasi = new JLabel(new ImageIcon(getClass().getResource("/resource/iconWidgetSegmentasi.png")));
		iconSegmentasi.setBounds(panelWidgetSegmentasi.getWidth()-88, 10, 78, 91);
		panelWidgetSegmentasi.add(iconSegmentasi);
		
		JButton btnSegmentasi = new JButton("More Info");
		btnSegmentasi.setForeground(Color.white);
		btnSegmentasi.setBackground(new Color(0, 158, 87));
		btnSegmentasi.setBorderPainted(false);
		btnSegmentasi.addMouseListener(new MouseController());
		btnSegmentasi.setBounds(0, panelWidgetSegmentasi.getHeight()-25, panelWidgetSegmentasi.getWidth(), 25);
		panelWidgetSegmentasi.add(btnSegmentasi);
		
		JPanel panelWidgetRileks = new JPanel();
		panelWidgetRileks.setBackground(new Color(0, 192, 239));
		panelWidgetRileks.setLayout(null);
		panelWidgetRileks.setBounds(460, 120, 220, 130);
		
		lblJumRileks = new JLabel("0");
		lblJumRileks.setFont(lblJumRileks.getFont().deriveFont(40f));
		lblJumRileks.setForeground(Color.white);
		lblJumRileks.setBounds(10, -15, 90, 100);
		panelWidgetRileks.add(lblJumRileks);
		
		JLabel lblKetRileks = new JLabel("Kelas Rileks");
		lblKetRileks.setForeground(Color.white);
		lblKetRileks.setBounds(10, 60, 130, 30);
		panelWidgetRileks.add(lblKetRileks);
		
		JLabel iconRileks = new JLabel(new ImageIcon(getClass().getResource("/resource/iconWidgetRileks.png")));
		iconRileks.setBounds(panelWidgetRileks.getWidth()-113, 10, 103, 64);
		panelWidgetRileks.add(iconRileks);
		
		JButton btnRileks = new JButton("More Info");
		btnRileks.setForeground(Color.white);
		btnRileks.setBackground(new Color(0, 171, 213));
		btnRileks.setBorderPainted(false);
		btnRileks.addMouseListener(new MouseController());
		btnRileks.setBounds(0, panelWidgetRileks.getHeight()-25, panelWidgetRileks.getWidth(), 25);
		panelWidgetRileks.add(btnRileks);
		
		JPanel panelWidgetNonRileks = new JPanel();
		panelWidgetNonRileks.setBackground(new Color(221, 75, 57));
		panelWidgetNonRileks.setLayout(null);
		panelWidgetNonRileks.setBounds(panelContent.getWidth()-220, 120, 220, 130);
		
		lblJumNonRileks = new JLabel("0");
		lblJumNonRileks.setFont(lblJumNonRileks.getFont().deriveFont(40f));
		lblJumNonRileks.setForeground(Color.white);
		lblJumNonRileks.setBounds(10, -15, 90, 100);
		panelWidgetNonRileks.add(lblJumNonRileks);
		
		JLabel lblKetNonRileks = new JLabel("Kelas Non-Rileks");
		lblKetNonRileks.setForeground(Color.white);
		lblKetNonRileks.setBounds(10, 60, 130, 30);
		panelWidgetNonRileks.add(lblKetNonRileks);
		
		JLabel iconNonRileks = new JLabel(new ImageIcon(getClass().getResource("/resource/iconWidgetNonRileks.png")));
		iconNonRileks.setBounds(panelWidgetNonRileks.getWidth()-100, 10, 90, 78);
		panelWidgetNonRileks.add(iconNonRileks);
		
		JButton btnNonRileks = new JButton("More Info");
		btnNonRileks.setForeground(Color.white);
		btnNonRileks.setBackground(new Color(204, 50, 55));
		btnNonRileks.setBorderPainted(false);
		btnNonRileks.addMouseListener(new MouseController());
		btnNonRileks.setBounds(0, panelWidgetNonRileks.getHeight()-25, panelWidgetNonRileks.getWidth(), 25);
		panelWidgetNonRileks.add(btnNonRileks);
		
		panelContent.add(panelJudul);
		panelContent.add(panelWidgetNaracoba);
		panelContent.add(panelWidgetSegmentasi);
		panelContent.add(panelWidgetRileks);
		panelContent.add(panelWidgetNonRileks);
		
		return panelContent;
	}
	
	public JPanel layouts(){
		JPanel panelLayouts = new JPanel();
		panelLayouts.setSize(1200,650);
		panelLayouts.setLayout(null);
		panelLayouts.setBackground(new Color(236, 240, 245));
		
		JPanel panelHeaderJudul = new JPanel();
		panelHeaderJudul.setLayout(new GridBagLayout());
		panelHeaderJudul.setBounds(0, 0, 270, 50);
		panelHeaderJudul.setBackground(new Color(53, 124, 165));
		
		JLabel lblJudulAtas = new JLabel("<html><center><font size='5'><span>Identifikasi</span> Rileks</font></html>");
		lblJudulAtas.setForeground(Color.white);
		panelHeaderJudul.add(lblJudulAtas);
		
		JPanel panelHeaderJudul2 = new JPanel();
		panelHeaderJudul2.setLayout(null);
		panelHeaderJudul2.setBounds(0, 0, 1200, 50);
		panelHeaderJudul2.setBackground(new Color(60, 141, 188));
		
		JLabel titlePage = new JLabel("Dashboard");
		titlePage.setFont(getFont().deriveFont(28f));
		titlePage.setForeground(Color.white);
		titlePage.setBounds(280, (panelHeaderJudul2.getHeight()/2)-25, 200, 50);
		panelHeaderJudul2.add(titlePage);
		
		JLabel lblAvaHeader = new JLabel(new ImageIcon(getClass().getResource("/resource/avaHeader.png")));
		lblAvaHeader.setBounds(panelHeaderJudul2.getWidth()-200, -25, 110, 100);
		panelHeaderJudul2.add(lblAvaHeader);
		
		JLabel lblNamaHeader = new JLabel("Rifky Ekayama");
		lblNamaHeader.setForeground(Color.white);
		lblNamaHeader.setBounds(panelHeaderJudul2.getWidth()-120, -25, 110, 100);
		panelHeaderJudul2.add(lblNamaHeader);
		
		JPanel panelSidebar = new JPanel();
		panelSidebar.setLayout(null);
		panelSidebar.setBackground(new Color(34, 45, 50));
		panelSidebar.setBounds(0, 0, 270, 650);
		
		JLabel lblAvaSidebar = new JLabel(new ImageIcon(getClass().getResource("/resource/avaSidebar.png")));
		lblAvaSidebar.setBounds(15, 35, 50, 100);
		panelSidebar.add(lblAvaSidebar);
		
		JLabel lblNamaSidebar = new JLabel("Rifky Ekayama");
		lblNamaSidebar.setForeground(Color.white);
		lblNamaSidebar.setBounds(80, 25, 110, 100);
		panelSidebar.add(lblNamaSidebar);
		
		JLabel lblNimSidebar = new JLabel("3411121013");
		lblNimSidebar.setForeground(Color.white);
		lblNimSidebar.setBounds(80, 45, 110, 100);
		panelSidebar.add(lblNimSidebar);
		
		JPanel panelMainNavigation = new JPanel();
		panelMainNavigation.setLayout(null);
		panelMainNavigation.setBackground(new Color(26, 34, 38));
		panelMainNavigation.setBounds(0, 120, 270, 50);
		
		JLabel lblMainNavigation = new JLabel("Main Navigation");
		lblMainNavigation.setForeground(new Color(70, 93, 106));
		lblMainNavigation.setBounds(15, -25, 120, 100);
		panelMainNavigation.add(lblMainNavigation);
		
		JButton btnDashboard = new JButton("<html><font color='white'> Dashboard</font></html>", new ImageIcon(getClass().getResource("/resource/iconDashboard.png")));
		btnDashboard.setHorizontalAlignment(SwingConstants.LEFT);
		btnDashboard.setBackground(new Color(34, 45, 50));
		btnDashboard.setBounds(0, 170, 270, 50);
		btnDashboard.setActionCommand("btnDashboard");
		btnDashboard.setBorderPainted(false);
		btnDashboard.addMouseListener(new MouseController());
		btnDashboard.addActionListener(new ButtonController());
		panelSidebar.add(btnDashboard);
		
		JButton btnKelolaDataLatih = new JButton("<html><font color='white'> Kelola Data Latih</font></html>", new ImageIcon(getClass().getResource("/resource/iconKelolaDataLatih.png")));
		btnKelolaDataLatih.setHorizontalAlignment(SwingConstants.LEFT);
		btnKelolaDataLatih.setBackground(new Color(34, 45, 50));
		btnKelolaDataLatih.setBounds(0, 220, 270, 50);
		btnKelolaDataLatih.setActionCommand("btnKelolaDataLatih");
		btnKelolaDataLatih.setBorderPainted(false);
		btnKelolaDataLatih.addMouseListener(new MouseController());
		btnKelolaDataLatih.addActionListener(new ButtonController());
		panelSidebar.add(btnKelolaDataLatih);
		
		JPanel panelLVQ = new JPanel();
		panelLVQ.setLayout(null);
		panelLVQ.setBackground(new Color(30, 40, 44));
		panelLVQ.setBounds(0, 270, 270, 50);
		
		JLabel lblLVQ = new JLabel("LVQ");
		lblLVQ.setForeground(Color.white);
		lblLVQ.setBounds(43, -25, 120, 100);
		panelLVQ.add(lblLVQ);
		
		JLabel iconLVQ = new JLabel(new ImageIcon(getClass().getResource("/resource/iconLVQ.png")));
		iconLVQ.setBounds(18, 1, 20, 50);
		panelLVQ.add(iconLVQ);
		
		JLabel iconLVQDrop = new JLabel(new ImageIcon(getClass().getResource("/resource/iconLVQDrop.png")));
		iconLVQDrop.setBounds(230, 1, 20, 50);
		panelLVQ.add(iconLVQDrop);
		
		JButton btnPelatihan = new JButton("Pelatihan Sistem", new ImageIcon(getClass().getResource("/resource/iconSubMenu.png")));
		btnPelatihan.setForeground(new Color(127, 157, 161));
		btnPelatihan.setHorizontalAlignment(SwingConstants.LEFT);
		btnPelatihan.setBackground(new Color(44, 59, 65));
		btnPelatihan.setBounds(0, 320, 270, 50);
		btnPelatihan.setActionCommand("btnPelatihan");
		btnPelatihan.setBorderPainted(false);
		btnPelatihan.addMouseListener(new MouseController());
		btnPelatihan.addActionListener(new ButtonController());
		panelSidebar.add(btnPelatihan);
		
		JButton btnPengujian = new JButton("Pengujian Sistem", new ImageIcon(getClass().getResource("/resource/iconSubMenu.png")));
		btnPengujian.setForeground(new Color(127, 157, 161));
		btnPengujian.setHorizontalAlignment(SwingConstants.LEFT);
		btnPengujian.setBackground(new Color(44, 59, 65));
		btnPengujian.setBounds(0, 370, 270, 50);
		btnPengujian.setActionCommand("btnPengujian");
		btnPengujian.setBorderPainted(false);
		btnPengujian.addMouseListener(new MouseController());
		btnPengujian.addActionListener(new ButtonController());
		panelSidebar.add(btnPengujian);
		
		JPanel panelFooter = new JPanel();
		panelFooter.setLayout(null);
		panelFooter.setBackground(Color.white);
		panelFooter.setBounds(0, panelLayouts.getHeight()-50, 1200, 50);
		
		JLabel lblFooterCopyright = new JLabel("Copyright \u00a9 2016");
		lblFooterCopyright.setBounds(280, (panelFooter.getHeight()/2)-25, 150, 50);
		panelFooter.add(lblFooterCopyright);
		
		JLabel lblFooterNama = new JLabel("<html><b>Rifky Ekayama</b></html>");
		lblFooterNama.setForeground(new Color(60, 137, 185));
		lblFooterNama.setBounds(410, (panelFooter.getHeight()/2)-25, 150, 50);
		panelFooter.add(lblFooterNama);
		
		JLabel lblFooterKelas = new JLabel("Informatika Unjani 2012");
		lblFooterKelas.setBounds(panelFooter.getWidth()-180, (panelFooter.getHeight()/2)-25, 200, 50);
		panelFooter.add(lblFooterKelas);
		
		panelLayouts.add(panelHeaderJudul);
		panelLayouts.add(panelHeaderJudul2);
		panelLayouts.add(panelMainNavigation);
		panelLayouts.add(panelLVQ);
		panelLayouts.add(panelSidebar);
		panelLayouts.add(panelFooter);
		
		return panelLayouts;
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
				Home.changeCard("panelDashboard");
			}else if(e.getActionCommand().equals("btnKelolaDataLatih")){
				Home.changeCard("panelKelolaDataLatih");
			}else if(e.getActionCommand().equals("btnPelatihan")){
				Home.changeCard("panelPelatihanSistem");
			}else if(e.getActionCommand().equals("btnPengujian")){
				Home.changeCard("panelPengujianSistem");
			}
		}
	}
}
