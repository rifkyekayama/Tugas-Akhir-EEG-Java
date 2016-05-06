package wavelet;

import java.util.ArrayList;

public class Wavelet {
	public WaveletEkstraksi waveletEkstraksi;
	public WaveletFiltering waveletFiltering;
	public ArrayList<double[][]> sinyalEEG = new ArrayList<double[][]>();
	
	public Wavelet(ArrayList<ArrayList<double[]>> sinyalEEG) {
		// TODO Auto-generated constructor stub
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
		
		
		waveletEkstraksi = new WaveletEkstraksi();
		waveletFiltering = new WaveletFiltering();
	}
}
