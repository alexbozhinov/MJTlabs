package bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.exceptions.CsvDataNotCorrectException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class BaseTableTest {

    private BaseTable baseTable;

    @BeforeEach
    public void setUp() {
        baseTable = new BaseTable();
    }

    @Test
    public void testAddDataCorrectData() throws CsvDataNotCorrectException {
        baseTable.addData(new String[]{"Name", "Age", "Club"});
        baseTable.addData(new String[]{"Marcus Rashford", "26", "Manchester United"});
        baseTable.addData(new String[]{"Erling Haaland", "23", "Manchester City"});

        Collection<String> columnNames = new ArrayList<>(baseTable.getColumnNames());
        Collection<String> nameColumnData = new ArrayList<>(baseTable.getColumnData("Name"));
        Collection<String> ageColumnData = new ArrayList<>(baseTable.getColumnData("Age"));
        Collection<String> genderColumnData = new ArrayList<>(baseTable.getColumnData("Club"));

        assertEquals(Arrays.asList("Name", "Age", "Club"), columnNames);
        assertEquals(Arrays.asList("Marcus Rashford", "Erling Haaland"), nameColumnData);
        assertEquals(Arrays.asList("26", "23"), ageColumnData);
        assertEquals(Arrays.asList("Manchester United", "Manchester City"), genderColumnData);
    }

    @Test
    public void testAddDataNullData() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> baseTable.addData(null));
    }

    @Test
    public void testAddDataMoreThanColumnSize() throws CsvDataNotCorrectException {
        baseTable.addData(new String[]{"Name", "Age", "Club"});

        Assertions.assertThrows(CsvDataNotCorrectException.class, () ->
            baseTable.addData(new String[]{"Marcus Rashford", "26", "Manchester United", "England"}));
    }

    @Test
    public void testAddDataLessThanColumnSize() throws CsvDataNotCorrectException {
        baseTable.addData(new String[]{"Name", "Age", "Club"});

        Assertions.assertThrows(CsvDataNotCorrectException.class, () ->
            baseTable.addData(new String[]{"Marcus Rashford", "Manchester United"}));
    }

    @Test
    public void testGetColumnDataNullColumnName() throws CsvDataNotCorrectException {
        baseTable.addData(new String[]{"Name", "Age", "Club"});

        Assertions.assertThrows(IllegalArgumentException.class, () -> baseTable.getColumnData(null));
    }

    @Test
    public void testGetColumnDataBlankColumnName() throws CsvDataNotCorrectException {
        baseTable.addData(new String[]{"Name", "Age", "Club"});

        Assertions.assertThrows(IllegalArgumentException.class, () -> baseTable.getColumnData(""));
    }

    @Test
    public void testGetColumnDataNonexistentColumnName() throws CsvDataNotCorrectException {
        baseTable.addData(new String[]{"Name", "Age", "Club"});

        Assertions.assertThrows(IllegalArgumentException.class, () -> baseTable.getColumnData("Gender"));
    }
}
