import java.awt.Color;
import java.util.ArrayList;

/**
 * ICS3U - Summative Project
 * 
 * This class is the blueprint for a Snake Object, 
 * which represents the user-controllable snake in 
 * the Snake Game.
 * 
 * @author Cindy Li
 * @since Friday, June 16, 2017
 *
 */
public class Snake {
	
	// static fields
	public static final Color SNAKE_COLOR = Color.GREEN; // stores the Snake's color
		
	// instance fields
	private ArrayList<Square> snakeSquares; // stores the Squares that are a part of the Snake
	
	
	// constructor
	Snake(Square head) {
		
		snakeSquares = new ArrayList<Square>();
		snakeSquares.add(head);
	}
	
	
	/**
	 * This method returns the ArrayList of 
	 * the Snake's Squares.
	 * @return
	 */
	public ArrayList<Square> getSnakeSquares() {
		return snakeSquares;
	}
	
} // end class
