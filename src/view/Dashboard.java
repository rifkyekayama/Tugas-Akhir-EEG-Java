package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mysql.Database;

public class Dashboard extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JLabel lblJumNaracoba, lblJumSegmentasi, lblJumRileks, lblJumNonRileks;
	protected Database dbAction = new Database();;
	
	public Dashboard(){
		setSize(1200, 650);
		setLayout(null);
		add(getContent());
		add(new Layout("Dashboard"));
		
		lblJumNaracoba.setText(Integer.toString(dbAction.getJumNaracoba()));
		lblJumSegmentasi.setText(Integer.toString(dbAction.getJumSegmentasi()));
		lblJumRileks.setText(Integer.toString(dbAction.getJumRileks()));
		lblJumNonRileks.setText(Integer.toString(dbAction.getJumNonRileks()));
	}
	
	public JPanel getContent(){
		JPanel panelContent = new JPanel();
		panelContent.setBounds(280, 60, 910, 530);
		panelContent.setLayout(null);
		
		JPanel panelJudul = new JPanel();
		panelJudul.setBackground(Color.white);
		panelJudul.setLayout(new GridBagLayout());
		panelJudul.setBounds(0, 0, panelContent.getWidth(), 100);
		
		JLabel lblJudul = new JLabel("<html><center>IDENTIFIKASI KONDISI RILEKS BERDASARKAN SINYAL ELEKTROENSEPHALOGRAM <br> MENGGUNAKAN EKSTRAKSI WAVELET DAN LEARNING VECTOR QUANTIZATION</center></html>");
		lblJudul.setFont(lblJudul.getFont().deriveFont(Font.BOLD, 18f));
		panelJudul.add(lblJudul);
		
		JPanel panelWidgetNaracoba = new JPanel();
		panelWidgetNaracoba.setBackground(new Color(243, 156, 18));
		panelWidgetNaracoba.setLayout(null);
		panelWidgetNaracoba.setBounds(0, 120, 220, 130);
		
		lblJumNaracoba = new JLabel("0");
		lblJumNaracoba.setFont(lblJumNaracoba.getFont().deriveFont(40f));
		lblJumNaracoba.setForeground(Color.white);
		lblJumNaracoba.setBounds(10, -15, 90, 100);
		panelWidgetNaracoba.add(lblJumNaracoba);
		
		JLabel lblKetNaracoba = new JLabel("Naracoba");
		lblKetNaracoba.setForeground(Color.white);
		lblKetNaracoba.setBounds(10, 60, 100, 30);
		panelWidgetNaracoba.add(lblKetNaracoba);
		
		JLabel iconNaracoba = new JLabel(new ImageIcon(getClass().getResource("/resource/iconWidgetNaracoba.png")));
		iconNaracoba.setBounds(panelWidgetNaracoba.getWidth()-107, 10, 97, 90);
		panelWidgetNaracoba.add(iconNaracoba);
		
		JButton btnNaracoba = new JButton("More Info");
		btnNaracoba.setForeground(Color.white);
		btnNaracoba.setBackground(new Color(221, 140, 41));
		btnNaracoba.setBorderPainted(false);
		btnNaracoba.setActionCommand("btnViewNaracoba");
		btnNaracoba.addMouseListener(new MouseController());
		btnNaracoba.addActionListener(new ButtonController());
		btnNaracoba.setBounds(0, panelWidgetNaracoba.getHeight()-25, panelWidgetNaracoba.getWidth(), 25);
		panelWidgetNaracoba.add(btnNaracoba);
		
		JPanel panelWidgetSegmentasi = new JPanel();
		panelWidgetSegmentasi.setBackground(new Color(0, 166, 90));
		panelWidgetSegmentasi.setLayout(null);
		panelWidgetSegmentasi.setBounds(230, 120, 220, 130);
		
		lblJumSegmentasi = new JLabel("0");
		lblJumSegmentasi.setFont(lblJumSegmentasi.getFont().deriveFont(40f));
		lblJumSegmentasi.setForeground(Color.white);
		lblJumSegmentasi.setBounds(10, -15, 90, 100);
		panelWidgetSegmentasi.add(lblJumSegmentasi);
		
		JLabel lblKetSegmentasi = new JLabel("Segmentasi Data");
		lblKetSegmentasi.setForeground(Color.white);
		lblKetSegmentasi.setBounds(10, 60, 130, 30);
		panelWidgetSegmentasi.add(lblKetSegmentasi);
		
		JLabel iconSegmentasi = new JLabel(new ImageIcon(getClass().getResource("/resource/iconWidgetSegmentasi.png")));
		iconSegmentasi.setBounds(panelWidgetSegmentasi.getWidth()-88, 10, 78, 91);
		panelWidgetSegmentasi.add(iconSegmentasi);
		
		JButton btnSegmentasi = new JButton("More Info");
		btnSegmentasi.setForeground(Color.white);
		btnSegmentasi.setBackground(new Color(0, 158, 87));
		btnSegmentasi.setBorderPainted(false);
		btnSegmentasi.setActionCommand("btnViewSegmentasi");
		btnSegmentasi.addMouseListener(new MouseController());
		btnSegmentasi.addActionListener(new ButtonController());
		btnSegmentasi.setBounds(0, panelWidgetSegmentasi.getHeight()-25, panelWidgetSegmentasi.getWidth(), 25);
		panelWidgetSegmentasi.add(btnSegmentasi);
		
		JPanel panelWidgetRileks = new JPanel();
		panelWidgetRileks.setBackground(new Color(0, 192, 239));
		panelWidgetRileks.setLayout(null);
		panelWidgetRileks.setBounds(460, 120, 220, 130);
		
		lblJumRileks = new JLabel("0");
		lblJumRileks.setFont(lblJumRileks.getFont().deriveFont(40f));
		lblJumRileks.setForeground(Color.white);
		lblJumRileks.setBounds(10, -15, 90, 100);
		panelWidgetRileks.add(lblJumRileks);
		
		JLabel lblKetRileks = new JLabel("Kelas Rileks");
		lblKetRileks.setForeground(Color.white);
		lblKetRileks.setBounds(10, 60, 130, 30);
		panelWidgetRileks.add(lblKetRileks);
		
		JLabel iconRileks = new JLabel(new ImageIcon(getClass().getResource("/resource/iconWidgetRileks.png")));
		iconRileks.setBounds(panelWidgetRileks.getWidth()-113, 10, 103, 64);
		panelWidgetRileks.add(iconRileks);
		
		JButton btnRileks = new JButton("More Info");
		btnRileks.setForeground(Color.white);
		btnRileks.setBackground(new Color(0, 171, 213));
		btnRileks.setBorderPainted(false);
		btnRileks.setActionCommand("btnViewRileks");
		btnRileks.addMouseListener(new MouseController());
		btnRileks.addActionListener(new ButtonController());
		btnRileks.setBounds(0, panelWidgetRileks.getHeight()-25, panelWidgetRileks.getWidth(), 25);
		panelWidgetRileks.add(btnRileks);
		
		JPanel panelWidgetNonRileks = new JPanel();
		panelWidgetNonRileks.setBackground(new Color(221, 75, 57));
		panelWidgetNonRileks.setLayout(null);
		panelWidgetNonRileks.setBounds(panelContent.getWidth()-220, 120, 220, 130);
		
		lblJumNonRileks = new JLabel("0");
		lblJumNonRileks.setFont(lblJumNonRileks.getFont().deriveFont(40f));
		lblJumNonRileks.setForeground(Color.white);
		lblJumNonRileks.setBounds(10, -15, 90, 100);
		panelWidgetNonRileks.add(lblJumNonRileks);
		
		JLabel lblKetNonRileks = new JLabel("Kelas Non-Rileks");
		lblKetNonRileks.setForeground(Color.white);
		lblKetNonRileks.setBounds(10, 60, 130, 30);
		panelWidgetNonRileks.add(lblKetNonRileks);
		
		JLabel iconNonRileks = new JLabel(new ImageIcon(getClass().getResource("/resource/iconWidgetNonRileks.png")));
		iconNonRileks.setBounds(panelWidgetNonRileks.getWidth()-100, 10, 90, 78);
		panelWidgetNonRileks.add(iconNonRileks);
		
		JButton btnNonRileks = new JButton("More Info");
		btnNonRileks.setForeground(Color.white);
		btnNonRileks.setBackground(new Color(204, 50, 55));
		btnNonRileks.setBorderPainted(false);
		btnNonRileks.setActionCommand("btnViewNonRileks");
		btnNonRileks.addMouseListener(new MouseController());
		btnNonRileks.addActionListener(new ButtonController());
		btnNonRileks.setBounds(0, panelWidgetNonRileks.getHeight()-25, panelWidgetNonRileks.getWidth(), 25);
		panelWidgetNonRileks.add(btnNonRileks);
		
		panelContent.add(panelJudul);
		panelContent.add(panelWidgetNaracoba);
		panelContent.add(panelWidgetSegmentasi);
		panelContent.add(panelWidgetRileks);
		panelContent.add(panelWidgetNonRileks);
		
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
			if(e.getActionCommand().equals("btnViewNaracoba")){
				Home.changeCard("panelKelolaDataLatih");
			}else if(e.getActionCommand().equals("btnViewSegmentasi")){
				Home.changeCard("panelKelolaDataLatih");
			}else if(e.getActionCommand().equals("btnViewRileks")){
				Home.changeCard("panelKelolaDataLatih");
			}else if(e.getActionCommand().equals("btnViewNonRileks")){
				Home.changeCard("panelKelolaDataLatih");
			}
		}
	}
}
