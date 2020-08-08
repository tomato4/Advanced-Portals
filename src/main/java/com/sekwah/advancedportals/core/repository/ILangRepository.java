package com.sekwah.advancedportals.core.repository;

public interface ILangRepository {
     String  translate(String s);

    String translate(String key, Object... args);

    String translateInsertVariablesColor(String s, Object... args);

    String translateColor(String s);

    void loadTranslations(String fileName);

    String getLang();
}
