package ru.netology;

import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSV_<T> {
    private final Class<T> type;

    public CSV_(Class<T> type) {
        this.type = type;
    }

    public List<T> parseCSV(String[] columnMapping, String csvDataFile) {
        List<T> staff = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(csvDataFile))) {
            ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(type);
            strategy.setColumnMapping(columnMapping);
            CsvToBean<T> csv = new CsvToBeanBuilder<T>(reader).
                    withMappingStrategy(strategy).build();
            staff = csv.parse();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return staff;
    }
}
