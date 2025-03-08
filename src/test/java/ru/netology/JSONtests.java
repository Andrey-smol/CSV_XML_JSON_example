package ru.netology;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class JSONtests<T> {

    JSON_<Employee> json_;

    private final static String rootDirectory = "src/test/java/ru/netology/resources/";
    private final static String pathFileToWrite = "testJsonFileForWrite.json";
    private final static String pathFileForRead = "testJsonFile.json";

    private final static String jsonListEmployee = "[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"firstName\": \"Koly\",\n" +
            "    \"lastName\": \"Petrov\",\n" +
            "    \"country\": \"RU\",\n" +
            "    \"age\": 25\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 2,\n" +
            "    \"firstName\": \"Wasy\",\n" +
            "    \"lastName\": \"Sidorov\",\n" +
            "    \"country\": \"RU\",\n" +
            "    \"age\": 20\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 3,\n" +
            "    \"firstName\": \"Misha\",\n" +
            "    \"lastName\": \"Mishin\",\n" +
            "    \"country\": \"RU\",\n" +
            "    \"age\": 35\n" +
            "  }\n" +
            "]";


    private Path workingDir;

    @BeforeAll
    public static void beforeAll() {
        File file = new File(rootDirectory + pathFileToWrite);
        if (file.exists()) {
            Assertions.assertTrue(file.delete());
        }
    }

    @BeforeEach
    public void init() {
        json_ = new JSON_<>(Employee.class);
        this.workingDir = Path.of("", rootDirectory);
    }

    @AfterEach
    public void cleanup() {
        json_ = null;
    }

//    @ParameterizedTest
//    @MethodSource("stringIntAndListProvider")
//    void testWithMultiArgMethodSource(String str, int num, List<String> list) {
//        assertEquals(5, str.length());
//        assertTrue(num >=1 && num <=2);
//        assertEquals(2, list.size());
//    }
//
//    static Stream<Arguments> stringIntAndListProvider() {
//        return Stream.of(
//                arguments("apple", 1, Arrays.asList("a", "b")),
//                arguments("lemon", 2, Arrays.asList("x", "y"))
//        );
//    }
//    @ParameterizedTest
//    @MethodSource
//    public void parametrizedTestListToJson(List<Employee> list) {
//
//    }


    @Test
    public void testListToJson() {
        // given:
        String expected = jsonListEmployee;

        List<Employee> list = Arrays.asList(new Employee(1, "Koly", "Petrov", "RU", 25),
                new Employee(2, "Wasy", "Sidorov", "RU", 20),
                new Employee(3, "Misha", "Mishin", "RU", 35));

        // when:
        String result = json_.listToJson(list);

        // then:
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testJsonToList() {
        // given:
        String str = jsonListEmployee;
        List<Employee> expected = Arrays.asList(new Employee(1, "Koly", "Petrov", "RU", 25),
                new Employee(2, "Wasy", "Sidorov", "RU", 20),
                new Employee(3, "Misha", "Mishin", "RU", 35));

        // when:
        List<Employee> result = json_.jsonToList(str);

        // then:
        Assertions.assertIterableEquals(expected, result);
    }

    @Test
    public void testReadStringFromFile() {
        // given:
        String expected = "[  {    \"id\": 1,    \"firstName\": \"Koly\",    \"lastName\": \"Petrov\",    \"country\": \"RU\",    \"age\": 25  },  " +
                "{    \"id\": 2,    \"firstName\": \"Wasy\",    \"lastName\": \"Sidorov\",    \"country\": \"RU\",    \"age\": 20  },  " +
                "{    \"id\": 3,    \"firstName\": \"Misha\",    \"lastName\": \"Mishin\",    \"country\": \"RU\",    \"age\": 35  }]";

        // when:
        String result = json_.readString(rootDirectory + pathFileForRead);

        // then:
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void testWriteJsonToFile() throws IOException {
        // given:
        String actual = jsonListEmployee;

        //String expected = jsonListEmployee.replace('1', '3');
        String expected = actual;

        Path file = this.workingDir.resolve(pathFileToWrite);
        File result = new File(rootDirectory + pathFileToWrite);

        // when:
        json_.writeJsonToFile(actual, rootDirectory + pathFileToWrite);

        // then:
        Assertions.assertNotNull(actual);
        Assertions.assertTrue(result.exists());
        Assertions.assertEquals(Files.readString(file), expected);
    }
}
