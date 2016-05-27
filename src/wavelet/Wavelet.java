package wavelet;

import java.io.IOException;
import java.util.ArrayList;

import main.TulisFile;
import view.Identifikasi;

public class Wavelet {
	
	public ArrayList<double[][]> sinyalEEG = new ArrayList<double[][]>();
	
	public Wavelet(){
		//constructor kosong
	}
	
	public Wavelet(ArrayList<ArrayList<double[]>> sinyalEEG){
		int i=0, j=0, k=0, x=0;
		
		for(i=0;i<sinyalEEG.size();i++){
			for(j=0;j<sinyalEEG.get(i).size();j++){
				double[][] sinyal = new double[sinyalEEG.get(i).get(j).length][2];
				for(k=0;k<sinyalEEG.get(i).get(j).length;k++){
					sinyal[k][0] = sinyalEEG.get(i).get(j)[k];
					sinyal[k][1] = (k+1)+(sinyalEEG.get(i).get(j).length*x);
				}
				x++;
				this.sinyalEEG.add(sinyal);
			}
			x=0;
		}
	}
	
	public double[][] downSamplingGenap(double[][] sinyalEEG){
		double[][] hasilSinyal = new double[sinyalEEG.length/2][2];
		int i=0, j=1;
		
		for(i=0;i<hasilSinyal.length;i++){
			hasilSinyal[i] = sinyalEEG[j];
			j+=2;
		}
		return hasilSinyal;
	}
	
	public double[][] downSamplingGanjil(double[][] sinyalEEG){
		double[][] hasilSinyal = new double[sinyalEEG.length/2][2];
		int i=0, j=0;
		
		for(i=0;i<hasilSinyal.length;i++){
			hasilSinyal[i] = sinyalEEG[j];
			j+=2;
		}
		return hasilSinyal;
	}
	
	public double[][] konvolusiLow(double[][] sinyalEEG){
		double[] lowPassFilter = {0.482962913144534, 0.836516303737808, 0.224143868042013, -0.129409522551260};
		double[] LPF_temp = new double[sinyalEEG.length];
		double[][] hasilSinyal = new double[sinyalEEG.length][2];
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
				hasilSinyal[idx][0] = hasilSinyal[idx][0] + (sinyalEEG[i_temp][0] * LPF_temp[idx_temp]);
				i_temp++;
				idx_temp++;
			}
			hasilSinyal[idx][1] = sinyalEEG[idx][1];
			idx++;
		}
		return hasilSinyal;
	}
	
	public double[][] konvolusiHigh(double[][] sinyalEEG){
		double[] highPassFilter = {-0.129409522551260, -0.224143868042013, 0.836516303737808, -0.482962913144534};
		double[] HPF_temp = new double[sinyalEEG.length];
		double[][] hasilSinyal = new double[sinyalEEG.length][2];
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
				hasilSinyal[idx][0] = hasilSinyal[idx][0] + (sinyalEEG[i_temp][0] * HPF_temp[idx_temp]);
				i_temp++;
				idx_temp++;
			}
			hasilSinyal[idx][1] = sinyalEEG[idx][1];
			idx++;
		}
		return hasilSinyal;
	}
	
	public double[][] getAlfa(double[][] sinyalEEG, int samplingRate){
		double[][] BA, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, AAA3, AAA3_3, DAAA4, DAAA4_4, ADAAA5, ADAAA5_5, DDAAA5, DDAAA5_5, ADDAAA6, ADDAAA6_6;
		double[][] hasilSinyal;
		int i, i_temp;
		
		if(samplingRate == 128){
//			A1_1 = downSamplingGanjil(sinyalEEG);
			A1_1 = sinyalEEG;
		}else if(samplingRate == 512){
//			BA = downSamplingGanjil(sinyalEEG);
			BA = sinyalEEG;
			try {
				TulisFile.Tulis("Sinyal Awal", BA, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA1 = konvolusiLow(BA);
			try {
				TulisFile.Tulis("Konvolusi Low 1", BA1, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BA1_1 = downSamplingGanjil(BA1);
			try {
				TulisFile.Tulis("downSampling Ganjil 1", BA1_1, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA2 = konvolusiLow(BA1_1);
			try {
				TulisFile.Tulis("Konvolusi Low 2", BA2, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			A1_1 = downSamplingGanjil(BA2);
			try {
				TulisFile.Tulis("DownSampling Ganjil 2", A1_1, 1);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		AA2 = konvolusiLow(A1_1);
		try {
			TulisFile.Tulis("Konvolusi Low 3", AA2, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AA2_2 = downSamplingGanjil(AA2);
		try {
			TulisFile.Tulis("DownSampling ganjil 3", AA2_2, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AAA3 = konvolusiLow(AA2_2);
		try {
			TulisFile.Tulis("konvolusi low 4", AAA3, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AAA3_3 = downSamplingGanjil(AAA3);
		try {
			TulisFile.Tulis("downsampling ganjil 4", AAA3_3, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DAAA4 = konvolusiHigh(AAA3_3);
		try {
			TulisFile.Tulis("konvolusi High 5", DAAA4, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAAA4_4 = downSamplingGenap(DAAA4);
		try {
			TulisFile.Tulis("downsampling Genap 5", DAAA4_4, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ADAAA5 = konvolusiHigh(DAAA4_4);
		try {
			TulisFile.Tulis("konvolusi High 6", ADAAA5, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ADAAA5_5 = downSamplingGenap(ADAAA5);
		try {
			TulisFile.Tulis("downsampling Genap 6", ADAAA5_5, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DDAAA5 = konvolusiHigh(DAAA4_4);
		try {
			TulisFile.Tulis("konvolusi High 7", DDAAA5, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DDAAA5_5 = downSamplingGenap(DDAAA5);
		try {
			TulisFile.Tulis("downsampling Genap 7", DDAAA5_5, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ADDAAA6 = konvolusiLow(DDAAA5_5);
		try {
			TulisFile.Tulis("konvolusi low 8", ADDAAA6, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ADDAAA6_6 = downSamplingGanjil(ADDAAA6);
		try {
			TulisFile.Tulis("downsampling ganjil 8", ADDAAA6_6, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		hasilSinyal = new double[ADAAA5_5.length + ADDAAA6_6.length][2];
		for(i=0;i<ADAAA5_5.length;i++){
			hasilSinyal[i] = ADAAA5_5[i];
		}
		i_temp = ADAAA5_5.length;
		for(i=0;i<ADDAAA6_6.length;i++){
			hasilSinyal[i_temp] = ADDAAA6_6[i];
			i_temp++;
		}
		
		try {
			TulisFile.Tulis("hasil Sinyal", hasilSinyal, 1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hasilSinyal;
	}
	
	public double[][] getBeta(double[][] sinyalEEG, int samplingRate){
		double[][] BA, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, AAA3, AAA3_3, DAA3, DAA3_3, DAAA4, DAAA4_4, DDAAA5, DDAAA5_5, DDDAAA6, DDDAAA6_6;
		double[][] hasilSinyal;
		int i, i_temp;
		
		if(samplingRate == 128){
//			A1_1 = downSamplingGanjil(sinyalEEG);
			A1_1 = sinyalEEG;
		}else if(samplingRate == 512){
//			BA = downSamplingGanjil(sinyalEEG);
			BA = sinyalEEG;
			try {
				TulisFile.Tulis("sinyal Asli", BA, 2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA1 = konvolusiLow(BA);
			try {
				TulisFile.Tulis("konvolusi low 1", BA1, 2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BA1_1 = downSamplingGanjil(BA1);
			try {
				TulisFile.Tulis("down sampling ganjil 1", BA1_1, 2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA2 = konvolusiLow(BA1_1);
			try {
				TulisFile.Tulis("konvolusi low 2", BA2, 2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			A1_1 = downSamplingGanjil(BA2);
			try {
				TulisFile.Tulis("down sampling ganjil 2", A1_1, 2);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		AA2 = konvolusiLow(A1_1);
		try {
			TulisFile.Tulis("konvolusi low 3", AA2, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AA2_2 = downSamplingGanjil(AA2);
		try {
			TulisFile.Tulis("down sampling ganjil 3", AA2_2, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AAA3 = konvolusiLow(AA2_2);
		try {
			TulisFile.Tulis("konvolusi low 4", AAA3, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AAA3_3 = downSamplingGanjil(AAA3);
		try {
			TulisFile.Tulis("down sampling ganjil 4", AAA3_3, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DAA3 = konvolusiHigh(AA2_2);
		try {
			TulisFile.Tulis("konvolusi high 5", DAA3, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAA3_3 = downSamplingGenap(DAA3);
		try {
			TulisFile.Tulis("down sampling genap 5", DAA3_3, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DAAA4 = konvolusiHigh(AAA3_3);
		try {
			TulisFile.Tulis("konvolusi high 6", DAAA4, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAAA4_4 = downSamplingGenap(DAAA4);
		try {
			TulisFile.Tulis("down sampling genap 6", DAAA4_4, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DDAAA5 = konvolusiHigh(DAAA4_4);
		try {
			TulisFile.Tulis("konvolusi high 7", DDAAA5, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DDAAA5_5 = downSamplingGenap(DDAAA5);
		try {
			TulisFile.Tulis("down sampling genap 7", DDAAA5_5, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DDDAAA6 = konvolusiHigh(DDAAA5_5);
		try {
			TulisFile.Tulis("konvolusi high 8", DDDAAA6, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DDDAAA6_6 = downSamplingGenap(DDDAAA6);
		try {
			TulisFile.Tulis("down sampling genap 8", DDDAAA6_6, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		hasilSinyal = new double[DAA3_3.length + DDDAAA6_6.length][2];
		for(i=0;i<DAA3_3.length;i++){
			hasilSinyal[i] = DAA3_3[i];
		}
		i_temp = DAA3_3.length;
		for(i=0;i<DDDAAA6_6.length;i++){
			hasilSinyal[i_temp] = DDDAAA6_6[i];
			i_temp++;
		}
		
		try {
			TulisFile.Tulis("hasil sinyal", hasilSinyal, 2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return hasilSinyal;
	}
	
	public double[][] getTeta(double[][] sinyalEEG, int samplingRate){
		double[][] BA, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, AAA3, AAA3_3, AAAA4, AAAA4_4, DAAAA5, DAAAA5_5;
		
		if(samplingRate == 128){
//			A1_1 = downSamplingGanjil(sinyalEEG);
			A1_1 = sinyalEEG;
		}else if(samplingRate == 512){
//			BA = downSamplingGanjil(sinyalEEG);
			BA = sinyalEEG;
			try {
				TulisFile.Tulis("sinyal asli", BA, 3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA1 = konvolusiLow(BA);
			try {
				TulisFile.Tulis("konvolusi low 1", BA1, 3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BA1_1 = downSamplingGanjil(BA1);
			try {
				TulisFile.Tulis("down sampling ganjil 1", BA1_1, 3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA2 = konvolusiLow(BA1_1);
			try {
				TulisFile.Tulis("konvolusi low 2", BA2, 3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			A1_1 = downSamplingGanjil(BA2);
			try {
				TulisFile.Tulis("down sampling ganjil 2", A1_1, 3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		AA2 = konvolusiLow(A1_1);
		try {
			TulisFile.Tulis("konvolusi low 3", AA2, 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AA2_2 = downSamplingGanjil(AA2);
		try {
			TulisFile.Tulis("down sampling ganjil 3", AA2_2, 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AAA3 = konvolusiLow(AA2_2);
		try {
			TulisFile.Tulis("konvolusi low 4", AAA3, 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AAA3_3 = downSamplingGanjil(AAA3);
		try {
			TulisFile.Tulis("down sampling ganjil 4", AAA3_3, 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AAAA4 = konvolusiLow(AAA3_3);
		try {
			TulisFile.Tulis("konvolusi low 5", AAAA4, 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AAAA4_4 = downSamplingGanjil(AAAA4);
		try {
			TulisFile.Tulis("down sampling ganjil 5", AAAA4_4, 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DAAAA5 = konvolusiHigh(AAAA4_4);
		try {
			TulisFile.Tulis("konvolusi high 6", DAAAA5, 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAAAA5_5 = downSamplingGenap(DAAAA5);
		try {
			TulisFile.Tulis("down sampling genap 6", DAAAA5_5, 3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return DAAAA5_5;
	}
	
	public double[][] gabungkanSinyal(double[][] alfa, double[][] beta, double[][] teta){
		double[][] hasil = new double[alfa.length + beta.length + teta.length][2];
		int i=0, idx = 0;
		
		for(i=0;i<alfa.length;i++){
			hasil[idx] = alfa[i];
			idx++;
		}
		
		for(i=0;i<beta.length;i++){
			hasil[idx] = beta[i];
			idx++;
		}
		
		for(i=0;i<teta.length;i++){
			hasil[idx] = teta[i];
			idx++;
		}
		
		return hasil;
	}
	
	public double[][] pengurutanSinyal(double[][] sinyalEEG){
		int i=0,j=0, minIndex;
		double[] tmp;
		
		for(i=0;i<sinyalEEG.length-1;i++){
			minIndex = i;
			for(j=i+1;j<sinyalEEG.length;j++){
				if(sinyalEEG[minIndex][1] > sinyalEEG[j][1]){
					minIndex = j;
				}
			}
			
			if(minIndex != i){
				tmp = sinyalEEG[i];
				sinyalEEG[i] = sinyalEEG[minIndex];
				sinyalEEG[minIndex] = tmp;
			}
		}
		
		try {
			TulisFile.Tulis("pengurutan sinyal ", sinyalEEG, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sinyalEEG;
	}
	
	public double[] transformasiWavelet(double[][] sinyalEEG, boolean isAlfaUse, boolean isBetaUse, boolean isTetaUse, int samplingRate, boolean isFiltering){
		double[][] alfa, beta, teta;
		double[][] hasilSinyal;
		int i, i_temp=0, idx=0;
		alfa = getAlfa(sinyalEEG, samplingRate);
		beta = getBeta(sinyalEEG, samplingRate);
		teta = getTeta(sinyalEEG, samplingRate);
		
		if(isAlfaUse == true){
			idx = idx + alfa.length;
		}
		
		if(isBetaUse == true){
			idx = idx + beta.length;
		}
		
		if(isTetaUse == true){
			idx = idx + teta.length;
		}
		hasilSinyal = new double[idx][2];
		
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
		
		if(isFiltering == true){
			hasilSinyal = pengurutanSinyal(hasilSinyal);
		}
		return getSinyal(hasilSinyal);
	}
	
	public double[][] getFiltering(double[][] alfa, double[][] beta, double[][] teta){
		
		double[][] hasil = gabungkanSinyal(alfa, beta, teta);
		double[][] urut = pengurutanSinyal(hasil);
		return urut;
	}
	
	public double[][] getNeuronPengujian(ArrayList<double[][]> dataUji, int samplingRate, boolean isFiltering){
		double[] hasilWavelet = new double[transformasiWavelet(dataUji.get(0), true, true, true, samplingRate, isFiltering).length];
		double[][] neuron = new double[dataUji.size()][hasilWavelet.length];
		int i=0;
		
		for(i=0;i<dataUji.size();i++){
			Identifikasi.txtAreaProgressMonitor.append("Ekstraksi Data Ke-"+(i+1)+"\n");
			hasilWavelet = transformasiWavelet(dataUji.get(i), true, true, true, samplingRate, isFiltering);
			neuron[i] = hasilWavelet;
		}
		return neuron;
	}
	
	public double[] getSinyal(double[][] sinyalEEG){
		double[] hasilSinyal = new double[sinyalEEG.length];
		int i=0;
		
		for(i=0;i<hasilSinyal.length;i++){
			hasilSinyal[i] = sinyalEEG[i][0];
		}
		return hasilSinyal;
	}
}