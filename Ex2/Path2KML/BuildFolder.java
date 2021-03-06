package Path2KML;


import GameGUI.Path;
import Geom.Point3D;
import java.util.LinkedList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class create folder with data information from CSV file and turn it to KML.
 * Folder include all points of project. Here we must to create GIS project from CSV file.
 * @author Max Marmer
 *
 */
public class BuildFolder {

	private Element folder = null;
	private LinkedList<Path> pathList;

	/**
	 * Construction method. Create folder and add all points.
	 * @param doc name of general document that include this folder.
	 * @param csvName name of CSV file
	 */
	public BuildFolder(Document doc, LinkedList<Path> pathList) {
		this.pathList = pathList;
		//create folder and give name to this folder
		createFolder(doc); 

		//Add this file to folder
		addPath(doc);
	}

	/**
	 * This method return folder with all data that we read from CSV file.
	 * @return folder of data
	 */
	public Node getFolder() {
		return folder;
	}

	/**
	 * This method create folder and add him to document.
	 * @param doc name of general document
	 */
	private void createFolder(Document doc) {
		try {
			folder = doc.createElement("Folder");
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode("Pacman Path"));
			folder.appendChild(name);
		}catch(Exception exp) {
			System.err.println("Error: Build folder problem");
		}
	}

	/**
	 * This method read all data from layer.
	 * @param doc name of general document
	 * @param layer layer that we need to read from him
	 */
	private void addPath(Document doc) {
		try {
			for(int i = 0; i < pathList.size(); i++) {
				for(int j = 0; j < pathList.get(i).getPointList().size(); j++) {
					String timeStamp = pathList.get(i).getTimeStamp().get(j);
					Point3D temp = pathList.get(i).getPointList().get(j);
					if(j == 0) {
						Node pacman = createObjects(doc, temp, timeStamp, "#pacman");
						folder.appendChild(pacman);
					}
					else {
						Node fruit = createObjects(doc, temp, timeStamp, "#fruit");
						folder.appendChild(fruit);
					}	
				}
				int color = i % 5;
				Node node = createPath(doc, pathList.get(i), color); //create node from current element 
				folder.appendChild(node); //add this element to folder
			}
		}catch(Exception exp) {
			System.out.println("Error: Placemark created problems!!!");
		}
	}
	
	/**
	 * Create placemark for point on the map.
	 * @param doc
	 * @param temp
	 * @param time
	 * @param type
	 * @return place mark node
	 */
	private Node createObjects(Document doc, Point3D temp, String time, String type) {
		Element placemark = doc.createElement("Placemark");
		
		Element timeStamp = doc.createElement("TimeStamp");
		timeStamp.appendChild(getTimeStamp(doc, time));
		placemark.appendChild(timeStamp);

		Element styleUrl = doc.createElement("styleUrl");
		styleUrl.appendChild(doc.createTextNode(type));
		placemark.appendChild(styleUrl);

		Element point = doc.createElement("Point");
		point.appendChild(getPointCoordinates(doc, temp));
		placemark.appendChild(point);

		return placemark;
	}
	
	/**
	 * This method create node with time stamp.
	 * @param doc
	 * @param time
	 * @return timestamp node
	 */
	public Node getTimeStamp(Document doc, String time) {
		Node node = doc.createElement("when");
		node.appendChild(doc.createTextNode(time));
		return node;
	}
	
	/**
	 * This method create node with coordinates for point node.
	 * @return coordinates for point
	 */
	public Node getPointCoordinates(Document doc, Point3D point) {
		Node node = doc.createElement("coordinates");
		String x = "" + point.y();
		String y = "" + point.x();
		String z = "" + point.z();
		node.appendChild(doc.createTextNode(x + "," + y + "," + z));
		return node;
	}
	

	/**
	 * This method create node line from data and return placemark if line.
	 * @param doc name of general document
	 * @param element GIS element that we need to explore
	 * @return new node
	 */
	private Node createPath(Document doc, Path temp, int color) {
		Element placemark = doc.createElement("Placemark");

		Element styleUrl = doc.createElement("styleUrl");
		styleUrl.appendChild(doc.createTextNode(getColor(color)));
		placemark.appendChild(styleUrl);

		Element LineString = doc.createElement("LineString");
		LineString.appendChild(getCoordinates(doc, temp));
		placemark.appendChild(LineString);
		
		return placemark;
	}

	/**
	 * This method return node with coordinates for create line.
	 * @param doc
	 * @param temp
	 * @return coordinates node
	 */
	private Node getCoordinates(Document doc, Path temp) {
		Element node = doc.createElement("coordinates");
		for(int i = 0; i < temp.getPointList().size(); i++) {
			String x = "" + temp.getPointList().get(i).y();
			String y = "" + temp.getPointList().get(i).x();
			String z = "" + temp.getPointList().get(i).z();
			node.appendChild(doc.createTextNode(x + "," + y + "," + z + "" + "\n"));
		}
		return node;
	}


//	/**
//	 * This method translate from date to time stamp format.
//	 * @param date current date
//	 * @return this date in time stamp format
//	 */
//	private String getTimeStamp(String date) {
//		Timestamp ts = Timestamp.valueOf(date) ;
//		return "" + ts.getTime();
//	}

	/**
	 * This method choose color for points.
	 * @param channel channel of points
	 * @return color for point
	 */
	private String getColor(int color) {
		if (color == 0)	return "#red";
		else if (color == 1) return "#yellow";
		else if (color == 2) return "#green";
		else if(color == 3) return "#blue";
		else return "#purple";
	}
}
