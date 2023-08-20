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

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import my.home.programming6.archive.bean.Entity;
import my.home.programming6.archive.bean.User;

import my.home.programming6.archive.exception.DaoException;

public class UserAccess implements StorageAccess

{

	private String fileName = "archiveData/users.xml";

	@Override
	public boolean add(Entity t) throws DaoException {

		File file = FileMethods.openFile(fileName);

		User user = (User) t;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document;

		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new DaoException(e);
		}

		if (file.length() == 0) {
			String root = "users";
			document = builder.newDocument();
			document.setXmlStandalone(true);
			Element rootElement = document.createElement(root);
			document.appendChild(rootElement);
			rootElement.appendChild(getClient(document, user));

		} else {
			try {

				document = builder.parse(file);
				if (hasElement(document, user)) {
					return false;
				}

				Element rootElement = document.getDocumentElement();
				rootElement.appendChild(getClient(document, user));

			} catch (SAXException | IOException e) {
				throw new DaoException(e);
			}

		}
		return FileMethods.writeXml(document, file);

	}

	private boolean hasElement(Document document, User user) {

		NodeList list = (NodeList) document.getElementsByTagName("user");
		for (int i = 0; i < list.getLength(); i++) {
			Element element = (Element) list.item(i);
			String login = element.getElementsByTagName("login").item(0).getTextContent();
			if (login.equals(user.getLogin())) {
				User tempUser = buildUser(element);
				if (user.equals(tempUser)) {
					return true;
				}
			}
		}

		return false;

	}

	private static Node getClient(Document document, User client) throws DOMException, DaoException {
		Element userElement = document.createElement("user");

		userElement.appendChild(getXmlElement(document, userElement, "login", client.getLogin()));
		userElement.appendChild(getXmlElement(document, userElement, "password", client.getPassword()));
		userElement.appendChild(getXmlElement(document, userElement, "role", client.getRole().getUserRole()));

		return userElement;
	}

	private static Node getXmlElement(Document document, Element element, String name, String value) {
		Element nodElement = document.createElement(name);
		nodElement.appendChild(document.createTextNode(value));
		return nodElement;
	}

	@Override
	public boolean delete(Entity entity) throws DaoException {
		File file = FileMethods.openFile(fileName);
		boolean isDelete = false;

		User user = (User) entity;

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
		NodeList nodes = document.getElementsByTagName("user");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element element = (Element) nodes.item(i);

			String login = element.getElementsByTagName("login").item(0).getTextContent();

			if (login.equals(user.getLogin())) {
				root.removeChild(element);
				isDelete = true;
			}

		}

		FileMethods.writeXml(document, file);

		return isDelete;
	}

	@Override
	public Set<? extends Entity> get(String parameter) throws DaoException {
		Set<User> users = new HashSet<>();

		File file;
		try {
			file = FileMethods.openFile(fileName);
		} catch (DaoException e) {
			throw new DaoException(e);
		}

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document document;
		try {

			builder = documentBuilderFactory.newDocumentBuilder();
			document = builder.parse(file);

			XPathFactory xPathFactory = XPathFactory.newInstance();
			XPath xPath = xPathFactory.newXPath();

			XPathExpression expression = xPath.compile(parameter);

			NodeList list = (NodeList) expression.evaluate(document, XPathConstants.NODESET);

			for (int i = 0; i < list.getLength(); i++) {
				Element element = (Element) list.item(i);

				User user = buildUser(element);
				users.add(user);
			}

			return users;

		} catch (ParserConfigurationException | SAXException | IOException | XPathExpressionException e) {
			throw new DaoException(e);
		}

	}

	@Override
	public Set<User> getAll() throws DaoException {

		File file = new File(fileName);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document;

		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new DaoException(e);
		}

		try {
			document = builder.parse(file);
		} catch (SAXException e) {
			throw new DaoException(e);
		} catch (IOException e) {
			throw new DaoException("file " + fileName + "can't be opened", e);
		}

		return domUserParser(document);
	}

	private Set<User> domUserParser(Document document) {
		Set<User> users = new HashSet<>();
		Element rootElement = document.getDocumentElement();

		NodeList userNodeList = rootElement.getElementsByTagName("user");
		for (int i = 0; i < userNodeList.getLength(); i++) {
			Element userElement = (Element) userNodeList.item(i);
			User user = buildUser(userElement);
			users.add(user);
		}

		return users;
	}

	private User buildUser(Element userElement) {
		User user = new User();
		user.setLogin(getElementTextContent(userElement, "login"));
		user.setPassword(getElementTextContent(userElement, "password"));
		user.setRole(getElementTextContent(userElement, "role"));
		return user;
	}

	private String getElementTextContent(Element element, String elenemtName) {
		NodeList list = element.getElementsByTagName(elenemtName);
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
			String exp = "/users/user[" + "login='" + ((User) entity).getLogin() + "']";
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
