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
     * Поле списка лемма лемма/ранг
     */
    public HashMap<String, Integer> lemmas = new HashMap<>();
    /**
     * Поле морфологических частей речи подлежащих удалению из списка лемм
     */
    public String[] partsOfSpeech = {"СОЮЗ", "МЕЖД", "ПРЕДЛ", "ЧАСТ"};
    public LuceneMorphology luceneMorphology = getRussianMorhology();

    /**
     * Функция получения лемма из екста страницы сайта
     *
     * @param text -  текстовая часть страницы сайта
     * @return возвращает список лемм полученны из текстовой части сайта
     */
    public HashMap<String, Integer> getLem (String text) {
        String[] words;
        words = text.toLowerCase()
                .replaceAll("[A-z]", "")
                .replaceAll("[^\\p{L}\\p{Z}]+", "").replaceAll("[^А-я,\\s]", "")
                .split("\\s");
        List<List<String>> lemWorkArray = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {

            if (words[i].isEmpty()) {
                continue;
            } else {
                lemWorkArray.add(luceneMorphology.getNormalForms(words[i]));

            }

        }
        lemmas = setLemmasCount(lemWorkArray);
        return lemmas;
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
        for (int i = 0; i < partsOfSpeech.length; i++) {
            if (word.contains(partsOfSpeech[i])) {
                isWord = false;
            }
        }
        return isWord;
    }

    /**
     * Функция проверки наличи я леммы в списке, в случае наличия слова в списке лемм, функция увеличивает ранг леммы
     *
     * @param word - слово проверемое на наличие в списке лемм
     */
    public void putWord (String word) {
        if (lemmas.containsKey(word)) {
            lemmas.put(word, lemmas.get(word) + 1);
        } else {
            lemmas.put(word, 1);
        }
    }

    public HashMap<String, Integer> setLemmasCount (List<List<String>> lemWorkArray) {
        for (List<String> lemma : lemWorkArray) {
            long start = System.currentTimeMillis();
            if (lemma.size() > 1) {
                for (int k = 0; k < lemma.size(); k++) {
                    if (isWord(luceneMorphology.getMorphInfo(lemma.get(k)).toString())) {
                        putWord(lemma.get(k));
                    }
                }
            } else {
                if (isWord(luceneMorphology.getMorphInfo(lemma.get(0)).toString())) {
                    putWord(lemma.get(0));
                }
            }
            long finish = System.currentTimeMillis() - start;

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
