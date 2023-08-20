package my.home.programming6.archive.dao;

import java.io.File;

import java.io.IOException;

import java.util.HashSet;

import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import my.home.programming6.archive.bean.Deed;
import my.home.programming6.archive.bean.Deed.Address;
import my.home.programming6.archive.bean.Entity;
import my.home.programming6.archive.exception.DaoException;

public class DeedAccess implements StorageAccess

{
	private String fileName = "archiveData/deeds.xml";

	@Override
	public boolean add(Entity entity) throws DaoException {

		File file = FileMethods.openFile(fileName);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new DaoException(e);
		}

		Document document;

		Deed deed = (Deed) entity;
		if (file.length() == 0) {
			document = builder.newDocument();
			document.setXmlStandalone(true);

			String root = "deeds";
			Element rootElement = document.createElement(root);
			document.appendChild(rootElement);
			rootElement.appendChild(getDeed(document, deed));
		} else {
			try {
				document = builder.parse(file);

				if (hasElement(document, deed)) {
					return false;
				}
				Element rootElement = document.getDocumentElement();

				rootElement.appendChild(getDeed(document, deed));

			} catch (SAXException | IOException e) {
				throw new DaoException(e);
			}
		}

		return FileMethods.writeXml(document, file);

	}

	private static Node getDeed(Document document, Deed deed) {
		Element deedElement = document.createElement("deed");

		deedElement.appendChild(getXmlElement(document, deedElement, "name", deed.getName()));
		deedElement.appendChild(getXmlElement(document, deedElement, "major", deed.getMajor()));
		deedElement.appendChild(getXmlElement(document, deedElement, "phone", deed.getPhoneNumber()));

		deedElement.appendChild(getAddressNode(document, deed.getAddress()));

		return deedElement;
	}

	private static Node getAddressNode(Document document, Address address) {
		Element addressElement = document.createElement("address");
		addressElement.appendChild(getXmlElement(document, addressElement, "country", address.getCountry()));
		addressElement.appendChild(getXmlElement(document, addressElement, "city", address.getCity()));
		addressElement.appendChild(getXmlElement(document, addressElement, "street", address.getStreet()));

		return addressElement;
	}

	private static Node getXmlElement(Document document, Element element, String name, String value) {
		Element nodElement = document.createElement(name);
		nodElement.appendChild(document.createTextNode(value));
		return nodElement;
	}

	@Override
	public boolean delete(Entity t) throws DaoException {
		File file = FileMethods.openFile(fileName);
		boolean isDelete = false;

		Deed deed = (Deed) t;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document;

		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(file);
		} catch (SAXException | IOException | ParserConfigurationException e) {
			throw new DaoException(e);
		}

		Element root = document.getDocumentElement();
		NodeList nodes = document.getElementsByTagName("deed");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);

			String name = element.getElementsByTagName("name").item(0).getTextContent();

			if (name.equals(deed.getName())) {
				Deed tempDeed = buildDeed(element);
				if (deed.equals(tempDeed)) {
					root.removeChild(element);
					isDelete = true;
				}
			}
		}

		try {
			FileMethods.writeXml(document, file);
		} catch (DaoException e) {
			throw new DaoException(e);
		}

		return isDelete;
	}

	private boolean hasElement(Document document, Deed deed) {
		NodeList list = (NodeList) document.getElementsByTagName("deed");
		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			String name = element.getElementsByTagName("name").item(0).getTextContent();

			if (name.equals(deed.getName())) {
				Deed tempDeed = buildDeed(element);
				if (deed.equals(tempDeed)) {
					return true;
				}
			}
		}

		return false;

	}

	@Override
	public Set<Deed> get(String parameter) throws DaoException {
		Set<Deed> deeds = new HashSet<>();

		File file = FileMethods.openFile(fileName);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document;

		try {
			builder = factory.newDocumentBuilder();
			document = builder.parse(file);
			

			XPathFactory xFactory = XPathFactory.newInstance();
			XPath xPath = xFactory.newXPath();
			XPathExpression expression = xPath.compile(parameter);
			
			if(!parameter.contains("address")) {
			
			NodeList list = (NodeList) expression.evaluate(document, XPathConstants.NODESET);
				
			for (int i = 0; i < list.getLength(); i++) {
				Element element = (Element) list.item(i);
				Deed deed = buildDeed((Element)element);
				deeds.add(deed);
			}
			} else {
				
			NodeList rootList = (NodeList) document.getElementsByTagName("deed");
			for(int j = 0; j < rootList.getLength(); j++) {
				Node deedNode = rootList.item(j);
	
				Node node = (Node) expression.evaluate(deedNode, XPathConstants.NODE);
				if(node != null) {
					Deed deed = buildDeed((Element) deedNode);
					deeds.add(deed);
				}
				
			}
			}
			return deeds;
		} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
			throw new DaoException(e);
		}

	}

	@Override
	public Set<Deed> getAll() throws DaoException {
		File file = new File(fileName);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new DaoException(e);
		}

		Document document;
		try {
			document = builder.parse(file);
		} catch (SAXException | IOException e) {
			throw new DaoException("parse document error", e);
		}

		return domDeedParser(document);

	}

	private Set<Deed> domDeedParser(Document document) {
		Set<Deed> deeds = new HashSet<>();

		Element rootElement = document.getDocumentElement();
		NodeList deedList = rootElement.getElementsByTagName("deed");

		for (int i = 0; i < deedList.getLength(); i++) {
			Element deedElement = (Element) deedList.item(i);
			Deed deed = buildDeed(deedElement);
			deeds.add(deed);
		}

		return deeds;
	}

	private Deed buildDeed(Element element) {
		Deed deed = buildPerson(element);
		Address address;

		NodeList addressList = element.getElementsByTagName("address");
		Element addressElement = (Element) addressList.item(0);
		address = buildAddress(addressElement);
		deed.setAddress(address);

		return deed;
	}

	private Deed buildPerson(Element element) {
		Deed deed = new Deed();
		String nameString = getElementTextContent(element, "name");
		deed.setName(nameString);

		deed.setMajor(getElementTextContent(element, "major"));
		deed.setPhoneNumber(getElementTextContent(element, "phone"));

		return deed;
	}

	private Address buildAddress(Element element) {
		Deed.Address address = new Deed().getAddress();

		String country = getElementTextContent(element, "country");
		address.setCountry(country);
		address.setCity(getElementTextContent(element, "city"));
		address.setStreet(getElementTextContent(element, "street"));

		return address;
	}

	private String getElementTextContent(Element element, String elementName) {
		NodeList list = element.getElementsByTagName(elementName);
		Node node = list.item(0);

		return node.getTextContent();
	}

	@Override
	public boolean change(Entity entity, String parameterName, String oldValue, String newValue) throws DaoException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		File file = FileMethods.openFile(fileName);

		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new DaoException(e);
		}

		Document document;
		try {
			document = builder.parse(file);

			Element rootElement = document.getDocumentElement();
			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xpath = xPathFactory.newXPath();
			String exp = "/deeds/deed["  + "name='" + ((Deed)entity).getName() + "']";
			XPathExpression expression = xpath.compile(exp);
			Element element = (Element) expression.evaluate(document, XPathConstants.NODE);
			if (element == null) {
				return false;
			}
			element.getElementsByTagName(parameterName).item(0).setTextContent(newValue);

			rootElement.appendChild(element);

			FileMethods.writeXml(document, file);
			return true;

		} catch (SAXException | IOException | XPathExpressionException e) {
			throw new DaoException(e);
		}
	}
	
	
}
