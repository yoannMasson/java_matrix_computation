package model;
import Jama.LUDecomposition;
import Jama.Matrix;

/**
 * Class encapsulating the matrix operations providing by the JAMA Library
 * To use this class you have to provide a String or a double dimension array representing the matrix
 * Then you need to set the vector in the A.x = B problem to be able to use LU or inverse methods
 * @author yoann
 *
 */
public class MyMatrix {

	private Matrix matrix;
	private Matrix vector;
	private LUDecomposition lu;


	/**
	 * Create a new object MyMatrix, the matrix must be square or an exception is thrown
	 * @param m the double dimension array that represents the matrix
	 * @throws IllegalArgumentException thrown if the matrix is not square or empty
	 */
	public MyMatrix(String m) throws IllegalArgumentException{


		String[] tabMatrix = m.split("\n");
		String[] element; 
		double[][] matrix = new double[tabMatrix.length][tabMatrix.length];
		try {
			for(int i = 0 ; i < tabMatrix.length; i++ ) {
				element = tabMatrix[i].split(" ");
				if(element.length != tabMatrix.length) {
					throw new IllegalArgumentException("PLease input a square matrix");
				}
				for(int j = 0 ; j < element.length; j++ ) {
					matrix[i][j] = Double.parseDouble(element[j]);
				}
			}
		}catch(NumberFormatException e) {
			throw new IllegalArgumentException("Please input only numbers");

		}

		this.matrix = new Matrix(matrix);
		this.lu = new LUDecomposition(this.matrix);			

	}
	
	/**
	 * Create a new matrix object from a double dimension array, the matrix must be square or a nexception is thrown
	 * @param matrix the new matrix representation
	 * @throws IllegalArgumentException thrown if the matrix is not square or is empty
	 */
	public MyMatrix(double[][] matrix) {
		
		if(matrix.length == 0 ) {
			throw new IllegalArgumentException("The matrix is empty");
		}
		for(int i =0 ; i < matrix.length ; i++) {
			if(matrix.length != matrix[i].length) {
				throw new IllegalArgumentException("The matrix is not square");
			}
		}
		this.matrix = new Matrix(matrix);
		this.lu = new LUDecomposition(this.matrix);			
	}

	/**
	 * Provide a print method of the original matrix 
	 */
	@Override
	public String toString() {
		String s = "";
		for(int i = 0 ; i < this.matrix.getRowDimension(); i++ ) {
			for(int j = 0 ; j < this.matrix.getColumnDimension(); j++ ) {
				s += this.matrix.get(i, j) + " ";
			}
			s += "\n";
		}
		return s;
	}

	/**
	 * Set the vector B of the A.X = B equation
	 * @param matrixB the string representation of the vector
	 * @throws IllegalArgumentException in case the vector b can not be parsed or not the same size as the matrix A
	 */
	public void setVector(String matrixB) throws IllegalArgumentException{
		
		String[] element = matrixB.split(" ");
		if(element.length != matrix.getColumnDimension()) {
			throw new IllegalArgumentException("The vector B should be of size "+matrix.getColumnDimension());
		}
		double[][] matrix = new double[element.length][1];

		try {
			for(int i = 0; i < element.length; i++ ) {
				matrix[i][0] = Double.parseDouble(element[i]);
			}
		}catch(NumberFormatException e) {
			throw new IllegalArgumentException("Please input only number in the vector B");
		}
		
		this.vector = new Matrix(matrix);


	}

	/**
	 * Return the vector B of the equation A.X = B as an array of double
	 * @return the vector B
	 */
	public double[] getVectorB() {
		
		double[] d = new double[this.vector.getRowDimension()];
		for(int i = 0 ; i < this.vector.getRowDimension(); i++) {
			d[i] = this.vector.get(i, 0);
		}
		
		return d;
	}

	public Matrix getLower() {
		return lu.getL();
		
	}
	
	public Matrix getUpper() {
		return lu.getU();
		
	}

	public Matrix getSolution() {
		return lu.solve(vector);
		
	}

	public double getDeterminant() {
		return lu.det();
		
	}

	public Matrix getInverse() {
		return matrix.inverse();
	}

	public double[][] getArray() {
		return matrix.getArray();
	}

	/**
	 * Set the vector B of the A.x = B computation problem
	 * @param originalVector a double array representing the vector
	 * @exception IllegalArgumentException thrown if the vector is not of the same size as the matrix
	 */
	public void setVector(double[] originalVector) throws IllegalArgumentException {
		if(originalVector.length != this.matrix.getColumnDimension()) {
			throw new IllegalArgumentException("The vector should be of size "+this.matrix.getColumnDimension());
		}
		double[][] vector = new double[originalVector.length][originalVector.length];
		for(int i = 0 ; i < originalVector.length ; i ++) {
			vector[i][0] = originalVector[i];
		}
		this.vector = new Matrix(vector);
	}


}
