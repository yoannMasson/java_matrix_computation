package view;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import Jama.Matrix;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ComputationResult;
import model.MyMatrix;


/**
 * Controller of the solution view, it is used to display all the informations provided by views
 * @author yoann
 *
 */
public class SolutionViewController {

	private MyMatrix matrix;

	private ComputationResult result;

	@FXML
	private TextArea solutionTextField ;

	public void setMatrix(MyMatrix m) {
		this.matrix = m;
	}


	/**
	 * Display the LU Factorisation of the matrix and the vector in the field of the main view
	 * Indicates on screen if the matrix is singular
	 */
	public void displayLU() {

		result = new ComputationResult(true);
		String s;
		double[] vector = matrix.getVectorB();
		double det;

		Matrix temp;
		det = matrix.getDeterminant();

		DecimalFormat dec = new DecimalFormat("#0.00");

		s = "Original Matrix \n"+matrix;
		s += "Original Vector\n";
		for(int i = 0; i < vector.length; i++ ) {
			s += vector[i]+" ";
		}
		result.setOriginalVector(vector);
		result.setOriginalMatrix(matrix.getArray());
		result.setDet(det);
		s += "\n";
		if(det != 0) {

			s += "Lower matrix\n";
			temp = matrix.getLower();
			result.setLowerMatrix(temp.getArray());
			for(int i = 0; i < temp.getRowDimension(); i++) {
				for( int j = 0 ; j < temp.getColumnDimension(); j++ ) {
					s += dec.format(temp.get(i,j))+" ";
				}
				s += "\n";
			}
			s += "\n";

			s += "Upper matrix\n";
			temp = matrix.getUpper();
			result.setUpperMatrix(temp.getArray());
			for(int i = 0; i < temp.getRowDimension(); i++) {
				for( int j = 0 ; j < temp.getColumnDimension(); j++ ) {
					s += dec.format(temp.get(i,j))+" ";
				}
				s += "\n";
			}
			s += "\n";

			s += "solution:\n";
			temp = matrix.getSolution();
			double[] solution = new double[temp.getRowDimension()];//The vector store in the result computation
			for(int i = 0; i < temp.getRowDimension(); i++) {
				for( int j = 0 ; j < temp.getColumnDimension(); j++ ) {
					s += dec.format(temp.get(i,j))+" ";
					solution[i] = temp.get(i,j);
				}
				s += "\n";
			}
			result.setSolution(solution);
			s += "\n";

		}else {
			s += "No LU decomposition singular matrix”";
			s += "\n";
		}

		s += "determinant:\n";
		s += det;
		s += "\n";

		solutionTextField.setText(s);
	}

	/**
	 * Display the inverse of the matrix in the main view
	 * If the matrix is singular write it on the screen
	 */
	public void displayInverse() {

		result = new ComputationResult(false);
		String s;
		double det;
		Matrix temp;
		DecimalFormat dec = new DecimalFormat("#0.00");
		det = matrix.getDeterminant();

		s = "Original Matrix \n"+matrix;

		if(det != 0) {
			s += "Lower matrix\n";
			temp = matrix.getLower();
			for(int i = 0; i < temp.getRowDimension(); i++) {
				for( int j = 0 ; j < temp.getColumnDimension(); j++ ) {
					s += dec.format(temp.get(i,j))+" ";
				}
				s += "\n";
			}
			s += "\n";

			s += "Upper matrix\n";
			temp = matrix.getUpper();
			for(int i = 0; i < temp.getRowDimension(); i++) {
				for( int j = 0 ; j < temp.getColumnDimension(); j++ ) {
					s += dec.format(temp.get(i,j))+" ";
				}
				s += "\n";
			}
			s += "\n";

			s += "Inverse matrix:\n";
			temp = matrix.getInverse();
			for(int i = 0; i < temp.getRowDimension(); i++) {
				for( int j = 0 ; j < temp.getColumnDimension(); j++ ) {
					s += dec.format(temp.get(i,j))+" ";
				}
				s += "\n";
			}
			s += "\n";
		}else {
			s+= "Matrix is singular\n";
		}


		s += "determinant:\n";
		s += det;
		s += "\n";

		solutionTextField.setText(s);

	}

	/**
	 * dismiss the solution view
	 */
	public void dismissWIndow() {
		// get the stage and close it
		Stage stage = (Stage) solutionTextField.getScene().getWindow();
		stage.close();
	}

	/**
	 * Save the result on disk
	 */
	public void saveResult() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save the computation result");
		File file = fileChooser.showSaveDialog(solutionTextField.getScene().getWindow());

		if (file != null) {
			try {
				this.result.writeResultOnDisk(file.getAbsolutePath());
			} catch (IOException e) {
				displayError(e.getMessage());
			}
		}

	}

	/**
	 * Display error on the text area
	 * @param error the error to be displayed
	 */
	public void displayError(String error) {
		this.solutionTextField.appendText(error+"\n");
	}

	/**
	 * Load result from disk by prompting a file chooser
	 */
	public void loadResult() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select a result file");
		File file = fileChooser.showOpenDialog(solutionTextField.getScene().getWindow());

		if (file != null) {
			try {
				this.result.loadResultFromDisk(file.getAbsolutePath());
				this.matrix = new MyMatrix(this.result.getOriginalMatrix());
				if(this.result.isLu()) {
					this.matrix.setVector(this.result.getOriginalVector());
					displayLU();
				}else {
					displayInverse();
				}
			} catch (IOException e) {
				displayError(e.getMessage());
			} catch (ClassNotFoundException e) {
				displayError(e.getMessage());
			}
		}
	}
}
