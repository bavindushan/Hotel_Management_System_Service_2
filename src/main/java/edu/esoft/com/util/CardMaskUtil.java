package edu.esoft.com.util;

public class CardMaskUtil {
    public static String mask(String raw) {
        if (raw == null || raw.length() < 4) return "****";
        return "**** **** **** " + raw.substring(raw.length() - 4);
    }
}
