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
	
	private Element document = null;
	/**
	 * Construction method. Create new KML type elements from CSV file and
	 * add them to general document.
	 * @param doc name of general document
	 * @param csvName name of CSV file that we need to translate
	 */
	public BuildDocument(Document doc) {
		//create <Document>
		document = doc.createElement("Document");
		//add all data to document
		createDocument(doc);
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
		document.appendChild(getStyle(doc, "Yellow", "3", "ff00ffff"));
		document.appendChild(getStyle(doc, "Green", "3", "ff00ff00"));
		document.appendChild(getStyle(doc, "blue", "3", "ffff0000"));
		document.appendChild(getStyle(doc, "Purple", "3", "ff800080"));

		document.appendChild(getStyleOfObjects(doc, "pacman", "data\\pacman.png", "50", "50"));
		document.appendChild(getStyleOfObjects(doc, "fruit", "data\\fruit.png", "30", "30"));
		
	}

	private Node getStyleOfObjects(Document doc, String id, String path, String width, String height) {
		Element style = doc.createElement("Style");
		style.setAttribute("id", id);
		Element iconStyle = doc.createElement("IconStyle");
		iconStyle.appendChild(getIcon(doc, path, width, height));
		style.appendChild(iconStyle);

		return style;
	}
	
	private Node getIcon(Document doc, String path, String width, String height) {
		Element node = doc.createElement("Icon");
		node.appendChild(doc.createTextNode(path));
		Element w = doc.createElement("w");
		w.appendChild(doc.createTextNode(width));
		node.appendChild(w);
		Element h = doc.createElement("h");
		h.appendChild(doc.createTextNode(height));
		node.appendChild(h);
		
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
		lineStyle.appendChild(getColor(doc, color));
		lineStyle.appendChild(getWidth(doc, width));
		
		style.appendChild(lineStyle);

		return style;
	}
	/**
	 * This method create node of width.
	 * @param doc name of general document.
	 * @param width
	 * @return node of width
	 */
	private Node getWidth(Document doc, String width) {
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
	private Node getColor(Document doc, String color) {
		Element node = doc.createElement("color");
		node.appendChild(doc.createTextNode(color));
		return node;
	}
}
