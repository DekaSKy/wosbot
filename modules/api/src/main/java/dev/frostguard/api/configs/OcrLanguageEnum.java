package dev.frostguard.api.configs;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Identifies the glyph languages available to the bundled Tesseract
 * models. Each constant maps to one {@code .traineddata} file shipped
 * under {@code tools/tesseract} (and staged into {@code lib/tesseract}
 * in packaged builds).
 */
public enum OcrLanguageEnum {

    ENGLISH("eng", "English"),
    RUSSIAN("rus", "Russian"),
    CHINESE_SIMPLIFIED("chi_sim", "Chinese (Simplified)");

    /* ---- reverse lookups ---- */

    private static final Map<String, OcrLanguageEnum> BY_CODE;
    private static final Map<String, OcrLanguageEnum> BY_NAME;

    static {
        Map<String, OcrLanguageEnum> codeMap = new HashMap<>();
        Map<String, OcrLanguageEnum> nameMap = new HashMap<>();
        for (OcrLanguageEnum language : values()) {
            codeMap.put(language.tessdataCode, language);
            nameMap.put(language.displayName.toUpperCase(), language);
        }
        BY_CODE = Collections.unmodifiableMap(codeMap);
        BY_NAME = Collections.unmodifiableMap(nameMap);
    }

    private final String tessdataCode;
    private final String displayName;

    OcrLanguageEnum(String tessdataCode, String displayName) {
        this.tessdataCode  = tessdataCode;
        this.displayName   = displayName;
    }

    /** Tesseract model identifier used in {@code setLanguage}. */
    public String tessdataCode()    { return tessdataCode; }

    /** Operator-facing name of the language. */
    public String displayName()     { return displayName; }

    /**
     * Case-insensitive lookup by the Tesseract model code
     * (e.g. {@code "rus"}, {@code "chi_sim"}).
     *
     * @param code the model code to resolve
     * @return the matching language, or {@code null} when unknown
     */
    public static OcrLanguageEnum fromCode(String code) {
        if (code == null) {
            return null;
        }
        return BY_CODE.get(code.trim());
    }

    /**
     * Case-insensitive lookup by the human-readable display name.
     *
     * @param name the display name to resolve (e.g. "Russian")
     * @return the matching language, or {@code null} when unknown
     */
    public static OcrLanguageEnum fromDisplayName(String name) {
        if (name == null) {
            return null;
        }
        return BY_NAME.get(name.trim().toUpperCase());
    }

    /**
     * Resolves an enum constant by its {@link #name()}, falling back to
     * a Tesseract code and then to a display name. Returns {@code null}
     * when nothing matches so callers can apply their own fallback.
     */
    public static OcrLanguageEnum parse(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String trimmed = value.trim();
        try {
            return valueOf(trimmed.toUpperCase());
        } catch (IllegalArgumentException ignored) {
            // fall through to code / display-name lookups
        }
        OcrLanguageEnum byCode = fromCode(trimmed);
        return byCode != null ? byCode : fromDisplayName(trimmed);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
