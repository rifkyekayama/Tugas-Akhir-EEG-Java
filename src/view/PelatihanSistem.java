package view;

import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import mysql.DatabaseAction;

public class PelatihanSistem extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JTextField txtMaksimumEpoch, txtMinimumError, txtLearningRate, txtPenguranganLR;
	protected JButton btnPelatihan;
	protected DefaultTableModel tableModel;
	protected JTable tableBobot;
	protected JScrollPane scrollTableBobot;
	protected DefaultTableCellRenderer centerTable;
	protected DatabaseAction dbAction;
	
	public PelatihanSistem(){
		setSize(1200, 650);
		setLayout(null);
		dbAction = new DatabaseAction();
		tableModel = dbAction.getListDataBobot();
		centerTable = new DefaultTableCellRenderer();
		centerTable.setHorizontalAlignment(SwingConstants.CENTER);
		add(getContent());
		add(layouts());
	}
	
	public JPanel getContent(){
		JPanel panelContent = new JPanel();
		panelContent.setBounds(280, 60, 910, 530);
		panelContent.setLayout(null);
		
		JPanel panelFormPelatihan = new JPanel();
		panelFormPelatihan.setLayout(null);
		panelFormPelatihan.setBackground(Color.white);
		panelFormPelatihan.setBounds(0, 0, 450, 250);
		
		JLabel lblTitleFormPelatihan = new JLabel("Pelatihan Sistem");
		lblTitleFormPelatihan.setForeground(new Color(68, 68, 68));
		lblTitleFormPelatihan.setBounds(15, 0, 150, 30);
		panelFormPelatihan.add(lblTitleFormPelatihan);
		
		JLabel lblMaksimumEpoch = new JLabel("Maksimum Epoch:");
		lblMaksimumEpoch.setFont(lblMaksimumEpoch.getFont().deriveFont(15f));
		lblMaksimumEpoch.setBounds(15, 30, 150, 30);
		panelFormPelatihan.add(lblMaksimumEpoch);
		
		txtMaksimumEpoch = new JTextField("10");
		txtMaksimumEpoch.setBounds(15, 60, 205, 30);
		panelFormPelatihan.add(txtMaksimumEpoch);
		
		JLabel lblMinimumError = new JLabel("Minimum Error:");
		lblMinimumError.setFont(lblMinimumError.getFont().deriveFont(15f));
		lblMinimumError.setBounds(panelFormPelatihan.getWidth()-220, 30, 150, 30);
		panelFormPelatihan.add(lblMinimumError);
		
		txtMinimumError = new JTextField("0.0001");
		txtMinimumError.setBounds(panelFormPelatihan.getWidth()-220, 60, 205, 30);
		panelFormPelatihan.add(txtMinimumError);
		
		JLabel lblLearningRate = new JLabel("Learning Rate:");
		lblLearningRate.setFont(lblLearningRate.getFont().deriveFont(15f));
		lblLearningRate.setBounds(15, 100, 150, 30);
		panelFormPelatihan.add(lblLearningRate);
		
		txtLearningRate = new JTextField("0.05");
		txtLearningRate.setBounds(15, 130, 205, 30);
		panelFormPelatihan.add(txtLearningRate);
		
		JLabel lblPenguranganLR = new JLabel("Pengurangan LR:");
		lblPenguranganLR.setFont(lblPenguranganLR.getFont().deriveFont(15f));
		lblPenguranganLR.setBounds(panelFormPelatihan.getWidth()-220, 100, 200, 30);
		panelFormPelatihan.add(lblPenguranganLR);
		
		txtPenguranganLR = new JTextField("0.1");
		txtPenguranganLR.setBounds(panelFormPelatihan.getWidth()-220, 130, 205, 30);
		panelFormPelatihan.add(txtPenguranganLR);
		
		btnPelatihan = new JButton("Mulai Pelatihan");
		btnPelatihan.setForeground(Color.white);
		btnPelatihan.setBackground(new Color(60, 137, 185));
		btnPelatihan.setBorderPainted(false);
		btnPelatihan.setActionCommand("mulaiPelatihan");
		btnPelatihan.addMouseListener(new MouseController());
		btnPelatihan.addActionListener(new ButtonController());
		btnPelatihan.setBounds(15, 180, 420, 50);
		panelFormPelatihan.add(btnPelatihan);
		
		JPanel panelTabelPelatihan = new JPanel();
		panelTabelPelatihan.setLayout(null);
		panelTabelPelatihan.setBackground(Color.white);
		panelTabelPelatihan.setBounds(460, 0, 450, 530);
		
		JLabel lblTitleTabelPelatihan = new JLabel("Hasil Bobot Pelatihan");
		lblTitleTabelPelatihan.setForeground(new Color(68, 68, 68));
		lblTitleTabelPelatihan.setBounds(15, 0, 250, 30);
		panelTabelPelatihan.add(lblTitleTabelPelatihan);
		
		JPanel panelTableDataBobot = new JPanel();
		panelTableDataBobot.setLayout(new BorderLayout());
		panelTableDataBobot.setBackground(Color.white);
		panelTableDataBobot.setBounds(15, 30, panelTabelPelatihan.getWidth()-30, 480);
		panelTabelPelatihan.add(panelTableDataBobot);
		
		tableBobot = new JTable(tableModel);
		tableBobot.setRowSelectionAllowed(false);
		tableBobot.setPreferredScrollableViewportSize(getSize());
		tableBobot.setFillsViewportHeight(true);
		tableBobot.getColumnModel().getColumn(0).setCellRenderer(centerTable);
		tableBobot.getColumnModel().getColumn(1).setCellRenderer(centerTable);
		tableBobot.getColumnModel().getColumn(2).setCellRenderer(centerTable);
		
		scrollTableBobot = new JScrollPane(tableBobot);
		scrollTableBobot.setVisible(true);
		panelTableDataBobot.add(scrollTableBobot, BorderLayout.CENTER);
		
		panelContent.add(panelFormPelatihan);
		panelContent.add(panelTabelPelatihan);
		
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
		
		JLabel titlePage = new JLabel("Pelatihan Sistem");
		titlePage.setFont(getFont().deriveFont(28f));
		titlePage.setForeground(Color.white);
		titlePage.setBounds(280, (panelHeaderJudul2.getHeight()/2)-25, 300, 50);
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
