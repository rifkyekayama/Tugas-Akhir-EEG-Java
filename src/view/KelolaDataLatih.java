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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class KelolaDataLatih extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JButton btnPilihDataEEG;
	protected JLabel lblFileDataEEG;
	protected JComboBox<?> cmbKelas, cmbKanal1, cmbKanal2;
	protected JTextField txtSegmentasi, txtSamplingrate;
	protected String[] kelas = {"Pilih salah satu...", "Rileks", "Non-Rileks"};
	protected String[] kanal = {"Pilih salah satu...", "AF3", "F7", "F3", "FC5", "T7", "P7", "O1", "O2", "P8", "T8", "FC6", "F4", "F8", "AF4"};

	public KelolaDataLatih(){
		setSize(1200, 650);
		setLayout(null);
		add(getContent());
		add(layouts());
	}
	
	public JPanel getContent(){
		JPanel panelContent = new JPanel();
		panelContent.setBounds(280, 60, 910, 530);
		panelContent.setLayout(null);
		
		JPanel panelFormDataLatih = new JPanel();
		panelFormDataLatih.setLayout(null);
		panelFormDataLatih.setBackground(Color.white);
		panelFormDataLatih.setBounds(0, 0, 450, 530);
		
		JLabel lblTitleInputDataLatih = new JLabel("Input Data latih");
		lblTitleInputDataLatih.setForeground(new Color(68, 68, 68));
		lblTitleInputDataLatih.setBounds(15, 0, 150, 30);
		panelFormDataLatih.add(lblTitleInputDataLatih);
		
		JLabel lblPilihDataEEG = new JLabel("Pilih Data EEG");
		lblPilihDataEEG.setFont(lblPilihDataEEG.getFont().deriveFont(15f));
		lblPilihDataEEG.setBounds(15, 30, 150, 30);
		panelFormDataLatih.add(lblPilihDataEEG);
		
		btnPilihDataEEG = new JButton("Choose File");
		btnPilihDataEEG.setBackground(Color.lightGray);
		btnPilihDataEEG.setActionCommand("pilihDataEEG");
		btnPilihDataEEG.setBorderPainted(false);
		btnPilihDataEEG.addMouseListener(new MouseController());
		btnPilihDataEEG.addActionListener(new ButtonController());
		btnPilihDataEEG.setBounds(15, 60, 120, 25);
		panelFormDataLatih.add(btnPilihDataEEG);
		
		lblFileDataEEG = new JLabel("No file chosen");
		lblFileDataEEG.setBounds(140, 60, 100, 30);
		panelFormDataLatih.add(lblFileDataEEG);
		
		JLabel lblKetDataEEG = new JLabel("<html>Data yang dimasukan harus bertipe .CSV dan terdiri dari<br>14 kanal.</html>");
		lblKetDataEEG.setForeground(new Color(115, 115, 115));
		lblKetDataEEG.setBounds(15, 90, 500, 30);
		panelFormDataLatih.add(lblKetDataEEG);
			
		JLabel lblKelas = new JLabel("Kelas :");
		lblKelas.setFont(lblKelas.getFont().deriveFont(15f));
		lblKelas.setBounds(15, 140, 100, 30);
		panelFormDataLatih.add(lblKelas);
		
		cmbKelas = new JComboBox<>(kelas);
		cmbKelas.setBackground(Color.white);
		cmbKelas.setBounds(15, 170, 420, 30);
		panelFormDataLatih.add(cmbKelas);
		
		JLabel lblSegmentasi = new JLabel("Segmentasi (per detik) :");
		lblSegmentasi.setFont(lblSegmentasi.getFont().deriveFont(15f));
		lblSegmentasi.setBounds(15, 210, 250, 30);
		panelFormDataLatih.add(lblSegmentasi);
		
		txtSegmentasi = new JTextField("60");
		txtSegmentasi.setBounds(15, 240, 420, 30);
		panelFormDataLatih.add(txtSegmentasi);
		
		JLabel lblSamplingRate = new JLabel("Sampling rate (Hertz) :");
		lblSamplingRate.setFont(lblSamplingRate.getFont().deriveFont(15f));
		lblSamplingRate.setBounds(15, 280, 250, 30);
		panelFormDataLatih.add(lblSamplingRate);
		
		txtSamplingrate = new JTextField("128");
		txtSamplingrate.setBounds(15, 310, 420, 30);
		panelFormDataLatih.add(txtSamplingrate);
		
		JLabel lblKanal1 = new JLabel("Kanal 1 :");
		lblKanal1.setFont(lblKanal1.getFont().deriveFont(15f));
		lblKanal1.setBounds(15, 350, 250, 30);
		panelFormDataLatih.add(lblKanal1);
		
		cmbKanal1 = new JComboBox<>(kanal);
		cmbKanal1.setBackground(Color.white);
		cmbKanal1.setBounds(15, 380, 420, 30);
		panelFormDataLatih.add(cmbKanal1);
		
		JCheckBox cbGunakanKanal2 = new JCheckBox("Gunakan Kanal 2", false);
		cbGunakanKanal2.setBackground(Color.white);
		cbGunakanKanal2.setBounds(15, 420, 250, 30);
		panelFormDataLatih.add(cbGunakanKanal2);
		
		JLabel lblKanal2 = new JLabel("Kanal 2 :");
		lblKanal2.setFont(lblKanal2.getFont().deriveFont(15f));
		lblKanal2.setBounds(15, 450, 250, 30);
		panelFormDataLatih.add(lblKanal2);
		
		cmbKanal2 = new JComboBox<>(kanal);
		cmbKanal2.setBackground(Color.white);
		cmbKanal2.setBounds(15, 480, 420, 30);
		panelFormDataLatih.add(cmbKanal2);
		
		JPanel panelLihatDataLatih = new JPanel();
		panelLihatDataLatih.setLayout(null);
		panelLihatDataLatih.setBackground(Color.white);
		panelLihatDataLatih.setBounds(460, 0, 450, 539);
		
		JLabel lblTitleTableDataLatih = new JLabel("Tabel Data Latih");
		lblTitleTableDataLatih.setForeground(new Color(68, 68, 68));
		lblTitleTableDataLatih.setBounds(15, 0, 150, 30);
		panelLihatDataLatih.add(lblTitleTableDataLatih);
		
		panelContent.add(panelFormDataLatih);
		panelContent.add(panelLihatDataLatih);
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
		
		JLabel titlePage = new JLabel("Kelola Data Latih");
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
