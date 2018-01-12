package fr.eni.puissancequatre.ihm;

import javafx.animation.TranslateTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class Grid extends Group {

	private static final int LINE_SIZE = 5;
	private static final Color LINE_COLOR = Color.BLUE;

	private double width;
	private double height;
	private int rowCount;
	private int colCount;
	private double tokenWidth;
	private double tokenHeight;
	private Token previewToken;
	private boolean previewTokenVisible;

	/**
	 * Constructeur prenant en compte la largeur, la taille, le nombre de lignes et de colonnes
	 * @param width
	 * @param height
	 * @param rows
	 * @param cols
	 */
	public Grid(double width, double height, int rows, int cols) {
		this.width = width;
		this.height = height;
		this.rowCount = rows;
		this.colCount = cols;

		this.previewTokenVisible = false;
		this.previewToken = new Token();

		initGrid();
	}

	/**
	 * Initialise la grille
	 */
	private void initGrid() {
		this.tokenWidth = (this.width - ((this.colCount + 1) * LINE_SIZE)) / this.colCount;
		this.tokenHeight = (this.height - ((this.rowCount + 1) * LINE_SIZE)) / this.rowCount;

		this.previewToken.setSize(this.tokenWidth);

		double lineX = 0;
		for (double i = 0; i <= this.rowCount; i++) {
			Rectangle rect = new Rectangle(lineX, 0, LINE_SIZE, this.height);
			rect.setFill(LINE_COLOR);
			super.getChildren().add(rect);
			lineX += (tokenWidth + LINE_SIZE);
		}

		double lineY = 0;
		for (double i = 0; i <= this.rowCount; i++) {
			Rectangle rect = new Rectangle(0, lineY, this.width, LINE_SIZE);
			rect.setFill(LINE_COLOR);
			super.getChildren().add(rect);
			lineY += (tokenHeight + LINE_SIZE);
		}
	}

	/**
	 * Retourne l'index d'une colonne en fonction d'une position en X
	 * @param x
	 * @return
	 */
	public int getColumnIndex(double x) {
		double index = x / (this.tokenWidth + LINE_SIZE);
		return (int) Math.floor(index);
	}

	/**
	 * Retourne le centre de la colonne en fonction de son index
	 * @param index
	 * @return
	 */
	public double getColumnCenter(int index) {
		return (index * (this.tokenWidth + LINE_SIZE)) + LINE_SIZE + (this.tokenWidth / 2);
	}

	/**
	 * Retourne le centre d'une ligne en fonction de son index
	 * @param index
	 * @return
	 */
	public double getRowCenter(int index) {
		return (index * (this.tokenHeight + LINE_SIZE)) + LINE_SIZE + (this.tokenHeight / 2);
	}

	/**
	 * Affiche l'apercu du pion avant de l'insérer
	 * @param color
	 * @param x
	 * @param y
	 */
	public void showPreviewToken(Color color, double x, double y) {

		showPreviewToken(color);

		this.previewToken.setLayoutX(x);
		this.previewToken.setLayoutY(y);

		if (!this.previewTokenVisible) {
			super.getChildren().add(this.previewToken);
			this.previewTokenVisible = true;
		}
	}

	/**
	 * Change la couleur du pion d'apercu
	 * @param color
	 */
	public void showPreviewToken(Color color) {
		Color alphaColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0.3);
		this.previewToken.setColor(alphaColor);
	}

	/**
	 * Insère le pion dans la grille
	 * @param color
	 * @param row
	 * @param col
	 */
	public void playToken(Color color, int row, int col) {

		double colPos = getColumnCenter(col);
		double rowPos = getRowCenter(row);

		Token token = new Token(color, this.tokenWidth);
		token.setLayoutX(colPos);
		token.setLayoutY(getRowCenter(0) - (this.tokenHeight / 2) - LINE_SIZE);
		super.getChildren().add(token);

		TranslateTransition trans = new TranslateTransition(Duration.millis(500), token);
		trans.setByY(rowPos);
		trans.play();
	}

	/**
	 * Supprime tous les pions
	 */
	public void clear() {
		int index = 0;
		while (index < super.getChildren().size()) {
			Node node = super.getChildren().get(index);
			if ((node instanceof Token) && (!node.equals(this.previewToken))) {
				super.getChildren().remove(index);
			} else {
				index++;
			}
		}
	}
}
