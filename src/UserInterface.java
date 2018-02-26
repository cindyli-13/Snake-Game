import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * ICS3U - Summative Project
 * 
 * This class is the interactive GUI 
 * for the Snake Game.
 * 
 * @author Cindy Li
 * @since Friday, June 16, 2017
 *
 */
@SuppressWarnings("serial")
public class UserInterface extends JFrame implements ActionListener, KeyListener {
	
	
	// instance fields
	private final String DATA_TEXT_FILE; // text file to store the high scores
	private SnakeGame game;
	private ArrayList<String[]> highScores;
	private int instructionsPage; // to keep track of the instructions screen
	private Timer timer; // for the run method and executions of the game
	private boolean gameOn; // set to true when the game is running, false otherwise
	private final int BOARD_WIDTH;
	private final int BOARD_LENGTH;
	
	
	// swing components
	private JPanel startMenuPanel, gamePanel, gameBoardPanel, instructionsPanel, leaderboardPanel, gameOverPanel, 
				   invalidInputPanel;
	
	private JLabel titleLabel, background, instructionsLabel, diagram, arrowsImage, diagram1A, diagram1B, 
				   diagram2A, diagram2B, leaderboardLabel, rankLabel, nicknameLabel, scoreLabel, lengthLabel, 
				   highScoreLabel, pausedLabel, gameOverLabel, nicknamePromptLabel, invalidInputLabel;
	
	private JTextArea instructionsText, ranksDisplay, nicknamesDisplay, scoresDisplay, textBox;
	
	private JButton playGameButton, instructionsButton, highScoresButton, homeButton, nextButton, previousButton,
					acceptButton, cancelButton, okButton;
	
	
	// main method
	public static void main(String[] args) {
		
		new UserInterface();
	}
	
	
	// constructor
	public UserInterface() {
		
		super("Snake Game");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700,550);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		// instantiate instance variables
		DATA_TEXT_FILE = "data//snake_game_scores.txt";
		
		BOARD_WIDTH = 18;
		BOARD_LENGTH = 30;
		game = new SnakeGame(BOARD_WIDTH, BOARD_LENGTH);
		
		instructionsPage = 0;
		highScores = new ArrayList<String[]>();
		timer = new Timer(90, this); // sets the speed of the game
		
		// get the data from the text file and store it in the highScores ArrayList
		getScores();
		
		
		// set up start menu screen
		startMenuPanel = new JPanel();
		startMenuPanel.setLayout(null);
		startMenuPanel.setBackground(new Color(225, 225, 225));
		startMenuPanel.setBounds(0, 0, 700, 550);
		
		// add background image to start menu screen
		ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/background.png"));
		Image backgroundImage = backgroundIcon.getImage().getScaledInstance(700, 550,  java.awt.Image.SCALE_SMOOTH);
		backgroundIcon = new ImageIcon(backgroundImage);
		
		background = new JLabel(backgroundIcon);
		background.setBounds(0, 0, 700, 550);
		
		startMenuPanel.add(background);
		
		
		// set up title
		titleLabel = new JLabel("SNAKE");
		titleLabel.setBounds(0, 150, 700, 100);
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setFont(new Font("Stencil", Font.BOLD, 100));
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		
		startMenuPanel.add(titleLabel);
		
		
		// set up play game button
		playGameButton = new JButton("Play Game");
		playGameButton.setBounds(265, 310, 170, 35);
		playGameButton.setBackground(Color.WHITE);
		playGameButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		playGameButton.setFocusPainted(false);
		playGameButton.addActionListener(this);
		
		startMenuPanel.add(playGameButton);
		
		
		// set up instructions button
		instructionsButton = new JButton("Instructions");
		instructionsButton.setBounds(265, 355, 170, 35);
		instructionsButton.setBackground(Color.WHITE);
		instructionsButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		instructionsButton.setFocusPainted(false);
		instructionsButton.addActionListener(this);
		
		startMenuPanel.add(instructionsButton);
		
		
		// set up high scores button
		highScoresButton = new JButton("High Scores");
		highScoresButton.setBounds(265, 400, 170, 35);
		highScoresButton.setBackground(Color.WHITE);
		highScoresButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		highScoresButton.setFocusPainted(false);
		highScoresButton.addActionListener(this);
		
		startMenuPanel.add(highScoresButton);
		
		
		// set the order of the background image to the last component
		startMenuPanel.setComponentZOrder(background, 4);
		
		
		// set up game screen
		gamePanel = new JPanel();
		gamePanel.setLayout(null);
		gamePanel.setBackground(new Color(140, 200, 80));
		gamePanel.setBounds(0, 0, 700, 550);
		
		
		// set up game board
		gameBoardPanel = new JPanel();
		gameBoardPanel.setBounds(50, 80, 600, 360);
		gameBoardPanel.setLayout(new GridLayout(BOARD_WIDTH, BOARD_LENGTH));
		gameBoardPanel.setBackground(Color.WHITE);
		
		// add the Squares to the game board
		for (int i = 0; i < (BOARD_WIDTH * BOARD_LENGTH); i++) {
			
			gameBoardPanel.add(game.getGameBoard()[i]);
		}
		
		gamePanel.add(gameBoardPanel);
		
		
		// set up length indicator
		lengthLabel = new JLabel();
		lengthLabel.setBounds(50, 455, 175, 35);
		lengthLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		lengthLabel.setBackground(Color.WHITE);
		lengthLabel.setForeground(Color.DARK_GRAY);
		lengthLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
		lengthLabel.setOpaque(true);
		
		gamePanel.add(lengthLabel);
		
		
		// set up high score indicator
		highScoreLabel = new JLabel();
		highScoreLabel.setBounds(475, 455, 175, 35);
		highScoreLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		highScoreLabel.setBackground(Color.WHITE);
		highScoreLabel.setForeground(Color.DARK_GRAY);
		highScoreLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
		highScoreLabel.setOpaque(true);
			
		gamePanel.add(highScoreLabel);
		
		
		// set up paused label
		pausedLabel = new JLabel("PAUSED");
		pausedLabel.setBackground(new Color(255, 0, 0, 100));
		pausedLabel.setForeground(Color.DARK_GRAY);
		pausedLabel.setFont(new Font("Rockwell", Font.BOLD, 36));
		pausedLabel.setHorizontalAlignment(JLabel.CENTER);
		pausedLabel.setVerticalAlignment(JLabel.CENTER);
		pausedLabel.setOpaque(true);
		
		gamePanel.add(pausedLabel);
		
		
		// set up game over panel
		gameOverPanel = new JPanel();
		gameOverPanel.setLayout(null);
		gameOverPanel.setBackground(new Color(240, 225, 100));
		gameOverPanel.setBounds(140, 150, 420, 220);
		gameOverPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		
		
		// set up game over label
		gameOverLabel = new JLabel("Game Over");
		gameOverLabel.setBounds(30, 20, 460, 35);
		gameOverLabel.setForeground(Color.BLACK);
		gameOverLabel.setFont(new Font("Rockwell", Font.PLAIN,32));
			
		gameOverPanel.add(gameOverLabel);
		
		
		// set up nickname prompt label
		nicknamePromptLabel = new JLabel("Please enter your nickname:");
		nicknamePromptLabel.setBounds(30, 75, 460, 30);
		nicknamePromptLabel.setForeground(Color.DARK_GRAY);
		nicknamePromptLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
		
		gameOverPanel.add(nicknamePromptLabel);
		
		
		// set up text box
		textBox = new JTextArea();
		textBox.setBounds(30, 120, 360, 30);
		textBox.setFont(new Font("Rockwell", Font.PLAIN, 18));
		textBox.setBackground(Color.WHITE);
		textBox.setForeground(Color.DARK_GRAY);
		textBox.setBorder(BorderFactory.createEtchedBorder());
		
		gameOverPanel.add(textBox);
		
		
		// set accept button
		acceptButton = new JButton("Accept");
		acceptButton.setBounds(30, 165, 120, 30);
		acceptButton.setBackground(Color.WHITE);
		acceptButton.setFont(new Font("Rockwell", Font.PLAIN, 18));
		acceptButton.setFocusPainted(false);
		acceptButton.addActionListener(this);
		
		gameOverPanel.add(acceptButton);
		
		
		// set cancel button
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(270, 165, 120, 30);
		cancelButton.setBackground(Color.WHITE);
		cancelButton.setFont(new Font("Rockwell", Font.PLAIN, 18));
		cancelButton.setFocusPainted(false);
		cancelButton.addActionListener(this);
				
		gameOverPanel.add(cancelButton);
		
		
		// add game over panel to game panel
		gamePanel.add(gameOverPanel);
		
		
		// set up invalid input panel
		invalidInputPanel = new JPanel();
		invalidInputPanel.setLayout(null);
		invalidInputPanel.setBackground(new Color(240, 225, 100));
		invalidInputPanel.setBounds(250, 190, 200, 120);
		invalidInputPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		
		
		// set up invalid input label
		invalidInputLabel = new JLabel("Invalid Input");
		invalidInputLabel.setBounds(30, 20, 160, 35);
		invalidInputLabel.setForeground(Color.BLACK);
		invalidInputLabel.setFont(new Font("Rockwell", Font.PLAIN, 24));
		
		invalidInputPanel.add(invalidInputLabel);
		
		
		// set up ok button
		okButton = new JButton("OK");
		okButton.setBounds(65, 70, 70, 30);
		okButton.setBackground(Color.WHITE);
		okButton.setFont(new Font("Rockwell", Font.PLAIN, 18));
		okButton.setFocusPainted(false);
		okButton.addActionListener(this);
		
		invalidInputPanel.add(okButton);
		
		
		// add invalid input panel to game panel
		gamePanel.add(invalidInputPanel);
		
		
		// set up instructions screen
		instructionsPanel = new JPanel();
		instructionsPanel.setLayout(null);
		instructionsPanel.setBackground(new Color(140, 200, 80));
		instructionsPanel.setBounds(0, 0, 700, 550);
		
		
		// set up instructions label
		instructionsLabel = new JLabel();
		instructionsLabel.setBounds(50, 30, 600, 50);
		instructionsLabel.setForeground(Color.BLACK);
		instructionsLabel.setFont(new Font("Rockwell", Font.PLAIN, 32));
		
		instructionsPanel.add(instructionsLabel);
		
		
		// set up diagram	
		diagram = new JLabel();
		diagram.setBounds(50, 110, 280, 160);
		diagram.setLayout(new GridLayout(8, 14));
		diagram.setBackground(Color.WHITE);
		
		// make diagram look like a section of the game board
		for (int i = 0; i < 112; i++) {
				
			// create dummy button
			JButton dummy = new JButton();
			dummy.setEnabled(false);
			dummy.setBorderPainted(false);
			
			// set the color
			if (i == 33 || i == 34 || i == 35 || i == 36 || i == 37 || i == 38 || i == 47 
						|| i == 61 || i == 73 || i == 74 || i == 75)
				dummy.setBackground(Snake.SNAKE_COLOR);
			else
				dummy.setBackground(Square.EMPTY_SQUARE_COLOR);
					
			diagram.add(dummy);
		}
						
		instructionsPanel.add(diagram);
		
		
		// set up arrows image
		ImageIcon arrowKeysIcon = new ImageIcon(getClass().getResource("/arrow_keys.png"));
		Image arrowKeysImage = arrowKeysIcon.getImage().getScaledInstance(190, 140,  java.awt.Image.SCALE_SMOOTH);
		arrowKeysIcon = new ImageIcon(arrowKeysImage);
		
		arrowsImage = new JLabel(arrowKeysIcon);
		arrowsImage.setBounds(370, 110, 280, 160);
		
		instructionsPanel.add(arrowsImage);
			
		
		// set up instructions diagram 1a
		diagram1A = new JLabel();
		diagram1A.setBounds(50, 110, 280, 160);
		diagram1A.setLayout(new GridLayout(8, 14));
		diagram1A.setBackground(Color.WHITE);
		
		// make diagram1A look like a section of the game board
		for (int i = 0; i < 112; i++) {
			
			// create dummy button
			JButton dummy = new JButton();
			dummy.setEnabled(false);
			dummy.setBorderPainted(false);
			
			// set the color
			if (i == 45 || i == 46 || i == 47 || i == 48 || i == 49 || i == 59 || i == 73)
				dummy.setBackground(Snake.SNAKE_COLOR);
			else if (i == 51)
				dummy.setBackground(Apple.APPLE_COLOR);
			else
				dummy.setBackground(Square.EMPTY_SQUARE_COLOR);
			
			diagram1A.add(dummy);
		}
		
		instructionsPanel.add(diagram1A);
		
		
		// set up instructions diagram 1b
		diagram1B = new JLabel();
		diagram1B.setBounds(370, 110, 280, 160);
		diagram1B.setLayout(new GridLayout(8, 14));
		
		// make diagram1B look like a section of the game board
		for (int i = 0; i < 112; i++) {
					
			// create dummy button
			JButton dummy = new JButton();
			dummy.setEnabled(false);
			dummy.setBorderPainted(false);
			
			// set the color
			if (i == 45 || i == 46 || i == 47 || i == 48 || i == 49 || i == 50 || i == 51 || i == 59)
				dummy.setBackground(Snake.SNAKE_COLOR);
			else
				dummy.setBackground(Square.EMPTY_SQUARE_COLOR);
					
			diagram1B.add(dummy);
		}
				
		instructionsPanel.add(diagram1B);
		
		
		// set up instructions diagram 2a
		diagram2A = new JLabel();
		diagram2A.setBounds(50, 110, 280, 160);
		diagram2A.setLayout(new GridLayout(8, 14));
		diagram2A.setBackground(Color.WHITE);
				
		// make instructionsDiagram1 look like a section of the game board
		for (int i = 0; i < 112; i++) {
				
			// create dummy button
			JButton dummy = new JButton();
			dummy.setEnabled(false);
			dummy.setBorderPainted(false);
					
			// set the color
			if (i == 9 || i == 23 || i == 37 || i == 46 || i == 47 || i == 48 || i == 49 || i == 50 
					|| i == 51  || i == 60 || i == 74 || i == 88 || i == 89 || i == 90 || i == 91 || i == 92)
				dummy.setBackground(Snake.SNAKE_COLOR);
			else
				dummy.setBackground(Square.EMPTY_SQUARE_COLOR);
					
			diagram2A.add(dummy);
		}
				
		instructionsPanel.add(diagram2A);
			
		
		// set up instructions diagram 2b
		diagram2B = new JLabel();
		diagram2B.setBounds(370, 110, 280, 160);
		diagram2B.setLayout(new GridLayout(8, 14));
				
		// make diagram2B look like a section of the game board
		for (int i = 0; i < 112; i++) {
						
			// create dummy button
			JButton dummy = new JButton();
			dummy.setEnabled(false);
			dummy.setBorderPainted(false);
					
			// set the color
			if (i == 45 || i == 46 || i == 47 || i == 48 || i == 59 || i == 62 || i == 66 || i == 73  
					|| i ==  76 || i ==  80 || i ==  87 || i ==  88 || i == 89 || i ==  90 || i ==  91   
					|| i ==  92 || i ==  93 || i ==  94)
				dummy.setBackground(Snake.SNAKE_COLOR);
			else
				dummy.setBackground(Square.EMPTY_SQUARE_COLOR);
							
			diagram2B.add(dummy);
		}
						
		instructionsPanel.add(diagram2B);
		
		
		// set up instructions text area
		instructionsText = new JTextArea();
		instructionsText.setBounds(50, 300, 600, 120);
		instructionsText.setFont(new Font("Rockwell", Font.PLAIN, 24));
		instructionsText.setBackground(Color.WHITE);
		instructionsText.setForeground(Color.DARK_GRAY);
		instructionsText.setBorder(BorderFactory.createRaisedBevelBorder());
		instructionsText.setWrapStyleWord(true);
		instructionsText.setLineWrap(true);
		instructionsText.setEditable(false);
		
		instructionsPanel.add(instructionsText);
		
		
		// set up previous button
		previousButton = new JButton("Previous");
		previousButton.setBounds(50, 440, 150, 35);
		previousButton.setBackground(Color.WHITE);
		previousButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		previousButton.setFocusPainted(false);
		previousButton.addActionListener(this);
		
		instructionsPanel.add(previousButton);
		
		
		// set up next button
		nextButton = new JButton("Next");
		nextButton.setBounds(500, 440, 150, 35);
		nextButton.setBackground(Color.WHITE);
		nextButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		nextButton.setFocusPainted(false);
		nextButton.addActionListener(this);
		
		instructionsPanel.add(nextButton);
		
		
		// set up leaderboard screen
		leaderboardPanel = new JPanel();
		leaderboardPanel.setLayout(null);
		leaderboardPanel.setBackground(new Color(140, 200, 80));
		leaderboardPanel.setBounds(0, 0, 700, 550);
		
		
		// set up leaderboard label
		leaderboardLabel = new JLabel("Leaderboard");
		leaderboardLabel.setBounds(50, 30, 600, 30);
		leaderboardLabel.setForeground(Color.BLACK);
		leaderboardLabel.setFont(new Font("Rockwell", Font.PLAIN, 32));
		
		leaderboardPanel.add(leaderboardLabel);
		
		
		// set up rank label
		rankLabel = new JLabel("Rank");
		rankLabel.setBounds(50, 85, 150, 35);
		rankLabel.setBackground(new Color(240, 225, 100));
		rankLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		rankLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		rankLabel.setHorizontalAlignment(JLabel.CENTER);
		rankLabel.setVerticalAlignment(JLabel.CENTER);
		rankLabel.setOpaque(true);
		
		leaderboardPanel.add(rankLabel);
		
		
		// set up nickname label
		nicknameLabel = new JLabel("Nickname");
		nicknameLabel.setBounds(200, 85, 300, 35);
		nicknameLabel.setBackground(new Color(240, 225, 100));
		nicknameLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		nicknameLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		nicknameLabel.setHorizontalAlignment(JLabel.CENTER);
		nicknameLabel.setVerticalAlignment(JLabel.CENTER);
		nicknameLabel.setOpaque(true);
		
		leaderboardPanel.add(nicknameLabel);
		
		
		// set up score label
		scoreLabel = new JLabel("Score");
		scoreLabel.setBounds(500, 85, 150, 35);
		scoreLabel.setText("Score");
		scoreLabel.setBackground(new Color(240, 225, 100));
		scoreLabel.setFont(new Font("Rockwell", Font.PLAIN, 20));
		scoreLabel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		scoreLabel.setVerticalAlignment(JLabel.CENTER);
		scoreLabel.setOpaque(true);
		
		leaderboardPanel.add(scoreLabel);
		
		
		// set up ranks display
		ranksDisplay = new JTextArea();
		ranksDisplay.setBounds(50, 120, 150, 365);
		ranksDisplay.setBackground(Color.WHITE);
		ranksDisplay.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		ranksDisplay.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		ranksDisplay.setEditable(false);
		
		leaderboardPanel.add(ranksDisplay);
		
		
		// set up nicknames display
		nicknamesDisplay = new JTextArea();
		nicknamesDisplay.setBounds(200, 120, 300, 365);
		nicknamesDisplay.setBackground(Color.WHITE);
		nicknamesDisplay.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		nicknamesDisplay.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		nicknamesDisplay.setEditable(false);
			
		leaderboardPanel.add(nicknamesDisplay);
		
		
		// set up scores display
		scoresDisplay = new JTextArea();
		scoresDisplay.setBounds(500, 120, 150, 365);
		scoresDisplay.setBackground(Color.WHITE);
		scoresDisplay.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 17));
		scoresDisplay.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		scoresDisplay.setEditable(false);
			
		leaderboardPanel.add(scoresDisplay);
		
		
		// set up home button
		ImageIcon homeIcon = new ImageIcon(getClass().getResource("/home.png"));
		Image homeImage = homeIcon.getImage().getScaledInstance(45, 45,  java.awt.Image.SCALE_SMOOTH);
		homeIcon = new ImageIcon(homeImage);
		
		homeButton = new JButton(homeIcon);
		homeButton.setBounds(600, 20, 50, 50);
		homeButton.setBackground(Color.WHITE);
		homeButton.setFont(new Font("Rockwell", Font.PLAIN, 20));
		homeButton.setFocusPainted(false);
		homeButton.addActionListener(this);
		
		
		// add all the panels to the JFrame
		add(startMenuPanel);
		add(gamePanel);
		add(instructionsPanel);
		add(leaderboardPanel);
		
		// set up the KeyListener
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		
		// start with the start menu screen
		setStartMenuPanel();
		
		setVisible(true);
	}
	
	
	@Override
	/**
	 * This method listens for when a button is 
	 * clicked.
	 */
	public void actionPerformed(ActionEvent e) {
		
		// play game button (start menu screen)
		if (e.getSource() == playGameButton) {
			
			// set up game screen
			setGamePanel();
		}
		
		// instructions button (start menu screen)
		else if (e.getSource() == instructionsButton) {
			
			// reset instructions page
			instructionsPage = 0;
			
			// set up instructions screen
			setInstructionsPanel();
		}
		
		// high scores button (start menu screen)
		else if (e.getSource() == highScoresButton) {
			
			// set up leaderboard screen
			setLeaderboardPanel();
		}
		
		// previous button (instructions screen)
		else if (e.getSource() == previousButton) {
			
			// decrement instructions page
			instructionsPage--;
			setInstructionsPanel();
		}
		
		// next button (instructions screen)
		else if (e.getSource() == nextButton) {
			
			// increment instructions page
			instructionsPage++;
			setInstructionsPanel();
		}
		
		// home button (game / instructions / leaderboard screen)
		else if (e.getSource() == homeButton) {
			
			// stop the game
			timer.stop();
			
			// set start menu screen
			setStartMenuPanel();
		}
		
		// accept button (game over panel)
		else if (e.getSource() == acceptButton) {
			
			// get entered nickname and trim leading and trailing spaces
			String nickname = textBox.getText().trim();
					
			// if the user did not enter anything or entered only spaces, display invalid input panel
			if (nickname.equals("")) {
				
				gameOverPanel.setVisible(false);
				
				gamePanel.setComponentZOrder(invalidInputPanel, 0);
				invalidInputPanel.setVisible(true);
			}
			
			// else save the user's score and direct them to the leaderboard screen
			else {
				
				// shorten the nickname to max 26 characters
				if (nickname.length() > 26) {
					
					nickname = nickname.substring(0, 26);
				}
				
				saveScore(nickname, game.getSnake().getSnakeSquares().size());
				setLeaderboardPanel();
			}
		}
		
		// cancel button (game over panel)
		else if (e.getSource() == cancelButton) {
			
			// hide game over panel
			gameOverPanel.setVisible(false);
			
			// reset and run the game
			resetGame();
			runGame();
		}
		
		// ok button (invalid input panel)
		else if (e.getSource() == okButton) {
			
			// hide invalid input panel
			invalidInputPanel.setVisible(false);
			
			// reset and run the game
			resetGame();
			runGame();
		}
		
		// timer (game)
		else if (e.getSource() == timer) {
			
			// as long as the direction command is not "STAY"
			if (!game.getDirection().equals("STAY")) {
				
				// check if the snake can move in that direction
				if (game.canMoveSnake()) {
					
					game.moveSnake();
					
					// update the length indicator
					lengthLabel.setText(" Length: " + game.getSnake().getSnakeSquares().size());
				}
				
				// if not, the game is over
				else {
					timer.stop();
					gameOn = false;
					displayGameOver();
				}
			}
		}
		
	}


	@Override
	/**
	 * This method listens for when a key is pressed. 
	 * Only the space and arrow keys will have an effect.
	 */
	public void keyPressed(KeyEvent e) {
		
		// get the position of the Snake's head
		int headPos = game.getSnake().getSnakeSquares().get(0).getPosition();
		
		switch (e.getKeyCode()) {
			
			// up arrow
			case KeyEvent.VK_UP:
				
				// as long as the game is not paused
				if (timer.isRunning()) {
					
					// make sure the snake will not go back onto itself
					if ((headPos - BOARD_LENGTH >= 0 
							&& game.getSnake().getSnakeSquares().size() > 1
							&& !game.getGameBoard()[headPos - BOARD_LENGTH].equals(game.getSnake().getSnakeSquares().get(1))) 
							|| game.getSnake().getSnakeSquares().size() == 1
							|| headPos - BOARD_LENGTH < 0)
						game.setDirection("UP");
				}
				
				break;
			
			// down arrow
			case KeyEvent.VK_DOWN:
				
				// as long as the game is not paused
				if (timer.isRunning()) {
					
					// make sure the snake will not go back onto itself
					if ((headPos + BOARD_LENGTH < (BOARD_WIDTH * BOARD_LENGTH)
							&& game.getSnake().getSnakeSquares().size() > 1
							&& !game.getGameBoard()[headPos + BOARD_LENGTH].equals(game.getSnake().getSnakeSquares().get(1)))
							|| game.getSnake().getSnakeSquares().size() == 1
							|| headPos + BOARD_LENGTH >= (BOARD_WIDTH * BOARD_LENGTH))
						game.setDirection("DOWN");
				}
				
				break;
			
			// left arrow
			case KeyEvent.VK_LEFT:
				
				// as long as the game is not paused
				if (timer.isRunning()) {
					
					// make sure the snake will not go back onto itself
					if ((headPos % BOARD_LENGTH != 0 
							&& game.getSnake().getSnakeSquares().size() > 1
							&& !game.getGameBoard()[headPos - 1].equals(game.getSnake().getSnakeSquares().get(1)))
							|| game.getSnake().getSnakeSquares().size() == 1
							|| headPos % BOARD_LENGTH == 0)
						game.setDirection("LEFT");
				}
				
				break;
			
			// right arrow
			case KeyEvent.VK_RIGHT:
				
				// as long as the game is not paused
				if (timer.isRunning()) {
					
					// make sure the snake will not go back onto itself
					if (((headPos + 1) % BOARD_LENGTH != 0 
							&& game.getSnake().getSnakeSquares().size() > 1
							&& !game.getGameBoard()[headPos + 1].equals(game.getSnake().getSnakeSquares().get(1)))
							|| game.getSnake().getSnakeSquares().size() == 1
							|| (headPos + 1) % BOARD_LENGTH == 0)
						game.setDirection("RIGHT");
				}
				
				break;
				
			// space bar
			case KeyEvent.VK_SPACE:
				
				// has an effect only if the game is running
				if (gameOn) {
					
					// pause
					if (timer.isRunning()) {
						
						// reveal paused label
						setComponentZOrder(pausedLabel, 0);
						pausedLabel.setVisible(true);
						
						timer.stop();
					}
					
					// resume
					else {
						
						// hide paused label
						pausedLabel.setVisible(false);
						
						timer.start();
					}
				}
				
				break;
		}
	}


	// **** The keyReleased and keyTyped methods are useless for this program ****
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	
	/**
	 * This method makes the start menu screen visible 
	 * and hides all the other screens.
	 */
	public void setStartMenuPanel() {
		
		// hide other screens / panels
		gamePanel.setVisible(false);
		instructionsPanel.setVisible(false);
		leaderboardPanel.setVisible(false);
		gameOverPanel.setVisible(false);
		invalidInputPanel.setVisible(false);
		
		gameOn = false;
		
		// hide home button
		homeButton.setVisible(false);
		
		// make this screen visible
		startMenuPanel.setVisible(true);
	}
	
	
	/**
	 * This method makes the game screen visible
	 * and hides all the other screens.
	 */
	public void setGamePanel() {
		
		// hide other screens / panels
		startMenuPanel.setVisible(false);
		instructionsPanel.setVisible(false);
		leaderboardPanel.setVisible(false);
		gameOverPanel.setVisible(false);
		invalidInputPanel.setVisible(false);
		
		// hide paused label
		pausedLabel.setVisible(false);
				
		// reveal home button
		gamePanel.add(homeButton);
		homeButton.setVisible(true);
		
		// make this screen visible
		gamePanel.setVisible(true);
		
		// reset and run the game
		resetGame();
		runGame();
	}
	
	
	/**
	 * This method makes the instructions screen visible 
	 * and hides all the other screens.
	 */
	public void setInstructionsPanel() {
		
		// hide other screens / panels
		startMenuPanel.setVisible(false);
		gamePanel.setVisible(false);
		leaderboardPanel.setVisible(false);
		gameOverPanel.setVisible(false);
		invalidInputPanel.setVisible(false);
		
		gameOn = false;
		
		// reveal home button
		instructionsPanel.add(homeButton);
		homeButton.setVisible(true);
				
		// make this screen visible
		instructionsPanel.setVisible(true);
		
		// set up the instructions page
		switch (instructionsPage) {
			
			// page 1
			case 0:
				
				// set up appropriate diagrams
				arrowsImage.setVisible(true);
				diagram.setVisible(true);
				diagram1A.setVisible(false);
				diagram1B.setVisible(false);
				diagram2A.setVisible(false);
				diagram2B.setVisible(false);
				
				// set up label and text
				instructionsLabel.setText("Instructions (1 of 3)");
				instructionsText.setText("Use the arrow keys to control the snake. Use the space bar to pause "
						+ "and resume the game.");
				
				previousButton.setVisible(false);
				nextButton.setVisible(true);
				
				break;
				
			// page 2
			case 1:
				
				// set up appropriate diagrams
				diagram1A.setVisible(true);
				diagram1B.setVisible(true);
				arrowsImage.setVisible(false);
				diagram.setVisible(false);
				diagram2A.setVisible(false);
				diagram2B.setVisible(false);
				
				// set up label and text
				instructionsLabel.setText("Instructions (2 of 3)");
				instructionsText.setText("The snake will grow in length every time it eats an apple.");
				
				previousButton.setVisible(true);
				nextButton.setVisible(true);
				
				break;
				
			// page 3
			case 2:
				
				// set up appropriate diagrams
				diagram2A.setVisible(true);
				diagram2B.setVisible(true);
				arrowsImage.setVisible(false);
				diagram.setVisible(false);
				diagram1A.setVisible(false);
				diagram1B.setVisible(false);
				
				// set up label and text
				instructionsLabel.setText("Instructions (3 of 3)");
				instructionsText.setText("Don't make the snake hit the walls or run into itself - you will lose!");
				
				previousButton.setVisible(true);
				nextButton.setVisible(false);
				
				break;
		}
	}
	
	
	/**
	 * This method makes the leaderboard screen visible 
	 * and hides all the other screens.
	 */
	public void setLeaderboardPanel() {
		
		// hide other screens / panels
		startMenuPanel.setVisible(false);
		instructionsPanel.setVisible(false);
		gamePanel.setVisible(false);
		gameOverPanel.setVisible(false);
		invalidInputPanel.setVisible(false);
		
		gameOn = false;
				
		// reveal home button
		leaderboardPanel.add(homeButton);
		homeButton.setVisible(true);
				
		// make this screen visible
		leaderboardPanel.setVisible(true);
		
		
		// reset displays
		ranksDisplay.setText("");
		nicknamesDisplay.setText("");
		scoresDisplay.setText("");
		
		// set up the top 15 high scores
		for (int i = 0; i < 15; i++) {
			
			// as long as the index is within bounds of the ArrayList
			if (i < highScores.size()) {
				
				// rank
				ranksDisplay.append(String.format("%8s", (i + 1)) + "\n"); // pad left 8 spaces
				
				
				/* The nickname part is a bit more complicated (to get it to be center-aligned):
				 * 	1) Split the String to 2 substrings of equal length
				 * 	2) append the first substring to the display (pad spaces to the left)
				 * 	3) append the second substring to the display (pad spaces to the right)
				 */
				String left = highScores.get(i)[1].substring(0, highScores.get(i)[1].length()/2);
				String right = highScores.get(i)[1].substring(highScores.get(i)[1].length()/2);
				
				nicknamesDisplay.append(String.format("%15s", left)); // pad left 15 spaces
				nicknamesDisplay.append(String.format("%-15s", right) + "\n"); // pad right 15 spaces
				
				
				// score
				scoresDisplay.append(String.format("%8s", highScores.get(i)[0]) + "\n"); // pad left 8 spaces
			}
			
			// else the index is out of bounds
			else {
				break;
			}
		}
	}
	
	
	/**
	 * This method restarts the timer, which starts 
	 * the executions of the game.
	 */
	public void runGame() {
			
		timer.restart();
		gameOn = true;
	}
	
	
	/**
	 * This method resets the game by resetting the Snake, 
	 * the Apple, the direction, and each Square on the game 
	 * board to default.
	 */
	public void resetGame() {
		
		// set the occupier of each Square in gameBoard to default
		for (int i = 0; i < game.getGameBoard().length; i++) {
			
			game.getGameBoard()[i].setOccupier("NONE");
			game.getGameBoard()[i].setBackground(Square.EMPTY_SQUARE_COLOR);
		}
		
		
		// pick a random square in gameBoard and set it to the Snake's initial starting position
		int randomPos = (int) (Math.random() * (game.getGameBoard().length));
		
		game.getGameBoard()[randomPos].setOccupier("SNAKE");
		game.getGameBoard()[randomPos].setBackground(Snake.SNAKE_COLOR);
		
		game.getSnake().getSnakeSquares().clear(); // make the ArrayList empty
		game.getSnake().getSnakeSquares().add(game.getGameBoard()[randomPos]); // add the Snake's "head"
		
		
		// pick a random square in gameBoard and set it to the Apple's initial starting position
		// make sure it is not the Snake's initial starting position
		while (game.getGameBoard()[randomPos].getOccupier().equals("SNAKE")) {
					
			randomPos = (int) (Math.random() * (game.getGameBoard().length));
		}
				
		game.getGameBoard()[randomPos].setOccupier("APPLE");
		game.getGameBoard()[randomPos].setBackground(Apple.APPLE_COLOR);
		game.getApple().setPostion(randomPos);
				
				
		// set the direction to default
		game.setDirection("STAY");
		
		// reset length and high score indicators
		lengthLabel.setText(" Length: " + game.getSnake().getSnakeSquares().size());

		if (highScores.size() > 0) {
			highScoreLabel.setText(" High Score: " + highScores.get(0)[0]);
		}
		else {
			highScoreLabel.setText(" High Score: ");
		}
	}
	
	
	/**
	 * This method displays the game over notifier and 
	 * prompts the user to enter their nickname to save 
	 * their score.
	 */
	public void displayGameOver() {
		
		// display game over panel
		gamePanel.setComponentZOrder(gameOverPanel, 0);
		gameOverPanel.setVisible(true);
		textBox.setText("");
	}
	
	
	/**
	 * This method retrieves the data from the text 
	 * file and stores it in the ArrayList highScores.
	 */
	public void getScores() {
		
		// open the file
		IO.openInputFile(DATA_TEXT_FILE);
		
		try {
			String line = IO.readLine();
			
			// as long as the line is not equal to null
			while (line != null) {
					
				// get the score and nickname
				String score = line;
				
				line = IO.readLine();
				String nickname = line;
				
				// store it in the ArrayList highScores
				highScores.add(new String[] {score, nickname});
				
				line = IO.readLine(); // get the next line
			}
						
			IO.closeInputFile();
		}
		catch (IOException e) {
				
		}
	}
	
	
	/**
	 * This method saves the user's score in the 
	 * data text file.
	 */
	public void saveScore(String nickname, int score) {
		
		boolean added = false; // boolean to determine if the user's score was added
		
		// loop through the ArrayList highScores and insert the user's score in the right place
		for (int i = 0; i < highScores.size(); i++) {
			 
			// check if the user's score is higher than the score at the current index
			if (score > Integer.parseInt(highScores.get(i)[0])) {
				
				highScores.add(i, new String[] {Integer.toString(score), nickname}); // insert the user's score
				
				added = true;
				break;
			}
		}
		
		// if the user's score is still not added to the ArrayList
		if (!added) {
			
			// append it to the end of the ArrayList
			highScores.add(new String[] {Integer.toString(score), nickname});
		}
		
		
		// **** Update the data text file ****
		
		// open file
		IO.createOutputFile(DATA_TEXT_FILE);
		
		// save the scores
		for (int i = 0; i < highScores.size(); i++) {
				
			// store the score followed by the nickname
			IO.println(highScores.get(i)[0]);
			IO.println(highScores.get(i)[1]);
		}
		
		// close file
		IO.closeOutputFile();
	}
	
} // end class
