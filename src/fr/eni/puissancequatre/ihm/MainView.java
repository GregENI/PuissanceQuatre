package fr.eni.puissancequatre.ihm;

import javax.swing.JOptionPane;

import fr.eni.puissancequatre.bo.Game;
import fr.eni.puissancequatre.bo.Player;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainView extends Application {

	private static final double SCENE_WIDTH = 800;
	private static final double SCENE_HEIGHT = 800;
	private static final int GRID_ROWS = 10;
	private static final int GRID_COLS = 10;

	private Grid grid;
	private Game game;
	private Player firstPlayer;
	private Player secondPlayer;
	private Player currentPlayer;

	public static void main(String[] args) {
		Application.launch(MainView.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Initialisation de la partie

		this.game = new Game(GRID_ROWS, GRID_COLS);

		// Initialisation des joueurs

		firstPlayer = new Player(1, "Joueur 1", Color.YELLOW);
		secondPlayer = new Player(2, "Joueur 2", Color.RED);
		currentPlayer = firstPlayer;

		// Initialisation de la grille

		primaryStage.setTitle("Puissance 4");
		grid = new Grid(SCENE_WIDTH, SCENE_HEIGHT, GRID_ROWS, GRID_COLS);

		Scene scene = new Scene(grid, SCENE_WIDTH, SCENE_HEIGHT);
		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {

			/**
			 * Affichage d'un apercu de la position du pion lors du déplacement de la souris
			 */
			@Override
			public void handle(MouseEvent event) {

				int colIndex = grid.getColumnIndex(event.getSceneX());
				double colPos = grid.getColumnCenter(colIndex);
				double rowPos = grid.getRowCenter(0);
				grid.showPreviewToken(currentPlayer.getColor(), colPos, rowPos);
			}
		});
		scene.setOnMouseClicked(new EventHandler<MouseEvent>() {

			/**
			 * Insertion du pion lors d'un click sur la grille
			 */
			@Override
			public void handle(MouseEvent event) {

				int colIndex = grid.getColumnIndex(event.getSceneX());
				int rowIndex = game.getNextRow(colIndex);
				if (rowIndex > -1) {
					System.out.println(rowIndex);
					grid.playToken(currentPlayer.getColor(), rowIndex, colIndex);
					game.setToken(currentPlayer.getId(), rowIndex, colIndex);
					int winner = game.getWinner();
					if (winner != Game.PLAYER_NONE) {
						if (winner == -1) {
							showMessage("Egalité !");
						} else {
							showMessage("Le joueur " + winner + " a gagné !");
						}
						game.clearTokens();
						grid.clear();
					} else {
						switchPlayer();
					}

				} else {
					showError("Impossible de jouer ici !");
				}
			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void switchPlayer() {
		if (this.currentPlayer == this.firstPlayer) {
			this.currentPlayer = this.secondPlayer;
		} else {
			this.currentPlayer = this.firstPlayer;
		}
		this.grid.showPreviewToken(this.currentPlayer.getColor());
	}

	/**
	 * Affiche une erreur
	 * 
	 * @param erreur
	 */
	private void showError(String erreur) {
		Alert alert = new Alert(AlertType.WARNING, erreur);
		alert.showAndWait();
	}

	/**
	 * Affiche un message
	 * 
	 * @param erreur
	 */
	private void showMessage(String message) {
		Alert alert = new Alert(AlertType.INFORMATION, message);
		alert.showAndWait();
	}

}
