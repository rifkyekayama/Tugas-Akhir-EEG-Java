package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import mysql.DatabaseAction;

public class PelatihanSistem extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected JTextField txtMaksimumEpoch, txtMinimumError, txtLearningRate, txtPenguranganLR;
	protected JButton btnPelatihan;
	protected DefaultTableModel tableModel;
	protected JTable tableBobot;
	protected JScrollPane scrollTableBobot;
	protected DefaultTableCellRenderer centerTable;
	protected DatabaseAction dbAction;
	
	public PelatihanSistem(){
		setSize(1200, 650);
		setLayout(null);
		dbAction = new DatabaseAction();
		tableModel = dbAction.getListDataBobot();
		centerTable = new DefaultTableCellRenderer();
		centerTable.setHorizontalAlignment(SwingConstants.CENTER);
		add(getContent());
		add(new Layout("Pelatihan Sistem"));
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
		
		scrollTableBobot = new JScrollPane(tableBobot);
		scrollTableBobot.setVisible(true);
		panelTableDataBobot.add(scrollTableBobot, BorderLayout.CENTER);
		
		panelContent.add(panelFormPelatihan);
		panelContent.add(panelTabelPelatihan);
		
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
			
		}
	}
}
