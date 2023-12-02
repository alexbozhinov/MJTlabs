package bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table;

import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.exceptions.CsvDataNotCorrectException;
import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table.column.BaseColumn;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaseTable implements Table {

    private final Map<String, BaseColumn> columns;
    public BaseTable() {
        this.columns = new LinkedHashMap<>();
    }

    @Override
    public void addData(String[] data) throws CsvDataNotCorrectException {
        if (null == data) {
            throw new IllegalArgumentException("Given argument data is null!");
        }

        // add header columns as keys
        if (columns.isEmpty()) {
            for (String columnName : data) {
                columns.put(columnName, new BaseColumn());
            }
        } else {
            if (data.length < columns.size()) {
                throw new CsvDataNotCorrectException("Data is in incorrect format - less parts than in the columns!",
                    new IOException());
            }
            if (data.length > columns.size()) {
                throw new CsvDataNotCorrectException("Data is in incorrect format - more parts than in the columns!");
            }

            // add columns data as values of the map, elements of BaseColumn
            int index = 0;
            for (String columnName : columns.keySet()) {
                columns.get(columnName).addData(data[index++]);
            }
        }
    }

    @Override
    public Collection<String> getColumnNames() {
        return Collections.unmodifiableSet(columns.keySet());
    }

    @Override
    public Collection<String> getColumnData(String columnName) throws IllegalArgumentException {
        if (null == columnName) {
            throw new IllegalArgumentException("Given argument column is null!");
        }
        if (columnName.isBlank()) {
            throw new IllegalArgumentException("Given argument column is blank!");
        }
        if (!columns.containsKey(columnName)) {
            throw new IllegalArgumentException("There is no corresponding column with that name in the table");
        }

        return columns.get(columnName).getData();
    }

    @Override
    public int getRowsCount() {
        // add 1 because of the columns headers which are keys in the Map
        return (columns.isEmpty()) ? 0 : columns.values().iterator().next().getData().size() + 1;
    }
}
