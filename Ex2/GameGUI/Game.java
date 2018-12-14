package GameGUI;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.JFrame;

import GIS.MetaData;

public class Game {
	
	private LinkedList<Pacman> pacmanList;
	private LinkedList<Fruit> fruitList;
	
	public Game() {
		pacmanList = new LinkedList<>();
		fruitList = new LinkedList<>();
	}
	
    public LinkedList<Pacman> getPacmanList() {
		return pacmanList;
	}

	public LinkedList<Fruit> getFruitList() {
		return fruitList;
	}

	public void readFileDialog(String fileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String row = br.readLine();// first row is garbage
            while((row = br.readLine()) != null) {
            	
        		StringTokenizer stringValues = new StringTokenizer(row, ","); // break the row to tokens
        		int fieldCount = stringValues.countTokens(); //count fields
    			String [] values = new String[fieldCount]; //create array for keeping all fields data
    			for(int i = 0; i < fieldCount; i++) {
    				values[i] = String.valueOf(stringValues.nextElement());  //put fields data to array
    			}
        		if(values[0].equals("P")) {
                	Pacman temp = new Pacman(values);
                	pacmanList.add(temp);
        		}
        		else {
                	Fruit temp = new Fruit(values);
                	fruitList.add(temp);
        		}
            }
            br.close();
        } catch (IOException ex) {
            System.out.print("Error reading file " + ex);
            System.exit(2);
        }
    }
	    
}
