package com.buivanhuy.utils.helper;

import com.buivanhuy.app.repositories.ResourceRepository;
import com.buivanhuy.utils.database.BaseEntity;

import java.util.Date;
import java.util.regex.Pattern;

public class Validation {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private void ValidationHelper() {}

    public static void requireNotNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void validateStringLength(String value, int minExclusive, int maxExclusive, String fieldName) {
        if (value == null || value.trim().length() <= minExclusive || value.trim().length() >= maxExclusive) {
            throw new IllegalArgumentException(
                    String.format("%s phải có độ dài từ %d đến %d ký tự!", fieldName, minExclusive + 1, maxExclusive - 1)
            );
        }
    }

    public static void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Email không đúng định dạng chuẩn!");
        }
    }

    public static void validatePastOrPresent(Date date, String fieldName) {
        if (date == null || date.after(new Date())) {
            throw new IllegalArgumentException(fieldName + " không được để trống và không được vượt quá thời điểm hiện tại!");
        }
    }

    public static <T extends BaseEntity> void validateEntityExists(
            int id,
            ResourceRepository<T> repository,
            String entityName
    ) {
        if (id <= 0 || repository.findById(id) == null) {
            throw new IllegalArgumentException(entityName + " không tồn tại trong hệ thống!");
        }
    }
}
