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

public class MyFrame extends JFrame implements MouseListener{


	public BufferedImage background;

	private LinkedList<Pacman> pacmanList;
	private LinkedList<Fruit> fruitList;
	private LinkedList<Path> pathList;
	private Game game = null;

	private int height, width, startHeight, startWidth;
	private double heightPercent, widthPercent;

	private ExecutorService pool;
	private int repaint = 0;
	private boolean newGame = false;
	private int x, y;

	private char figure = 'N';


	public MyFrame() {
		try {
			createGUI();
		} 
		catch (IOException e) {

		}
		this.addMouseListener(this);
	}

	private void createGUI() throws IOException {

		background = new Map().getMap();
		heightPercent = widthPercent = 1;
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
				pacmanList = null;
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
				String fileName = readFileDialog();
				game = new Game();
				game.readFileDialog(fileName);
				pacmanList = game.getPacmanList();
				fruitList = game.getFruitList();
				ShortestPathAlgo pathes = new ShortestPathAlgo(game);
				pathList = pathes.getPathList();

				repaint();
			}
		});

		fileItemSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(game != null) {
					String fileName = writeFileDialog();
					game.writeFileDialog(fileName);
				}
				else {
					System.out.println("Can't save empty game!");
				}
			}
		});

		fileItemSaveToKml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});

		////////////////   create menu   //////////////////////
		createItemPacman.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(newGame) {
					figure = 'P';
				}
				else {
					System.out.println("You need to create new game!");
				}
			}
		});

		createItemFruit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(newGame) {
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
				if(newGame) {
					if(pacmanList.size() > 0 && fruitList.size() > 0) {
						ShortestPathAlgo pathes = new ShortestPathAlgo(game);
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
			public void actionPerformed(ActionEvent e) {
				if(pacmanList != null && fruitList != null && pathList != null && pacmanList.size() > 0 && fruitList.size() > 0) {
					pool = Executors.newFixedThreadPool(pathList.size());
					for(int i = 0; i < pathList.size(); i++) {
						PacmanRunner temp = new PacmanRunner(i);
						pool.execute(temp);
					}
					
					pool.shutdown();
				}
			}
		});

		gameItemClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pathList = null;
				fruitList = null;
				pacmanList = null;
				game = null;
				repaint();
			}
		});


		//////////////////////////window frame///////////////////////
		getContentPane().addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				Component c = (Component)e.getComponent();
				width = c.getWidth();
				height = c.getHeight();
				windowResize();
				repaint();
			}
		});	
	}

	public int getIndexOfThisId(int id) {
		int index;
		for(index = 0; index < fruitList.size(); index++) {
			if(fruitList.get(index).getId() == id) {
				return index;
			}
		}
		return 0;
	}

	public void windowResize() {
		widthPercent = (double)width / startWidth;
		heightPercent = (double)height / startHeight;
	}

	public void paint(Graphics g){
		switch(repaint){
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

	public void drawPacman(Graphics g) {
		int index = 0;
		while(index < pacmanList.size()) {
			Pacman temp = pacmanList.get(index);
			int [] coor = getXYfromLatLon(temp.getCoordinates().x(), temp.getCoordinates().y());
			int xP = (int)((double)coor[0] * widthPercent);
			int yP = (int)((double)coor[1] * heightPercent);
			int dX = (int)(20.0 * widthPercent);
			int dY = (int)(20.0 * heightPercent);
			int imgSize = (int)(40.0 * ((widthPercent + heightPercent) / 2));
			g.drawImage(pacmanList.get(index).getPacmanImage(), xP - dX, 50 + yP - dY, imgSize, imgSize, this);

			index++;
		}
	}

	public void drawFruit(Graphics g) {
		int index = 0;
		while(index < fruitList.size()) {
			Fruit temp = fruitList.get(index);
			int [] coor = getXYfromLatLon(temp.getCoordinates().x(), temp.getCoordinates().y());
			int xP = (int)((double)coor[0] * widthPercent);
			int yP = (int)((double)coor[1] * heightPercent);
			int dX = (int)(15.0 * widthPercent);
			int dY = (int)(15.0 * heightPercent);
			int imgSize = (int)(30.0 * ((widthPercent + heightPercent) / 2));
			g.drawImage(fruitList.get(index).getFruitImage(), xP - dX, 50 + yP - dY, imgSize, imgSize, this);
			index++;
		}
	}


	public void drawPath(Graphics g) {
		for(int i = 0; i < pathList.size(); i++) {
			for(int j = 0; j < pathList.get(i).getPathSize() - 1; j++) {
				Point3D temp = pathList.get(i).getPointList().get(j);
				Point3D temp2 = pathList.get(i).getPointList().get(j + 1);
				double x1 = temp.x();
				double y1 = temp.y();
				double x2 = temp2.x();
				double y2 = temp2.y();
				int [] coor1 = getXYfromLatLon(x1, y1);
				int [] coor2 = getXYfromLatLon(x2, y2);

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

	@Override
	public void mouseClicked(MouseEvent arg) {
		if(newGame) {
			x = arg.getX();
			y = arg.getY();
			if(figure == 'N') {
				System.out.println("Please choose type of your figure!");
			}
			else if (figure == 'P') {
				try {
					String speedInput = JOptionPane.showInputDialog(null, "Choose speed");
					double tempSpeed = Double.parseDouble(speedInput);
					if(tempSpeed > 0) {
						int xP = (int)((double)x * ((1 + (1 - widthPercent)))); //change x to actually size
						int yP = (int)((double)(y - 50) * ((1 + (1 - heightPercent)))); //change y to actually size
						Pacman temp = new Pacman(xP, yP, pacmanList.size());
						temp.setSpeed(tempSpeed);
						pacmanList.add(temp);
					}
					else {
						System.out.println("Wrong speed!");
					}
				}catch(Exception exp) {
					System.err.println("Speed input failed. Wrong type input!");
				}
				
			}
			else if (figure == 'F'){
				int xP = (int)((double)x * ((1 + (1 - widthPercent)))); //change x to actually size
				int yP = (int)((double)(y - 50) * ((1 + (1 - heightPercent)))); //change y to actually size
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
	
	class PacmanRunner implements Runnable{
		
		private int pathIndex;
		
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
					fruitList.get(indexNextFruit).removeFruitImage();
				}
				index++;
			}
		}
	}
	
}

