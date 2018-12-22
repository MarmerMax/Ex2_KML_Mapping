package GameGUI;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.JFrame;

import GIS.MetaData;
import Path2KML.Path2KML;

public class Game {

	private LinkedList<Pacman> pacmanList;
	private LinkedList<Fruit> fruitList;
	private LinkedList<Path> pathList;

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

	public void setPathList(LinkedList<Path> pathList) {
		this.pathList = pathList;
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

	public void writeFileDialog(String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName);
			System.out.println(fileName);
			PrintWriter outs = new PrintWriter(fw);
			StringBuilder sb = new StringBuilder();
			String [] columnName = {"Type", "Id", "Lat", "Lon", "Alt", "Speed/Weight",
					"Radius", "" + pacmanList.size(), "" + fruitList.size()};
			for(int i = 0; i < columnName.length; i++) {
				sb.append(columnName[i]);
				if(i + 1 < columnName.length) {
					sb.append(',');
				}
				else if(i + 1 == columnName.length) {
					sb.append('\n');
				}
			}
			for(int i = 0; i < pacmanList.size(); i++) {
				sb.append(pacmanList.get(i).getType());
				sb.append(',');
				sb.append(pacmanList.get(i).getId());
				sb.append(',');
				sb.append(pacmanList.get(i).getCoordinates().x());
				sb.append(',');
				sb.append(pacmanList.get(i).getCoordinates().y());
				sb.append(',');
				sb.append(pacmanList.get(i).getCoordinates().z());
				sb.append(',');
				sb.append(pacmanList.get(i).getSpeed());
				sb.append(',');
				sb.append(pacmanList.get(i).getRadius());
				sb.append('\n');
			}

			for(int i = 0; i < fruitList.size(); i++) {
				sb.append(fruitList.get(i).getType());
				sb.append(',');
				sb.append(fruitList.get(i).getId());
				sb.append(',');
				sb.append(fruitList.get(i).getCoordinates().x());
				sb.append(',');
				sb.append(fruitList.get(i).getCoordinates().y());
				sb.append(',');
				sb.append(fruitList.get(i).getCoordinates().z());
				sb.append(',');
				sb.append(fruitList.get(i).getSpeed());
				sb.append('\n');
			}
			outs.write(sb.toString());
			outs.close();
			fw.close();
		} catch (IOException ex) {
			System.out.print("Error writing file  " + ex);
		}
		System.out.println("Save file done!");
	}

	public void writeFileKML(String fileName, LinkedList<Path> pathList) {
		setPathList(pathList);
//		System.out.println(fileName);
//		int index = 0;
//		int lastBackSlash = 0;
//		while(index < fileName.length()) {
//			if(fileName.charAt(index) == '\\') {
//				lastBackSlash = index;
//			}
//			index++;
//		}
//		fileName = fileName.substring(0, lastBackSlash);
//		System.out.println(fileName);
		new Path2KML(fileName, pathList);
	}
}
