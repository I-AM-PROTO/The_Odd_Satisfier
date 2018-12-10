package main_hub;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Snake.*;
import sorter.Main;

//TODO color preset

public class MainHubFrame extends JFrame {
	public static final int WINDOW_WIDTH = 810;
	public static final int WINDOW_HEIGHT = 470;
	public static final int DVD_WIDTH = 180;
	public static final int DVD_HEIGHT = 80;
	public static final String[] btnStr = {"Creator of this... thing", "Inspiration", "GitHub", "Exit"};
	public static final Dimension[] btnSize = {new Dimension(30, 30),
											   new Dimension(30, 30),
											   new Dimension(30, 30),
											   new Dimension(180, 180),
											   new Dimension(180, 180),
											   new Dimension(180, 180),
											   new Dimension(190, 40),
											   new Dimension(120, 40),
											   new Dimension(120, 40),
											   new Dimension(120, 40)};
	public static final Point[] btnPos = {new Point(250, 165),
										  new Point(450, 165),
										  new Point(650, 165),
										  new Point(100, 165),
										  new Point(300, 165),
										  new Point(500, 165),
										  new Point(100, 365),
										  new Point(300, 365),
										  new Point(430, 365),
										  new Point(560, 365),};
	private BootScreen bootScreen = new BootScreen((int)(Math.random() * 11));
	private JButton[] menuBtn = new JButton[10];
	private MainBackground mainBackground = new MainBackground(this);
	private int egg = 15;
	private int mode = 0;
	
	public MainHubFrame() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT + 30);
		setTitle("virus.exe");
		setIconImage(Toolkit.getDefaultToolkit().getImage("./res/bup.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		setResizable(false);
		
		bootScreen.setLocation(0, 0);
		bootScreen.setSize(1000, 1000);
		mainBackground.setLocation(0, 0);
		mainBackground.setSize(1000, 1000);
		
		getContentPane().add(bootScreen);
		runBootScreen();
		
		getContentPane().add(mainBackground);
		this.revalidate(); this.repaint();
		mainBackground.enterSequence();
		
		addKeyListener(new Egg(this));
		
		this.setFocusable(true);
		this.requestFocus();
		
		runMainBackground();
	}
	
	class Egg extends KeyAdapter {
		MainHubFrame m;
		public Egg(MainHubFrame m) {this.m = m;}
		@Override
		public void keyTyped(KeyEvent e) {
			if(e.getKeyChar() == '+' && egg > 1)
				egg--;
			else if(e.getKeyChar() == '-')
				egg++;
			else if(e.getKeyChar() == 'd')
				mainBackground.addDVD();
			else if(e.getKeyChar() == 'v')
				mainBackground.removeDVD();
			m.requestFocus();
		}
	}
	
	class BootScreen extends JPanel {
		public int visibility = 255, index;
		private int qx, nx;
		public BootScreen (int index) {this.index = index;}
		public void paintComponent (Graphics g) {
			super.paintComponent(g);
			setBackground(Color.WHITE);
			String quote, name;
			switch(index) {
			case -1:{
				quote = "Satisfactory experience may differ significantly. Sorry :(";
				name = "";
				qx = 130; nx = 100;
				break;
			}
			case 0:{
				quote = "\"Ok who the fuck read my diary\"";
				name = "- Anne Frank";
				qx = 200; nx = 490;
				break;
			}
			case 1:{
				quote = "\"To show you the power of flex tape, I sawed this boat in half!\"";
				name = "- Phil Swift";
				qx = 80; nx = 620;
				break;
			}
			case 2:{
				quote = "\"SMASH SUBSCRIBE\"";
				name = "- PewDiePie (and basically every single youtuber)";
				qx = 200; nx = 300;
				break;
			}
			case 3:{
				quote = "\"bup\"";
				name = "- Toad";
				qx = 360; nx = 400;
				break;
			}
			case 4:{
				quote = "\"I guess painter was a better job choice\"";
				name = "- Hitler";
				qx = 180; nx = 550;
				break;
			}
			case 5:{
				quote = "\"Aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa\"";
				name = "- Aaaaaaaaaaa";
				qx = 180; nx = 500;
				break;
			}
			case 6:{
				quote = "\"There's John Cena!\"";
				name = "- Nobody";
				qx = 260; nx = 440;
				break;
			}
			case 7:{
				quote = "\"I am How to Basic\"";
				name = "- Everyone";
				qx = 270; nx = 450;
				break;
			}
			case 8:{
				quote = "\"Boom tetris for Jeff\"";
				name = "- CTWC commentator";
				qx = 220; nx = 420;
				break;
			}
			case 9:{
				quote = "\"... or is it?\"";
				name = "- Vsauce Michael";
				qx = 280; nx = 430;
				break;
			}
			case 10:{
				quote = "\"An A press is an A press. You can't say it's only a half\"";
				name = "- TJ \"\"\"\"\"Henry\"\"\"\"\" Yoshi";
				qx = 80; nx = 550;
				break;
			}
			default:{
				quote = "\"GRAND DAD\"";
				name = "- Vargskelethor Joel";
				qx = 290; nx = 380;
			}
			}
			g.setFont(new Font("Arial", Font.BOLD, 20));
			g.drawString(quote, qx, WINDOW_HEIGHT / 2 - 20);
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			g.drawString(name, nx, WINDOW_HEIGHT / 2 + 10);
			g.setColor(new Color(255, 255, 255, visibility));
			g.fillRect(0, 0, 800, 500);
		}
	}

	class MainBackground extends JPanel {
		private MainHubFrame mhf;
		private int enterStage = -1;
		private int enterInterval = 500;
		Image DVD;
		Vector<Point> DVDpos = new Vector<Point>();
		Vector<Point> DVDmov = new Vector<Point>();

		public MainBackground (MainHubFrame mhf) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(new File("./res/DVD.png"));
			} catch (IOException e) {}
			
			DVD = img.getScaledInstance(DVD_WIDTH, DVD_HEIGHT, Image.SCALE_SMOOTH);
			
			addDVD();
			this.mhf = mhf;
			setLayout(null);
			setBackground(Color.WHITE);
			for(int i=0; i<10; i++) {
				menuBtn[i] = new JButton(i >= 6 ? btnStr[i-6] : null);
				menuBtn[i].setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
				menuBtn[i].setName(Integer.toString(i));
				menuBtn[i].setLocation(btnPos[i]);
				menuBtn[i].setSize(btnSize[i]);
				menuBtn[i].setBackground(null);
				if(i <= 5) menuBtn[i].setIcon(new ImageIcon("./menuskin/"+ i + ".png"));
				if(3 <= i && i <= 5) menuBtn[i].setBorder(null);
				menuBtn[i].setOpaque(false);
				menuBtn[i].setFocusable(false);
				menuBtn[i].addActionListener(new MenuListener(mhf));
			}
		}

		private void addDVD() {
			DVDpos.add(new Point((int)(Math.random() * (WINDOW_WIDTH - DVD_WIDTH)), (int)(Math.random() * (WINDOW_HEIGHT - DVD_HEIGHT))));
			DVDmov.add(new Point(DVDpos.get(DVDpos.size()-1).x % 2 == 1 ? -2 : 2, DVDpos.get(DVDpos.size()-1).y % 2 == 1 ? -2 : 2));
		}
		
		private void removeDVD() {
			if(DVDpos.size() == 0) return;
			DVDpos.remove(DVDpos.size()-1);
			DVDmov.remove(DVDmov.size()-1);
		}

		public void enterSequence () {
			interval(enterInterval);
			enterStage++;
			revalidate(); repaint();
			interval(enterInterval * 2);
			enterStage++;
			revalidate(); repaint();
			interval(enterInterval);
			for(int i=0; i<=2; i++) {
				menuBtn[i].setVisible(false);
				add(menuBtn[i]);
			}
			for(int i=3; i<=5; i++) {
				interval(enterInterval);
				add(menuBtn[i]);
				revalidate(); repaint();
			}
			interval(enterInterval/2);
			for(int i=6; i<10; i++) {
				add(menuBtn[i]);
			}
			interval(enterInterval/2);
			for(int i=0; i<=2; i++) {
				menuBtn[i].setVisible(true);
			}
			enterStage++;
			revalidate(); repaint();
		}

		public void paintComponent (Graphics g) {
			super.paintComponent(g);

			switch(enterStage) {
			case 2: {
				for(int i=0; i<DVDpos.size(); i++) {
					g.drawImage(DVD, DVDpos.get(i).x += DVDmov.get(i).x, DVDpos.get(i).y += DVDmov.get(i).y, this);
				}
			}
			case 1:{
				g.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
				g.drawString("the best way to sweep your crippling depression under the rug haha", 120, 125);
			}
			case 0:{
				g.setFont(new Font("Comic Sans MS", Font.BOLD, 50));
				g.drawString("THE ODD SATISFIER", 120, 100);
			}
			}
		}
	}

	class MenuListener implements ActionListener {
		private MainHubFrame mhf;
		public MenuListener(MainHubFrame mhf) { this.mhf = mhf; }
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(Integer.parseInt(((JButton)e.getSource()).getName())) {
			case 0:{
				try {
					Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=DgrwX5Ypn3Q").toURI());
				} catch (Exception ee) {
					ee.printStackTrace();
				}
				break;
			}
			case 1:{
				try {
					Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=XINAniQCh8Y").toURI());
				} catch (Exception ee) {
					ee.printStackTrace();
				}
				break;
			}
			case 2:{
				try {
					Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=_-0zn69cisc").toURI());
				} catch (Exception ee) {
					ee.printStackTrace();
				}
				break;
			}
			case 3:{
				mode = 1;
				mhf.dispose();
				break;
			}
			case 4:{
				mode = 2;
				mhf.dispose();
				break;
			}
			case 5:{
				mode = 3;
				mhf.dispose();
				break;
			}
			case 6:{
				JOptionPane.showMessageDialog(mhf, "Korea Univ. #2018320120 ±èµ¿ÈÄ", "Press + and - for eggs", JOptionPane.PLAIN_MESSAGE);
				break;
			}
			case 7:{
				try {
					Desktop.getDesktop().browse(new URL("https://www.reddit.com/r/oddlysatisfying/top/?t=all").toURI());
				} catch (Exception ee) {
					ee.printStackTrace();
				}
				break;
			}
			case 8:{
				try {
					Desktop.getDesktop().browse(new URL("https://github.com/I-AM-PROTO/The_Odd_Satisfier").toURI());
				} catch (Exception ee) {
					ee.printStackTrace();
				}
				break;
			}
			case 9:{
				System.exit(0);
			}
			}
		}
	}

	private void runBootScreen() {
		interval(400);
		for(int i=255; i>=0; i-=5) {
			interval(30);
			bootScreen.visibility = i;
			bootScreen.revalidate();
			bootScreen.repaint();
		}
		interval(1000);
		for(int i=0; i<=255; i+=5) {
			interval(30);
			bootScreen.visibility = i;
			bootScreen.revalidate();
			bootScreen.repaint();
		}
		interval(100);
		bootScreen.index = -1;
		for(int i=255; i>=0; i-=5) {
			interval(30);
			bootScreen.visibility = i;
			bootScreen.revalidate();
			bootScreen.repaint();
		}
		interval(1000);
		for(int i=0; i<=255; i+=5) {
			interval(30);
			bootScreen.visibility = i;
			bootScreen.revalidate();
			bootScreen.repaint();
		}
		interval(1000);
	}
	
	private void runMainBackground() {
		while(true) {
			interval(egg);
			for(int i=0; i < mainBackground.DVDpos.size(); i++) {
				if(mainBackground.DVDpos.get(i).x >= WINDOW_WIDTH - DVD_WIDTH || mainBackground.DVDpos.get(i).x <= 0) {
					mainBackground.DVDmov.get(i).x = mainBackground.DVDpos.get(i).x <= 0 ? 2 : -2;
				}
				if(mainBackground.DVDpos.get(i).y >= WINDOW_HEIGHT - DVD_HEIGHT || mainBackground.DVDpos.get(i).y <= 0) {
					mainBackground.DVDmov.get(i).y = mainBackground.DVDpos.get(i).y <= 0 ? 2 : -2;
				}
			}
			mainBackground.revalidate();
			mainBackground.repaint();
			
			if(mode != 0)
				break;
		}
		
		switch(mode) {
		case 1:snakeGame(); break;
		case 2:snakeAutoplay(); break;
		case 3:sortSim();
		}
	}
	
	private void interval(int l) {
		try {
			Thread.sleep(l);
		}catch(InterruptedException e) {
			
		}
	}
	
	private void snakeGame() {
		Snake_game snakeGame = new Snake_game(10, 10, this);
		snakeGame.run();
	}
	
	private void snakeAutoplay() {
		Snake_Autoplay sap = new Snake_Autoplay(this);
		sap.runAutoPlay();
	}
	
	private void sortSim() {
		Main sortSim = new Main(this);
		sortSim.run();
	}
	
	public void reEnter() {
		mode = 0;
		setVisible(true);
		this.runMainBackground();
		revalidate(); repaint();
	}
	
	public static void main(String[] args) {
		new MainHubFrame();
	}

}
