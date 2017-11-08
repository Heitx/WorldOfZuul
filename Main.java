
import BLL.Game;
import DAL.scoring.HighscoreManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	/*
		Phase 1 is complete!
			Date: 03/11/2017
	 */

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("UI/view/start_view.fxml"));

		primaryStage.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
		primaryStage.setTitle("Rick's Adventure - Group 24");
		primaryStage.setResizable(false);

		primaryStage.show();
	}

	/* runs the game */
	public static void main(String[] args) {
		new Game().start();



		Application.launch(args);

	}
}



