package dataLatih;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DataLatih {
	
	public List dataEeg;
	public int naracoba;
	public int samplingRate;
	public int segmentasi;
	public String kelas, kanal1, kanal2, alatPerekaman;
	public String[] pathFile;
	
	public DataLatih() {
		
	}
	
	public DataLatih(String[] pathFile, String kelas, int segmentasi, int samplingRate, String kanal1, String kanal2, String alatPerekaman){
		this.pathFile = pathFile;
		this.kelas = kelas;
		this.segmentasi = segmentasi;
		this.samplingRate = samplingRate;
		this.kanal1 = kanal1;
		this.kanal2 = kanal2;
		this.alatPerekaman = alatPerekaman;
	}
	
	public List readFile(String pathFile) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(pathFile));
		List lines = new List();
		String line = null;
		while ((line = reader.readLine()) != null) {
		    lines.add(line);
		}
		reader.close();
		return lines;
	}
	
	public String[][] gabungkanArray(String[][] kanal1, String[][] kanal2){
		String[][] hasilPenggabungan = new String[kanal1.length][kanal1[0].length+kanal2[0].length];
		int i=0, iTemp=0, j=0, k=0;
		for(i=0;i<hasilPenggabungan.length;i++){
			for(j=0;j<hasilPenggabungan[i].length;j++){
				for(k=0;k<kanal1[i].length;k++){
					if(iTemp < hasilPenggabungan[i].length){
						hasilPenggabungan[i][iTemp] = kanal1[i][k];
						iTemp++;
					}
				}
				for(k=0;k<kanal2[i].length;k++){
					if(iTemp < hasilPenggabungan[i].length){
						hasilPenggabungan[i][iTemp] = kanal2[i][k];
						iTemp++;
					}
				}
			}
			iTemp=0;
		}
		return hasilPenggabungan;
	}
	
	public int kelasToInt(String kelas){
		int indexKelas = 0;
		switch (kelas) {
		case "Rileks": indexKelas=1;break;
		case "Non-Rileks": indexKelas=-1;break;
		default:break;
		}
		return indexKelas;
	}
	
	public static String intToKanal(int kanal){
		String kanalTemp = null;
		switch(kanal){
			case 1: kanalTemp = "FP1"; break;
			case 2: kanalTemp = "AF3"; break;
			case 3: kanalTemp = "F7"; break;
			case 4: kanalTemp = "F3"; break;
			case 5: kanalTemp = "FC5"; break;
			case 6: kanalTemp = "T7"; break;
			case 7: kanalTemp = "P7"; break;
			case 8: kanalTemp = "O1"; break;
			case 9: kanalTemp = "O2"; break;
			case 10: kanalTemp = "P8"; break;
			case 11: kanalTemp = "T8"; break;
			case 12: kanalTemp = "FC6"; break;
			case 13: kanalTemp = "F4"; break;
			case 14: kanalTemp = "AF4"; break;
			default:break;
		}
		return kanalTemp;
	}
	
	public int kanalToInt(String kanal){
		int indexKanal = 0;
		switch (kanal) {
			case "FP1":indexKanal = 1;break;
			case "AF3":indexKanal = 2;break;
			case "F7":indexKanal = 3;break;
			case "F3":indexKanal = 4;break;
			case "FC5":indexKanal = 5;break;
			case "T7":indexKanal = 6;break;
			case "P7":indexKanal = 7;break;
			case "O1":indexKanal = 8;break;
			case "O2":indexKanal = 9;break;
			case "P8":indexKanal = 10;break;
			case "T8":indexKanal = 11;break;
			case "FC6":indexKanal = 12;break;
			case "F4":indexKanal = 13;break;
			case "F8":indexKanal = 14;break;
			case "AF4":indexKanal = 15;break;
			default:break;
		}
		return indexKanal;
	}
	
	public static double[] stringToDouble(String[] data){
		double[] hasil = new double[data.length];
		int i=0;
		
		for(i=0;i<data.length;i++){
			hasil[i] = Double.parseDouble(data[i]);
		}
		
		return hasil;
	}
	
	public String[][] segmentasiEEG(List lineOfSinyal, int kanal, int segmentasi, int sampling, String alatPerekaman){
		int fs=0, waktu=0, i=1, j=0, k=0, l=0, mulaiIterasi = 0;
		int jumSegmen = (int) Math.floor(lineOfSinyal.getItemCount()/(sampling*segmentasi));
		String[] temp = new String[sampling*segmentasi];
		String[][] segmen = new String[jumSegmen][sampling*segmentasi];
		String delimiter = null;
		
		if(alatPerekaman == "Emotiv"){
			delimiter = ", ";
			mulaiIterasi = 1;
		}else if(alatPerekaman == "Neurosky"){
			delimiter = ",";
			mulaiIterasi = 0;
		}
		
		for(i=mulaiIterasi; i<lineOfSinyal.getItemCount()-mulaiIterasi; i++){
			temp[j] = lineOfSinyal.getItem(i).split(delimiter)[kanal];
			j++;
			fs++;
			if(fs == sampling){
				fs=0;
				waktu++;
			}
			if(waktu == segmentasi){
				j=0;
				waktu=0;
				for(l=0;l<temp.length;l++){
					segmen[k][l] = temp[l];
				}
				k++;
			}
		}
		return segmen;
	}
}
