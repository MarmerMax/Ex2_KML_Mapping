package GIS;

import java.util.ArrayList;

/**
 * This class create project that include others layers.
 * For keep some amount of layers we need array list.
 * @author Max Marmer
 *
 */
public class GISProject implements GIS_project{
	
	private ArrayList<GISLayer> project;
	private MetaData data;
	
	/**
	 * Construction method. Create GIS project that include some amount of layers.
	 * @param csvName name of CSV file
	 */
	public GISProject() {
		data = new MetaData();
		try {
			project = new ArrayList<GISLayer>(); //create array listFiles
			
			//project.add(new GISLayer(csvName)); //project add new layer that will read from csv file
		}
		catch(Exception exp) {
			System.err.println(exp.toString());
		}
	}
	
	/**
	 * This method add GISlayer to this GISProject.
	 * @param layer GISLayer
	 */
	public void addLayer(GISLayer layer) {
		project.add(layer);
	}
	
	/**
	 * This method return project.
	 * @return project
	 */
	public ArrayList<GISLayer> getProject(){
		return project;
	}

	@Override
	public Meta_data get_Meta_data() {
		return data;
	}

}
