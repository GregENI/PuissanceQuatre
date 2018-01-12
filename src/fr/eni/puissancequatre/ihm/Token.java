package fr.eni.puissancequatre.ihm;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Token extends Group {

	private Circle circle;
	
	public void setColor(Color color)
	{
		this.circle.setFill(color);
	}
	
	public void setSize(double size)
	{
		this.circle.setRadius(size / 2);
	}
	
	public Token()
	{
		this(Color.BLACK, 10);
	}
	
	public Token(Color color, double size)
	{
		this.circle = new Circle();
		super.getChildren().add(this.circle);
		
		setColor(color);
		setSize(size);
	}
}
