package File_format;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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

/**
 * This class create new KML file from all files that placed in actually input folder.
 * @author Max Marmer
 *
 */
public class MultiCSV {

	private Set<File> fileTree;	//set of files in folder
	private String address;		//address of folder when we create file

	protected DocumentBuilderFactory domFactory= null;
	protected DocumentBuilder domBuilder = null;

	/**
	 * Construction method. This method create KML file from all CSV files which placed in actually
	 * folder that we get.
	 * @param path way to folder
	 */
	public MultiCSV(File path){

		address = path.toString();		//save address of folder 
		createTree(path);				//create set of files

		//build DOM
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
		String kmlName = address + "\\AllProjects.kml";

		try {
			Document newDoc = domBuilder.newDocument();

			//root element
			Element kml = newDoc.createElementNS("http://www.opengis.net/kml/2.2","kml");
			newDoc.appendChild(kml); //doc add root(kml)

			//create new document and add him to our kml
			BuildDocument buildDocument = new BuildDocument(newDoc);
			Element document = (Element) buildDocument.getDocument();

			//run all files with iterator
			Iterator it = fileTree.iterator();
			while(it.hasNext()) {
				//create new folder from current csv file
				BuildFolder buildFolder = new BuildFolder(newDoc, it.next().toString());
				Element folder = (Element) buildFolder.getFolder();
				document.appendChild(folder); //add folder to document
				
				buildDocument.addLayer(buildFolder.getLayer());// add layer to document
			}

			kml.appendChild(document); //add all created data to DOM

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
	
	/**
	 * This method call to function realFolder for create set of files.
	 * @param path address of the received folder
	 */
	private void createTree(File path) {
		fileTree = readFolder(path);
		cleanSet(); //
	}
	
	/**
	 * This function remove from set all files except CSV files.
	 */
	private void cleanSet() {
		Iterator<File> it = fileTree.iterator();
		while(it.hasNext()) {
			File temp = it.next();
			String fileName = temp.getName();
			if(!fileName.substring(fileName.length() - 3, fileName.length()).equals("csv")) {
				it.remove();
			}
		}
	}

	/**
	 * This method run recursively in folder and put all files to set.
	 * @param path address of the received folder
	 * @return set with files
	 */
	private Set<File> readFolder(File path){
		Set<File> tempSet = new HashSet<File>();
		if(path==null||path.listFiles()==null){
			return null;
		}
		for (File entry : path.listFiles()) {
			if (entry.isFile()) tempSet.add(entry);
			else tempSet.addAll(readFolder(entry));
		}
		return tempSet;
	}
	
	/**
	 * Get collection of this class.
	 * @return set
	 */
	public Set<File> getCollection(){
		return fileTree;
	}
}



