/**
 * ICS3U - Summative Project
 * 
 * This class is the blueprint for a SnakeGame. It 
 * contains all the necessary game mechanics and 
 * algorithms to the Snake Game.
 * 
 * @author Cindy Li
 * @since Friday, June 16, 2017
 *
 */
public class SnakeGame {
	
	// instance fields
	private Square[] gameBoard; // stores all the squares on the game board
	private Snake snake; // stores the Snake Object of the game
	private Apple apple; // stores the Apple Object of the game
	private String direction; // stores the direction command for the Snake
	private final int WIDTH;
	private final int LENGTH;
	
	
	// constructor
	SnakeGame(int width, int length) {
		
		WIDTH = width;
		LENGTH = length;
		
		gameBoard = new Square[WIDTH * LENGTH]; // creates an array of (width * length) Squares
												// the game board will be (width) rows x (length) columns
		
		// fill each element of gameBoard with a Square Object
		for (int i = 0; i < gameBoard.length; i++) {
			
			gameBoard[i] = new Square(i);
		}
		
		
		// pick a random square in gameBoard and set it to the Snake's initial starting position
		int randomPos = (int) (Math.random() * (gameBoard.length));
		
		gameBoard[randomPos].setOccupier("SNAKE");
		gameBoard[randomPos].setBackground(Snake.SNAKE_COLOR);
		snake = new Snake(gameBoard[randomPos]);
		
		
		// pick a random square in gameBoard and set it to the Apple's initial starting position
		// make sure it is not the Snake's initial starting position
		while (gameBoard[randomPos].getOccupier().equals("SNAKE")) {
			
			randomPos = (int) (Math.random() * (gameBoard.length));
		}
		
		gameBoard[randomPos].setOccupier("APPLE");
		gameBoard[randomPos].setBackground(Apple.APPLE_COLOR);
		apple = new Apple(randomPos);
		
		
		// set the direction to default
		direction = "STAY";
	}
	
	
	/**
	 * This method changes the Snake's position depending 
	 * on the direction command. Also checks if the Snake 
	 * ate the Apple.
	 */
	public void moveSnake() {
		
		/* Firstly, add a Square to the front to be the new head, 
		 * depending on the direction command.
		 * 
		 * The canMoveSnake() method should catch all illegal moves, 
		 * so we won't need to worry about that.
		 */
		
		int headPos = snake.getSnakeSquares().get(0).getPosition(); // get the position of the current head Square
		
		switch (direction) {
			
			case ("UP"):
				
				snake.getSnakeSquares().add(0, gameBoard[headPos - LENGTH]);
				break;
			
			case ("DOWN"):
				snake.getSnakeSquares().add(0, gameBoard[headPos + LENGTH]);
				break;
				
			case ("LEFT"):
				snake.getSnakeSquares().add(0, gameBoard[headPos - 1]);
				break;
				
			case ("RIGHT"):
				snake.getSnakeSquares().add(0, gameBoard[headPos + 1]);
				break;
		}
		
		// check to see if the Snake ate the Apple (if head Square's occupier is "APPLE")
		if (snake.getSnakeSquares().get(0).getOccupier().equals("APPLE")) {
			
			// pick a random Square in gameBoard and set it to the Apple's position
			// make sure it is not occupied by the Snake
			int randomPos = (int) (Math.random() * (gameBoard.length));
			
			while (gameBoard[randomPos].getOccupier().equals("SNAKE") || gameBoard[randomPos] == snake.getSnakeSquares().get(0)) {
								
				randomPos = (int) (Math.random() * (gameBoard.length));
			}
			
			gameBoard[randomPos].setOccupier("APPLE");
			gameBoard[randomPos].setBackground(Apple.APPLE_COLOR);
			apple.setPostion(randomPos);
		}
		
		// else the Snake didn't eat the apple
		else {
			
			// get the Snake's tail and set its occupier to "NONE" and set its color to default
			snake.getSnakeSquares().get(snake.getSnakeSquares().size() - 1).setOccupier("NONE");
			snake.getSnakeSquares().get(snake.getSnakeSquares().size() - 1).setBackground(Square.EMPTY_SQUARE_COLOR);
			
			// remove the Snake's tail
			snake.getSnakeSquares().remove(snake.getSnakeSquares().size() - 1);
		}
		
		// set the color and the occupier of the Snake's head
		snake.getSnakeSquares().get(0).setBackground(Snake.SNAKE_COLOR);
		snake.getSnakeSquares().get(0).setOccupier("SNAKE");
	}
	
	
	/**
	 * This method checks if the snake can move given 
	 * the direction.
	 * @return true if the snake can move in that 
	 * direction, false otherwise
	 */
	public boolean canMoveSnake() {
		
		/* Firstly, check if the snake will go out of bounds.
		 * Then, check if the snake will hit itself.
		 */
		
		switch (direction) {
			 
			case ("UP"):
				
				// if the Snake's head's position is in the top row of the 
				// game board, the snake will go out of bounds
				if (snake.getSnakeSquares().get(0).getPosition() < LENGTH)
					return false;
			
				// if the Square just above the Snake's head is also part 
				// of the Snake, it will hit itself
				else if (gameBoard[snake.getSnakeSquares().get(0).getPosition() - LENGTH].getOccupier().equals("SNAKE"))
					return false;
					
				break;
			
			case ("DOWN"):
				
				// if the Snake's head's position is in the bottom row of the 
				// game board, the snake will go out of bounds
				if (snake.getSnakeSquares().get(0).getPosition() > (gameBoard.length - 1 - LENGTH))
					return false;
			
				// if the Square just below the Snake's head is also part 
				// of the Snake, it will hit itself
				else if (gameBoard[snake.getSnakeSquares().get(0).getPosition() + LENGTH].getOccupier().equals("SNAKE"))
					return false;
				
				break;	
				
			case ("LEFT"):
				
				// if the Snake's head's position is in the leftmost column of 
				// the game board, the snake will go out of bounds
				if (snake.getSnakeSquares().get(0).getPosition() % LENGTH == 0)
					return false;
			
				// if the Square just to the left of the Snake's head is also  
				// part of the Snake, it will hit itself
				else if (gameBoard[snake.getSnakeSquares().get(0).getPosition() - 1].getOccupier().equals("SNAKE"))
					return false;
				
				break;
			
			case ("RIGHT"):
				
				// if the Snake's head's position is in the rightmost column of 
				// the game board, the snake will go out of bounds
				if ((snake.getSnakeSquares().get(0).getPosition() + 1) % LENGTH == 0)
					return false;
					
				// if the Square just to the right of the Snake's head is also  
				// part of the Snake, it will hit itself
				else if (gameBoard[snake.getSnakeSquares().get(0).getPosition() + 1].getOccupier().equals("SNAKE"))
					return false;
				
				break;
		}
		
		// if the program gets to this point, then the snake can move in the given direction
		return true;
	}
	
	
	/**
	 * This method returns the array of Squares 
	 * (the game board).
	 * @return gameBoard
	 */
	public Square[] getGameBoard() {
		return gameBoard;
	}
	
	
	/**
	 * This method returns the Snake Object.
	 * @return snake
	 */
	public Snake getSnake() {
		return snake;
	}
	
	
	/**
	 * This method returns the Apple Object.
	 * @return snake
	 */
	public Apple getApple() {
		return apple;
	}
	
	
	/**
	 * This method returns the current direction
	 * command.
	 * @return direction
	 */
	public String getDirection() {
		return direction;
	}
	
	
	/**
	 * This method sets the direction command to
	 * the given String.
	 * @param d  The String to set the direction to
	 */
	public void setDirection(String d) {
		direction = d;
	}
	
} // end class
