package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GameGUI.Fruit;
import GameGUI.Pacman;

class MyFrameTest {

	LinkedList<Fruit> fruitList;
	LinkedList<Pacman> pacmanList;
	Pacman pacman1;
	Pacman pacman2;
	Fruit fruit1;
	Fruit fruit2;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		pacmanList = new LinkedList<>();
		fruitList = new LinkedList<>();
		pacman1 = new Pacman(10,10,0);
		pacman2 = new Pacman(20,20,1);
		fruit1 = new Fruit(10, 10, 0);
		fruit2 = new Fruit(20, 20, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetIndexOfThisId() {
		fruitList = new LinkedList<>();
		fruitList.add(fruit1);
		fruitList.add(fruit2);
		int index = 0;
		for(int i = 0; i < fruitList.size(); i++) {
			if(fruitList.get(i).getId() == fruit2.getId()) {
				index = i;
			}
		}
		assertEquals(1, index);
	}
	
	
}
