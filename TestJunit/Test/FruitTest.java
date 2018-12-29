package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GameGUI.Fruit;

class FruitTest {
	
	Fruit fruit;
	boolean flag;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		fruit = new Fruit(100, 100, 10);
		flag = true;
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFruit() {
		Fruit fruit = new Fruit(50, 50, 5);
		if(fruit.getId() != 5) {
			flag = false;
		}
		if(fruit.getSpeed() != 1) {
			flag = false;
		}
		if(fruit.getType() != 'F') {
			flag = false;
		}
		assertTrue(flag);
	}

	@Test
	void testGetId() {
		if(fruit.getId() != 10) {
			flag = false;
		}
		assertTrue(flag);
	}

	@Test
	void testGetSpeed() {
		if(fruit.getSpeed() != 1) {
			flag = false;
		}
		assertTrue(flag);
	}

	@Test
	void testGetType() {
		if(fruit.getType() != 'F') {
			flag = false;
		}
		assertTrue(flag);
	}

}
