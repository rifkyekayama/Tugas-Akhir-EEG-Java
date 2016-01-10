package mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import view.Home;

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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
		int i=0;
		String[] bobotW1, bobotW2;
		
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Koefisien_Bobot");
			rs.last();
			if(rs.getRow() != 0){
				rs.first();
				bobotW1 = new String[rs.getString("w1").split(" ").length];
				bobotW2 = new String[rs.getString("w2").split(" ").length];
				bobotW1 = rs.getString("w1").split(" ");
				bobotW2 = rs.getString("w2").split(" ");
				for(i=0;i<bobotW1.length;i++){
					Object[] data = new Object[3];
					data[0] = (i+1);
					data[1] = bobotW1[i];
					data[2] = bobotW2[i];
					listDataBobot.addRow(data);
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
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
			e.printStackTrace();
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
			rs.last();
			if(rs.getRow() == 0){
				JOptionPane.showMessageDialog(null, "Data latih kelas Rileks kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				Home.changeCard("panelKelolaDataLatih");
			}else{
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
			JOptionPane.showMessageDialog(null, "getDataLatihRileks error = "+e, "Error", JOptionPane.ERROR_MESSAGE);
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
			rs.last();
			if(rs.getRow() == 0){
				JOptionPane.showMessageDialog(null, "Data latih kelas Non-Rileks kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				Home.changeCard("panelKelolaDataLatih");
			}else{
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
			JOptionPane.showMessageDialog(null, "getDataLatihNonRileks error = "+e, "Error", JOptionPane.ERROR_MESSAGE);
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
	
	public void inputHasilBobot(double[][] bobot){
//		int i=0;
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM Koefisien_Bobot");
			rs.last();
			if(rs.getRow() != 0){
				rs.first();
				String tempBobotRileks = Arrays.toString(bobot[0]).substring(1, Arrays.toString(bobot[0]).length()-1).replaceAll(",", "");
				String tempBobotNonRileks = Arrays.toString(bobot[1]).substring(1, Arrays.toString(bobot[1]).length()-1).replaceAll(",", "");
				stmt = koneksi.createStatement();
				stmt.executeUpdate("UPDATE Koefisien_Bobot SET w1='"+tempBobotRileks+"', w2='"+tempBobotNonRileks+"' WHERE id='"+rs.getInt("id")+"'");
			}else{				
				String tempBobotRileks = Arrays.toString(bobot[0]).substring(1, Arrays.toString(bobot[0]).length()-1).replaceAll(",", "");
				String tempBobotNonRileks = Arrays.toString(bobot[1]).substring(1, Arrays.toString(bobot[1]).length()-1).replaceAll(",", "");
				stmt = koneksi.createStatement();
				stmt.executeUpdate("INSERT INTO Koefisien_Bobot (w1, w2) VALUES ('"+tempBobotRileks+"', '"+tempBobotNonRileks+"')");
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
}
