package ru.netology;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    static final String CSV_DATA_FILE = "data.csv";
    static final String JSON_DATA_FILE = "data.json";
    static final String XML_DATA_FILE = "data.xml";

    public static void main(String[] args) {

        CSV_<Employee> csv_ = new CSV_<>(Employee.class);
        JSON_<Employee> json_ = new JSON_<>(Employee.class);

        //task 1
        createFileData(CSV_DATA_FILE);

        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        List<Employee> list = csv_.parseCSV(columnMapping, CSV_DATA_FILE);
        if (list != null && !list.isEmpty()) {
            String json = json_.listToJson(list);
            if (json != null && !json.isEmpty()) {
                json_.writeJsonToFile(json, JSON_DATA_FILE);
            }
        }

        //task 2
        try {
            XML_.createXMLDataFile(XML_DATA_FILE);
            list = XML_.parseXML(XML_DATA_FILE);

            if (!list.isEmpty()) {
                String json = json_.listToJson(list);
                if (json != null && !json.isEmpty()) {
                    json_.writeJsonToFile(json, "data2.json");
                }
            }
        } catch (IOException | ParserConfigurationException | TransformerException | SAXException e) {
            e.printStackTrace();
        }

        //task 3
        List<Employee> employees = Arrays.asList(new Employee(1, "John", "Smith", "USA", 25),
                new Employee(2, "Ivan", "Petrov", "RU", 23));

        json_.createJsonFile(employees, "new_data.json");
        String json = json_.readString("new_data.json");
        list = json_.jsonToList(json);
        list.stream().forEach(System.out::println);
    }

    private static void createFileData(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("1,John,Smith,USA,25");
                writer.append('\n');
                writer.write("2,Ivan,Petrov,RU,23");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}