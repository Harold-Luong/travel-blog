package com.travel.blog.common;

import java.text.Normalizer;
import java.util.function.Predicate;

public class SlugUtil {

    /**
     * Chuyển text thành slug (không unique).
     */
    private static String toSlug(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Tag name không được để trống");
        }
        if (input.length() > 100) {
            throw new IllegalArgumentException("Tag name không được quá 100 ký tự");
        }

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String noAccent = normalized.replaceAll("đ", "d").replaceAll("Đ", "D");
        noAccent = noAccent.replaceAll("\\p{M}", "");
        noAccent = noAccent.toLowerCase();
        noAccent = noAccent.replaceAll("[^a-z0-9\\s-]", "");
        noAccent = noAccent.trim().replaceAll("\\s+", "-");
        return noAccent;
    }

    /**
     * Sinh slug unique bằng cách thêm hậu tố -1, -2... nếu cần.
     *
     * @param title         chuỗi gốc
     * @param existsChecker hàm kiểm tra slug đã tồn tại chưa (ví dụ gọi repository)
     * @return slug unique
     */
    public static String generateUniqueSlug(String title, Predicate<String> existsChecker) {
        String baseSlug = toSlug(title);
        String slug = baseSlug;
        int counter = 1;

        while (existsChecker.test(slug)) {
            slug = baseSlug + "-" + counter++;
        }

        return slug;
    }
}
