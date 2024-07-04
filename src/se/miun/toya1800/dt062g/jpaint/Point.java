package se.miun.toya1800.dt062g.jpaint;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents coordinate system with x and y Includes constructor with
 * parameters and an empty one
 *
 * @author Tommy Yasi (toya1800)
 * @version 1.0
 * @since 2019-11-17
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Point {
	@XmlElement
	private double y;
	@XmlElement
	private double x;

	// Empty constructor
	public Point() {}

	// Constructor with arguments
	public Point(double x, double y) {
		this.y = y; // "this" refers to the first variables in this class
		this.x = x;
	}

	// Set and get
	void setY(double y) {
		this.y = y;
	}

	double getY() {
		return y;
	}

	void setX(double x) {
		this.x = x;
	}

	double getX() {
		return x;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}