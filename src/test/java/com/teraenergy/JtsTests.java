package com.teraenergy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

class JtsTests {
	GeometryFactory geometryFactory = new GeometryFactory();
	@Test
	public void innerPointTest() {

		String polygonPoints = "126.567668343956 33.451276403135246,126.56935715259203 33.45123719996867,126.56834423197559 33.451621366446425,126.56966217559021 33.45045386564941,126.567668343956 33.451276403135246";
		String[] polygonPointArr = polygonPoints.split(",");

		Coordinate[] coordinates = new Coordinate[polygonPointArr.length];
//		Coordinate[] coordinates = {
//				new Coordinate(126.567668343956, 33.451276403135246),
//				new Coordinate(126.56966217559021, 33.45045386564941),
//				new Coordinate(126.56935715259203, 33.45123719996867),
//				new Coordinate(126.56834423197559, 33.451621366446425),
//				new Coordinate(126.567668343956, 33.451276403135246),
//		};
		for (int i = 0; i < polygonPointArr.length; i++) {
			String[] test = polygonPointArr[i].split(" ");
			coordinates[i] = new Coordinate(Double.parseDouble(test[0]), Double.parseDouble(test[1]));
		}

		Polygon testPoly = geometryFactory.createPolygon(coordinates);
		Point polyInnerPoint = geometryFactory.createPoint(new Coordinate(126.56866907996265,33.451180712229686));
		Point polyInnerPoint2 = geometryFactory.createPoint(new Coordinate(126.56915449563454,33.450893887400085));
		Point polyInnerPoint3 = geometryFactory.createPoint(new Coordinate(126.56826037812841,33.45117928982703));
		Point polyInnerPoint4 = geometryFactory.createPoint(new Coordinate(126.56816407185906,33.451079778302194));
		Point polyOuterPoint = geometryFactory.createPoint(new Coordinate(126.56916190732109,33.45157011423124));
		Point polyOuterPoint2 = geometryFactory.createPoint(new Coordinate(126.56652326588099,33.45011835549815));
		Point polyOuterPoint3 = geometryFactory.createPoint(new Coordinate(126.56601552728885,33.450567380165516));

		boolean innerWithin = polyInnerPoint.within(testPoly);
		boolean innerWithin2 = polyInnerPoint2.within(testPoly);
		boolean innerWithin3 = polyInnerPoint3.within(testPoly);
		boolean innerWithin4 = polyInnerPoint4.within(testPoly);
		boolean outerWithin = polyOuterPoint.within(testPoly);
		boolean outerWithin2 = polyOuterPoint2.within(testPoly);
		boolean outerWithin3 = polyOuterPoint3.within(testPoly);

		System.out.println("innerWithin :" + innerWithin);
		System.out.println("innerWithin2 :" + innerWithin2);
		System.out.println("innerWithin3 :" + innerWithin3);
		System.out.println("innerWithin4 :" + innerWithin4);
		System.out.println("outerWithin :" + outerWithin);
		System.out.println("outerWithin2 :" + outerWithin2);
		System.out.println("outerWithin3 :" + outerWithin3);
	}
}