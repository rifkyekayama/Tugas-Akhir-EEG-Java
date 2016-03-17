package lvq;

import java.util.ArrayList;

public class LVQ {
	
	public LVQ() {
		// TODO Auto-generated constructor stub
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
	
	public double[][] pembelajaran(ArrayList<double[][]> neuronRileks, ArrayList<double[][]> neuronNonRileks, double learningRate, double pengurangLR, int maxEpoch, double error){
		int epoch=0, i=0, jarak;
		double[] bobot1 = new double[neuronRileks.get(0)[0].length];
		double[] bobot2 = new double[neuronNonRileks.get(0)[0].length];
		ArrayList<double[][]> data = new ArrayList<double[][]>();
		double[][] result = new double[2][bobot1.length];
		
		bobot1 = neuronRileks.get(0)[0];
		bobot2 = neuronNonRileks.get(0)[0];
		
		for(i=1;i<neuronRileks.size();i++){
			data.add(neuronRileks.get(i));
		}
		
		for(i=1;i<neuronNonRileks.size();i++){
			data.add(neuronNonRileks.get(i));
		}
		
		while(epoch < maxEpoch && learningRate > error){
			for(i=0;i<data.size();i++){
				jarak = cariJarakBobot(bobot1, bobot2, data.get(i)[0]);
				if(jarak == (int)data.get(i)[1][0]){
					if(jarak == 1){
						bobot1 = perbaikiBobot(bobot1, data.get(i)[0], learningRate, true);
					}else{
						bobot2 = perbaikiBobot(bobot2, data.get(i)[0], learningRate, true);
					}
				}else{
					if(jarak == 1){
						bobot1 = perbaikiBobot(bobot1, data.get(i)[0], learningRate, false);
					}else{
						bobot2 = perbaikiBobot(bobot2, data.get(i)[0], learningRate, false);
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
			System.out.println(hasil[i]);
		}
		return hasil;
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
