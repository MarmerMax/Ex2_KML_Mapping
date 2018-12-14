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
		this.addMouseListener(this); 
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
				Game temp = new Game();
				temp.readFileDialog(fileName);
				pacmanList = temp.getPacmanList();
				fruitList = temp.getFruitList();
				repaint();
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

		//		gameItemClear.addActionListener(new ActionListener() {
		//			@Override
		//			public void actionPerformed(ActionEvent e) {
		//				speed = 1;
		//				figure = 'P';
		//				x = y = 0;
		//			}
		//		});

		fileItemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});





	}
	//35.20222222	35.21222222 
	//32.10555556	32.10194444
	private int Lat2X(Double lat) {
		double px2deg = 143300;
		double tempLat = 35.21222222 - lat;
		return (int)(px2deg * tempLat);
	}

	private int Lon2Y(Double lon) {
		double px2deg = 172241.914;
		double tempLon = 32.10194444 - lon;
		return (int)(px2deg * tempLon);
	}
	
	public int[] getXYfromLatLon(double latitude, double longitude) {
		// get x value
		int mapWidth = 1433, mapHeight = 622;
		int pX = (int)((longitude+180.)*(mapWidth/360.));

		// convert from degrees to radians
		double latRad = latitude*Math.PI/180.;

		// get y value
		double mercN = Math.log(Math.tan((Math.PI/4.)+(latRad/2.)));
		int pY = (int)((mapHeight/2.)-(mapWidth*mercN/(2.*Math.PI)));
		System.out.println("x = "+pX+", y = "+pY);
		int [] c = new int[2];
		c[0] = pX;
		c[1] = pY;
		return c;
	}

	public void paint(Graphics g){
		g.drawImage(background, 0, 0, this);
		if(pacmanList != null) {
			Iterator<Pacman> it1 = pacmanList.iterator();
			int index = 0;
			while(it1.hasNext()) {
				Pacman temp = it1.next();
				int imageSize = 50;
				int [] c = new int[2]; 
				c =	getXYfromLatLon(temp.getCoordinates().x(), temp.getCoordinates().y());
//				int pxX = Lat2X(temp.getCoordinates().y());
//				int pxY = Lon2Y(temp.getCoordinates().x());
				g.drawImage(temp.getPacmanImage(), c[0] - (imageSize / 2), c[1] - (imageSize / 2), imageSize, imageSize, this);
				index++;
			}
		}
		if(fruitList != null) {
//			Iterator<Fruit> it2 = fruitList.iterator();
//			while(it2.hasNext()) {
//				Fruit temp = it2.next();
//				int imageSize = 30;
//				int [] c = new int[2]; 
//				c = getXYfromLatLon(temp.getCoordinates().x(), temp.getCoordinates().y());
//				int pxX = c[0];
//				int pxY = c[1];
//				g.drawImage(temp.getFruitImage(), pxX - (imageSize / 2), pxY - (imageSize / 2), imageSize, imageSize, this);
//			}
//			int a = 0;
//			while(a < 3) {
//				x = 50;
//				y = 100;
//				g.drawImage(fruitList.get(0).getFruitImage(),x - (30 / 2), y - (30 / 2), 30, 30, this);
//				g.drawImage(fruitList.get(0).getFruitImage(),x - (30 / 2), y - (30 / 2), 30, 30, this);
//				x += 100;
//				a++;
//			}
			//System.out.println(a);
			x = 50;
			y = 100;
			g.drawImage(fruitList.get(0).getFruitImage(),x - (30 / 2), y - (30 / 2), 30, 30, this);
			g.drawImage(fruitList.get(0).getFruitImage(),x - (30 / 2) + 50, y - (30 / 2), 30, 30, this);
			g.drawImage(fruitList.get(0).getFruitImage(),x - (30 / 2) + 100, y - (30 / 2), 30, 30, this);
		}

		//		if(x!=-1 && y!=-1){
		//			BufferedImage image = null;
		//			int imageSize;
		//			
		//			switch (figure) {
		//			case 'P':
		//				image = new Pacman(x, y).getPacman();
		//				imageSize = 50;
		//				break;
		//			case 'F': 
		//				image = new Fruit(x, y).getFruit();
		//				imageSize = 30;
		//				break;
		//			default:
		//				imageSize = 20;
		//				break;
		//			}
		//			
		//			g.drawImage(image, x - (imageSize / 2), y - (imageSize / 2), imageSize, imageSize, this);
		//		}
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
