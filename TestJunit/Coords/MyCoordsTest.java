package Coords;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Geom.Point3D;

class MyCoordsTest {
	
	Point3D point1 = null;
	Point3D point2 = null;
	MyCoords myCoords = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		point1 = new Point3D(32.10332, 35.20904, 670);
		point2 = new Point3D(32.10635, 35.20523, 650);
		myCoords = new MyCoords();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testDistance3d() {
		double dist = myCoords.distance3d(point1, point2);
		boolean flag = false;
		if(dist > 492 && dist < 493) {
			flag = true;
		}
		assertTrue(flag);
	}

	@Test
	void testIsValid_GPS_Point() {
		assertTrue(myCoords.isValid_GPS_Point(point1));
	}

}
