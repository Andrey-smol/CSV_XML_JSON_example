package ru.netology;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static javax.xml.transform.OutputKeys.INDENT;
import static javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION;

public class XML_ {
    public static List<Employee> parseXML(String path) throws ParserConfigurationException, IOException, SAXException {
        List<Employee> list = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(path));

        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType() && node.getNodeName().equals("employee")) {
                Element element = (Element) node;
                Employee employee = new Employee(Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent()),
                        element.getElementsByTagName("firstName").item(0).getTextContent(),
                        element.getElementsByTagName("lastName").item(0).getTextContent(),
                        element.getElementsByTagName("country").item(0).getTextContent(),
                        Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent()));
                list.add(employee);
            }
        }
        return list;
    }

    public static void createXMLDataFile(String path) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element root = document.createElement("staff");
        document.appendChild(root);

        Element employee = document.createElement("employee");
        root.appendChild(employee);

        Element id = document.createElement("id");
        id.setTextContent("1");
        employee.appendChild(id);

        Element firstName = document.createElement("firstName");
        firstName.setTextContent("John");
        employee.appendChild(firstName);

        Element lastName = document.createElement("lastName");
        lastName.setTextContent("Smith");
        employee.appendChild(lastName);

        Element country = document.createElement("country");
        country.setTextContent("USA");
        employee.appendChild(country);

        Element age = document.createElement("age");
        age.setTextContent("25");
        employee.appendChild(age);

        Element employee1 = document.createElement("employee");
        root.appendChild(employee1);

        Element id1 = document.createElement("id");
        id1.setTextContent("2");
        employee1.appendChild(id1);

        Element firstName1 = document.createElement("firstName");
        firstName1.setTextContent("Ivan");
        employee1.appendChild(firstName1);

        Element lastName1 = document.createElement("lastName");
        lastName1.setTextContent("Petrov");
        employee1.appendChild(lastName1);

        Element country1 = document.createElement("country");
        country1.setTextContent("RU");
        employee1.appendChild(country1);

        Element age1 = document.createElement("age");
        age1.setTextContent("23");
        employee1.appendChild(age1);

        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(path));
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        //включить отступы INDENT и настроить их величину
        transformer.setOutputProperty(INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        //исключить декларацию XML
        transformer.setOutputProperty(OMIT_XML_DECLARATION, "yes");
        transformer.transform(domSource, streamResult);
    }
}
