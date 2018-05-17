package main;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

import gameObj.*;
import util.*;

/***
 * 
 * @author Jason.
 *
 */
public class GameScreen extends JPanel implements Runnable, KeyListener {
	private static final int START_GAME = 0;
	private static final int GAME_PLAYING= 1;
	private static final int GAME_OVER = 2;
	
	private Ground ground;
	private MainCharacter mainCharacter;
	private Main gameWindow;
	private EnemiesManager enemiesManager;
	private Clouds clouds;
	private Thread thread;

	private boolean isKeyPressed;

	private int gameState = START_GAME;

	private BufferedImage replayButtonImage;
	private BufferedImage gameOverButtonImage;

	public GameScreen() {
		mainCharacter = new MainCharacter();
		ground = new Ground(Main.SCREEN_WIDTH, mainCharacter);
		mainCharacter.setSpeedX(4);
		
		replayButtonImage = Resource.getResouceImage("assets/image/replay_button.png");
		gameOverButtonImage = Resource.getResouceImage("assets/image/gameover_text.png");
		enemiesManager = new EnemiesManager(mainCharacter);
		clouds = new Clouds(Main.SCREEN_WIDTH, mainCharacter);
	}

	public void startGame() {
		thread = new Thread(this);
		thread.start();
	}

	public void gameUpdate() {
		if(gameState == GAME_PLAYING) {
			clouds.update();
			ground.update();
			mainCharacter.update();
			enemiesManager.update();
			
			if(enemiesManager.isCollision()) {
				mainCharacter.playDeadSound();
				gameState = GAME_OVER;
				mainCharacter.dead(true);
			}
		}
	}

	public void paint(Graphics g) {
		g.setColor(Color.decode("#f7f7f7")); //#F7F7F7
		g.fillRect(0, 0, getWidth(), getHeight());

		switch (gameState) {
			case START_GAME:
				mainCharacter.draw(g);
			break;
				
			case GAME_PLAYING:
			
			case GAME_OVER:
				clouds.draw(g);
				ground.draw(g);
				enemiesManager.draw(g);
				mainCharacter.draw(g);
				
				g.setColor(Color.BLACK);
				g.drawString("HI " + mainCharacter.hiScore, Main.SCREEN_WIDTH - 150, 20);
				g.drawString("" + mainCharacter.score, Main.SCREEN_WIDTH - 75, 20);
				
				if (gameState == GAME_OVER) {
					g.drawImage(gameOverButtonImage, 200, 30, null);
					g.drawImage(replayButtonImage, 283, 50, null);
				}
			break;
		}
	}

	@Override
	public void run() {
		int fps = 100;
		long msPerFrame = 1000 * 1000000 / fps;
		long lastTime = 0;
		long elapsed;
		
		int msSleep;
		int nanoSleep;

		long endGame;
		//long lag = 0;

		while(true) {
			gameUpdate();
			repaint();
			
			endGame = System.nanoTime();
			elapsed = (lastTime + msPerFrame - System.nanoTime());
			msSleep = (int)(elapsed / 1000000);
			nanoSleep = (int)(elapsed % 1000000);
			
			if(mainCharacter.score>250 && mainCharacter.score<500)
				nanoSleep = (int)(elapsed % 500000);
			else if(mainCharacter.score>500 && mainCharacter.score<750)
				nanoSleep = (int)(elapsed % 100000);
			else if(mainCharacter.score>750 && mainCharacter.score<1000)
				nanoSleep = (int)(elapsed % 50000);
			else if(mainCharacter.score>1000 && mainCharacter.score<1500)
				nanoSleep = (int)(elapsed % 25000);
			else if(mainCharacter.score>1500 && mainCharacter.score<2000)
				nanoSleep = (int)(elapsed % 10000);
			else if(mainCharacter.score>2000 && mainCharacter.score<3000)
				nanoSleep = (int)(elapsed % 5000);
			else if(mainCharacter.score > 3000)
				nanoSleep = (int)(elapsed % 1000);
			
			if(msSleep <= 0) {
				lastTime = System.nanoTime();
				continue;
			}
			
			try {
				Thread.sleep(msSleep, nanoSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			lastTime = System.nanoTime();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(!isKeyPressed) {
			isKeyPressed = true;
			
			switch (gameState) {
				case START_GAME:
					if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP)
						gameState = GAME_PLAYING;
				break;
				
				case GAME_PLAYING:
					if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE)
						mainCharacter.jump();
					else if(e.getKeyCode() == KeyEvent.VK_DOWN)
						mainCharacter.down(true);
				break;
				
				case GAME_OVER:
					if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
						gameState = GAME_PLAYING;
						resetGame();
					}
				break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		isKeyPressed = false;
		
		if(gameState == GAME_PLAYING) {
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				mainCharacter.down(false);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void resetGame() {
		enemiesManager.reset();
		mainCharacter.dead(false);
		mainCharacter.reset();
	}
}
