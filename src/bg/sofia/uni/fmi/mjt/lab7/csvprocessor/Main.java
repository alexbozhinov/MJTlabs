package bg.sofia.uni.fmi.mjt.lab7.csvprocessor;

import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.exceptions.CsvDataNotCorrectException;
import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table.printer.ColumnAlignment;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

public class Main {

    public static void main(String[] args) {
        CsvProcessor csvProcessor = new CsvProcessor();

        try (Reader fileReader = new FileReader(
            "C:\\Users\\abozhinov\\IdeaProjects\\MJTLabs\\src\\bg\\sofia\\uni\\fmi\\mjt\\lab7\\csvprocessor\\CSVDemo.csv");
             PrintWriter printWriter = new PrintWriter(System.out)) {

            csvProcessor.readCsv(fileReader, ".");
            csvProcessor.writeTable(printWriter,
                ColumnAlignment.CENTER, ColumnAlignment.LEFT, ColumnAlignment.NOALIGNMENT);
        } catch (IOException | CsvDataNotCorrectException e) {
            throw new RuntimeException(e);
        }
    }
}