package com.mqfcu7.jiangmeilan.emoticon;

import java.util.ArrayList;
import java.util.List;

public class JokeSuite extends Object {
    public int id;
    public String content;
    public String userName;
    public String userAvatar;
    public int good;
    public int bad;
    public int comment;
    public String time;

    public class Comment extends Object {
        public int id;
        public String content;
        public String userName;
    }
    public List<Comment> topComments;

    public JokeSuite() {
        topComments = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "id: " + id
                + ", content: " + content
                + ", userName: " + userName
                + ", userAvatar: " + userAvatar
                + ", good: " + good
                + ", bad: " + bad
                + ", comment: " + comment
                + ", time: " + time;
    }
}
