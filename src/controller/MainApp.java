package controller;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.MyMatrix;
import view.MainViewController;
import view.SolutionViewController;

public class MainApp extends Application {

	private Stage primaryStage;
	private AnchorPane rootLayout;
	private MainViewController mainView;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Matrix Computation");

		initRootLayout();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/view/MainView.fxml"));
			rootLayout = (AnchorPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();

			//Set the main view controller
			MainViewController controller = loader.getController();
			controller.setMainApp(this);
			this.mainView = controller;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Pop the window of the solution view using LU Computation
	 * @param matrixA the original matrix in A.x = B
	 * @param matrixB the vector in A.x = B
	 */
	public void popSolutionViewLU(String matrixA, String matrixB) {
		try {
			MyMatrix matrixa = new MyMatrix(matrixA);
			matrixa.setVector(matrixB);
			try {
				// Load root layout from fxml file.
				FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/view/SolutionView.fxml"));
				BorderPane viewLayout = (BorderPane) loader.load();

				// Show the scene containing the root layout.
				Scene newScene = new Scene(viewLayout);
				Stage newStage = new Stage();
				newStage.setScene(newScene);
				newStage.show();

				//Input the matrices value
				SolutionViewController controller = loader.getController();
				controller.setMatrix(matrixa);
				controller.displayLU();

				this.mainView.clearError();

			}catch(IOException e) {
				e.printStackTrace();
			}
		}catch(IllegalArgumentException e ) {//In case the matrixA can not be parsed
			this.printError(e.getMessage());
		}
	}


	/**
	 * Pop the window of the matrix inverse
	 * @param matrixA the matrix to inverse
	 */
	public void popSolutionViewInverse(String matrixA) {
		try {
			MyMatrix matrixa = new MyMatrix(matrixA);
			try {
				// Load root layout from fxml file.
				FXMLLoader loader = new FXMLLoader();
				BorderPane viewLayout;
				loader.setLocation(MainApp.class.getResource("/view/SolutionView.fxml"));
				viewLayout = (BorderPane) loader.load();

				// Show the scene containing the root layout.
				Scene newScene = new Scene(viewLayout);
				Stage newStage = new Stage();
				newStage.setScene(newScene);
				newStage.show();

				//Input the matrices value
				SolutionViewController controller = loader.getController();
				controller.setMatrix(matrixa);
				controller.displayInverse();

				this.mainView.clearError();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}catch(Exception e ) {
			this.printError(e.getMessage());
		}

	}

	/**
	 * Print error in the main view
	 * @param message the message to be displayed
	 */
	private void printError(String message) {

		this.mainView.showError(message);
	}


	/**
	 * Returns the main stage of the main view.
	 * @return the main stage
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}