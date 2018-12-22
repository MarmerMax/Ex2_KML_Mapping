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
	private int[][] usedFruits; //flag to already used fruits

	public ShortestPathAlgo(Game game) {
		this.pacmanList = game.getPacmanList();
		this.fruitList = game.getFruitList();
		findPath();
	}
	
	
	private void findPath() {
		if (pacmanList.size() != 0 && fruitList.size() != 0) {
			pathList = new LinkedList<>();
			
			createUsedFruits();
			int pathSize = (fruitList.size() / pacmanList.size()) + 1;
			
			for(int i = 0; i < pacmanList.size(); i++) {
				double minDist  = Double.MAX_VALUE;
				Pacman tempPacman = pacmanList.get(i);
				int nextFruit = -1;
				for(int j = 0; j < fruitList.size(); j++) {
					Fruit tempFruit = fruitList.get(j);
					double tempDist = new MyCoords().distance3d(tempPacman.getCoordinates(), tempFruit.getCoordinates());
					if(minDist > tempDist) {
						nextFruit = j;
						minDist = tempDist;
					}
				}
				Path newPath = new Path(tempPacman, fruitList, pathSize, nextFruit, usedFruits);
				pathList.add(newPath);
				updateUsedFruits(i);
			}
		}
	}
	
	private void createUsedFruits() {
		usedFruits = new int[fruitList.size()][2];
		for(int i = 0; i < fruitList.size(); i++) {
			usedFruits[i][1] = fruitList.get(i).getId();
		}
	}
	
	private void updateUsedFruits(int index) {
		LinkedList<Integer> temp = pathList.get(index).getIdList();
		for(int i = 0; i < temp.size(); i++) {
			for(int j = 0; j < usedFruits.length; j++){
				if(temp.get(i) == usedFruits[j][1]) {
					usedFruits[j][0] = 1;
				}
			}
		}
	}
	
	public LinkedList<Path> getPathList(){
		return pathList;
	}

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
}
//	private double distance(Fruit f, Pacman p) {
//		int [] fruitCoords = new Map().ll2xy(f.getCoordinates().x(), f.getCoordinates().y());
//		int [] pacmanCoords = new Map().ll2xy(p.getCoordinates().x(), p.getCoordinates().y());
//		double dx = fruitCoords[0] - pacmanCoords[0];
//		double dy = fruitCoords[1] - pacmanCoords[1];
//		double res = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
//		return res;
//	}


