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

public class Layout extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JPanel panelHeaderJudul, panelHeaderJudul2, panelSidebar,
				  panelMainNavigation, panelLVQ, panelFooter;
	public JLabel lblJudulAtas, titlePage, lblAvaHeader, lblNamaHeader, lblAvaSidebar,
				  lblNamaSidebar, lblNimSidebar, lblMainNavigation, lblLVQ, iconLVQ,
				  iconLVQDrop, lblFooterCopyright, lblFooterNama, lblFooterKelas;
	public JButton btnDashboard, btnKelolaDataLatih, btnPelatihan, btnPengujian;

	public Layout(){
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
		
		titlePage = new JLabel("Kelola Data Latih");
		titlePage.setFont(getFont().deriveFont(28f));
		titlePage.setForeground(Color.white);
		titlePage.setBounds(280, (panelHeaderJudul2.getHeight()/2)-25, 300, 50);
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
		
		btnKelolaDataLatih = new JButton(" Kelola Data Latih", new ImageIcon(getClass().getResource("/resource/iconKelolaDataLatih.png")));
		btnKelolaDataLatih.setHorizontalAlignment(SwingConstants.LEFT);
		btnKelolaDataLatih.setForeground(Color.white);
		btnKelolaDataLatih.setBackground(new Color(34, 45, 50));
		btnKelolaDataLatih.setBounds(0, 220, 270, 50);
		btnKelolaDataLatih.setActionCommand("btnKelolaDataLatih");
		btnKelolaDataLatih.setBorderPainted(false);
		btnKelolaDataLatih.addMouseListener(new MouseController());
		btnKelolaDataLatih.addActionListener(new ButtonController());
		panelSidebar.add(btnKelolaDataLatih);
		
		panelLVQ = new JPanel();
		panelLVQ.setLayout(null);
		panelLVQ.setBackground(new Color(30, 40, 44));
		panelLVQ.setBounds(0, 270, 270, 50);
		
		lblLVQ = new JLabel("LVQ");
		lblLVQ.setForeground(Color.white);
		lblLVQ.setBounds(43, -25, 120, 100);
		panelLVQ.add(lblLVQ);
		
		iconLVQ = new JLabel(new ImageIcon(getClass().getResource("/resource/iconLVQ.png")));
		iconLVQ.setBounds(18, 1, 20, 50);
		panelLVQ.add(iconLVQ);
		
		iconLVQDrop = new JLabel(new ImageIcon(getClass().getResource("/resource/iconLVQDrop.png")));
		iconLVQDrop.setBounds(230, 1, 20, 50);
		panelLVQ.add(iconLVQDrop);
		
		btnPelatihan = new JButton("Pelatihan Sistem", new ImageIcon(getClass().getResource("/resource/iconSubMenu.png")));
		btnPelatihan.setForeground(new Color(127, 157, 161));
		btnPelatihan.setHorizontalAlignment(SwingConstants.LEFT);
		btnPelatihan.setBackground(new Color(44, 59, 65));
		btnPelatihan.setBounds(0, 320, 270, 50);
		btnPelatihan.setActionCommand("btnPelatihan");
		btnPelatihan.setBorderPainted(false);
		btnPelatihan.addMouseListener(new MouseController());
		btnPelatihan.addActionListener(new ButtonController());
		panelSidebar.add(btnPelatihan);
		
		btnPengujian = new JButton("Pengujian Sistem", new ImageIcon(getClass().getResource("/resource/iconSubMenu.png")));
		btnPengujian.setForeground(new Color(127, 157, 161));
		btnPengujian.setHorizontalAlignment(SwingConstants.LEFT);
		btnPengujian.setBackground(new Color(44, 59, 65));
		btnPengujian.setBounds(0, 370, 270, 50);
		btnPengujian.setActionCommand("btnPengujian");
		btnPengujian.setBorderPainted(false);
		btnPengujian.addMouseListener(new MouseController());
		btnPengujian.addActionListener(new ButtonController());
		panelSidebar.add(btnPengujian);
		
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
		add(panelLVQ);
		add(panelSidebar);
		add(panelFooter);
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
