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

}
