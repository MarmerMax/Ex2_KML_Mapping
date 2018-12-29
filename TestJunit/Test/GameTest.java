package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import org.junit.jupiter.api.Test;

import GameGUI.Fruit;
import GameGUI.Game;
import GameGUI.Pacman;

class GameTest {
	
	LinkedList<Pacman> pacmanList;
	LinkedList<Fruit> fruitList;

	@Test
	void testGame() {
		boolean flag = true;
		Game game = new Game();
		if(game.getFruitList() == null) {
			flag = false;
		}
		if(game.getPacmanList() == null) {
			flag = false;
		}
		assertTrue(flag);
	}

	@Test
	void testGetPacmanList() {
		Game game = new Game();
		pacmanList = game.getPacmanList();
		boolean flag = true;
		if(pacmanList == null) {
			flag = false;
		}
		assertTrue(flag);
	}

	@Test
	void testGetFruitList() {
		Game game = new Game();
		fruitList = game.getFruitList();
		boolean flag = true;
		if(fruitList == null) {
			flag = false;
		}
		assertTrue(flag);
	}

}
