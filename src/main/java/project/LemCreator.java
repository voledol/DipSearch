package project;



import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Класс создания лемм со стараницы саййта
 *
 * @author VG
 * @version 0.1
 **/
public class LemCreator {
    /**
     * Поле морфологических частей речи подлежащих удалению из списка лемм
     */
    private final  String[] PARTS_OF_SPEECH = {"СОЮЗ", "МЕЖД", "ПРЕДЛ", "ЧАСТ"};
    private final String DELETE_ENGLISH_REG = "\\s+";
    private final String DELETE_DOUBLE_SPACES =  "";
    public LuceneMorphology luceneMorphology = getRussianMorhology();

    /**
     * Функция получения лемма из екста страницы сайта
     *
     * @param text -  текстовая часть страницы сайта
     * @return возвращает список лемм полученны из текстовой части сайта
     */
    public HashMap<String, Integer> getLem (String text) {
        String[] words;
        words = text.toLowerCase().replaceAll(DELETE_ENGLISH_REG, "").trim()
                .replaceAll(DELETE_DOUBLE_SPACES, " ")
                .split("\\s");
        List<List<String>> lemWorkArray = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {

            if (words[i].isEmpty()) {
                continue;
            } else {
                lemWorkArray.add(luceneMorphology.getNormalForms(words[i]));

            }

        }
        return setLemmasCount(lemWorkArray);
    }

    /**
     * функция определения части речи
     *
     * @param word - проверемое слово
     * @return Возваращет false если переданная часть речи содержится в массиве partsOfSpeech
     * возвращает true если переданная чась речи является словом
     */
    public boolean isWord (String word) {
        boolean isWord = true;
        for (int i = 0; i < PARTS_OF_SPEECH.length; i++) {
            if (word.contains(PARTS_OF_SPEECH[i])) {
                isWord = false;
            }
        }
        return isWord;
    }


    public HashMap<String, Integer> setLemmasCount (List<List<String>> lemWorkArray) {
        HashMap<String, Integer> lemmas = new HashMap<>();
        for (List<String> lemma : lemWorkArray) {
            if (lemma.size() > 1) {
                for (int k = 0; k < lemma.size(); k++) {
                    if (isWord(luceneMorphology.getMorphInfo(lemma.get(k)).toString())) {
                        if (lemmas.containsKey(lemma.get(k))) {
                            lemmas.put(lemma.get(k), lemmas.get(lemma.get(k)) + 1);
                        } else {
                            lemmas.put(lemma.get(k), 1);
                        }
                    }
                }
            } else {
                if (isWord(luceneMorphology.getMorphInfo(lemma.get(0)).toString())) {
                    if (lemmas.containsKey(lemma.get(0))) {
                        lemmas.put(lemma.get(0), lemmas.get(lemma.get(0)) + 1);
                    } else {
                        lemmas.put(lemma.get(0), 1);
                    }
                }
            }


        }
        return lemmas;
    }

    public LuceneMorphology getRussianMorhology () {
        LuceneMorphology luceneMorphology = null;
        try {
            luceneMorphology = new RussianLuceneMorphology();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return luceneMorphology;
    }
}
