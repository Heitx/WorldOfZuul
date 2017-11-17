package UI.controller;

import BLL.Domain;
import BLL.character.player.Backpack;
import BLL.character.player.Player;
import UI.SearchTask;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
	private Domain domain;

	@FXML private Label labelFuel;
	@FXML private Label labelBackpack;
	@FXML private ProgressBar barFuel;
	@FXML private ProgressBar barBackpack;
	@FXML private ProgressBar barSearch;
	@FXML private Button buttonSearch;
	@FXML private Button buttonBackpack;
	@FXML private Button buttonInformation;

	private Player player;
	private Task task;

	public GameController(Domain domain) {
		this.domain = domain;
		this.player = domain.getPlayer();
		this.task = null;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		updateFuelProgressBar();
		updateBackpackProgressBar();
	}

	@FXML
	void handleSearchAction(ActionEvent event) {
		if(task == null || !task.isRunning()) {
			this.task = new SearchTask(false);

			barSearch.setPrefWidth(buttonSearch.getWidth() * 8/10);
			barSearch.setVisible(true);
			buttonSearch.setText("");

			// player.getCurrentPlanet().getPermSearched()
			Thread th = new Thread(task);

			barSearch.progressProperty().bind(task.progressProperty());
			task.setOnSucceeded(event1 -> {
				buttonSearch.setText("Search");
				barSearch.setVisible(false);
				barSearch.setPrefWidth(0);

				// Place action here!
			});

			th.start();
		}
	}

	@FXML
	void handleBackpackAction(ActionEvent event) {

	}

	@FXML
	void handleInformationAction(ActionEvent event) {

	}

	private void updateFuelProgressBar() {
		barFuel.progressProperty().setValue(player.getFuel() / player.getMaxFuel());
		labelFuel.setText(String.format("[%.0f / %d]", player.getFuel(), player.getMaxFuel()));
	}

	private void updateBackpackProgressBar() {
		Backpack bp = player.getBackpack();
		barBackpack.progressProperty().setValue(bp.getCurrentCapacity() / bp.getMaxCapacity());
		labelBackpack.setText(String.format("[%.1f / %.1f]", bp.getCurrentCapacity(), bp.getMaxCapacity()));
	}
}
