package File_format;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import GIS.GISLayer;
import GIS.GISProject;

/**
 * This class create new Document that include properties for colors of points and 
 * create new folder with all points of project.
 * @author Max Marmer
 *
 */
public class BuildDocument {
	
	private Element document = null;
	private GISProject project = null;
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
		getDocument(doc);
		
		project = new GISProject();

	}
	
	/**
	 *This method return document to CSV2KML class. 
	 * @return Node document
	 */
	public Node getDocument() {
		return document;
	}
	
	/**
	 * THis method return current project.
	 * @return GISProject
	 */
	public GISProject getProject() {
		return project;
	}
	
	/**
	 * This method adding layer to project. (Add GISLayer to GISProject)
	 * @param layer
	 */
	public void addLayer(GISLayer layer) {
		project.addLayer(layer);
	}
	
	/**
	 * This method add all data to document.
	 * @param doc name of general document
	 * @param csvName name of CSV file that we need to translate
	 */
	private void getDocument(Document doc) {
		//add style of points to DOM 
		document.appendChild(getStyle(doc, "red", "http://maps.google.com/mapfiles/ms/icons/red-dot.png"));
		document.appendChild(getStyle(doc,"yellow","http://maps.google.com/mapfiles/ms/icons/yellow-dot.png"));
		document.appendChild(getStyle(doc, "green", "http://maps.google.com/mapfiles/ms/icons/green-dot.png"));
		document.appendChild(getStyle(doc, "purple", "http://maps.google.com/mapfiles/ms/icons/purple-dot.png"));
		document.appendChild(getStyle(doc, "blue", "http://maps.google.com/mapfiles/ms/icons/blue-dot.png"));

	}
	
	/**
	 * This method create node that include style information.
	 * @param doc name of general document
	 * @param id color of point
	 * @param link 
	 * @return node of Style
	 */
	private Node getStyle(Document doc, String id, String link) {
		Element style = doc.createElement("Style");
		style.setAttribute("id", id);
		Element iconStyle = doc.createElement("IconStyle");
		style.appendChild(iconStyle);
		Element icon = doc.createElement("Icon");
		iconStyle.appendChild(icon);
		icon.appendChild(getHref(doc, link));
		return style;
	}
	
	/**
	 * This method create node of link.
	 * @param doc name of general document.
	 * @param link
	 * @return node of link
	 */
	private Node getHref(Document doc, String link) {
		Element node = doc.createElement("href");
		node.appendChild(doc.createTextNode(link));
		return node;
	}
}
