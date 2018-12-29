
package GameGUI;

import java.util.LinkedList;
import Coords.MyCoords;
import Geom.Point3D;

/**
<<<<<<< HEAD
 * This class find the next fruit for every pacman and add him to path.
 * @author Max Marmer
 *
=======
 * This class find all paths in project.
 * @author Maksim
>>>>>>> 3d9bf0f5160f10d3344293b4acadb1cf35f1bf30
 */
public class ShortestPathAlgo {

	private LinkedList<Pacman> pacmanList;
	private LinkedList<Fruit> fruitList;
	private LinkedList<Path> pathList;
	private LinkedList<Integer> usedFruits;

	/**
<<<<<<< HEAD
	 * Construction function.
=======
	 * This constructor function receive game and search best paths. 
>>>>>>> 3d9bf0f5160f10d3344293b4acadb1cf35f1bf30
	 * @param game
	 */
	public ShortestPathAlgo(Game game) {
		this.pacmanList = game.getPacmanList();
		this.fruitList = game.getFruitList();
		findPath();
	}
<<<<<<< HEAD

	/**
	 * Find path if game have pacmans and fruits.
=======
	
	/**
	 * This function creates list of paths.
>>>>>>> 3d9bf0f5160f10d3344293b4acadb1cf35f1bf30
	 */
	private void findPath() {
		
		if(pacmanList.size() != 0 && fruitList.size() != 0) {//if we have least at one object of each type
			pathList = new LinkedList<>();
			usedFruits = new LinkedList<>();
			int pathSize = (fruitList.size() / pacmanList.size()) + 1; //count path size 

<<<<<<< HEAD
			for(int i = 0; i < pacmanList.size(); i++) {//create path for every pacman
=======
			for(int i = 0; i < pacmanList.size(); i++) { //create path for all pacman
>>>>>>> 3d9bf0f5160f10d3344293b4acadb1cf35f1bf30
				pathList.add(new Path(pacmanList.get(i)));
			}

			for(int i = 0; i < pathSize; i++) { //start to build paths

				for(int j = 0; j < pacmanList.size(); j++) { //choose next path 
					double minDist = Double.MAX_VALUE;
					int nextFruit = -1;

					for(int k = 0; k < fruitList.size(); k++) { //find next point in path
						
						if(isNewFruit(k)) { //if this index has not been used
							
							Point3D p1 = pathList.get(j).getPointList().get(i);
							Point3D p2 = fruitList.get(k).getCoordinates();

							double tempDist = new MyCoords().distance3d(p1, p2);

							if(minDist > tempDist) { //if this distance less that previous so it's minimum
								minDist = tempDist;
								nextFruit = k;
							}	
						}
					}
					if(nextFruit != -1) {
						pathList.get(j).add(fruitList.get(nextFruit)); //add fruit to path
						usedFruits.add(nextFruit); //add index of this fruit to list of "used fruits"
					}
				}
			}
		}
	}
<<<<<<< HEAD

	/**
	 * Function that check if this fruit still doens't use
	 * @param k index of fruit
	 * @return
=======
	
	/**
	 *This function check if this index still has not been used in any path. 
	 * @param k index to check
	 * @return true if index has not been used, false used
>>>>>>> 3d9bf0f5160f10d3344293b4acadb1cf35f1bf30
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

	/**
	 * Get path list of all paths.
	 * @return all paths
	 */
	public LinkedList<Path> getPathList(){
		return pathList;
	}
}


