package view;

import controller.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Controller of the main view, use to clear the field and call the controller of the solution view
 * @author yoann
 *
 */
public class MainViewController {

	//FIELD FROM THE VIEW
	@FXML
	private Button LUButton;
	
	@FXML
	private Button InverseButton;
	
	@FXML
	private Button ClearButton;
	
	@FXML
	private TextArea matrixA;
	
	@FXML
	private TextArea matrixB;
	
	@FXML
	private Label errorLabel;
	
	private MainApp main;

	//METHODS
	public void setMainApp(MainApp mainApp) {
		this.main = mainApp;
	}
	
	/**
	 * Pop the window of the LU pivot using the value from text fields
	 */
	public void popLUPivot() {
		this.main.popSolutionViewLU(matrixA.getText(),matrixB.getText());
	}
	
	/**
	 * pop the window of the inverse using value from the left text area
	 */
	public void popInverse() {
		this.main.popSolutionViewInverse(matrixA.getText());
	}
	
	/**
	 * Clear all the field
	 */
	public void clearField() {
		matrixA.setText("");
		matrixB.setText("");
		clearError();
	}
	
	/**
	 * Show error in the center of the view
	 * @param message the message to be displayed
	 */
	public void showError(String message) {
		this.errorLabel.setText(message);
	}
	
	/**
	 * CLear the label showing the error
	 */
	public void clearError() {
		this.errorLabel.setText("");
	}
	
}
