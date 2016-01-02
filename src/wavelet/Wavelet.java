package wavelet;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;

import mysql.DatabaseAction;

public class Wavelet {
	
	protected List lineOfSinyal;
	protected String kelas, kanal1, kanal2;
	protected String[] pathFile;
	protected int segmentasi, samplingRate;
	protected DatabaseAction dbAction = new DatabaseAction();
	
	public Wavelet(String[] pathFile, String kelas, int segmentasi, int samplingRate, String kanal1, String kanal2) throws IOException{
		this.pathFile = pathFile;
		this.kelas = kelas;
		this.segmentasi = segmentasi;
		this.samplingRate = samplingRate;
		this.kanal1 = kanal1;
		this.kanal2 = kanal2;
		
		String[][] sinyalKanal1, sinyalKanal2, kanalMerge;
		int naracoba = dbAction.getJumNaracoba()+1;
		String kanal = null;
		int i;
		for(i=0; i<pathFile.length; i++){
			lineOfSinyal = readCsv(pathFile[i]);
			if(kanal2 == null){
				sinyalKanal1 = new String[(int) Math.floor(lineOfSinyal.getItemCount()/(this.samplingRate*this.segmentasi))][lineOfSinyal.getItemCount()-1];
				sinyalKanal1 = segmentasiEEG(lineOfSinyal, kanalToInt(kanal1), segmentasi, samplingRate);
				kanal = Integer.toString(kanalToInt(this.kanal1));
				dbAction.inputSegmentasiSinyal(sinyalKanal1, kelasToInt(kelas), naracoba, samplingRate, kanal);
			}else{
				sinyalKanal1 = new String[(int) Math.floor(lineOfSinyal.getItemCount()/(this.samplingRate*this.segmentasi))][lineOfSinyal.getItemCount()-1];
				sinyalKanal1 = segmentasiEEG(lineOfSinyal, kanalToInt(kanal1), segmentasi, samplingRate);
				sinyalKanal2 = new String[(int) Math.floor(lineOfSinyal.getItemCount()/(this.samplingRate*this.segmentasi))][lineOfSinyal.getItemCount()-1];
				sinyalKanal2 = segmentasiEEG(lineOfSinyal, kanalToInt(kanal2), segmentasi, samplingRate);
				kanalMerge = new String[sinyalKanal1.length+sinyalKanal2.length][sinyalKanal1[0].length];
				kanalMerge = mergeArrays(String.class, sinyalKanal1, sinyalKanal2);
				kanal = Integer.toString(kanalToInt(this.kanal1))+","+Integer.toString(kanalToInt(this.kanal1));
				dbAction.inputSegmentasiSinyal(kanalMerge, kelasToInt(kelas), naracoba, samplingRate, kanal);
			}
		}
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
	
	@SuppressWarnings("unchecked")
	public <T> T[][] mergeArrays(Class<T> clazz, T[][]... arrays) {
	    // determine length of 1st dimension.
	    int dim1 = 0;
	    for (T[][] arr : arrays) {
	        dim1 += arr.length;
	    }
	    // Create new 2Dim Array
	    T[][] result = (T[][]) Array.newInstance(clazz, dim1, 0);
	    // Fill the new array with all 'old' arrays
	    int index = 0;
	    for (T[][] arr : arrays) {
	        for (T[] array : arr) {
	            // changes within your old arrays will reflect to merged one
	            result[index++] = array;
	        }
	    }
	    return result;
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

	public String[][] segmentasiEEG(List lineOfSinyal, int kanal, int segmentasi, int sampling){
		int fs=0, waktu=0, i=1, j=0, k=0, l=0;
		int jumSegmen = (int) Math.floor(lineOfSinyal.getItemCount()/(sampling*segmentasi));
		String[] temp = new String[sampling*segmentasi];
		String[][] segmen = new String[jumSegmen][sampling*segmentasi];
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
				for(l=0;l<temp.length;l++){
					segmen[k][l] = temp[l];
				}
				k++;
			}
		}
		return segmen;
	}
}
