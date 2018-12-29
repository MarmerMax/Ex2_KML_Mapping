package Path2KML;

import java.io.File;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import GameGUI.Path;

/**
 * This class create new KML file from all files that placed in actually input folder.
 * @author Max Marmer
 *
 */
public class Path2KML {

	private String address;		//address of folder when we create file

	protected DocumentBuilderFactory domFactory= null;
	protected DocumentBuilder domBuilder = null;
	private LinkedList<Path> pathList;
	//private final String[] colors = {"red", "yellow", "green", "blue" ,"purple"};

	/**
	 * Construction method. This method create KML file from all CSV files which placed in actually
	 * folder that we get.
	 * @param path way to folder
	 */
	public Path2KML(String fileName, LinkedList<Path> pathList){
		this.pathList = pathList;
		address = fileName;
		try {
			domFactory = DocumentBuilderFactory.newInstance();
			domBuilder = domFactory.newDocumentBuilder();
		} 
		catch (FactoryConfigurationError exp) {
			System.out.println(exp.toString());
		}
		catch (ParserConfigurationException exp) {
			System.out.println(exp.toString());
		}
		catch (Exception exp) {
			System.out.println(exp.toString());
		}
		
		convertAllFiles(",,,,"); // create kml
	}
	
	/**
	 * This method convert from csv to kml.
	 * @param delimetr how break the csv
	 */
	private void convertAllFiles(String delimetr) {

		//create name for kml file
		String kmlName = address;

		try {
			Document newDoc = domBuilder.newDocument();

			//root element
			Element kml = newDoc.createElementNS("http://www.opengis.net/kml/2.2","kml");
			newDoc.appendChild(kml); //doc add root(kml)

			//create new document and add him to our kml
			BuildDocument buildDocument = new BuildDocument(newDoc, kmlName);
			Element document = (Element) buildDocument.getDocument();
			
			BuildFolder buildFolder = new BuildFolder(newDoc, pathList);
			Element folder = (Element) buildFolder.getFolder();
			document.appendChild(folder); //add folder to document
			
			kml.appendChild(document); //add all created data to DOM

			TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();
			aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
			aTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
			aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			Source src = new DOMSource(newDoc);
			Result result = new StreamResult(new File(kmlName));
			aTransformer.transform(src, result);

			System.out.println("KML path Created Successfully..");

		} catch (Exception exp) {
			System.err.println(exp.toString());
		}
	}
}




