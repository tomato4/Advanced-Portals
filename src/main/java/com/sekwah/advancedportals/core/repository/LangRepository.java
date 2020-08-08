package com.sekwah.advancedportals.core.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sekwah.advancedportals.core.AdvancedPortalsCore;
import com.sekwah.advancedportals.core.util.DataHandler;
import com.sekwah.advancedportals.core.util.InfoLogger;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author sekwah41
 *         <p>
 *         The language translation file for the game. Will always load english first
 *         so that if the translations are missing any then they are still readable and can then be translated.
 *         (Its better than a raw translate string)
 *         <p>
 *         TODO add a loaddefault where it only loads from the plugins version of the data rather than paying attention to any
 *         possible changed versions in the lang folder.
 */
@Singleton
public class LangRepository implements ILangRepository {

    private final HashMap<String, String> languageMap = new HashMap<>();
    private String selectedLang = "en_GB";

    public static final String version = "1.0.0";
    public static final String lastTranslationUpdate = "1.0.0";

    @Inject
    private DataHandler dataHandler;
    @Inject
    private InfoLogger infoLogger;

    @Override
    public  String translate(String s) {
        return languageMap.getOrDefault(s, s);
    }

    @Override
    public String translate(String key, Object... args) {
        String translation = translate(key);
        for (int i = 1; i <= args.length; i++) {
            translation = translation.replaceAll("%" + i + "\\$s", args[i-1].toString());
        }
        return translation;
    }

    public final String getVersion() {
        return version;
    }
    public final String getLastTranslationUpdate() {
        return lastTranslationUpdate;
    }


    @Override
    public  String translateInsertVariablesColor(String s, Object... args) {
        String translation = translateColor(s);
        for (int i = 1; i <= args.length; i++) {
            translation = translation.replaceAll("%" + i + "\\$s", args[i-1].toString());
        }
        return translation;
    }

    @Override
    public  String translateColor(String s) {
        String translation = translate(s);
        translation = translation.replaceAll("\\\\u00A7", "\u00A7");
        return translation;
    }

    @Override
    public void loadTranslations(String fileName) {
        try {
            //URL url = lang.getClass().getClassLoader().getResource("lang/" + fileName + ".lang");
            //System.out.println(url);
            //Map<String, String> newLangMap = lang.parseLang(url.openStream());
            InputStream stream = dataHandler.loadResource("lang/" + fileName + ".lang");
            if (stream != null) {
                Map<String, String> newLangMap = parseLang(stream);
                if (newLangMap != null) {
                    languageMap.putAll(newLangMap);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            infoLogger.logWarning("Could not load " + fileName + ".lang The file does" +
                    "not exist or there has been an error reading the file. Canceled loading language file.");
        }
    }

    @Override
    public String getLang() {
        return selectedLang;
    }

    private Map<String, String> parseLang(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream, "UTF-8");
        String line = getNextLine(scanner);
        HashMap<String, String> newMap = new HashMap<>();
        while (scanner != null && line != null) {
            //System.out.println(line);
            if (!line.startsWith("#") && line.indexOf('=') > -1) {
                int split = line.indexOf('=');
                String key = line.substring(0, split);
                String value = line.substring(split + 1, line.length());
                newMap.put(key, value);
            }
            line = getNextLine(scanner);
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newMap;
    }

    private String getNextLine(Scanner scanner) {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;
    }
}
