package File_format;


import java.io.File;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.TransformerFactory;
/**
 * This class translate from CSV type to KML type and create new KML file. 
 * @author Max Marmer
 *
 */
public class Csv2kml {
	
	protected DocumentBuilderFactory domFactory= null;
	protected DocumentBuilder domBuilder = null;
	
	/**
	 * This construction method get name of CSV file and turn him to KML type.
	 * @param name name of CSV file can be in two types
	 */
	public Csv2kml(Object name) {
		
		//Constructor can get name from user in two types. String or File.
		String csvName = "";
		if(!name.getClass().equals("String")) {
			csvName = name.toString();
		}
		
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
		convertFile(csvName, ",,,,");
	}

	/**
	 * This private function that call to other function and classes for create new KML file.
	 * @param csvName name of CSV file that we need to translate
	 * @param kmlName name of KML file that we need to create
	 * @param delimetr how we want to split this file
	 */
	private void convertFile(String csvName, String delimetr) {
		
		String kmlName = csvName.substring(0, csvName.length() - 3) + "kml";
		
		try {
			Document newDoc = domBuilder.newDocument();

			//root element
			Element kml = newDoc.createElementNS("http://www.opengis.net/kml/2.2","kml");
			newDoc.appendChild(kml); //doc add root(kml)
			
			//create new document and add him to our kml
			BuildDocument buildDocument = new BuildDocument(newDoc);
			
			//create new folder from csv file which we get
			BuildFolder buildFolder = new BuildFolder(newDoc, csvName);
			
			//add layer to project
			buildDocument.addLayer(buildFolder.getLayer());
			
			//casting
			Element document = (Element) buildDocument.getDocument();
			Element folder = (Element) buildFolder.getFolder();
			
			document.appendChild(folder);		//add folder to document
			kml.appendChild(document);			//add document to kml file
			
	
            TransformerFactory tranFactory = TransformerFactory.newInstance();
			Transformer aTransformer = tranFactory.newTransformer();
			aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
			aTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
			aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			Source src = new DOMSource(newDoc);
			Result result = new StreamResult(new File(kmlName));
			aTransformer.transform(src, result);
 
            System.out.println("\nKML DOM Created Successfully..");
 	
		} catch (Exception exp) {
			System.err.println(exp.toString());
		}
	}
}
