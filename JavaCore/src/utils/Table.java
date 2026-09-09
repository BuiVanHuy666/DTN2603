package utils;

import java.util.List;
import java.util.function.Function;

public class Table {
    public static <T> void printTable(List<T> items, String border, String format, Object[] headers, Function<T, Object[]> rowMapper) {
        if (items == null || items.isEmpty()) {
            System.out.println("Không có dữ liệu để hiển thị.");
            return;
        }

        System.out.println(border);
        System.out.printf(format, headers);
        System.out.println(border);

        for (T item : items) {
            System.out.printf(format, rowMapper.apply(item));
        }

        System.out.println(border);
    }
}