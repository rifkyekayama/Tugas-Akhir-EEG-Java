package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Bantuan extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Bantuan() {
		// TODO Auto-generated constructor stub
		setSize(1200, 650);
		setLayout(null);
		add(getContent());
		add(new Menu("Bantuan"));
	}
	
	public JPanel getContent(){
		JPanel panelContent = new JPanel();
		panelContent.setBounds(280, 60, 910, 530);
		panelContent.setLayout(null);
		
		JPanel panelKomponen = new JPanel();
		panelKomponen.setBounds(0, 60, 910, 400);
		panelKomponen.setLayout(null);
		panelContent.add(panelKomponen);
		
		JLabel lblTitleBeranda = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuTitleBeranda.png")));
		lblTitleBeranda.setBounds(55, 0, 61, 30);
		panelKomponen.add(lblTitleBeranda);
		
		JLabel lblIconBeranda = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuIconBeranda.png")));
		lblIconBeranda.setBounds(17, 0, 30, 30);
		panelKomponen.add(lblIconBeranda);
		
		JPanel panelBeranda = new JPanel();
		panelBeranda.setLayout(null);
		panelBeranda.setBackground(Color.white);
		panelBeranda.setBounds(130, 0, 730, 40);
		panelKomponen.add(panelBeranda);
		
		JLabel lblPenjelasanBeranda = new JLabel("<html>Merupakan halaman yang menampilkan "
				+ "Judul program dan jumlah data latih yang tersimpan di database.<br> "
				+ "Terdiri dari Jumlah Naracoba, Jumlah Segmentasi Data, Jumlah Naracoba Kelas Rileks"
				+ ", dan Jumlah Naracoba Tidak Rileks</html>");
		lblPenjelasanBeranda.setBounds(5, 0, panelBeranda.getWidth()-5, 40);
		panelBeranda.add(lblPenjelasanBeranda);
		
		JLabel lblTitleKelolaDataLatih = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuTitleKelolaDataLatih.png")));
		lblTitleKelolaDataLatih.setBounds(55, 60, 113, 30);
		panelKomponen.add(lblTitleKelolaDataLatih);
		
		JLabel lblIconKelolaDataLatih = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuIconKelolaDataLatih.png")));
		lblIconKelolaDataLatih.setBounds(17, 60, 30, 30);
		panelKomponen.add(lblIconKelolaDataLatih);
		
		JPanel panelKelolaDataLatih = new JPanel();
		panelKelolaDataLatih.setLayout(null);
		panelKelolaDataLatih.setBackground(Color.white);
		panelKelolaDataLatih.setBounds(180, 60, 680, 30);
		panelKomponen.add(panelKelolaDataLatih);
		
		JLabel lblPenjelasanKelolaDataLatih = new JLabel("<html>Merupakan halaman yang berfungsi untuk"
				+ " menambahkan, mengubah dan menghapus Data Latih.</html>");
		lblPenjelasanKelolaDataLatih.setBounds(5, 0, panelBeranda.getWidth()-5, 30);
		panelKelolaDataLatih.add(lblPenjelasanKelolaDataLatih);
		
		JLabel lblTitleEkstraksiWavelet = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuTitleEkstraksiWavelet.png")));
		lblTitleEkstraksiWavelet.setBounds(55, 120, 116, 30);
		panelKomponen.add(lblTitleEkstraksiWavelet);
		
		JLabel lblIconEkstraksiWavelet = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuIconEkstraksiWavelet.png")));
		lblIconEkstraksiWavelet.setBounds(17, 120, 30, 30);
		panelKomponen.add(lblIconEkstraksiWavelet);
		
		JPanel panelEkstraksiWavelet = new JPanel();
		panelEkstraksiWavelet.setLayout(null);
		panelEkstraksiWavelet.setBackground(Color.white);
		panelEkstraksiWavelet.setBounds(180, 120, 680, 40);
		panelKomponen.add(panelEkstraksiWavelet);
		
		JLabel lblPenjelasanEkstraksiWavelet = new JLabel("<html>Merupakan halaman yang berfungsi untuk"
				+ " melakukan Ekstraksi Wavelet dan menampilkan"
				+ "<br> hasil Ekstraksi dalam bentuk grafik.</html>");
		lblPenjelasanEkstraksiWavelet.setBounds(5, 0, panelBeranda.getWidth()-5, 40);
		panelEkstraksiWavelet.add(lblPenjelasanEkstraksiWavelet);
		
		JLabel lblTitlePelatihanLVQ = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuTitlePelatihanLVQ.png")));
		lblTitlePelatihanLVQ.setBounds(55, 180, 94, 30);
		panelKomponen.add(lblTitlePelatihanLVQ);
		
		JLabel lblIconPelatihanLVQ = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuIconPelatihanLVQ.png")));
		lblIconPelatihanLVQ.setBounds(17, 180, 30, 30);
		panelKomponen.add(lblIconPelatihanLVQ);
		
		JPanel panelPelatihanLVQ = new JPanel();
		panelPelatihanLVQ.setLayout(null);
		panelPelatihanLVQ.setBackground(Color.white);
		panelPelatihanLVQ.setBounds(160, 180, 700, 40);
		panelKomponen.add(panelPelatihanLVQ);
		
		JLabel lblPenjelasanPelatihanLVQ = new JLabel("<html>Merupakan halaman yang berfungsi untuk"
				+ " Melakukan Pelatihan terhadap Data Latih yang telah di Ekstraksi.</html>");
		lblPenjelasanPelatihanLVQ.setBounds(5, 0, panelBeranda.getWidth()-5, 40);
		panelPelatihanLVQ.add(lblPenjelasanPelatihanLVQ);
		
		JLabel lblTitleIdentifikasiRileks = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuTitleIdentifikasiRileks.png")));
		lblTitleIdentifikasiRileks.setBounds(55, 240, 117, 30);
		panelKomponen.add(lblTitleIdentifikasiRileks);
		
		JLabel lblIconIdentifikasiRileks = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuIconIdentifikasiRileks.png")));
		lblIconIdentifikasiRileks.setBounds(17, 240, 30, 30);
		panelKomponen.add(lblIconIdentifikasiRileks);
		
		JPanel panelIdentifikasiRileks = new JPanel();
		panelIdentifikasiRileks.setLayout(null);
		panelIdentifikasiRileks.setBackground(Color.white);
		panelIdentifikasiRileks.setBounds(182, 240, 680, 40);
		panelKomponen.add(panelIdentifikasiRileks);
		
		JLabel lblPenjelasanIdentifikasiRileks = new JLabel("<html>Merupakan halaman yang berfungsi untuk"
				+ " melakukan Identifikasi Data Baru terhadap Data Latih.</html>");
		lblPenjelasanIdentifikasiRileks.setBounds(5, 0, panelBeranda.getWidth()-5, 40);
		panelIdentifikasiRileks.add(lblPenjelasanIdentifikasiRileks);
		
		JLabel lblTitleBantuan = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuTitleBantuan.png")));
		lblTitleBantuan.setBounds(55, 300, 61, 30);
		panelKomponen.add(lblTitleBantuan);
		
		JLabel lblIconBantuan = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuIconBantuan.png")));
		lblIconBantuan.setBounds(17, 300, 30, 30);
		panelKomponen.add(lblIconBantuan);
		
		JPanel panelBantuan = new JPanel();
		panelBantuan.setLayout(null);
		panelBantuan.setBackground(Color.white);
		panelBantuan.setBounds(126, 300, 740, 40);
		panelKomponen.add(panelBantuan);
		
		JLabel lblPenjelasanBantuan = new JLabel("<html>Merupakan halaman yang berfungsi untuk"
				+ " menampilkan fungsi-fungsi dari setiap menu.</html>");
		lblPenjelasanBantuan.setBounds(5, 0, panelBeranda.getWidth()-5, 40);
		panelBantuan.add(lblPenjelasanBantuan);
		
		JLabel lblTitleKeluar = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuTitleKeluar.png")));
		lblTitleKeluar.setBounds(55, 360, 49, 30);
		panelKomponen.add(lblTitleKeluar);
		
		JLabel lblIconKeluar = new JLabel(new ImageIcon(getClass().getResource("/resource/bantuIconKeluar.png")));
		lblIconKeluar.setBounds(17, 360, 30, 30);
		panelKomponen.add(lblIconKeluar);
		
		JPanel panelKeluar = new JPanel();
		panelKeluar.setLayout(null);
		panelKeluar.setBackground(Color.white);
		panelKeluar.setBounds(114, 360, 750, 40);
		panelKomponen.add(panelKeluar);
		
		JLabel lblPenjelasanKeluar = new JLabel("<html>Merupakan menu yang berfungsi untuk "
				+ "Keluar dari Sistem.</html>");
		lblPenjelasanKeluar.setBounds(5, 0, panelBeranda.getWidth()-5, 40);
		panelKeluar.add(lblPenjelasanKeluar);
		 
		JPanel panelTimeLine = new JPanel();
		panelTimeLine.setLayout(new GridBagLayout());
		panelTimeLine.setBackground(new Color(221, 221, 221));
		panelTimeLine.setBounds(30, 0, 5, 700);
		panelKomponen.add(panelTimeLine);
		
		return panelContent;
	}
	
	class MouseController implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
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

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
	
	class ButtonController implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
