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

import javax.swing.DefaultComboBoxModel;
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

public class KelolaDataLatih extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JPanel panelFormEmotiv, panelFormNeurosky, panelFormInputDataLatih, panelFormEditDanHapusDataLatih;
	protected JButton btnPilihDataEEG, btnSubmitDataEEG, btnUbahDataLatih, btnHapusDataLatih, btnCariDataNaracoba;
	protected JLabel lblFileDataEEG, isLockedKanal1, isLockedKanal2;
	protected JComboBox<?> cmbAlatPerekaman, cmbKelasInput, cmbKelasEdit, cmbKanal1, cmbKanal2, cmbNaracobaEdit, cmbNaracobaHapus;
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
	protected String[] kelas = {"Pilih salah satu...", "Rileks", "Tidak Rileks"};
	protected String[] kanal = {"Pilih salah satu...", "AF3", "F7", "F3", "FC5", "T7", "P7", "O1", "O2", "P8", "T8", "FC6", "F4", "F8", "AF4"};
	protected Database database = new Database();
	protected DataLatih dataLatih;
	protected String[] fullPathDataEEG, naracobaEdit, naracobaHapus;
	protected int i=0;
	protected Menu menu;
	
	public KelolaDataLatih(){
		setSize(1200, 650);
		setLayout(null);
		tableModel = database.getListDataLatih();
		centerTable = new DefaultTableCellRenderer();
		centerTable.setHorizontalAlignment(SwingConstants.CENTER);
		
		naracobaEdit = new String[database.getJumNaracoba()+1];
		naracobaEdit[0] = "-";
		for(i=0;i<database.getJumNaracoba();i++){
			naracobaEdit[i+1] = Integer.toString(i+1);
		}
		
		naracobaHapus = new String[database.getJumNaracoba()+2];
		naracobaHapus[0] = "-";
		for(i=0;i<database.getJumNaracoba();i++){
			naracobaHapus[i+1] = Integer.toString(i+1);
		}
		naracobaHapus[naracobaHapus.length-1] = "Semua";
		
		menu = new Menu("Tambah Data Latih");
		
		add(getTableDataLatih());
		add(getFormInputDataLatih());
		add(getPanelUbahDanHapusDataLatih());
		add(menu);
		updateStatusAlat();
		updateStatusKanal();
	}
	
	public JPanel getTableDataLatih(){
		JPanel panelContent = new JPanel();
		panelContent.setBounds(280+460, 60, 450, 460);
		panelContent.setLayout(null);
		
		JPanel panelLihatDataLatih = new JPanel();
		panelLihatDataLatih.setLayout(null);
		panelLihatDataLatih.setBackground(Color.white);
		panelLihatDataLatih.setBounds(0, 0, 450, 460);
		
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
		
		panelContent.add(panelLihatDataLatih);
		return panelContent;
	}
	
	public JPanel getFormInputDataLatih(){
		panelFormInputDataLatih = new JPanel();
		panelFormInputDataLatih.setBounds(280, 60, 910, 530);
		panelFormInputDataLatih.setLayout(null);
		
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
		
		cmbKelasInput = new JComboBox<>(kelas);
		cmbKelasInput.setBackground(Color.white);
		cmbKelasInput.setBounds(15, 190, 420, 30);
		panelFormDataLatih.add(cmbKelasInput);
		
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
		
		btnSubmitDataEEG = new JButton("Tambah Data Latih");
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
		
		JPanel panelUbahDataLatih = new JPanel();
		panelUbahDataLatih.setLayout(null);
		panelUbahDataLatih.setBackground(Color.white);
		panelUbahDataLatih.setBounds(460, 470, 450, 60);
		
		JButton btnUbahDataLatih = new JButton("Ubah/Hapus Data Latih");
		btnUbahDataLatih.setActionCommand("btnUbahDataLatih");
		btnUbahDataLatih.setBackground(new Color(60, 137, 185));
		btnUbahDataLatih.setForeground(Color.white);
		btnUbahDataLatih.setBorderPainted(false);
		btnUbahDataLatih.addMouseListener(new MouseController());
		btnUbahDataLatih.addActionListener(new ButtonController());
		btnUbahDataLatih.setBounds(15, 15, panelUbahDataLatih.getWidth()-(15*2), panelUbahDataLatih.getHeight()-(15*2));
		panelUbahDataLatih.add(btnUbahDataLatih);
		
		panelFormInputDataLatih.add(panelFormDataLatih);
		panelFormInputDataLatih.add(progressSubmitDataEEG);
		panelFormInputDataLatih.add(panelStatusLoading);
		panelFormInputDataLatih.add(panelUbahDataLatih);
		return panelFormInputDataLatih;
	}
	
	public JPanel getPanelUbahDanHapusDataLatih(){
		panelFormEditDanHapusDataLatih = new JPanel();
		panelFormEditDanHapusDataLatih.setBounds(280, 60, 910, 530);
		panelFormEditDanHapusDataLatih.setVisible(false);
		panelFormEditDanHapusDataLatih.setLayout(null);
		
		JPanel panelFormEditDataLatih = new JPanel();
		panelFormEditDataLatih.setLayout(null);
		panelFormEditDataLatih.setBackground(Color.white);
		panelFormEditDataLatih.setBounds(0, 0, 450, 260);
		
		JLabel lblTitleEditDataLatih = new JLabel("Edit Data latih");
		lblTitleEditDataLatih.setForeground(new Color(68, 68, 68));
		lblTitleEditDataLatih.setBounds(15, 0, 150, 30);
		panelFormEditDataLatih.add(lblTitleEditDataLatih);
		
		JLabel lblPilihNaracoba = new JLabel("Pilih Naracoba");
		lblPilihNaracoba.setFont(lblPilihNaracoba.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihNaracoba.setBounds(15, 30, 150, 30);
		panelFormEditDataLatih.add(lblPilihNaracoba);
		
		JLabel lblNaracoba = new JLabel("Naracoba ke-");
		lblNaracoba.setBounds(15, 60, 100, 30);
		panelFormEditDataLatih.add(lblNaracoba);
		
		cmbNaracobaEdit = new JComboBox<>(naracobaEdit);
		cmbNaracobaEdit.setBounds(120, 60, 150, 30);
		cmbNaracobaEdit.setBackground(Color.white);
		panelFormEditDataLatih.add(cmbNaracobaEdit);
		
		btnCariDataNaracoba = new JButton("Cari");
		btnCariDataNaracoba.setForeground(Color.white);
		btnCariDataNaracoba.setBackground(new Color(60, 137, 185));
		btnCariDataNaracoba.setBounds(280, 60, 70, 30);
		btnCariDataNaracoba.setActionCommand("btnCariDataNaracoba");
		btnCariDataNaracoba.addMouseListener(new MouseController());
		btnCariDataNaracoba.addActionListener(new ButtonController());
		panelFormEditDataLatih.add(btnCariDataNaracoba);
		
		JLabel lblKelas = new JLabel("Pilih Kelas");
		lblKelas.setFont(lblKelas.getFont().deriveFont(Font.BOLD, 15f));
		lblKelas.setBounds(15, 110, 150, 30);
		panelFormEditDataLatih.add(lblKelas);
		
		cmbKelasEdit = new JComboBox<>(kelas);
		cmbKelasEdit.setBackground(Color.white);
		cmbKelasEdit.setBounds(15, 140, 150, 30);
		cmbKelasEdit.setEnabled(false);
		panelFormEditDataLatih.add(cmbKelasEdit);
		
		btnUbahDataLatih = new JButton("Ubah Data Latih");
		btnUbahDataLatih.setForeground(Color.white);
		btnUbahDataLatih.setBackground(new Color(60, 137, 185));
		btnUbahDataLatih.setBounds(15, 190, panelFormEditDataLatih.getWidth()-30, 50);
		btnUbahDataLatih.setEnabled(false);
		btnUbahDataLatih.setActionCommand("btnUbahDataLatih");
		btnUbahDataLatih.addMouseListener(new MouseController());
		btnUbahDataLatih.addActionListener(new ButtonController());
		panelFormEditDataLatih.add(btnUbahDataLatih);
		
		JPanel panelHapusDataLatih = new JPanel();
		panelHapusDataLatih.setLayout(null);
		panelHapusDataLatih.setBackground(Color.white);
		panelHapusDataLatih.setBounds(0, 270, 450, 190);
		
		JLabel lblTitleHapusDataLatih = new JLabel("Hapus Data Latih");
		lblTitleHapusDataLatih.setForeground(new Color(68, 68, 68));
		lblTitleHapusDataLatih.setBounds(15, 0, 150, 30);
		panelHapusDataLatih.add(lblTitleHapusDataLatih);
		
		JLabel lblNaracobaHapus = new JLabel("Naracoba ke-");
		lblNaracobaHapus.setBounds(15, 60, 100, 30);
		panelHapusDataLatih.add(lblNaracobaHapus);
		
		JLabel lblPilihNaracobaHapus = new JLabel("Pilih Naracoba");
		lblPilihNaracobaHapus.setFont(lblPilihNaracobaHapus.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihNaracobaHapus.setBounds(15, 30, 150, 30);
		panelHapusDataLatih.add(lblPilihNaracobaHapus);
		
		cmbNaracobaHapus = new JComboBox<>(naracobaHapus);
		cmbNaracobaHapus.setBounds(120, 60, 150, 30);
		cmbNaracobaHapus.setBackground(Color.white);
		panelHapusDataLatih.add(cmbNaracobaHapus);
		
		btnHapusDataLatih = new JButton("Hapus Data Latih");
		btnHapusDataLatih.setForeground(Color.white);
		btnHapusDataLatih.setBackground(new Color(60, 137, 185));
		btnHapusDataLatih.setBounds(15, 110, panelHapusDataLatih.getWidth()-30, 50);
		btnHapusDataLatih.setActionCommand("btnHapusDataLatih");
		btnHapusDataLatih.addMouseListener(new MouseController());
		btnHapusDataLatih.addActionListener(new ButtonController());
		panelHapusDataLatih.add(btnHapusDataLatih);
		
		JPanel panelKelolaDataLatih = new JPanel();
		panelKelolaDataLatih.setLayout(null);
		panelKelolaDataLatih.setBackground(Color.white);
		panelKelolaDataLatih.setBounds(460, 470, 450, 60);
		
		JButton btnKelolaDataLatih = new JButton("Tambah Data Latih");
		btnKelolaDataLatih.setActionCommand("btnKelolaDataLatih");
		btnKelolaDataLatih.setBackground(new Color(60, 137, 185));
		btnKelolaDataLatih.setForeground(Color.white);
		btnKelolaDataLatih.setBorderPainted(false);
		btnKelolaDataLatih.addMouseListener(new MouseController());
		btnKelolaDataLatih.addActionListener(new ButtonController());
		btnKelolaDataLatih.setBounds(15, 15, panelKelolaDataLatih.getWidth()-(15*2), panelKelolaDataLatih.getHeight()-(15*2));
		panelKelolaDataLatih.add(btnKelolaDataLatih);
		
		panelFormEditDanHapusDataLatih.add(panelFormEditDataLatih);
		panelFormEditDanHapusDataLatih.add(panelHapusDataLatih);
		panelFormEditDanHapusDataLatih.add(panelKelolaDataLatih);
		return panelFormEditDanHapusDataLatih;
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateCmbEditNaracoba(){
		String[] naracobaEdit = new String[database.getJumNaracoba()+1];
		String[] naracobaHapus = new String[database.getJumNaracoba()+2];
		DefaultComboBoxModel modelNaracobaEdit, modelNaracobaHapus;
		
		naracobaEdit[0] = "-";
		for(i=0;i<database.getJumNaracoba();i++){
			naracobaEdit[i+1] = Integer.toString(i+1);
		}
		modelNaracobaEdit = new DefaultComboBoxModel(naracobaEdit);
		
		naracobaHapus[0] = "-";
		for(i=0;i<database.getJumNaracoba();i++){
			naracobaHapus[i+1] = Integer.toString(i+1);
		}
		naracobaHapus[naracobaHapus.length-1] = "Semua";
		modelNaracobaHapus = new DefaultComboBoxModel(naracobaHapus);
		
		cmbNaracobaEdit.removeAllItems();
		cmbNaracobaEdit.setModel(modelNaracobaEdit);
		cmbNaracobaEdit.repaint();
		
		cmbNaracobaHapus.removeAllItems();
		cmbNaracobaHapus.setModel(modelNaracobaHapus);
		cmbNaracobaHapus.repaint();
	}
	
	public void updateTableDataLatih(){
		tableDataLatih.setModel(database.getListDataLatih());
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
		if(database.getKanal() != null){
			kanal = database.getKanal();
			if(kanal[0] != 1){
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
				isLockedKanal1.setVisible(false);
				isLockedKanal2.setVisible(false);
			}
		}else{
			resetFormDataLatih();
			cbGunakanKanal2.setVisible(true);
			isLockedKanal1.setVisible(false);
			isLockedKanal2.setVisible(false);
		}
	}
	
	public void changeSettingAlat(String jenisAlat){
		if(jenisAlat == "kosong"){
			resetFormDataLatih();
			btnPilihDataEEG.setEnabled(false);
			btnSubmitDataEEG.setEnabled(false);
			panelFormEmotiv.setVisible(false);
			panelFormNeurosky.setVisible(false);
			txtSamplingrate.setText("");
			cbGunakanKanal2.setSelected(false);
			cmbKanal2.setEnabled(false);
		}else if(jenisAlat == "Emotiv"){
			resetFormDataLatih();
			updateStatusKanal();
			btnPilihDataEEG.setEnabled(true);
			btnSubmitDataEEG.setEnabled(true);
			panelFormEmotiv.setVisible(true);
			panelFormNeurosky.setVisible(false);
			txtSamplingrate.setText("128");
			cbGunakanKanal2.setSelected(false);
			cmbKanal2.setEnabled(false);
		}else if(jenisAlat == "Neurosky"){
			resetFormDataLatih();
			btnPilihDataEEG.setEnabled(true);
			btnSubmitDataEEG.setEnabled(true);
			panelFormEmotiv.setVisible(false);
			panelFormNeurosky.setVisible(true);
			txtSamplingrate.setText("512");
			cbGunakanKanal2.setSelected(false);
			cmbKanal2.setEnabled(false);
		}
	}
	
	public void updateStatusAlat(){
		if(database.getAlatPerekaman() != null){
			changeSettingAlat(database.getAlatPerekaman());
			cmbAlatPerekaman.setSelectedItem(database.getAlatPerekaman());
			cmbAlatPerekaman.setEnabled(false);
		}
	}
	
	public void resetFormDataLatih(){
		fullPathDataEEG = null;
		lblFileDataEEG.setText("Tidak ada file dipilih.");
		cmbKelasInput.setSelectedIndex(0);
		cmbKelasEdit.setSelectedIndex(0);
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
					changeSettingAlat("kosong");
				}else if((String)cmbAlatPerekaman.getSelectedItem() == "Emotiv"){
					changeSettingAlat("Emotiv");
				}else if((String)cmbAlatPerekaman.getSelectedItem() == "Neurosky"){
					changeSettingAlat("Neurosky");
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
				}else if(cmbKelasInput.getSelectedIndex() == 0){
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
						dataLatih = new DataLatih(fullPathDataEEG, (String)cmbKelasInput.getSelectedItem(), Integer.parseInt(txtSegmentasi.getText()), Integer.parseInt(txtSamplingrate.getText()), kanal, null, (String)cmbAlatPerekaman.getSelectedItem());
						CoreKelolaDataLatih coreKelolaDataLatih = new CoreKelolaDataLatih(dataLatih);
						coreKelolaDataLatih.execute();
					}else{
						dataLatih = new DataLatih(fullPathDataEEG, (String)cmbKelasInput.getSelectedItem(), Integer.parseInt(txtSegmentasi.getText()), Integer.parseInt(txtSamplingrate.getText()), (String)cmbKanal1.getSelectedItem(), (String)cmbKanal2.getSelectedItem(), (String)cmbAlatPerekaman.getSelectedItem());
						CoreKelolaDataLatih coreKelolaDataLatih = new CoreKelolaDataLatih(dataLatih);
						coreKelolaDataLatih.execute();
					}
				}
			}else if(e.getActionCommand().equals("btnUbahDataLatih")){
				if(!panelFormEditDanHapusDataLatih.isVisible()){
					menu.setTitle("Ubah/Hapus Data Latih");
					panelFormEditDanHapusDataLatih.setVisible(true);
					panelFormInputDataLatih.setVisible(false);
				}
			}else if(e.getActionCommand().equals("btnKelolaDataLatih")){
				if(!panelFormInputDataLatih.isVisible()){
					menu.setTitle("Tambah Data Latih");
					panelFormInputDataLatih.setVisible(true);
					panelFormEditDanHapusDataLatih.setVisible(false);
				}
			}else if(e.getActionCommand().equals("btnCariDataNaracoba")){
				if((String)cmbNaracobaEdit.getSelectedItem() != "-"){
					int kelasNaracoba = database.getKelasFromDataLatih(Integer.parseInt((String)cmbNaracobaEdit.getSelectedItem()));
					if(kelasNaracoba == 1){
						cmbKelasEdit.setSelectedIndex(1);
					}else if(kelasNaracoba == -1){
						cmbKelasEdit.setSelectedIndex(2);
					}
					cmbKelasEdit.setEnabled(true);
					btnUbahDataLatih.setEnabled(true);
				}else{
					cmbKelasEdit.setSelectedIndex(0);
					cmbKelasEdit.setEnabled(false);
					btnUbahDataLatih.setEnabled(false);
				}
			}else if(e.getActionCommand().equals("btnUbahDataLatih")){
				int indexKelas = 0;
				if((String)cmbNaracobaEdit.getSelectedItem() != "-"){
					if(cmbKelasEdit.getSelectedIndex() == 1){
						indexKelas = 1;
					}else if(cmbKelasEdit.getSelectedIndex() == 2){
						indexKelas = -1;
					}
					database.ubahDataLatih(indexKelas, Integer.parseInt((String)cmbNaracobaEdit.getSelectedItem()));
				}
				cmbKelasEdit.setSelectedIndex(0);
				cmbKelasEdit.setEnabled(false);
				btnUbahDataLatih.setEnabled(false);
				ViewController.refreshAllElement();
			}else if(e.getActionCommand().equals("btnHapusDataLatih")){
				if((String)cmbNaracobaHapus.getSelectedItem() == "-"){
					JOptionPane.showMessageDialog(null, "Pilihan naracoba tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else{
					boolean hasilHapus = database.hapusDataLatih((String)cmbNaracobaHapus.getSelectedItem());
					if(hasilHapus == true){
						ViewController.refreshAllElement();
						JOptionPane.showMessageDialog(null, "Data latih berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
					}else if(hasilHapus == false){
						JOptionPane.showMessageDialog(null, "Data latih gagal dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
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
			int naracoba = database.getJumNaracoba()+1;
			String kanal = null;
			int i, progress=0, progressDistance = 100/dataLatih.pathFile.length;
			for(i=0; i<dataLatih.pathFile.length; i++){
				lblStatusLoading.setText("membaca EEG signal ke "+(i+1));
				progressSubmitDataEEG.setValue(progress+=progressDistance);
				
				dataLatih.dataEeg = dataLatih.readFile(dataLatih.pathFile[i]);
				if(dataLatih.kanal2 == null){
					sinyalKanal1 = dataLatih.segmentasiEEG(dataLatih.dataEeg, dataLatih.kanalToInt(dataLatih.kanal1), dataLatih.segmentasi, dataLatih.samplingRate, dataLatih.alatPerekaman);
					kanal = Integer.toString(dataLatih.kanalToInt(dataLatih.kanal1));
					database.inputSegmentasiSinyal(sinyalKanal1, dataLatih.kelasToInt(dataLatih.kelas), naracoba, dataLatih.samplingRate, kanal, dataLatih.alatPerekaman);
					updateTableDataLatih();
				}else{
					sinyalKanal1 = dataLatih.segmentasiEEG(dataLatih.dataEeg, dataLatih.kanalToInt(dataLatih.kanal1), dataLatih.segmentasi, dataLatih.samplingRate, dataLatih.alatPerekaman);
					sinyalKanal2 = dataLatih.segmentasiEEG(dataLatih.dataEeg, dataLatih.kanalToInt(dataLatih.kanal2), dataLatih.segmentasi, dataLatih.samplingRate, dataLatih.alatPerekaman);
					kanalMerge = dataLatih.gabungkanArray(sinyalKanal1, sinyalKanal2);
					kanal = Integer.toString(dataLatih.kanalToInt(dataLatih.kanal1))+","+Integer.toString(dataLatih.kanalToInt(dataLatih.kanal2));
					database.inputSegmentasiSinyal(kanalMerge, dataLatih.kelasToInt(dataLatih.kelas), naracoba, dataLatih.samplingRate, kanal, dataLatih.alatPerekaman);
					updateTableDataLatih();
				}
			}
			return null;
		}
		
		@Override
		public void done(){
			resetFormDataLatih();
			updateStatusAlat();
			updateStatusKanal();
			ViewController.refreshAllElement();
			progressSubmitDataEEG.setValue(100);
			JOptionPane.showMessageDialog(null, "Proses Segmentasi Berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
			lblStatusLoading.setVisible(false);
			progressSubmitDataEEG.setValue(0);
		}
	}
}
