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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

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
	private XYDataset dataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	private boolean isUseRileks = false, isUseNonRileks = false, isUseAlfa = false, isUseBeta = false, isUseTeta = false;
	private String[] naracoba = new String[dbAction.getJumNaracoba()+1];
	private JPanel panelChart;
	private int i=0;
	
	public EkstraksiWavelet() {
		setSize(1200, 650);
		setLayout(null);
		
		tableWavelet = dbAction.getListDataWavelet();
		centerTable = new DefaultTableCellRenderer();
		centerTable.setHorizontalAlignment(SwingConstants.CENTER);
		
		naracoba[0] = "Pilih salah satu...";
		for(i=0;i<dbAction.getJumNaracoba();i++){
			naracoba[i+1] = String.valueOf(i+1);
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
		cmbNaracoba.setActionCommand("cmbNaracoba");
		cmbNaracoba.addActionListener(new ButtonController());
		panelChartWavelet.add(cmbNaracoba);
		
		JLabel lblPilihSinyal = new JLabel("Pilih Sinyal :");
		lblPilihSinyal.setFont(lblPilihSinyal.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihSinyal.setBounds(15, 100, 150, 30);
		panelChartWavelet.add(lblPilihSinyal);
		
		cbSinyalRileks = new JCheckBox("Rileks");
		cbSinyalRileks.setBackground(Color.white);
		cbSinyalRileks.setBounds(15, 125, 70, 30);
		cbSinyalRileks.setEnabled(false);
		panelChartWavelet.add(cbSinyalRileks);
		
		cbSinyalNonRileks = new JCheckBox("Non-Rileks");
		cbSinyalNonRileks.setBackground(Color.white);
		cbSinyalNonRileks.setBounds(90, 125, 100, 30);
		cbSinyalNonRileks.setEnabled(false);
		panelChartWavelet.add(cbSinyalNonRileks);
		
		JLabel lblPilihGelombang = new JLabel("Pilih Gelombang :");
		lblPilihGelombang.setFont(lblPilihGelombang.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihGelombang.setBounds(15, 160, 150, 30);
		panelChartWavelet.add(lblPilihGelombang);
		
		cbGelAlfa = new JCheckBox("Alfa");
		cbGelAlfa.setBackground(Color.white);
		cbGelAlfa.setBounds(15, 180, 55, 30);
		cbGelAlfa.setEnabled(false);
		panelChartWavelet.add(cbGelAlfa);
		
		cbGelBeta = new JCheckBox("Beta");
		cbGelBeta.setBackground(Color.white);
		cbGelBeta.setBounds(70, 180, 60, 30);
		cbGelBeta.setEnabled(false);
		panelChartWavelet.add(cbGelBeta);
		
		cbGelTeta = new JCheckBox("Teta");
		cbGelTeta.setBackground(Color.white);
		cbGelTeta.setBounds(130, 180, 60, 30);
		cbGelTeta.setEnabled(false);
		panelChartWavelet.add(cbGelTeta);
		
		btnLihatGrafik = new JButton("Lihat Grafik");
		btnLihatGrafik.setForeground(Color.white);
		btnLihatGrafik.setBackground(new Color(60, 137, 185));
		btnLihatGrafik.setBounds(15, 220, 170, 30);
		btnLihatGrafik.setActionCommand("btnLihatGrafik");
		btnLihatGrafik.addActionListener(new ButtonController());
		btnLihatGrafik.addMouseListener(new MouseController());
		panelChartWavelet.add(btnLihatGrafik);
		
		dataset = createDataSetSinyalAsli(false, false, false, false, false, 0);
		chart = createChart(dataset);
		chartPanel = new ChartPanel(chart);
		chartPanel.setMouseWheelEnabled(true);
		chartPanel.setMouseZoomable(true);
		
		panelChart = new JPanel();
		panelChart.setLayout(new BorderLayout());
		panelChart.setBounds(200, 10, 710, 240);
		panelChart.add(chartPanel, BorderLayout.CENTER);
		panelChartWavelet.add(panelChart);
		
		panelContent.add(panelFormWavelet);
		panelContent.add(panelTabelWavelet);
		panelContent.add(panelChartWavelet);
		return panelContent;
	}
	
	public JFreeChart createChart(XYDataset dataset){
		return ChartFactory.createTimeSeriesChart(
				"Grafik Wavelet", 
				"waktu (detik)", 
				"Nilai titik sinyal", 
				dataset);
	}
	
	public void updateGrafikWavelet(XYDataset dataset){
		chart = createChart(dataset);
		chartPanel.removeAll();
		chartPanel.setChart(chart);
		chartPanel.setMouseWheelEnabled(true);
		chartPanel.setMouseZoomable(true);
		chartPanel.repaint();
		panelChart.repaint();
		panelChart.revalidate();
	}
	
//	public void updateComboboxNaracoba(){
//		String[] naracoba = new String[dbAction.getJumNaracoba()+1];
//		
//		naracoba[0] = "Pilih salah satu...";
//		for(i=0;i<dbAction.getJumNaracoba();i++){
//			naracoba[i+1] = String.valueOf(i+1);
//		}
//		
//		cmbNaracoba.removeAllItems();
//		cmbNaracoba.setModel(new DefaultComboBoxModel(naracoba));
//		cmbNaracoba.repaint();
//		cmbNaracoba.revalidate();
//	}
	
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
	
	public XYDataset createDataSetSinyalAsli(boolean isUseRileks, boolean isUseNonRileks, boolean isUseAlfa, boolean isUseBeta, boolean isUseTeta, int naracoba){
		final TimeSeriesCollection collection = new TimeSeriesCollection();
		int i=0, j=0;
		
		if(isUseRileks == true){
			final TimeSeries seriesRileks = new TimeSeries("Sinyal Rileks");
			Second current = new Second();
			ArrayList<double[]> sinyalRileks = new ArrayList<double[]>();
			sinyalRileks = dbAction.getDataLatihRileks(naracoba);
			
			for(i=0;i<sinyalRileks.size();i++){
				for(j=0;j<sinyalRileks.get(i).length;j++){
					seriesRileks.add(current, sinyalRileks.get(i)[j]);
					current = ( Second ) current.next( ); 
				}
			}
			collection.addSeries(seriesRileks);
		}
		
		if(isUseNonRileks == true){
			final TimeSeries seriesNonRileks = new TimeSeries("Sinyal Non Rileks");
			Second current = new Second();
			ArrayList<double[]> sinyalNonRileks = new ArrayList<double[]>();
			sinyalNonRileks = dbAction.getDataLatihNonRileks(naracoba);
			
			for(i=0;i<sinyalNonRileks.size();i++){
				for(j=0;j<sinyalNonRileks.get(i).length;j++){
					seriesNonRileks.add(current, sinyalNonRileks.get(i)[j]);
					current = ( Second ) current.next( ); 
				}
			}
			collection.addSeries(seriesNonRileks);
		}
		
		if(isUseAlfa == true){
			final TimeSeries seriesAlfa = new TimeSeries("Sinyal Alfa");
			Second current = new Second();
			ArrayList<double[]> sinyalAlfa = new ArrayList<double[]>();
			sinyalAlfa = dbAction.getAlfaByNaracoba(naracoba);
			
			for(i=0;i<sinyalAlfa.size();i++){
				for(j=0;j<sinyalAlfa.get(i).length;j++){
					seriesAlfa.add(current, sinyalAlfa.get(i)[j]);
					current = ( Second ) current.next( ); 
				}
			}
			collection.addSeries(seriesAlfa);
		}
		
		if(isUseBeta == true){
			final TimeSeries seriesBeta = new TimeSeries("Sinyal Beta");
			Second current = new Second();
			ArrayList<double[]> sinyalBeta = new ArrayList<double[]>();
			sinyalBeta = dbAction.getBetaByNaracoba(naracoba);
			
			for(i=0;i<sinyalBeta.size();i++){
				for(j=0;j<sinyalBeta.get(i).length;j++){
					seriesBeta.add(current, sinyalBeta.get(i)[j]);
					current = ( Second ) current.next( ); 
				}
			}
			collection.addSeries(seriesBeta);
		}
		
		if(isUseTeta == true){
			final TimeSeries seriesTeta = new TimeSeries("Sinyal Teta");
			Second current = new Second();
			ArrayList<double[]> sinyalTeta = new ArrayList<double[]>();
			sinyalTeta = dbAction.getTetaByNaracoba(naracoba);
			
			for(i=0;i<sinyalTeta.size();i++){
				for(j=0;j<sinyalTeta.get(i).length;j++){
					seriesTeta.add(current, sinyalTeta.get(i)[j]);
					current = ( Second ) current.next( ); 
				}
			}
			
			collection.addSeries(seriesTeta);
		}
		return collection;
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
				CoreEkstraksiWavelet coreEkstraksiWavelet = new CoreEkstraksiWavelet();
				coreEkstraksiWavelet.execute();
			}else if(e.getActionCommand().equals("cmbNaracoba")){
				if((String)cmbNaracoba.getSelectedItem() == "Pilih salah satu..."){
					cbSinyalRileks.setEnabled(false);
					cbSinyalRileks.setSelected(false);
					cbSinyalNonRileks.setEnabled(false);
					cbSinyalNonRileks.setSelected(false);
					cbGelAlfa.setEnabled(false);
					cbGelAlfa.setSelected(false);
					cbGelBeta.setEnabled(false);
					cbGelBeta.setSelected(false);
					cbGelTeta.setEnabled(false);
					cbGelTeta.setSelected(false);
				}else{
					int kelas = dbAction.getKelasFromDataLatih(Integer.parseInt((String)cmbNaracoba.getSelectedItem()));
					cbGelAlfa.setEnabled(true);
					cbGelAlfa.setSelected(false);
					cbGelBeta.setEnabled(true);
					cbGelBeta.setSelected(false);
					cbGelTeta.setEnabled(true);
					cbGelTeta.setSelected(false);
					if(kelas == 1){
						cbSinyalRileks.setEnabled(true);
						cbSinyalRileks.setSelected(false);
						cbSinyalNonRileks.setEnabled(false);
						cbSinyalNonRileks.setSelected(false);
					}else if(kelas == -1){
						cbSinyalRileks.setEnabled(false);
						cbSinyalRileks.setSelected(false);
						cbSinyalNonRileks.setEnabled(true);
						cbSinyalNonRileks.setSelected(false);
					}
				}
			}else if(e.getActionCommand().equals("btnLihatGrafik")){
				int kelas = dbAction.getKelasFromDataLatih(Integer.parseInt((String)cmbNaracoba.getSelectedItem()));
				
				if((String)cmbNaracoba.getSelectedItem() == "Pilih salah satu..."){
					JOptionPane.showMessageDialog(null, "Pilihan naracoba tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if((kelas == 1 && dbAction.getNeuronRileks().size() == 0) || (kelas == -1 && dbAction.getNeuronNonRileks().size() == 0)){
					JOptionPane.showMessageDialog(null, "Data latih belum diekstraksi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(dbAction.getDataLatih().size() != dbAction.getJumDataWavelet()){
					JOptionPane.showMessageDialog(null, "Data ekstraksi belum di update!", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}
				
				if(cbSinyalRileks.isSelected()){
					isUseRileks = true;
				}
				if(cbSinyalNonRileks.isSelected()){
					isUseNonRileks = true;
				}
				if(cbGelAlfa.isSelected()){
					isUseAlfa = true;
				}
				if(cbGelBeta.isSelected()){
					isUseBeta = true;
				}
				if(cbGelTeta.isSelected()){
					isUseTeta = true;
				}
				
				dataset = createDataSetSinyalAsli(isUseRileks, isUseNonRileks, isUseAlfa, isUseBeta, isUseTeta, Integer.parseInt((String)cmbNaracoba.getSelectedItem()));
				updateGrafikWavelet(dataset);
				isUseRileks = false;
				isUseNonRileks = false;
				isUseAlfa = false;
				isUseBeta = false;
				isUseTeta = false;
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
