package Coords;

import Geom.GeomElement;
import Geom.Point3D;

/**
 * This class implements interface coords_converter.
 * In this class write function for work with point.
 * @author Max Marmer
 *
 */
public class MyCoords implements coords_converter{

	private final double radius = 6378137;

	/**
	 * This method convert point from polar coordinates to cartesian
	 * @param point point which need to convert to cartesian
	 * @return Point in cartesian
	 */
	private Point3D llaToCartesian(Point3D point) {
		double a = radius;
		final double e = 8.1819190842622e-2;  // eccentricity

		final double asq = Math.pow(a,2);
		final double esq = Math.pow(e,2);
		double lat = Math.toRadians(point.x());
		double lon = Math.toRadians(point.y());
		double alt = point.z();

		double N = a / Math.sqrt(1 - esq * Math.pow(Math.sin(lat),2) );

		double x = (N+alt) * Math.cos(lat) * Math.cos(lon);
		double y = (N+alt) * Math.cos(lat) * Math.sin(lon);
		double z = ((1-esq) * N + alt) * Math.sin(lat);

		return new Point3D(x, y, z);
	}


	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		Point3D temp1 = llaToCartesian(gps);
		double x = local_vector_in_meter.x();
		double y = local_vector_in_meter.y();
		double z = local_vector_in_meter.z();

		return new Point3D(temp1.x() + x, temp1.y() + y, temp1.z() + z);
	}

	@Override
	public double distance3d(Point3D p1, Point3D p2) {
		double dLat = Math.toRadians(p1.x()-p2.x());
		double dLon = Math.toRadians(p1.y()-p2.y());
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(p2.x())) * Math.cos(Math.toRadians(p1.x())) * 
				Math.sin(dLon/2) * Math.sin(dLon/2); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		return radius * c;   
	}

	@Override
	public Point3D vector3D(Point3D p1, Point3D p2) {
		double b = radius + p2.z();
		double c = radius + p1.z();

		double b2 = b * b;
		double c2 = c * c;
		double bc2 = 2 * b * c;	    

		double z = p2.z() - p1.z();
		double y = Math.sqrt(b2 + c2 - bc2*Math.cos(Math.toRadians(p2.y() - p1.y())));
		double x = Math.sqrt(b2 + c2 - bc2*Math.cos(Math.toRadians(p2.x() - p1.x())));

		return new Point3D(x, y, z);
	}

	@Override
	public double[] azimuth_elevation_dist(Point3D p1, Point3D p2) {
		//find distance
		double dLat = Math.toRadians(p1.x()-p2.x());
		double dLon = Math.toRadians(p1.y()-p2.y());
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(p2.x())) * Math.cos(Math.toRadians(p1.x())) * 
				Math.sin(dLon/2) * Math.sin(dLon/2); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		double dist = radius * c;   

		//find azimuth
		dLat = p2.x() - p1.x();
		dLon = p2.y() - p1.y();
		double tetha = Math.atan2(Math.sin(dLon) * Math.cos(p2.x()),
				Math.cos(p1.x()) * Math.sin(p2.x()) - Math.sin(p1.x()) * Math.cos(p2.x()) * Math.cos(dLon));
		if(tetha < 0) {
			tetha = Math.toDegrees(tetha) + 360;
		}

		//elevation
		double elev = p2.z() - p1.z();

		double arr[] = {tetha, elev, dist};
		return arr;
	}

	@Override
	public boolean isValid_GPS_Point(Point3D p) {
		if(p.x() < -180 && p.x() > 180) {
			return false;
		}
		if(p.y() < -90 || p.y() > 90) {
			return false;
		}
		if(p.z() < -450) {
			return false;
		}
		return true;
	}

}
