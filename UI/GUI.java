package UI;

import BLL.ACQ.Domain;
import BLL.ACQ.UserInterface;
import UI.components.launcher.LauncerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The facade of the User Interface Layer (UI).
 */
public class GUI extends Application implements UserInterface {

	/** Reference to the business layer object implementing {@link Domain} interface */
	private static Domain domain;

	/**
	 * Loads the initial scene and controller and shows the window.
	 * {@inheritDoc}
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("components/launcher/launcher_view.fxml"));
		LauncerController controller = new LauncerController(domain, primaryStage);
		loader.setController(controller);
		AnchorPane pane = loader.load();
		primaryStage.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
		primaryStage.setTitle("Rick's Adventure The Game// Gruppe 24");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void injectDomain(Domain domain) {
		GUI.domain = domain;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void startApplication() {
		launch(getClass());
	}
}
