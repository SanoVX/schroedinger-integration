package org.schrodinger.gui;

public class linearFit {
	
	public static double mittelwert(double[] mea){
		double m = 0;
		for(int i = 0; i < mea.length; i++){
			m+=mea[i];
		}
		return m;
	}

	public static double[] parameters(double[][] mea){
		double[] listXY = new double[mea.length];
		for(int i = 0; i < mea.length; i++){
			listXY[i] = mea[i][0]*mea[i][1];
		}
		double XY = mittelwert(listXY);
		
		double[] listX = new double[mea.length];
		for(int i = 0; i < mea.length; i++){
			listX[i] = mea[i][0];
		}
		double X = mittelwert(listX);
		
		double[] listXX = new double[mea.length];
		for(int i = 0; i < mea.length; i++){
			listXX[i] = mea[i][0]*mea[i][0];
		}
		double XX = mittelwert(listXX);
		
		
		double[] listY = new double[mea.length];
		for(int i = 0; i < mea.length; i++){
			listY[i] = mea[i][1];
		}
		double Y = mittelwert(listY);
		double n = mea.length;
		double b = (XX*Y-XY*X)/(n*XX-X*X);
		double m = (n*XY-X*Y)/(n*XX-X*X);
		
		double[] para = {m,b};
		return para;
	}
}
