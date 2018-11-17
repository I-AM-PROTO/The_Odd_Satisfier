package Snake;
import Snake.Direction;
import Snake.BlockType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class MainFrame extends JFrame {
	ImageIcon image = new ImageIcon(Toolkit.getDefaultToolkit().getImage("./res/snake1.png"));
	JLabel label = new JLabel(image);
	
	GameboardPanel gameboardPanel;
	
	public MainFrame (int gameHeight, int gameWidth) {
		setSize((gameWidth)*55 + 240, (gameHeight)*52 + 40);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container container = getContentPane();
		container.setLayout(new BorderLayout());
		
		gameboardPanel = new GameboardPanel(gameHeight, gameWidth);
		container.add(new settingsPanel(this), BorderLayout.EAST);
		container.add(gameboardPanel, BorderLayout.CENTER);
		
		setVisible(true);
		setResizable(true);
	}
	
	public void repaintGameBoard(Snake_gameboard gameboard) { gameboardPanel.repaintBoard(gameboard); }
}

class GameboardPanel extends JPanel {
	private int gameHeight, gameWidth;
	private HashMap<String, Image> imageMap = new HashMap<>();
	
	public GameboardPanel (int gameHeight, int gameWidth) {
		this.gameHeight = gameHeight;
		this.gameWidth = gameWidth;
		this.setLayout(new GridLayout(gameHeight, gameWidth));
		
		imageMap.put("apple", Toolkit.getDefaultToolkit().getImage("./snakeskin/apple.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("blank", Toolkit.getDefaultToolkit().getImage("./snakeskin/blank.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("body0", Toolkit.getDefaultToolkit().getImage("./snakeskin/body0.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("body1", Toolkit.getDefaultToolkit().getImage("./snakeskin/body1.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("body2", Toolkit.getDefaultToolkit().getImage("./snakeskin/body2.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("body3", Toolkit.getDefaultToolkit().getImage("./snakeskin/body3.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("body4", Toolkit.getDefaultToolkit().getImage("./snakeskin/body4.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("body5", Toolkit.getDefaultToolkit().getImage("./snakeskin/body5.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("head0", Toolkit.getDefaultToolkit().getImage("./snakeskin/head0.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("head1", Toolkit.getDefaultToolkit().getImage("./snakeskin/head1.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("head2", Toolkit.getDefaultToolkit().getImage("./snakeskin/head2.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("head3", Toolkit.getDefaultToolkit().getImage("./snakeskin/head3.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("tail0", Toolkit.getDefaultToolkit().getImage("./snakeskin/tail0.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("tail1", Toolkit.getDefaultToolkit().getImage("./snakeskin/tail1.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("tail2", Toolkit.getDefaultToolkit().getImage("./snakeskin/tail2.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("tail3", Toolkit.getDefaultToolkit().getImage("./snakeskin/tail3.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("wall0", Toolkit.getDefaultToolkit().getImage("./snakeskin/wall0.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("wall1", Toolkit.getDefaultToolkit().getImage("./snakeskin/wall1.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("wall2", Toolkit.getDefaultToolkit().getImage("./snakeskin/wall2.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("wall3", Toolkit.getDefaultToolkit().getImage("./snakeskin/wall3.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("wall4", Toolkit.getDefaultToolkit().getImage("./snakeskin/wall4.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		imageMap.put("wall5", Toolkit.getDefaultToolkit().getImage("./snakeskin/wall5.png").getScaledInstance(50, 50, Image.SCALE_DEFAULT));	
	}

	public void repaintBoard(Snake_gameboard gameboard) {
		this.removeAll();
		for(int i=0; i<gameHeight; i++) {
			for(int j=0; j<gameWidth; j++) {
				JLabel snake = new JLabel(new ImageIcon(convertBlock(gameboard.getBlock(i, j), gameboard)));
				//snake.setSize(50, 50);
				this.add(snake);
			}
		}
		this.revalidate(); this.repaint();
	}

	private Image convertBlock(Block block, Snake_gameboard gameboard) {
		Direction currDir = block.getDirection();
		
		if(block.getBlockType() == BlockType.BLANK)
			return imageMap.get("blank");
		else if(block.getBlockType() == BlockType.APPLE)
			return imageMap.get("apple");
		else if(block.getBlockType() == BlockType.BODY) {
			Direction pastDir = gameboard.getPastDirection(block);

			if(pastDir == block.getDirection())
				return imageMap.get("body" + (int)(currDir.getID()/2));
			else {
				switch(pastDir) {
				case UP: if(currDir == Direction.LEFT) return imageMap.get("body2");
				else return imageMap.get("body3");
				case DOWN: if(currDir == Direction.LEFT) return imageMap.get("body4");
				else return imageMap.get("body5");
				case LEFT: if(currDir == Direction.UP) return imageMap.get("body5");
				else return imageMap.get("body3");
				case RIGHT: if(currDir == Direction.UP) return imageMap.get("body4");
				else return imageMap.get("body2");
				case NULL: return imageMap.get("tail" + currDir.getID());
				}
				return null;
			}
		} else if(block.getBlockType() == BlockType.HEAD)
			return imageMap.get("head" + currDir.getID());
		else if(block.getBlockType() == BlockType.WALL) {
			int x = block.getxpos(), y = block.getypos();
			int width = gameboard.getWidth(), height = gameboard.getHeight();
			if(x != 0 && x != height - 1)
				return imageMap.get("wall0");
			else if(y != 0 && y != width - 1)
				return imageMap.get("wall1");
			else if(x + y == 0)
				return imageMap.get("wall3");
			else if(x + y == width + height - 2)
				return imageMap.get("wall4");
			else if(x == height - 1)
				return imageMap.get("wall5");
			else
				return imageMap.get("wall2");
		} else
			return null;
	}
}

class settingsPanel extends JPanel {
	Dictionary<Integer, String> tickdict;
	MainFrame mainFrame;
	Dimension d;
	JButton b1, b2, b3, b4;
	JSlider js;
	
	public settingsPanel(MainFrame mainFrame) {
		d = new Dimension();
		d.setSize(120, 30);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.BLACK);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(2, 2));
		b1 = new JButton("Pause (P)");
		b2 = new JButton("Continue (C)");
		b3 = new JButton("Github");
		b4 = new JButton("Exit");
		b1.setPreferredSize(d);
		b2.setPreferredSize(d);
		b3.setPreferredSize(d);
		b4.setPreferredSize(d);
		btnPanel.add(b1);
		btnPanel.add(b2);
		btnPanel.add(b3);
		btnPanel.add(b4);
		
		js = new JSlider(JSlider.HORIZONTAL, 0, 30, 30);
		js.setPaintTicks(true);
		js.setPaintLabels(true);
		js.setMajorTickSpacing(10);
		js.setMinorTickSpacing(2);
		//js.addChangeListener(this);
		
		add(js);
		add(btnPanel);
		
		b1.addActionListener(new btn(mainFrame));
	}
}

class btn implements ActionListener{
	MainFrame mainFrame;
	public btn (MainFrame mainFrame) {this.mainFrame = mainFrame;}
	
	@Override
	public void actionPerformed (ActionEvent e) {
		mainFrame.setSize(10, 10);
		mainFrame.label.setSize(500, 500);
		Image newImage = mainFrame.image.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT);
		mainFrame.image.setImage(newImage);
	}
}