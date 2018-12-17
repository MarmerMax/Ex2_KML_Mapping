package GameGUI;

import java.awt.Point;
import java.util.LinkedList;

import Coords.coords_converter;
import Geom.Point3D;

public class Path {
	
	private LinkedList<Point> coords;
	private int[][] lengths;
	
	public Path(LinkedList<Fruit> fruitList) {
		coords = new LinkedList<>();
		addPointToPath(fruitList);
	}
	
	private void addPointToPath(LinkedList<Fruit> fruitList) {
		for(Fruit f: fruitList) {
			double x = f.getCoordinates().x();
			double y = f.getCoordinates().y();
			int [] xyPixels = new Map().ll2xy(x, y);
			Point temp = new Point(xyPixels[0], xyPixels[1]);
			coords.add(temp);
		}
	}
	
	private void findThePath() { //find all distances between every two points
		lengths = new int[coords.size()][coords.size()];
		for(int i = 1; i < coords.size(); i++) {
			for(int j = i + 1; j < coords.size(); j++) {
				lengths[i][j] = distance(coords.get(i), coords.get(j));
			}
		}
	}
	
	private int distance(Point a, Point b) {
		double dx = b.getX() - a.getX();
		double dy = b.getY() - a.getY();
		double res = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		return (int)res;
	}
	
	//private
	
	

}
