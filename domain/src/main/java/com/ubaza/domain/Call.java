package com.ubaza.domain;

import java.lang.StringBuilder;
import java.util.List;

public class Call {
    private int mDuration;
    private boolean mAnswered;

    public Call(int duration, boolean answered) {
        mDuration = duration;
        mAnswered = answered;
    }

    @Override
    public String toString() {
        String answered = mAnswered ? "answered" : "declined" ;
        return "Call was" + answered + " and ring lasted " + mDuration;
    }

    public static String toString(List<Call> calls) {
        StringBuilder s = new StringBuilder();
        for( Call c : calls) {
            s.append(c.toString() + '\n');
        }
        return s.toString();
    }
}
