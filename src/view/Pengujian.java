package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dataLatih.DataLatih;
import lvq.LVQ;
import mysql.Database;
import wavelet.Wavelet;

public class Pengujian extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JPanel panelFormEmotiv, panelFormNeurosky, panelHasilRileks, 
					 panelHasilNonRileks, panelHasilPengujian;
	protected JButton btnPilihDataEEG, btnSubmitDataEEG;
	protected JLabel lblFileDataEEG, lblHasilPengujian, lblJumPresentaseRileks, 
					 lblJumPresentaseNonRileks,isLockedKanal1, isLockedKanal2;
	protected JComboBox<?> cmbAlatPerekaman, cmbKanal1, cmbKanal2;
	protected JTextField txtSegmentasi, txtSamplingrate, txtKanalNeurosky;
	protected JCheckBox cbGunakanKanal2;
	protected DefaultTableModel tableModel;
	protected JTable tableDataUji;
	protected JScrollPane scrollTableDataUji;
	protected DefaultTableCellRenderer centerTable;
	protected JFileChooser inputDataEEG = new JFileChooser();
	public JProgressBar progressSubmitDataEEG;
	public JLabel lblStatusLoading;
	protected String[] alatPerekaman = {"Pilih salah satu...", "Emotiv", "Neurosky"};
	protected String[] kanal = {"Pilih salah satu...", "AF3", "F7", "F3", "FC5", "T7", "P7", "O1", "O2", "P8", "T8", "FC6", "F4", "F8", "AF4"};
	protected Database dbAction;
	protected Wavelet wavelet;
	protected DataLatih dataLatih;
	protected String[] fullPathDataEEG;
	protected int i=0;
	
	public Pengujian(){
		setSize(1200, 650);
		setLayout(null);
		dbAction = new Database();
		centerTable = new DefaultTableCellRenderer();
		centerTable.setHorizontalAlignment(SwingConstants.CENTER);
		add(getContent());
		add(new Layout("Pengujian Sistem"));
		updateStatusAlatPerekaman();
		updateStatusKanal();
	}
	
	public JPanel getContent(){
		JPanel panelContent = new JPanel();
		panelContent.setBounds(280, 60, 910, 530);
		panelContent.setLayout(null);
		
		JPanel panelFormDataUji = new JPanel();
		panelFormDataUji.setLayout(null);
		panelFormDataUji.setBackground(Color.white);
		panelFormDataUji.setBounds(0, 0, 450, 360);
		
		JLabel lblTitleInputDataLatih = new JLabel("Input Data Uji");
		lblTitleInputDataLatih.setForeground(new Color(68, 68, 68));
		lblTitleInputDataLatih.setBounds(15, 0, 150, 30);
		panelFormDataUji.add(lblTitleInputDataLatih);
		
		JLabel lblPilihAlatPerekaman = new JLabel("Pilih Alat Perekaman :");
		lblPilihAlatPerekaman.setFont(lblPilihAlatPerekaman.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihAlatPerekaman.setBounds(15, 30, 200, 30);
		panelFormDataUji.add(lblPilihAlatPerekaman);
		
		cmbAlatPerekaman = new JComboBox<>(alatPerekaman);
		cmbAlatPerekaman.setBackground(Color.white);
		cmbAlatPerekaman.setBounds(15, 60, 420, 30);
		cmbAlatPerekaman.setActionCommand("cmbAlatPerekaman");
		cmbAlatPerekaman.addActionListener(new ButtonController());
		panelFormDataUji.add(cmbAlatPerekaman);
		
		JLabel lblPilihDataEEG = new JLabel("Pilih Data EEG");
		lblPilihDataEEG.setFont(lblPilihDataEEG.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihDataEEG.setBounds(15, 100, 150, 30);
		panelFormDataUji.add(lblPilihDataEEG);
		
		btnPilihDataEEG = new JButton("Pilih File");
		btnPilihDataEEG.setBackground(Color.lightGray);
		btnPilihDataEEG.setActionCommand("pilihDataEEG");
		btnPilihDataEEG.setBorderPainted(false);
		btnPilihDataEEG.addMouseListener(new MouseController());
		btnPilihDataEEG.addActionListener(new ButtonController());
		btnPilihDataEEG.setBounds(15, 130, 120, 25);
		panelFormDataUji.add(btnPilihDataEEG);
		
		lblFileDataEEG = new JLabel("Tidak ada file dipilih.");
		lblFileDataEEG.setBounds(140, 130, 300, 30);
		panelFormDataUji.add(lblFileDataEEG);
		
		JLabel lblSegmentasi = new JLabel("Segmentasi (per detik) :");
		lblSegmentasi.setFont(lblSegmentasi.getFont().deriveFont(Font.BOLD, 15f));
		lblSegmentasi.setBounds(15, 160, 205, 30);
		panelFormDataUji.add(lblSegmentasi);
		
		txtSegmentasi = new JTextField("60");
		txtSegmentasi.setBounds(15, 190, 205, 30);
		panelFormDataUji.add(txtSegmentasi);
		
		JLabel lblSamplingRate = new JLabel("Sampling rate (Hertz) :");
		lblSamplingRate.setFont(lblSamplingRate.getFont().deriveFont(Font.BOLD, 15f));
		lblSamplingRate.setBounds(panelFormDataUji.getWidth()-220, 160, 205, 30);
		panelFormDataUji.add(lblSamplingRate);
		
		txtSamplingrate = new JTextField("128");
		txtSamplingrate.setBounds(panelFormDataUji.getWidth()-220, 190, 205, 30);
		panelFormDataUji.add(txtSamplingrate);
		
		btnSubmitDataEEG = new JButton("Mulai Pengujian");
		btnSubmitDataEEG.setForeground(Color.white);
		btnSubmitDataEEG.setBackground(new Color(60, 137, 185));
		btnSubmitDataEEG.setActionCommand("submitDataEEG");
		btnSubmitDataEEG.setBounds(15, 305, 420, 40);
		btnSubmitDataEEG.addMouseListener(new MouseController());
		btnSubmitDataEEG.addActionListener(new ButtonController());
		panelFormDataUji.add(btnSubmitDataEEG);
		
		panelFormDataUji.add(panelFormEmotiv());
		panelFormDataUji.add(panelFormNeurosky());
		
		progressSubmitDataEEG = new JProgressBar();
		progressSubmitDataEEG.setBounds(15, 370, 420, 30);
		progressSubmitDataEEG.setForeground(new Color(44, 195, 107));
		progressSubmitDataEEG.setBackground(new Color(251, 252, 252));
		progressSubmitDataEEG.setStringPainted(true);
		progressSubmitDataEEG.setVisible(true);
		
		JPanel panelStatusLoading = new JPanel(new GridBagLayout());
		panelStatusLoading.setBounds(15, 400, 420, 30);
		
		lblStatusLoading = new JLabel("Loading...");
		lblStatusLoading.setVisible(false);
		panelStatusLoading.add(lblStatusLoading);
		
		JPanel panelLihatDataUji = new JPanel();
		panelLihatDataUji.setLayout(null);
		panelLihatDataUji.setBackground(Color.white);
		panelLihatDataUji.setBounds(460, 0, 450, 530);
		
		JLabel lblTitleTableDataLatih = new JLabel("Tabel Hasil Pengujian");
		lblTitleTableDataLatih.setForeground(new Color(68, 68, 68));
		lblTitleTableDataLatih.setBounds(15, 0, 180, 30);
		panelLihatDataUji.add(lblTitleTableDataLatih);
		
		JPanel panelTableDataUji = new JPanel();
		panelTableDataUji.setLayout(new BorderLayout());
		panelTableDataUji.setBackground(Color.white);
		panelTableDataUji.setBounds(15, 30, panelLihatDataUji.getWidth()-30, 490);
		panelLihatDataUji.add(panelTableDataUji);
		
		tableDataUji = new JTable();
		tableDataUji.setRowSelectionAllowed(false);
		tableDataUji.setPreferredScrollableViewportSize(getSize());
		tableDataUji.setFillsViewportHeight(true);
		
		scrollTableDataUji = new JScrollPane(tableDataUji);
		scrollTableDataUji.setVisible(true);
		panelTableDataUji.add(scrollTableDataUji, BorderLayout.CENTER);
		
		panelHasilRileks = new JPanel();
		panelHasilRileks.setLayout(null);
		panelHasilRileks.setBackground(new Color(0, 192, 239));
		panelHasilRileks.setBounds(0, 430, panelFormDataUji.getWidth()/2, 50);
		panelHasilRileks.setVisible(false);
		
		JLabel lblPresentaseRileks = new JLabel("Rileks = ");
		lblPresentaseRileks.setFont(lblPresentaseRileks.getFont().deriveFont(Font.BOLD, 15f));
		lblPresentaseRileks.setForeground(Color.white);
		lblPresentaseRileks.setBounds(10, 10, 200, 30);
		panelHasilRileks.add(lblPresentaseRileks);
		
		lblJumPresentaseRileks = new JLabel("0.0%");
		lblJumPresentaseRileks.setFont(lblJumPresentaseRileks.getFont().deriveFont(Font.BOLD, 20f));
		lblJumPresentaseRileks.setForeground(Color.white);
		lblJumPresentaseRileks.setBounds(130, 10, 200, 30);
		panelHasilRileks.add(lblJumPresentaseRileks);
		
		panelHasilNonRileks = new JPanel();
		panelHasilNonRileks.setLayout(null);
		panelHasilNonRileks.setBackground(new Color(221, 75, 57));
		panelHasilNonRileks.setBounds(panelHasilRileks.getWidth(), 430, panelFormDataUji.getWidth()/2, 50);
		panelHasilNonRileks.setVisible(false);
		
		lblJumPresentaseNonRileks = new JLabel("0.0%");
		lblJumPresentaseNonRileks.setFont(lblJumPresentaseNonRileks.getFont().deriveFont(Font.BOLD, 20f));
		lblJumPresentaseNonRileks.setForeground(Color.white);
		lblJumPresentaseNonRileks.setBounds(130, 10, 200, 30);
		panelHasilNonRileks.add(lblJumPresentaseNonRileks);
		
		JLabel lblPresentaseNonRileks = new JLabel("Non Rileks = ");
		lblPresentaseNonRileks.setFont(lblPresentaseNonRileks.getFont().deriveFont(Font.BOLD, 15f));
		lblPresentaseNonRileks.setForeground(Color.white);
		lblPresentaseNonRileks.setBounds(10, 10, 200, 30);
		panelHasilNonRileks.add(lblPresentaseNonRileks);
		
		panelHasilPengujian = new JPanel(new GridBagLayout());
		panelHasilPengujian.setBackground(new Color(0, 166, 90));
		panelHasilPengujian.setBounds(0, 480, panelFormDataUji.getWidth(), 50);
		panelHasilPengujian.setVisible(false);
		
		lblHasilPengujian = new JLabel("-");
		lblHasilPengujian.setFont(lblHasilPengujian.getFont().deriveFont(Font.BOLD, 23f));
		lblHasilPengujian.setForeground(Color.white);
		panelHasilPengujian.add(lblHasilPengujian);
		
		panelContent.add(panelFormDataUji);
		panelContent.add(panelLihatDataUji);
		panelContent.add(progressSubmitDataEEG);
		panelContent.add(panelStatusLoading);
		panelContent.add(panelHasilRileks);
		panelContent.add(panelHasilNonRileks);
		panelContent.add(panelHasilPengujian);
		return panelContent;
	}
	
	public JPanel panelFormEmotiv(){
		panelFormEmotiv = new JPanel();
		panelFormEmotiv.setLayout(null);
		panelFormEmotiv.setBounds(0, 0, 450, 460);
		panelFormEmotiv.setBackground(Color.white);
		panelFormEmotiv.setVisible(false);
		
		cbGunakanKanal2 = new JCheckBox("Gunakan Kanal 2", false);
		cbGunakanKanal2.setBackground(Color.white);
		cbGunakanKanal2.setBounds(panelFormEmotiv.getWidth()-220, 300, 205, 30);
		cbGunakanKanal2.setActionCommand("cekKanal2");
		cbGunakanKanal2.addActionListener(new ButtonController());
		cbGunakanKanal2.setVisible(false);
		panelFormEmotiv.add(cbGunakanKanal2);
		
		isLockedKanal1 = new JLabel("Dikunci!");
		isLockedKanal1.setFont(isLockedKanal1.getFont().deriveFont(Font.BOLD, 15f));
		isLockedKanal1.setForeground(Color.red);
		isLockedKanal1.setBounds(90, 230, 150, 30);
		panelFormEmotiv.add(isLockedKanal1);
		
		JLabel lblKanal1 = new JLabel("Kanal 1 :");
		lblKanal1.setFont(lblKanal1.getFont().deriveFont(Font.BOLD, 15f));
		lblKanal1.setBounds(15, 230, 205, 30);
		panelFormEmotiv.add(lblKanal1);
		
		cmbKanal1 = new JComboBox<>(kanal);
		cmbKanal1.setBackground(Color.white);
		cmbKanal1.setBounds(15, 260, 205, 30);
		panelFormEmotiv.add(cmbKanal1);
		
		isLockedKanal2 = new JLabel("Dikunci!");
		isLockedKanal2.setFont(isLockedKanal2.getFont().deriveFont(Font.BOLD, 15f));
		isLockedKanal2.setForeground(Color.red);
		isLockedKanal2.setBounds(305, 230, 150, 30);
		panelFormEmotiv.add(isLockedKanal2);
		
		JLabel lblKanal2 = new JLabel("Kanal 2 :");
		lblKanal2.setFont(lblKanal2.getFont().deriveFont(Font.BOLD, 15f));
		lblKanal2.setBounds(panelFormEmotiv.getWidth()-220, 230, 205, 30);
		panelFormEmotiv.add(lblKanal2);
		
		cmbKanal2 = new JComboBox<>(kanal);
		cmbKanal2.setBackground(Color.white);
		cmbKanal2.setBounds(panelFormEmotiv.getWidth()-220, 260, 205, 30);
		cmbKanal2.setEnabled(false);
		panelFormEmotiv.add(cmbKanal2);
		
		return panelFormEmotiv;
	}
	
	public JPanel panelFormNeurosky(){
		panelFormNeurosky = new JPanel();
		panelFormNeurosky.setLayout(null);
		panelFormNeurosky.setBounds(0, 0, 450, 460);
		panelFormNeurosky.setBackground(Color.white);
		panelFormNeurosky.setVisible(false);
		
		JLabel lblKanal1 = new JLabel("Kanal :");
		lblKanal1.setFont(lblKanal1.getFont().deriveFont(Font.BOLD, 15f));
		lblKanal1.setBounds(15, 230, 205, 30);
		panelFormNeurosky.add(lblKanal1);
		
		txtKanalNeurosky = new JTextField("FP1");
		txtKanalNeurosky.setBounds(15, 260, 420, 30);
		txtKanalNeurosky.setEnabled(false);
		panelFormNeurosky.add(txtKanalNeurosky);
		
		return panelFormNeurosky;
	}
	
	public void changeSettingAlatPerekaman(int jenisAlat){
		if(jenisAlat == 0){
			resetFormDataUji();
			btnPilihDataEEG.setEnabled(false);
			btnSubmitDataEEG.setEnabled(false);
			panelFormEmotiv.setVisible(false);
			panelFormNeurosky.setVisible(false);
			txtSamplingrate.setText("");
			cbGunakanKanal2.setSelected(false);
			cmbKanal2.setEnabled(false);
		}else if(jenisAlat == 1){
			resetFormDataUji();
			updateStatusKanal();
			btnPilihDataEEG.setEnabled(true);
			btnSubmitDataEEG.setEnabled(true);
			panelFormEmotiv.setVisible(true);
			panelFormNeurosky.setVisible(false);
			txtSamplingrate.setText("128");
			cbGunakanKanal2.setSelected(false);
			cmbKanal2.setEnabled(false);
		}else if(jenisAlat == 2){
			resetFormDataUji();
			btnPilihDataEEG.setEnabled(true);
			btnSubmitDataEEG.setEnabled(true);
			panelFormEmotiv.setVisible(false);
			panelFormNeurosky.setVisible(true);
			txtSamplingrate.setText("512");
			cbGunakanKanal2.setSelected(false);
			cmbKanal2.setEnabled(false);
		}
	}
	
	public void updateStatusAlatPerekaman(){
		if(dbAction.getAlatPerekaman() != null){
			int indexAlat = 0;
			switch (dbAction.getAlatPerekaman()) {
				case "Emotiv": indexAlat = 1;break;
				case "Neurosky": indexAlat = 2;break;
				default: indexAlat = 0;break;
			};
			changeSettingAlatPerekaman(indexAlat);
			cmbAlatPerekaman.setSelectedItem(dbAction.getAlatPerekaman());
			cmbAlatPerekaman.setEnabled(false);
		}
	}
	
	public DefaultTableModel initTableModelPengujian(String[] kondisi){
		DefaultTableModel tableModel = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

		    @SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
		    public Class getColumnClass(int column) {
		        return getValueAt(0, column).getClass();
		    }

		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		tableModel.addColumn("Segmentasi");
		tableModel.addColumn("Kondisi");
		
		for(i=0;i<kondisi.length;i++){
			Object[] data = new Object[2];
			data[0] = (i+1);
			data[1] = kondisi[i];
			tableModel.addRow(data);
		}
		return tableModel;
	}
	
	public void updateTablePengujian(DefaultTableModel tableModel){
		tableDataUji.setModel(tableModel);
		tableDataUji.repaint();
		tableDataUji.setRowSelectionAllowed(false);
		tableDataUji.setPreferredScrollableViewportSize(getSize());
		tableDataUji.setFillsViewportHeight(true);
		tableDataUji.getColumnModel().getColumn(0).setCellRenderer(centerTable);
		tableDataUji.getColumnModel().getColumn(1).setCellRenderer(centerTable);
	}
	
	public void resetFormDataUji(){
		fullPathDataEEG = null;
		lblFileDataEEG.setText("No file chosen");
		txtSegmentasi.setText("60");
		txtSamplingrate.setText("");
		cmbKanal1.setSelectedIndex(0);
		cbGunakanKanal2.setSelected(false);
		cmbKanal2.setSelectedIndex(0);
		cmbKanal2.setEnabled(false);
	}
	
	public void updateStatusPengujian(double jumRileks, double jumNonRileks, double jumDataUji){
		double totalRileks, totalNonRileks;
		totalRileks = (jumRileks/jumDataUji)*100;
		totalNonRileks = (jumNonRileks/jumDataUji)*100;
		
		lblJumPresentaseRileks.setText(Double.toString(totalRileks)+"%");
		lblJumPresentaseNonRileks.setText(Double.toString(totalNonRileks)+"%");
		if(totalRileks >= totalNonRileks){
			lblHasilPengujian.setText("Rileks");
		}else{
			lblHasilPengujian.setText("Non-Rileks");
		}
	}
	
	public void updateStatusKanal(){
		int[] kanal;
		if(dbAction.getKanal() != null){
			kanal = dbAction.getKanal();
			if(kanal.length == 2){
				cmbKanal1.setSelectedIndex(kanal[0]-1);
				cmbKanal1.setEnabled(false);
				cmbKanal2.setSelectedIndex(kanal[1]-1);
				cmbKanal2.setEnabled(false);
				cbGunakanKanal2.setSelected(true);
				cbGunakanKanal2.setVisible(false);
			}else{
				cmbKanal1.setSelectedIndex(kanal[0]-1);
				cmbKanal1.setEnabled(false);
				cbGunakanKanal2.setSelected(false);
				cbGunakanKanal2.setVisible(false);
			}
			isLockedKanal1.setVisible(true);
			isLockedKanal2.setVisible(true);
		}else{
			resetFormDataUji();
			cbGunakanKanal2.setVisible(true);
			isLockedKanal1.setVisible(false);
			isLockedKanal2.setVisible(false);
		}
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
			}else if(e.getActionCommand().equals("cmbAlatPerekaman")){
				if((String)cmbAlatPerekaman.getSelectedItem() == "Pilih salah satu..."){
					changeSettingAlatPerekaman(0);
				}else if((String)cmbAlatPerekaman.getSelectedItem() == "Emotiv"){
					changeSettingAlatPerekaman(1);
				}else if((String)cmbAlatPerekaman.getSelectedItem() == "Neurosky"){
					changeSettingAlatPerekaman(2);
				}
			}else if(e.getActionCommand().equals("pilihDataEEG")){
				if((String)cmbAlatPerekaman.getSelectedItem() == "Emotiv"){
					FileNameExtensionFilter filterFile = new FileNameExtensionFilter("CSV FILE", "csv");
					inputDataEEG.setFileFilter(filterFile);
				}else if((String)cmbAlatPerekaman.getSelectedItem() == "Neurosky"){
					FileNameExtensionFilter filterFile = new FileNameExtensionFilter("TXT FILE", "txt");
					inputDataEEG.setFileFilter(filterFile);
				}
				inputDataEEG.setCurrentDirectory(inputDataEEG.getCurrentDirectory());
				inputDataEEG.setFileSelectionMode(JFileChooser.FILES_ONLY);
				inputDataEEG.setMultiSelectionEnabled(true);
				if(inputDataEEG.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					File file1[] = inputDataEEG.getSelectedFiles();
					String fullName = (String)file1[0].getName();
					fullPathDataEEG = new String[file1.length];
					if(file1.length > 1){
						lblFileDataEEG.setText(file1.length+" files");
						for(int i=0; i<file1.length; i++){
							fullPathDataEEG[i] = (String)file1[i].getAbsolutePath();
						}
					}else{
						lblFileDataEEG.setText(fullName);
						fullPathDataEEG[0] = (String)file1[0].getAbsolutePath();
					}
				}
			}else if(e.getActionCommand().equals("submitDataEEG")){
				if(fullPathDataEEG == null){
					JOptionPane.showMessageDialog(null, "Sinyal EEG belum dipilih", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(txtSegmentasi == null){
					JOptionPane.showMessageDialog(null, "Segmentasi tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(txtSamplingrate == null){
					JOptionPane.showMessageDialog(null, "Sampling Rate tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(!dbAction.isBobotNotNull()){
					JOptionPane.showMessageDialog(null, "Belum Melakukan Pelatihan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
					Home.changeCard("panelPelatihanSistem");
				}else{
					if((String)cmbAlatPerekaman.getSelectedItem() == "Emotiv"){
						if((String)cmbKanal1.getSelectedItem() == "Pilih salah satu..."){
							JOptionPane.showMessageDialog(null, "Pilihan Kanal 1 tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
						}else if(cmbKanal2.isEnabled() == true && (String)cmbKanal2.getSelectedItem() == "Pilih salah satu..."){
							JOptionPane.showMessageDialog(null, "Pilihan Kanal 2 tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
						}
					}
					
					if(cbGunakanKanal2.isSelected() == false){
						String kanal = null;
						if((String)cmbAlatPerekaman.getSelectedItem() == "Emotiv"){
							kanal = (String)cmbKanal1.getSelectedItem();
						}else if((String)cmbAlatPerekaman.getSelectedItem() == "Neurosky"){
							kanal = (String)txtKanalNeurosky.getText();
						}
						dataLatih = new DataLatih(fullPathDataEEG, null, Integer.parseInt(txtSegmentasi.getText()), Integer.parseInt(txtSamplingrate.getText()), kanal, null, (String)cmbAlatPerekaman.getSelectedItem());
					}else{
						dataLatih = new DataLatih(fullPathDataEEG, null, Integer.parseInt(txtSegmentasi.getText()), Integer.parseInt(txtSamplingrate.getText()), (String)cmbKanal1.getSelectedItem(), (String)cmbKanal2.getSelectedItem(), (String)cmbAlatPerekaman.getSelectedItem());
					}
					CorePengujianSistem corePengujianSistem = new CorePengujianSistem(dataLatih);
					corePengujianSistem.execute();
				}
			}
		}
	}
	
	class CorePengujianSistem extends SwingWorker<Void, Void>{
		
		Wavelet wavelet = new Wavelet();
		DataLatih dataLatih;
		
		public CorePengujianSistem(DataLatih dataLatih) {
			// TODO Auto-generated constructor stub
			this.dataLatih = dataLatih;
			lblStatusLoading.setVisible(true);
		}

		@Override
		protected Void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			String[] hasilPengujian;
			String[][] sinyalKanal1, sinyalKanal2, sinyalFull, sinyalTemp;
			double[][] bobotPelatihan;
			LVQ lvq = new LVQ();
			wavelet = new Wavelet();
			int i, itemp=0, j, progress=0, progressDistance = 70/dataLatih.pathFile.length;
			if(dataLatih.kanal2 == null){
				sinyalFull = new String[dataLatih.pathFile.length][dataLatih.samplingRate*dataLatih.segmentasi];
			}else{
				sinyalFull = new String[dataLatih.pathFile.length][dataLatih.samplingRate*dataLatih.segmentasi*2];
			}
			for(i=0; i<dataLatih.pathFile.length; i++){
				lblStatusLoading.setText("membaca EEG signal ke "+(i+1));
				progressSubmitDataEEG.setValue(progress+=progressDistance);
				
				try {
					dataLatih.dataEeg = dataLatih.readFile(dataLatih.pathFile[i]);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(dataLatih.kanal2 == null){
					sinyalKanal1 = new String[(int) Math.floor(dataLatih.dataEeg.getItemCount()/(dataLatih.samplingRate*dataLatih.segmentasi))][dataLatih.dataEeg.getItemCount()-1];
					sinyalKanal1 = dataLatih.segmentasiEEG(dataLatih.dataEeg, dataLatih.kanalToInt(dataLatih.kanal1), dataLatih.segmentasi, dataLatih.samplingRate, dataLatih.alatPerekaman);
					for(j=0;j<sinyalKanal1.length;j++){
						if(itemp < dataLatih.pathFile.length){
							sinyalFull[itemp] = sinyalKanal1[j];
							itemp++;
						}
					}
				}else{
					sinyalKanal1 = new String[(int) Math.floor(dataLatih.dataEeg.getItemCount()/(dataLatih.samplingRate*dataLatih.segmentasi))][dataLatih.dataEeg.getItemCount()-1];
					sinyalKanal1 = dataLatih.segmentasiEEG(dataLatih.dataEeg, dataLatih.kanalToInt(dataLatih.kanal1), dataLatih.segmentasi, dataLatih.samplingRate, dataLatih.alatPerekaman);
					sinyalKanal2 = new String[(int) Math.floor(dataLatih.dataEeg.getItemCount()/(dataLatih.samplingRate*dataLatih.segmentasi))][dataLatih.dataEeg.getItemCount()-1];
					sinyalKanal2 = dataLatih.segmentasiEEG(dataLatih.dataEeg, dataLatih.kanalToInt(dataLatih.kanal2), dataLatih.segmentasi, dataLatih.samplingRate, dataLatih.alatPerekaman);
					sinyalTemp = dataLatih.gabungkanArray(sinyalKanal1, sinyalKanal2);
					for(j=0;j<sinyalTemp.length;j++){
						if(itemp < dataLatih.pathFile.length){
							sinyalFull[itemp] = sinyalTemp[j];
							itemp++;
						}
					}
				}
			}
			lblStatusLoading.setText("Proses Pengujian dengan LVQ");
			progressSubmitDataEEG.setValue(80);
			bobotPelatihan = dbAction.getBobotPelatihan();
			hasilPengujian = lvq.pengujian(bobotPelatihan[0], bobotPelatihan[1], wavelet.getNeuronPengujian(lvq.string2DtoDouble(sinyalFull)));
			lblStatusLoading.setText("Update Tabel Bobot");
			progressSubmitDataEEG.setValue(90);
			updateTablePengujian(initTableModelPengujian(hasilPengujian));
			updateStatusPengujian((double)lvq.getJumlahHasilUjiRileks(hasilPengujian), (double)lvq.getJumlahHasilUjiNonRileks(hasilPengujian), (double)hasilPengujian.length);
			return null;
		}
		
		@Override
		public void done(){
			progressSubmitDataEEG.setValue(100);
			resetFormDataUji();
			updateStatusAlatPerekaman();
			Home.refreshAllElement();
			updateStatusKanal();
			JOptionPane.showMessageDialog(null, "Proses Pengujian Berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
			lblStatusLoading.setVisible(false);
			progressSubmitDataEEG.setValue(0);
			panelHasilRileks.setVisible(true);
			panelHasilNonRileks.setVisible(true);
			panelHasilPengujian.setVisible(true);
		}
	}
}
