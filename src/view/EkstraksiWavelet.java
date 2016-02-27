package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class EkstraksiWavelet extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EkstraksiWavelet() {
		setSize(1200, 650);
		setLayout(null);
		add(getContent());
		add(new Layout("Ekstraksi Wavelet"));
	}
	
	public JPanel getContent(){
		JPanel panelContent = new JPanel();
		panelContent.setBounds(280, 60, 910, 530);
		panelContent.setLayout(null);
		
		JPanel panelFormWavelet = new JPanel();
		panelFormWavelet.setLayout(null);
		panelFormWavelet.setBounds(0, 0, 450, 265);
		panelFormWavelet.setBackground(Color.white);
		
		JLabel lblTitleEkstraksiWavelet = new JLabel("Ekstraksi Wavelet");
		lblTitleEkstraksiWavelet.setForeground(new Color(68, 68, 68));
		lblTitleEkstraksiWavelet.setBounds(15, 0, 150, 30);
		panelFormWavelet.add(lblTitleEkstraksiWavelet);
		
		JLabel lblTipeWavelet = new JLabel("Pilih Tipe Wavelet :");
		lblTipeWavelet.setFont(lblTipeWavelet.getFont().deriveFont(Font.BOLD, 15f));
		lblTipeWavelet.setBounds(15, 30, 170, 30);
		panelFormWavelet.add(lblTipeWavelet);
		
		JRadioButton rdWaveletFilter = new JRadioButton("Filter");
		rdWaveletFilter.setBackground(Color.white);
		rdWaveletFilter.setBounds(200, 30, 70, 30);
		panelFormWavelet.add(rdWaveletFilter);
		
		JRadioButton rdWaveletGelombang = new JRadioButton("Gelombang");
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
		
		JRadioButton RdDb4 = new JRadioButton("4 Koefisien");
		RdDb4.setBackground(Color.white);
		RdDb4.setBounds(130, 80, 110, 30);
		panelFormWavelet.add(RdDb4);
		
		JRadioButton RdDb8 = new JRadioButton("8 Koefisien");
		RdDb8.setBackground(Color.white);
		RdDb8.setBounds(260, 80, 110, 30);
		panelFormWavelet.add(RdDb8);
		
		JPanel panelTabelWavelet = new JPanel();
		panelTabelWavelet.setLayout(null);
		panelTabelWavelet.setBounds(460, 0, 450, 265);
		panelTabelWavelet.setBackground(Color.white);
		
		panelContent.add(panelFormWavelet);
		panelContent.add(panelTabelWavelet);
		return panelContent;
	}

}
