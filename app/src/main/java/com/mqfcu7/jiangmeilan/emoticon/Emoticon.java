package com.mqfcu7.jiangmeilan.emoticon;

public class Emoticon extends Object {
    public int id;
    public int hash;
    public int type;
    public String url;

    @Override
    public String toString() {
        return "id: " + id
                + ", hash: " + hash
                + ", type: " + type
                + ", url: " + url;
    }

    public void calcHash() {
        hash = url.hashCode();
    }
}
