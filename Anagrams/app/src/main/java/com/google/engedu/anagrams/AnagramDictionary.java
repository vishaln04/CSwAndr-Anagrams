package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Arrays;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private static final String Log = "AnagramDict";
    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<String, ArrayList<String>>();
    private HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();
    private int wordLength = DEFAULT_WORD_LENGTH;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            if (lettersToWord.containsKey(sortLetters(word))){
                lettersToWord.get(sortLetters(word)).add(word);
            }
            else {
                // asList method returns a list view of the specified array.
                lettersToWord.put(sortLetters(word), new ArrayList<String>(Arrays.asList(word)));
            }
            // Store length in Hashmap
            if (sizeToWords.containsKey(word.length()))
                sizeToWords.get(word.length()).add(word);
            else
                sizeToWords.put(word.length(), new ArrayList<String>(Arrays.asList(word)));
        }
    }

    public boolean isGoodWord(String word, String base) {
        // Provided word is a valid word
        if (!wordSet.contains(word)) {
            return false;
        }

        // Word does not contain the base word as substring
        if (word.contains(base))
            return false;
        return true;
    }

    // Helper Function sortLetters

    public String sortLetters(String s){
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        targetWord = this.sortLetters(targetWord);
        for (String s: this.wordList){
            if (targetWord.equals(this.sortLetters(s))){
                result.add(s);
                android.util.Log.d(Log,s);
            }
        }
        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String key;
        for (char s = 'a'; s <= 'z'; s++){
            key = sortLetters(word + s);
            if (lettersToWord.containsKey(key)){
                android.util.Log.d(Log, key);
                // .addAll method appends all of the elements in the specified collection to the end of this list
                result.addAll(lettersToWord.get(key));
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        ArrayList<String> wordArray = sizeToWords.get(Math.min(MAX_WORD_LENGTH, wordLength));
        int len = wordArray.size();
        int var = random.nextInt(len),loopIndex;
        String key;
        for (loopIndex = var; loopIndex < (len+var+1); loopIndex++){
            key = sortLetters(wordArray.get(loopIndex % len));
            if (lettersToWord.containsKey(key)){
                if (lettersToWord.get(key).size() >= MIN_NUM_ANAGRAMS)
                    break;
            }
        }
        wordLength+=1;
        return wordArray.get(loopIndex % len);
    }
}
