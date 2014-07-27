package com.ubaza.domain;

import java.lang.StringBuilder;
import java.util.List;

public class Call {
    private int mDuration;
    private boolean mAnswered;
    private int mVolume;
    private long mDate;

    public Call(int duration, boolean answered, int volume, long date) {
        mDuration = duration;
        mAnswered = answered;
        mVolume = volume;
        mDate = date;
    }

    public Call(int duration, boolean answered, int volume) {
        mDuration = duration;
        mAnswered = answered;
        mVolume = volume;
        mDate = System.currentTimeMillis();
    }

    @Override
    public String toString() {
        String answered = mAnswered ? "answered" : "declined" ;
        return "Call was " + answered + " and ring lasted " + mDuration;
    }

    public static String toString(List<Call> calls) {
        StringBuilder s = new StringBuilder();
        for( Call c : calls) {
            s.append(c.toString() + '\n');
        }
        return s.toString();
    }

    /**
     * Get mDuration.
     *
     * @return mDuration as int.
     */
    public int getDuration()
    {
        return mDuration;
    }

    /**
     * Get mAnswered.
     *
     * @return mAnswered as boolean.
     */
    public boolean getAnswered()
    {
        return mAnswered;
    }

    /**
     * Get mVolume.
     *
     * @return mVolume as int.
     */
    public int getVolume()
    {
        return mVolume;
    }

    /**
     * Set mVolume.
     *
     * @param mVolume the value to set.
     */
    public void setVolume(int volume)
    {
        this.mVolume = mVolume;
    }

    /**
     * Get date.
     *
     * @return date as long.
     */
    public long getDate()
    {
        return mDate;
    }

    /**
     * Set date.
     *
     * @param date the value to set.
     */
    public void setDate(long date)
    {
        this.mDate = date;
    }
}
