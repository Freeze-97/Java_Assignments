package se.miun.toya1800.dt062g.jpaint;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * This class will save and load Drawing-objects Using JAXB
 *
 * @author Tommy Yasi (toya1800)
 * @version 1.0
 * @since 2019-12-08
 */

public class FileHandler {

	public static void saveToXML(Drawing drawing, String fileName) throws JAXBException {
		// Check if fileName ends with ".xml"
		if (!fileName.endsWith(".xml")) {
			fileName += ".xml";
		}

		// Save drawing to file
		try {
			JAXBContext context = JAXBContext.newInstance(Drawing.class);
			Marshaller marshaller = context.createMarshaller();

			// Check if formating needed
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Store XML to the file
			File file = new File(fileName);

			// XML to file-system
			marshaller.marshal(drawing, file);
		} catch (JAXBException e) {
			System.err.println(e.getMessage());
		}
	}

	public void saveToXML(Drawing drawing) throws JAXBException {
		String name = drawing.getName();
		String author = drawing.getAuthor();
		String fileName = name + " by " + author + ".xml";

		// Call saveToXML
		saveToXML(drawing, fileName);
	}

	public static Drawing loadFromXML(String fileName) throws JAXBException {
		try {
			JAXBContext context = JAXBContext.newInstance(Drawing.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();

			// Read from file and cast to correct type
			Drawing drawing = (Drawing) unmarshaller.unmarshal(new File(fileName));
			return drawing;
		} catch (JAXBException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
}
