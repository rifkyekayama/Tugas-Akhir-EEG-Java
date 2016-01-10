package lvq;

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
		return hasilCompare;
	}
	
	public int cariJarakBobot(double[] w1, double[] w2, double[] neuron){
		double hasil1=0, hasil2=0;
		int i=0, kelas=0;
		
		for(i=0;i<w1.length;i++){
			hasil1 = hasil1 + Math.pow((neuron[i]-w1[i]), 2);
		}
		hasil1 = Math.sqrt(hasil1);
		
		for(i=0;i<w2.length;i++){
			hasil2 = hasil2 + Math.pow((neuron[i]-w2[i]), 2);
		}
		hasil2 = Math.sqrt(hasil2);
		
		if(Math.min(hasil1, hasil2) == hasil1){
			kelas = 1;
		}else{
			kelas = -1;
		}
		return kelas;
	}
	
	public double[] perbaikiBobot(double[] w, double[] data, double learningRate, boolean kesesuaian){
		double[] wTemp = new double[w.length];
		int i=0;
		
		if(kesesuaian == true){
			for(i=0;i<w.length;i++){
				wTemp[i] = w[i] + (learningRate*(data[i] - w[i]));
			}
		}else if(kesesuaian == false){
			for(i=0;i<w.length;i++){
				wTemp[i] = w[i] - (learningRate*(data[i] - w[i]));
			}
		}
		return wTemp;
	}
	
	public double[][] pembelajaran(Object[][][] w1, Object[][][] w2, Object[][][] data, double learningRate, double pengurangLR, int maxEpoch, double error){
		int epoch=0, i=0, jarak;
		double[] bobot1 = new double[w1[0][0].length];
		double[] bobot2 = new double[w2[1][0].length];
		double[][] result = new double[2][bobot1.length];
		
		bobot1 = objectToDouble(w1[0][0]);
		bobot2 = objectToDouble(w2[1][0]);
		
		while(epoch < maxEpoch){
			for(i=2;i<data.length-2;i++){
				jarak = cariJarakBobot(bobot1, bobot2, objectToDouble(data[i][0]));
				if(jarak == (int)data[i][1][0]){
					if(jarak == 1){
						bobot1 = perbaikiBobot(bobot1, objectToDouble(data[i][0]), learningRate, true);
					}else{
						bobot2 = perbaikiBobot(bobot2, objectToDouble(data[i][0]), learningRate, true);
					}
				}else{
					if(jarak == 1){
						bobot1 = perbaikiBobot(bobot1, objectToDouble(data[i][0]), learningRate, false);
					}else{
						bobot2 = perbaikiBobot(bobot2, objectToDouble(data[i][0]), learningRate, false);
					}
				}
			}
			learningRate = learningRate - (pengurangLR * learningRate);
			epoch++;
		}
		
		for(i=0;i<bobot1.length;i++){
			result[0][i] = bobot1[i];
		}
		for(i=0;i<bobot2.length;i++){
			result[1][i] = bobot2[i];
		}
		return result;
	}
	
	public String[] pengujian(double[] w1, double[] w2, double[][] data){
		String[] hasil = new String[data.length];
		int i=0,jarak=0;
		
		for(i=0;i<data.length;i++){
			jarak = cariJarakBobot(w1, w2, data[i]);
			if(jarak == 1){
				hasil[i] = "Rileks";
			}else{
				hasil[i] = "Non-Rileks";
			}
		}
		return hasil;
	}
	
	public double[] objectToDouble(Object[] data){
		double[] result = new double[data.length];
		int i=0;
		for(i=0;i<data.length;i++){
			result[i] = (double) data[i];
		}
		return result;
	}
	
	public double[][] string2DtoDouble(String[][] data){
		double[][] result = new double[data.length][data[0].length];
		int i=0, j=0;
		for(i=0;i<result.length;i++){
			for(j=0;j<result[i].length;j++){
				result[i][j] = Double.parseDouble(data[i][j]);
			}
		}
		return result;
	}
	
	public int getJumlahHasilUjiRileks(String[] data){
		int jumlah=0, i=0;
		
		for(i=0;i<data.length;i++){
			if(data[i] == "Rileks"){
				jumlah++;
			}
		}
		return jumlah;
	}
	
	public int getJumlahHasilUjiNonRileks(String[] data){
		int jumlah=0, i=0;
		
		for(i=0;i<data.length;i++){
			if(data[i] == "Non-Rileks"){
				jumlah++;
			}
		}
		return jumlah;
	}
}
