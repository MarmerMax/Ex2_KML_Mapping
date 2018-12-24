package GIS;

import Geom.Point3D;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

//import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

public class MetaData implements Meta_data{


	private long UTC; // timestamp
	private String Date; // yyyy-MM-dd'T'HH:mm:ss.SSS zzz

	public MetaData() {
		Date date = new Date();
		this.UTC = date.getTime();
		this.Date = createString(date);
	}
	
	private String createString(Date date) {
		final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS zzz";
		final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
		final TimeZone utc = TimeZone.getTimeZone("UTC");
		sdf.setTimeZone(utc);
		return sdf.format(date);
	}
	
	public String toString() {
		return Date;
	}


	@Override
	public long getUTC() {
		return UTC;
	}

	@Override
	public Point3D get_Orientation() {/////////////
		return null;
	}


}
