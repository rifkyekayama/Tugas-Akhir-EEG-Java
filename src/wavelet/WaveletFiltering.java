package wavelet;

import java.util.ArrayList;
public class WaveletFiltering {
	
	public ArrayList<double[][]> sinyalEEG = new ArrayList<double[][]>();
	
	public WaveletFiltering() {
		// TODO Auto-generated constructor stub
	}
	
	public WaveletFiltering(ArrayList<ArrayList<double[]>> sinyalEEG) {
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
		
		for(i=0; i<hasilSinyal.length;i++){
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
	
	public double[][] getFrekuensi5to8(double[][] sinyalEEG, int samplingRate){
		double[][] BA, BA_1, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, AAA3, AAA3_3, AAA4,
				   AAA4_4, DAAA5, DAAA5_5;
		
		if(samplingRate == 128){
			BA = konvolusiLow(sinyalEEG);
			A1_1 = downSamplingGanjil(BA);
		}else if(samplingRate == 512){
			BA = konvolusiLow(sinyalEEG);
			BA_1 = downSamplingGanjil(BA);
			
			BA1 = konvolusiLow(BA_1);
			BA1_1 = downSamplingGanjil(BA1);
			
			BA2 = konvolusiLow(BA1_1);
			A1_1 = downSamplingGanjil(BA2);
		}
		
		AA2 = konvolusiLow(A1_1);
		AA2_2 = downSamplingGanjil(AA2);
		
		AAA3 = konvolusiLow(AA2_2);
		AAA3_3 = downSamplingGanjil(AAA3);
		
		AAA4 = konvolusiLow(AAA3_3);
		AAA4_4 = downSamplingGanjil(AAA4);
		
		DAAA5 = konvolusiHigh(AAA4_4);
		DAAA5_5 = downSamplingGenap(DAAA5);
		
		return DAAA5_5;
	}
	
	public double[][] getFrekuensi9to16(double[][] sinyalEEG, int samplingRate){
		double[][] BA, BA_1, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, AAA3, AAA3_3, 
				   DAAA4, DAAA4_4;
		
		if(samplingRate == 128){
			BA = konvolusiLow(sinyalEEG);
			A1_1 = downSamplingGanjil(BA);
		}else if(samplingRate == 512){
			BA = konvolusiLow(sinyalEEG);
			BA_1 = downSamplingGanjil(BA);
			
			BA1 = konvolusiLow(BA_1);
			BA1_1 = downSamplingGanjil(BA1);
			
			BA2 = konvolusiLow(BA1_1);
			A1_1 = downSamplingGanjil(BA2);
		}
		
		AA2 = konvolusiLow(A1_1);
		AA2_2 = downSamplingGanjil(AA2);
		
		AAA3 = konvolusiLow(AA2_2);
		AAA3_3 = downSamplingGanjil(AAA3);
		
		DAAA4 = konvolusiHigh(AAA3_3);
		DAAA4_4 = downSamplingGenap(DAAA4);
		
		return DAAA4_4;
	}
	
	public double[][] getFrekuensi17to24(double[][] sinyalEEG, int samplingRate){
		double[][] BA, BA_1, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, DAA3, DAA3_3, 
				   ADAA4, ADAA4_4;
		
		if(samplingRate == 128){
			BA = konvolusiLow(sinyalEEG);
			A1_1 = downSamplingGanjil(BA);
		}else if(samplingRate == 512){
			BA = konvolusiLow(sinyalEEG);
			BA_1 = downSamplingGanjil(BA);
			
			BA1 = konvolusiLow(BA_1);
			BA1_1 = downSamplingGanjil(BA1);
			
			BA2 = konvolusiLow(BA1_1);
			A1_1 = downSamplingGanjil(BA2);
		}
		
		AA2 = konvolusiLow(A1_1);
		AA2_2 = downSamplingGanjil(AA2);
		
		DAA3 = konvolusiHigh(AA2_2);
		DAA3_3 = downSamplingGenap(DAA3);
		
		ADAA4 = konvolusiLow(DAA3_3);
		ADAA4_4 = downSamplingGenap(ADAA4);
			
		return ADAA4_4;
	}
	
	public double[][] getFrekuensi25to28(double[][] sinyalEEG, int samplingRate){
		double[][] BA, BA_1, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, DAA3, DAA3_3, 
				   DDAA4, DDAA4_4, ADDAA4, ADDAA4_4;
		
		if(samplingRate == 128){
			BA = konvolusiLow(sinyalEEG);
			A1_1 = downSamplingGanjil(BA);
		}else if(samplingRate == 512){
			BA = konvolusiLow(sinyalEEG);
			BA_1 = downSamplingGanjil(BA);
			
			BA1 = konvolusiLow(BA_1);
			BA1_1 = downSamplingGanjil(BA1);
			
			BA2 = konvolusiLow(BA1_1);
			A1_1 = downSamplingGanjil(BA2);
		}
		
		AA2 = konvolusiLow(A1_1);
		AA2_2 = downSamplingGanjil(AA2);
		
		DAA3 = konvolusiHigh(AA2_2);
		DAA3_3 = downSamplingGenap(DAA3);
		
		DDAA4 = konvolusiHigh(DAA3_3);
		DDAA4_4 = downSamplingGenap(DDAA4);
		
		ADDAA4 = konvolusiLow(DDAA4_4);
		ADDAA4_4 = downSamplingGanjil(ADDAA4);
		
		return ADDAA4_4;
	}
	
	public double[][] getFrekuensi29to30(double[][] sinyalEEG, int samplingRate){
		double[][] BA, BA_1, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, DAA3, DAA3_3, DDAA4,
				   DDAA4_4, DDDAA5, DDDAA5_5, ADDDAA6, ADDDAA6_6;
		
		if(samplingRate == 128){
			BA = konvolusiLow(sinyalEEG);
			A1_1 = downSamplingGanjil(BA);
		}else if(samplingRate == 512){
			BA = konvolusiLow(sinyalEEG);
			BA_1 = downSamplingGanjil(BA);
			
			BA1 = konvolusiLow(BA_1);
			BA1_1 = downSamplingGanjil(BA1);
			
			BA2 = konvolusiLow(BA1_1);
			A1_1 = downSamplingGanjil(BA2);
		}
		
		AA2 = konvolusiLow(A1_1);
		AA2_2 = downSamplingGanjil(AA2);
		
		DAA3 = konvolusiHigh(AA2_2);
		DAA3_3 = downSamplingGenap(DAA3);
		
		DDAA4 = konvolusiHigh(DAA3_3);
		DDAA4_4 = downSamplingGenap(DDAA4);
		
		DDDAA5 = konvolusiHigh(DDAA4_4);
		DDDAA5_5 = downSamplingGenap(DDDAA5);
		
		ADDDAA6 = konvolusiLow(DDDAA5_5);
		ADDDAA6_6 = downSamplingGanjil(ADDDAA6);
		
		return ADDDAA6_6;
	}
	
	public double[][] gabungkanSinyal(double[][] freq5to8, double[][] freq9to16, double[][] freq17to24, double[][] freq25to28, double[][] freq29to30){
		double[][] hasilSinyal = new double[freq5to8.length+freq9to16.length+freq17to24.length+freq25to28.length+freq29to30.length][2];
		int i=0, idx=0;
		
		for(i=0;i<freq5to8.length;i++){
			hasilSinyal[idx] = freq5to8[i];
			idx++;
		}
		
		for(i=0;i<freq9to16.length;i++){
			hasilSinyal[idx] = freq9to16[i];
			idx++;
		}
		
		for(i=0;i<freq17to24.length;i++){
			hasilSinyal[idx] = freq17to24[i];
			idx++;
		}
		
		for(i=0;i<freq25to28.length;i++){
			hasilSinyal[idx] = freq25to28[i];
			idx++;
		}
		
		for(i=0;i<freq29to30.length;i++){
			hasilSinyal[idx] = freq29to30[i];
			idx++;
		}
		return hasilSinyal;
	}
	
	public double[][] transformasiWavelet(double[][] sinyalEEG, boolean isUseFreq5to8, boolean isUseFreq9to16, boolean isUseFreq17to24, boolean isUseFreq25to28, boolean isUseFreq29to30, int samplingRate){
		double[][] freq5to8, freq9to16, freq17to24, freq25to28, freq29to30, hasilSinyal;
		int i, i_temp=0, idx=0;
		
		freq5to8 = getFrekuensi5to8(sinyalEEG, samplingRate);
		freq9to16 = getFrekuensi9to16(sinyalEEG, samplingRate);
		freq17to24 = getFrekuensi17to24(sinyalEEG, samplingRate);
		freq25to28 = getFrekuensi25to28(sinyalEEG, samplingRate);
		freq29to30 = getFrekuensi29to30(sinyalEEG, samplingRate);
		
		if(isUseFreq5to8){
			idx = idx + freq5to8.length;
		}
		
		if(isUseFreq9to16){
			idx = idx + freq9to16.length;
		}
		
		if(isUseFreq17to24){
			idx = idx + freq17to24.length;
		}
		
		if(isUseFreq25to28){
			idx = idx + freq25to28.length;
		}
		
		if(isUseFreq29to30){
			idx = idx + freq29to30.length;
		}
		
		hasilSinyal = new double[idx][2];
		
		if(isUseFreq5to8 == true){
			for(i=0;i<freq5to8.length;i++){
				hasilSinyal[i_temp] = freq5to8[i];
				if(i_temp < idx){
					i_temp++;
				}
			}
		}
		
		if(isUseFreq9to16 == true){
			for(i=0;i<freq9to16.length;i++){
				hasilSinyal[i_temp] = freq9to16[i];
				if(i_temp < idx){
					i_temp++;
				}
			}
		}
		
		if(isUseFreq17to24 == true){
			for(i=0;i<freq17to24.length;i++){
				hasilSinyal[i_temp] = freq17to24[i];
				if(i_temp < idx){
					i_temp++;
				}
			}
		}
		
		if(isUseFreq25to28 == true){
			for(i=0;i<freq25to28.length;i++){
				hasilSinyal[i_temp] = freq25to28[i];
				if(i_temp < idx){
					i_temp++;
				}
			}
		}
		
		if(isUseFreq29to30 == true){
			for(i=0;i<freq29to30.length;i++){
				hasilSinyal[i_temp] = freq29to30[i];
				if(i_temp < idx){
					i_temp++;
				}
			}
		}
		return hasilSinyal;
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
		return sinyalEEG;
	}
	
	public double[] getSinyalHasilFiltering(double[][] sinyalEEG){
		double[] hasilSinyal = new double[sinyalEEG.length];
		int i=0;
		
		for(i=0;i<hasilSinyal.length;i++){
			hasilSinyal[i] = sinyalEEG[i][0];
		}
		return hasilSinyal;
	}
	
	public double[][] getNeuronPengujian(ArrayList<double[][]> sinyalEEG, int samplingRate){
		double[][] hasilFiltering = new double[transformasiWavelet(sinyalEEG.get(0), true, true, true, true, true, samplingRate).length][2];
		double[][] neuron = new double[sinyalEEG.size()][hasilFiltering.length];
		int i=0;
		
		for(i=0;i<sinyalEEG.size();i++){
			hasilFiltering = transformasiWavelet(sinyalEEG.get(i), true, true, true, true, true, samplingRate);
			neuron[i] = getSinyalHasilFiltering(pengurutanSinyal(hasilFiltering));
		}
		return neuron;
	}
}
