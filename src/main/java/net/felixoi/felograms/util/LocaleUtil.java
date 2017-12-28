package net.felixoi.felograms.util;

import java.util.Locale;
import java.util.ResourceBundle;

import static com.google.common.base.Preconditions.checkNotNull;

public final class LocaleUtil {

    private static ResourceBundle bundle;

    public static void initialize(String language) {
        checkNotNull(language, "The variable 'language' in LocaleUtil#initialize(language) cannot be null.");

        Locale.setDefault(new Locale(language));//todo
        bundle = ResourceBundle.getBundle("assets.felograms.messages");
    }

    public static String getMessage(String key) {
        checkNotNull(key, "The variable 'key' in LocaleUtil#getMessage(key) cannot be null.");

        return bundle.getString(key);
    }

    public static String getMessage(String key, Object... args) {
        checkNotNull(key, "The variable 'key' in LocaleUtil#getMessage(key) cannot be null.");

        return String.format(bundle.getString(key), args);
    }

}
