package mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import view.Home;
import wavelet.Wavelet;

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
			rs = stmt.executeQuery("SELECT MAX(naracoba) FROM data_latih");
			if(rs.next()){
				maxNaracoba = rs.getInt(1);
			}
			stmt.close();
			rs.close();
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
			rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM data_latih");
			if(rs.next()){
				jumSegmentasi = rs.getInt(1);
			}
			stmt.close();
			rs.close();
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
			rs = stmt.executeQuery("SELECT DISTINCT naracoba FROM data_latih WHERE kelas=1");
			rs.last();
			jumRileks = rs.getRow();
			stmt.close();
			rs.close();
		}catch(SQLException e){
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
		return jumRileks;
	}
	
	public int getJumNonRileks(){
		int jumNonRileks = 0;
		
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT naracoba FROM data_latih WHERE kelas=-1");
			rs.last();
			jumNonRileks = rs.getRow();
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
		return jumNonRileks;
	}
	
	public int[] getKanal(){
		int[] kanal = null;
		int i;
		String[] temp;
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT kanal FROM data_latih");
			rs.last();
			if(rs.getRow() != 0){
				kanal = new int[rs.getString("kanal").split(",").length];
				temp = rs.getString("kanal").split(",");
				for(i=0;i<temp.length;i++){
					kanal[i] = Integer.parseInt(temp[i]);
				}
			}
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return kanal;
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
		listDataLatih.addColumn("Kanal");
		int no=1;
		String[] kanalTemp;
		
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih");
			while(rs.next()){
				Object[] data = new Object[5];
				data[0] = no++;
				data[1] = rs.getString("data_eeg");
				if(rs.getInt("kelas") == 1){
					data[2] = "Rileks";
				}else{
					data[2] = "Non-Rileks";
				}
				data[3] = rs.getString("naracoba");
				kanalTemp = new String[rs.getString("kanal").split(",").length];
				kanalTemp = rs.getString("kanal").split(",");
				if(kanalTemp.length > 1){
					data[4] = Wavelet.intToKanal(Integer.parseInt(kanalTemp[0]))+" dan "+Wavelet.intToKanal(Integer.parseInt(kanalTemp[1]));
				}else{
					data[4] = Wavelet.intToKanal(Integer.parseInt(kanalTemp[0]));
				}
				listDataLatih.addRow(data);
			}
			stmt.close();
			rs.close();
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
			rs = stmt.executeQuery("SELECT * FROM koefisien_bobot");
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
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
		
		return listDataBobot;
	}
	
	public boolean isBobotNotNull(){
		boolean isBobotNotNull = false;
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM koefisien_bobot");
			rs.last();
			if(rs.getRow() != 0){
				isBobotNotNull = true;
			}else{
				isBobotNotNull = false;
			}
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return isBobotNotNull;
	}
	
	public void inputSegmentasiSinyal(String[][] sinyal, int kelas, int naracoba, int samplingRate, String kanal){
		try{
			for(int i=0;i<sinyal.length;i++){
				String tempSinyal = Arrays.toString(sinyal[i]).substring(1, Arrays.toString(sinyal[i]).length()-1).replaceAll(",", "");
				stmt = koneksi.createStatement();
				stmt.executeUpdate("INSERT INTO data_latih (data_eeg, kelas, naracoba, sampling_rate, kanal) VALUES ('"+tempSinyal+"', '"+kelas+"', '"+naracoba+"', '"+samplingRate+"', '"+kanal+"')");
			}
			stmt.close();
			rs.close();
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
			rs = stmt.executeQuery("SELECT * FROM data_latih WHERE kelas=1");
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
			stmt.close();
			rs.close();
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
			rs = stmt.executeQuery("SELECT * FROM data_latih WHERE kelas=-1");
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
			stmt.close();
			rs.close();
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
			rs = stmt.executeQuery("SELECT DISTINCT sampling_rate FROM data_latih");
			if(rs.next()){
				samplingRate = rs.getInt(1);
			}
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return samplingRate;
	}
	
	public void inputHasilBobot(double[][] bobot){
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM koefisien_bobot");
			rs.last();
			if(rs.getRow() != 0){
				rs.first();
				String tempBobotRileks = Arrays.toString(bobot[0]).substring(1, Arrays.toString(bobot[0]).length()-1).replaceAll(",", "");
				String tempBobotNonRileks = Arrays.toString(bobot[1]).substring(1, Arrays.toString(bobot[1]).length()-1).replaceAll(",", "");
				stmt = koneksi.createStatement();
				stmt.executeUpdate("UPDATE koefisien_bobot SET w1='"+tempBobotRileks+"', w2='"+tempBobotNonRileks+"' WHERE id='"+rs.getInt("id")+"'");
			}else{				
				String tempBobotRileks = Arrays.toString(bobot[0]).substring(1, Arrays.toString(bobot[0]).length()-1).replaceAll(",", "");
				String tempBobotNonRileks = Arrays.toString(bobot[1]).substring(1, Arrays.toString(bobot[1]).length()-1).replaceAll(",", "");
				stmt = koneksi.createStatement();
				stmt.executeUpdate("INSERT INTO koefisien_bobot (w1, w2) VALUES ('"+tempBobotRileks+"', '"+tempBobotNonRileks+"')");
			}
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public double[][] getBobotPelatihan(){
		double[][] bobotPelatihan = null;
		String[] bobotw1, bobotw2;
		int i=0;
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM koefisien_bobot");
			rs.last();
			if(rs.getRow() != 0){
				rs.first();
				bobotw1 = new String[rs.getString("w1").split(" ").length];
				bobotw2 = new String[rs.getString("w2").split(" ").length];
				
				bobotw1 = rs.getString("w1").split(" ");
				bobotw2 = rs.getString("w2").split(" ");
				
				bobotPelatihan = new double[2][bobotw1.length];
				for(i=0;i<bobotw1.length;i++){
					bobotPelatihan[0][i] = Double.parseDouble(bobotw1[i]);
				}
				for(i=0;i<bobotw2.length;i++){
					bobotPelatihan[1][i] = Double.parseDouble(bobotw2[i]);
				}
			}
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		return bobotPelatihan;
	}
}
