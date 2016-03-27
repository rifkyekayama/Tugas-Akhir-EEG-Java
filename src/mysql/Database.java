package mysql;

import java.awt.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dataLatih.DataLatih;
import main.Main;
import view.Home;

public class Database {
	
	Statement stmt = null;
	ResultSet rs = null;
	
	public Database(){
		
	}
	
	public int getJumNaracoba(){
		int maxNaracoba = 0;
		
		try {
			stmt = Main.koneksi.createStatement();
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
			stmt = Main.koneksi.createStatement();
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
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT naracoba FROM data_latih WHERE kelas=1");
			while(rs.next()){
				jumRileks++;
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
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT DISTINCT naracoba FROM data_latih WHERE kelas=-1");
			while(rs.next()){
				jumNonRileks++;
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
			stmt = Main.koneksi.createStatement();
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
	
	public String getAlatPerekaman(){
		String hasil = null;
		try {
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT alatPerekaman FROM data_latih");
			if(rs.next()){
				hasil = rs.getString("alatPerekaman");
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hasil;
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
			stmt = Main.koneksi.createStatement();
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
		listDataBobot.addColumn("Bobot Rileks");
		listDataBobot.addColumn("Bobot NonRileks");
		int i=0;
		String[] bobotRileks, bobotNonRileks;
		
		try{
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM lvq");
			if(rs.next() && rs.getInt(1) != 0){
				bobotRileks = new String[rs.getString("bobotRileks").split(" ").length];
				bobotNonRileks = new String[rs.getString("bobotNonRileks").split(" ").length];
				bobotRileks = rs.getString("bobotRileks").split(" ");
				bobotNonRileks = rs.getString("bobotNonRileks").split(" ");
				for(i=0;i<bobotRileks.length;i++){
					Object[] data = new Object[3];
					data[0] = (i+1);
					data[1] = bobotRileks[i];
					data[2] = bobotNonRileks[i];
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
		listDataWavelet.addColumn("Filter");
		int no=1;
		
		try{
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM wavelet");
			while(rs.next()){
				Object[] data = new Object[5];
				data[0] = no++;
				data[1] = rs.getString("alfa");
				data[2] = rs.getString("beta");
				data[3] = rs.getString("teta");
				data[4] = rs.getString("filter");
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
			stmt = Main.koneksi.createStatement();
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
	
	public void inputSegmentasiSinyal(String[][] sinyal, int kelas, int naracoba, int samplingRate, String kanal, String alatPerekaman){
		try{
			for(int i=0;i<sinyal.length;i++){
				String tempSinyal = Arrays.toString(sinyal[i]).substring(1, Arrays.toString(sinyal[i]).length()-1).replaceAll(",", "");
				stmt = Main.koneksi.createStatement();
				stmt.executeUpdate("INSERT INTO data_latih (dataEeg, kelas, naracoba, samplingRate, kanal, alatPerekaman) VALUES ('"+tempSinyal+"', '"+kelas+"', '"+naracoba+"', '"+samplingRate+"', '"+kanal+"', '"+alatPerekaman+"')");
			}
			stmt.close();
			rs.close();
		}catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void inputEkstraksiWavelet(double[] alfa, double[] beta, double[] teta, double[] filter, int dataLatih_id){
		try {
			if(filter == null && alfa != null && beta != null && teta != null){
				String tempSinyalAlfa = Arrays.toString(alfa).substring(1, Arrays.toString(alfa).length()-1).replaceAll(",", "");
				String tempSinyalBeta = Arrays.toString(beta).substring(1, Arrays.toString(beta).length()-1).replaceAll(",", "");
				String tempSinyalTeta = Arrays.toString(teta).substring(1, Arrays.toString(teta).length()-1).replaceAll(",", "");
				stmt = Main.koneksi.createStatement();
				stmt.executeUpdate("INSERT INTO wavelet (dataLatih_id, alfa, beta, teta, filter) VALUES ('"+dataLatih_id+"', '"+tempSinyalAlfa+"', '"+tempSinyalBeta+"', '"+tempSinyalTeta+"', '-')");
			}else if(filter != null && alfa == null && beta == null && teta == null){
				String tempSinyalFilter = Arrays.toString(filter).substring(1, Arrays.toString(filter).length()-1).replaceAll(",", "");
				stmt = Main.koneksi.createStatement();
				stmt.executeUpdate("INSERT INTO wavelet (dataLatih_id, alfa, beta, teta, filter) VALUES ('"+dataLatih_id+"', '-', '-', '-', '"+tempSinyalFilter+"')");
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL Error: "+e, "Peringatan", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void deleteEkstraksiWavelet(){
		try {
			stmt = Main.koneksi.createStatement();
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
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM lvq");
			if(rs.next() && rs.getInt(1) != 0){
				String tempBobotRileks = Arrays.toString(bobot[0]).substring(1, Arrays.toString(bobot[0]).length()-1).replaceAll(",", "");
				String tempBobotNonRileks = Arrays.toString(bobot[1]).substring(1, Arrays.toString(bobot[1]).length()-1).replaceAll(",", "");
				stmt = Main.koneksi.createStatement();
				stmt.executeUpdate("UPDATE lvq SET bobotRileks='"+tempBobotRileks+"', bobotNonRileks='"+tempBobotNonRileks+"' WHERE id='"+rs.getInt("id")+"'");
			}else{				
				String tempBobotRileks = Arrays.toString(bobot[0]).substring(1, Arrays.toString(bobot[0]).length()-1).replaceAll(",", "");
				String tempBobotNonRileks = Arrays.toString(bobot[1]).substring(1, Arrays.toString(bobot[1]).length()-1).replaceAll(",", "");
				stmt = Main.koneksi.createStatement();
				stmt.executeUpdate("INSERT INTO lvq (bobotRileks, bobotNonRileks) VALUES ('"+tempBobotRileks+"', '"+tempBobotNonRileks+"')");
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
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih");
			if(rs.next()){
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
	
	public int getJumDataWavelet(){
		int hasil = 0;
		try {
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM wavelet");
			hasil = rs.getInt("total");
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hasil;
	}
	
	public ArrayList<double[]> getDataLatihRileks(int naracoba){
		ArrayList<double[]> sinyalRileks = new ArrayList<double[]>();
		String[] sinyalTemp;
		try {
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih WHERE naracoba="+naracoba+" AND kelas=1");
			if(rs.next()){
				sinyalTemp = rs.getString("dataEeg").split(" ");
				sinyalRileks.add(DataLatih.stringToDouble(sinyalTemp));
				while(rs.next()){
					sinyalTemp = rs.getString("dataEeg").split(" ");
					sinyalRileks.add(DataLatih.stringToDouble(sinyalTemp));
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
		return sinyalRileks;
	}
	
	public ArrayList<double[]> getDataLatihNonRileks(int naracoba){
		ArrayList<double[]> sinyalRileks = new ArrayList<double[]>();
		String[] sinyalTemp;
		try {
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih WHERE naracoba="+naracoba+" AND kelas=-1");
			if(rs.next()){
				sinyalTemp = rs.getString("dataEeg").split(" ");
				sinyalRileks.add(DataLatih.stringToDouble(sinyalTemp));
				while(rs.next()){
					sinyalTemp = rs.getString("dataEeg").split(" ");
					sinyalRileks.add(DataLatih.stringToDouble(sinyalTemp));
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
		return sinyalRileks;
	}
	
	public List getIdOfDataLatih(){
		List hasil = new List();
		
		try {
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih");
			while(rs.next()){
				hasil.add(rs.getString("id"));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hasil;
	}
	
	public ArrayList<double[]> getAlfaByNaracoba(int naracoba){
		ArrayList<double[]> sinyalAlfa = new ArrayList<double[]>();
		try {
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT wavelet.alfa FROM data_latih INNER JOIN wavelet ON data_latih.id = wavelet.dataLatih_id WHERE data_latih.naracoba="+naracoba);
			while(rs.next()){
				String[] alfa = rs.getString("alfa").split(" ");
				sinyalAlfa.add(DataLatih.stringToDouble(alfa));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sinyalAlfa;
	}
	
	public ArrayList<double[]> getBetaByNaracoba(int naracoba){
		ArrayList<double[]> sinyalBeta = new ArrayList<double[]>();
		try {
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT wavelet.beta FROM data_latih INNER JOIN wavelet ON data_latih.id = wavelet.dataLatih_id WHERE data_latih.naracoba="+naracoba);
			while(rs.next()){
				String[] beta = rs.getString("beta").split(" ");
				sinyalBeta.add(DataLatih.stringToDouble(beta));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sinyalBeta;
	}
	
	public ArrayList<double[]> getTetaByNaracoba(int naracoba){
		ArrayList<double[]> sinyalTeta = new ArrayList<double[]>();
		try {
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT wavelet.teta FROM data_latih INNER JOIN wavelet ON data_latih.id = wavelet.dataLatih_id WHERE data_latih.naracoba="+naracoba);
			while(rs.next()){
				String[] teta = rs.getString("teta").split(" ");
				sinyalTeta.add(DataLatih.stringToDouble(teta));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sinyalTeta;
	}
	
	public ArrayList<double[]> getFilterByNaracoba(int naracoba){
		ArrayList<double[]> sinyalFilter = new ArrayList<double[]>();
		try {
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT wavelet.filter FROM data_latih INNER JOIN wavelet ON data_latih.id = wavelet.dataLatih_id WHERE data_latih.naracoba="+naracoba);
			while(rs.next()){
				String[] filter = rs.getString("filter").split(" ");
				sinyalFilter.add(DataLatih.stringToDouble(filter));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sinyalFilter;
	}
	
	public ArrayList<double[][]> getNeuronRileks(){
		ArrayList<double[][]> neuron = new ArrayList<double[][]>();
		try {
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih INNER JOIN wavelet ON data_latih.id = wavelet.dataLatih_id WHERE data_latih.kelas = 1");
			while(rs.next()){
				if(rs.getString("filter").equals("-")){
					String[] alfa = rs.getString("alfa").split(" ");
					String[] beta = rs.getString("beta").split(" ");
					String[] teta = rs.getString("teta").split(" ");
					double[][] hasil = new double[2][alfa.length+beta.length+teta.length];
					int i=0, idx=0;
					for(i=0;i<alfa.length;i++){
						hasil[0][idx] = Double.parseDouble(alfa[i]);
						idx++;
					}
					for(i=0;i<beta.length;i++){
						hasil[0][idx] = Double.parseDouble(beta[i]);
						idx++;
					}
					for(i=0;i<teta.length;i++){
						hasil[0][idx] = Double.parseDouble(teta[i]);
						idx++;
					}
					hasil[1][0] = rs.getDouble("kelas");
					neuron.add(hasil);
				}else{
					String[] filter = rs.getString("filter").split(" ");
					double[][] hasil = new double[2][filter.length];
					int i=0;
					
					for(i=0;i<filter.length;i++){
						hasil[0][i] = Double.parseDouble(filter[i]);
					}
					hasil[1][0] = rs.getDouble("kelas");
					neuron.add(hasil);
				}
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return neuron;
	}
	
	public ArrayList<double[][]> getNeuronNonRileks(){
		ArrayList<double[][]> neuron = new ArrayList<double[][]>();
		try {
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih INNER JOIN wavelet ON data_latih.id = wavelet.dataLatih_id WHERE data_latih.kelas = -1");
			while(rs.next()){
				if(rs.getString("filter").equals("-")){
					String[] alfa = rs.getString("alfa").split(" ");
					String[] beta = rs.getString("beta").split(" ");
					String[] teta = rs.getString("teta").split(" ");
					double[][] hasil = new double[2][alfa.length+beta.length+teta.length];
					int i=0, idx=0;
					for(i=0;i<alfa.length;i++){
						hasil[0][idx] = Double.parseDouble(alfa[i]);
						idx++;
					}
					for(i=0;i<beta.length;i++){
						hasil[0][idx] = Double.parseDouble(beta[i]);
						idx++;
					}
					for(i=0;i<teta.length;i++){
						hasil[0][idx] = Double.parseDouble(teta[i]);
						idx++;
					}
					hasil[1][0] = rs.getDouble("kelas");
					neuron.add(hasil);
				}else{
					String[] filter = rs.getString("filter").split(" ");
					double[][] hasil = new double[2][filter.length];
					int i=0;
					
					for(i=0;i<filter.length;i++){
						hasil[0][i] = Double.parseDouble(filter[i]);
					}
					hasil[1][0] = rs.getDouble("kelas");
					neuron.add(hasil);
				}
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return neuron;
	}
	
	public String getStatusWavelet(){
		String hasil = null;
		try {
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih INNER JOIN wavelet ON data_latih.id = wavelet.dataLatih_id");
			if(rs.next()){
				if(rs.getString("filter").equals("-")){
					hasil = "ekstraksi";
				}else if(!rs.getString("filter").equals("-")){
					hasil = "filter";
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hasil;
	}
	
	public int getSamplingRate(){
		int samplingRate = 0;
		try{
			stmt = Main.koneksi.createStatement();
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
	
	public double[][] getBobotPelatihan(){
		double[][] bobotPelatihan = null;
		String[] bobotRileks, bobotNonRileks;
		int i=0;
		try{
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM lvq");
			if(rs.next() && rs.getInt(1) != 0){
				bobotRileks = new String[rs.getString("bobotRileks").split(" ").length];
				bobotNonRileks = new String[rs.getString("bobotNonRileks").split(" ").length];
				
				bobotRileks = rs.getString("bobotRileks").split(" ");
				bobotNonRileks = rs.getString("bobotNonRileks").split(" ");
				
				bobotPelatihan = new double[2][bobotRileks.length];
				for(i=0;i<bobotRileks.length;i++){
					bobotPelatihan[0][i] = Double.parseDouble(bobotRileks[i]);
				}
				for(i=0;i<bobotNonRileks.length;i++){
					bobotPelatihan[1][i] = Double.parseDouble(bobotNonRileks[i]);
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
			stmt = Main.koneksi.createStatement();
			rs = stmt.executeQuery("SELECT kelas FROM data_latih WHERE naracoba="+Integer.toString(naracoba));
			hasilNaracoba = rs.getInt("kelas");
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hasilNaracoba;
	}
	
	public boolean editDataLatih(int indexKelas, int naracoba){
		try {
			stmt = Main.koneksi.createStatement();
			stmt.executeUpdate("UPDATE data_latih SET kelas="+Integer.toString(indexKelas)+" WHERE naracoba="+Integer.toString(naracoba));
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean hapusDataLatih(String naracoba){
		String sql;
//		Boolean hasil;
		
		try {
			Main.koneksi.setAutoCommit(false);
			stmt = Main.koneksi.createStatement();
			if(naracoba == "Semua"){
				sql = "DELETE FROM data_latih";
			}else{
				sql = "DELETE FROM data_latih WHERE naracoba="+Integer.parseInt(naracoba);
			}
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}