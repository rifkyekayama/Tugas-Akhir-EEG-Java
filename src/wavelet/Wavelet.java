package wavelet;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Wavelet {
	
	protected List lineOfSinyal;
	protected String kelas, kanal1, kanal2;
	protected String[] pathFile;
	protected int segmentasi, samplingRate;
	
	public Wavelet(String[] pathFile, String kelas, int segmentasi, int samplingRate, String kanal1) throws IOException{
		this.pathFile = pathFile;
		this.kelas = kelas;
		this.segmentasi = segmentasi;
		this.samplingRate = samplingRate;
		this.kanal1 = kanal1;
		ekstraksi(pathFile, kanal1, null, segmentasi, samplingRate);
	}
	
	public Wavelet(String[] pathFile, String kelas, int segmentasi, int samplingRate, String kanal1, String kanal2) throws IOException{
		this.pathFile = pathFile;
		this.kelas = kelas;
		this.segmentasi = segmentasi;
		this.samplingRate = samplingRate;
		this.kanal1 = kanal1;
		this.kanal2 = kanal2;
		ekstraksi(pathFile, kanal1, kanal2, segmentasi, samplingRate);
	}
	
	@SuppressWarnings("unused")
	public void ekstraksi(String[] pathFile, String kanal1, String kanal2, int segmentasi, int samplingRate) throws IOException{
		ArrayList<Object> sinyalKanal1 = null, sinyalKanal2;
		for(int i=0; i<pathFile.length; i++){
			lineOfSinyal = readCsv(pathFile[i]);
			if(kanal2 == null){
				sinyalKanal1 = new ArrayList<Object>();
				sinyalKanal1 = segmentasiEEG(lineOfSinyal, kanalToInt(kanal1), segmentasi, samplingRate);
			}else{
				sinyalKanal1 = new ArrayList<Object>();
				sinyalKanal1 = segmentasiEEG(lineOfSinyal, kanalToInt(kanal1), segmentasi, samplingRate);
				sinyalKanal2 = new ArrayList<Object>();
				sinyalKanal2 = segmentasiEEG(lineOfSinyal, kanalToInt(kanal2), segmentasi, samplingRate);
			}
		}
		System.out.println("panjang array list = "+sinyalKanal1);
	}
	
	public int kanalToInt(String kanal){
		int indexKanal = 0;
		switch (kanal) {
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
	
	public List readCsv(String pathFile) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(pathFile));
		List lines = new List();
		String line = null;
		while ((line = reader.readLine()) != null) {
		    lines.add(line);
		}
		reader.close();
		return lines;
	}

	@SuppressWarnings("unused")
	public ArrayList<Object> segmentasiEEG(List lineOfSinyal, int kanal, int segmentasi, int sampling){
		String[] temp = new String[lineOfSinyal.getItemCount()-1];
		ArrayList<Object> segmen = new ArrayList<Object>();
		int fs=0, waktu=0, i, j=0, k=0;
		for(i=1; i<lineOfSinyal.getItemCount()-1; i++){
			temp[j] = lineOfSinyal.getItem(i).split(", ")[kanal];
			j++;
			fs++;
			if(fs == sampling){
				fs=0;
				waktu++;
			}
			if(waktu == segmentasi){
				j=0;
				waktu=0;
				segmen.add(temp);
				k++;
			}
		}
		return segmen;
	}
}
