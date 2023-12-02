package bg.sofia.uni.fmi.mjt.lab7.csvprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.exceptions.CsvDataNotCorrectException;
import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table.Table;
import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table.printer.ColumnAlignment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;

public class CsvProcessorTest {

    private CsvProcessor csvProcessor;

    @BeforeEach
    public void setUp() {
        csvProcessor = new CsvProcessor();
    }

    @Test
    public void testReadCsvCorrectData() throws CsvDataNotCorrectException {
        String csvData = "Name,Age,Club" +
            System.lineSeparator() + "Marcus Rashford,26,Manchester United" +
            System.lineSeparator() + "Erling Haaland,23,Manchester City";
        csvProcessor.readCsv(new StringReader(csvData), ",");
        Table table = csvProcessor.getTable();

        assertEquals(Arrays.asList("Name", "Age", "Club"), new ArrayList<>(table.getColumnNames()));
        assertEquals(Arrays.asList("Marcus Rashford", "Erling Haaland"), new ArrayList<>(table.getColumnData("Name")));
        assertEquals(Arrays.asList("26", "23"), new ArrayList<>(table.getColumnData("Age")));
        assertEquals(Arrays.asList("Manchester United", "Manchester City"), new ArrayList<>(table.getColumnData("Club")));

    }

    @Test
    public void testWriteTableCorrectData() throws CsvDataNotCorrectException {
        String csvData = "Name,Age,Club" +
            System.lineSeparator() + "Marcus Rashford,26,Manchester United" +
            System.lineSeparator() + "Erling Haaland,23,Manchester City";
        csvProcessor.readCsv(new StringReader(csvData), ",");

        Writer writer = new StringWriter();
        csvProcessor.writeTable(writer, ColumnAlignment.LEFT, ColumnAlignment.RIGHT, ColumnAlignment.CENTER);

        String expectedMarkdown =
            "| Name            | Age | Club              |" + System.lineSeparator() +
                "| :-------------- | --: | :---------------: |" + System.lineSeparator() +
                "| Marcus Rashford | 26  | Manchester United |" + System.lineSeparator() +
                "| Erling Haaland  | 23  | Manchester City   |";

        assertEquals(expectedMarkdown, writer.toString().strip());
    }

    @Test
    public void testWriteTableLessAlignments() throws CsvDataNotCorrectException {
        String csvData = "Name,Age,Club" +
            System.lineSeparator() + "Marcus Rashford,26,Manchester United" +
            System.lineSeparator() + "Erling Haaland,23,Manchester City";
        csvProcessor.readCsv(new StringReader(csvData), ",");

        Writer writer = new StringWriter();
        csvProcessor.writeTable(writer, ColumnAlignment.LEFT);

        String expectedMarkdown =
            "| Name            | Age | Club              |" + System.lineSeparator() +
                "| :-------------- | --- | ----------------- |" + System.lineSeparator() +
                "| Marcus Rashford | 26  | Manchester United |" + System.lineSeparator() +
                "| Erling Haaland  | 23  | Manchester City   |";

        assertEquals(expectedMarkdown, writer.toString().strip());
    }

    @Test
    public void testWriteTableMoreAlignments() throws CsvDataNotCorrectException {
        String csvData = "Name,Age,Club" +
            System.lineSeparator() + "Marcus Rashford,26,Manchester United" +
            System.lineSeparator() + "Erling Haaland,23,Manchester City";
        csvProcessor.readCsv(new StringReader(csvData), ",");

        Writer writer = new StringWriter();
        csvProcessor.writeTable(writer,
            ColumnAlignment.LEFT, ColumnAlignment.CENTER, ColumnAlignment.RIGHT, ColumnAlignment.NOALIGNMENT);

        String expectedMarkdown =
            "| Name            | Age | Club              |" + System.lineSeparator() +
                "| :-------------- | :-: | ----------------: |" + System.lineSeparator() +
                "| Marcus Rashford | 26  | Manchester United |" + System.lineSeparator() +
                "| Erling Haaland  | 23  | Manchester City   |";

        assertEquals(expectedMarkdown, writer.toString().strip());
    }

    @Test
    public void testReadCsvWithInvalidData() {
        String invalidCsvData = "column1,column2,column3" +
            System.lineSeparator() + "value1,value2";

        Assertions.assertThrows(CsvDataNotCorrectException.class,
            () -> csvProcessor.readCsv(new StringReader(invalidCsvData), ","), "Error reading CSV data");
    }
}
