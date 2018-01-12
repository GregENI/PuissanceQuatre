package fr.eni.puissancequatre.bo;

import javafx.scene.paint.Color;

public class Player {

	private int id;
	private String name;
	private Color color;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Player(int id, String name, Color color) {
		super();
		setId(id);
		setName(name);
		setColor(color);
	}
}
