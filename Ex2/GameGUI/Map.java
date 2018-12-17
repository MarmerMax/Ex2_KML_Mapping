package GameGUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Map {
	
	private BufferedImage map;
	
	public Map() {
		try {
			map = ImageIO.read(new File("data\\Ariel1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getMap() {
		return map;
	}
	
	public int[] ll2xy(double lat, double lon) {
		double mapHeight = 622;
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
		int [] coor = new int[2];
		coor[0] = (int)x;
		coor[1] = (int)y;
		return coor;
	}

}
