package se.miun.toya1800.dt062g.jpaint;

import java.awt.Graphics;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a Rectangle which extends the Shape class upper left point is the
 * start point and the lower right is the end point
 * 
 *
 * @author Tommy Yasi (toya1800)
 * @version 1.0
 * @since 2019-11-17
 */

@XmlRootElement
public class Rectangle extends Shape implements Drawable {
	// Call constructors
	public Rectangle(double x, double y, String color) {
		super(x, y, color);
	}

	public Rectangle(Point p, String color) {
		super(p, color);
	}

	public Rectangle() {
	}

	// Width and height, rectangles own unique methods
	double getWidth() throws MissingEndpointException {
		try {
			return points.get(1).getX() - points.get(0).getX();
		}

		catch (Exception e) {
			throw new MissingEndpointException("Could not get width, missing endpoint");
		}
	}

	double getHeight() throws MissingEndpointException {
		try {
			return points.get(1).getY() - points.get(0).getY();
		}

		catch (Exception e) {
			throw new MissingEndpointException("Could not get height, missing endpoint");
		}
	}

	// Abstract classes from Shape
	double getCircumference() throws MissingEndpointException {
		try {
			return getHeight() * 2 + getWidth() * 2;
		}

		catch (MissingEndpointException e) {
			throw new MissingEndpointException("Could not get circumference, missing endpoint");
		}
	}

	double getArea() throws MissingEndpointException {
		try {
			return getHeight() * getWidth();
		}

		catch (MissingEndpointException e) {
			throw new MissingEndpointException("Could not get area, missing endpoint");
		}
	}

	// Draw
	public void draw() {
		System.out.println(toString());
	}

	public void draw(Graphics g) throws MissingEndpointException {
		int x = (int) points.get(0).getX();
		int y = (int) points.get(0).getY();
		int width = (int) getWidth();
		int height = (int) getHeight();

		// Draw rectangle with color
		String c = getColor();
		g.setColor(getColorObject(c)); // Function take string color and returns Color object
		g.fillRect(x, y, width, height);
	}

	public String toString() {
		// See if there is a value or not
		try {
			return "[start=" + points.get(0) + "; end=" + points.get(1) + "; " + "width=" + getWidth() + "; "
					+ "height=" + getHeight() + "; color=" + getColor() + "]";
		} catch (MissingEndpointException e) {
			e.getMessage();
		}
		return "[start=" + points.get(0) + "; end=N/A  " + "; " + "width=N/A" + "; " + "height=N/A" + "; color="
				+ getColor() + "]";
	}
}