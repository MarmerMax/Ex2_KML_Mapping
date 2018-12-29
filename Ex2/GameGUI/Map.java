package GameGUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Class for create map.
 * @author Max Marmer
 *
 */
public class Map {
	
	private BufferedImage map;
	
	/**
	 * Class constructor.
	 */
	public Map() {
		try {
			map = ImageIO.read(new File("data\\Ariel1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get image.
	 * @return
	 */
	public BufferedImage getMap() {
		return map;
	}
	
	/**
	 * This method convert from lat, lon to pixel x,y for this map.
	 * @param lat
	 * @param lon
	 * @return array of x, y in pixel
	 */
	public int[] fromLatLonToPixel(double lat, double lon) {
		double mapHeight = 642;
		double mapWidth = 1433;
		double mapLatBottom = 32.10194444;
		double mapLngLeft = 35.20222222;
		double mapLngRight = 	35.21222222;
		double mapLatBottomRad = mapLatBottom * Math.PI / 180;
		double latitudeRad = lat * Math.PI / 180;
		double mapLngDelta = (mapLngRight - mapLngLeft);
		double worldMapWidth = ((mapWidth / mapLngDelta) * 360) / (2 * Math.PI);
		double mapOffsetY = (worldMapWidth / 2 * Math.log((1 + Math.sin(mapLatBottomRad))
							 / (1 - Math.sin(mapLatBottomRad))));
		double x = (lon - mapLngLeft) * (mapWidth / mapLngDelta);
		double y = mapHeight - ((worldMapWidth / 2 * Math.log((1 + Math.sin(latitudeRad)) 
								/ (1 - Math.sin(latitudeRad)))) - mapOffsetY);
		int [] pixelXY = new int[2];
		pixelXY[0] = (int)x;
		pixelXY[1] = (int)y;
		return pixelXY;
	}
	
	/**
	 * This method convert from x,y in pixel to lat, lon in degrees for this map.
	 * @param x
	 * @param y
	 * @return
	 */
	public double[] fromPixelToLatLon(int x, int y) {
		double [] degreesLatlon = new double[2];
		double xStep = (35.212479 - 35.202340) / 1433; 
		double yStep = (32.105739 - 32.101852) / 642;
		degreesLatlon[0] = 32.105739 - (yStep * y);
		degreesLatlon[1] = 35.202340 + (xStep * x);
		return degreesLatlon;
	}

}
