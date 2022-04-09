package main;

import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * Класс создания лемм со стараницы саййта
 * @autor VG
 * @version 0.1
 * **/
public class LemCreator {
    /**Поле списка лемма лемма/ранг*/
    public  HashMap<String, Integer> lemmas = new HashMap<>();
    /**Поле морфологических частей речи подлежащих удалению из списка лемм*/
    public  String[] partsOfSpeech = {"СОЮЗ","МЕЖД","ПРЕДЛ","ЧАСТ"};

    /**Функция получения лемма из екста страницы сайта
     * @param text -  текстовая часть страницы сайта
     * @return возвращает список лемм полученны из текстовой части сайта*/
    public  HashMap<String, Integer> getLem(String text) throws IOException {
        LuceneMorphology luceneMorph =
                new RussianLuceneMorphology();
        List<List<String>> lem  = new ArrayList<>();
        String[] words = text.toLowerCase()
                .replaceAll("[A-z]", "")
                .replaceAll("[^\\p{L}\\p{Z}]+", "").replaceAll("[^А-я,\\s]", "")
                .split("\\s");
        for (String word : words){
         if(word.isEmpty()){
             continue;
         }else {lem.add(luceneMorph.getNormalForms(word));
         }

        }
        for(List<String> lemma: lem){
            if (lemma.size() > 1){
                for(int k = 0; k < lemma.size() ; k++){
                    if(isWord(luceneMorph.getMorphInfo(lemma.get(k)).toString())){
                        putWord(lemma.get(k));
                    }
                }
            }
            else{
                if(isWord(luceneMorph.getMorphInfo(lemma.get(0)).toString())){
                    putWord(lemma.get(0));
                }
            }
        }

        return lemmas;
    }
    /**функция определения части речи
     * @param  word - проверемое слово
     * @return  Возваращет false если переданная часть речи содержится в массиве partsOfSpeech
     * возвращает true если переданная чась речи является словом*/
    public  boolean isWord(String word){
        boolean isWord  = true;
        for (int i = 0; i < partsOfSpeech.length; i ++){
            if (word.contains(partsOfSpeech[i])){
                isWord = false;
            }
        }
        return isWord;
    }
    /**Функция проверки наличи я леммы в списке, в случае наличия слова в списке лемм, функция увеличивает ранг леммы
     * @param word - слово проверемое на наличие в списке лемм
     * */
    public void putWord(String word){
        if (lemmas.containsKey(word)){
            lemmas.put(word, lemmas.get(word) + 1);
        }
        else{
            lemmas.put(word, 1);
        }


    }
}
