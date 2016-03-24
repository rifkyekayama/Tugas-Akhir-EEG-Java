package wavelet;

public class Wavelet {
	
	public Wavelet(){
		//constructor kosong
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
	
	public double[] downSamplingGenap(double[] sinyalEEG){
		double[] hasilSinyal = new double[sinyalEEG.length/2];
		int i=0, j=1;
		
		for(i=0;i<hasilSinyal.length;i++){
			hasilSinyal[i] = sinyalEEG[j];
			j+=2;
		}
		return hasilSinyal;
	}
	
	public double[] downSamplingGanjil(double[] sinyalEEG){
		double[] hasilSinyal = new double[sinyalEEG.length/2];
		int i=0, j=0;
		
		for(i=0;i<hasilSinyal.length;i++){
			hasilSinyal[i] = sinyalEEG[j];
			j+=2;
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
	
	public double[] getAlfa(double[] sinyalEEG, int samplingRate){
		double[] BA, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, AAA3, AAA3_3, DAAA4, DAAA4_4, ADAAA5, ADAAA5_5, DDAAA5, DDAAA5_5, ADDAAA6, ADDAAA6_6;
		double[] hasilSinyal;
		int i, i_temp;
		
		if(samplingRate == 128){
			A1_1 = downSamplingGanjil(sinyalEEG);
		}else if(samplingRate == 512){
			BA = downSamplingGanjil(sinyalEEG);
			
			BA1 = konvolusiLow(BA);
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
		
		ADAAA5 = konvolusiHigh(DAAA4_4);
		ADAAA5_5 = downSamplingGenap(ADAAA5);
		
		DDAAA5 = konvolusiHigh(DAAA4_4);
		DDAAA5_5 = downSamplingGenap(DDAAA5);
		
		ADDAAA6 = konvolusiLow(DDAAA5_5);
		ADDAAA6_6 = downSamplingGanjil(ADDAAA6);
		
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
	
	public double[] getBeta(double[] sinyalEEG, int samplingRate){
		double[] BA, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, AAA3, AAA3_3, DAA3, DAA3_3, DAAA4, DAAA4_4, DDAAA5, DDAAA5_5, DDDAAA6, DDDAAA6_6;
		double[] hasilSinyal;
		int i, i_temp;
		
		if(samplingRate == 128){
			A1_1 = downSamplingGanjil(sinyalEEG);
		}else if(samplingRate == 512){
			BA = downSamplingGanjil(sinyalEEG);
			
			BA1 = konvolusiLow(BA);
			BA1_1 = downSamplingGanjil(BA1);
			
			BA2 = konvolusiLow(BA1_1);
			A1_1 = downSamplingGanjil(BA2);
		}
		
		AA2 = konvolusiLow(A1_1);
		AA2_2 = downSamplingGanjil(AA2);
		
		AAA3 = konvolusiLow(AA2_2);
		AAA3_3 = downSamplingGanjil(AAA3);
		
		DAA3 = konvolusiHigh(AA2_2);
		DAA3_3 = downSamplingGenap(DAA3);
		
		DAAA4 = konvolusiHigh(AAA3_3);
		DAAA4_4 = downSamplingGenap(DAAA4);
		
		DDAAA5 = konvolusiHigh(DAAA4_4);
		DDAAA5_5 = downSamplingGenap(DDAAA5);
		
		DDDAAA6 = konvolusiHigh(DDAAA5_5);
		DDDAAA6_6 = downSamplingGenap(DDDAAA6);
		
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
	
	public double[] getTeta(double[] sinyalEEG, int samplingRate){
		double[] BA, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, AAA3, AAA3_3, AAAA4, AAAA4_4, DAAAA5, DAAAA5_5;
		
		if(samplingRate == 128){
			A1_1 = downSamplingGanjil(sinyalEEG);
		}else if(samplingRate == 512){
			BA = downSamplingGanjil(sinyalEEG);
			
			BA1 = konvolusiLow(BA);
			BA1_1 = downSamplingGanjil(BA1);
			
			BA2 = konvolusiLow(BA1_1);
			A1_1 = downSamplingGanjil(BA2);
		}
		
		AA2 = konvolusiLow(A1_1);
		AA2_2 = downSamplingGanjil(AA2);
		
		AAA3 = konvolusiLow(AA2_2);
		AAA3_3 = downSamplingGanjil(AAA3);
		
		AAAA4 = konvolusiLow(AAA3_3);
		AAAA4_4 = downSamplingGanjil(AAAA4);
		
		DAAAA5 = konvolusiHigh(AAAA4_4);
		DAAAA5_5 = downSamplingGenap(DAAAA5);
		
		return DAAAA5_5;
	}
	
	public double[] transformasiWavelet(double[] sinyalEEG, boolean isAlfaUse, boolean isBetaUse, boolean isTetaUse, int samplingRate){
		double[] alfa, beta, teta, hasilSinyal;
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
	
	public double[][] getNeuronPengujian(double[][] dataUji, int samplingRate){
		double[] hasilWavelet = new double[transformasiWavelet(dataUji[0], true, true, true, samplingRate).length];
		double[][] neuron = new double[dataUji.length][hasilWavelet.length];
		int i=0;
		
		for(i=0;i<dataUji.length;i++){
			hasilWavelet = transformasiWavelet(dataUji[i], true, true, true, samplingRate);
			dataUji[i] = hasilWavelet;
		}
		return neuron;
	}
}