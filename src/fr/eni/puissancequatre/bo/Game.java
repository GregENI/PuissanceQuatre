package fr.eni.puissancequatre.bo;

public class Game {

	public static final int PLAYER_NONE = 0;

	private int[][] tokens;
	private int colCount;
	private int rowCount;

	/**
	 * Constructeur prenant en compte le nombre de lignes et de colonnes
	 * 
	 * @param row
	 * @param col
	 */
	public Game(int row, int col) {
		this.colCount = col;
		this.rowCount = row;
		this.tokens = new int[row][col];
		clearTokens();
	}

	/**
	 * Supprime tous les pions
	 */
	public void clearTokens() {
		for (int i = 0; i < this.rowCount; i++) {
			for (int j = 0; j < this.colCount; j++) {
				this.tokens[i][j] = PLAYER_NONE;
			}
		}
	}

	/**
	 * Retourne la prochaine libre ligne de la colonne passée en paramètre
	 * 
	 * @param col
	 * @return
	 */
	public int getNextRow(int col) {
		for (int i = (this.rowCount - 1); i >= 0; i--) {
			if (this.tokens[i][col] == PLAYER_NONE)
				return i;
		}
		return -1;
	}

	/**
	 * Ajoute un pion
	 * 
	 * @param player
	 * @param row
	 * @param col
	 */
	public void setToken(int player, int row, int col) {
		this.tokens[row][col] = player;
	}

	/**
	 * Vérifie si un joueur a gagné
	 * 
	 * @return
	 */
	public int getWinner() {

		int winner = PLAYER_NONE;

		if (isGameEnded())
			return -1;

		winner = getHorizontalWinner();
		if (winner != PLAYER_NONE)
			return winner;

		winner = getVerticalWinner();
		if (winner != PLAYER_NONE)
			return winner;

		if (getDiagonalWinner(1))
			return 1;
		if (getDiagonalWinner(2))
			return 2;

		return PLAYER_NONE;
	}

	/**
	 * Vérifie si le jeu est terminé
	 * @return
	 */
	public boolean isGameEnded() {
		for (int i = 0; i < this.rowCount; i++) {
			for (int j = 0; j < this.colCount; j++) {
				if (this.tokens[i][j] == PLAYER_NONE)
					return false;
			}
		}
		return true;
	}

	/**
	 * Vérification horizontale
	 * 
	 * @return
	 */
	public int getHorizontalWinner() {
		for (int i = 0; i < this.rowCount; i++) {
			for (int j = 0; j < (this.colCount - 3); j++) {
				int player = this.tokens[i][j];
				boolean samePlayer = true;
				for (int k = 1; k < 4; k++) {
					int token = this.tokens[i][j + k];
					if (token != player) {
						samePlayer = false;
						break;
					}
				}
				if ((samePlayer) && (player != PLAYER_NONE)) {
					return player;
				}
			}
		}
		return PLAYER_NONE;
	}

	/**
	 * Vérification verticale
	 * 
	 * @return
	 */
	public int getVerticalWinner() {
		for (int i = 0; i < (this.rowCount - 3); i++) {
			for (int j = 0; j < this.colCount; j++) {
				int player = this.tokens[i][j];
				boolean samePlayer = true;
				for (int k = 1; k < 4; k++) {
					int token = this.tokens[i + k][j];
					if (token != player) {
						samePlayer = false;
						break;
					}
				}
				if ((samePlayer) && (player != PLAYER_NONE)) {
					return player;
				}
			}
		}
		return PLAYER_NONE;
	}

	/**
	 * Vérification en diagonale
	 * 
	 * @return
	 */
	public boolean getDiagonalWinner(int player) {

		for (int i = 3; i < this.colCount; i++) {
			for (int j = 0; j < this.rowCount - 3; j++) {
				if (this.tokens[i][j] == player && this.tokens[i - 1][j + 1] == player
						&& this.tokens[i - 2][j + 2] == player && this.tokens[i - 3][j + 3] == player)
					return true;
			}
		}
		
		for (int i = 3; i < this.colCount; i++) {
			for (int j = 3; j < this.rowCount; j++) {
				if (this.tokens[i][j] == player && this.tokens[i - 1][j - 1] == player
						&& this.tokens[i - 2][j - 2] == player && this.tokens[i - 3][j - 3] == player)
					return true;
			}
		}

		return false;
	}
}
