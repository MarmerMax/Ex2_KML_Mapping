package GameGUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Geom.Point3D;

/**
 * This class includes all data of pacman that we need to run our project.
 * @author Max Marmer
 */
public class Pacman {


	private final char type = 'P';
	private int id;
	private Point3D point;
	private double speed;
	private double radius;


	/**
	 * This constructor method we use when we create pacman by click on window in new game.
	 * @param x latitude
	 * @param y longitude
	 * @param id id in future csv file
	 */
	public Pacman(int x, int y, int id) {
		this.id = id;
		double [] xyCoordinate = fromPixelToLatLon(x, y); //call to function that converts from x,y window to lat/long
		this.point = new Point3D(xyCoordinate[0], xyCoordinate[1], 0);
		this.speed = 1;
		this.radius = 1;
	}
	
	/**
	 * This constructor method we use when we create game by reading from csv file. 
	 * @param values array of data from csv file that include all parameters for actual pacman.
	 */
	public Pacman(String [] values) {
		try {
			id = Integer.parseInt(values[1]);
			double x = Double.parseDouble(values[2]);
			double y = Double.parseDouble(values[3]);
			double z = Double.parseDouble(values[4]);
			point = new Point3D(x, y, z);
			speed = Double.parseDouble(values[5]);
			radius = Double.parseDouble(values[6]);
		}catch(Exception e) {
			System.err.println("Pacman create failed. Incorect data!");
		}	
	}
	
	/**
	 * This function convert x, y of window pixels to lat/long degrees.
	 * @param x count of pixels in X axis
	 * @param y count of pixels in Y axis
	 * @return array of lat/long in degrees
	 */
	public double[] fromPixelToLatLon(int x, int y) {
		double [] xy = new double[2];
		double xStep = (35.212479 - 35.202340) / 1433; 
		double yStep = (32.105739 - 32.101852) / 642;
		xy[0] = 32.105739 - (yStep * y);
		xy[1] = 35.202340 + (xStep * x);
		return xy;
	}
	
	/**
	 * Set speed for pacman. We use it when we create pacman by click in new game.
	 * @param speed speed that user input
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	/**
	 * Get id of this pacman.
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get Point3D coordinates of this pacman.
	 * @return Point3D
	 */
	public Point3D getCoordinates() {
		return point;
	}

	/**
	 * Get radius value of this pacman.
	 * @return radius value
	 */
	public double getRadius() {
		return radius;
	}
	
	/**
	 * Get speed of this pacman.
	 * @return speed value
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Get type of this pacman.
	 * @return char 'P' 
	 */
	public char getType() {
		return type;
	}

	/**
	 * This method read pacman image from computer and return him.
	 * @return image of pacman
	 */
	public BufferedImage getPacmanImage() {
		BufferedImage pacmanImage = null;
		try {
			pacmanImage = ImageIO.read(new File("data\\pacman.png"));
		} catch (IOException e) {
			System.err.println("Pacman image create failed!!!");
		}
		return pacmanImage;
	}
}
