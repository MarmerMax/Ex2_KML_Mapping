package Path2KML;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


/**
 * This class create new Document that include properties for colors of points and 
 * create new folder with all points of project.
 * @author Max Marmer
 *
 */
public class BuildDocument {
	
	private String address;
	private String addressPacman;
	private String addressFruit;
	
	private Element document = null;
	/**
	 * Construction method. Create new KML type elements from CSV file and
	 * add them to general document.
	 * @param doc name of general document
	 * @param csvName name of CSV file that we need to translate
	 */
	public BuildDocument(Document doc, String fileName) {
		address = fileName;
		createObjectsAddress();
		//create <Document>
		document = doc.createElement("Document");
		//add all data to document
		createDocument(doc);
	}
	
	private void createObjectsAddress() {
		int index = 0; 
		int indexLastBackSlash = 0;
		while(index < address.length()) {
			if(address.charAt(index) == '\\') {
				indexLastBackSlash = index;
			}
			index++;
		}
		addressPacman = address.substring(0, indexLastBackSlash + 1) + "pacmanIcon.png";
		addressFruit = address.substring(0, indexLastBackSlash + 1) + "fruitIcon.png";
	}
	
	/**
	 *This method return document to CSV2KML class. 
	 * @return Node document
	 */
	public Node getDocument() {
		return document;
	}

	/**
	 * This method add all data to document.
	 * @param doc name of general document
	 * @param csvName name of CSV file that we need to translate
	 */
	private void createDocument(Document doc) {
		//add style of points to DOM 
		document.appendChild(getStyle(doc, "red", "3", "ff0000ff"));
		document.appendChild(getStyle(doc, "yellow", "3", "ff00ffff"));
		document.appendChild(getStyle(doc, "green", "3", "ff00ff00"));
		document.appendChild(getStyle(doc, "blue", "3", "ffff0000"));
		document.appendChild(getStyle(doc, "purple", "3", "ff800080"));

		document.appendChild(getStyleOfObjects(doc, "pacman", addressPacman, "80", "80"));
		document.appendChild(getStyleOfObjects(doc, "fruit", addressFruit, "80", "80"));
		
	}

	private Node getStyleOfObjects(Document doc, String id, String path, String width, String height) {
		Element style = doc.createElement("Style");
		style.setAttribute("id", id);
		Element iconStyle = doc.createElement("IconStyle");
		iconStyle.appendChild(getIcon(doc, path, width, height));
		style.appendChild(iconStyle);

		return style;
	}
	
//	private Node getStyleOfObjects(Document doc, String id, String path, String width, String height) {
//	Element style = doc.createElement("Style");
//	style.setAttribute("id", id);
//	Element img = doc.createElement("img");
//	img.setAttribute("src", path);
//	img.setAttribute("width", width);
//	img.setAttribute("height", height);
//	style.appendChild(img);
//
//	return style;
//}
	
	private Node getIcon(Document doc, String path, String width, String height) {
		Element node = doc.createElement("Icon");
		node.appendChild(getPathIcon(doc, path));
		node.appendChild(getWidthIcon(doc, width));
		node.appendChild(getHeightIcon(doc, height));
				
		return node;
	}
	
	public Node getPathIcon(Document doc, String path) {
		Element node = doc.createElement("href");
		node.appendChild(doc.createTextNode(path));
		return node;
	}
	
	public Node getWidthIcon(Document doc, String width) {
		Element node = doc.createElement("w");
		node.appendChild(doc.createTextNode(width));
		return node;
	}
	
	public Node getHeightIcon(Document doc, String height) {
		Element node = doc.createElement("h");
		node.appendChild(doc.createTextNode(height));
		return node;
	}
	
	/**
	 * This method create node that include style information.
	 * @param doc name of general document
	 * @param id color of point
	 * @param link 
	 * @return node of Style
	 */
	private Node getStyle(Document doc, String id, String width, String color) {
		Element style = doc.createElement("Style");
		style.setAttribute("id", id);
		Element lineStyle = doc.createElement("LineStyle");
		lineStyle.appendChild(getColorLine(doc, color));
		lineStyle.appendChild(getWidthLine(doc, width));
		
		style.appendChild(lineStyle);

		return style;
	}
	/**
	 * This method create node of width.
	 * @param doc name of general document.
	 * @param width
	 * @return node of width
	 */
	private Node getWidthLine(Document doc, String width) {
		Element node = doc.createElement("width");
		node.appendChild(doc.createTextNode(width));
		return node;
	}
	/**
	 * This method create node of color.
	 * @param doc name of general document.
	 * @param color
	 * @return node of color
	 */
	private Node getColorLine(Document doc, String color) {
		Element node = doc.createElement("color");
		node.appendChild(doc.createTextNode(color));
		return node;
	}
}
