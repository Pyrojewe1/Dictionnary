package com.example.dictionary;

public class Word {
    Word(){}

    public Word(String word, String meaning, String sample) {
        this.word = word;
        this.meaning = meaning;
        this.sample = sample;
    }

    private String word;
    private  String meaning;
    private  String sample;

    public void setWord(String word) {
        this.word = word;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getWord() {
        return word;
    }

    public String getMeaning() {
        return meaning;
    }

    public String getSample() {
        return sample;
    }
}
