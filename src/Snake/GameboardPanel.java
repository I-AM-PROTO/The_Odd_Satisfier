package Snake;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

class GameboardPanel extends JPanel {
	private int gameHeight, gameWidth;
	private HashMap<String, Image> imageMap = new HashMap<>();
	
	public void resetGameboardPanel(int gameHeight, int gameWidth) {
		this.removeAll();
		this.gameHeight = gameHeight;
		this.gameWidth = gameWidth;
		this.setLayout(new GridLayout(gameHeight, gameWidth));
	}
	
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
			if(block.getBodyID() < 0) return imageMap.get("tail" + currDir.getID());
			else return imageMap.get("body" + block.getBodyID());	
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