package dev.frostguard.vision.ocr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import dev.frostguard.api.domain.OcrSettingsData;

class TesseractOcrProviderTest {

    @Test
    void selectsWindowsDebugSinkOnWindows() {
        assertEquals("quiet", TesseractOcrProvider.quietConfigName("Windows 11"));
    }

    @Test
    void selectsUnixDebugSinkOnLinuxAndMacOs() {
        assertEquals("quiet-unix", TesseractOcrProvider.quietConfigName("Linux"));
        assertEquals("quiet-unix", TesseractOcrProvider.quietConfigName("Mac OS X"));
    }

    @AfterEach
    void resetDefaultLanguage() {
        TesseractOcrProvider.setDefaultOcrLanguage("");
    }

    @Test
    void resolveLanguageFallsBackToEnglishWhenUnset() {
        TesseractOcrProvider.setDefaultOcrLanguage("");
        assertEquals("eng", TesseractOcrProvider.resolveLanguage(null));
    }

    @Test
    void resolveLanguageCombinesConfiguredLanguageWithEnglish() {
        TesseractOcrProvider.setDefaultOcrLanguage("rus");
        assertEquals("rus+eng", TesseractOcrProvider.resolveLanguage(null));
    }

    @Test
    void resolveLanguageHonoursPerPresetOverride() {
        TesseractOcrProvider.setDefaultOcrLanguage("rus");
        OcrSettingsData cfg = OcrSettingsData.builder()
                .language("eng+chi_sim").build();
        assertEquals("eng+chi_sim", TesseractOcrProvider.resolveLanguage(cfg));
    }
}
