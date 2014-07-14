package com.spengilley.activityfragmentmvp.services;

import android.app.Service;
import android.os.Messenger;
import android.widget.Toast;
import android.os.Message;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.IBinder;
import android.content.Intent;
import android.content.Context;

public class CounterService extends Service {

    private final String TAG = getClass().getName();

    /**
     * A Messenger that encapsulates the RequestHandler used to handle
     * request Messages sent from the UniqueIDGeneratorActivity.
     */
    private Messenger mReqMessenger = null;

    /**
     * Hook method called when the Service is created.
     */
    @Override
    public void onCreate() {
        // A Messenger that encapsulates the RequestHandler used to
        // handle request Messages sent from the
        // UniqueIDGeneratorActivity.
        mReqMessenger = new Messenger(new RequestHandler());
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
    }

    /**
     * Factory method to make the desired Intent.
     */
    public static Intent makeIntent(Context context) {
        // Create the Intent that's associated to the
        // UniqueIDGeneratorService class.
        return new Intent(context, CounterService.class);
    }


    /**
     * @class RequestHandler
     *
     * @brief This class handles messages sent by the
     *        UniqueIDGeneratorActivity.
     */
    private class RequestHandler extends Handler {
        /**
         * Return a Message containing an ID that's unique
         * system-wide.
         */
        private Message generateCall() {
            String uniqueID;

            // Create a Message that's used to send the unique ID back
            // to the UniqueIDGeneratorActivity.
            Message reply = Message.obtain();
            Bundle data = new Bundle();
            data.putString("ID", "Call 1");
            reply.setData(data);
            return reply;
        }

        // Hook method called back when a request Message arrives from
        // the UniqueIDGeneratorActivity.  The message it receives
        // contains the Messenger used to reply to the Activity.
        public void handleMessage(Message request) {

            // Store the reply Messenger so it doesn't change out from
            // underneath us.
            final Messenger replyMessenger = request.replyTo;

            // Put a runnable that generates a unique ID into the
            // thread pool for subsequent concurrent processing.
            Message reply = generateCall();

            try {
                // Send the reply back to the
                // UniqueIDGeneratorActivity.
                replyMessenger.send(reply);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        };
    }

    public static String uniqueID(Message replyMessage) {
        return replyMessage.getData().getString("ID");
    }

    /**
     * Called when the service is destroyed, which is the last call
     * the Service receives informing it to clean up any resources it
     * holds.
     */
    @Override
    public void onDestroy() {
        /* Nothing to on the end of the service */
    }

    /**
     * Factory method that returns the underlying IBinder associated
     * with the Request Messenger.
     */
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "onBind", Toast.LENGTH_LONG).show();
        return mReqMessenger.getBinder();
    }
}
