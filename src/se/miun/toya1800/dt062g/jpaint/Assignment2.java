package se.miun.toya1800.dt062g.jpaint;

import java.awt.Graphics;

/**
 * <h1>Assignment 2</h1> This application creates different shapes and calls
 * various methods to print circumference, print area and draw the shapes to the
 * standard output.
 * <p>
 * Giving proper comments in your program makes it more user friendly and it is
 * assumed as a high quality code.
 * 
 *
 * @author Tommy Yasi (toya1800)
 * @version 1.0
 * @since 2019-11-17
 */
public class Assignment2 {

	public static void main(String[] args) {
		testRectangle();
		System.out.println(); // new line
		testCircle();
	}

	private static void testRectangle() {
		// Create two rectangles. Store one as a Rectangle and
		// one as a Shape. Draw them.
		Rectangle r1 = new Rectangle(new Point(0, 0), "#0000ff"); // blue
		Shape s1 = new Rectangle(2, -5, "red");
		System.out.println("Drawing two newly created rectangles...");
		r1.draw();
		s1.draw();

		// Print the area of the rectangle
		try {
			System.out.println("\nThe area of the rectangle is: " + r1.getArea());
		} catch (MissingEndpointException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Add end points to the rectangles and draw them again
		Point p1 = new Point(10, 10);
		System.out.println("\nAdding " + p1 + " as end point to the two rectangles...");
		r1.addPoint(p1);
		s1.addPoint(p1);
		r1.draw();
		s1.draw();

		// Set new end point to rectangle r1 by calling addPoint with a
		// new value and then print the new width
		r1.addPoint(5, 5);
		double widthR1 = 0;
		try {
			widthR1 = r1.getWidth();
		} catch (MissingEndpointException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.out.println("\nChanging end point of r1 to (5.0, 5.0)...");
		System.out.println("The width of r1 is now: " + widthR1); // 5.0

		// It is not possible to call getWidth/Height on s1 since it is stored
		// as an Shape. We first need to cast it to a Rectangle
		Rectangle r2 = (Rectangle) s1;
		System.out.println("\nCasting Shape s1 to a Rectangle (r2)...");
		try {
			System.out.println("The height of r2 (s1) is: " + r2.getHeight());
		} catch (MissingEndpointException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} // 15.0

		// Change end point on s1. Will r2 change as well???
		System.out.println("\nChanging end point of s1 to (12.0, 0.0)...");
		s1.addPoint(12, 0);
		s1.draw();
		r2.draw();

		// Print circumference and area of rectangle s1
		try {
			System.out.println("\nThe circumference of s1 is: " + s1.getCircumference());
		} catch (MissingEndpointException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 30.0
		try {
			System.out.println("The area of s1 is: " + s1.getArea());
		} catch (MissingEndpointException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 50.0
	}

	private static void testCircle() {
		// Create a circle and store it as a Shape. Draw it.
		Shape s1 = new Circle(5, 5, "#000000"); // black
		System.out.println("Drawing a newly created circle...");
		s1.draw();

		// Add a end point to the circle and draw it again
		Point p1 = new Point(8, 9);
		System.out.println("\nAdding " + p1 + " as end point to shape s1...");
		s1.addPoint(p1);
		s1.draw();

		// Print circumference and area of rectangle s1
		try {
			System.out.println("\nThe circumference of s1 is: " + s1.getCircumference());
		} catch (MissingEndpointException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 31.4
		try {
			System.out.println("The area of s1 is: " + s1.getArea());
		} catch (MissingEndpointException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 78.5

		// Set new end point to shape s1 by calling addPoint with a
		// new value and then print the new radius (by doing a cast)
		s1.addPoint(5, 15);
		// double radiusS1 = s1.getRadius(); // will not work

		double radiusS1 = 0;
		try {
			radiusS1 = ((Circle) s1).getRadius();
		} catch (MissingEndpointException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("\nChanging end point of s1 to (5.0, 15.0)...");
		System.out.println("The radius of s1 is now: " + radiusS1); // 10.0
	}
}