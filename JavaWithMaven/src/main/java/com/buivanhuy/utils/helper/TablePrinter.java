package com.buivanhuy.utils.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TablePrinter {
    private final List<String> headers;
    private final List<List<String>> rows = new ArrayList<>();

    public TablePrinter(String... headers) {
        this.headers = Arrays.asList(headers);
    }

    public void addRow(String... rowData) {
        rows.add(Arrays.asList(rowData));
    }

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

    private void printRow(List<String> row, int[] colWidths) {
        StringBuilder sb = new StringBuilder("|");
        for (int i = 0; i < colWidths.length; i++) {
            String val = (i < row.size() && row.get(i) != null) ? row.get(i) : "";
            sb.append(String.format(" %-" + colWidths[i] + "s |", val));
        }
        System.out.println(sb);
    }

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