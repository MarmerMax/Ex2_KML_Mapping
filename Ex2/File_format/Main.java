package File_format;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		
		
		Csv2kml file = new Csv2kml(new File("E:\\OOP\\Ex2\\Ex2\\Files\\WigleWifi_2020.csv"));	

		MultiCSV file2 = new MultiCSV(new File("E:\\OOP\\Ex2\\Ex2\\Files"));

	}

}
