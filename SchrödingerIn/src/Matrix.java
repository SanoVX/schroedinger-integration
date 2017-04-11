

public class Matrix {
	
	public static void printMatrix(double[][] A){
		for(int a=0; a<A.length; a++){
			for(int b = 0; b<A[0].length;b++){
				System.out.print(A[a][b]+"  ");
			}
			System.out.println();
		}
	}
	
	public static double[] add(double[] a, double[] b){
		int n = a.length;
        if (b.length != n) throw new RuntimeException("Illegal vector dimensions.");
		double[] ret = new double[n];
		for(int i = 0; i < n; i++){
			ret[i] = a[i] + b[i];
		}
		return ret;
	}
	
	public static double[] multiply(double c, double[] x){
		for(int i=0; i<x.length; i++){
			x[i]*=c;
		}
		return x;
	}
	
	public static double[][] multiply(double c, double[][] A){
		for(int i=0; i<A.length; i++){
			for(int j = 0; j<A[0].length;j++){
				A[i][j] *=c;
			}
		}
		return A;
	}
	
	public static double[] multiply(double[][] A, double[] x){
        int m = A.length;
        int n = A[0].length;
        if (x.length != n) throw new RuntimeException("Illegal matrix dimensions.");
        double[] ret = new double[m];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                ret[i] += A[i][j] * x[j];
        return ret;
	}
	
	public static double multiplySkalar(double[] a, double[] b){
        int n = a.length;
        if (b.length != n) throw new RuntimeException("Illegal vector dimensions.");
        double ret = 0;
        for (int i = 0; i < n; i++)
                ret += a[i] * b[i];
        return ret;
	}
	
	public static double[][] multiplyMatrix(double[] a, double[] b){
        int m = a.length;
        int n = b.length;
        double[][] ret = new double[m][n];
        for(int i = 0; i < m; i++)
        	for (int j = 0; j < n; j++)
                ret[i][j] += a[i] * b[j];
        return ret;
	}
	
	public static double[][] multiply(double[][] A, double[][] B){
		  int m1 = A.length;
	      int n1 = A[0].length;
	      int m2 = B.length;
	      int n2 = B[0].length;
	      if (n1 != m2) throw new RuntimeException("Illegal matrix dimensions.");
	      double[][] ret = new double[m1][n2];
	      for (int i = 0; i < m1; i++)
	          for (int j = 0; j < n2; j++)
	              for (int k = 0; k < n1; k++)
	                  ret[i][j] += A[i][k] * B[k][j];
	      return ret;
	}
	
	public static double[] hadamardDot(double[] a, double[] b){
		int n = a.length;
        if (b.length != n) throw new RuntimeException("Illegal vector dimensions.");
		double[] ret = new double[n];
		for(int i = 0; i < n; i++){
			ret[i] = a[i] * b[i];
		}
		return ret;
	}

	public static double[][] transpose(double[][] A) {
		 int m = A.length;
	     int n = A[0].length;
	     double[][] ret = new double[n][m];
	     for (int i = 0; i < m; i++)
	    	 for (int j = 0; j < n; j++)
	    		 ret[j][i] = A[i][j];
	     return ret;
	}

	public static double[] subtract(double[] a, double[] b) {
		int n = a.length;
        if (b.length != n) throw new RuntimeException("Illegal vector dimensions.");
		double[] ret = new double[n];
		for(int i = 0; i < n; i++){
			ret[i] = a[i] - b[i];
		}
		return ret;
	}
	
	public static double[][] subtract(double[][] A, double[][] B){
		int m = A.length;
		int n = A[0].length;
		if (B.length != m) throw new RuntimeException("Illegal Matrix dimensions.");
		if (B[0].length != n) throw new RuntimeException("Illegal Matrix dimensions.");
		double[][] ret = new double[m][n];
		for(int i = 0; i < m; i++){
			ret[i] = Matrix.subtract(A[i], B[i]);
		}
		return ret;
	}

	public static double[][] hadamardDot(double[][] A, double[][] B) {
		int m = A.length;
		int n = A[0].length;
		if (B.length != m) throw new RuntimeException("Illegal Matrix dimensions.");
		if (B[0].length != n) throw new RuntimeException("Illegal Matrix dimensions.");
		double[][] ret = new double[m][n];
		for(int i = 0; i < m; i++){
			ret[i] = hadamardDot(A[i], B[i]);
		}
		return ret;
	}

	public static double[][] add(double[][] A, double[][] B) {
		int m = A.length;
		int n = A[0].length;
		if (B.length != m) throw new RuntimeException("Illegal Matrix dimensions.");
		if (B[0].length != n) throw new RuntimeException("Illegal Matrix dimensions.");
		double[][] ret = new double[m][n];
		for(int i = 0; i < m; i++){
			ret[i] = Matrix.add(A[i], B[i]);
		}
		return ret;		
	}

	public static double[][] expandToMatrix(double[] b, int n) {
		double[][] ret = new double[n][b.length];
		
		for(int i = 0; i<n; i++){
			ret[i] = b;
		}
		
		return transpose(ret);
	}

	public static double getLength(double[] x) {
		return Math.sqrt(Matrix.multiplySkalar(x, x));
	}
	
	public static double[] norm(double[] x){
		return Matrix.multiply(1/Matrix.getLength(x),x);
	}
	

}
