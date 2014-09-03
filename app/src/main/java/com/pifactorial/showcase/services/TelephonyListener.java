package com.pifactorial.showcase.services;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.pifactorial.showcase.util.Sound;
import com.pifactorial.showcase.domain.Call;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

public class TelephonyListener extends PhoneStateListener {
    private Context mContext;
    private CounterService mService;
    private STATE mCurrentState;

    private DateTime startRingTime;

    public enum STATE { STANDBY, CALLING, ANSWERED }

    public TelephonyListener( CounterService service, Context context ) {
        mContext = context;
        mService = service;
        mCurrentState = STATE.STANDBY;
    }

    public void onCallStateChanged( int state, String incomingNumber ) {
        super.onCallStateChanged( state, incomingNumber );
        switch ( state ) {
        case TelephonyManager.CALL_STATE_IDLE:
            // CALL_STATE_IDLE;
            recordCall( STATE.STANDBY );
            mCurrentState = STATE.STANDBY;
            break;
        case TelephonyManager.CALL_STATE_OFFHOOK:
            // CALL_STATE_OFFHOOK;
            recordCall( STATE.ANSWERED );
            mCurrentState = STATE.ANSWERED;
            break;
        case TelephonyManager.CALL_STATE_RINGING:
            //CALL_STATE_RINGING
            startRingTime = new DateTime();
            mCurrentState = STATE.CALLING;
            break;

        default:
            break;
        }
    }

    public void recordCall( STATE newState ) {
        DateTime currTime = new DateTime();
        int volume = Sound.getRingtoneVolume(mContext);

        /* Check if the phone was ringing and user answered the call */
        if( mCurrentState == STATE.CALLING && newState == STATE.ANSWERED ) {
            int seconds = Seconds.secondsBetween( startRingTime, currTime ).getSeconds();
            boolean answered = true;
            mService.addCall( new Call( seconds, answered, volume ) );
        }

        if( mCurrentState == STATE.CALLING && newState == STATE.STANDBY ) {
            int seconds = Seconds.secondsBetween( startRingTime, currTime ).getSeconds();
            boolean answered = false;
            mService.addCall( new Call( seconds, answered, volume ) );
        }
    }
}
