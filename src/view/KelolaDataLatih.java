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

import mysql.DatabaseAction;
import wavelet.Wavelet;

public class KelolaDataLatih extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JButton btnPilihDataEEG, btnSubmitDataEEG;
	protected JLabel lblFileDataEEG;
	protected JComboBox<?> cmbKelas, cmbKanal1, cmbKanal2;
	protected JTextField txtSegmentasi, txtSamplingrate;
	protected JCheckBox cbGunakanKanal2;
	protected DefaultTableModel tableModel;
	protected JTable tableDataLatih;
	protected JScrollPane scrollTableDataLatih;
	protected DefaultTableCellRenderer centerTable;
	protected JFileChooser inputDataEEG = new JFileChooser();
	public JProgressBar progressSubmitDataEEG;
	public JLabel lblStatusLoading;
	protected String[] kelas = {"Pilih salah satu...", "Rileks", "Non-Rileks"};
	protected String[] kanal = {"Pilih salah satu...", "AF3", "F7", "F3", "FC5", "T7", "P7", "O1", "O2", "P8", "T8", "FC6", "F4", "F8", "AF4"};
	protected DatabaseAction dbAction;
	protected Wavelet wavelet;
	protected String[] fullPathDataEEG;
	
	public KelolaDataLatih(){
		setSize(1200, 650);
		setLayout(null);
		dbAction = new DatabaseAction();
		tableModel = dbAction.getListDataLatih();
		centerTable = new DefaultTableCellRenderer();
		centerTable.setHorizontalAlignment(SwingConstants.CENTER);
		add(getContent());
		add(new Layout("Kelola Data Latih"));
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
		
		cbGunakanKanal2 = new JCheckBox("Gunakan Kanal 2", false);
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
		btnSubmitDataEEG.setVisible(true);
		panelFormDataLatih.add(btnSubmitDataEEG);
		
		progressSubmitDataEEG = new JProgressBar();
		progressSubmitDataEEG.setBounds(15, 470, 420, 30);
		progressSubmitDataEEG.setForeground(new Color(44, 195, 107));
		progressSubmitDataEEG.setBackground(new Color(251, 252, 252));
		progressSubmitDataEEG.setStringPainted(true);
		progressSubmitDataEEG.setVisible(true);
		
		JPanel panelStatusLoading = new JPanel(new GridBagLayout());
		panelStatusLoading.setBounds(15, 500, 420, 30);
		
		lblStatusLoading = new JLabel("Loading...");
		lblStatusLoading.setVisible(false);
		panelStatusLoading.add(lblStatusLoading);
		
		JPanel panelLihatDataLatih = new JPanel();
		panelLihatDataLatih.setLayout(null);
		panelLihatDataLatih.setBackground(Color.white);
		panelLihatDataLatih.setBounds(460, 0, 450, 539);
		
		JLabel lblTitleTableDataLatih = new JLabel("Tabel Data Latih");
		lblTitleTableDataLatih.setForeground(new Color(68, 68, 68));
		lblTitleTableDataLatih.setBounds(15, 0, 150, 30);
		
		JPanel panelTableDataLatih = new JPanel();
		panelTableDataLatih.setLayout(new BorderLayout());
		panelTableDataLatih.setBackground(Color.white);
		panelTableDataLatih.setBounds(15, 30, panelLihatDataLatih.getWidth()-30, 480);
		panelLihatDataLatih.add(panelTableDataLatih);
		
		tableDataLatih = new JTable(tableModel);
		tableDataLatih.setRowSelectionAllowed(false);
		tableDataLatih.setPreferredScrollableViewportSize(getSize());
		tableDataLatih.setFillsViewportHeight(true);
		tableDataLatih.getColumnModel().getColumn(0).setCellRenderer(centerTable);
		tableDataLatih.getColumnModel().getColumn(2).setCellRenderer(centerTable);
		tableDataLatih.getColumnModel().getColumn(3).setCellRenderer(centerTable);
		
		scrollTableDataLatih = new JScrollPane(tableDataLatih);
		scrollTableDataLatih.setVisible(true);
		panelTableDataLatih.add(scrollTableDataLatih, BorderLayout.CENTER);
		
		panelContent.add(panelFormDataLatih);
		panelContent.add(panelLihatDataLatih);
		panelContent.add(progressSubmitDataEEG);
		panelContent.add(panelStatusLoading);
		return panelContent;
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
	}
	
	public void resetFormTableDataLatih(){
		fullPathDataEEG = null;
		lblFileDataEEG.setText("No file chosen");
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
			if(e.getActionCommand().equals("cekKanal2")){
				if(cmbKanal2.isEnabled() == true){
					cmbKanal2.setEnabled(false);
				}else{
					cmbKanal2.setEnabled(true);
				}
			}else if(e.getActionCommand().equals("pilihDataEEG")){
				FileNameExtensionFilter filterFile = new FileNameExtensionFilter("CSV FILE", "csv");
				inputDataEEG.setCurrentDirectory(inputDataEEG.getCurrentDirectory());
				inputDataEEG.setFileFilter(filterFile);
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
				}else if((String)cmbKelas.getSelectedItem() == "Pilih salah satu..."){
					JOptionPane.showMessageDialog(null, "Pilihan kelas tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(txtSegmentasi == null){
					JOptionPane.showMessageDialog(null, "Segmentasi tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(txtSamplingrate == null){
					JOptionPane.showMessageDialog(null, "Sampling Rate tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if((String)cmbKanal1.getSelectedItem() == "Pilih salah satu..."){
					JOptionPane.showMessageDialog(null, "Pilihan Kanal 1 tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(cmbKanal2.isEnabled() == true && (String)cmbKanal2.getSelectedItem() == "Pilih salah satu..."){
					JOptionPane.showMessageDialog(null, "Pilihan Kanal 2 tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else{
					if(cmbKanal2.isEnabled() == false){
						wavelet = new Wavelet(fullPathDataEEG, (String)cmbKelas.getSelectedItem(), Integer.parseInt(txtSegmentasi.getText()), Integer.parseInt(txtSamplingrate.getText()), (String)cmbKanal1.getSelectedItem(), null);
						CoreKelolaDataLatih coreKelolaDataLatih = new CoreKelolaDataLatih(wavelet);
						coreKelolaDataLatih.execute();
					}else{
						wavelet = new Wavelet(fullPathDataEEG, (String)cmbKelas.getSelectedItem(), Integer.parseInt(txtSegmentasi.getText()), Integer.parseInt(txtSamplingrate.getText()), (String)cmbKanal1.getSelectedItem(), (String)cmbKanal2.getSelectedItem());
						CoreKelolaDataLatih coreKelolaDataLatih = new CoreKelolaDataLatih(wavelet);
						coreKelolaDataLatih.execute();
					}
				}
			}
		}
	}
	
	class CoreKelolaDataLatih extends SwingWorker<Void, Void> {
		
		Wavelet wavelet;
		
		public CoreKelolaDataLatih(Wavelet wavelet) {
			// TODO Auto-generated constructor stub
			this.wavelet = wavelet;
			lblStatusLoading.setVisible(true);
		}

		@Override
		protected Void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			String[][] sinyalKanal1, sinyalKanal2, kanalMerge;
			int naracoba = dbAction.getJumNaracoba()+1;
			String kanal = null;
			int i, progress=0, progressDistance = 100/wavelet.pathFile.length;
			for(i=0; i<wavelet.pathFile.length; i++){
				lblStatusLoading.setText("membaca EEG signal ke "+(i+1));
				progressSubmitDataEEG.setValue(progress+=progressDistance);
				
				wavelet.lineOfSinyal = wavelet.readCsv(wavelet.pathFile[i]);
				if(wavelet.kanal2 == null){
					sinyalKanal1 = new String[(int) Math.floor(wavelet.lineOfSinyal.getItemCount()/(wavelet.samplingRate*wavelet.segmentasi))][wavelet.lineOfSinyal.getItemCount()-1];
					sinyalKanal1 = wavelet.segmentasiEEG(wavelet.lineOfSinyal, wavelet.kanalToInt(wavelet.kanal1), wavelet.segmentasi, wavelet.samplingRate);
					kanal = Integer.toString(wavelet.kanalToInt(wavelet.kanal1));
					dbAction.inputSegmentasiSinyal(sinyalKanal1, wavelet.kelasToInt(wavelet.kelas), naracoba, wavelet.samplingRate, kanal);
				}else{
					sinyalKanal1 = new String[(int) Math.floor(wavelet.lineOfSinyal.getItemCount()/(wavelet.samplingRate*wavelet.segmentasi))][wavelet.lineOfSinyal.getItemCount()-1];
					sinyalKanal1 = wavelet.segmentasiEEG(wavelet.lineOfSinyal, wavelet.kanalToInt(wavelet.kanal1), wavelet.segmentasi, wavelet.samplingRate);
					sinyalKanal2 = new String[(int) Math.floor(wavelet.lineOfSinyal.getItemCount()/(wavelet.samplingRate*wavelet.segmentasi))][wavelet.lineOfSinyal.getItemCount()-1];
					sinyalKanal2 = wavelet.segmentasiEEG(wavelet.lineOfSinyal, wavelet.kanalToInt(wavelet.kanal2), wavelet.segmentasi, wavelet.samplingRate);
					kanalMerge = new String[sinyalKanal1.length][sinyalKanal1[0].length+sinyalKanal2[0].length];
					kanalMerge = wavelet.gabungkanArray(sinyalKanal1, sinyalKanal2);
					kanal = Integer.toString(wavelet.kanalToInt(wavelet.kanal1))+","+Integer.toString(wavelet.kanalToInt(wavelet.kanal1));
					dbAction.inputSegmentasiSinyal(kanalMerge, wavelet.kelasToInt(wavelet.kelas), naracoba, wavelet.samplingRate, kanal);
				}
			}
			return null;
		}
		
		@Override
		public void done(){
			JOptionPane.showMessageDialog(null, "Proses Segmentasi Berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
			Home.refreshAllElement();
			//resetFormTableDataLatih();
			progressSubmitDataEEG.setValue(100);
			lblStatusLoading.setVisible(false);
			progressSubmitDataEEG.setValue(0);
		}
	}
}
