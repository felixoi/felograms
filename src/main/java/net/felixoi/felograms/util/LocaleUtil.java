package net.felixoi.felograms.util;

import net.felixoi.felograms.Felograms;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import static com.google.common.base.Preconditions.checkNotNull;

public final class LocaleUtil {

    private static ResourceBundle bundle;

    public static void initialize(String language) {
        checkNotNull(language, "The variable 'language' in LocaleUtil#initialize(language) cannot be null.");

        Locale locale;

        switch (language) {
            case "de":
                locale = Locale.GERMAN;
                break;
            case "en":
                locale = Locale.ENGLISH;
                break;
            default:
                locale = Locale.ENGLISH;
                Felograms.getInstance().getLogger().warn("Language '" + language + "' is unsupported! Falling back to english.");
        }

        Locale.setDefault(locale);
        
        bundle = PropertyResourceBundle.getBundle("assets.felograms.messages", new UTF8Control());
    }

    public static String getMessage(String key) {
        checkNotNull(key, "The variable 'key' in LocaleUtil#getMessage(key) cannot be null.");

        return bundle.getString(key);
    }

    public static String getMessage(String key, Object... args) {
        checkNotNull(key, "The variable 'key' in LocaleUtil#getMessage(key) cannot be null.");

        return String.format(getMessage(key), args);
    }

}
