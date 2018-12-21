package GameGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

import Geom.GeomElement;
import Geom.Point3D;

public class Pacman {


	private final char type = 'P';
	private int id;
	private Point3D point;
	private double speed;
	private double radius;
	private int idCount = 0;
	//private int nearFruitId;

	//private BufferedImage pacmanImage;

	public Pacman(int x, int y) {
		this.id = idCount++;
		double [] xyCoordinate = fromPixelToLatLon(x, y);
		this.point = new Point3D(xyCoordinate[0], xyCoordinate[1], 0);
		this.speed = 1;
		this.radius = 1;
	}

	public Pacman() {

	}

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
	
	public double[] fromPixelToLatLon(int x, int y) {
		double [] xy = new double[2];
		double xStep = (35.212479 - 35.202340) / 1433; 
		double yStep = (32.105739 - 32.101852) / 642;
		xy[0] = 32.105739 - (yStep * y);
		xy[1] = 35.202340 + (xStep * x);
		return xy;
	}

//	public void setNearFruitId(int id) {
//		this.nearFruitId = id;
//	}
//
//	public int getNearFruitId() {
//		return nearFruitId;
//	}
	
	public void setSpeed(double sped) {
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
