package GameGUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Geom.Point3D;

/**
 * This class used for initialization fruit by data on map.
 * @author Max Marmer
 *
 */
public class Fruit {

	private final char type = 'F';
	private int id;
	private Point3D point;
	private double speed;
	private BufferedImage fruitImage;

	/**
	 * Fruit constructor by click on the map.
	 * @param x
	 * @param y
	 * @param id 
	 */
	public Fruit(int x, int y, int id) {
		this.id = id;
		double [] xyCoordinate = new Map().fromPixelToLatLon(x, y);
		this.point = new Point3D(xyCoordinate[0], xyCoordinate[1], 0);
		this.speed = 1;
		createImage();
	}

	/**
	 * Fruit constructor from csv file.
	 * @param values
	 */
	public Fruit(String [] values) {
		try {
			id = Integer.parseInt(values[1]);
			double x = Double.parseDouble(values[2]);
			double y = Double.parseDouble(values[3]);
			double z = Double.parseDouble(values[4]);
			createImage();
			point = new Point3D(x, y, z);
			speed = Double.parseDouble(values[5]);
		}catch(Exception e) {
			System.err.println("Fruit create failed. Incorect data!");
		}	
	}

	public int getId() {
		return id;
	}

	public Point3D getCoordinates() {
		return point;
	}

	public double getSpeed() {
		return speed;
	}

	public char getType() {
		return type;
	}
	
	/**
	 * Method to create image for this fruit.
	 */
	public void createImage() {
		fruitImage = null;
		try {
			fruitImage = ImageIO.read(new File("data\\fruit.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getFruitImage() {
		return fruitImage;
	}

	public void removeFruitImage() {
		fruitImage = null;
	}
}
