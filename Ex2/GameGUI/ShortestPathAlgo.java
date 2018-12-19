package GameGUI;

import java.util.LinkedList;

public class ShortestPathAlgo {

	private LinkedList<Pacman> pacmanList;
	private LinkedList<Fruit> fruitList;
	private LinkedList<Path> pathList;
	private boolean[] idOfUsedFruits; //flag to already used fruits

	public ShortestPathAlgo(Game game) {
		this.pacmanList = game.getPacmanList();
		this.fruitList = game.getFruitList();
		findPath();
	}

	private void findPath() {
		if(pacmanList.size() != 0 && fruitList.size() != 0) {

			int pathLength = fruitList.size() / pacmanList.size();
			pathList = new LinkedList<>();
			idOfUsedFruits = new boolean[fruitList.size()];

			for(int i = 0; i < pacmanList.size(); i++) {
				Path tempPath = new Path(pacmanList.get(i), fruitList, pathLength, idOfUsedFruits);
			}
		}
		//			for(int i = 0; i < pacmanList.size(); i++) {
		//				double minPath = Double.MAX_VALUE;
		//				int nearestFruit = -1;
		//				
		//				for(int j = 0; j < fruitList.size(); j++) {
		//					double dist = distance(fruitList.get(j), pacmanList.get(i));
		//					if(dist < minPath) {
		//						minPath = dist;
		//						nearestFruit = j;
		//					}
		//				}
		//		
		//				if(nearestFruit != -1) {
		//					pacmanList.get(i).setNearFruitId(nearestFruit);
		//					Path path = new Path(pacmanList.get(i), fruitList, pathLength, idOfUsedFruits);
		//					pathList.add(path);
		//				}
		//			}
	}

	private double distance(Fruit f, Pacman p) {
		int [] fruitCoords = new Map().ll2xy(f.getCoordinates().x(), f.getCoordinates().y());
		int [] pacmanCoords = new Map().ll2xy(p.getCoordinates().x(), p.getCoordinates().y());
		double dx = fruitCoords[0] - pacmanCoords[0];
		double dy = fruitCoords[1] - pacmanCoords[1];
		double res = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		return res;
	}

}
