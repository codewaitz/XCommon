package android.lib.common.language;

import android.content.Context;
import android.lib.common.bean.Language;

import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * 多语言工具类
 */
public class LanguageManager {
    private static LinkedHashMap<String, Language> languages = new LinkedHashMap<>(); // 语言集
    private static String curLanguageKey = ""; // 当前语言

    // 初始化
    public static void init(LinkedHashMap<String, Language> languagePool) {
        languages = languagePool;
        curLanguageKey = getLanguageKeyByLocale(MultiLanguages.getAppLanguage());
    }

    // 获取当前语言key
    public static String getCurLanguageKey() {
        return curLanguageKey;
    }

    // 当前是否是中文语言
    public static Boolean isCurChineseLanguage() {
        return "zh".equals(LanguageManager.getCurLanguageKey());
    }

    // 通过Locale获取语言key
    public static String getLanguageKeyByLocale(Locale locale) {
        if (locale != null) {
            for (String key : languages.keySet()) {
                Language language = languages.get(key);
                if (MultiLanguages.equalsLanguage(locale, language.getLocale())) {
                    return key;
                }
            }
        }
        return "";
    }

    // 通过语言装载Locale
    public static Locale getLocaleByLanguageKey(String languageKey) {
        Language language = getLanguageByLanguageKey(languageKey);
        if (language != null) {
            return language.getLocale();
        }
        return Locale.US;
    }

    // 通过语言key获取Language
    public static Language getLanguageByLanguageKey(String languageKey) {
        if (languages.containsKey(languageKey)) {
            return languages.get(languageKey);
        }
        return null;
    }

    // 改变语言
    public static void changeLanguage(Context context, String languageKey) {
        curLanguageKey = languageKey;
        Locale locale = LanguageManager.getLocaleByLanguageKey(languageKey);
        if (locale != null) MultiLanguages.setAppLanguage(context, locale);
    }
}
