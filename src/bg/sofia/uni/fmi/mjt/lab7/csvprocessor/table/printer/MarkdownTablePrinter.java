package bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table.printer;

import bg.sofia.uni.fmi.mjt.lab7.csvprocessor.table.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MarkdownTablePrinter implements TablePrinter {

    public static final int MIN_COLUMN_LENGTH = 3;
    @Override
    public Collection<String> printTable(Table table, ColumnAlignment... alignments) {

        List<String> printableTable = new ArrayList<>();
        List<String> columnNames = new ArrayList<>(table.getColumnNames());
        List<List<String>> columnsData = new ArrayList<>();
        int numberOfColumns = columnNames.size();
        alignments = validateAlignments(alignments, numberOfColumns);

        // find the widths of the columns
        for (int i = 0; i < numberOfColumns; i++) {
            List<String> columnData = new ArrayList<>(table.getColumnData(columnNames.get(i)));
            columnsData.add(columnData);
        }
        int[] columnWidths = calculateMaxColumnWidths(columnNames, columnsData);

        // print header row and alignment row
        printRow(printableTable, columnNames, columnWidths);
        printAlignments(printableTable, columnWidths, alignments);

        // print the rest rows of the table
        for (int i = 0; i < table.getRowsCount() - 1; i++) {
            List<String> rowData = new ArrayList<>();
            for (String columnName : columnNames) {
                rowData.add(table.getColumnData(columnName).toArray()[i].toString());
            }
            printRow(printableTable, rowData, columnWidths);
        }

        return printableTable;
    }

    private int[] calculateMaxColumnWidths(List<String> columnNames, List<List<String>> columnsData) {
        int numberOfColumns = columnNames.size();
        int[] maxColumnWidths = new int[numberOfColumns];

        // Initialize maxColumnWidths with column names
        for (int i = 0; i < numberOfColumns; i++) {
            maxColumnWidths[i] = Math.max(columnNames.get(i).length(), MIN_COLUMN_LENGTH);
        }

        // Update maxColumnWidths with data values
        for (int i = 0; i < numberOfColumns; i++) {
            for (String data : columnsData.get(i)) {
                maxColumnWidths[i] = Math.max(maxColumnWidths[i], data.length());
            }
        }

        return maxColumnWidths;
    }

    private void printRow(List<String> printableTable, Collection<String> data, int[] columnWidths) {
        StringBuilder row = new StringBuilder("|");
        int columnIndex = 0;

        for (String datum : data) {
            row.append(" ").append(datum).append(" ".repeat(columnWidths[columnIndex] - datum.length())).append(" |");
            columnIndex++;
        }
        printableTable.add(row.toString());
    }

    private void printAlignments(List<String> printableTable, int[] columnWidths, ColumnAlignment ...alignments) {
        StringBuilder row = new StringBuilder("|");
        int columnIndex = 0;

        for (ColumnAlignment ca : alignments) {
            String alignment = createAlignmentString(ca, columnWidths[columnIndex]);
            row.append(" ").append(alignment).append(" |");
            columnIndex++;
        }

        printableTable.add(row.toString());
    }

    private String createAlignmentString(ColumnAlignment alignment, int width) {
        final int repetitions = width - alignment.getAlignmentCharactersCount();

        return switch (alignment) {
            case LEFT -> ":" + "-".repeat(repetitions);
            case CENTER -> ":" + "-".repeat(repetitions) + ":";
            case RIGHT -> "-".repeat(repetitions) + ":";
            case NOALIGNMENT -> "-".repeat(repetitions);
        };
    }

    private ColumnAlignment[] validateAlignments(ColumnAlignment[] alignments, int columnsCount) {
        ColumnAlignment[] validAlignments = new ColumnAlignment[columnsCount];
        Arrays.fill(validAlignments, ColumnAlignment.NOALIGNMENT);

        if (validAlignments.length == alignments.length) {
            System.arraycopy(alignments, 0, validAlignments, 0, alignments.length);
        } else {
            System.arraycopy(alignments, 0, validAlignments, 0, Math.min(validAlignments.length, alignments.length));
        }

        return validAlignments;
    }
}
