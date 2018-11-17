package Snake;
import Snake.Direction;
import Snake.BlockType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.*;

class MainFrame extends JFrame {
	Snake_game snakeGame;
	GameboardPanel gameboardPanel;
	SettingsPanel settingsPanel;
	private Direction inputDir;
	private int speed;
	private boolean pause = false;
	private boolean replay = false;
	
	public MainFrame (Snake_game snakeGame, int speed, int gameHeight, int gameWidth) {
		this.snakeGame = snakeGame;
		this.speed = speed;
		this.inputDir = Direction.NULL;
		setSize((gameWidth)*55 + 240, (gameHeight)*52 + 40);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		
		gameboardPanel = new GameboardPanel(gameHeight, gameWidth);
		settingsPanel = new SettingsPanel(this);
		container.add(settingsPanel, BorderLayout.EAST);
		container.add(gameboardPanel, BorderLayout.CENTER);
		
		this.addKeyListener(new KeyListener() {
				public void keyReleased (KeyEvent e) {
					if(e.getKeyCode() == 'P') {
						pauseGame();
						JButton btn = settingsPanel.getPauseButton();
						if(btn.getText().equals("Pause (P)"))
							btn.setText("Continue (P)");
						else
							btn.setText("Pause (P)");
					}
				}
				public void keyTyped (KeyEvent e) {}
				public void keyPressed (KeyEvent e) {}
		});

		this.addKeyListener(new KeyListener() {
			public void keyReleased (KeyEvent e) {}
			public void keyTyped (KeyEvent e) {}
			public void keyPressed (KeyEvent e) {
				if(!pause) if(e.getKeyCode() == e.VK_UP) {
					inputDir = Direction.UP;
				} else if(e.getKeyCode() == e.VK_DOWN) {
					inputDir = Direction.DOWN;
				} else if(e.getKeyCode() == e.VK_RIGHT) {
					inputDir = Direction.RIGHT;
				} else if(e.getKeyCode() == e.VK_LEFT) {
					inputDir = Direction.LEFT;
				} else
					inputDir = Direction.NULL;
			}
		});

		setVisible(true);
		setResizable(false);
		this.setFocusable(true);
	    this.requestFocus();
	}
	
	public void resetFrame() {
		inputDir = Direction.NULL;
		flipReplay();
		settingsPanel.resetSetting();
		pause = false;
	}
	
	public void resetFrame(int gameHeight, int gameWidth) {
		inputDir = Direction.NULL;
		flipReplay();
		settingsPanel.resetSetting();
		pause = false;
		
		setSize((gameWidth)*55 + 240, (gameHeight)*52 + 40);
		gameboardPanel.resetGameboardPanel(gameHeight, gameWidth);
	}
	
	public void repaintGameBoard(Snake_gameboard gameboard) { gameboardPanel.repaintBoard(gameboard); }
	public Direction getInputDir() { return inputDir; }
	public boolean getPause() { return pause; }
	public void pauseGame() { pause = !pause; }
	public boolean getReplay() { return replay; }
	public void flipReplay() { replay = !replay; }
	public void setSpeed(int speed) { snakeGame.setSpeed(speed); }
}