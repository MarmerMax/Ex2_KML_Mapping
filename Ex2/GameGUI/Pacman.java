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


	private char type = 'P';
	private int id;
	private Point3D coor;
	private double speed;
	private double radius;
	private int nearFruitId;

	//private BufferedImage pacmanImage;

	//	public Pacman(int x, int y) {
	//		
	//		//coor = new Point3D(p)
	//		try {
	//			pacman = ImageIO.read(new File("data\\pacman.png"));
	//		} catch (IOException e) {
	//		    Graphics g = null;
	//		    g.setColor(Color.yellow);
	//		    g.fillOval(x-25, y-25, 50, 50);
	//		}
	//	}
	
	public Pacman() {
		
	}

	public Pacman(String [] values) {
		try {
			id = Integer.parseInt(values[1]);
			double x = Double.parseDouble(values[2]);
			double y = Double.parseDouble(values[3]);
			double z = Double.parseDouble(values[4]);
			coor = new Point3D(x, y, z);
			speed = Double.parseDouble(values[5]);
			radius = Double.parseDouble(values[6]);
		}catch(Exception e) {
			System.err.println("Pacman create failed. Incorect data!");
		}	
	}
	
	public void setNearFruitId(int id) {
		this.nearFruitId = id;
	}
	
	public int getNearFruitId() {
		return nearFruitId;
	}

	public int getId() {
		return id;
	}

	public Point3D getCoordinates() {
		return coor;
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
//	public JLabel getPacmanImage() {
//		JLabel pacmanImage = null;
//		try {
//			//pacmanImage = ImageIO.read(new File("data\\pacman.png"));
//			pacmanImage = new JLabel("data\\pacman.png");
//		} catch (Exception e) {
//			System.err.println("Pacman image create failed!!!");
//		}
//		return pacmanImage;
//	}

}
