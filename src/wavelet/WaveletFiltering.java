package wavelet;

import java.io.IOException;
import java.util.ArrayList;

import main.TulisFile;
import view.Identifikasi;

public class WaveletFiltering {
	
//	public ArrayList<double[][]> sinyalEEG = new ArrayList<double[][]>();
	
	public WaveletFiltering() {
		// TODO Auto-generated constructor stub
	}
	
//	public WaveletFiltering(ArrayList<ArrayList<double[]>> sinyalEEG) {
//		int i=0, j=0, k=0, x=0;
//		
//		for(i=0;i<sinyalEEG.size();i++){
//			for(j=0;j<sinyalEEG.get(i).size();j++){
//				double[][] sinyal = new double[sinyalEEG.get(i).get(j).length][2];
//				for(k=0;k<sinyalEEG.get(i).get(j).length;k++){
//					sinyal[k][0] = sinyalEEG.get(i).get(j)[k];
//					sinyal[k][1] = (k+1)+(sinyalEEG.get(i).get(j).length*x);
//				}
//				x++;
//				this.sinyalEEG.add(sinyal);
//			}
//			x=0;
//		}
//	}
	
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
			try {
				TulisFile.Tulis("sinyal Asli", sinyalEEG, 4);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA = konvolusiLow(sinyalEEG);
			try {
				TulisFile.Tulis("konvolusi low 1", BA, 4);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BA_1 = downSamplingGanjil(BA);
			try {
				TulisFile.Tulis("down sampling ganjil 1", BA_1, 4);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA1 = konvolusiLow(BA_1);
			try {
				TulisFile.Tulis("konvolusi low 2", BA1, 4);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BA1_1 = downSamplingGanjil(BA1);
			try {
				TulisFile.Tulis("down sampling ganjil 2", BA1_1, 4);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA2 = konvolusiLow(BA1_1);
			try {
				TulisFile.Tulis("konvolusi low 3", BA2, 4);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			A1_1 = downSamplingGanjil(BA2);
			try {
				TulisFile.Tulis("down sampling ganjil 3", A1_1, 4);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		AA2 = konvolusiLow(A1_1);
		try {
			TulisFile.Tulis("konvolusi low 4", AA2, 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AA2_2 = downSamplingGanjil(AA2);
		try {
			TulisFile.Tulis("down sampling ganjil 4", AA2_2, 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AAA3 = konvolusiLow(AA2_2);
		try {
			TulisFile.Tulis("konvolusi low 5", AAA3, 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AAA3_3 = downSamplingGanjil(AAA3);
		try {
			TulisFile.Tulis("down sampling ganjil 5", AAA3_3, 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AAA4 = konvolusiLow(AAA3_3);
		try {
			TulisFile.Tulis("konvolusi low 6", AAA4, 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AAA4_4 = downSamplingGanjil(AAA4);
		try {
			TulisFile.Tulis("down sampling ganjil 6", AAA4_4, 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DAAA5 = konvolusiHigh(AAA4_4);
		try {
			TulisFile.Tulis("konvolusi high 7", DAAA5, 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAAA5_5 = downSamplingGenap(DAAA5);
		try {
			TulisFile.Tulis("down sampling genap 7", DAAA5_5, 4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return DAAA5_5;
	}
	
	public double[][] getFrekuensi9to16(double[][] sinyalEEG, int samplingRate){
		double[][] BA, BA_1, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, AAA3, AAA3_3, 
				   DAAA4, DAAA4_4;
		
		if(samplingRate == 128){
			BA = konvolusiLow(sinyalEEG);
			A1_1 = downSamplingGanjil(BA);
		}else if(samplingRate == 512){
			try {
				TulisFile.Tulis("sinyal asli", sinyalEEG, 5);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA = konvolusiLow(sinyalEEG);
			try {
				TulisFile.Tulis("konvolusi low 1", BA, 5);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BA_1 = downSamplingGanjil(BA);
			try {
				TulisFile.Tulis("down sampling ganjil 1", BA_1, 5);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA1 = konvolusiLow(BA_1);
			try {
				TulisFile.Tulis("konvolusi low 2", BA1, 5);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BA1_1 = downSamplingGanjil(BA1);
			try {
				TulisFile.Tulis("down sampling ganjil 2", BA1_1, 5);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA2 = konvolusiLow(BA1_1);
			try {
				TulisFile.Tulis("konvolusi low 3", BA2, 5);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			A1_1 = downSamplingGanjil(BA2);
			try {
				TulisFile.Tulis("down sampling ganjil 3", A1_1, 5);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		AA2 = konvolusiLow(A1_1);
		try {
			TulisFile.Tulis("konvolusi low 4", AA2, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AA2_2 = downSamplingGanjil(AA2);
		try {
			TulisFile.Tulis("down sampling ganjil 4", AA2_2, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AAA3 = konvolusiLow(AA2_2);
		try {
			TulisFile.Tulis("konvolusi low 5", AAA3, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AAA3_3 = downSamplingGanjil(AAA3);
		try {
			TulisFile.Tulis("down sampling ganjil 5", AAA3_3, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DAAA4 = konvolusiHigh(AAA3_3);
		try {
			TulisFile.Tulis("konvolusi low 6", DAAA4, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAAA4_4 = downSamplingGenap(DAAA4);
		try {
			TulisFile.Tulis("down sampling ganjil 6", DAAA4_4, 5);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return DAAA4_4;
	}
	
	public double[][] getFrekuensi17to24(double[][] sinyalEEG, int samplingRate){
		double[][] BA, BA_1, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, DAA3, DAA3_3, 
				   ADAA4, ADAA4_4;
		
		if(samplingRate == 128){
			BA = konvolusiLow(sinyalEEG);
			A1_1 = downSamplingGanjil(BA);
		}else if(samplingRate == 512){
			try {
				TulisFile.Tulis("sinyal asli", sinyalEEG, 6);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA = konvolusiLow(sinyalEEG);
			try {
				TulisFile.Tulis("konvolusi low 1", BA, 6);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BA_1 = downSamplingGanjil(BA);
			try {
				TulisFile.Tulis("down sampling ganjil 1", BA_1, 6);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA1 = konvolusiLow(BA_1);
			try {
				TulisFile.Tulis("konvolusi low 2", BA1, 6);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BA1_1 = downSamplingGanjil(BA1);
			try {
				TulisFile.Tulis("down sampling ganjil 2", BA1_1, 6);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA2 = konvolusiLow(BA1_1);
			try {
				TulisFile.Tulis("konvolusi low 3", BA2, 6);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			A1_1 = downSamplingGanjil(BA2);
			try {
				TulisFile.Tulis("down sampling ganjil 3", A1_1, 6);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		AA2 = konvolusiLow(A1_1);
		try {
			TulisFile.Tulis("konvolusi low 4", AA2, 6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AA2_2 = downSamplingGanjil(AA2);
		try {
			TulisFile.Tulis("down sampling ganjil 4", AA2_2, 6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DAA3 = konvolusiHigh(AA2_2);
		try {
			TulisFile.Tulis("konvolusi high 5", DAA3, 6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAA3_3 = downSamplingGenap(DAA3);
		try {
			TulisFile.Tulis("down sampling genap 5", DAA3_3, 6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ADAA4 = konvolusiLow(DAA3_3);
		try {
			TulisFile.Tulis("konvolusi low 6", ADAA4, 6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ADAA4_4 = downSamplingGenap(ADAA4);
		try {
			TulisFile.Tulis("down sampling ganjil 6", ADAA4_4, 6);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return ADAA4_4;
	}
	
	public double[][] getFrekuensi25to28(double[][] sinyalEEG, int samplingRate){
		double[][] BA, BA_1, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, DAA3, DAA3_3, 
				   DDAA4, DDAA4_4, ADDAA4, ADDAA4_4;
		
		if(samplingRate == 128){
			BA = konvolusiLow(sinyalEEG);
			A1_1 = downSamplingGanjil(BA);
		}else if(samplingRate == 512){
			try {
				TulisFile.Tulis("Sinyal asli", sinyalEEG, 7);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA = konvolusiLow(sinyalEEG);
			try {
				TulisFile.Tulis("konvolusi low 1", BA, 7);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BA_1 = downSamplingGanjil(BA);
			try {
				TulisFile.Tulis("down sampling ganjil 1", BA_1, 7);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA1 = konvolusiLow(BA_1);
			try {
				TulisFile.Tulis("konvolusi low 2", BA1, 7);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BA1_1 = downSamplingGanjil(BA1);
			try {
				TulisFile.Tulis("down sampling ganjil 2", BA1_1, 7);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA2 = konvolusiLow(BA1_1);
			try {
				TulisFile.Tulis("konvolusi low 3", BA2, 7);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			A1_1 = downSamplingGanjil(BA2);
			try {
				TulisFile.Tulis("down sampling ganjil 3", A1_1, 7);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		AA2 = konvolusiLow(A1_1);
		try {
			TulisFile.Tulis("konvolusi low 4", AA2, 7);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AA2_2 = downSamplingGanjil(AA2);
		try {
			TulisFile.Tulis("down sampling ganjil 4", AA2_2, 7);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DAA3 = konvolusiHigh(AA2_2);
		try {
			TulisFile.Tulis("konvolusi high 5", DAA3, 7);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAA3_3 = downSamplingGenap(DAA3);
		try {
			TulisFile.Tulis("down sampling genap 5", DAA3_3, 7);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DDAA4 = konvolusiHigh(DAA3_3);
		try {
			TulisFile.Tulis("konvolusi high 6", DDAA4, 7);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DDAA4_4 = downSamplingGenap(DDAA4);
		try {
			TulisFile.Tulis("down sampling genap 6", DDAA4_4, 7);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ADDAA4 = konvolusiLow(DDAA4_4);
		try {
			TulisFile.Tulis("konvolusi low 7", ADDAA4, 7);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ADDAA4_4 = downSamplingGanjil(ADDAA4);
		try {
			TulisFile.Tulis("down sampling ganjil 7", ADDAA4_4, 7);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ADDAA4_4;
	}
	
	public double[][] getFrekuensi29to30(double[][] sinyalEEG, int samplingRate){
		double[][] BA, BA_1, BA1, BA1_1, BA2, A1_1 = null, AA2, AA2_2, DAA3, DAA3_3, DDAA4,
				   DDAA4_4, DDDAA5, DDDAA5_5, ADDDAA6, ADDDAA6_6;
		
		if(samplingRate == 128){
			BA = konvolusiLow(sinyalEEG);
			A1_1 = downSamplingGanjil(BA);
		}else if(samplingRate == 512){
			try {
				TulisFile.Tulis("sinyal asli", sinyalEEG, 8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA = konvolusiLow(sinyalEEG);
			try {
				TulisFile.Tulis("konvolusi low 1", BA, 8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BA_1 = downSamplingGanjil(BA);
			try {
				TulisFile.Tulis("down sampling ganjil 1", BA_1, 8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA1 = konvolusiLow(BA_1);
			try {
				TulisFile.Tulis("konvolusi low 2", BA1, 8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BA1_1 = downSamplingGanjil(BA1);
			try {
				TulisFile.Tulis("down sampling ganjil 2", BA1_1, 8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			BA2 = konvolusiLow(BA1_1);
			try {
				TulisFile.Tulis("konvolusi low 3", BA2, 8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			A1_1 = downSamplingGanjil(BA2);
			try {
				TulisFile.Tulis("down sampling ganjil 3", A1_1, 8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		AA2 = konvolusiLow(A1_1);
		try {
			TulisFile.Tulis("konvolusi low 4", AA2, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AA2_2 = downSamplingGanjil(AA2);
		try {
			TulisFile.Tulis("down sampling ganjil 4", AA2_2, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DAA3 = konvolusiHigh(AA2_2);
		try {
			TulisFile.Tulis("konvolusi high 5", DAA3, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DAA3_3 = downSamplingGenap(DAA3);
		try {
			TulisFile.Tulis("down sampling genap 5", DAA3_3, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DDAA4 = konvolusiHigh(DAA3_3);
		try {
			TulisFile.Tulis("konvolusi high 6", DDAA4, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DDAA4_4 = downSamplingGenap(DDAA4);
		try {
			TulisFile.Tulis("down sampling genap 6", DDAA4_4, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DDDAA5 = konvolusiHigh(DDAA4_4);
		try {
			TulisFile.Tulis("konvolusi high 7", DDDAA5, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DDDAA5_5 = downSamplingGenap(DDDAA5);
		try {
			TulisFile.Tulis("down sampling genap 7", DDDAA5_5, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ADDDAA6 = konvolusiLow(DDDAA5_5);
		try {
			TulisFile.Tulis("konvolusi low 8", ADDDAA6, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ADDDAA6_6 = downSamplingGanjil(ADDDAA6);
		try {
			TulisFile.Tulis("down sampling ganjil 8", ADDDAA6_6, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		try {
			TulisFile.Tulis("penggabungan sinyal", hasilSinyal, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		
		try {
			TulisFile.Tulis("pengurutan sinyal ", sinyalEEG, 8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			Identifikasi.txtAreaProgressMonitor.append("Ekstraksi Data Ke-"+(i+1)+"\n");
			hasilFiltering = transformasiWavelet(sinyalEEG.get(i), true, true, true, true, true, samplingRate);
			neuron[i] = getSinyalHasilFiltering(pengurutanSinyal(hasilFiltering));
		}
		return neuron;
	}
}
