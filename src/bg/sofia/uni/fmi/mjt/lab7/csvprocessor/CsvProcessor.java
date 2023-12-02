package bg.sofia.uni.fmi.mjt.lab7.csvprocessor;

import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.exceptions.CsvDataNotCorrectException;
import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table.printer.ColumnAlignment;
import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table.printer.MarkdownTablePrinter;
import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table.printer.TablePrinter;
import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table.BaseTable;
import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table.Table;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;

public class CsvProcessor implements CsvProcessorAPI {

    private Table table;
    public CsvProcessor() {
        this(new BaseTable());
    }

    public CsvProcessor(Table table) {
        setTable(table);
    }

    public void setTable(Table table) {
        this.table = (null == table) ? new BaseTable() : table;
    }

    public Table getTable() {
        return table;
    }

    @Override
    public void readCsv(Reader reader, String delimiter) throws CsvDataNotCorrectException {
        try (BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] rowData = line.split("\\Q" + delimiter + "\\E");

                table.addData(rowData);
            }
        } catch (IOException e) {
            throw new CsvDataNotCorrectException("Error reading CSV data", e);
        }
    }

    @Override
    public void writeTable(Writer writer, ColumnAlignment... alignments) {
        TablePrinter tablePrinter = new MarkdownTablePrinter();
        Collection<String> formattedTable = tablePrinter.printTable(table, alignments);

        try (BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            for (int i = 0; i < formattedTable.size() - 1; i++) {
                String line = formattedTable.toArray()[i].toString();
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            String line = formattedTable.toArray()[formattedTable.size() - 1].toString();
            bufferedWriter.write(line);
        } catch (IOException e) {
            throw new IllegalStateException("Error writing MD data", e);
        }
    }
}