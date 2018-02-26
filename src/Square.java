import java.awt.Color;

import javax.swing.JButton;

/**
 * ICS3U - Summative Project
 * 
 * This class is the blueprint for a Square Object, 
 * which represents one square on the game board of 
 * the Snake Game.
 * 
 * @author Cindy Li
 * @since Friday, June 16, 2017
 *
 */
@SuppressWarnings("serial")
public class Square extends JButton {
	
	// static fields
	public static final Color EMPTY_SQUARE_COLOR = Color.WHITE; // stores the color of an "empty" square
	
	// instance fields
	private String occupier; // stores the Square's occupier (either "NONE", "SNAKE", or "APPLE")
	private int position; // stores the Square's position on the game board
	
	
	// constructor
	public Square(int pos) {
		
		// disable the button
		setEnabled(false);
		
		// set up occupier and position
		occupier = "NONE";
		position = pos;
		
		// set color
		setBackground(EMPTY_SQUARE_COLOR);
		
		// set to no border
		setBorderPainted(false);
	}
	
	
	/**
	 * This method returns the Square's occupier.
	 * @return occupier
	 */
	public String getOccupier() {
		return occupier;
	}
	
	
	/**
	 * This method sets the Square's occupier 
	 * field to the given String.
	 * @param o  The String to set the occupier to
	 */
	public void setOccupier(String o) {
		occupier = o;
	}
	
	
	/**
	 * This method returns the Square's position.
	 * @return position
	 */
	public int getPosition() {
		return position;
	}
	
} // end class
