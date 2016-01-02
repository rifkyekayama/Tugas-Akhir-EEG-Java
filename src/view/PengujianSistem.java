package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PengujianSistem extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JButton btnPilihDataEEG, btnSubmitDataEEG;
	protected JLabel lblFileDataEEG;
	protected JComboBox<?> cmbKelas, cmbKanal1, cmbKanal2;
	protected JTextField txtSegmentasi, txtSamplingrate;
	protected JFileChooser inputDataEEG;
	protected String[] kelas = {"Pilih salah satu...", "Rileks", "Non-Rileks"};
	protected String[] kanal = {"Pilih salah satu...", "AF3", "F7", "F3", "FC5", "T7", "P7", "O1", "O2", "P8", "T8", "FC6", "F4", "F8", "AF4"};
	
	public PengujianSistem(){
		setSize(1200, 650);
		setLayout(null);
		add(getContent());
		add(new Layout("Pengujian Sistem"));
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
		lblPilihDataEEG.setFont(lblPilihDataEEG.getFont().deriveFont(Font.BOLD, 15f));
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
		lblFileDataEEG.setBounds(140, 60, 300, 30);
		panelFormDataLatih.add(lblFileDataEEG);
		
		JLabel lblKetDataEEG = new JLabel("<html>Data yang dimasukan harus bertipe .CSV dan terdiri dari<br>14 kanal.</html>");
		lblKetDataEEG.setForeground(new Color(115, 115, 115));
		lblKetDataEEG.setBounds(15, 90, 500, 30);
		panelFormDataLatih.add(lblKetDataEEG);
			
		JLabel lblKelas = new JLabel("Kelas :");
		lblKelas.setFont(lblKelas.getFont().deriveFont(Font.BOLD, 15f));
		lblKelas.setBounds(15, 140, 100, 30);
		panelFormDataLatih.add(lblKelas);
		
		cmbKelas = new JComboBox<>(kelas);
		cmbKelas.setBackground(Color.white);
		cmbKelas.setBounds(15, 170, 420, 30);
		panelFormDataLatih.add(cmbKelas);
		
		JLabel lblSegmentasi = new JLabel("Segmentasi (per detik) :");
		lblSegmentasi.setFont(lblSegmentasi.getFont().deriveFont(Font.BOLD, 15f));
		lblSegmentasi.setBounds(15, 210, 205, 30);
		panelFormDataLatih.add(lblSegmentasi);
		
		txtSegmentasi = new JTextField("60");
		txtSegmentasi.setBounds(15, 240, 205, 30);
		panelFormDataLatih.add(txtSegmentasi);
		
		JLabel lblSamplingRate = new JLabel("Sampling rate (Hertz) :");
		lblSamplingRate.setFont(lblSamplingRate.getFont().deriveFont(Font.BOLD, 15f));
		lblSamplingRate.setBounds(panelFormDataLatih.getWidth()-220, 210, 205, 30);
		panelFormDataLatih.add(lblSamplingRate);
		
		txtSamplingrate = new JTextField("128");
		txtSamplingrate.setBounds(panelFormDataLatih.getWidth()-220, 240, 205, 30);
		panelFormDataLatih.add(txtSamplingrate);
		
		JCheckBox cbGunakanKanal2 = new JCheckBox("Gunakan Kanal 2", false);
		cbGunakanKanal2.setBackground(Color.white);
		cbGunakanKanal2.setBounds(panelFormDataLatih.getWidth()-220, 280, 205, 30);
		cbGunakanKanal2.setActionCommand("cekKanal2");
		cbGunakanKanal2.addActionListener(new ButtonController());
		panelFormDataLatih.add(cbGunakanKanal2);
		
		JLabel lblKanal1 = new JLabel("Kanal 1 :");
		lblKanal1.setFont(lblKanal1.getFont().deriveFont(Font.BOLD, 15f));
		lblKanal1.setBounds(15, 310, 205, 30);
		panelFormDataLatih.add(lblKanal1);
		
		cmbKanal1 = new JComboBox<>(kanal);
		cmbKanal1.setBackground(Color.white);
		cmbKanal1.setBounds(15, 340, 205, 30);
		panelFormDataLatih.add(cmbKanal1);
		
		JLabel lblKanal2 = new JLabel("Kanal 2 :");
		lblKanal2.setFont(lblKanal2.getFont().deriveFont(Font.BOLD, 15f));
		lblKanal2.setBounds(panelFormDataLatih.getWidth()-220, 310, 205, 30);
		panelFormDataLatih.add(lblKanal2);
		
		cmbKanal2 = new JComboBox<>(kanal);
		cmbKanal2.setBackground(Color.white);
		cmbKanal2.setBounds(panelFormDataLatih.getWidth()-220, 340, 205, 30);
		cmbKanal2.setEnabled(false);
		panelFormDataLatih.add(cmbKanal2);
		
		btnSubmitDataEEG = new JButton("Submit");
		btnSubmitDataEEG.setForeground(Color.white);
		btnSubmitDataEEG.setBackground(new Color(60, 137, 185));
		btnSubmitDataEEG.setActionCommand("submitDataEEG");
		btnSubmitDataEEG.setBounds(15, 390, 420, 50);
		btnSubmitDataEEG.addMouseListener(new MouseController());
		btnSubmitDataEEG.addActionListener(new ButtonController());
		panelFormDataLatih.add(btnSubmitDataEEG);
		
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
			if(e.getActionCommand().equals("cekKanal2")){
				if(cmbKanal2.isEnabled() == true){
					cmbKanal2.setEnabled(false);
				}else{
					cmbKanal2.setEnabled(true);
				}
			}else if(e.getActionCommand().equals("pilihDataEEG")){
				FileNameExtensionFilter filterFile = new FileNameExtensionFilter("CSV FILE", "csv");
				inputDataEEG = new JFileChooser();
				inputDataEEG.setFileFilter(filterFile);
				inputDataEEG.setFileSelectionMode(JFileChooser.FILES_ONLY);
				inputDataEEG.setMultiSelectionEnabled(true);
				if(inputDataEEG.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					File file1[] = inputDataEEG.getSelectedFiles();
					String fullName = (String)file1[0].getName();
					lblFileDataEEG.setText(fullName);
				}
			}
		}
	}
}
