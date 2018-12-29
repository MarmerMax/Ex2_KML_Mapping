
package GameGUI;

import java.util.LinkedList;

import Coords.MyCoords;
import Geom.Point3D;

/**
 * This class find the next fruit for every pacman and add him to path.
 * @author Max Marmer
 *
 */
public class ShortestPathAlgo {

	private LinkedList<Pacman> pacmanList;
	private LinkedList<Fruit> fruitList;
	private LinkedList<Path> pathList;
	private LinkedList<Integer> usedFruits;

	/**
	 * Construction function.
	 * @param game
	 */
	public ShortestPathAlgo(Game game) {
		this.pacmanList = game.getPacmanList();
		this.fruitList = game.getFruitList();
		findPath();
	}

	/**
	 * Find path if game have pacmans and fruits.
	 */
	private void findPath() {
		if(pacmanList.size() != 0 && fruitList.size() != 0) {
			pathList = new LinkedList<>();
			usedFruits = new LinkedList<>();
			int pathSize = (fruitList.size() / pacmanList.size()) + 1;

			for(int i = 0; i < pacmanList.size(); i++) {//create path for every pacman
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

	/**
	 * Function that check if this fruit still doens't use
	 * @param k index of fruit
	 * @return
	 */
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


