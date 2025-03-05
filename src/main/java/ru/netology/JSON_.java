package ru.netology;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSON_<T> {
    private final Class<T> type;

    public JSON_(Class<T> type) {
        this.type = type;
    }

    public void writeJsonToFile(String json, String jsonDataFile) {
        try (FileWriter writer = new FileWriter(jsonDataFile)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String listToJson(List<T> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        Type listType = new TypeToken<List<T>>() {
        }.getType();
        return gson.toJson(list, listType);
    }

    public List<T> jsonToList(String json) {
        List<T> list = new ArrayList<>();
        JsonParser parser = new JsonParser();
        try {
            JsonElement obj = parser.parse(json);
            JsonArray arr = obj.getAsJsonArray();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.setPrettyPrinting().create();
            for (int i = 0; i < arr.size(); i++) {
                list.add(gson.fromJson(arr.get(i), type));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String readString(String path) {
        StringBuilder str = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String s;
            while ((s = br.readLine()) != null) {
                str.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    public void createJsonFile(List<T> list, String path) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        Type listType = new TypeToken<List<T>>() {
        }.getType();
        String json = gson.toJson(list, listType);

        try (FileWriter writer = new FileWriter(path)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
