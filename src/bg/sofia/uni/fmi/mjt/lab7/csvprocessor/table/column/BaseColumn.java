package bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table.column;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class BaseColumn implements Column {

    private Set<String> columnData;

    public BaseColumn() {
        this(new LinkedHashSet<>());
    }

    public BaseColumn(Set<String> columnData) {
        setColumnData(columnData);
    }

    public void setColumnData(Set<String> columnData) {
        this.columnData = (null == columnData) ? new LinkedHashSet<>() : columnData;
    }

    @Override
    public void addData(String data) {
        if (null == data) {
            throw new IllegalArgumentException("Given argument data is null!");
        }
        if (data.isBlank()) {
            throw new IllegalArgumentException("Given string argument data is blank!");
        }

        columnData.add(data);
    }

    @Override
    public Collection<String> getData() {
        return Collections.unmodifiableSet(columnData);
    }
}