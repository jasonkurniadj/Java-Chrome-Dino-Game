package main;

import javax.swing.*;

/***
 * 
 * @author Jason.
 *
 */
public class Main extends JFrame {
	public static final int SCREEN_WIDTH = 650;
	public static final int SCREEN_HEIGHT = 225;
	private GameScreen gameScreen;
	
	public void init() {
		setTitle("Chrome T-Rex Game - Jason\"QXY\"");
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	public Main() {
		init();
		
		gameScreen = new GameScreen();
		addKeyListener(gameScreen);
		add(gameScreen);
		
		this.setVisible(true);
		gameScreen.startGame();
	}
	
	public static void main(String args[]) {
		new Main();
	}
}
