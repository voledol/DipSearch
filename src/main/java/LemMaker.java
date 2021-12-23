import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LemMaker {
    public static HashMap<String, Integer> lemmas = new HashMap<>();
    public static String[] partsOfSpeech = {"СОЮЗ","МЕЖД","ПРЕДЛ","ЧАСТ"};


    public static HashMap<String, Integer> getLem(String text) throws IOException {
        LuceneMorphology luceneMorph =
                new RussianLuceneMorphology();
        List<List<String>> lem  = new ArrayList<>();


        String[] words = text.toLowerCase()
                .replaceAll("[A-z]", "")
                .replaceAll("[^\\p{L}\\p{Z}]+", "")
                .split("\\s");
        for (String word : words){
         if(word.isEmpty()){
             continue;
         }else {lem.add(luceneMorph.getNormalForms(word));}

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
    public static boolean isWord(String text){
        boolean isWord  = true;
        for (int i = 0; i < partsOfSpeech.length; i ++){
            if (text.contains(partsOfSpeech[i])){
                isWord = false;
            }
        }
        return isWord;
    }
    public static void putWord(String text){
        if (lemmas.containsKey(text)){
            lemmas.put(text, lemmas.get(text) + 1);
        }
        else{
            lemmas.put(text, 1);
        }


    }
    public static void print(){
        for (Map.Entry entry: lemmas.entrySet()) {

            System.out.println(entry);
        }
    }
}
