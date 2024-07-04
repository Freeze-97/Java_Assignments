package se.miun.toya1800.dt062g.jpaint;

import javax.xml.bind.JAXBException;

/**
 * <h1>Assignment 5</h1> This application creates a <code>Drawing</code> with a
 * name, author and different shapes in it. It then saves the drawing to XML,
 * clear the drawing and then loads a drawing from XML.
 *
 * @author Tommy Yasi (toya1800)
 * @version 1.0
 * @since 2019-12-08
 */

public class Assignment5 {
	public static void main(String[] args) {
		testDrawing();
	}

	private static void testDrawing() {
		// Create a drawing with a name and author.
		System.out.println("Create a drawing...\n");
		Drawing d1 = new Drawing("Anders", "Andersson");

		// Create at least 5 shapes with end points
		Shape s1 = new Circle(20, 40, "yellow");
		s1.addPoint(60, 90);
		Shape s2 = new Circle(35, 65, "blue");
		s2.addPoint(75, 100);
		Shape s3 = new Rectangle(10, 25, "green");
		s3.addPoint(30, 50);
		Shape s4 = new Rectangle(30, 55, "black");
		s4.addPoint(45, 80);
		Shape s5 = new Circle(25, 50, "green");
		s5.addPoint(35, 70);

		// Add shapes to the drawing
		d1.addShape(s1);
		d1.addShape(s2);
		d1.addShape(s3);
		d1.addShape(s4);
		d1.addShape(s5);

		// Print the drawing
		d1.draw();

		// Save the drawing to XML
		final String fileName = "toya1800_drawing.xml";
		System.out.println("\nSave the drawing to " + fileName + "...");
		try {
			FileHandler.saveToXML(d1, fileName);
		} catch (JAXBException e) {
			System.err.println(e.getMessage());
		}

		// Clear and print
		System.out.println("\nClearing the drawing and then draw it....");
		d1.clear();
		d1.draw();

		// Load drawing from XML
		System.out.println("\nLoad drawing from " + fileName + "...\n");
		try {
			d1 = FileHandler.loadFromXML(fileName);
		} catch (JAXBException e) {
			System.err.println(e.getMessage());
		}

		// Print the drawing
		d1.draw();
	}
}
