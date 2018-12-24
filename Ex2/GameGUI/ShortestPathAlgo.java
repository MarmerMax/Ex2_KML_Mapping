//package GameGUI;
//
//import java.util.LinkedList;
//
//import Geom.Point3D;
//
//public class ShortestPathAlgo {
//
//	private LinkedList<Pacman> pacmanList;
//	private LinkedList<Fruit> fruitList;
//	private LinkedList<LinkedList<Point3D>> pathList;
//	private boolean[] usedFruits; //flag to already used fruits
//
//	public ShortestPathAlgo(Game game) {
//		this.pacmanList = game.getPacmanList();
//		this.fruitList = game.getFruitList();
//		findPath();
//	}
//
//	private void findPath() {
//		if(pacmanList.size() != 0 && fruitList.size() != 0) {
//			//int pathLength = fruitList.size() / pacmanList.size();
//			pathList = new LinkedList<>();
//			//usedFruits = new boolean[fruitList.size()];
//
////			for(int i = 0; i < pacmanList.size(); i++) {
////				Path tempPath = new Path(pacmanList.get(i), fruitList, pathLength, usedFruits);
////				pathList.add(tempPath);
////			}
//			
//			for(int i = 0; i < pacmanList.size(); i++) {
//				LinkedList<Point3D> tempPath = new LinkedList<>();
//				tempPath.add(pacmanList.get(i).getCoordinates());
//				for(int j = 0; j < fruitList.size(); j++) {
//					double x = fruitList.get(j).getCoordinates().x();
//					double y = fruitList.get(j).getCoordinates().y();
//					double z = fruitList.get(j).getCoordinates().z();
//					Point3D temp = new Point3D(x, y, z);
//					tempPath.add(temp);
//				}
//				pathList.add(tempPath);
//			}
//		}
//	}
//	public LinkedList<LinkedList<Point3D>> getPathList(){
//		return pathList;
//	}
//}
//	private double distance(Fruit f, Pacman p) {
//		int [] fruitCoords = new Map().ll2xy(f.getCoordinates().x(), f.getCoordinates().y());
//		int [] pacmanCoords = new Map().ll2xy(p.getCoordinates().x(), p.getCoordinates().y());
//		double dx = fruitCoords[0] - pacmanCoords[0];
//		double dy = fruitCoords[1] - pacmanCoords[1];
//		double res = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
//		return res;
//	}

package GameGUI;

import java.util.LinkedList;

import Coords.MyCoords;
import Geom.Point3D;

public class ShortestPathAlgo {

	private LinkedList<Pacman> pacmanList;
	private LinkedList<Fruit> fruitList;
	private LinkedList<Path> pathList;
	private LinkedList<Integer> usedFruits;

	public ShortestPathAlgo(Game game) {
		this.pacmanList = game.getPacmanList();
		this.fruitList = game.getFruitList();
		findPath();
	}

	private void findPath() {
		if(pacmanList.size() != 0 && fruitList.size() != 0) {
			pathList = new LinkedList<>();
			usedFruits = new LinkedList<>();
			int pathSize = (fruitList.size() / pacmanList.size()) + 1;

			for(int i = 0; i < pacmanList.size(); i++) {
				pathList.add(new Path(pacmanList.get(i)));
			}

			for(int i = 0; i < pathSize; i++) {

				for(int j = 0; j < pacmanList.size(); j++) {
					double minDist = Double.MAX_VALUE;
					int nextFruit = -1;

					for(int k = 0; k < fruitList.size(); k++) {
						if(isNewFruit(k)) {
							Point3D p1 = pathList.get(j).getPointList().get(i);
							Point3D p2 = fruitList.get(k).getCoordinates();

							double tempDist = new MyCoords().distance3d(p1, p2);

							if(minDist > tempDist) {
								minDist = tempDist;
								nextFruit = k;
							}	
						}
					}
					if(nextFruit != -1) {
						pathList.get(j).add(fruitList.get(nextFruit));
						usedFruits.add(nextFruit);
					}
				}
			}
		}
	}

	private boolean isNewFruit(int k) {
		int i = 0;
		while(i < usedFruits.size()) {
			if(usedFruits.get(i) == k) {
				return false;
			}
			i++;
		}
		return true;
	}

	public LinkedList<Path> getPathList(){
		return pathList;
	}
}


