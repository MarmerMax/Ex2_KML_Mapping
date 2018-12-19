package GameGUI;

import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import Geom.Point3D;

public class MyFrame extends JFrame implements MouseListener{

	public BufferedImage background;
	private LinkedList<Pacman> pacmanList = null;
	private LinkedList<Fruit> fruitList = null;
	private int x, y;

	private char figure = 'P';
	private double speed = 1;


	public MyFrame() {
		try {
			createGUI();
		} 
		catch (IOException e) {

		}
		//this.addMouseListener(this);
	}

	private void createGUI() throws IOException {

		MenuBar menuBar = new MenuBar();
		//file menu 
		Menu file = new Menu("File");
		MenuItem fileItemNewGame = new MenuItem("New game");
		MenuItem fileItemOpen = new MenuItem("Open");
		MenuItem fileItemSave = new MenuItem("Save");
		MenuItem fileItemExit = new MenuItem("Exit");
		file.add(fileItemNewGame);
		file.add(fileItemOpen);
		file.add(fileItemSave);
		file.add(fileItemExit);
		menuBar.add(file);

		//game menu
		Menu game = new Menu("Game");
		MenuItem gameItemStart = new MenuItem("Start");
		MenuItem gameItemPacman = new MenuItem("Add Pacman");
		MenuItem gameItemFruit = new MenuItem("Add Fruit");
		MenuItem gameItemSetSpeed = new MenuItem("Set speed");
		MenuItem gameItemClear = new MenuItem("Clear");	
		game.add(gameItemStart);
		game.add(gameItemPacman);
		game.add(gameItemFruit);
		game.add(gameItemSetSpeed);
		game.add(gameItemClear);
		menuBar.add(game);

		this.setMenuBar(menuBar);


		background = new Map().getMap();
		/////////////////   file menu   //////////////////////
		fileItemOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String fileName = readFileDialog();
				Game game = new Game();
				game.readFileDialog(fileName);
				pacmanList = game.getPacmanList();
				fruitList = game.getFruitList();
				game.createPath();
				repaint();
				//ShortestPathAlgo path = new ShortestPathAlgo(game);
			}
		});

		////////////////   game menu   //////////////////////
		gameItemPacman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				figure = 'P';
			}
		});
		gameItemFruit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				figure = 'F';
			}
		});

		gameItemSetSpeed.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String speedInput = JOptionPane.showInputDialog(null, "Choose speed");
				try {
					speed = Double.parseDouble(speedInput);
				}catch(Exception exp) {
					System.err.println("Speed input failed. Wrong type input!");
				}
			}
		});

		fileItemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});
	}
	//35.20222222	35.21222222 
	//32.10555556	32.10194444
	
	public int[] getXYfromLatLon(double latitude, double longitude) {
		double mapHeight = 622;
		double mapWidth = 1433;
		double mapLatBottom = 32.10194444;
		double mapLngLeft = 35.20222222;
		double mapLngRight = 	35.21222222;
		double mapLatBottomRad = mapLatBottom * Math.PI / 180;
		double latitudeRad = latitude * Math.PI / 180;
		double mapLngDelta = (mapLngRight - mapLngLeft);
		double worldMapWidth = ((mapWidth / mapLngDelta) * 360) / (2 * Math.PI);
		double mapOffsetY = (worldMapWidth / 2 * Math.log((1 + Math.sin(mapLatBottomRad))
							 / (1 - Math.sin(mapLatBottomRad))));
		double x = (longitude - mapLngLeft) * (mapWidth / mapLngDelta);
		double y = mapHeight - ((worldMapWidth / 2 * Math.log((1 + Math.sin(latitudeRad)) 
								/ (1 - Math.sin(latitudeRad)))) - mapOffsetY);
		int [] cc = new int[2];
		cc[0] = (int)x;
		cc[1] = (int)y;
		return cc;
	}

	public void paint(Graphics g){
		g.drawImage(background, 0, 0, this);
		if(pacmanList != null) {
			drawPacman(g);
			//repaint();
		}
		if(fruitList != null) {
			drawFruit(g);
			//repaint();
		}
	}

	public void drawPacman(Graphics g) {
		int index = 0;
		while(index < pacmanList.size()) {
			Pacman temp = pacmanList.get(index);
			int [] coor = getXYfromLatLon(temp.getCoordinates().x(), temp.getCoordinates().y());
			g.drawImage(pacmanList.get(index).getPacmanImage(), coor[0] - 20, coor[1] - 20, 40, 40, this);
			index++;
		}
	}

	public void drawFruit(Graphics g) {
		int index = 0;
		while(index < fruitList.size()) {
			Fruit temp = fruitList.get(index);
			int [] coor = getXYfromLatLon(temp.getCoordinates().x(), temp.getCoordinates().y());
			g.drawImage(fruitList.get(index).getFruitImage(), coor[0] - 15, coor[1] - 15, 30, 30, this);
			index++;
		}
	}
	
	public void drawPath() {
		
	}



	public String readFileDialog() {
		//try read from the file
		FileDialog fd = new FileDialog(this, "Open text file", FileDialog.LOAD);
		fd.setFile("*.csv");
		fd.setDirectory("data\\");
		fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".csv");
			}
		});
		fd.setVisible(true);
		String folder = fd.getDirectory();
		String fileName = fd.getFile();
		return folder + fileName;
	}		

	@Override
	public void mouseClicked(MouseEvent arg) {	
		System.out.println("mouse Clicked");
		System.out.println("("+ arg.getX() + "," + arg.getY() + "," + figure  + ")");
		x = arg.getX();
		y = arg.getY();
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}


}
