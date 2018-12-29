package GameGUI;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Geom.Point3D;

/**
 * This class used for initialization pacman by data on map.
 * @author Max Marmer
 *
 */
public class Pacman {


	private final char type = 'P';
	private int id;
	private Point3D point;
	private double speed;
	private double radius;


	/**
	 * Constructor function for create pacman by click.
	 * @param x lat
	 * @param y lon
	 * @param id id
	 */ 
	public Pacman(int x, int y, int id) {
		this.id = id;
		double [] xyCoordinate = new Map().fromPixelToLatLon(x, y);
		this.point = new Point3D(xyCoordinate[0], xyCoordinate[1], 0);
		this.speed = 1;
		this.radius = 1;
	}
	
	/**
	 * Constructor function for create pacman from csv data.
	 * @param values
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
	 * This method create image for pacman and return him.
	 * @return
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
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getId() {
		return id;
	}

	public Point3D getCoordinates() {
		return point;
	}

	public double getRadius() {
		return radius;
	}

	public double getSpeed() {
		return speed;
	}
	
	public char getType() {
		return type;
	}
}
