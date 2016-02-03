package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import view.KelolaDataLatih.ButtonController;

public class FormDataLatih extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JButton btnPilihDataEEG, btnSubmitDataEEG, btnExit;
	protected JLabel lblFileDataEEG, isLockedKanal1, isLockedKanal2;
	protected JComboBox<?> cmbKelas, cmbKanal1, cmbKanal2;
	protected JTextField txtSegmentasi, txtSamplingrate;
	protected JCheckBox cbGunakanKanal2;
	protected String[] kelas = {"Pilih salah satu...", "Rileks", "Non-Rileks"};
	protected String[] kanal = {"Pilih salah satu...", "AF3", "F7", "F3", "FC5", "T7", "P7", "O1", "O2", "P8", "T8", "FC6", "F4", "F8", "AF4"};

	public FormDataLatih() {
		setUndecorated(true);
		setSize(1200, 650);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBackground(new Color(0, 0, 0, 0.7f));
		setAlwaysOnTop(true);
		
		getContentPane().setLayout(null);
		
		JPanel panelForm = new JPanel();
		panelForm.setLayout(null);
		panelForm.setBackground(Color.white);
		panelForm.setBounds((this.getWidth()/2)-330, 30, 660, 520);
		
		JPanel panelFormHeader = new JPanel();
		panelFormHeader.setLayout(null);
		panelFormHeader.setBackground(new Color(0, 167, 208));
		panelFormHeader.setBounds(0, 0, panelForm.getWidth(), 60);
		panelForm.add(panelFormHeader);
		
		JLabel lblJudulForm = new JLabel("Form Data Latih");
		lblJudulForm.setFont(lblJudulForm.getFont().deriveFont(20f));
		lblJudulForm.setForeground(Color.white);
		lblJudulForm.setBounds(15, 20, 300, 20);
		panelFormHeader.add(lblJudulForm);
		
		JPanel panelFormBody = new JPanel();
		panelFormBody.setLayout(null);
		panelFormBody.setBackground(new Color(0, 192, 239));
		panelFormBody.setBounds(0, panelFormHeader.getHeight(), panelForm.getWidth(), (panelForm.getHeight()-(panelFormHeader.getHeight()*2)));
		panelForm.add(panelFormBody);
		
		JLabel lblPilihDataEEG = new JLabel("Pilih Data EEG");
		lblPilihDataEEG.setFont(lblPilihDataEEG.getFont().deriveFont(Font.BOLD, 15f));
		lblPilihDataEEG.setForeground(Color.white);
		lblPilihDataEEG.setBounds(50, 10, 150, 20);
		panelFormBody.add(lblPilihDataEEG);
		
		btnPilihDataEEG = new JButton("Choose File");
		btnPilihDataEEG.setBackground(Color.lightGray);
		btnPilihDataEEG.setActionCommand("pilihDataEEG");
		btnPilihDataEEG.setBorderPainted(false);
//		btnPilihDataEEG.addMouseListener(new MouseController());
//		btnPilihDataEEG.addActionListener(new ButtonController());
		btnPilihDataEEG.setBounds(50, 30, 120, 25);
		panelFormBody.add(btnPilihDataEEG);
		
		lblFileDataEEG = new JLabel("No file chosen");
		lblFileDataEEG.setForeground(Color.white);
		lblFileDataEEG.setBounds(180, 30, 300, 30);
		panelFormBody.add(lblFileDataEEG);
		
		JLabel lblKetDataEEG = new JLabel("Data yang dimasukan harus bertipe .CSV dan terdiri dari 14 kanal.");
		lblKetDataEEG.setForeground(new Color(115, 115, 115));
		lblKetDataEEG.setBounds(50, 50, 500, 30);
		panelFormBody.add(lblKetDataEEG);
		
		JLabel lblKelas = new JLabel("Kelas :");
		lblKelas.setFont(lblKelas.getFont().deriveFont(Font.BOLD, 15f));
		lblKelas.setForeground(Color.white);
		lblKelas.setBounds(50, 80, 100, 20);
		panelFormBody.add(lblKelas);
		
		cmbKelas = new JComboBox<>(kelas);
		cmbKelas.setBackground(Color.white);
		cmbKelas.setBounds(50, 105, 420, 30);
		panelFormBody.add(cmbKelas);
		
		JLabel lblSegmentasi = new JLabel("Segmentasi (per detik) :");
		lblSegmentasi.setForeground(Color.white);
		lblSegmentasi.setFont(lblSegmentasi.getFont().deriveFont(Font.BOLD, 15f));
		lblSegmentasi.setBounds(50, 145, 210, 30);
		panelFormBody.add(lblSegmentasi);
		
		txtSegmentasi = new JTextField("60");
		txtSegmentasi.setBounds(50, 175, 210, 30);
		panelFormBody.add(txtSegmentasi);
		
		JLabel lblSamplingRate = new JLabel("Sampling rate (Hertz) :");
		lblSamplingRate.setForeground(Color.white);
		lblSamplingRate.setFont(lblSamplingRate.getFont().deriveFont(Font.BOLD, 15f));
		lblSamplingRate.setBounds(270, 145, 210, 30);
		panelFormBody.add(lblSamplingRate);
		
		txtSamplingrate = new JTextField("128");
		txtSamplingrate.setBounds(270, 175, 210, 30);
		panelFormBody.add(txtSamplingrate);
		
		cbGunakanKanal2 = new JCheckBox("Gunakan Kanal 2", false);
		cbGunakanKanal2.setBackground(new Color(0, 192, 239));
		cbGunakanKanal2.setBounds(270, 215, 205, 30);
		cbGunakanKanal2.setActionCommand("cekKanal2");
		//cbGunakanKanal2.addActionListener(new ButtonController());
		panelFormBody.add(cbGunakanKanal2);
		
		JLabel lblKanal1 = new JLabel("Kanal 1 :");
		lblKanal1.setForeground(Color.white);
		lblKanal1.setFont(lblKanal1.getFont().deriveFont(Font.BOLD, 15f));
		lblKanal1.setBounds(50, 245, 210, 30);
		panelFormBody.add(lblKanal1);
		
		cmbKanal1 = new JComboBox<>(kanal);
		cmbKanal1.setBackground(Color.white);
		cmbKanal1.setBounds(50, 275, 210, 30);
		panelFormBody.add(cmbKanal1);
		
		JLabel lblKanal2 = new JLabel("Kanal 2 :");
		lblKanal2.setForeground(Color.white);
		lblKanal2.setFont(lblKanal2.getFont().deriveFont(Font.BOLD, 15f));
		lblKanal2.setBounds(270, 245, 205, 30);
		panelFormBody.add(lblKanal2);
		
		cmbKanal2 = new JComboBox<>(kanal);
		cmbKanal2.setBackground(Color.white);
		cmbKanal2.setBounds(270, 275, 205, 30);
		cmbKanal2.setEnabled(false);
		panelFormBody.add(cmbKanal2);
		
		JPanel panelFormFooter = new JPanel();
		panelFormFooter.setLayout(null);
		panelFormFooter.setBackground(new Color(0, 167, 208));
		panelFormFooter.setBounds(0, panelForm.getHeight()-panelFormHeader.getHeight(), panelForm.getWidth(), panelFormHeader.getHeight());
		panelForm.add(panelFormFooter);
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(10, 10, 100, 40);
		btnExit.setBackground(Color.blue);
		btnExit.setForeground(Color.white);
		btnExit.setBorderPainted(true);
		btnExit.setActionCommand("btnExit");
		btnExit.addActionListener(new ButtonController());
		panelFormFooter.add(btnExit);
		
		getContentPane().add(panelForm);
	}
	
	class ButtonController implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("btnExit")){
				setVisible(false);
			}
		}
		
	}
}
