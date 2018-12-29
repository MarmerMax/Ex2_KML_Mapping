package GameGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


import Coords.MyCoords;
import Geom.Point3D;

/**
 * This class need for GUI initialization. This class include resize function that change size of 
 * window adaptive for user. 
 * @author Max Marmer
 *
 */
public class MyFrame extends JFrame implements MouseListener{

	public BufferedImage background;//background image

	private LinkedList<Pacman> pacmanList;//list of pacman
	private LinkedList<Fruit> fruitList;//list of fruit
	private LinkedList<Path> pathList;//list of path
	private Game game = null;

	private int height, width, startHeight, startWidth;//sizes
	private double heightPercent, widthPercent;//size in percent for adaptive changes

	private int repaint = 0;//what need to repaint
	private boolean newGame = false;//if we create new game
	private int x, y;//x, y by click

	private char figure = 'N';//which figure to create

	/**
	 * Construction function of class
	 */
	public MyFrame() {
		try {
			createGUI();
		} 
		catch (IOException e) {

		}
		this.addMouseListener(this);
	}

	/**
	 * In this class we create menu bar with buttons and give them event by click.
	 * @throws IOException
	 */
	private void createGUI() throws IOException {

		background = new Map().getMap();//create background
		heightPercent = widthPercent = 1;//start percent 100%
		startHeight = background.getHeight();
		startWidth = background.getWidth();
		height = startHeight;
		width = startWidth;
		


		setVisible(true);
		setSize(background.getWidth(),background.getHeight());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		MenuBar menuBar = new MenuBar();
		//file menu 
		Menu file = new Menu("File");
		MenuItem fileItemNewGame = new MenuItem("New game");
		MenuItem fileItemOpen = new MenuItem("Open");
		MenuItem fileItemSave = new MenuItem("Save");
		MenuItem fileItemSaveToKml = new MenuItem("Path to KML");
		MenuItem fileItemExit = new MenuItem("Exit");
		file.add(fileItemNewGame);
		file.add(fileItemOpen);
		file.add(fileItemSave);
		file.add(fileItemSaveToKml);
		file.add(fileItemExit);
		menuBar.add(file);

		//game menu
		Menu create = new Menu("Create");
		MenuItem createItemPacman = new MenuItem("Pacman");
		MenuItem createItemFruit = new MenuItem("Fruit");
		MenuItem createItemPath = new MenuItem("Build Path");
		create.add(createItemPacman);
		create.add(createItemFruit);
		create.add(createItemPath);
		menuBar.add(create);

		//start menu
		Menu game1 = new Menu("Game");
		MenuItem gameItemStart = new MenuItem("Start");
		MenuItem gameItemClear = new MenuItem("Clear");
		game1.add(gameItemStart);
		game1.add(gameItemClear);
		menuBar.add(game1);
		this.setMenuBar(menuBar);

		/////////////////   file menu   //////////////////////
		fileItemNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pacmanList = null; //remove all exists lists
				fruitList = null;
				pathList = null;
				repaint(); //repaint before after remove all objects
				repaint = 0; //paint all objects
				newGame = true; //flag to create new objects
				game = new Game();
				pacmanList = game.getPacmanList();
				fruitList = game.getFruitList();
			}
		});

		fileItemOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint = 0;
				newGame = false;
				String fileName = readFileDialog();//read file name
				game = new Game();
				game.readFileDialog(fileName);//create new game from file
				pacmanList = game.getPacmanList();
				fruitList = game.getFruitList();
				ShortestPathAlgo pathes = new ShortestPathAlgo(game);//create paths
				pathList = pathes.getPathList();

				repaint();
			}
		});

		fileItemSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {//save to csv
				if(game != null) {
					String fileName = writeFileDialog();
					game.writeFileDialog(fileName);//create file csv with name
				}
				else {
					System.out.println("Can't save empty game!");
				}
			}
		});

		fileItemSaveToKml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {//save paths to kml
				if(game != null) {
					String fileName = writeFileKML();
					game.writeFileKML(fileName, pathList);
				}
				else {
					System.out.println("Can't save empty game!");
				}
			}
		});

		fileItemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {//close project
				System.exit(0);
			}
		});

		////////////////   create menu   //////////////////////
		createItemPacman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(newGame) {//if we create new game we can choose create pacman
					figure = 'P';
				}
				else {
					System.out.println("You need to create new game!");
				}
			}
		});

		createItemFruit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(newGame) {//if we create new game we can choose create fruit
					figure = 'F';
				}
				else {
					System.out.println("You need to create new game!");
				}
			}
		});

		createItemPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(newGame) {//if we have new game so we can continue
					if(pacmanList.size() > 0 && fruitList.size() > 0) {//if them not null so we can check path
						ShortestPathAlgo pathes = new ShortestPathAlgo(game);//create paths
						pathList = pathes.getPathList();

						repaint();
					}
					else {
						System.out.println("Can't build path. Need at least one pacman and one fruit!");
					}
				}
				else {
					System.out.println("Can't build path. Need to create new game!");
				}
			}
		});

		/////////////////////game menu//////////////////////////////
		gameItemStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {//start game
				ExecutorService pool = Executors.newFixedThreadPool(pathList.size());//create thread pool
				for(int i = 0; i < pathList.size(); i++) {
					PacmanRunner temp = new PacmanRunner(i);//for every pacman we create thread
					pool.execute(temp);//add thread to executor
				}
				
				pool.shutdown();
			}
		});

		gameItemClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {//remove all and repaint
				pathList = null;
				fruitList = null;
				pacmanList = null;
				game = null;
				repaint();
			}
		});


		//////////////////////////window frame///////////////////////
		getContentPane().addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {//window listener
				Component c = (Component)e.getComponent();
				width = c.getWidth();
				height = c.getHeight();
				windowResize();//resize all by actual situation
				repaint();
			}
		});	
	}

	/**
	 * Check the width and height in percent
	 */
	public void windowResize() {
		widthPercent = (double)(width * 1.0 / (startWidth * 1.0));
		heightPercent = (double)(height * 1.0 / (startHeight * 1.0));
	}

	/**
	 * This function search index of needed id in idList
	 * @param id id to search
	 * @return index of this id
	 */
	public int getIndexOfThisId(int id) {
		int index;
		for(index = 0; index < fruitList.size(); index++) {
			if(fruitList.get(index).getId() == id) {
				return index;
			}
		}
		return 0;
	}

	/**
	 * Paint all objects on GUI window 
	 */
	public void paint(Graphics g){
		switch(repaint){ // we have other type of paint
		case 0: //repaint all
			g.drawImage(background, 0, 50, width, height + 50, this);
			if(pacmanList != null) {
				drawPacman(g);
			}
			if(fruitList != null) {
				drawFruit(g);
			}
			if(pathList != null) {
				drawPath(g);  
			}
			break;
		case 1: //repaint only pacman
			g.drawImage(background, 0, 50, width, height + 50, this);
			if(pacmanList != null) {
				drawPacman(g);
			}
			break;
		case 2: //repaint pacmans and fruit
			g.drawImage(background, 0, 50, width, height + 50, this);
			if(pacmanList != null) {
				drawPacman(g);
			}
			if(fruitList != null) {
				drawFruit(g);
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * Draw pacman in GUI window
	 * @param g
	 */
	public void drawPacman(Graphics g) {
		int index = 0;
		while(index < pacmanList.size()) {//run on list of pacman and draw every one
			Pacman temp = pacmanList.get(index);
			int [] coor = fromLatLonToPixel(temp.getCoordinates().x(), temp.getCoordinates().y());
			int xP = (int)((double)coor[0] * widthPercent);
			int yP = (int)((double)coor[1] * heightPercent);
			int dX = (int)(20.0 * widthPercent);
			int dY = (int)(20.0 * heightPercent);
			int imgSize = (int)(40.0 * ((widthPercent + heightPercent) / 2));
			g.drawImage(pacmanList.get(index).getPacmanImage(), xP - dX, 50 + yP - dY, imgSize, imgSize, this);

			index++;
		}
	}

	/**
	 * Draw fruit in GUI window
	 * @param g
	 */
	public void drawFruit(Graphics g) {
		int index = 0;
		while(index < fruitList.size()) {
			Fruit temp = fruitList.get(index);
			int [] coor = fromLatLonToPixel(temp.getCoordinates().x(), temp.getCoordinates().y());
			int xP = (int)((double)coor[0] * widthPercent);
			int yP = (int)((double)coor[1] * heightPercent);
			int dX = (int)(15.0 * widthPercent);
			int dY = (int)(15.0 * heightPercent);
			int imgSize = (int)(30.0 * ((widthPercent + heightPercent) / 2));
			g.drawImage(fruitList.get(index).getFruitImage(), xP - dX, 50 + yP - dY, imgSize, imgSize, this);
			index++;
		}
	}


	/**
	 * Draw path in GUI window
	 * @param g
	 */
	public void drawPath(Graphics g) {
		for(int i = 0; i < pathList.size(); i++) {
			for(int j = 0; j < pathList.get(i).getPathSize() - 1; j++) {
				
				Point3D temp = pathList.get(i).getPointList().get(j);
				Point3D temp2 = pathList.get(i).getPointList().get(j + 1);
				double x1 = temp.x();
				double y1 = temp.y();
				double x2 = temp2.x();
				double y2 = temp2.y();
				int [] coor1 = fromLatLonToPixel(x1, y1);
				int [] coor2 = fromLatLonToPixel(x2, y2);

				int xP1 = (int)((double)coor1[0] * widthPercent);
				int yP1 = (int)((double)coor1[1] * heightPercent);

				int xP2 = (int)((double)coor2[0] * widthPercent);
				int yP2 = (int)((double)coor2[1] * heightPercent);

				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(3));
				g2.setColor(Color.white);
				g2.drawLine(xP1, 50 + yP1, xP2, 50 + yP2);
			}
		}
	}

	/**
	 * Open window for choose file
	 * @return file name to open
	 */
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

	/**
	 * Open window to choose path of save file and choose name
	 * @return
	 */
	public String writeFileDialog() {
		//try write to the file
		FileDialog fd = new FileDialog(this, "Save the text file", FileDialog.SAVE);
		fd.setFile("*.csv");
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

	/**
	 * Open window to choose name of KML file
	 * @return
	 */
	public String writeFileKML() {
		//try write to the file
		FileDialog fd = new FileDialog(this, "Save the kml file", FileDialog.SAVE);
		fd.setFile("*.kml");
		fd.setFilenameFilter(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".kml");
			}
		});
		fd.setVisible(true);
		String folder = fd.getDirectory();
		String fileName = fd.getFile();
		return folder + fileName;
	}

	/**
	 * Create object by mouse click
	 */
	@Override
	public void mouseClicked(MouseEvent arg) {
		if(newGame) {
			x = arg.getX();
			y = arg.getY();
			if(figure == 'N') {//we need to choose type of object to create 
				System.out.println("Please choose type of your figure!");
			}
			else if (figure == 'P') {//create pacman
//				int xP = (int)((double)x * ((1 + (1 - widthPercent)))); //change x to actually size
//				int yP = (int)((double)(y - 50) * ((1 + (1 - heightPercent)))); //change y to actually size
				int xP = (int)((double)x * (Math.pow(widthPercent, -1))); //change x to actually size
				int yP = (int)((double)(y - 50) * (Math.pow(heightPercent, -1))); //change y to actually size
				Pacman temp = new Pacman(xP, yP, pacmanList.size());
				try {
					String speedInput = JOptionPane.showInputDialog(null, "Choose speed");//choose speed
					temp.setSpeed(Double.parseDouble(speedInput));
				}catch(Exception exp) {
					//if choose speed failed so speed by default equals to 1
					System.err.println("Speed input failed. Wrong type input!");
				}
				pacmanList.add(temp);
			}
			else if (figure == 'F'){//create fruit
				int xP = (int)((double)x * (Math.pow(widthPercent, -1))); //change x to actually size
				int yP = (int)((double)(y - 50) * (Math.pow(heightPercent, -1))); //change y to actually size
				Fruit temp = new Fruit(xP, yP, fruitList.size());
				fruitList.add(temp);
			}			

			repaint();
		}
		else {
			System.out.println("You need to create new game!");
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}


	//35.20222222	35.21222222 
	//32.10555556	32.10194444

	/**
	 * From latitude and longitude to pixels
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public int[] fromLatLonToPixel(double latitude, double longitude) {
		double mapHeight = 642;
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
	
	/**
	 * This class of Pacman Thread that implements runnable. Find length between pacman to next 
	 * point and makes need's step.
	 * @author Max Marmer
	 *
	 */
	class PacmanRunner implements Runnable{
		
		private int pathIndex;//index of path
		
		public PacmanRunner(int index) {
			this.pathIndex = index;
		}
		
		@Override
		public void run() {
			pacmanMooving();
		}
		
		private void pacmanMooving() {
			int index = 1;
			while(pathList.get(pathIndex).getPointList().size() > index) {

				Point3D pacmanCoordinates = pathList.get(pathIndex).getPointList().get(0);
				Point3D fruitCoordinates = pathList.get(pathIndex).getPointList().get(index);
				
				//save index of next fruit
				int indexNextFruit = getIndexOfThisId(pathList.get(pathIndex).getIdList().get(index));

				MyCoords pacToFruit = new MyCoords();
				double dist = pacToFruit.distance3d(fruitCoordinates, pacmanCoordinates);
				double steps = dist / pathList.get(pathIndex).getSpeed();//count steps
				int countSteps = (int)steps;

				double dX = (fruitCoordinates.x() - pacmanCoordinates.x()) / steps;
				double dY = (fruitCoordinates.y() - pacmanCoordinates.y()) / steps;

				while(countSteps > 0) {
					repaint = 2;
					pacmanCoordinates.addXYZ(dX, dY, 0);
					countSteps--;
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					repaint();
				}
				if(countSteps == 0) {
					fruitList.get(indexNextFruit).removeFruitImage();//remove image from GUI
				}
				index++;
			}
		}
	}
}

