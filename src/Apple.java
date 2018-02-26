import java.awt.Color;

/**
 * ICS3U - Summative Project
 * 
 * This class is the blueprint for an Apple Object, 
 * which represents the apple on the game board of 
 * the Snake Game.
 * 
 * @author Cindy Li
 * @since Friday, June 16, 2017
 *
 */
public class Apple {
	
	// static fields
	public static final Color APPLE_COLOR = Color.RED; // stores the Apple's color
			
	// instance fields
	private int position; // stores the Apple's position on the game board
	
	
	// constructor
	Apple(int pos) {
		
		position = pos;
	}
	
	
	/**
	 * This method returns the Apple's position.
	 * @return position
	 */
	public int getPositon() {
		return position;
	}
	
	
	/**
	 * This method sets the Apple's position to 
	 * the given integer.
	 * @param pos  The integer to set the position to
	 */
	public void setPostion(int pos) {
		position = pos;
	}
	
} // end class
