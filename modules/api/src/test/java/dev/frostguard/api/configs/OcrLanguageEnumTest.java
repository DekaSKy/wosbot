package dev.frostguard.api.configs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class OcrLanguageEnumTest {

    @Test
    void parseAcceptsCodesNamesAndDisplayNames() {
        assertEquals(OcrLanguageEnum.RUSSIAN, OcrLanguageEnum.parse("rus"));
        assertEquals(OcrLanguageEnum.RUSSIAN, OcrLanguageEnum.parse("RUSSIAN"));
        assertEquals(OcrLanguageEnum.RUSSIAN, OcrLanguageEnum.parse("Russian"));
        assertNull(OcrLanguageEnum.parse("klingon"));
    }

    @Test
    void resolveOrDefaultFallsBackToEnglish() {
        assertEquals("rus", OcrLanguageEnum.resolveOrDefault("rus"));
        assertEquals("eng", OcrLanguageEnum.resolveOrDefault(null));
        assertEquals("eng", OcrLanguageEnum.resolveOrDefault("  "));
        assertEquals("eng", OcrLanguageEnum.resolveOrDefault("klingon"));
    }

    @Test
    void combineCodesDropsBlanksAndDuplicates() {
        assertEquals("rus+eng", OcrLanguageEnum.combineCodes("rus", "eng"));
        assertEquals("rus", OcrLanguageEnum.combineCodes("rus", null, " ", "rus"));
        assertEquals("eng", OcrLanguageEnum.combineCodes());
        assertEquals("eng", OcrLanguageEnum.combineCodes("", null));
    }
}
