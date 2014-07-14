package com.spengilley.activityfragmentmvp.services;

import android.telephony.PhoneStateListener;
import android.widget.Toast;
import android.telephony.TelephonyManager;
import android.content.Context;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import android.content.Intent;

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
                // Toast.makeText(mContext, "CALL_STATE_IDLE", 1000).show();
                recordCall(STATE.STANDBY, startRingTime);

                mCurrentState = STATE.STANDBY;
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                //CALL_STATE_OFFHOOK;
                // Toast.makeText(mContext, "CALL_STATE_OFFHOOK", 1000).show();
                recordCall(STATE.ANSWERED, startRingTime);

                mCurrentState = STATE.ANSWERED;
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                //CALL_STATE_RINGING
                startRingTime = new DateTime();

                // Toast.makeText(mContext, "CALL_STATE_RINGING", 1000).show();
                mCurrentState = STATE.CALLING;
                break;

            default:
                break;
        }
    }

    public void recordCall(STATE newState, DateTime startRingTime) {
            DateTime currTime = new DateTime();

            /* Check if the phone was ringing and user answered the call */
            if(mCurrentState == STATE.CALLING && newState == STATE.ANSWERED) {
                int seconds = Seconds.secondsBetween(startRingTime, currTime).getSeconds();
                Toast.makeText(mContext, "Ringtone lasted seconds: " + seconds , 10000).show();
                mContext.getApplicationContext().sendBroadcast(new Intent("ReceivedCall"));
            }

            if(mCurrentState == STATE.CALLING && newState == STATE.STANDBY) {
                int seconds = Seconds.secondsBetween(startRingTime, currTime).getSeconds();
                Toast.makeText(mContext, "Ringtone lasted seconds: " + seconds , 10000).show();
                mContext.getApplicationContext().sendBroadcast(new Intent("ReceivedCall"));
            }
    }
}
