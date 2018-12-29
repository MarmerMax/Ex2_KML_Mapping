package GameGUI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import Coords.MyCoords;
import Geom.Point3D;

/**
 * This class create path from pacman to fruits.
 * @author Max Marmer
 *
 */
public class Path {

	private LinkedList<Point3D> pointList;
	private LinkedList<Integer> idList;
	private int pathSize;
	private double pathLength;
	private double speed;
	private double radiusPacman;
	private LinkedList<String> timestampList;

	/**
	 * This construction function of path
	 * @param pacman pacman that will be the first in path
	 */
	public Path(Pacman pacman) {
		this.speed = pacman.getSpeed();//speed of path  it's speed of pacman
		this.radiusPacman = pacman.getRadius();//pacman radius
		this.pathLength = 0;
		this.pathSize = 1;
		this.idList = new LinkedList<>();
		this.pointList = new LinkedList<>();
		this.timestampList = new LinkedList<>();
		idList.add(pacman.getId());//add pacman id to list
		pointList.add(pacman.getCoordinates());//add pacman coordiantes to path 
		timestampList.add(createTimestamp());//create him time stamp
	}
	
	/**
	 * Function to add fruit in path
	 * @param fruit fruit that need to add
	 */
	public void add(Fruit fruit) {
		pathSize++;
		idList.add(fruit.getId());
		pointList.add(fruit.getCoordinates());
		timestampList.add(createTimestamp());
		countLength();
	}
	
	/**
	 * Check length of path
	 */
	private void countLength() {
		int index = 0;
		while(index < pointList.size() - 1) {
			double tempDist = new MyCoords().distance3d(pointList.get(index), pointList.get(index + 1));
			pathLength += tempDist;
			index++;
		}
	}
	
	/**
	 * Create time stamp for point
	 * @return
	 */
	private String createTimestamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String date = sdf.format(new Date());
		return date;
	}
	
	public double getPacmanRadius() {
		return radiusPacman;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public LinkedList<String> getTimeStamp() {
		return timestampList;
	}

	public LinkedList<Point3D> getPointList(){
		return pointList;
	}

	public int getPathSize() {
		return pathSize;
	}

	public double getPathLength() {
		return pathLength;
	}
	
	public LinkedList<Integer> getIdList(){
		return idList;
	}
}