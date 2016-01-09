package lvq;

import java.util.Arrays;

import wavelet.Wavelet;

public class LVQ {
	
	private Wavelet wavelet = new Wavelet();
	
	public LVQ() {
		// TODO Auto-generated constructor stub
	}
	
	public Object[][][][] initLVQ(double[][][] data1, double[][][] data2){
		Object[][][][] bobotDanNeuronRileks, bobotDanNeuronNonRileks, hasilCompare;
		int i=0, i2=0, j=0;
		
		bobotDanNeuronRileks = wavelet.getBobot(wavelet.getNeuron(data1, "Rileks"));
		bobotDanNeuronNonRileks = wavelet.getBobot(wavelet.getNeuron(data2, "Non-Rileks"));
		
		hasilCompare = new Object[3][bobotDanNeuronRileks[0].length+bobotDanNeuronNonRileks[0].length][2][bobotDanNeuronRileks[0][0][0].length];
		
		for(i=0;i<bobotDanNeuronRileks[0][0][0].length;i++){
			hasilCompare[0][0][0][i] = bobotDanNeuronRileks[0][0][0][i];
		}
		hasilCompare[0][0][1] = bobotDanNeuronRileks[0][0][1];
		
		for(i=0;i<bobotDanNeuronNonRileks[0][0][0].length;i++){
			hasilCompare[1][1][0][i] = bobotDanNeuronNonRileks[0][0][0][i];
		}
		hasilCompare[1][1][1] = bobotDanNeuronNonRileks[0][0][1];
		
		for(i=2;i<bobotDanNeuronRileks[1].length;i++){
			for(j=0;j<bobotDanNeuronRileks[1][i][0].length;j++){
				hasilCompare[2][i][0][j] = bobotDanNeuronRileks[1][i-1][0][j];
			}
			hasilCompare[2][i][1] = bobotDanNeuronRileks[1][i-1][1];
		}
		
		for(i2=2;i2<bobotDanNeuronNonRileks[1].length;i2++){
			for(j=0;j<bobotDanNeuronNonRileks[1][i2][0].length;j++){
				hasilCompare[2][(i2+i)-2][0][j] = bobotDanNeuronNonRileks[1][i2-1][0][j];
			}
			hasilCompare[2][(i2+i)-2][1] = bobotDanNeuronNonRileks[1][i2-1][1];
		}
		
		System.out.println(hasilCompare.length);           //3
		System.out.println(hasilCompare[0].length);        //40
		System.out.println(hasilCompare[0][0].length);     //2
		System.out.println(hasilCompare[0][0][0].length);  //6720
		
		System.out.println(Arrays.toString(hasilCompare[2][19][1]));
		for(i=2;i<hasilCompare.length;i++){
			for(j=2;j<hasilCompare[i].length-2;j++){
				System.out.println(i+" "+j+" "+hasilCompare[i][j][0][0]+" "+Arrays.toString(hasilCompare[i][j][1]));
			}
		}
		System.out.println(Arrays.toString(hasilCompare[0][0][0]));
		
		return hasilCompare;
	}
	
	public Object[] pembelajaran(Object[][][] w1, Object[][][] w2, Object[][][] data, int learningRate, int pengurangLR, int maxEpoch, double error){
		return null;
	}
}
