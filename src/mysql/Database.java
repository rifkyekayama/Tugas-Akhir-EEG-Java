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
import org.jfree.data.xy.XYSeriesCollection;

import com.mysql.fabric.xmlrpc.base.Array;

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
	
	public ArrayList<double[]> getDataLatihRileks(int naracoba){
		ArrayList<double[]> sinyalRileks = new ArrayList<double[]>();
		String[] sinyalTemp;
		try {
			stmt = koneksi.createStatement();
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
			stmt = koneksi.createStatement();
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
			stmt = koneksi.createStatement();
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
			stmt = koneksi.createStatement();
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
			stmt = koneksi.createStatement();
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
			stmt = koneksi.createStatement();
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
	
	public ArrayList<double[][]> getNeuronRileks(){
		ArrayList<double[][]> neuron = new ArrayList<double[][]>();
		try {
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih INNER JOIN wavelet ON data_latih.id = wavelet.dataLatih_id WHERE data_latih.kelas = 1");
			while(rs.next()){
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
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM data_latih INNER JOIN wavelet ON data_latih.id = wavelet.dataLatih_id WHERE data_latih.kelas = -1");
			while(rs.next()){
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
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return neuron;
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
	
	public XYDataset createDataSetSinyalAsli(boolean isUseRileks, boolean isUseNonRileks, boolean isUseAlfa, boolean isUseBeta, boolean isUseTeta, int naracoba){
		final TimeSeriesCollection collection = new TimeSeriesCollection();
		int i=0, j=0;
		
		if(isUseRileks){
			final TimeSeries seriesRileks = new TimeSeries("Sinyal Rileks");
			Second current = new Second();
			ArrayList<double[]> sinyalRileks = new ArrayList<double[]>();
			sinyalRileks = getDataLatihRileks(naracoba);
			
			for(i=0;i<sinyalRileks.size();i++){
				for(j=0;j<sinyalRileks.get(i).length;j++){
					seriesRileks.add(current, sinyalRileks.get(i)[j]);
					current = ( Second ) current.next( ); 
				}
			}
			collection.addSeries(seriesRileks);
		}
		
		if(isUseNonRileks){
			final TimeSeries seriesNonRileks = new TimeSeries("Sinyal Non Rileks");
			Second current = new Second();
			ArrayList<double[]> sinyalNonRileks = new ArrayList<double[]>();
			sinyalNonRileks = getDataLatihNonRileks(naracoba);
			
			for(i=0;i<sinyalNonRileks.size();i++){
				for(j=0;j<sinyalNonRileks.get(i).length;j++){
					seriesNonRileks.add(current, sinyalNonRileks.get(i)[j]);
					current = ( Second ) current.next( ); 
				}
			}
			collection.addSeries(seriesNonRileks);
		}
		
		if(isUseAlfa){
			final TimeSeries seriesAlfa = new TimeSeries("Sinyal Alfa");
			Second current = new Second();
			ArrayList<double[]> sinyalAlfa = new ArrayList<double[]>();
			sinyalAlfa = getAlfaByNaracoba(naracoba);
			
			for(i=0;i<sinyalAlfa.size();i++){
				for(j=0;j<sinyalAlfa.get(i).length;j++){
					seriesAlfa.add(current, sinyalAlfa.get(i)[j]);
					current = ( Second ) current.next( ); 
				}
			}
			collection.addSeries(seriesAlfa);
		}
		
		if(isUseBeta){
			final TimeSeries seriesBeta = new TimeSeries("Sinyal Beta");
			Second current = new Second();
			ArrayList<double[]> sinyalBeta = new ArrayList<double[]>();
			sinyalBeta = getBetaByNaracoba(naracoba);
			
			for(i=0;i<sinyalBeta.size();i++){
				for(j=0;j<sinyalBeta.get(i).length;j++){
					seriesBeta.add(current, sinyalBeta.get(i)[j]);
					current = ( Second ) current.next( ); 
				}
			}
			collection.addSeries(seriesBeta);
		}
		
		if(isUseTeta){
			final TimeSeries seriesTeta = new TimeSeries("Sinyal Teta");
			Second current = new Second();
			ArrayList<double[]> sinyalTeta = new ArrayList<double[]>();
			sinyalTeta = getTetaByNaracoba(naracoba);
			
			for(i=0;i<sinyalTeta.size();i++){
				for(j=0;j<sinyalTeta.get(i).length;j++){
					seriesTeta.add(current, sinyalTeta.get(i)[j]);
					current = ( Second ) current.next( ); 
				}
			}
			
			collection.addSeries(seriesTeta);
		}
		return collection;
	}
	
	public double[][] getBobotPelatihan(){
		double[][] bobotPelatihan = null;
		String[] bobotw1, bobotw2;
		int i=0;
		try{
			stmt = koneksi.createStatement();
			rs = stmt.executeQuery("SELECT * FROM lvq");
			if(rs.next() && rs.getInt(1) != 0){
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
			stmt = koneksi.createStatement();
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
		Boolean hasil;
		
		try {
			koneksi.setAutoCommit(false);
			stmt = koneksi.createStatement();
			if(naracoba == "Semua"){
				sql = "DELETE FROM data_latih;";
			}else{
				sql = "DELETE FROM data_latih WHERE naracoba="+Integer.parseInt(naracoba)+";";
			}
			rs = stmt.executeQuery(sql);
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
}
