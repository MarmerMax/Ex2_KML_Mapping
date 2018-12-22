//package GameGUI;
//
//import java.util.LinkedList;
//
//import Coords.MyCoords;
//import Geom.Point3D;
//
//public class Path {
//
//	private LinkedList<Point3D> pointList;
//	private LinkedList<Integer> idList;
//	private int pathSize;
//	private double pathLength;
//	//private double speed;
//
//
//	public Path(Pacman pacman, LinkedList<Fruit> fruitList, int pathSize, boolean [] usedFruits) {
//		this.pointList = new LinkedList<>();
//		this.idList = new LinkedList<>();
//	//	this.speed = pacman.getSpeed();
//		this.pathSize = pathSize;
//		this.pathLength = 0;
//		pointList.add(pacman.getCoordinates());
//		idList.add(pacman.getId());
//		buildPath(fruitList, usedFruits);
//	}
//
//	public LinkedList<Point3D> getPointList(){
//		return pointList;
//	}
//
//	public int getPathSize() {
//		return pathSize;
//	}
//
//	public double getPathLength() {
//		return pathLength;
//	}
//	
//	private void buildPath(LinkedList<Fruit> fruitList, boolean [] usedFruits) {
//		
//	}
//
////	private void buildPath(LinkedList<Fruit> fruitList, boolean [] usedFruits) {
////		findNextFruitAfterPacman(fruitList, usedFruits);
////		findAllNextFruits(fruitList, usedFruits);
////	}
//
//	private void findNextFruitAfterPacman(LinkedList<Fruit> fruitList, boolean [] usedFruits) {
//		double minDist = Double.MAX_VALUE;
//		//int nextFruit = -1;
//		int index = -1;
//		for(int i = 0; i < fruitList.size(); i++) {//find first fruit near to pacman by geom coordinates
//			MyCoords dist = new MyCoords();
//			double tempDist = dist.distance3d(pointList.get(0), fruitList.get(i).getCoordinates());//find dist between pacman to fruit
//			if(minDist > tempDist) {
//				minDist = tempDist;
//				index = i;
//			}
//		}
//		//add distance between pacman to next fruit into path length
//		pathLength += minDist;
//
//		//add next fruit coordinates to path list
//		pointList.add(fruitList.get(index).getCoordinates());
//		idList.add(fruitList.get(index).getId());
//		//mark index of next fruit in array of indexes in order not to use more  
//		usedFruits[index] = true;
//	}
//
//	private void findAllNextFruits(LinkedList<Fruit> fruitList, boolean [] usedFruits) {
//		double minDist;
//		int index = -1;
//		for(int i = 1; i < pathSize; i++) {
//			minDist = Double.MAX_VALUE;
//			for(int j = 0; j < fruitList.size(); j++) {
//				if(!usedFruits[j]) {	
//					MyCoords dist = new MyCoords();
//					double tempDist = dist.distance3d(pointList.get(i), fruitList.get(j).getCoordinates());
//					if(minDist > tempDist) {
//						minDist = tempDist;
//						index = i;
//					}			
//				}
//			}
//			pathLength += minDist;
//			pointList.add(fruitList.get(index).getCoordinates());
//			idList.add(fruitList.get(index).getId());
//			usedFruits[index] = true;
//		}
//	}
//
//}

package GameGUI;

import java.util.LinkedList;

import Coords.MyCoords;
import Geom.Point3D;
import sun.security.tools.PathList;

public class Path {

	private LinkedList<Point3D> pointList;
	private LinkedList<Integer> idList;
	private int pathSize;
	private double pathLength;
	private double speed;
	private double radiusPacman;
	private double radiusFruit;
	private int[][] usedFruits;
	//private double speed;


	public Path(Pacman pacman, LinkedList<Fruit> fruitList, int pathSize, int nextFruit,int [][] usedFruits) {
		this.pointList = new LinkedList<>();
		this.idList = new LinkedList<>();
		//	this.speed = pacman.getSpeed();
		this.pathSize = pathSize;
		this.pathLength = 0;
		this.usedFruits = usedFruits;
		this.speed = pacman.getSpeed();
		this.radiusPacman = pacman.getRadius();
		this.radiusFruit = 1;
		pointList.add(pacman.getCoordinates());
		idList.add(pacman.getId());
		buildPath(fruitList, nextFruit);
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

	private void buildPath(LinkedList<Fruit> fruitList, int nextFruit) {
		pointList.add(fruitList.get(nextFruit).getCoordinates());
		idList.add(fruitList.get(nextFruit).getId());
		usedFruits[nextFruit][0] = 1;
		
		pathLength += new MyCoords().distance3d(pointList.get(0), pointList.get(1));

		int indexAtualy = nextFruit;
		for(int i = 1; i < pathSize; i++) {
			Fruit temp = fruitList.get(indexAtualy);
			double minDist = Double.MAX_VALUE;
			int indexNext = -1;
			for(int j = 0; j < fruitList.size(); j++) {
				Fruit tempNext = fruitList.get(j);
				if(usedFruits[j][0] == 0 && temp.getId() != tempNext.getId()) {
					double tempDist = new MyCoords().distance3d(temp.getCoordinates(), tempNext.getCoordinates());
					if(minDist > tempDist) {
						indexNext = j;
						minDist = tempDist;
					}
				}
			}
			if(indexNext != -1) {
				pointList.add(fruitList.get(indexNext).getCoordinates());
				idList.add(fruitList.get(indexNext).getId());
				usedFruits[indexNext][0] = 1;
				indexAtualy = indexNext;
				pathLength += new MyCoords().distance3d(pointList.get(i), pointList.get(i + 1));
			}
		}
	}
	


	//	private void buildPath(LinkedList<Fruit> fruitList, boolean [] usedFruits) {
	//		findNextFruitAfterPacman(fruitList, usedFruits);
	//		findAllNextFruits(fruitList, usedFruits);
	//	}

	//	private void findNextFruitAfterPacman(LinkedList<Fruit> fruitList, boolean [] usedFruits) {
	//		double minDist = Double.MAX_VALUE;
	//		//int nextFruit = -1;
	//		int index = -1;
	//		for(int i = 0; i < fruitList.size(); i++) {//find first fruit near to pacman by geom coordinates
	//			MyCoords dist = new MyCoords();
	//			double tempDist = dist.distance3d(pointList.get(0), fruitList.get(i).getCoordinates());//find dist between pacman to fruit
	//			if(minDist > tempDist) {
	//				minDist = tempDist;
	//				index = i;
	//			}
	//		}
	//		//add distance between pacman to next fruit into path length
	//		pathLength += minDist;
	//
	//		//add next fruit coordinates to path list
	//		pointList.add(fruitList.get(index).getCoordinates());
	//		idList.add(fruitList.get(index).getId());
	//		//mark index of next fruit in array of indexes in order not to use more  
	//		usedFruits[index] = true;
	//	}

	//	private void findAllNextFruits(LinkedList<Fruit> fruitList, boolean [] usedFruits) {
	//		double minDist;
	//		int index = -1;
	//		for(int i = 1; i < pathSize; i++) {
	//			minDist = Double.MAX_VALUE;
	//			for(int j = 0; j < fruitList.size(); j++) {
	//				if(!usedFruits[j]) {	
	//					MyCoords dist = new MyCoords();
	//					double tempDist = dist.distance3d(pointList.get(i), fruitList.get(j).getCoordinates());
	//					if(minDist > tempDist) {
	//						minDist = tempDist;
	//						index = i;
	//					}			
	//				}
	//			}
	//			pathLength += minDist;
	//			pointList.add(fruitList.get(index).getCoordinates());
	//			idList.add(fruitList.get(index).getId());
	//			usedFruits[index] = true;
	//		}
	//	}

}



//private

//because the id of fruits in csv file can be all number and not start from zero 
//we must to find this fruit
//		int index = 0;
//		Fruit temp = fruitList.get(index);
//		while(temp.getId() != nextFruitIndex) {
//			temp = fruitList.get(index);
//			index++;
//		}


//	coords.add(fruitList.get(index).getCoordinates());


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





//private

//because the id of fruits in csv file can be all number and not start from zero 
//we must to find this fruit
//		int index = 0;
//		Fruit temp = fruitList.get(index);
//		while(temp.getId() != nextFruitIndex) {
//			temp = fruitList.get(index);
//			index++;
//		}


//	coords.add(fruitList.get(index).getCoordinates());


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

