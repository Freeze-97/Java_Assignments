package se.miun.toya1800.dt062g.jpaint;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.xml.bind.JAXBException;

/**
 * <h1>JavaFrame</h1> Creates JFrame (windowed) components, simple GUI
 * 
 *
 * @author Tommy Yasi (toya1800)
 * @version 1.0
 * @since 2019-12-31
 */

public class JavaFrame extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;

	// Variables
	private String name;
	private String author;

	// Color buttons
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JButton button4;
	private JButton button5;
	
	// Current selected color as string
	private String selectedColor = "";

	// Box shows color selected
	private JPanel tinySquare = null;
	
	// Rectangle or Circle selected as shape
	String[] shapes = { "Rectangle", "Circle" };
	JComboBox<String> shapeList = new JComboBox<String>(shapes);

	// Mouse position for mouse movement
	private int xPos;
	private int yPos;
	private String fullCoordinates = xPos + ", " + yPos;
	private JLabel textCoord; // Used to update coordinates
	
	// Mouse press, drag and release
	private int sX = -1;
	private int sY = -1;

	// Drawing area
	private DrawingPanel drawArea = new DrawingPanel();

	// Constructor
	public JavaFrame() {
		// Variables
		name = "";
		author = "";
		setTitle("JavaPaintDemo");

		// Close window
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Set icon
		ImageIcon img = new ImageIcon(JavaFrame.class.getResource("paint-logo.png"));
		setIconImage(img.getImage());

		// Size and location
		setLocationRelativeTo(null); // Position it in the middle

		createMenuBar();
		createAndAddContent();
		pack(); // Sets size of frame based on components
	}

	private JPanel createStatusRow() {
		// Shows what color is active
		tinySquare = new JPanel(new BorderLayout());
		tinySquare.setMaximumSize(new Dimension(20, 20));
		tinySquare.setPreferredSize(new Dimension(20, 20));

		JLabel coordinates = new JLabel("Coordinates: ", JLabel.LEFT);
		JLabel color = new JLabel("Selected color: ", JLabel.RIGHT);
		textCoord = new JLabel(fullCoordinates);
		coordinates.setFont(new Font(coordinates.getFont().getFontName(), Font.BOLD, 15));
		color.setFont(new Font(color.getFont().getFontName(), Font.BOLD, 15));
		textCoord.setFont(new Font(textCoord.getFont().getFontName(), Font.BOLD, 15));

		// Show text "Selected" and color box
		JPanel colorPanel = new JPanel(new BorderLayout());
		colorPanel.add(color, BorderLayout.LINE_START);
		colorPanel.add(tinySquare, BorderLayout.LINE_END);

		// coordinate panel, show text "Coordinates" and numbers
		JPanel coordinatesPanel = new JPanel(new BorderLayout());
		coordinatesPanel.add(coordinates, BorderLayout.LINE_START);
		coordinatesPanel.add(textCoord, BorderLayout.LINE_END);

		JPanel statusBar = new JPanel(new BorderLayout());
		statusBar.add(coordinatesPanel, BorderLayout.LINE_START);
		statusBar.add(colorPanel, BorderLayout.LINE_END);

		JPanel fullDrawPanel = new JPanel(new BorderLayout());
		fullDrawPanel.add(statusBar, BorderLayout.PAGE_END);

		return fullDrawPanel;
	}

	private JPanel createToolRow() {
		
		// Create 5 color buttons
		button1 = new JButton();
		button1.setBackground(Color.blue);
		button1.addActionListener(this);

		button2 = new JButton();
		button2.setBackground(Color.yellow);
		button2.addActionListener(this);

		button3 = new JButton();
		button3.setBackground(Color.green);
		button3.addActionListener(this);

		button4 = new JButton();
		button4.setBackground(Color.black);
		button4.addActionListener(this);

		button5 = new JButton();
		button5.setBackground(Color.red);
		button5.addActionListener(this);

		JPanel colorPanel = new JPanel(new GridLayout(1, 0));
		colorPanel.add(button1);
		colorPanel.add(button2);
		colorPanel.add(button3);
		colorPanel.add(button4);
		colorPanel.add(button5);

		// Box were user chooses shape
		shapeList.setPreferredSize(new Dimension(100, 50));
		shapeList.setMaximumSize(new Dimension(100, 50));
		shapeList.addActionListener(this);

		JPanel comboBoxPanel = new JPanel(new BorderLayout());
		comboBoxPanel.add(shapeList);
		comboBoxPanel.setPreferredSize(new Dimension(100, 50));
		comboBoxPanel.setMaximumSize(new Dimension(100, 50));
		comboBoxPanel.setBackground(Color.CYAN);

		JPanel fullPanel = new JPanel();
		fullPanel.setLayout(new BoxLayout(fullPanel, BoxLayout.LINE_AXIS));
		fullPanel.add(colorPanel);
		fullPanel.add(comboBoxPanel);
		return fullPanel;
	}

	private void createAndAddContent() {
		JPanel toolbar = createToolRow();
		add(toolbar, BorderLayout.PAGE_START);

		drawArea.addMouseMotionListener(this);
		drawArea.addMouseListener(this);
		drawArea.setPreferredSize(new Dimension(600, 300));
		add(this.drawArea, BorderLayout.CENTER);

		JPanel statusbar = createStatusRow();
		add(statusbar, BorderLayout.PAGE_END);
	}

	private void createMenuBar() {
		// Menu bar
		JMenuBar menuBar = new JMenuBar();

		// File menu
		JMenu jmFile = new JMenu("File");
		JMenuItem jmiNew = new JMenuItem("New...");
		JMenuItem jmiSaveAs = new JMenuItem("Save as...");
		JMenuItem jmiLoad = new JMenuItem("Load...");
		JMenuItem jmiInfo = new JMenuItem("Info");
		JMenuItem jmiExit = new JMenuItem("Exit");

		// Add menu components to the File-menu
		jmFile.add(jmiNew);
		jmFile.add(jmiSaveAs);
		jmFile.add(jmiLoad);
		jmFile.add(jmiInfo);
		jmFile.addSeparator();
		jmFile.add(jmiExit);
		menuBar.add(jmFile); // Add to the menu bar

		// Edit menu
		JMenu jmEdit = new JMenu("Edit");
		JMenuItem jmiName = new JMenuItem("Name...");
		JMenuItem jmiAuthor = new JMenuItem("Author...");

		// Add menu components to the Edit-menu
		jmEdit.add(jmiName);
		jmEdit.add(jmiAuthor);
		menuBar.add(jmEdit); // Add to the menu bar

		// Add action listeners for all menu items
		jmiNew.addActionListener(this);
		jmiSaveAs.addActionListener(this);
		jmiLoad.addActionListener(this);
		jmiInfo.addActionListener(this);
		jmiExit.addActionListener(this);
		jmiName.addActionListener(this);
		jmiAuthor.addActionListener(this);

		// Add menu bar to the frame
		setJMenuBar(menuBar);
	}

	private boolean askNameTitle() {
		String tmpName = JOptionPane.showInputDialog("Enter name of the drawing:");
		if (tmpName.equals(null)) {
			return false;
		}
		name = tmpName;
		return true;
	}

	private boolean askAuthorTitle() {
		String tmpAuthor = JOptionPane.showInputDialog("Enter author of the drawing:");
		if (tmpAuthor.equals(null)) {
			return false;
		}
		author = tmpAuthor;
		return true;
	}

	private void updateNameTitle() {
		if (author.equals("")) {
			setTitle("JavaPainDemo - " + name);
		} else if (!author.equals("")) {
			setTitle("JavaPainDemo - " + name + " by " + author);
		}
	}

	private void updateAuthorTitle() {
		if (name.equals("")) {
			setTitle("JavaPainDemo - " + author);
		} else if (!name.equals("")) {
			setTitle("JavaPainDemo - " + name + " by " + author);
		}
	}
	
	private String getDrawingTitle() {
		String title = "(None given title)";
		String tmpName = drawArea.getDrawing().getName();
		String tmpAuthor = drawArea.getDrawing().getAuthor();
		
		if(tmpName.equals("") && !tmpAuthor.equals("")) {
			title = tmpAuthor;
		} 
		if(!tmpName.equals("") && tmpAuthor.equals("")) {
			title = tmpName;
		}
		if(!tmpName.equals("") && !tmpAuthor.equals("")){
			title = tmpName + " by " + tmpAuthor;
		}
		return title;
	}
	

	// Handle menu item action events
	public void actionPerformed(ActionEvent e) {
		String comStr = e.getActionCommand();

		if (comStr.equals("New...")) {
			if (askAuthorTitle()) {
				updateAuthorTitle();
				if (askNameTitle()) {
					updateNameTitle();
				}
			} else {
				updateAuthorTitle();
			}
			Drawing drawing = new Drawing(name, author);
			drawArea.setDrawing(drawing);
		}

		if (comStr.equals("Save as...")) {
			String tmpFileName;
			if (author.equals("") && name.equals("")) {
				tmpFileName = JOptionPane.showInputDialog("Save drawing to:", ".xml");
			} else if (author.equals("") && !name.equals("")) {
				tmpFileName = JOptionPane.showInputDialog("Save drawing to:", name + ".xml");
			} else if (!author.equals("") && name.equals("")) {
				tmpFileName = JOptionPane.showInputDialog("Save drawing to:", author + ".xml");
			} else {
				tmpFileName = JOptionPane.showInputDialog("Save drawing to:", name + " by " + author + ".xml");
			}
			try {
				if(tmpFileName.equals(null)) {
					return;
				}
				FileHandler.saveToXML(drawArea.getDrawing(), tmpFileName);
			} catch (JAXBException e1) {
				System.err.println(e1.getMessage());
			}
		}

		if (comStr.equals("Load...")) {
			String tmpFileName = JOptionPane.showInputDialog("Load drawing from");
			if(tmpFileName.equals(null)) {
				return;
			}
			Drawing drawing = new Drawing();
			try {
				drawing = FileHandler.loadFromXML(tmpFileName);
			} catch (JAXBException e1) {
				System.err.println(e1.getMessage());
			}
			if(drawArea.getDrawing().equals(null)) {
				drawArea.setDrawing(drawing);
			} else {
				drawArea.addDrawing(drawing);
			}
		}
		
		if (comStr.equals("Info")) {
			String drawName = getDrawingTitle();
			int numShapes = drawArea.getDrawing().getSize();
			double totalArea = 0;
			try {
				totalArea = drawArea.getDrawing().getTotalArea();
			} catch (MissingEndpointException e1) {
				// TODO Auto-generated catch block
				System.err.println(e);
			}
			double totalCircumference = 0;
			try {
				totalCircumference = drawArea.getDrawing().getTotalCircumference();
			} catch (MissingEndpointException e1) {
				// TODO Auto-generated catch block
				System.err.println(e);
			}
			
			JOptionPane.showMessageDialog(null, drawName + "\nNumber of shapes: " + numShapes +
					"\nTotal area: " + totalArea + "\nTotal circumference: " + totalCircumference, 
					"Info", JOptionPane.INFORMATION_MESSAGE);
		}
		
		if (comStr.equals("Exit")) {
			System.exit(0);
		}

		if (comStr.equals("Name...")) {
			askNameTitle();
			updateNameTitle();
			drawArea.getDrawing().setName(name);
		}
		if (comStr.equals("Author...")) {
			askAuthorTitle();
			updateAuthorTitle();
			drawArea.getDrawing().setAuthor(author);
		}

		// Handling colors, getSource returns variable name
		if (e.getSource() == button1) {
			tinySquare.setBackground(Color.blue);
			selectedColor = "blue";
		} else if (e.getSource() == button2) {
			tinySquare.setBackground(Color.yellow);
			selectedColor = "yellow";
		} else if (e.getSource() == button3) {
			tinySquare.setBackground(Color.green);
			selectedColor = "green";
		} else if (e.getSource() == button4) {
			tinySquare.setBackground(Color.black);
			selectedColor = "black";
		} else if (e.getSource() == button5) {
			tinySquare.setBackground(Color.red);
			selectedColor = "red";
		}
	}

	public void mouseClicked(MouseEvent e) {
		sX = e.getX();
		sY = e.getY();
		String shapeType = String.valueOf(shapeList.getSelectedItem()); // Rectangle or Circle object
		
		// If no color is selected, do not draw
		if(!selectedColor.equals("")) {
        Shape newShape;
        if(shapeType.equals("Rectangle")) {
            newShape = new Rectangle(sX, sY, selectedColor);
        }
        else {
            newShape = new Circle(sX, sY, selectedColor);
        }
        newShape.addPoint(sX + 30, sY + 30);
        drawArea.addShape(newShape);
		}
    }

	
	public void mouseMoved(MouseEvent e) {
		xPos = e.getX();
		yPos = e.getY();
		fullCoordinates = xPos + ", " + yPos;
		textCoord.setText(fullCoordinates);
	}
	
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
}
