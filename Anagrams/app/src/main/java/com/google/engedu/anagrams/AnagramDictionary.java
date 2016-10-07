package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private static final String Log = "AnagramDict";
    private Random random = new Random();
    private ArrayList<String> wordlist = new ArrayList<>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            this.wordlist.add(word);
        }
    }

    public boolean isGoodWord(String word, String base) {
        return true;
    }

    // Helper Function

    public String sortString(String s){
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        targetWord = this.sortString(targetWord);
        for (String s: this.wordlist){
            if (targetWord.equals(this.sortString(s))){
                result.add(s);
                android.util.Log.d(Log,s);
            }
        }
        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        return result;
    }

    public String pickGoodStarterWord() {
        return "skate";
        //return "stop";
    }
}
