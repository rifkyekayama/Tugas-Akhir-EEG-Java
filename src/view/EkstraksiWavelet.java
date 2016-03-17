package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import lvq.LVQ;
import mysql.Database;
import wavelet.Wavelet;

public class EkstraksiWavelet extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Database dbAction = new Database();
	Wavelet wavelet;
	private JRadioButton rdWaveletFilter, rdWaveletGelombang, RdDb4, RdDb8;
	private JCheckBox cbSinyalRileks, cbSinyalNonRileks, cbGelAlfa, cbGelBeta, cbGelTeta;
	private JButton btnEkstraksiWavelet, btnLihatGrafik;
	private JLabel lblStatusLoading;
	private DefaultTableModel tableWavelet;
	private DefaultTableCellRenderer centerTable;
	private JTable tableDataWavelet;
	private JScrollPane scrollTableDataWavelet;
	private JComboBox<?> cmbNaracoba;
	private JProgressBar progressEkstraksiWavelet;
	private String[] naracoba = new String[dbAction.getJumNaracoba()+1];
	private int i=0;
	
	public EkstraksiWavelet() {
		setSize(1200, 650);
		setLayout(null);
		
		tableWavelet = dbAction.getListDataWavelet();
		centerTable = new DefaultTableCellRenderer();
		centerTable.setHorizontalAlignment(SwingConstants.CENTER);
		
		naracoba[0] = "Pilih salah satu...";
		for(i=0;i<dbAction.getJumNaracoba();i++){
			naracoba[i+1] = "Naracoba ke-"+(i+1);
		}
		
		add(getContent());
		add(new Layout("Ekstraksi Wavelet"));
	}
	
	public JPanel getContent(){
		JPanel panelContent = new JPanel();
		panelContent.setBounds(280, 60, 910, 530);
		panelContent.setLayout(null);
		
		JPanel panelFormWavelet = new JPanel();
		panelFormWavelet.setLayout(null);
		panelFormWavelet.setBounds(0, 0, 450, 260);
		panelFormWavelet.setBackground(Color.white);
		
		JLabel lblTitleEkstraksiWavelet = new JLabel("Ekstraksi Wavelet");
		lblTitleEkstraksiWavelet.setForeground(new Color(68, 68, 68));
		lblTitleEkstraksiWavelet.setBounds(15, 0, 150, 30);
		panelFormWavelet.add(lblTitleEkstraksiWavelet);
		
		JLabel lblTipeWavelet = new JLabel("Pilih Tipe Wavelet :");
		lblTipeWavelet.setFont(lblTipeWavelet.getFont().deriveFont(Font.BOLD, 15f));
		lblTipeWavelet.setBounds(15, 30, 170, 30);
		panelFormWavelet.add(lblTipeWavelet);
		
		rdWaveletFilter = new JRadioButton("Filter");
		rdWaveletFilter.setBackground(Color.white);
		rdWaveletFilter.setBounds(200, 30, 70, 30);
		rdWaveletFilter.setEnabled(false);
		panelFormWavelet.add(rdWaveletFilter);
		
		rdWaveletGelombang = new JRadioButton("Gelombang");
		rdWaveletGelombang.setBackground(Color.white);
		rdWaveletGelombang.setBounds(300, 30, 110, 30);
		rdWaveletGelombang.setSelected(true);
		panelFormWavelet.add(rdWaveletGelombang);
		
		ButtonGroup groupTipeWavelet = new ButtonGroup();
		groupTipeWavelet.add(rdWaveletFilter);
		groupTipeWavelet.add(rdWaveletGelombang);
		
		JLabel lblJenisWavelet = new JLabel("Pilih Jenis Wavelet :");
		lblJenisWavelet.setFont(lblJenisWavelet.getFont().deriveFont(Font.BOLD, 15f));
		lblJenisWavelet.setBounds(15, 60, 170, 30);
		panelFormWavelet.add(lblJenisWavelet);
		
		JLabel lblDaubechies = new JLabel("Daubechies :");
		lblDaubechies.setBounds(30, 80, 110, 30);
		panelFormWavelet.add(lblDaubechies);
		
		RdDb4 = new JRadioButton("4 Koefisien");
		RdDb4.setBackground(Color.white);
		RdDb4.setBounds(130, 80, 110, 30);
		RdDb4.setSelected(true);
		panelFormWavelet.add(RdDb4);
		
		RdDb8 = new JRadioButton("8 Koefisien");
		RdDb8.setBackground(Color.white);
		RdDb8.setBounds(260, 80, 110, 30);
		RdDb8.setEnabled(false);
		panelFormWavelet.add(RdDb8);
		
		ButtonGroup groupDaubechies = new ButtonGroup();
		groupDaubechies.add(RdDb4);
		groupDaubechies.add(RdDb8);
		
		btnEkstraksiWavelet = new JButton("Mulai Ekstraksi");
		btnEkstraksiWavelet.setForeground(Color.white);
		btnEkstraksiWavelet.setBackground(new Color(60, 137, 185));
		btnEkstraksiWavelet.setActionCommand("btnEkstraksiWavelet");
		btnEkstraksiWavelet.setBounds(15, 130, panelFormWavelet.getWidth()-30, 50);
		btnEkstraksiWavelet.addActionListener(new ButtonController());
		btnEkstraksiWavelet.addMouseListener(new MouseController());
		panelFormWavelet.add(btnEkstraksiWavelet);
		
		progressEkstraksiWavelet = new JProgressBar();
		progressEkstraksiWavelet.setBounds(15, 190, 420, 30);
		progressEkstraksiWavelet.setForeground(new Color(44, 195, 107));
		progressEkstraksiWavelet.setBackground(new Color(251, 252, 252));
		progressEkstraksiWavelet.setStringPainted(true);
		progressEkstraksiWavelet.setVisible(true);
		progressEkstraksiWavelet.setMinimum(0);
		progressEkstraksiWavelet.setMaximum(1000);
		panelFormWavelet.add(progressEkstraksiWavelet);
		
		JPanel panelStatusLoading = new JPanel();
		panelStatusLoading.setLayout(new GridBagLayout());
		panelStatusLoading.setBounds(15, 220, 420, 30);
		panelStatusLoading.setBackground(Color.white);
		panelFormWavelet.add(panelStatusLoading);
		
		lblStatusLoading = new JLabel("Loading...");
		lblStatusLoading.setVisible(false);
		panelStatusLoading.add(lblStatusLoading);
		
		JPanel panelTabelWavelet = new JPanel();
		panelTabelWavelet.setLayout(null);
		panelTabelWavelet.setBounds(460, 0, 450, 260);
		panelTabelWavelet.setBackground(Color.white);
		
		JLabel lblTitleTableWavelet = new JLabel("Tabel Ekstraksi Wavelet");
		lblTitleTableWavelet.setForeground(new Color(68, 68, 68));
		lblTitleTableWavelet.setBounds(15, 0, 170, 30);
		panelTabelWavelet.add(lblTitleTableWavelet);
		
		JPanel panelTabel = new JPanel();
		panelTabel.setLayout(new BorderLayout());
		panelTabel.setBackground(Color.white);
		panelTabel.setBounds(15, 30, 420, 215);
		panelTabelWavelet.add(panelTabel);
		
		tableDataWavelet = new JTable(tableWavelet);
		tableDataWavelet.setRowSelectionAllowed(false);
		tableDataWavelet.setPreferredScrollableViewportSize(getSize());
		tableDataWavelet.setFillsViewportHeight(true);
		tableDataWavelet.getColumnModel().getColumn(0).setCellRenderer(centerTable);
		tableDataWavelet.getColumnModel().getColumn(1).setCellRenderer(centerTable);
		tableDataWavelet.getColumnModel().getColumn(2).setCellRenderer(centerTable);
		tableDataWavelet.getColumnModel().getColumn(3).setCellRenderer(centerTable);
		tableDataWavelet.getColumnModel().getColumn(0).setMinWidth(30);
		tableDataWavelet.getColumnModel().getColumn(0).setMaxWidth(30);
		
		scrollTableDataWavelet = new JScrollPane(tableDataWavelet);
		scrollTableDataWavelet.setVisible(true);
		panelTabel.add(scrollTableDataWavelet);
		
		JPanel panelChartWavelet = new JPanel();
		panelChartWavelet.setLayout(null);
		panelChartWavelet.setBounds(0, 270, 910, 260);
		panelChartWavelet.setBackground(Color.white);
		
		JLabel lblTitleChartWavelet = new JLabel("Grafik Sinyal EEG");
		lblTitleChartWavelet.setForeground(new Color(68, 68, 68));
		lblTitleChartWavelet.setBounds(15, 0, 170, 30);
		panelChartWavelet.add(lblTitleChartWavelet);
		
		JLabel lblPilihNaracoba = new JLabel("Pilih Naracoba :");
		lblPilihNaracoba.setFont(lblPilihNaracoba.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihNaracoba.setBounds(15, 30, 150, 30);
		panelChartWavelet.add(lblPilihNaracoba);
		
		cmbNaracoba = new JComboBox<>(naracoba);
		cmbNaracoba.setBackground(Color.white);
		cmbNaracoba.setBounds(15, 60, 150, 30);
		panelChartWavelet.add(cmbNaracoba);
		
		JLabel lblPilihSinyal = new JLabel("Pilih Sinyal :");
		lblPilihSinyal.setFont(lblPilihSinyal.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihSinyal.setBounds(15, 100, 150, 30);
		panelChartWavelet.add(lblPilihSinyal);
		
		cbSinyalRileks = new JCheckBox("Rileks");
		cbSinyalRileks.setBackground(Color.white);
		cbSinyalRileks.setBounds(15, 125, 70, 30);
		panelChartWavelet.add(cbSinyalRileks);
		
		cbSinyalNonRileks = new JCheckBox("Non-Rileks");
		cbSinyalNonRileks.setBackground(Color.white);
		cbSinyalNonRileks.setBounds(90, 125, 100, 30);
		panelChartWavelet.add(cbSinyalNonRileks);
		
		JLabel lblPilihGelombang = new JLabel("Pilih Gelombang :");
		lblPilihGelombang.setFont(lblPilihGelombang.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihGelombang.setBounds(15, 160, 150, 30);
		panelChartWavelet.add(lblPilihGelombang);
		
		cbGelAlfa = new JCheckBox("Alfa");
		cbGelAlfa.setBackground(Color.white);
		cbGelAlfa.setBounds(15, 180, 55, 30);
		panelChartWavelet.add(cbGelAlfa);
		
		cbGelBeta = new JCheckBox("Beta");
		cbGelBeta.setBackground(Color.white);
		cbGelBeta.setBounds(70, 180, 60, 30);
		panelChartWavelet.add(cbGelBeta);
		
		cbGelTeta = new JCheckBox("Teta");
		cbGelTeta.setBackground(Color.white);
		cbGelTeta.setBounds(130, 180, 60, 30);
		panelChartWavelet.add(cbGelTeta);
		
		btnLihatGrafik = new JButton("Lihat Grafik");
		btnLihatGrafik.setForeground(Color.white);
		btnLihatGrafik.setBackground(new Color(60, 137, 185));
		btnLihatGrafik.setBounds(15, 220, 170, 30);
		btnLihatGrafik.setActionCommand("btnLihatGrafik");
		btnLihatGrafik.addActionListener(new ButtonController());
		btnLihatGrafik.addMouseListener(new MouseController());
		panelChartWavelet.add(btnLihatGrafik);
		
		JPanel panelChart = new JPanel();
		panelChart.setLayout(new BorderLayout());
		panelChart.setBounds(200, 30, 710, 230);
		panelChart.setBackground(Color.gray);
		panelChartWavelet.add(panelChart);
//		panelChart.add(getChart(), BorderLayout.CENTER);
		
		panelContent.add(panelFormWavelet);
		panelContent.add(panelTabelWavelet);
		panelContent.add(panelChartWavelet);
		return panelContent;
	}
	
	public ChartPanel getChart(){
		
		return null;
	}
	
	public void updateTabelEkstraksiWavelet(){
		tableDataWavelet.setModel(dbAction.getListDataWavelet());
		tableDataWavelet.repaint();
		tableDataWavelet.setRowSelectionAllowed(false);
		tableDataWavelet.setPreferredScrollableViewportSize(getSize());
		tableDataWavelet.setFillsViewportHeight(true);
		tableDataWavelet.getColumnModel().getColumn(0).setCellRenderer(centerTable);
		tableDataWavelet.getColumnModel().getColumn(1).setCellRenderer(centerTable);
		tableDataWavelet.getColumnModel().getColumn(2).setCellRenderer(centerTable);
		tableDataWavelet.getColumnModel().getColumn(3).setCellRenderer(centerTable);
		tableDataWavelet.getColumnModel().getColumn(0).setMinWidth(30);
		tableDataWavelet.getColumnModel().getColumn(0).setMaxWidth(30);
	}
	
	public void ekstraksiWavelet(boolean boolAlfa, boolean boolBeta, boolean boolTeta){
		ArrayList<double[]> sinyalEEG = dbAction.getDataLatih();
		List idOfDataLatih = dbAction.getIdOfDataLatih();
		double[] alfa = null, beta = null, teta = null;
		int i=0;
		
		dbAction.deleteEkstraksiWavelet();
		for(i=0;i<sinyalEEG.size();i++){
			if(boolAlfa == true){
				alfa = wavelet.getAlfa(sinyalEEG.get(i));
			}
			if(boolBeta == true){
				beta = wavelet.getBeta(sinyalEEG.get(i));
			}
			if(boolTeta == true){
				teta = wavelet.getTeta(sinyalEEG.get(i));
			}
			dbAction.inputEkstraksiWavelet(alfa, beta, teta, Integer.parseInt(idOfDataLatih.getItem(i)));
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
			if(e.getActionCommand().equals("btnEkstraksiWavelet")){
//				wavelet = new Wavelet();
//				ekstraksiWavelet(true, true, true);
//				Home.refreshAllElement();
				CoreEkstraksiWavelet coreEkstraksiWavelet = new CoreEkstraksiWavelet();
				coreEkstraksiWavelet.execute();
			}else if(e.getActionCommand().equals("btnLihatGrafik")){
				
			}
		}
	}
	
	class CoreEkstraksiWavelet extends SwingWorker<Void, Void>{
		
		Wavelet wavelet;
		ArrayList<double[]> sinyalEEG = dbAction.getDataLatih();
		List idOfDataLatih = dbAction.getIdOfDataLatih();
		double[] alfa = null, beta = null, teta = null;
		int i=0, progress=0, progressDistance;
		
		public CoreEkstraksiWavelet() {
			// TODO Auto-generated constructor stub
			wavelet = new Wavelet();
			sinyalEEG = dbAction.getDataLatih();
			idOfDataLatih = dbAction.getIdOfDataLatih();
			lblStatusLoading.setVisible(true);
			progressEkstraksiWavelet.setValue(0);
			progressDistance = 1000/(sinyalEEG.size()*3);
		}

		@Override
		protected Void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			dbAction.deleteEkstraksiWavelet();
			updateTabelEkstraksiWavelet();
			for(i=0;i<sinyalEEG.size();i++){
				alfa = wavelet.getAlfa(sinyalEEG.get(i));
				progressEkstraksiWavelet.setValue(progress+=progressDistance);
				beta = wavelet.getBeta(sinyalEEG.get(i));
				progressEkstraksiWavelet.setValue(progress+=progressDistance);
				teta = wavelet.getTeta(sinyalEEG.get(i));
				progressEkstraksiWavelet.setValue(progress+=progressDistance);
				dbAction.inputEkstraksiWavelet(alfa, beta, teta, Integer.parseInt(idOfDataLatih.getItem(i)));
				updateTabelEkstraksiWavelet();
			}
			return null;
		}
		
		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();
			Home.refreshAllElement();
			progressEkstraksiWavelet.setValue(1000);
			JOptionPane.showMessageDialog(null, "Proses Ekstraksi Berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
			lblStatusLoading.setVisible(false);
			progressEkstraksiWavelet.setValue(0);
		}
	}
}
