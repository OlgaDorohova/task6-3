package my.home.programming6.archive.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import my.home.programming6.archive.exception.DaoException;

public class FileMethods {
	
	public static File openFile(String fileName) throws DaoException {
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				createXML(fileName);
			} catch (Exception e) {
				throw new DaoException(fileName, e);
			}

		}

		return file;

	}

	public static void createXML(String fileName) throws DaoException {

		String folderTemp[] = fileName.split("/");
		String fileNameTemp = "";
		File file;

		for (String s : folderTemp) {
			fileNameTemp += s;
			file = new File(fileNameTemp);
			if (!file.exists()) {
				if (!fileNameTemp.contains(".")) {
					file.mkdir();
				} else {
					try {
						file.createNewFile();
					} catch (Exception e) {
						throw new DaoException("File couldn't created", e);
					}
				}
				break;
			}
			fileNameTemp += "/";
		}
	}
	
	public static boolean writeXml(Document document, File file) throws DaoException {
		TransformerFactory factory = TransformerFactory.newInstance();

		try {
			Transformer transformer = factory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new FileWriter(file));
			transformer.transform(source, result);
			return true;
		} catch (TransformerException | IOException e) {
			throw new DaoException(e);

		}

	}


}
