package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GameGUI.Pacman;

class PacmanTest {
	
	Pacman pacman;
	boolean flag;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		pacman = new Pacman(100, 100, 5);
		flag = true;
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testPacman() {
		Pacman pacman = new Pacman(50, 50, 10);
		if(pacman.getId() != 10) {
			flag = false;
		}
		if(pacman.getRadius() != 1) {
			flag = false;
		}
		if(pacman.getSpeed() != 1) {
			flag = false;
		}
		if(pacman.getType() != 'P') {
			flag = false;
		}
		assertTrue(flag);
	}

	@Test
	void testSetSpeed() {
		pacman.setSpeed(10);
		if(pacman.getSpeed() != 10) {
			flag = false;
		}
		assertTrue(flag);
	}

	@Test
	void testGetId() {
		if(pacman.getId() != 5) {
			flag = false;
		}
		assertTrue(flag);
	}

	@Test
	void testGetRadius() {
		if(pacman.getRadius() != 1) {
			flag = false;
		}
		assertTrue(flag);
	}

	@Test
	void testGetSpeed() {
		if(pacman.getSpeed() != 1) {
			flag = false;
		}
		assertTrue(flag);
	}

}
