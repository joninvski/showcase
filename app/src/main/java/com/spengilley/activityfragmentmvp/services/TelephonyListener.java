package com.spengilley.activityfragmentmvp.services;

import android.telephony.PhoneStateListener;
import android.widget.Toast;
import android.telephony.TelephonyManager;
import android.content.Context;
import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class TelephonyListener extends PhoneStateListener
{
    private Context mContext;
    private STATE mCurrentState;

    private DateTime startRingTime;

    public enum STATE { STANDBY, CALLING, ANSWERED }


    public TelephonyListener(Context context) {
        mContext = context;
        mCurrentState = STATE.STANDBY;
    }

    public void onCallStateChanged(int state, String incomingNumber)
    {
        super.onCallStateChanged(state, incomingNumber);
        switch (state)
        {
            case TelephonyManager.CALL_STATE_IDLE:
                //CALL_STATE_IDLE;
                Toast.makeText(mContext, "CALL_STATE_IDLE", 10000).show();
                startRingTime = new DateTime();

                mCurrentState = STATE.STANDBY;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //CALL_STATE_OFFHOOK;
                Toast.makeText(mContext, "CALL_STATE_OFFHOOK", 10000).show();

                mCurrentState = STATE.ANSWERED;
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                //CALL_STATE_RINGING
                Toast.makeText(mContext, "CALL_STATE_RINGING", 10000).show();

                mCurrentState = STATE.CALLING;
                break;
            default:
                break;
        }
    }

    public void recordCall(STATE newState, DateTime startRingTime) {
            DateTime currTime = new DateTime();

            int seconds = Seconds.secondsBetween(startRingTime, currTime).getSeconds();

            /* Check if the phone was ringing and user answered the call */
            if(mCurrentState == STATE.CALLING && newState == STATE.ANSWERED) {
                Toast.makeText(mContext, "Ringtone lasted seconds: " + seconds , 10000).show();
            }

            if(mCurrentState == STATE.CALLING && newState == STATE.STANDBY) {
                Toast.makeText(mContext, "Ringtone lasted seconds: " + seconds , 10000).show();
            }
    }

}
