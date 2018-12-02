package File_format;

import org.w3c.dom.*;

import GIS.GISElement;
import GIS.GISLayer;
import GIS.GISProject;

import java.sql.Timestamp;
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
	private GISLayer layer = null;
	
	/**
	 * Construction method. Create folder and add all points.
	 * @param doc name of general document that include this folder.
	 * @param csvName name of CSV file
	 */
	public BuildFolder(Document doc, String csvName) {

		//create folder and give name to this folder
		getFolder(doc); 
		
		//Create GIS project from csvName file
		//GISProject project = new GISProject(csvName);
		
		layer = new GISLayer(csvName);
		
		//Add this file to folder
		translateLayer(doc, layer);
	}
	
	/**
	 * This method return folder with all data that we read from CSV file.
	 * @return folder of data
	 */
	public Node getFolder() {
		return folder;
	}
	
	/**
	 * This method return current layer for input him to project. (Input GISLayer to GISProject)
	 * @return current layer
	 */
	public GISLayer getLayer() {
		return layer;
	}
	
	
	/**
	 * This method create folder and add him to document.
	 * @param doc name of general document
	 */
	private void getFolder(Document doc) {
		try {
			folder = doc.createElement("Folder");
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode("Wifi Networks "));
			folder.appendChild(name);
		}catch(Exception exp) {
			System.err.println("Error: Build folder problem");
		}
	}
	
//	/**
//	 * This method run for all objects of project.
//	 * @param doc name of general document
//	 * @param project project that we need to read
//	 */
//	private void getProject(Document doc, GISProject project) {
//		try {
//			for(GISLayer curLayer: project.getProject()) { 
//				
//				//add layer to this folder
//				getLayer(doc, curLayer);
//			}
//		}catch(Exception exp) {
//			System.out.println("Error: Get project problem!!!");
//		}
//	}
	
	/**
	 * This method read all data from layer.
	 * @param doc name of general document
	 * @param layer layer that we need to read from him
	 */
	private void translateLayer(Document doc, GISLayer layer) {
		try {
 			for(GISElement curElement: layer.getLayer()) {
				Node node = getElements(doc, curElement); //create node from current element 
				folder.appendChild(node); //add this element to folder
			}
		}catch(Exception exp) {
			System.out.println("Error: Get layer problem!!!");
		}
	}
	
	/**
	 * This method get element from layer and need to turn him to node.
	 * @param doc name of general document
	 * @param element GIS element that we need to explore
	 * @return new node
	 */
	private Node getElements(Document doc, GISElement element) {
		Element placemark = doc.createElement("Placemark");
		getElementData(doc, placemark, element);
		return placemark;
	}
	
	/**
	 * This method add all data from GIS element to current node. 
	 * @param doc name of general document
	 * @param placemark name of root element
	 * @param element GIS element that we translate to kml
	 */
	private void getElementData(Document doc, Element placemark, GISElement element) {
		try {
			//create all needed <elements>
			Element name = doc.createElement("name");
			Element decribtion = doc.createElement("decribtion");
			Element styleUrl = doc.createElement("styleUrl");
			Element point = doc.createElement("Point");
			
			//add data to <elements>
			CDATASection ssid = doc.createCDATASection(element.getSSID());
			name.appendChild(ssid);
			styleUrl.appendChild(doc.createTextNode(getColor(element.getChannel())));
			point.appendChild(getPointCoordinates(doc, element.getCurrentLatitude(), element.getCurrentLongtitude()));
			String temp = "BSSID: <b>" + element.getMAC() + "</b><br/>"
					+ "Capabilities: <b>" + element.getAuthMode() + "</b><br/>"
					+ "Frequency: <b>" + getFrequency(element.getChannel()) + "</b><br/>"
					+ "Timestamp: <b>" + getTimeStamp(element.getFirstSeen()) + "</b><br/>"
					+ "Date: <b>" + element.getFirstSeen() + "</b>";
			CDATASection dSection = doc.createCDATASection(temp);
			decribtion.appendChild(dSection);
		
			//add <elements> to root element
			placemark.appendChild(name);
			placemark.appendChild(decribtion);
			placemark.appendChild(styleUrl);
			placemark.appendChild(point);
			
		}catch(Exception exp) {
			System.out.println("Error: Get Elements problem");
		}
	}
	/**
	 * This method create <element> coordinates that include x, y coordinates and return this node.
	 * @param doc name of general document
	 * @param lat Latitude
	 * @param lon Longitude
	 * @return new node with needed data
	 */
	private Node getPointCoordinates(Document doc, String lat, String lon) {
		Element coordinates = doc.createElement("coordinates");
		coordinates.appendChild(doc.createTextNode(lat + "," + lon));
		return coordinates;
	}
	
	/**
	 * This method translate from date to time stamp format.
	 * @param date current date
	 * @return this date in time stamp format
	 */
	private String getTimeStamp(String date) {
		Timestamp ts = Timestamp.valueOf(date) ;
		return "" + ts.getTime();
	}
	
	/**
	 * This method choose color for points.
	 * @param channel channel of points
	 * @return color for point
	 */
	private String getColor(String channel) {
		if (Integer.parseInt(channel) <= 6)	return "#red";
		else if (Integer.parseInt(channel) > 6 && Integer.parseInt(channel) <= 10) return "#yellow";
		else if(Integer.parseInt(channel) > 10 && Integer.parseInt(channel) <= 40)return "#green";
		else if(Integer.parseInt(channel) > 40 && Integer.parseInt(channel) <= 48)return "#blue";
		else return "#purple";
	}
	
	/**
	 * This method translate from RSSI to frequency.
	 * @param channel number of channel
	 * @return number of frequency
	 */
	private int getFrequency(String channel) {	
		int chan = Integer.parseInt(channel);
		switch (chan) {
		case 1: return 2412;
		case 2: return 2417;
		case 3: return 2422;
		case 4: return 2427;
		case 5: return 2432;
		case 6: return 2437;
		case 7: return 2442;
		case 8: return 2447;
		case 9: return 2452;
		case 10: return 2457;
		case 11: return 2462;
		case 12: return 2467;
		case 13: return 2472;
		case 14: return 2477;
		case 36: return 5180;
		case 40: return 5200;
		case 44: return 5220;
		case 48: return 5240;
		default: return 5250;
		}

	}

}
