package model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Class made to store the computation result of LU or inverse operation on disk
 * Implements Serializable
 * @author yoann
 *
 */
public class ComputationResult implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean lu;//True if LU, false if inverse

	private double[][] originalMatrix;

	private double[] originalVector;

	private double[] solution; 

	private double[][] lowerMatrix;

	private double[][] upperMatrix;

	private double det;

	private double [][] inverseMatrix;

	/**
	 * Create a new Computation Result object
	 * @param b, true if the result is LU operation false if the operation is inverse
	 */
	public ComputationResult(boolean b) {
		this.lu = b;
	}

	//GENERATED GETTERS AND SETTERS

	public void setLu(boolean lu) {
		this.lu = lu;
	}

	public void setOriginalMatrix(double[][] originalMatrix) {
		this.originalMatrix = originalMatrix;
	}

	public void setOriginalVector(double[] originalVector) {
		this.originalVector = originalVector;
	}

	public void setSolution(double[] solution) {
		this.solution = solution;
	}

	public void setLowerMatrix(double[][] lowerMatrix) {
		this.lowerMatrix = lowerMatrix;
	}

	public void setUpperMatrix(double[][] upperMatrix) {
		this.upperMatrix = upperMatrix;
	}

	public void setDet(double det) {
		this.det = det;
	}

	public void setInverseMatrix(double[][] inverseMatrix) {
		this.inverseMatrix = inverseMatrix;
	}

	public boolean isLu() {
		return lu;
	}

	public double[][] getOriginalMatrix() {
		return originalMatrix;
	}

	public double[] getOriginalVector() {
		return originalVector;
	}

	public double[] getSolution() {
		return solution;
	}

	public double[][] getLowerMatrix() {
		return lowerMatrix;
	}

	public double[][] getUpperMatrix() {
		return upperMatrix;
	}

	public double getDet() {
		return det;
	}

	public double[][] getInverseMatrix() {
		return inverseMatrix;
	}

	//WRITE AND READ FUNCTIONS
	/**
	 * Write the object on disk
	 * @param path the path of the object
	 * @throws IOException thrown if there is a probleme while writing the object
	 */
	public void writeResultOnDisk(String path) throws IOException{

		FileOutputStream fileOut = new FileOutputStream(path);
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(this);
		out.close();
		fileOut.close();
	}

	/**
	 * Load result from disk using the given path, throws IO excception if the file is not a result file
	 * @param absolutePath the path of the result on disk
	 * @throws IOException thrown if the file is corrupted
	 * @throws ClassNotFoundException if the ComputationResult class is not found
	 */
	public void loadResultFromDisk(String absolutePath) throws IOException, ClassNotFoundException {
		
		ComputationResult load;
		//Read file from disk
        FileInputStream fileInputStream = new FileInputStream(absolutePath);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
		ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);
		load = (ComputationResult)objectInputStream.readObject();
		objectInputStream.close();
		
        //Copy result to local object     
        setOriginalMatrix(load.getOriginalMatrix());
        setLowerMatrix(load.lowerMatrix);
        setUpperMatrix(load.upperMatrix);
        setDet(load.det);
        setLu(load.lu);
        if(load.lu) {//It is a LU result
        	setOriginalVector(load.getOriginalVector());
        	setSolution(load.getSolution());
        }else {
        	setInverseMatrix(load.getInverseMatrix());
        }
	}


}
