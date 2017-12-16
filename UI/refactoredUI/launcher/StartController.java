package UI.refactoredUI.launcher;

import BLL.ACQ.Domain;
import BLL.ACQ.IScore;
import UI.UIScore;
import UI.refactoredUI.game.GameController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The start controller for the game launcher
 */
public class StartController extends Controller {

	@FXML private Button buttonNewGame;
	@FXML private Button buttonExit;
	@FXML private Button buttonHighscore;
	@FXML private TableView<UIScore> tableviewHighscore;
	@FXML private TableColumn<UIScore, String> tablecolumnIndex;
	@FXML private AnchorPane aboutWrapper;
	@FXML private Button exitButton__about;
	@FXML private Button button__about;
	@FXML private Button buttonContinueGame;


	/**
	 * Constructor.
	 * @param domain reference to domain logic.
	 */
	public StartController(Domain domain) {
		super(domain);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize() {
		configListView();
		getStage().getIcons().add(new Image("./UI/resources/img/favicon.png"));

		buttonContinueGame.setVisible(getDomain().hasLoadingFile());
	}

	/**
	 * Method to handle continuation of game.
	 * @param event the event fired by the button.
	 */
	@FXML
	void continueGame(ActionEvent event) {
		try {
			getDomain().load();
			switchToGameView();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to handle the start of a new game.
	 * @param event	hooked by the {@link FXMLLoader}
	 */
	@FXML
	void handleNewGameAction(ActionEvent event) {
		try {
			getDomain().init();
			switchToGameView();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Event based method to invoke toggling method of the about screen's visibility.
	 * @param event	hooked by the {@link FXMLLoader}
	 */
	@FXML void displayAboutScreen(ActionEvent event) {
		toggleAboutScreenVisibility();
	}

	/**
	 * Event based method to invoke toggling method of the about screen's visibility.
	 * @param event	hooked by the {@link FXMLLoader}
	 */
	@FXML void closeAboutScreen(ActionEvent event) {
		toggleAboutScreenVisibility();
	}

	/**
	 * Method to toggle the about screen visibility.
	 */
	void toggleAboutScreenVisibility(){aboutWrapper.setVisible(!aboutWrapper.isVisible());}


	/**
	 * Event based to close the application.
	 * @param event hooked by the {@link FXMLLoader}
	 */
	@FXML void handleExitAction(ActionEvent event) {
		Platform.exit();
	}


	/**
	 * Method that uses the {@link FXMLLoader} to process the loading process of a new scene.
	 * The scene is switched by loading and injecting the necessary FXML and controller for actually playing the game.
	 * @throws IOException
	 */
	private void switchToGameView() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/UI/refactoredUI/game/game_view.fxml"));
		GameController controller = new GameController(getDomain(), getStage());
		//FXMLLoader loader = new FXMLLoader(getClass().getResource("view/start_view.fxml"));
		//StartController controller = new StartController(domain);
		//controller.setStage(primaryStage);
		loader.setController(controller);
		AnchorPane pane = loader.load();
		getStage().setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
		getStage().setTitle("Rick's Adventure The Game// Gruppe 24");
		getStage().setResizable(true);
		getStage().show();

		getStage().setOnCloseRequest(event -> {
			Platform.exit();
			System.exit(0);
		});
	}


	private void configListView() {
		List<IScore> highscore = getDomain().getHighscore();
		List<UIScore> scores = new ArrayList<>();

		IScore is;
		for(int i = 0; i < highscore.size(); i++) {
			is = highscore.get(i);
			scores.add(new UIScore(is.getName(), is.getScore(), i + 1));
		}

		aboutWrapper.setVisible(false);

		tableviewHighscore.setSelectionModel(null);
		tableviewHighscore.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("place"));
		tableviewHighscore.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
		tableviewHighscore.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("score"));
		tableviewHighscore.setItems(FXCollections.observableArrayList(scores));
	}
}