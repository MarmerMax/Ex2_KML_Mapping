package GameGUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import Geom.Point3D;

public class Fruit {

	private final char type = 'F';
	private int id;
	private Point3D point;
	private double speed;
	private BufferedImage fruitImage;
	
	private int idCount = 0;
	

	//private BufferedImage fruitImage;

		public Fruit(int x, int y) {
			this.id = idCount++;
			double [] xyCoordinate = fromPixelToLatLon(x, y);
			this.point = new Point3D(xyCoordinate[0], xyCoordinate[1], 0);
			this.speed = 1;
			createImage();
		}

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
	
	public double[] fromPixelToLatLon(int x, int y) {
		double [] xy = new double[2];
		double xStep = (35.21222222 - 35.20222222) / 1433; 
		double yStep = (32.10555556 - 32.10194444) / 642;
		xy[0] = 32.10555556 - (yStep * y);
		xy[1] = 35.20222222 + (xStep * x);
		return xy;
	}

}
