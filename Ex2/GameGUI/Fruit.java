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
	private Point3D coor;
	private double speed;

	//private BufferedImage fruitImage;

	//	public Fruit(int x, int y) {
	//		coor = new int[2];
	//		coor[0] = x;
	//		coor[1] = y;
	//		try {
	//			fruit = ImageIO.read(new File("data\\fruit.png"));
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//	}

	public Fruit(String [] values) {
		try {
			id = Integer.parseInt(values[1]);
			double x = Double.parseDouble(values[2]);
			double y = Double.parseDouble(values[3]);
			double z = Double.parseDouble(values[4]);
			coor = new Point3D(x, y, z);
			speed = Double.parseDouble(values[5]);
		}catch(Exception e) {
			System.err.println("Fruit create failed. Incorect data!");
		}	
	}

	public int getId() {
		return id;
	}

	public Point3D getCoordinates() {
		return coor;
	}

	public double getSpeed() {
		return speed;
	}

	public char getType() {
		return type;
	}

	public BufferedImage getFruitImage() {
		BufferedImage fruitImage = null;
		try {
			double r = Math.random();
			if(r < 0.5) {
				fruitImage = ImageIO.read(new File("data\\fruit.png"));
			}
			else {
				fruitImage = ImageIO.read(new File("data\\fruit2.png"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fruitImage;
	}

}
