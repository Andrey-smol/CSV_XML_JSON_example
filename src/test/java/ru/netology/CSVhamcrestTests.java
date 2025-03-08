package ru.netology;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class CSVhamcrestTests<T> {

    private final static String rootDirectory = "src/test/java/ru/netology/resources/";
    private final static String pathFileForRead = "testCSVFile.csv";

    CSV_<Employee> csv_;

    @BeforeEach
    public void init(){
        csv_ = new CSV_<>(Employee.class);
    }

    @Test
    public void testParseCSV(){
        // given:
        String[] actual = {"id", "firstName", "lastName", "country", "age"};
        String pathFile = rootDirectory + pathFileForRead;
        List<Employee> expected = List.of(new Employee(1, "John", "Smith", "USA", 25),
                                          new Employee(2, "Ivan", "Petrov", "RU", 23));
        String str = expected.get(0).toString();

        // when:
        List<Employee> result = csv_.parseCSV(actual, pathFile);

        // than:
        assertThat(result, Matchers.notNullValue());
        assertThat(str, Matchers.hasToString(result.get(0).toString()));
        assertThat(result, Matchers.hasSize(2));
        assertThat(result.get(0), Matchers.instanceOf(Employee.class));
        assertThat(expected, Matchers.is(result));
        assertThat(expected.get(0), Matchers.samePropertyValuesAs(result.get(0)));
        assertThat(expected.get(1), Matchers.samePropertyValuesAs(result.get(1)));
    }
}
