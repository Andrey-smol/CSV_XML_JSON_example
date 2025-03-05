## Задача 1: CSV - JSON парсер
# Описание
В данном домашнем задании написано два конвертора: из формата CSV и XML в формат JSON, а так же парсер JSON файлов в Java классы.

В первой задаче запись в файл JSON объекта, полученного из CSV файла.

Для работы с проектом потребуются вспомогательные библиотеки, поэтому необходимо создать новый проект с использованием сборщика проекта Gradle или Maven. Далее пропишите зависимости для следующих библиотек: opencsv, json-simple и gson. Ниже приведен пример для сборщика Gradle:
```

    implementation group: 'com.opencsv', name: 'opencsv', version: '5.1'
    implementation group: 'com.googlecode.json-simple', name: 'json-simple', version: '1.1.1'
    implementation group: 'com.google.code.gson', name: 'gson', version: '2.8.2'

```
В качестве исходной информации создан файл data.csv со следующим содержимым:
```
1,John,Smith,USA,25
2,Inav,Petrov,RU,23
```
Помимо этого потребуется класс Employee, который будет содержать информацию о сотрудниках. Обратите внимание, что для парсинга Java классов из CSV потребуется пустой конструктор класса.

```java
public class Employee {
    public long id;
    public String firstName;
    public String lastName;
    public String country;
    public int age;

    public Employee() {
        // Пустой конструктор
    }

    public Employee(long id, String firstName, String lastName, String country, int age) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.age = age;
    }   
}
```
В резльтате работы программы в корне проекта должен появиться файл data.json со следующим содержимым:
```json
[
  {
    "id": 1,
    "firstName": "John",
    "lastName": "Smith",
    "country": "USA",
    "age": 25
  },
  {
    "id": 2,
    "firstName": "Inav",
    "lastName": "Petrov",
    "country": "RU",
    "age": 23
  }
]
```
# Реализация
Первым делом в классе Main в методе main() создаётся массив строчек columnMapping, содержащий информацию о предназначении колонок в CVS файле:
```
String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
```

Далее получаем список сотрудников, вызвав метод parseCSV():

```
List<Employee> list = parseCSV(columnMapping, fileName);
```

Полученный список преобразуется в строчку в формате JSON. Сделает это  метод listToJson().
```
String json = listToJson(list);
```
При написании метода listToJson() вам понадобятся объекты типа GsonBuilder и Gson. Для преобразования списка объектов в JSON, требуется определить тип этого спика:
```
Type listType = new TypeToken<List<T>>() {}.getType();
```
Получить JSON из экземпляра класса Gson можно с помощтю метода toJson(), передав в качестве аргументов список сотрудников и тип списка:
```
String json = gson.toJson(list, listType);
```
Далее запишим полученный JSON в файл с помощью метода writeString().


## Задача 2: XML - JSON парсер
# Описание
В данной задаче производим запись в файл JSON объекта, полученного из XML файла.

В качестве исходной информации создаётся файл data.xml со следующим содержимым:
```
<staff>
    <employee>
        <id>1</id>
        <firstName>John</firstName>
        <lastName>Smith</lastName>
        <country>USA</country>
        <age>25</age>
    </employee>
    <employee>
        <id>2</id>
        <firstName>Inav</firstName>
        <lastName>Petrov</lastName>
        <country>RU</country>
        <age>23</age>
    </employee>
</staff>
```
В резyльтате работы программы в корне проекта должен появиться файл data2.json с содержимым, аналогичным json-файлу из предыдущей задачи.

# Реализация
Для получения списка сотрудников из XML документа используется метод parseXML():
```
List<Employee> list = parseXML("data.xml");
```
При реализации метода parseXML() вам необходимо получить экземпляр класса Document с использованием DocumentBuilderFactory и DocumentBuilder через метод parse(). Далее получите из объекта Document корневой узел Node с помощью метода getDocumentElement(). Из корневого узла извлеките список узлов NodeList с помощью метода getChildNodes(). Пройдитесь по списку узлов и получите из каждого из них Element. У элементов получите значения, с помощью которых создайте экземпляр класса Employee. Так как элементов может быть несколько, организуйте всю работу в цикле. Метод parseXML() должен возвращать список сотрудников.

С помощью ранее написанного метода listToJson() преобразуйте список в JSON и запишите его в файл c помощью метода writeString().


## Задача 3: JSON парсер (со звездочкой *)
# Описание
В данной задаче производится чтение файла JSON, его парсинг и преобразование объектов JSON в классы Java.

В ходе выполнения программы в консоле вы должны увидеть следующие строки

```
Employee{id=1, firstName='John', lastName='Smith', country='USA', age=25}
Employee{id=2, firstName='Inav', lastName='Petrov', country='RU', age=23}
```
# Реализация
Выполнение задачи следует начать с получения JSON из файла. Сделайте это с помощью метода readString():
```java
String json = readString("new_data.json");
```
Метод readString() реализован с использованием BufferedReader и FileReader. Метод должен возвращать прочитанный из файла JSON типа String.

Прочитанный JSON необходимо преобразовать в список сотрудников.
```java
List<Employee> list = jsonToList(json);
```

Далее, содержимое полученного списка выводится в консоль.