/**
* <h1>Assignment 1</h1>
* This application allows the user to enter data for a circle
* or rectangle. The circumference and area are then calculated
* and the result is displayed to the standard output.
*
* @author Tommy Yasi (toya1800)
* @version 1.0
* @since 2019-11-10
*/

package se.miun.toya1800.dt062g.jpaint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Assignment1 {

	public static void main(String[] args) throws IOException {
		boolean loop = true; // Used to see if user wants to continue again
		do {

			// Print out the options for the user
			System.out.println("1. rectangle");
			System.out.println("2. circle");
			System.out.println("3. exit");
			System.out.print("Enter your choice : ");
			String choice; // Input from user
			// Use to get input from user
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			// Read input from user
			choice = input.readLine();

			switch (choice) {
			case "rectangle":
				calculateRectangle();
				break;
			case "circle":
				calculateCircle();
				break;
			case "exit":
				loop = false;
				System.out.println("Bye have a great day!");
				break;
			default:
				System.out.println("Invalid input!");
			}
		} while (loop == true);
	}

	// Methods
	private static void calculateRectangle() throws IOException {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter width: ");
		double width = in.nextDouble();

		System.out.print("Enter height: ");
		double height = in.nextDouble();

		// Calculate circumference
		double circumference = width * 2 + height * 2;

		// Calculate area
		double area = width * height;

		// Print out results
		System.out.println("circumference:" + circumference);
		System.out.println("area:" + area);
	}

	private static void calculateCircle() throws IOException {
		Scanner in = new Scanner(System.in);
		System.out.print("Enter radius: ");
		double radius = in.nextDouble();

		// Calculate circumference
		final double PI = 3.14;
		double circumference = 2 * PI * radius;

		// Calculate area
		double area = PI * radius * radius;

		// Print out results
		System.out.println("circumference:" + circumference);
		System.out.println("area:" + area);
	}
}
