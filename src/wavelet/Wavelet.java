package wavelet;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import mysql.DatabaseAction;

public class Wavelet {
	
	public List lineOfSinyal;
	public String kelas, kanal1, kanal2;
	public String[] pathFile;
	public int segmentasi, samplingRate;
	public DatabaseAction dbAction = new DatabaseAction();
	
	public Wavelet(){
		//constructor kosong
	}
	
	public Wavelet(String[] pathFile, String kelas, int segmentasi, int samplingRate, String kanal1, String kanal2){
		this.pathFile = pathFile;
		this.kelas = kelas;
		this.segmentasi = segmentasi;
		this.samplingRate = samplingRate;
		this.kanal1 = kanal1;
		this.kanal2 = kanal2;
	}
	
	public Wavelet(boolean useAlfa, boolean useBeta, boolean useTeta){
		//constructor untuk ekstraksi data alfa beda dan teta
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
	
	public double[][][] unSegmenEEG(double[][] sinyalEEG, int samplingRate){
		double[][][] hasilSinyal = new double[sinyalEEG.length][sinyalEEG[0].length/samplingRate][samplingRate];
		int i=0, j=0, idx=0, idx2=0;
		
		for(i=0;i<sinyalEEG.length;i++){
			for(j=0;j<sinyalEEG[i].length;j++){
				if(idx2 < samplingRate-1){
					hasilSinyal[i][idx][idx2] = sinyalEEG[i][j];
					idx2++;
				}else{
					idx2=0;
					idx++;
				}
			}
			idx=0;
			idx2=0;
		}
		return hasilSinyal;
	}
	
	public double[] downSampling(double[] sinyalEEG, int factor){
		double[] hasilSinyal = new double[sinyalEEG.length/factor];
		int idx = 0, i=0;
		
		for(i=0;i<hasilSinyal.length;i++){
			hasilSinyal[i] = sinyalEEG[idx];
			if(i != hasilSinyal.length-1){
				idx+=factor;
			}
		}
		return hasilSinyal;
	}
	
	public double[] konvolusiLow(double[] sinyalEEG){
		double[] lowPassFilter = {0.482962913144534, 0.836516303737808, 0.224143868042013, -0.129409522551260};
		double[] LPF_temp = new double[sinyalEEG.length];
		double[] hasilSinyal = new double[sinyalEEG.length];
		int idx=0, i=0, j=0, i_temp=0, idx_temp=0;
		
		for(i=0;i<sinyalEEG.length;i++){
			if(i<lowPassFilter.length){
				LPF_temp[i] = lowPassFilter[i];
			}else{
				LPF_temp[i] = 0;
			}
		}
		
		for(i=0;i<sinyalEEG.length;i++){
			i_temp = i;
			idx_temp = 0;
			for(j=i; j<sinyalEEG.length; j++){
				hasilSinyal[idx] = hasilSinyal[idx] + (sinyalEEG[i_temp] * LPF_temp[idx_temp]);
				i_temp++;
				idx_temp++;
			}
			idx++;
		}
		return hasilSinyal;
	}
	
	public double[] konvolusiHigh(double[] sinyalEEG){
		double[] highPassFilter = {-0.129409522551260, -0.224143868042013, 0.836516303737808, -0.482962913144534};
		double[] HPF_temp = new double[sinyalEEG.length];
		double[] hasilSinyal = new double[sinyalEEG.length];
		int idx=0, i=0, j=0, i_temp=0, idx_temp=0;
		
		for(i=0;i<sinyalEEG.length;i++){
			if(i<highPassFilter.length){
				HPF_temp[i] = highPassFilter[i];
			}else{
				HPF_temp[i] = 0;
			}
		}
		
		for(i=0;i<sinyalEEG.length;i++){
			i_temp = i;
			idx_temp = 0;
			for(j=i; j<sinyalEEG.length; j++){
				hasilSinyal[idx] = hasilSinyal[idx] + (sinyalEEG[i_temp] * HPF_temp[idx_temp]);
				i_temp++;
				idx_temp++;
			}
			idx++;
		}
		return hasilSinyal;
	}
	
	public double[] getAlfa(double[] sinyalEEG){
		double[] A1_1, AA2, AA2_2, AAA3, AAA3_3, DAAA4, DAAA4_4, ADAAA5, ADAAA5_5, DDAAA5, DDAAA5_5, ADDAAA6, ADDAAA6_6;
		double[] hasilSinyal;
		int i, i_temp;
		
		A1_1 = downSampling(sinyalEEG, 2);
		
		AA2 = konvolusiLow(A1_1);
		AA2_2 = downSampling(AA2, 2);
		
		AAA3 = konvolusiLow(AA2_2);
		AAA3_3 = downSampling(AAA3, 2);
		
		DAAA4 = konvolusiHigh(AAA3_3);
		DAAA4_4 = downSampling(DAAA4, 2);
		
		ADAAA5 = konvolusiHigh(DAAA4_4);
		ADAAA5_5 = downSampling(ADAAA5, 2);
		
		DDAAA5 = konvolusiHigh(DAAA4_4);
		DDAAA5_5 = downSampling(DDAAA5, 2);
		
		ADDAAA6 = konvolusiLow(DDAAA5_5);
		ADDAAA6_6 = downSampling(ADDAAA6, 2);
		
		hasilSinyal = new double[ADAAA5_5.length + ADDAAA6_6.length];
		for(i=0;i<ADAAA5_5.length;i++){
			hasilSinyal[i] = ADAAA5_5[i];
		}
		i_temp = ADAAA5_5.length;
		for(i=0;i<ADDAAA6_6.length;i++){
			hasilSinyal[i_temp] = ADDAAA6_6[i];
			i_temp++;
		}
		
		return hasilSinyal;
	}
	
	public double[] getBeta(double[] sinyalEEG){
		double[] A1_1, AA2, AA2_2, AAA3, AAA3_3, DAA3, DAA3_3, DAAA4, DAAA4_4, DDAAA5, DDAAA5_5, DDDAAA6, DDDAAA6_6;
		double[] hasilSinyal;
		int i, i_temp;
		
		A1_1 = downSampling(sinyalEEG, 2);
		
		AA2 = konvolusiLow(A1_1);
		AA2_2 = downSampling(AA2, 2);
		
		AAA3 = konvolusiLow(AA2_2);
		AAA3_3 = downSampling(AAA3, 2);
		
		DAA3 = konvolusiHigh(AA2_2);
		DAA3_3 = downSampling(DAA3, 2);
		
		DAAA4 = konvolusiHigh(AAA3_3);
		DAAA4_4 = downSampling(DAAA4, 2);
		
		DDAAA5 = konvolusiHigh(DAAA4_4);
		DDAAA5_5 = downSampling(DDAAA5, 2);
		
		DDDAAA6 = konvolusiHigh(DDAAA5_5);
		DDDAAA6_6 = downSampling(DDDAAA6, 2);
		
		hasilSinyal = new double[DAA3_3.length + DDDAAA6_6.length];
		for(i=0;i<DAA3_3.length;i++){
			hasilSinyal[i] = DAA3_3[i];
		}
		i_temp = DAA3_3.length;
		for(i=0;i<DDDAAA6_6.length;i++){
			hasilSinyal[i_temp] = DDDAAA6_6[i];
			i_temp++;
		}
		
		return hasilSinyal;
	}
	
	public double[] getTeta(double[] sinyalEEG){
		double[] A1_1, AA2, AA2_2, AAA3, AAA3_3, AAAA4, AAAA4_4, DAAAA5, DAAAA5_5;
		
		A1_1 = downSampling(sinyalEEG, 2);
		
		AA2 = konvolusiLow(A1_1);
		AA2_2 = downSampling(AA2, 2);
		
		AAA3 = konvolusiLow(AA2_2);
		AAA3_3 = downSampling(AAA3, 2);
		
		AAAA4 = konvolusiLow(AAA3_3);
		AAAA4_4 = downSampling(AAAA4, 2);
		
		DAAAA5 = konvolusiHigh(AAAA4_4);
		DAAAA5_5 = downSampling(DAAAA5, 2);
		
		return DAAAA5_5;
	}
	
	public double[] transformasiWavelet(double[] sinyalEEG, boolean isAlfaUse, boolean isBetaUse, boolean isTetaUse){
		double[] alfa, beta, teta, hasilSinyal;
		int i, i_temp=0, idx=0;
		alfa = getAlfa(sinyalEEG);
		beta = getBeta(sinyalEEG);
		teta = getTeta(sinyalEEG);
		
		if(isAlfaUse == true){
			idx = idx + alfa.length;
		}
		
		if(isBetaUse == true){
			idx = idx + beta.length;
		}
		
		if(isTetaUse == true){
			idx = idx + teta.length;
		}
		hasilSinyal = new double[idx];
		
		if(isAlfaUse == true){
			for(i=0;i<alfa.length;i++){
				hasilSinyal[i_temp] = alfa[i];
				if(i_temp < idx){
					i_temp++;
				}
			}
		}
		
		if(isBetaUse == true){
			for(i=0;i<beta.length;i++){
				hasilSinyal[i_temp] = beta[i];
				if(i_temp < idx){
					i_temp++;
				}
			}
		}
		
		if(isTetaUse == true){
			for(i=0;i<teta.length;i++){
				hasilSinyal[i_temp] = teta[i];
				if(i_temp < idx){
					i_temp++;
				}
			}
		}
		return hasilSinyal;
	}
	
	public Object[][][] getNeuron(double[][][] sinyalEEG, String kelas){
		double[] hasilWavelet = new double[transformasiWavelet(sinyalEEG[0][0], true, true, true).length];
		Object[] objectKelas = new Object[]{(Integer)kelasToInt(kelas)};
		Object[][][] neuron = new Object[sinyalEEG.length][2][sinyalEEG[0].length*hasilWavelet.length];
		int i=0, j=0, k=0, iTemp=0;
		
		hasilWavelet = transformasiWavelet(sinyalEEG[19][119], true, true, true);
		
		for(i=0;i<sinyalEEG.length;i++){
			for(j=0;j<sinyalEEG[i].length;j++){
				hasilWavelet = transformasiWavelet(sinyalEEG[i][j], true, true, true);
				for(k=0;k<hasilWavelet.length;k++){
					if(iTemp < neuron[i][0].length){
						neuron[i][0][iTemp] = hasilWavelet[k];
						iTemp++;
					}
				}
			}
			neuron[i][1] = objectKelas;
			iTemp=0;
		}
		
		return neuron;
	}
	
	public Object[][][][] getBobot(Object[][][] neuron){
		Object[][][][] bobotAndNeuron = new Object[2][neuron.length][neuron[0].length][neuron[0][0].length];
		int i=0, j=0;
		
		for(i=0;i<neuron[0][0].length;i++){
			bobotAndNeuron[0][0][0][i] = neuron[0][0][i];
		}
		bobotAndNeuron[0][0][1] = neuron[0][1];
		
		for(i=1;i<neuron.length;i++){
			for(j=0;j<neuron[0][0].length;j++){
				bobotAndNeuron[1][i][0][j] = neuron[i][0][j];
			}
			bobotAndNeuron[1][i][1] = neuron[i][1];
		}
		
		return bobotAndNeuron;
	}
}