package GIS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class get name of CSV file and turn him to data. 
 * Layer composed from many others GIS element so we need use array list for keep all elements.
 * @author Max Marmer
 *
 */
public class GISLayer implements GIS_layer{

	private ArrayList<GISElement> layer;
	private MetaData data;
	
	
	/**
	 * Construction method. Create array list and keep inside all him elements.
	 * @param csvName name of CSV file which we need translate to data.
	 */
	public GISLayer(String csvName) {
		
		layer = new ArrayList<>();
		data = new MetaData();
		
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(csvName));//read from file
			String curline = csvReader.readLine(); // first row is garbage
			curline = csvReader.readLine(); //second row is garbage
			
			while((curline = csvReader.readLine()) != null) { //run all lines
				GISElement newLine = new GISElement(curline); //create GIS element
				if(newLine.getIsValid()) { //if element is valid add him to array list
					layer.add(newLine);
				}
			}
			csvReader.close();
		} 
		catch (FileNotFoundException e) {
			System.err.println(e.toString());
		}
		catch(IOException e) {
			System.err.println(e.toString());
		}
	}
	/**
	 * This method return layer.
	 * @return layer from array list
	 */
	public ArrayList<GISElement> getLayer(){
		return this.layer;
	}

	@Override
	public Meta_data get_Meta_data() {
		return this.data;
	}

}
