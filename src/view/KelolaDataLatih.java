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
import mysql.Database;
import wavelet.Wavelet;

public class KelolaDataLatih extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JPanel panelFormEmotiv, panelFormNeurosky;
	protected JButton btnPilihDataEEG, btnSubmitDataEEG;
	protected JLabel lblFileDataEEG, isLockedKanal1, isLockedKanal2;
	protected JComboBox<?> cmbAlatPerekaman, cmbKelas, cmbKanal1, cmbKanal2;
	protected JTextField txtSegmentasi, txtSamplingrate, txtKanalNeurosky;
	protected JCheckBox cbGunakanKanal2;
	protected DefaultTableModel tableModel;
	protected JTable tableDataLatih;
	protected JScrollPane scrollTableDataLatih;
	protected DefaultTableCellRenderer centerTable;
	protected JFileChooser inputDataEEG = new JFileChooser();
	public JProgressBar progressSubmitDataEEG;
	public JLabel lblStatusLoading;
	protected String[] alatPerekaman = {"Pilih salah satu...", "Emotiv", "Neurosky"};
	protected String[] kelas = {"Pilih salah satu...", "Rileks", "Non-Rileks"};
	protected String[] kanal = {"Pilih salah satu...", "AF3", "F7", "F3", "FC5", "T7", "P7", "O1", "O2", "P8", "T8", "FC6", "F4", "F8", "AF4"};
	protected Database dbAction;
	protected Wavelet wavelet;
	protected DataLatih dataLatih;
	protected String[] fullPathDataEEG;
	protected EditDataLatih editDataLatih = new EditDataLatih();
	
	public KelolaDataLatih(){
		setSize(1200, 650);
		setLayout(null);
		dbAction = new Database();
		tableModel = dbAction.getListDataLatih();
		centerTable = new DefaultTableCellRenderer();
		centerTable.setHorizontalAlignment(SwingConstants.CENTER);
		add(getContent());
		add(new Layout("Kelola Data Latih"));
		updateStatusKanal();
	}
	
	public JPanel getContent(){
		JPanel panelContent = new JPanel();
		panelContent.setBounds(280, 60, 910, 530);
		panelContent.setLayout(null);
		
		JPanel panelFormDataLatih = new JPanel();
		panelFormDataLatih.setLayout(null);
		panelFormDataLatih.setBackground(Color.white);
		panelFormDataLatih.setBounds(0, 0, 450, 460);
		
		JLabel lblTitleInputDataLatih = new JLabel("Input Data latih");
		lblTitleInputDataLatih.setForeground(new Color(68, 68, 68));
		lblTitleInputDataLatih.setBounds(15, 0, 150, 30);
		panelFormDataLatih.add(lblTitleInputDataLatih);
		
		JLabel lblPilihAlatPerekaman = new JLabel("Pilih Alat Perekaman :");
		lblPilihAlatPerekaman.setFont(lblPilihAlatPerekaman.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihAlatPerekaman.setBounds(15, 30, 200, 30);
		panelFormDataLatih.add(lblPilihAlatPerekaman);
		
		cmbAlatPerekaman = new JComboBox<>(alatPerekaman);
		cmbAlatPerekaman.setBackground(Color.white);
		cmbAlatPerekaman.setBounds(15, 60, 420, 30);
		cmbAlatPerekaman.setActionCommand("cmbAlatPerekaman");
		cmbAlatPerekaman.addActionListener(new ButtonController());
		panelFormDataLatih.add(cmbAlatPerekaman);
		
		JLabel lblPilihDataEEG = new JLabel("Pilih Data EEG :");
		lblPilihDataEEG.setFont(lblPilihDataEEG.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihDataEEG.setBounds(15, 100, 150, 30);
		panelFormDataLatih.add(lblPilihDataEEG);
		
		btnPilihDataEEG = new JButton("Pilih File");
		btnPilihDataEEG.setBackground(Color.lightGray);
		btnPilihDataEEG.setActionCommand("pilihDataEEG");
		btnPilihDataEEG.setBorderPainted(false);
		btnPilihDataEEG.addMouseListener(new MouseController());
		btnPilihDataEEG.addActionListener(new ButtonController());
		btnPilihDataEEG.setBounds(15, 130, 120, 25);
		btnPilihDataEEG.setEnabled(false);
		panelFormDataLatih.add(btnPilihDataEEG);
		
		lblFileDataEEG = new JLabel("Tidak ada file dipilih.");
		lblFileDataEEG.setBounds(140, 130, 300, 30);
		panelFormDataLatih.add(lblFileDataEEG);
			
		JLabel lblKelas = new JLabel("Kelas :");
		lblKelas.setFont(lblKelas.getFont().deriveFont(Font.BOLD, 15f));
		lblKelas.setBounds(15, 160, 110, 30);
		panelFormDataLatih.add(lblKelas);
		
		cmbKelas = new JComboBox<>(kelas);
		cmbKelas.setBackground(Color.white);
		cmbKelas.setBounds(15, 190, 420, 30);
		panelFormDataLatih.add(cmbKelas);
		
		JLabel lblSegmentasi = new JLabel("Segmentasi (per detik) :");
		lblSegmentasi.setFont(lblSegmentasi.getFont().deriveFont(Font.BOLD, 15f));
		lblSegmentasi.setBounds(15, 230, 205, 30);
		panelFormDataLatih.add(lblSegmentasi);
		
		txtSegmentasi = new JTextField("60");
		txtSegmentasi.setBounds(15, 260, 205, 30);
		panelFormDataLatih.add(txtSegmentasi);
		
		JLabel lblSamplingRate = new JLabel("Sampling rate (Hertz) :");
		lblSamplingRate.setFont(lblSamplingRate.getFont().deriveFont(Font.BOLD, 15f));
		lblSamplingRate.setBounds(panelFormDataLatih.getWidth()-220, 230, 205, 30);
		panelFormDataLatih.add(lblSamplingRate);
		
		txtSamplingrate = new JTextField();
		txtSamplingrate.setBounds(panelFormDataLatih.getWidth()-220, 260, 205, 30);
		txtSamplingrate.setEnabled(false);
		panelFormDataLatih.add(txtSamplingrate);
		
		btnSubmitDataEEG = new JButton("Mulai");
		btnSubmitDataEEG.setForeground(Color.white);
		btnSubmitDataEEG.setBackground(new Color(60, 137, 185));
		btnSubmitDataEEG.setActionCommand("submitDataEEG");
		btnSubmitDataEEG.setBounds(15, 405, 420, 40);
		btnSubmitDataEEG.addMouseListener(new MouseController());
		btnSubmitDataEEG.addActionListener(new ButtonController());
		btnSubmitDataEEG.setVisible(true);
		btnSubmitDataEEG.setEnabled(false);
		panelFormDataLatih.add(btnSubmitDataEEG);
		
		progressSubmitDataEEG = new JProgressBar();
		progressSubmitDataEEG.setBounds(15, 470, 420, 30);
		progressSubmitDataEEG.setForeground(new Color(44, 195, 107));
		progressSubmitDataEEG.setBackground(new Color(251, 252, 252));
		progressSubmitDataEEG.setStringPainted(true);
		progressSubmitDataEEG.setVisible(true);
		
		panelFormDataLatih.add(panelFormEmotiv());
		panelFormDataLatih.add(panelFormNeurosky());
		
		JPanel panelStatusLoading = new JPanel(new GridBagLayout());
		panelStatusLoading.setBounds(15, 500, 420, 30);
		
		lblStatusLoading = new JLabel("Loading...");
		lblStatusLoading.setVisible(false);
		panelStatusLoading.add(lblStatusLoading);
		
		JPanel panelLihatDataLatih = new JPanel();
		panelLihatDataLatih.setLayout(null);
		panelLihatDataLatih.setBackground(Color.white);
		panelLihatDataLatih.setBounds(460, 0, 450, 460);
		
		JLabel lblTitleTableDataLatih = new JLabel("Tabel Data Latih");
		lblTitleTableDataLatih.setForeground(new Color(68, 68, 68));
		lblTitleTableDataLatih.setBounds(15, 0, 150, 30);
		panelLihatDataLatih.add(lblTitleTableDataLatih);
		
		JPanel panelTableDataLatih = new JPanel();
		panelTableDataLatih.setLayout(new BorderLayout());
		panelTableDataLatih.setBackground(Color.white);
		panelTableDataLatih.setBounds(15, 30, panelLihatDataLatih.getWidth()-30, 415);
		panelLihatDataLatih.add(panelTableDataLatih);
		
		tableDataLatih = new JTable(tableModel);
		tableDataLatih.setRowSelectionAllowed(false);
		tableDataLatih.setPreferredScrollableViewportSize(getSize());
		tableDataLatih.setFillsViewportHeight(true);
		tableDataLatih.getColumnModel().getColumn(0).setCellRenderer(centerTable);
		tableDataLatih.getColumnModel().getColumn(2).setCellRenderer(centerTable);
		tableDataLatih.getColumnModel().getColumn(3).setCellRenderer(centerTable);
		tableDataLatih.getColumnModel().getColumn(4).setCellRenderer(centerTable);
		tableDataLatih.getColumnModel().getColumn(0).setMinWidth(50);
		tableDataLatih.getColumnModel().getColumn(0).setMaxWidth(50);
		tableDataLatih.getColumnModel().getColumn(2).setMinWidth(80);
		tableDataLatih.getColumnModel().getColumn(2).setMaxWidth(80);
		tableDataLatih.getColumnModel().getColumn(3).setMinWidth(70);
		tableDataLatih.getColumnModel().getColumn(3).setMaxWidth(70);
		tableDataLatih.getColumnModel().getColumn(4).setMinWidth(80);
		tableDataLatih.getColumnModel().getColumn(4).setMaxWidth(80);
		
		scrollTableDataLatih = new JScrollPane(tableDataLatih);
		scrollTableDataLatih.setVisible(true);
		panelTableDataLatih.add(scrollTableDataLatih, BorderLayout.CENTER);
		
		JPanel panelEditDataLatih = new JPanel();
		panelEditDataLatih.setLayout(null);
		panelEditDataLatih.setBackground(Color.white);
		panelEditDataLatih.setBounds(panelLihatDataLatih.getX(), panelLihatDataLatih.getHeight()+10, panelLihatDataLatih.getWidth(), 60);
		
		JButton btnEditDataLatih = new JButton("Edit Data Latih");
		btnEditDataLatih.setActionCommand("btnEditDataLatih");
		btnEditDataLatih.setBackground(new Color(60, 137, 185));
		btnEditDataLatih.setForeground(Color.white);
		btnEditDataLatih.setBorderPainted(false);
		btnEditDataLatih.addMouseListener(new MouseController());
		btnEditDataLatih.addActionListener(new ButtonController());
		btnEditDataLatih.setBounds(15, 15, panelEditDataLatih.getWidth()-(15*2), panelEditDataLatih.getHeight()-(15*2));
		panelEditDataLatih.add(btnEditDataLatih);
		
		panelContent.add(panelFormDataLatih);
		panelContent.add(panelLihatDataLatih);
		panelContent.add(progressSubmitDataEEG);
		panelContent.add(panelStatusLoading);
		panelContent.add(panelEditDataLatih);
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
		panelFormEmotiv.add(cbGunakanKanal2);
		
		isLockedKanal1 = new JLabel("Dikunci!");
		isLockedKanal1.setFont(isLockedKanal1.getFont().deriveFont(Font.BOLD, 15f));
		isLockedKanal1.setForeground(Color.red);
		isLockedKanal1.setBounds(90, 330, 150, 30);
		panelFormEmotiv.add(isLockedKanal1);
		
		JLabel lblKanal1 = new JLabel("Kanal 1 :");
		lblKanal1.setFont(lblKanal1.getFont().deriveFont(Font.BOLD, 15f));
		lblKanal1.setBounds(15, 330, 205, 30);
		panelFormEmotiv.add(lblKanal1);
		
		cmbKanal1 = new JComboBox<>(kanal);
		cmbKanal1.setBackground(Color.white);
		cmbKanal1.setBounds(15, 360, 205, 30);
		panelFormEmotiv.add(cmbKanal1);
		
		isLockedKanal2 = new JLabel("Dikunci!");
		isLockedKanal2.setFont(isLockedKanal2.getFont().deriveFont(Font.BOLD, 15f));
		isLockedKanal2.setForeground(Color.red);
		isLockedKanal2.setBounds(305, 330, 150, 30);
		panelFormEmotiv.add(isLockedKanal2);
		
		JLabel lblKanal2 = new JLabel("Kanal 2 :");
		lblKanal2.setFont(lblKanal2.getFont().deriveFont(Font.BOLD, 15f));
		lblKanal2.setBounds(panelFormEmotiv.getWidth()-220, 330, 205, 30);
		panelFormEmotiv.add(lblKanal2);
		
		cmbKanal2 = new JComboBox<>(kanal);
		cmbKanal2.setBackground(Color.white);
		cmbKanal2.setBounds(panelFormEmotiv.getWidth()-220, 360, 205, 30);
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
		lblKanal1.setBounds(15, 310, 205, 30);
		panelFormNeurosky.add(lblKanal1);
		
		txtKanalNeurosky = new JTextField("FP1");
		txtKanalNeurosky.setBounds(15, 340, 420, 30);
		txtKanalNeurosky.setEnabled(false);
		panelFormNeurosky.add(txtKanalNeurosky);
		
		return panelFormNeurosky;
	}
	
	public void updateTableDataLatih(){
		tableDataLatih.setModel(dbAction.getListDataLatih());
		tableDataLatih.repaint();
		tableDataLatih.setRowSelectionAllowed(false);
		tableDataLatih.setPreferredScrollableViewportSize(getSize());
		tableDataLatih.setFillsViewportHeight(true);
		tableDataLatih.getColumnModel().getColumn(0).setCellRenderer(centerTable);
		tableDataLatih.getColumnModel().getColumn(2).setCellRenderer(centerTable);
		tableDataLatih.getColumnModel().getColumn(3).setCellRenderer(centerTable);
		tableDataLatih.getColumnModel().getColumn(4).setCellRenderer(centerTable);
		tableDataLatih.getColumnModel().getColumn(0).setMinWidth(50);
		tableDataLatih.getColumnModel().getColumn(0).setMaxWidth(50);
		tableDataLatih.getColumnModel().getColumn(2).setMinWidth(80);
		tableDataLatih.getColumnModel().getColumn(2).setMaxWidth(80);
		tableDataLatih.getColumnModel().getColumn(3).setMinWidth(70);
		tableDataLatih.getColumnModel().getColumn(3).setMaxWidth(70);
		tableDataLatih.getColumnModel().getColumn(4).setMinWidth(80);
		tableDataLatih.getColumnModel().getColumn(4).setMaxWidth(80);
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
			resetFormDataLatih();
			cbGunakanKanal2.setVisible(true);
			isLockedKanal1.setVisible(false);
			isLockedKanal2.setVisible(false);
		}
	}
	
	public void changeSettingAlatPerekaman(String jenisAlat){
		if(jenisAlat == "kosong"){
			btnPilihDataEEG.setEnabled(false);
			btnSubmitDataEEG.setEnabled(false);
			panelFormEmotiv.setVisible(false);
			panelFormNeurosky.setVisible(false);
			txtSamplingrate.setText("");
			cbGunakanKanal2.setSelected(false);
			cmbKanal2.setEnabled(false);
		}else if(jenisAlat == "emotiv"){
			btnPilihDataEEG.setEnabled(true);
			btnSubmitDataEEG.setEnabled(true);
			panelFormEmotiv.setVisible(true);
			panelFormNeurosky.setVisible(false);
			txtSamplingrate.setText("128");
			cbGunakanKanal2.setSelected(false);
			cmbKanal2.setEnabled(false);
		}else if(jenisAlat == "neurosky"){
			btnPilihDataEEG.setEnabled(true);
			btnSubmitDataEEG.setEnabled(true);
			panelFormEmotiv.setVisible(false);
			panelFormNeurosky.setVisible(true);
			txtSamplingrate.setText("512");
			cbGunakanKanal2.setSelected(false);
			cmbKanal2.setEnabled(false);
		}
	}
	
	public void resetFormDataLatih(){
		fullPathDataEEG = null;
		lblFileDataEEG.setText("Tidak ada file dipilih.");
		cmbKelas.setSelectedIndex(0);
		txtSegmentasi.setText("60");;
		txtSamplingrate.setText("128");;
		cmbKanal1.setSelectedIndex(0);
		cbGunakanKanal2.setSelected(false);
		cmbKanal2.setSelectedIndex(0);
		cmbKanal2.setEnabled(false);
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
			if(e.getActionCommand().equals("cmbAlatPerekaman")){
				if((String)cmbAlatPerekaman.getSelectedItem() == "Pilih salah satu..."){
					changeSettingAlatPerekaman("kosong");
				}else if((String)cmbAlatPerekaman.getSelectedItem() == "Emotiv"){
					changeSettingAlatPerekaman("emotiv");
				}else if((String)cmbAlatPerekaman.getSelectedItem() == "Neurosky"){
					changeSettingAlatPerekaman("neurosky");
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
			}else if(e.getActionCommand().equals("cekKanal2")){
				if(cmbKanal2.isEnabled() == true){
					cmbKanal2.setEnabled(false);
				}else{
					cmbKanal2.setEnabled(true);
				}
			}else if(e.getActionCommand().equals("submitDataEEG")){
				if(fullPathDataEEG == null){
					JOptionPane.showMessageDialog(null, "Sinyal EEG belum dipilih", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if((String)cmbKelas.getSelectedItem() == "Pilih salah satu..."){
					JOptionPane.showMessageDialog(null, "Pilihan kelas tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(txtSegmentasi == null){
					JOptionPane.showMessageDialog(null, "Segmentasi tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(txtSamplingrate == null){
					JOptionPane.showMessageDialog(null, "Sampling Rate tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
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
						dataLatih = new DataLatih(fullPathDataEEG, (String)cmbKelas.getSelectedItem(), Integer.parseInt(txtSegmentasi.getText()), Integer.parseInt(txtSamplingrate.getText()), kanal, null, (String)cmbAlatPerekaman.getSelectedItem());
						CoreKelolaDataLatih coreKelolaDataLatih = new CoreKelolaDataLatih(dataLatih);
						coreKelolaDataLatih.execute();
					}else{
						dataLatih = new DataLatih(fullPathDataEEG, (String)cmbKelas.getSelectedItem(), Integer.parseInt(txtSegmentasi.getText()), Integer.parseInt(txtSamplingrate.getText()), (String)cmbKanal1.getSelectedItem(), (String)cmbKanal2.getSelectedItem(), (String)cmbAlatPerekaman.getSelectedItem());
						CoreKelolaDataLatih coreKelolaDataLatih = new CoreKelolaDataLatih(dataLatih);
						coreKelolaDataLatih.execute();
					}
				}
			}else if(e.getActionCommand().equals("btnEditDataLatih")){
				Home.changeCard("panelEditDataLatih");
			}
		}
	}
	
	class CoreKelolaDataLatih extends SwingWorker<Void, Void> {
		
		DataLatih dataLatih;
		
		public CoreKelolaDataLatih(DataLatih dataLatih) {
			// TODO Auto-generated constructor stub
			this.dataLatih = dataLatih;
			lblStatusLoading.setVisible(true);
		}

		@Override
		protected Void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			String[][] sinyalKanal1, sinyalKanal2, kanalMerge;
			int naracoba = dbAction.getJumNaracoba()+1;
			String kanal = null;
			int i, progress=0, progressDistance = 100/dataLatih.pathFile.length;
			for(i=0; i<dataLatih.pathFile.length; i++){
				lblStatusLoading.setText("membaca EEG signal ke "+(i+1));
				progressSubmitDataEEG.setValue(progress+=progressDistance);
				
				dataLatih.dataEeg = dataLatih.readFile(dataLatih.pathFile[i]);
				if(dataLatih.kanal2 == null){
					sinyalKanal1 = new String[(int) Math.floor(dataLatih.dataEeg.getItemCount()/(dataLatih.samplingRate*dataLatih.segmentasi))][dataLatih.dataEeg.getItemCount()-1];
					sinyalKanal1 = dataLatih.segmentasiEEG(dataLatih.dataEeg, dataLatih.kanalToInt(dataLatih.kanal1), dataLatih.segmentasi, dataLatih.samplingRate, dataLatih.alatPerekaman);
					kanal = Integer.toString(dataLatih.kanalToInt(dataLatih.kanal1));
					dbAction.inputSegmentasiSinyal(sinyalKanal1, dataLatih.kelasToInt(dataLatih.kelas), naracoba, dataLatih.samplingRate, kanal, dataLatih.alatPerekaman);
					updateTableDataLatih();
				}else{
					sinyalKanal1 = new String[(int) Math.floor(dataLatih.dataEeg.getItemCount()/(dataLatih.samplingRate*dataLatih.segmentasi))][dataLatih.dataEeg.getItemCount()-1];
					sinyalKanal1 = dataLatih.segmentasiEEG(dataLatih.dataEeg, dataLatih.kanalToInt(dataLatih.kanal1), dataLatih.segmentasi, dataLatih.samplingRate, dataLatih.alatPerekaman);
					sinyalKanal2 = new String[(int) Math.floor(dataLatih.dataEeg.getItemCount()/(dataLatih.samplingRate*dataLatih.segmentasi))][dataLatih.dataEeg.getItemCount()-1];
					sinyalKanal2 = dataLatih.segmentasiEEG(dataLatih.dataEeg, dataLatih.kanalToInt(dataLatih.kanal2), dataLatih.segmentasi, dataLatih.samplingRate, dataLatih.alatPerekaman);
					kanalMerge = new String[sinyalKanal1.length][sinyalKanal1[0].length+sinyalKanal2[0].length];
					kanalMerge = dataLatih.gabungkanArray(sinyalKanal1, sinyalKanal2);
					kanal = Integer.toString(dataLatih.kanalToInt(dataLatih.kanal1))+","+Integer.toString(dataLatih.kanalToInt(dataLatih.kanal2));
					dbAction.inputSegmentasiSinyal(kanalMerge, dataLatih.kelasToInt(dataLatih.kelas), naracoba, dataLatih.samplingRate, kanal, dataLatih.alatPerekaman);
					updateTableDataLatih();
				}
			}
			return null;
		}
		
		@Override
		public void done(){
			resetFormDataLatih();
			updateStatusKanal();
			Home.refreshAllElement();
			progressSubmitDataEEG.setValue(100);
			JOptionPane.showMessageDialog(null, "Proses Segmentasi Berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
			lblStatusLoading.setVisible(false);
			progressSubmitDataEEG.setValue(0);
		}
	}
}
