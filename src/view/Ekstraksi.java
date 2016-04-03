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
import javax.swing.DefaultComboBoxModel;
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
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import mysql.Database;
import wavelet.WaveletEkstraksi;
import wavelet.WaveletFiltering;

public class Ekstraksi extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Database dbAction = new Database();
	private JRadioButton rdWaveletFilter, rdWaveletGelombang, RdDb4, RdDb8;
	private JCheckBox cbSinyalRileks, cbSinyalNonRileks, cbGelAlfa, cbGelBeta, cbGelTeta, cbGelFilter;
	private JButton btnEkstraksiWavelet, btnLihatGrafik, btnCariDataNaracoba;
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
	private boolean isUseRileks = false, isUseNonRileks = false, isUseAlfa = false, isUseBeta = false, isUseTeta = false, isUseFilter = false;
	private String[] naracoba = new String[dbAction.getJumNaracoba()+1];
	private JPanel panelChart;
	private int i=0;
	
	public Ekstraksi() {
		setSize(1200, 650);
		setLayout(null);
		
		tableWavelet = dbAction.getListDataWavelet();
		centerTable = new DefaultTableCellRenderer();
		centerTable.setHorizontalAlignment(SwingConstants.CENTER);
		
		naracoba[0] = "-";
		for(i=0;i<dbAction.getJumNaracoba();i++){
			naracoba[i+1] = String.valueOf(i+1);
		}
		
		add(getContent());
		add(new MenuUtama("Ekstraksi Wavelet"));
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
		panelFormWavelet.add(rdWaveletFilter);
		
		rdWaveletGelombang = new JRadioButton("Gelombang");
		rdWaveletGelombang.setBackground(Color.white);
		rdWaveletGelombang.setBounds(300, 30, 110, 30);
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
		tableDataWavelet.getColumnModel().getColumn(4).setCellRenderer(centerTable);
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
		lblPilihNaracoba.setBounds(15, 20, 150, 30);
		panelChartWavelet.add(lblPilihNaracoba);
		
		cmbNaracoba = new JComboBox<>(naracoba);
		cmbNaracoba.setBackground(Color.white);
		cmbNaracoba.setBounds(15, 50, 100, 30);
		cmbNaracoba.setActionCommand("cmbNaracoba");
		cmbNaracoba.addActionListener(new ButtonController());
		panelChartWavelet.add(cmbNaracoba);
		
		btnCariDataNaracoba = new JButton("cari");
		btnCariDataNaracoba.setForeground(Color.white);
		btnCariDataNaracoba.setBackground(new Color(60, 137, 185));
		btnCariDataNaracoba.setBounds(125, 50, 60, 30);
		btnCariDataNaracoba.setActionCommand("btnCariDataNaracoba");
		btnCariDataNaracoba.addMouseListener(new MouseController());
		btnCariDataNaracoba.addActionListener(new ButtonController());
		panelChartWavelet.add(btnCariDataNaracoba);
		
		JLabel lblPilihSinyal = new JLabel("Pilih Sinyal :");
		lblPilihSinyal.setFont(lblPilihSinyal.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihSinyal.setBounds(15, 80, 150, 30);
		panelChartWavelet.add(lblPilihSinyal);
		
		cbSinyalRileks = new JCheckBox("Rileks");
		cbSinyalRileks.setBackground(Color.white);
		cbSinyalRileks.setBounds(15, 105, 70, 30);
		cbSinyalRileks.setEnabled(false);
		panelChartWavelet.add(cbSinyalRileks);
		
		cbSinyalNonRileks = new JCheckBox("Non-Rileks");
		cbSinyalNonRileks.setBackground(Color.white);
		cbSinyalNonRileks.setBounds(90, 105, 100, 30);
		cbSinyalNonRileks.setEnabled(false);
		panelChartWavelet.add(cbSinyalNonRileks);
		
		JLabel lblPilihGelombang = new JLabel("Pilih Gelombang :");
		lblPilihGelombang.setFont(lblPilihGelombang.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihGelombang.setBounds(15, 130, 150, 30);
		panelChartWavelet.add(lblPilihGelombang);
		
		cbGelAlfa = new JCheckBox("Alfa");
		cbGelAlfa.setBackground(Color.white);
		cbGelAlfa.setBounds(15, 160, 55, 30);
		cbGelAlfa.setBackground(Color.white);
		cbGelAlfa.setEnabled(false);
		panelChartWavelet.add(cbGelAlfa);
		
		cbGelBeta = new JCheckBox("Beta");
		cbGelBeta.setBackground(Color.white);
		cbGelBeta.setBounds(70, 160, 60, 30);
		cbGelBeta.setEnabled(false);
		panelChartWavelet.add(cbGelBeta);
		
		cbGelTeta = new JCheckBox("Teta");
		cbGelTeta.setBackground(Color.white);
		cbGelTeta.setBounds(130, 160, 60, 30);
		cbGelTeta.setEnabled(false);
		panelChartWavelet.add(cbGelTeta);
		
		cbGelFilter = new JCheckBox("Filter");
		cbGelFilter.setBackground(Color.white);
		cbGelFilter.setBounds(15, 185, 60, 30);
		cbGelFilter.setEnabled(false);
		panelChartWavelet.add(cbGelFilter);
		
		btnLihatGrafik = new JButton("Lihat Grafik");
		btnLihatGrafik.setForeground(Color.white);
		btnLihatGrafik.setBackground(new Color(60, 137, 185));
		btnLihatGrafik.setBounds(15, 220, 170, 30);
		btnLihatGrafik.setEnabled(false);
		btnLihatGrafik.setActionCommand("btnLihatGrafik");
		btnLihatGrafik.addActionListener(new ButtonController());
		btnLihatGrafik.addMouseListener(new MouseController());
		panelChartWavelet.add(btnLihatGrafik);
		
		dataset = createDataSetSinyalAsli(false, false, false, false, false, false, 0);
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
		return ChartFactory.createXYLineChart(
				"Grafik Wavelet", 
				"waktu (detik)", 
				"Amplitudo", 
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void updateCmbNaracoba(){
		String[] naracoba = new String[dbAction.getJumNaracoba()+1];
		DefaultComboBoxModel model;
		
		naracoba[0] = "-";
		for(i=0;i<dbAction.getJumNaracoba();i++){
			naracoba[i+1] = String.valueOf(i+1);
		}
		model = new DefaultComboBoxModel(naracoba);
		
		cmbNaracoba.removeAllItems();
		cmbNaracoba.setModel(model);
		cmbNaracoba.repaint();
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
		tableDataWavelet.getColumnModel().getColumn(4).setCellRenderer(centerTable);
		tableDataWavelet.getColumnModel().getColumn(0).setMinWidth(30);
		tableDataWavelet.getColumnModel().getColumn(0).setMaxWidth(30);
	}
	
	public XYDataset createDataSetSinyalAsli(boolean isUseRileks, boolean isUseNonRileks, boolean isUseAlfa, boolean isUseBeta, boolean isUseTeta, boolean isUseFilter, int naracoba){
		final XYSeriesCollection collection = new XYSeriesCollection();
		int i=0, j=0;
		
		if(isUseRileks == true){
			final XYSeries seriesRileks = new XYSeries("Sinyal Rileks");
			ArrayList<double[]> sinyalRileks = new ArrayList<double[]>();
			sinyalRileks = dbAction.getDataLatihRileks(naracoba);
			int x=0;
			
			for(i=0;i<sinyalRileks.size();i++){
				for(j=0;j<sinyalRileks.get(i).length;j++){
					seriesRileks.add((j+1)+(sinyalRileks.get(i).length*x), sinyalRileks.get(i)[j]);
				}
				x++;
			}
			collection.addSeries(seriesRileks);
		}
		
		if(isUseNonRileks == true){
			final XYSeries seriesNonRileks = new XYSeries("Sinyal Non Rileks");
			ArrayList<double[]> sinyalNonRileks = new ArrayList<double[]>();
			sinyalNonRileks = dbAction.getDataLatihNonRileks(naracoba);
			int x=0;
			
			for(i=0;i<sinyalNonRileks.size();i++){
				for(j=0;j<sinyalNonRileks.get(i).length;j++){
					seriesNonRileks.add((j+1)+(sinyalNonRileks.get(i).length*x), sinyalNonRileks.get(i)[j]);
				}
				x++;
			}
			collection.addSeries(seriesNonRileks);
		}
		
		if(isUseAlfa == true){
			final XYSeries seriesAlfa = new XYSeries("Sinyal Alfa");
			ArrayList<double[][]> sinyalAlfa = new ArrayList<double[][]>();
			sinyalAlfa = dbAction.getAlfaByNaracoba(naracoba);
			
			for(i=0;i<sinyalAlfa.size();i++){
				for(j=0;j<sinyalAlfa.get(i).length;j++){
					seriesAlfa.add(sinyalAlfa.get(i)[j][1], sinyalAlfa.get(i)[j][0]);
				}
			}
			collection.addSeries(seriesAlfa);
		}
		
		if(isUseBeta == true){
			final XYSeries seriesBeta = new XYSeries("Sinyal Beta");
			ArrayList<double[][]> sinyalBeta = new ArrayList<double[][]>();
			sinyalBeta = dbAction.getBetaByNaracoba(naracoba);
			
			for(i=0;i<sinyalBeta.size();i++){
				for(j=0;j<sinyalBeta.get(i).length;j++){
					seriesBeta.add(sinyalBeta.get(i)[j][1], sinyalBeta.get(i)[j][0]);
				}
			}
			collection.addSeries(seriesBeta);
		}
		
		if(isUseTeta == true){
			final XYSeries seriesTeta = new XYSeries("Sinyal Teta");
			ArrayList<double[][]> sinyalTeta = new ArrayList<double[][]>();
			sinyalTeta = dbAction.getTetaByNaracoba(naracoba);
			
			for(i=0;i<sinyalTeta.size();i++){
				for(j=0;j<sinyalTeta.get(i).length;j++){
					seriesTeta.add(sinyalTeta.get(i)[j][1], sinyalTeta.get(i)[j][0]);
				}
			}
			
			collection.addSeries(seriesTeta);
		}
		
		if(isUseFilter == true){
			final XYSeries seriesFilter = new XYSeries("Sinyal Filter");
			ArrayList<double[][]> sinyalFilter = new ArrayList<double[][]>();
			sinyalFilter = dbAction.getFilterByNaracoba(naracoba);

			for(i=0;i<sinyalFilter.size();i++){
				for(j=0;j<sinyalFilter.get(i).length;j++){
					seriesFilter.add(sinyalFilter.get(i)[j][1], sinyalFilter.get(i)[j][0]);
				}
			}
			collection.addSeries(seriesFilter);
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
				if(rdWaveletGelombang.isSelected()){
					CoreEkstraksiWavelet coreEkstraksiWavelet = new CoreEkstraksiWavelet();
					coreEkstraksiWavelet.execute();
				}else if(rdWaveletFilter.isSelected()){
					CoreFilteringWavelet coreFilteringWavelet = new CoreFilteringWavelet();
					coreFilteringWavelet.execute();
				}else if(!rdWaveletFilter.isSelected() && !rdWaveletGelombang.isSelected()){
					JOptionPane.showMessageDialog(null, "Pilih salah satu tipe wavelet!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
				}
			}else if(e.getActionCommand().equals("cmbNaracoba")){
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
				cbGelFilter.setEnabled(false);
				cbGelFilter.setSelected(false);
				btnLihatGrafik.setEnabled(false);
			}else if(e.getActionCommand().equals("btnCariDataNaracoba")){
				if((String)cmbNaracoba.getSelectedItem() == "-"){
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
					cbGelFilter.setEnabled(false);
					cbGelFilter.setSelected(false);
					btnLihatGrafik.setEnabled(false);
				}else{
					int kelas = dbAction.getKelasFromDataLatih(Integer.parseInt((String)cmbNaracoba.getSelectedItem()));
					
					if(dbAction.getStatusWavelet() == "ekstraksi"){
						cbGelAlfa.setEnabled(true);
						cbGelAlfa.setSelected(false);
						cbGelBeta.setEnabled(true);
						cbGelBeta.setSelected(false);
						cbGelTeta.setEnabled(true);
						cbGelTeta.setSelected(false);
						cbGelFilter.setEnabled(false);
						cbGelFilter.setSelected(false);
					}else if(dbAction.getStatusWavelet() == "filter"){
						cbGelAlfa.setEnabled(false);
						cbGelAlfa.setSelected(false);
						cbGelBeta.setEnabled(false);
						cbGelBeta.setSelected(false);
						cbGelTeta.setEnabled(false);
						cbGelTeta.setSelected(false);
						cbGelFilter.setEnabled(true);
						cbGelFilter.setSelected(false);
					}
					
					btnLihatGrafik.setEnabled(true);
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
				}else if((dbAction.getDataLatih().size()*dbAction.getDataLatih().get(0).size()) != dbAction.getJumDataWavelet()){
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
				if(cbGelFilter.isSelected()){
					isUseFilter = true;
				}
				
				dataset = createDataSetSinyalAsli(isUseRileks, isUseNonRileks, isUseAlfa, isUseBeta, isUseTeta, isUseFilter, Integer.parseInt((String)cmbNaracoba.getSelectedItem()));
				updateGrafikWavelet(dataset);
				isUseRileks = false;
				isUseNonRileks = false;
				isUseAlfa = false;
				isUseBeta = false;
				isUseTeta = false;
				isUseFilter = false;
			}
		}
	}
	
	class CoreEkstraksiWavelet extends SwingWorker<Void, Void>{
		
		WaveletEkstraksi waveletEkstraksi;
		ArrayList<ArrayList<double[]>> sinyalEEG;
		List idOfDataLatih = dbAction.getIdOfDataLatih();
		double[][] alfa = null, beta = null, teta = null;
		int i=0, progress=0, progressDistance;
		
		public CoreEkstraksiWavelet() {
			// TODO Auto-generated constructor stub
			sinyalEEG = dbAction.getDataLatih();
			waveletEkstraksi = new WaveletEkstraksi(sinyalEEG);
			idOfDataLatih = dbAction.getIdOfDataLatih();
			lblStatusLoading.setVisible(true);
			progressEkstraksiWavelet.setValue(0);
			progressDistance = 1000/(waveletEkstraksi.sinyalEEG.size()*3);
		}

		@Override
		protected Void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			dbAction.deleteEkstraksiWavelet();
			updateTabelEkstraksiWavelet();
			for(i=0;i<waveletEkstraksi.sinyalEEG.size();i++){
				alfa = waveletEkstraksi.getAlfa(waveletEkstraksi.sinyalEEG.get(i), dbAction.getSamplingRate());
				progressEkstraksiWavelet.setValue(progress+=progressDistance);
				beta = waveletEkstraksi.getBeta(waveletEkstraksi.sinyalEEG.get(i), dbAction.getSamplingRate());
				progressEkstraksiWavelet.setValue(progress+=progressDistance);
				teta = waveletEkstraksi.getTeta(waveletEkstraksi.sinyalEEG.get(i), dbAction.getSamplingRate());
				progressEkstraksiWavelet.setValue(progress+=progressDistance);
				dbAction.inputEkstraksiWavelet(alfa, beta, teta, null, Integer.parseInt(idOfDataLatih.getItem(i)));
				updateTabelEkstraksiWavelet();
			}
			return null;
		}
		
		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();
			ViewController.refreshAllElement();
			progressEkstraksiWavelet.setValue(1000);
			JOptionPane.showMessageDialog(null, "Proses Ekstraksi Berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
			lblStatusLoading.setVisible(false);
			progressEkstraksiWavelet.setValue(0);
		}
	}
	
	class CoreFilteringWavelet extends SwingWorker<Void, Void>{
		
		WaveletFiltering waveletFilter;
		ArrayList<ArrayList<double[]>> sinyalEEG;
		List idOfDataLatih;
		double[][] freq5to8=null, freq9to16=null, freq17to24=null, freq25to28=null, freq29to30=null, gabung=null, hasilUrut=null;
		double[] hasilSinyal=null;
		int i=0, progress=0, progressDistance;
		
		public CoreFilteringWavelet() {
			// TODO Auto-generated constructor stub
			sinyalEEG = dbAction.getDataLatih();
			waveletFilter = new WaveletFiltering(sinyalEEG);
			idOfDataLatih = dbAction.getIdOfDataLatih();
			lblStatusLoading.setVisible(true);
			progressEkstraksiWavelet.setValue(0);
			progressDistance = 1000/(waveletFilter.sinyalEEG.size()*8);
		}

		@Override
		protected Void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			dbAction.deleteEkstraksiWavelet();
			updateTabelEkstraksiWavelet();
			for(i=0;i<waveletFilter.sinyalEEG.size();i++){
				freq5to8 = waveletFilter.getFrekuensi5to8(waveletFilter.sinyalEEG.get(i), dbAction.getSamplingRate());
				progressEkstraksiWavelet.setValue(progress+=progressDistance);
				freq9to16 = waveletFilter.getFrekuensi9to16(waveletFilter.sinyalEEG.get(i), dbAction.getSamplingRate());
				progressEkstraksiWavelet.setValue(progress+=progressDistance);
				freq17to24 = waveletFilter.getFrekuensi17to24(waveletFilter.sinyalEEG.get(i), dbAction.getSamplingRate());
				progressEkstraksiWavelet.setValue(progress+=progressDistance);
				freq25to28 = waveletFilter.getFrekuensi25to28(waveletFilter.sinyalEEG.get(i), dbAction.getSamplingRate());
				progressEkstraksiWavelet.setValue(progress+=progressDistance);
				freq29to30 = waveletFilter.getFrekuensi29to30(waveletFilter.sinyalEEG.get(i), dbAction.getSamplingRate());
				progressEkstraksiWavelet.setValue(progress+=progressDistance);
				gabung = waveletFilter.gabungkanSinyal(freq5to8, freq9to16, freq17to24, freq25to28, freq29to30);
				progressEkstraksiWavelet.setValue(progress+=progressDistance);
				hasilUrut = waveletFilter.pengurutanSinyal(gabung);
				progressEkstraksiWavelet.setValue(progress+=progressDistance);
//				hasilSinyal = waveletFilter.getSinyalHasilFiltering(hasilUrut);
//				progressEkstraksiWavelet.setValue(progress+=progressDistance);
				dbAction.inputEkstraksiWavelet(null, null, null, hasilUrut, Integer.parseInt(idOfDataLatih.getItem(i)));
				updateTabelEkstraksiWavelet();
			}
			return null;
		}
		
		@Override
		protected void done() {
			// TODO Auto-generated method stub
			super.done();
			ViewController.refreshAllElement();
			progressEkstraksiWavelet.setValue(1000);
			JOptionPane.showMessageDialog(null, "Proses Filtering Berhasil", "Sukses", JOptionPane.INFORMATION_MESSAGE);
			lblStatusLoading.setVisible(false);
			progressEkstraksiWavelet.setValue(0);
		}
	}
}
