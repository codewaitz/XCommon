package android.lib.common.manager;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.lib.common.base.BaseApplication;
import android.lib.common.bean.Language;
import android.lib.common.utils.StringUtil;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * 多语言工具类
 */
public class LanguageManager {
    private static final String SPR_CONTEXT_LANGUAGE = "spr_context_language";
    private static final String SPR_SAVE_LANGUAGE = "spr_save_language";
    private static LinkedHashMap<String, Language> languages = new LinkedHashMap<>(); // 语言集
    public static final String SYSTEM_LANGUAGE_KEY = "system_language"; // 系统语言key,或不用

    public static void init(LinkedHashMap<String, Language> languagePool) {
        languages = languagePool;
    }

    /**
     * 更新该context的config语言配置，对于application进行反射更新
     *
     * @param context
     * @param locale
     */
    public static void updateLanguage(final Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        Locale contextLocale = config.locale;
        if (isSameLocale(contextLocale, locale)) {
            return;
        }
        DisplayMetrics dm = resources.getDisplayMetrics();
        config.setLocale(locale);
        if (context instanceof Application) {
            Context newContext = context.createConfigurationContext(config);
            try {
                Field mBaseField = ContextWrapper.class.getDeclaredField("mBase");
                mBaseField.setAccessible(true);
                mBaseField.set(context, newContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resources.updateConfiguration(config, dm);
    }

    /**
     * 对Application上下文进行替换
     *
     * @param activity activity
     */
    public static void applyAppLanguage(@NonNull Activity activity) {
        Locale appLocale = getCurrentAppLocale();
        updateLanguage(BaseApplication.instance, appLocale);
        updateLanguage(activity, appLocale);
    }

    /**
     * 获取系统Local
     *
     * @return
     */
    public static Locale getSystemLocale() {
        return Resources.getSystem().getConfiguration().locale;
    }

    /**
     * 获取app缓存语言
     *
     * @return
     */
    private static String getPrefAppLocaleLanguage() {
        SharedPreferences sp = BaseApplication.instance.getSharedPreferences(SPR_CONTEXT_LANGUAGE, Context.MODE_PRIVATE);
        return sp.getString(SPR_SAVE_LANGUAGE, "");
    }

    /**
     * 获取app缓存Locale
     *
     * @return null则无
     */
    public static Locale getPrefAppLocale() {
        String appLocaleLanguage = getPrefAppLocaleLanguage();
        if (!TextUtils.isEmpty(appLocaleLanguage)) {
            if (SYSTEM_LANGUAGE_KEY.equals(appLocaleLanguage)) { //系统语言则返回null
                return null;
            } else {
                return Locale.forLanguageTag(appLocaleLanguage);
            }
        }
        return Locale.forLanguageTag(getAppLanguage()); // 默认跟随系统
    }

    /**
     * 获取当前需要使用的locale，用于activity上下文的生成
     *
     * @return
     */
    public static Locale getCurrentAppLocale() {
        Locale prefAppLocale = getPrefAppLocale();
        return prefAppLocale == null ? getSystemLocale() : prefAppLocale;
    }


    /**
     * 缓存app当前语言
     *
     * @param language
     */
    public static void saveAppLocaleLanguage(String language) {
        SharedPreferences sp = BaseApplication.instance.getSharedPreferences(SPR_CONTEXT_LANGUAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(SPR_SAVE_LANGUAGE, language);
        edit.apply();
    }

    /**
     * 获取App当前语言
     *
     * @return
     */
    public static String getAppLanguage() {
        Locale locale = BaseApplication.instance.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        String country = locale.getCountry();
        StringBuilder stringBuilder = new StringBuilder();
        if (!TextUtils.isEmpty(language)) { //语言
            stringBuilder.append(language);
        }
        if (!TextUtils.isEmpty(country)) { //国家
            stringBuilder.append("-").append(country);
        }
        return stringBuilder.toString();
    }

    /**
     * 是否是相同的locale
     *
     * @param l0
     * @param l1
     * @return
     */
    private static boolean isSameLocale(Locale l0, Locale l1) {
        return equals(l1.getLanguage(), l0.getLanguage())
                && equals(l1.getCountry(), l0.getCountry());
    }

    /**
     * Return whether string1 is equals to string2.
     *
     * @param s1 The first string.
     * @param s2 The second string.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean equals(final CharSequence s1, final CharSequence s2) {
        if (s1 == s2) return true;
        int length;
        if (s1 != null && s2 != null && (length = s1.length()) == s2.length()) {
            if (s1 instanceof String && s2 instanceof String) {
                return s1.equals(s2);
            } else {
                for (int i = 0; i < length; i++) {
                    if (s1.charAt(i) != s2.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
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

    // 获取本地语言
    public static String getLanguageByPref() {
        String appLocaleLanguage = getPrefAppLocaleLanguage();
        if (StringUtil.isEmpty(appLocaleLanguage)) appLocaleLanguage = getAppLanguage();
        if (!StringUtil.isEmpty(appLocaleLanguage)) {
            for (Language language : languages.values()) {
                if (appLocaleLanguage.equals(language.getLocale().toLanguageTag())) {
                    return language.getKey();
                }
            }
        }
        return "";
    }
}
