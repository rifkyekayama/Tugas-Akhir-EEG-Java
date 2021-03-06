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
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import lvq.LVQ;
import mysql.Database;

public class Pelatihan extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JTextField txtMaksimumEpoch, txtMinimumError, txtLearningRate, txtPenguranganLR;
	protected JButton btnPelatihan;
	public JProgressBar progressBarPelatihan;
	public JLabel lblStatusLoading;
	public static JTextArea txtAreaProgressMonitor;
	protected DefaultTableModel tableModel;
	protected JTable tableBobot;
	protected JScrollPane scrollTableBobot, scrollTextAreaProgressMonitor;
	protected DefaultTableCellRenderer centerTable;
	protected Database dbAction = new Database();
	
	public Pelatihan(){
		setSize(1200, 650);
		setLayout(null);
		dbAction = new Database();
		tableModel = dbAction.getListDataBobot();
		centerTable = new DefaultTableCellRenderer();
		centerTable.setHorizontalAlignment(SwingConstants.CENTER);
		add(getContent());
		add(new MenuUtama("Pelatihan Sistem"));
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
		lblMaksimumEpoch.setFont(lblMaksimumEpoch.getFont().deriveFont(Font.BOLD, 15f));
		lblMaksimumEpoch.setBounds(15, 30, 150, 30);
		panelFormPelatihan.add(lblMaksimumEpoch);
		
		txtMaksimumEpoch = new JTextField("10");
		txtMaksimumEpoch.setBounds(15, 60, 205, 30);
		panelFormPelatihan.add(txtMaksimumEpoch);
		
		JLabel lblMinimumError = new JLabel("Minimum Error:");
		lblMinimumError.setFont(lblMinimumError.getFont().deriveFont(Font.BOLD, 15f));
		lblMinimumError.setBounds(panelFormPelatihan.getWidth()-220, 30, 150, 30);
		panelFormPelatihan.add(lblMinimumError);
		
		txtMinimumError = new JTextField("0.0001");
		txtMinimumError.setBounds(panelFormPelatihan.getWidth()-220, 60, 205, 30);
		panelFormPelatihan.add(txtMinimumError);
		
		JLabel lblLearningRate = new JLabel("Learning Rate:");
		lblLearningRate.setFont(lblLearningRate.getFont().deriveFont(Font.BOLD, 15f));
		lblLearningRate.setBounds(15, 100, 150, 30);
		panelFormPelatihan.add(lblLearningRate);
		
		txtLearningRate = new JTextField("0.05");
		txtLearningRate.setBounds(15, 130, 205, 30);
		panelFormPelatihan.add(txtLearningRate);
		
		JLabel lblPenguranganLR = new JLabel("Pengurangan LR:");
		lblPenguranganLR.setFont(lblPenguranganLR.getFont().deriveFont(Font.BOLD, 15f));
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
		
		progressBarPelatihan = new JProgressBar();
		progressBarPelatihan.setForeground(new Color(44, 195, 107));
		progressBarPelatihan.setBackground(new Color(251, 252, 252));
		progressBarPelatihan.setStringPainted(true);
		progressBarPelatihan.setBounds(15, panelFormPelatihan.getHeight()+15, panelFormPelatihan.getWidth()-30, 30);
		
		JPanel panelLblStatusLoading = new JPanel(new GridBagLayout());
		panelLblStatusLoading.setBounds(15, panelFormPelatihan.getHeight()+45, panelFormPelatihan.getWidth()-30, 30);
		
		lblStatusLoading = new JLabel("Loading...");
		lblStatusLoading.setVisible(true);
		panelLblStatusLoading.add(lblStatusLoading);
		
		JLabel lblTxtAreaProgressMonitor = new JLabel("Proses Pelatihan");
		lblTxtAreaProgressMonitor.setBounds(0, 330, 450, 30);
		panelContent.add(lblTxtAreaProgressMonitor);
		
		JPanel panelTextAreaProgressMonitor = new JPanel();
		panelTextAreaProgressMonitor.setLayout(new BorderLayout());
		panelTextAreaProgressMonitor.setBounds(0, 360, 450, 150);
		
		txtAreaProgressMonitor = new JTextArea(10,5);
		txtAreaProgressMonitor.setEditable(false);
		txtAreaProgressMonitor.setBorder(null);
		txtAreaProgressMonitor.setVisible(true);
		
		scrollTextAreaProgressMonitor = new JScrollPane(txtAreaProgressMonitor);
		panelTextAreaProgressMonitor.add(scrollTextAreaProgressMonitor, "Center");
		
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
		tableBobot.getColumnModel().getColumn(0).setMinWidth(50);
		tableBobot.getColumnModel().getColumn(0).setMaxWidth(50);
		
		scrollTableBobot = new JScrollPane(tableBobot);
		scrollTableBobot.setVisible(true);
		panelTableDataBobot.add(scrollTableBobot, BorderLayout.CENTER);
		
		panelContent.add(panelFormPelatihan);
		panelContent.add(progressBarPelatihan);
		panelContent.add(panelTextAreaProgressMonitor);
		panelContent.add(panelLblStatusLoading);
		panelContent.add(panelTabelPelatihan);
		
		return panelContent;
	}
	
	public void updateTableDataBobot(){
		tableBobot.setModel(dbAction.getListDataBobot());
		tableBobot.repaint();
		tableBobot.setRowSelectionAllowed(false);
		tableBobot.setPreferredScrollableViewportSize(getSize());
		tableBobot.setFillsViewportHeight(true);
		tableBobot.getColumnModel().getColumn(0).setCellRenderer(centerTable);
		tableBobot.getColumnModel().getColumn(1).setCellRenderer(centerTable);
		tableBobot.getColumnModel().getColumn(2).setCellRenderer(centerTable);
		tableBobot.getColumnModel().getColumn(0).setMinWidth(50);
		tableBobot.getColumnModel().getColumn(0).setMaxWidth(50);
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
			if(e.getActionCommand().equals("mulaiPelatihan")){
				if(txtMaksimumEpoch.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Kolom Maksimum Epoch tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(txtMinimumError.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Kolom Minimum Error tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(txtLearningRate.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Kolom Learning Rate tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(txtPenguranganLR.getText().isEmpty()){
					JOptionPane.showMessageDialog(null, "Kolom Pengurangan Learning Rate tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(dbAction.getNeuronRileks().size() == 0){
					JOptionPane.showMessageDialog(null, "Data kelas RILEKS belum diekstraksi", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else if(dbAction.getNeuronNonRileks().size() == 0){
					JOptionPane.showMessageDialog(null, "Data kelas Non-RILEKS belum diekstraksi", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else{
					CorePelatihan corePelatihan = new CorePelatihan();
					corePelatihan.execute();
				}
			}
		}
	}
	
	class CorePelatihan extends SwingWorker<Void, Void>{
		
		ArrayList<double[][]> neuronRileks = new ArrayList<double[][]>();
		ArrayList<double[][]> neuronNonRileks = new ArrayList<double[][]>();
		double[][] belajar;
		LVQ lvq = new LVQ();
		
		public CorePelatihan() {
			// TODO Auto-generated constructor stub
			lblStatusLoading.setVisible(true);
			txtAreaProgressMonitor.setText("");
		}
		
		@Override
		protected Void doInBackground() throws Exception {
			// TODO Auto-generated method stub
			lblStatusLoading.setText("Pembangkitan Neuron Rileks");
			progressBarPelatihan.setValue(25);
			neuronRileks = dbAction.getNeuronRileks();
			lblStatusLoading.setText("Pembangkitan Neuron Tidak Rileks");
			progressBarPelatihan.setValue(50);
			neuronNonRileks = dbAction.getNeuronNonRileks();
			lblStatusLoading.setText("Pembelajaran LVQ");
			progressBarPelatihan.setValue(75);
			belajar = lvq.pembelajaran(neuronRileks, neuronNonRileks, Double.parseDouble(txtLearningRate.getText()), Double.parseDouble(txtPenguranganLR.getText()), Integer.parseInt(txtMaksimumEpoch.getText()), Double.parseDouble(txtMinimumError.getText()));
			lblStatusLoading.setText("Input hasil pelatihan bobot ke DB");
			progressBarPelatihan.setValue(90);
			dbAction.inputHasilBobot(belajar);
			return null;
		}
		
		@Override
		public void done(){
			progressBarPelatihan.setValue(100);
			lblStatusLoading.setVisible(false);
			progressBarPelatihan.setValue(0);
			ViewController.refreshAllElement();
			updateTableDataBobot();
			JOptionPane.showMessageDialog(null, "Proses pelatihan beres", "Pelatihan", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
