package GIS;

import java.util.StringTokenizer;
import Geom.GeomElement;
import Geom.Geom_element;
import Geom.Point3D;

/**
 * This class read line from CSV file and create object with all fields data.
 * @author Max Marmer
 * 
 */
public class GISElement implements GIS_element{

	private GeomElement element;

	private MetaData data;
	private String MAC;
	private String SSID;
	private String AuthMode;
	private String FirstSeen;
	private String Channel;
	private String RSSI;
	private String CurrentLatitude;
	private String CurrentLongtitude;
	private String AltitudeMeters;
	private String AccuracyMeters;
	private String Type;
	private boolean isValid;
	/**
	 * This method create GIS element with all fields data. 
	 * @param csvRow line from CSV document
	 */
	public GISElement(String csvRow) {

		StringTokenizer stringValues = new StringTokenizer(csvRow, ","); // break the row to tokens
		int fieldCount = stringValues.countTokens(); //count fields

		if(fieldCount == 11) { //if we have all needed fields so we can go on
			data = new MetaData(); // create meta data
			isValid = true; //this line exist

			String [] values = new String[fieldCount]; //create array for keeping all fields data
			for(int i = 0; i < fieldCount; i++) {
				values[i] = String.valueOf(stringValues.nextElement());  //put fields data to array
			}
			this.MAC = values[0];
			this.SSID = values[1];
			this.AuthMode = values[2];
			this.FirstSeen = values[3];
			this.Channel =values[4];
			this.RSSI = values[5];
			this.CurrentLatitude = values[7];
			this.CurrentLongtitude = values[6];
			this.AltitudeMeters = values[8];
			this.AccuracyMeters = values[9];
			this.Type = values[10];
			
			//create geom element
			element = (GeomElement) getGeom();
			
		} else {//if we have another amount of fields so line is incorrect
			isValid = false;
		}

	}

	//getters
	public String getMAC() {return this.MAC;}
	public String getSSID() {return this.SSID;}
	public String getAuthMode() {return this.AuthMode;}
	public String getFirstSeen() {return this.FirstSeen;}
	public String getChannel() {return this.Channel;}
	public String getRSSI() {return this.RSSI;}
	public String getCurrentLatitude() {return this.CurrentLatitude;}
	public String getCurrentLongtitude() {return this.CurrentLongtitude;}
	public String getAltitudeMeters() {return this.AltitudeMeters;}
	public String getAccuracyMeters() {return this.AccuracyMeters;}
	public String getType() {return this.Type;}
	public boolean getIsValid() {return this.isValid;}

	@Override
	public Geom_element getGeom() {
		double x = Double.parseDouble(CurrentLatitude);
		double y = Double.parseDouble(CurrentLongtitude);
		double z = Double.parseDouble(AltitudeMeters);

		return new GeomElement(new Point3D(x, y, z));
	}

	@Override
	public Meta_data getData() {
		return this.data;
	}

	@Override
	public void translate(Point3D vec) {
		Point3D temp1 = lla2ecef(element.getMyPoint());
		Point3D temp2 = lla2ecef(vec);
		element = new GeomElement(new Point3D(temp1.x() + temp2.x(),
				temp1.y() + temp2.y(), temp1.z() + temp2.z()));
	}

	/**
	 * This method convert point from polar coordinates to cartesian
	 * @param point point which need to convert to cartesian
	 * @return Point in cartesian
	 */
	private Point3D lla2ecef(Point3D point) {
		final double a = 6378137; // radius
		final double e = 8.1819190842622e-2;  // eccentricity

		final double asq = Math.pow(a,2);
		final double esq = Math.pow(e,2);
		double lat = point.x();
		double lon = point.y();
		double alt = point.z();

		double N = a / Math.sqrt(1 - esq * Math.pow(Math.sin(lat),2) );

		double x = (N+alt) * Math.cos(lat) * Math.cos(lon);
		double y = (N+alt) * Math.cos(lat) * Math.sin(lon);
		double z = ((1-esq) * N + alt) * Math.sin(lat);
		
		return new Point3D(x, y, z);
	}

}
