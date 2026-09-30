package com.buivanhuy.utils.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A utility class for formatting and printing tabular data to the console.
 * This class dynamically calculates the required width for each column based on
 * the headers and the provided row data to ensure proper alignment and a clean
 * visual representation with ASCII borders.
 */
public class TablePrinter {

    /**
     * The list of column headers.
     */
    private final List<String> headers;

    /**
     * The matrix of row data.
     */
    private final List<List<String>> rows = new ArrayList<>();

    /**
     * Constructs a new {@link TablePrinter} with the specified column headers.
     *
     * @param headers A variable number of strings representing the column titles.
     */
    public TablePrinter(String... headers) {
        this.headers = Arrays.asList(headers);
    }

    /**
     * Adds a new row of data to the table.
     * The number of elements should ideally match the number of headers.
     *
     * @param rowData A variable number of strings representing the data for each cell in the row.
     */
    public void addRow(String... rowData) {
        rows.add(Arrays.asList(rowData));
    }

    /**
     * Renders and prints the formatted table to the standard output (console).
     * <p>
     * The printing process includes:
     * 1. Calculating the maximum width for each column based on headers and cell content.
     * 2. Generating horizontal separator borders.
     * 3. Printing the headers.
     * 4. Printing the data rows, or a centered "Không có dữ liệu" (No data) message if the table is empty.
     * </p>
     */
    public void print() {
        int[] colWidths = new int[headers.size()];

        // 1. Tính độ rộng lớn nhất cho từng cột dựa vào header và dữ liệu
        for (int i = 0; i < headers.size(); i++) {
            colWidths[i] = headers.get(i).length();
        }
        for (List<String> row : rows) {
            for (int i = 0; i < row.size(); i++) {
                String cell = row.get(i) != null ? row.get(i) : "NULL";
                if (cell.length() > colWidths[i]) {
                    colWidths[i] = cell.length();
                }
            }
        }

        // 2. Tạo đường viền phân cách ngang
        StringBuilder border = new StringBuilder("+");
        for (int width : colWidths) {
            border.append("-".repeat(width + 2)).append("+");
        }

        // 3. In tiêu đề (Header)
        System.out.println(border);
        printRow(headers, colWidths);
        System.out.println(border);

        // 4. In dữ liệu
        if (rows.isEmpty()) {
            System.out.println("| " + centerString("Không có dữ liệu", border.length() - 4) + " |");
        } else {
            for (List<String> row : rows) {
                printRow(row, colWidths);
            }
        }
        System.out.println(border);
    }

    /**
     * Helper method to print a single row of data with the appropriate column widths.
     *
     * @param row       The list of strings representing the cell values for the row.
     * @param colWidths An array containing the calculated maximum width for each column.
     */
    private void printRow(List<String> row, int[] colWidths) {
        StringBuilder sb = new StringBuilder("|");
        for (int i = 0; i < colWidths.length; i++) {
            String val = (i < row.size() && row.get(i) != null) ? row.get(i) : "";
            sb.append(String.format(" %-" + colWidths[i] + "s |", val));
        }
        System.out.println(sb);
    }

    /**
     * Helper method to center a string within a specified total width space.
     * Used primarily for formatting the "No data" message when the table is empty.
     *
     * @param s    The string to be centered.
     * @param size The total width of the space in which the string should be centered.
     * @return A new string with the original string padded with spaces on both sides.
     */
    private String centerString(String s, int size) {
        if (s == null || size <= s.length()) return s;
        StringBuilder sb = new StringBuilder(size);
        int pad = (size - s.length()) / 2;
        sb.append(" ".repeat(pad));
        sb.append(s);
        while (sb.length() < size) sb.append(" ");
        return sb.toString();
    }
}