package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import mysql.Database;

public class EditDataLatih extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected DefaultTableModel tableModel;
	protected JComboBox<?> cmbNaracobaEdit, cmbNaracobaHapus, cmbKelas;
	protected JButton btnEditDataLatih, btnHapusDataLatih, btnCariDataNaracoba;
	protected JTable tableDataLatih;
	protected JScrollPane scrollTableDataLatih;
	protected DefaultTableCellRenderer centerTable;
	protected Database dbAction;
	private String[] naracobaEdit, naracobaHapus;
	protected String[] kelas = {"Pilih salah satu...", "Rileks", "Non-Rileks"};
	protected int i=0;
	
	public EditDataLatih() {
		setSize(1200, 650);
		setLayout(null);
		dbAction = new Database();
		tableModel = dbAction.getListDataLatih();
		centerTable = new DefaultTableCellRenderer();
		centerTable.setHorizontalAlignment(SwingConstants.CENTER);
		
		naracobaEdit = new String[dbAction.getJumNaracoba()+1];
		naracobaEdit[0] = "-";
		for(i=0;i<dbAction.getJumNaracoba();i++){
			naracobaEdit[i+1] = Integer.toString(i+1);
		}
		
		naracobaHapus = new String[dbAction.getJumNaracoba()+2];
		naracobaHapus[0] = "-";
		for(i=0;i<dbAction.getJumNaracoba();i++){
			naracobaHapus[i+1] = Integer.toString(i+1);
		}
		naracobaHapus[naracobaHapus.length-1] = "Semua";
		
		add(getContent());
		add(new Layout("Edit/Hapus Data Latih"));
	}
	
	public JPanel getContent(){
		JPanel panelContent = new JPanel();
		panelContent.setBounds(280, 60, 910, 530);
		panelContent.setLayout(null);
		
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
		
		cmbKelas = new JComboBox<>(kelas);
		cmbKelas.setBackground(Color.white);
		cmbKelas.setBounds(15, 140, 150, 30);
		cmbKelas.setEnabled(false);
		panelFormEditDataLatih.add(cmbKelas);
		
		btnEditDataLatih = new JButton("Ubah Data Latih");
		btnEditDataLatih.setForeground(Color.white);
		btnEditDataLatih.setBackground(new Color(60, 137, 185));
		btnEditDataLatih.setBounds(15, 190, panelFormEditDataLatih.getWidth()-30, 50);
		btnEditDataLatih.setEnabled(false);
		btnEditDataLatih.setActionCommand("btnEditDataLatih");
		btnEditDataLatih.addMouseListener(new MouseController());
		btnEditDataLatih.addActionListener(new ButtonController());
		panelFormEditDataLatih.add(btnEditDataLatih);
		
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
		
		JPanel panelKelolaDataLatih = new JPanel();
		panelKelolaDataLatih.setLayout(null);
		panelKelolaDataLatih.setBackground(Color.white);
		panelKelolaDataLatih.setBounds(panelLihatDataLatih.getX(), panelLihatDataLatih.getHeight()+10, panelLihatDataLatih.getWidth(), 60);
		
		JButton btnKelolaDataLatih = new JButton("Kelola Data Latih");
		btnKelolaDataLatih.setActionCommand("btnKelolaDataLatih");
		btnKelolaDataLatih.setBackground(new Color(60, 137, 185));
		btnKelolaDataLatih.setForeground(Color.white);
		btnKelolaDataLatih.setBorderPainted(false);
		btnKelolaDataLatih.addMouseListener(new MouseController());
		btnKelolaDataLatih.addActionListener(new ButtonController());
		btnKelolaDataLatih.setBounds(15, 15, panelKelolaDataLatih.getWidth()-(15*2), panelKelolaDataLatih.getHeight()-(15*2));
		panelKelolaDataLatih.add(btnKelolaDataLatih);
		
		panelContent.add(panelFormEditDataLatih);
		panelContent.add(panelHapusDataLatih);
		panelContent.add(panelLihatDataLatih);
		panelContent.add(panelKelolaDataLatih);
		return panelContent;
	}
	
	public void updateTableEditDataLatih(){
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void updateCmbEditNaracoba(){
		String[] naracobaEdit = new String[dbAction.getJumNaracoba()+1];
		String[] naracobaHapus = new String[dbAction.getJumNaracoba()+2];
		DefaultComboBoxModel modelNaracobaEdit, modelNaracobaHapus;
		
		naracobaEdit[0] = "-";
		for(i=0;i<dbAction.getJumNaracoba();i++){
			naracobaEdit[i+1] = Integer.toString(i+1);
		}
		modelNaracobaEdit = new DefaultComboBoxModel(naracobaEdit);
		
		naracobaHapus[0] = "-";
		for(i=0;i<dbAction.getJumNaracoba();i++){
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
			if(e.getActionCommand().equals("btnKelolaDataLatih")){
				Home.changeCard("panelKelolaDataLatih");
			}else if(e.getActionCommand().equals("btnCariDataNaracoba")){
				if((String)cmbNaracobaEdit.getSelectedItem() != "-"){
					int kelasNaracoba = dbAction.getKelasFromDataLatih(Integer.parseInt((String)cmbNaracobaEdit.getSelectedItem()));
					if(kelasNaracoba == 1){
						cmbKelas.setSelectedIndex(1);
					}else if(kelasNaracoba == -1){
						cmbKelas.setSelectedIndex(2);
					}
					cmbKelas.setEnabled(true);
					btnEditDataLatih.setEnabled(true);
				}else{
					cmbKelas.setSelectedIndex(0);
					cmbKelas.setEnabled(false);
					btnEditDataLatih.setEnabled(false);
				}
			}else if(e.getActionCommand().equals("btnEditDataLatih")){
				int indexKelas = 0;
				if((String)cmbNaracobaEdit.getSelectedItem() != "-"){
					if(cmbKelas.getSelectedIndex() == 1){
						indexKelas = 1;
					}else if(cmbKelas.getSelectedIndex() == 2){
						indexKelas = -1;
					}
					dbAction.editDataLatih(indexKelas, Integer.parseInt((String)cmbNaracobaEdit.getSelectedItem()));
				}
				cmbKelas.setSelectedIndex(0);
				cmbKelas.setEnabled(false);
				btnEditDataLatih.setEnabled(false);
				Home.refreshAllElement();
			}else if(e.getActionCommand().equals("btnHapusDataLatih")){
				if((String)cmbNaracobaHapus.getSelectedItem() == "-"){
					JOptionPane.showMessageDialog(null, "Pilihan naracoba tidak boleh kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				}else{
					boolean hasilHapus = dbAction.hapusDataLatih((String)cmbNaracobaHapus.getSelectedItem());
					if(hasilHapus == true){
						Home.refreshAllElement();
						JOptionPane.showMessageDialog(null, "Data latih berhasil dihapus.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
					}else if(hasilHapus == false){
						JOptionPane.showMessageDialog(null, "Data latih gagal dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
		
	}
	
}