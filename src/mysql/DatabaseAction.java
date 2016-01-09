package mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DatabaseAction {
	
	Connection koneksi = null;
	Statement stmt = null;
	ResultSet rs = null;
	MySQL_Connector konektor = new MySQL_Connector();
	
	public DatabaseAction(){
		try {
			koneksi = konektor.getKoneksi();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public int getJumNaracoba(){
		int maxNaracoba = 0;
		
		try {
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT MAX(naracoba) FROM Data_Latih");
			if(rs.next()){
				maxNaracoba = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
		return maxNaracoba;
	}
	
	public int getJumSegmentasi(){
		int jumSegmentasi = 0;
		
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM Data_Latih");
			if(rs.next()){
				jumSegmentasi = rs.getInt(1);
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
		return jumSegmentasi;
	}
	
	public int getJumRileks(){
		int jumRileks = 0;
		
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT naracoba FROM Data_Latih WHERE kelas=1");
			rs.last();
			jumRileks = rs.getRow();
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
		return jumRileks;
	}
	
	public int getJumNonRileks(){
		int jumNonRileks = 0;
		
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT naracoba FROM Data_Latih WHERE kelas=-1");
			rs.last();
			jumNonRileks = rs.getRow();
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
		return jumNonRileks;
	}
	
	public DefaultTableModel getListDataLatih(){
		DefaultTableModel listDataLatih = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

		    @SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
		    public Class getColumnClass(int column) {
		        return getValueAt(0, column).getClass();
		    }

		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		listDataLatih.addColumn("No");
		listDataLatih.addColumn("Data EEG");
		listDataLatih.addColumn("Kelas");
		listDataLatih.addColumn("Naracoba");
		int no=1;
		
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Data_Latih");
			while(rs.next()){
				Object[] data = new Object[4];
				data[0] = no++;
				data[1] = rs.getString("data_eeg");
				if(rs.getInt("kelas") == 1){
					data[2] = "Rileks";
				}else{
					data[2] = "Non-Rileks";
				}
				data[3] = rs.getString("naracoba");
				listDataLatih.addRow(data);
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
		return listDataLatih;
	}
	
	public DefaultTableModel getListDataBobot(){
		DefaultTableModel listDataBobot = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;

		    @SuppressWarnings({ "rawtypes", "unchecked" })
			@Override
		    public Class getColumnClass(int column) {
		        return getValueAt(0, column).getClass();
		    }

		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		listDataBobot.addColumn("No");
		listDataBobot.addColumn("Bobot Kelas 1");
		listDataBobot.addColumn("Bobot Kelas 2");
		int no=1;
		
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Koefisien_Bobot");
			while(rs.next()){
				Object[] data = new Object[3];
				data[0] = no++;
				data[1] = rs.getString("w1");
				data[2] = rs.getString("w2");
				listDataBobot.addRow(data);
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
		
		return listDataBobot;
	}
	
	public void inputSegmentasiSinyal(String[][] sinyal, int kelas, int naracoba, int samplingRate, String kanal){
		try{
			for(int i=0;i<sinyal.length;i++){
				String tempSinyal = Arrays.toString(sinyal[i]).substring(1, Arrays.toString(sinyal[i]).length()-1).replaceAll(",", "");
				stmt = koneksi.createStatement();
				stmt.executeUpdate("INSERT INTO Data_Latih (data_eeg, kelas, naracoba, sampling_rate, kanal) VALUES ('"+tempSinyal+"', '"+kelas+"', '"+naracoba+"', '"+samplingRate+"', '"+kanal+"')");
			}
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public double[][] getDataLatihRileks(){
		double[][] sinyalDataLatih = null;
		String[] sinyalTemp;
		int i=0, j=0;
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Data_Latih WHERE kelas=1");
			if(rs.next()){
				rs.last();
				sinyalDataLatih = new double[rs.getRow()][rs.getString("data_eeg").split(" ").length];
				rs.first();
				sinyalTemp = rs.getString("data_eeg").split(" ");
				for(j=0;j<sinyalTemp.length;j++){
					sinyalDataLatih[i][j] = Double.parseDouble(sinyalTemp[j]);
				}
				i++;
				while(rs.next()){
					sinyalTemp = rs.getString("data_eeg").split(" ");
					for(j=0;j<sinyalTemp.length;j++){
						sinyalDataLatih[i][j] = Double.parseDouble(sinyalTemp[j]);
					}
					i++;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return sinyalDataLatih;
	}
	
	public double[][] getDataLatihNonRileks(){
		double[][] sinyalDataLatih = null;
		String[] sinyalTemp;
		int i=0, j=0;
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Data_Latih WHERE kelas=-1");
			if(rs.next()){
				rs.last();
				sinyalDataLatih = new double[rs.getRow()][rs.getString("data_eeg").split(" ").length];
				rs.first();
				sinyalTemp = rs.getString("data_eeg").split(" ");
				for(j=0;j<sinyalTemp.length;j++){
					sinyalDataLatih[i][j] = Double.parseDouble(sinyalTemp[j]);
				}
				i++;
				while(rs.next()){
					sinyalTemp = rs.getString("data_eeg").split(" ");
					for(j=0;j<sinyalTemp.length;j++){
						sinyalDataLatih[i][j] = Double.parseDouble(sinyalTemp[j]);
					}
					i++;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return sinyalDataLatih;
	}
	
	public int getSamplingRate(){
		int samplingRate = 0;
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT sampling_rate FROM Data_Latih");
			if(rs.next()){
				samplingRate = rs.getInt(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return samplingRate;
	}
}
