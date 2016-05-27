package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TulisFile {
	
	public static String alfa = "alfa.txt";
	public static String beta = "beta.txt";
	public static String teta = "teta.txt";
	public static String filter5to8 = "filter5to8.txt";
	public static String filter9to16 = "filter9to16.txt";
	public static String filter17to24 = "filter17to24.txt";
	public static String filter25to28 = "filter25to28.txt";
	public static String filter29to30 = "filter29to30.txt";
	
	public static void Tulis(String judul, double[][] isi, int kodeFile) throws IOException {
		// TODO Auto-generated constructor stub
		
		String namaFile = "";
		
		if(kodeFile == 1){
			namaFile = alfa;
		}else if(kodeFile == 2){
			namaFile = beta;
		}else if(kodeFile == 3){
			namaFile = teta;
		}else if(kodeFile == 4){
			namaFile = filter5to8;
		}else if(kodeFile == 5){
			namaFile = filter9to16;
		}else if(kodeFile == 6){
			namaFile = filter17to24;
		}else if(kodeFile == 7){
			namaFile = filter25to28;
		}else if(kodeFile == 8){
			namaFile = filter29to30;
		}
		
//		PrintWriter out = new PrintWriter(new BufferedWriter(
//	            new FileWriter(namaFile, true)));
//		out.println(judul+" :\n");
//		out.println("jumlah total ="+isi.length+"\n");
//		for(int i=0;i<isi.length;i++){
//			out.print(Double.toString(isi[i][0])+"--"+Integer.toString((int)isi[i][1])+", ");
//		}
//		out.print("\n\n\n");
//		out.close();
	}
	
	public static void TulisPengujian(String jenis, int naracoba, int dikenaliRileks, int dikenaliTidakRileks, String waktuEksekusi) throws IOException {
		// TODO Auto-generated constructor stub
		
		String namaFile = "Pengujian.txt";
		
		PrintWriter out = new PrintWriter(new BufferedWriter(
	            new FileWriter(namaFile, true)));
		out.println(jenis+"  |  "+"naracoba "+naracoba+"  |  rileks = "+dikenaliRileks+"  |  Tidak_Rileks = "+dikenaliTidakRileks+"  |  waktu_eksekusi = "+waktuEksekusi+"\n");
		out.close();
	}
	
	public static void TulisEnterDoble() throws IOException {
		// TODO Auto-generated constructor stub
		
		String namaFile = "Pengujian.txt";
		
		PrintWriter out = new PrintWriter(new BufferedWriter(
	            new FileWriter(namaFile, true)));
		out.println("\n\n\n");
		out.close();
	}
}
