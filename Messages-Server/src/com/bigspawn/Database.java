package com.bigspawn;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * Created by bigsp on 24.07.2016.
 */
public class Database {
    private String filePath;
    private Document document;

    public Database(String filePath) {
        this.filePath = filePath;
    }

    private boolean prepareFile() {
        try {
            File dataFile = new File(filePath);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(dataFile);
            document.getDocumentElement().normalize();
            return true;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        } catch (SAXException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addUserToXML () {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        if (prepareFile()) {
            Node node = document.getFirstChild();
            Element newUserElement = document.createElement(Tags.USER_TAG);
            Element usernameElement = document.createElement(Tags.USERNAME_TAG);
            Element passwordElement = document.createElement(Tags.PASSWORD_TAG);
            Element infoElement = document.createElement(Tags.INFO_TAG);

            usernameElement.setTextContent(user.getName());
            passwordElement.setTextContent(user.getPassword());
            infoElement.setTextContent(user.getInfo());

            newUserElement.appendChild(usernameElement);
            newUserElement.appendChild(passwordElement);
            newUserElement.appendChild(infoElement);
            node.appendChild(newUserElement);

            addUserToXML();
        }
    }

    public boolean isUserExist(User user) {
        if (prepareFile()) {
            NodeList nodeList = document.getElementsByTagName(Tags.USER_TAG);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (getTagValue(Tags.USER_TAG, element).equals(user.getName())){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public boolean authentication(User user){
        if (isUserExist(user)) {
            NodeList nodeList = document.getElementsByTagName(Tags.USER_TAG);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (getTagValue(Tags.USERNAME_TAG, element).equals(user.getName())
                            && getTagValue(Tags.PASSWORD_TAG, element).equals(user.getPassword())) {
                        return true;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public String getInfo(User user) {
        NodeList nodeList = document.getElementsByTagName(Tags.USER_TAG);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                if (getTagValue(Tags.USERNAME_TAG, element).equals(user.getName())) {
                    return getTagValue(Tags.INFO_TAG, element);
                }
            }
        }
        return "";
    }

    public static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        return nodeList.item(0).getNodeValue();
    }
}
