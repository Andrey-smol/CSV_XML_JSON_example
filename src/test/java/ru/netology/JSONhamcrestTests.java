package ru.netology;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class JSONhamcrestTests<T> {

    JSON_<Employee> json_;
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

    List<Employee> list = Arrays.asList(new Employee(1, "Koly", "Petrov", "RU", 25),
            new Employee(2, "Wasy", "Sidorov", "RU", 20),
            new Employee(3, "Misha", "Mishin", "RU", 35));

    private Path workingDir;

    @BeforeAll
    public static void beforeAll() {
        File file = new File(CommonResources.rootDirectory + pathFileToWrite);
        if (file.exists()) {
            Assertions.assertTrue(file.delete());
        }
    }

    @BeforeEach
    public void init() {
        json_ = new JSON_<>(Employee.class);
        this.workingDir = Path.of("", CommonResources.rootDirectory);
    }

    @AfterEach
    public void cleanup() {
        json_ = null;
    }

    @Test
    public void testListToJson() {
        // given:
        String expected = jsonListEmployee;
        // when:
        String result = json_.listToJson(list);
        // then:
        assertThat(result, Matchers.notNullValue());
        assertThat(result, Matchers.not(result.isEmpty()));
        assertThat(expected, Matchers.equalTo(result));
    }

    @Test
    public void testJsonToList() {
        // given:
        String str = jsonListEmployee;
        List<Employee> expected = list;
        // when:
        List<Employee> result = json_.jsonToList(str);
        // then:
        assertThat(result, Matchers.notNullValue());
        assertThat(result, Matchers.not(result.isEmpty()));
        assertThat(expected, Matchers.equalTo(result));
    }

    @Test
    public void testReadStringFromFile() {
        // given:
        String expected = "[  {    \"id\": 1,    \"firstName\": \"Koly\",    \"lastName\": \"Petrov\",    \"country\": \"RU\",    \"age\": 25  },  " +
                "{    \"id\": 2,    \"firstName\": \"Wasy\",    \"lastName\": \"Sidorov\",    \"country\": \"RU\",    \"age\": 20  },  " +
                "{    \"id\": 3,    \"firstName\": \"Misha\",    \"lastName\": \"Mishin\",    \"country\": \"RU\",    \"age\": 35  }]";
        // when:
        String result = json_.readString(CommonResources.rootDirectory + pathFileForRead);
        // then:
        assertThat(result, Matchers.notNullValue());
        assertThat(expected, Matchers.equalTo(result));
    }

    @Test
    public void testWriteJsonToFile() throws IOException {
        // given:
        String actual = jsonListEmployee;

        String expected = actual;

        Path file = this.workingDir.resolve(pathFileToWrite);
        File result = new File(CommonResources.rootDirectory + pathFileToWrite);
        // when:
        json_.writeJsonToFile(actual, CommonResources.rootDirectory + pathFileToWrite);
        // then:
        assertThat(result, Matchers.notNullValue());
        assertThat("", result.exists());
        assertThat(expected, Matchers.equalTo(Files.readString(file)));
    }
}
