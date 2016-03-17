package mysql;

import java.awt.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import dataLatih.DataLatih;
import view.Home;

public class Database {
	
	Connection koneksi = null;
	Statement stmt = null;
	ResultSet rs = null;
	SQLiteConnector konektor = new SQLiteConnector();
	
	public Database(){
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
			if(rs.next()){
				jumRileks = rs.getInt(1);
			}
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
			if(rs.next()){
				jumNonRileks = rs.getInt(1);
			}
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
			if(rs.next() && rs.getInt(1) != 0){
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
				data[1] = rs.getString("dataEeg");
				if(rs.getInt("kelas") == 1){
					data[2] = "Rileks";
				}else{
					data[2] = "Non-Rileks";
				}
				data[3] = rs.getString("naracoba");
				kanalTemp = new String[rs.getString("kanal").split(",").length];
				kanalTemp = rs.getString("kanal").split(",");
				if(kanalTemp.length > 1){
					data[4] = DataLatih.intToKanal(Integer.parseInt(kanalTemp[0]))+" dan "+DataLatih.intToKanal(Integer.parseInt(kanalTemp[1]));
				}else{
					data[4] = DataLatih.intToKanal(Integer.parseInt(kanalTemp[0]));
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
			rs = stmt.executeQuery("SELECT * FROM lvq");
			if(rs.next() && rs.getInt(1) != 0){
//				rs.first();
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
	
	public DefaultTableModel getListDataWavelet(){
		DefaultTableModel listDataWavelet = new DefaultTableModel(){
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
		listDataWavelet.addColumn("No");
		listDataWavelet.addColumn("Alfa");
		listDataWavelet.addColumn("Beta");
		listDataWavelet.addColumn("Teta");
		int no=1;
		
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM wavelet");
			while(rs.next()){
				Object[] data = new Object[4];
				data[0] = no++;
				data[1] = rs.getString("alfa");
				data[2] = rs.getString("beta");
				data[3] = rs.getString("teta");
				listDataWavelet.addRow(data);
			}
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
		
		return listDataWavelet;
	}
	
	public boolean isBobotNotNull(){
		boolean isBobotNotNull = false;
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM lvq");
			if(rs.next() && rs.getInt(1) != 0){
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
				stmt.executeUpdate("INSERT INTO data_latih (dataEeg, kelas, naracoba, samplingRate, kanal) VALUES ('"+tempSinyal+"', '"+kelas+"', '"+naracoba+"', '"+samplingRate+"', '"+kanal+"')");
			}
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void inputEkstraksiWavelet(double[] alfa, double[] beta, double[] teta, int dataLatih_id){
		try {
			String tempSinyalAlfa = Arrays.toString(alfa).substring(1, Arrays.toString(alfa).length()-1).replaceAll(",", "");
			String tempSinyalBeta = Arrays.toString(beta).substring(1, Arrays.toString(beta).length()-1).replaceAll(",", "");
			String tempSinyalTeta = Arrays.toString(teta).substring(1, Arrays.toString(teta).length()-1).replaceAll(",", "");
			stmt = koneksi.createStatement();
			stmt.executeUpdate("INSERT INTO wavelet (dataLatih_id, alfa, beta, teta) VALUES ('"+dataLatih_id+"', '"+tempSinyalAlfa+"', '"+tempSinyalBeta+"', '"+tempSinyalTeta+"')");
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void deleteEkstraksiWavelet(){
		try {
			stmt = koneksi.createStatement();
			stmt.executeUpdate("DELETE FROM wavelet");
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void inputHasilBobot(double[][] bobot){
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM lvq");
			if(rs.next() && rs.getInt(1) != 0){
//				rs.first();
				String tempBobotRileks = Arrays.toString(bobot[0]).substring(1, Arrays.toString(bobot[0]).length()-1).replaceAll(",", "");
				String tempBobotNonRileks = Arrays.toString(bobot[1]).substring(1, Arrays.toString(bobot[1]).length()-1).replaceAll(",", "");
				stmt = koneksi.createStatement();
				stmt.executeUpdate("UPDATE lvq SET w1='"+tempBobotRileks+"', w2='"+tempBobotNonRileks+"' WHERE id='"+rs.getInt("id")+"'");
			}else{				
				String tempBobotRileks = Arrays.toString(bobot[0]).substring(1, Arrays.toString(bobot[0]).length()-1).replaceAll(",", "");
				String tempBobotNonRileks = Arrays.toString(bobot[1]).substring(1, Arrays.toString(bobot[1]).length()-1).replaceAll(",", "");
				stmt = koneksi.createStatement();
				stmt.executeUpdate("INSERT INTO lvq (w1, w2) VALUES ('"+tempBobotRileks+"', '"+tempBobotNonRileks+"')");
			}
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<double[]> getDataLatih(){
		ArrayList<double[]> sinyalDataLatih = new ArrayList<double[]>();
		String[] sinyalTemp;
		try {
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih");
			if(rs.next() && rs.getInt(1) != 0){
				sinyalTemp = rs.getString("dataEeg").split(" ");
				sinyalDataLatih.add(DataLatih.stringToDouble(sinyalTemp));
				while(rs.next()){
					sinyalTemp = rs.getString("dataEeg").split(" ");
					sinyalDataLatih.add(DataLatih.stringToDouble(sinyalTemp));
				}
			}else{
				JOptionPane.showMessageDialog(null, "Data latih kelas Rileks kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				Home.changeCard("panelKelolaDataLatih");
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sinyalDataLatih;
	}
	
	public List getIdOfDataLatih(){
		List hasil = new List();
		
		try {
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih");
			while(rs.next()){
				hasil.add(rs.getString("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hasil;
	}
	
	public double[][] getDataLatihRileks(){
		double[][] sinyalDataLatih = null;
		String[] sinyalTemp;
		int i=0, j=0;
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih WHERE kelas=1");
			if(rs.next() && rs.getInt(1) != 0){
				sinyalDataLatih = new double[rs.getInt(1)-1][rs.getString("dataEeg").split(" ").length];
//				rs.first();
				sinyalTemp = rs.getString("dataEeg").split(" ");
				for(j=0;j<sinyalTemp.length;j++){
					sinyalDataLatih[i][j] = Double.parseDouble(sinyalTemp[j]);
				}
				i++;
				while(rs.next()){
					sinyalTemp = rs.getString("dataEeg").split(" ");
					for(j=0;j<sinyalTemp.length;j++){
						sinyalDataLatih[i][j] = Double.parseDouble(sinyalTemp[j]);
					}
					i++;
				}
			}else{
				JOptionPane.showMessageDialog(null, "Data latih kelas Rileks kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				Home.changeCard("panelKelolaDataLatih");
			}
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "getDataLatihRileks error = "+e, "Error", JOptionPane.ERROR_MESSAGE);
		}
		return sinyalDataLatih;
	}
	
	public double[][] getDataLatihRileksByNaracoba(int naracoba){
		double[][] sinyalDataLatih = null;
		String[] sinyalTemp;
		int i=0, j=0;
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih WHERE kelas=1 AND naracoba="+naracoba);
			if(rs.next() && rs.getInt(1) != 0){
				sinyalDataLatih = new double[rs.getInt(1)-1][rs.getString("dataEeg").split(" ").length];
//				rs.first();
				sinyalTemp = rs.getString("dataEeg").split(" ");
				for(j=0;j<sinyalTemp.length;j++){
					sinyalDataLatih[i][j] = Double.parseDouble(sinyalTemp[j]);
				}
				i++;
				while(rs.next()){
					sinyalTemp = rs.getString("dataEeg").split(" ");
					for(j=0;j<sinyalTemp.length;j++){
						sinyalDataLatih[i][j] = Double.parseDouble(sinyalTemp[j]);
					}
					i++;
				}
			}else{
				JOptionPane.showMessageDialog(null, "Data latih kelas Rileks kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				Home.changeCard("panelKelolaDataLatih");
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
			if(rs.next() && rs.getInt(1) != 0){
				sinyalDataLatih = new double[rs.getInt(1)-1][rs.getString("dataEeg").split(" ").length];
//				rs.first();
				sinyalTemp = rs.getString("dataEeg").split(" ");
				for(j=0;j<sinyalTemp.length;j++){
					sinyalDataLatih[i][j] = Double.parseDouble(sinyalTemp[j]);
				}
				i++;
				while(rs.next()){
					sinyalTemp = rs.getString("dataEeg").split(" ");
					for(j=0;j<sinyalTemp.length;j++){
						sinyalDataLatih[i][j] = Double.parseDouble(sinyalTemp[j]);
					}
					i++;
				}
			}else{
				JOptionPane.showMessageDialog(null, "Data latih kelas Non-Rileks kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				Home.changeCard("panelKelolaDataLatih");
			}
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "getDataLatihNonRileks error = "+e, "Error", JOptionPane.ERROR_MESSAGE);
		}
		return sinyalDataLatih;
	}
	
	public double[][] getDataLatihNonRileksByNaracoba(int naracoba){
		double[][] sinyalDataLatih = null;
		String[] sinyalTemp;
		int i=0, j=0;
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih WHERE kelas=-1 AND naracoba="+naracoba);
			if(rs.next() && rs.getInt(1) != 0){
				sinyalDataLatih = new double[rs.getInt(1)-1][rs.getString("dataEeg").split(" ").length];
//				rs.first();
				sinyalTemp = rs.getString("dataEeg").split(" ");
				for(j=0;j<sinyalTemp.length;j++){
					sinyalDataLatih[i][j] = Double.parseDouble(sinyalTemp[j]);
				}
				i++;
				while(rs.next()){
					sinyalTemp = rs.getString("dataEeg").split(" ");
					for(j=0;j<sinyalTemp.length;j++){
						sinyalDataLatih[i][j] = Double.parseDouble(sinyalTemp[j]);
					}
					i++;
				}
			}else{
				JOptionPane.showMessageDialog(null, "Data latih kelas Non-Rileks kosong", "Peringatan", JOptionPane.WARNING_MESSAGE);
				Home.changeCard("panelKelolaDataLatih");
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
			rs = stmt.executeQuery("SELECT DISTINCT samplingRate FROM data_latih");
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
	
	public XYDataset createDataSetSinyalAsli(){
		final TimeSeries seriesRileks = new TimeSeries("Sinyal Rileks");
		Second current = new Second();
		
		return new TimeSeriesCollection();
	}
	
	public double[][] getBobotPelatihan(){
		double[][] bobotPelatihan = null;
		String[] bobotw1, bobotw2;
		int i=0;
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM lvq");
			if(rs.next() && rs.getInt(1) != 0){
//				rs.first();
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
	
	public int getKelasFromDataLatih(int naracoba){
		int hasilNaracoba = 0;
		try {
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih WHERE naracoba="+Integer.toString(naracoba));
			hasilNaracoba = rs.getInt("kelas");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hasilNaracoba;
	}
	
	public boolean editDataLatih(int indexKelas, int naracoba){
		try {
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("UPDATE data_latih SET kelas="+Integer.toString(indexKelas)+" WHERE naracoba="+Integer.toString(naracoba));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean hapusDataLatih(String naracoba){
		String sql;
		Boolean hasil;
		
		try {
			koneksi.setAutoCommit(false);
			stmt = koneksi.createStatement();
			if(naracoba == "Semua"){
				sql = "DELETE FROM data_latih;";
			}else{
				sql = "DELETE FROM data_latih WHERE naracoba="+Integer.parseInt(naracoba)+";";
			}
			rs = stmt.executeQuery("SELECT * FROM data_latih");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
}
