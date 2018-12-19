package GameGUI;

import java.awt.Point;
import java.util.LinkedList;

import Coords.MyCoords;
import Coords.coords_converter;
import Geom.Point3D;

public class Path {
	
	private LinkedList<Point3D> coords;
	private int length;
	//private int[][] lengths;
	
	
	public Path(Pacman pacman, LinkedList<Fruit> fruitList, int pathLength, boolean [] idOfUsedFruits) {
		coords = new LinkedList<>();
		coords.add(pacman.getCoordinates());
		length = pathLength;
		buildPath(fruitList, idOfUsedFruits);
	}
	

	private void buildPath(LinkedList<Fruit> fruitList, boolean [] idOfUsedFruits) {
		double minDist = Double.MAX_VALUE;
		int nextFruitId = -1;
		int index = -1;
		for(int i = 0; i < fruitList.size(); i++) {//find first fruit near to pacman by geom coordinates
			MyCoords dist = new MyCoords();
			double tempDist = dist.distance3d(coords.get(0), fruitList.get(i).getCoordinates());
			if(minDist > tempDist) {
				minDist = tempDist;
				index = i;
				nextFruitId = fruitList.get(i).getId();
			}
		}
		
		//because the id of fruits in csv file can be all number and not start from zero 
		//we must to find this fruit
//		int index = 0;
//		Fruit temp = fruitList.get(index);
//		while(temp.getId() != nextFruitIndex) {
//			temp = fruitList.get(index);
//			index++;
//		}
		
		
	//	coords.add(fruitList.get(index).getCoordinates());
		
		coords.add(fruitList.get(index).getCoordinates());
		idOfUsedFruits[index] = true;
		
//		for(int i = 0; i < length; i++) {
//			int tempIndexNext= -1;
//			int tempFruitIdNext = -1;
//			minDist = Double.MAX_VALUE;
//			for(int j = 0; j < fruitList.size(); j++) {
//				//if we don't used this fruit and we check two different fruits
//				
//				
//				if(!idOfUsedFruits[j] && fruitList.get(j).getId() != nextFruitId) {
//					MyCoords dist = new MyCoords();
//					double tempDist = dist.distance3d(fruitList.get(index).getCoordinates(), fruitList.get(j).getCoordinates());
//				
//					if(minDist > tempDist) {
//						minDist = tempDist;
//						tempIndexNext = j;
//						tempFruitIdNext = fruitList.get(i).getId();
//					}
//				}
//				
//			}
			
		}
	}
	
//	public Path(LinkedList<Fruit> fruitList) {
//		coords = new LinkedList<>();
//		addPointToPath(fruitList);
//	}
//	
//	private void addPointToPath(LinkedList<Fruit> fruitList) {
//		for(Fruit f: fruitList) {
//			double x = f.getCoordinates().x();
//			double y = f.getCoordinates().y();
//			int [] xyPixels = new Map().ll2xy(x, y);
//			Point temp = new Point(xyPixels[0], xyPixels[1]);
//			coords.add(temp);
//		}
//	}
//	
//	private void findThePath() { //find all distances between every two points
//		lengths = new int[coords.size()][coords.size()];
//		for(int i = 1; i < coords.size(); i++) {
//			for(int j = i + 1; j < coords.size(); j++) {
//				lengths[i][j] = distance(coords.get(i), coords.get(j));
//			}
//		}
//	}
	
	private int distance(Point a, Point b) {
		double dx = b.getX() - a.getX();
		double dy = b.getY() - a.getY();
		double res = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		return (int)res;
	}
	
	//private
	
	

}
