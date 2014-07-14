package com.spengilley.activityfragmentmvp.ui.main;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.spengilley.activityfragmentmvp.R;
import com.spengilley.activityfragmentmvp.ui.common.BaseFragment;
import com.spengilley.activityfragmentmvp.ui.main.presenters.IntroPresenterImpl;
import com.spengilley.activityfragmentmvp.ui.main.views.IntroView;

import javax.inject.Inject;
import android.util.Log;
import android.os.Handler;
import android.os.Message;
import com.spengilley.activityfragmentmvp.services.CounterService;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;
import android.content.Context;
import android.content.ServiceConnection;
import android.content.ComponentName;
import android.os.IBinder;

public class IntroFragment extends BaseFragment implements IntroView {

    private static final String TAG = "IntroFragment";
    @Inject
    IntroPresenterImpl presenter;
    private FragmentCallback callback;
    private View view;

    private Button startService;

    private Messenger mReqMessengerRef = null;


    public static IntroFragment newInstance() {
        return new IntroFragment();
    }

    public IntroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_intro, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize button with listener
        startService = (Button) view.findViewById(R.id.start_service);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // presenter.getDetails();
                startCallListenerService();
                Log.d(TAG, "Clicked view");
            }
        });

        startService = (Button) view.findViewById(R.id.start_service);
    }

    class ReplyHandler extends Handler {
        /**
         * Callback to handle the reply from the UniqueIDGeneratorService.
         */
        public void handleMessage(Message reply) {
            // Get the unique ID encapsulated in reply Message.
            String uniqueID = CounterService.callID(reply);

            Log.d(TAG, "Got result" + uniqueID);
        }
    }

    /**
     * This ServiceConnection is used to receive a Messenger reference
     * after binding to the UniqueIDGeneratorService using bindService().
     */
    private ServiceConnection mSvcConn = new ServiceConnection() {
            /**
             * Called after the UniqueIDGeneratorService is connected to
             * convey the result returned from onBind().
             */
            public void onServiceConnected(ComponentName className, IBinder binder) {
                mReqMessengerRef = new Messenger(binder);
            }

            /**
             * Called if the Service crashes and is no longer
             * available.  The ServiceConnection will remain bound,
             * but the Service will not respond to any requests.
             */
            public void onServiceDisconnected(ComponentName className) {
                Log.d(TAG, "Service has crashed");
                mReqMessengerRef = null;
            }
	};

    public void startCallListenerService() {
        Log.d(TAG, "calling bindService()");
        if (mReqMessengerRef == null) {
            // Bind to the UniqueIDGeneratorService associated with this Intent.
            getActivity().bindService(CounterService.makeIntent(getActivity()),
                        mSvcConn,
                        Context.BIND_AUTO_CREATE);
        }
    }

    public void getCalls() {
        // Create a request Message that indicates the Service should
        // send the reply back to ReplyHandler encapsulated by the
        // Messenger.
        Message request = Message.obtain();
        request.replyTo = new Messenger(new ReplyHandler());

        Log.d(TAG, "Inside getCalls");
        try {
            if (mReqMessengerRef != null) {
                Log.d(TAG, "sending message");
                mReqMessengerRef.send(request);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Bit naughty but we know it will work!
        callback = (FragmentCallback) activity;
    }

    /**
     * View implementation
     */
    @Override
    public void loadDetailsFragment() {
        // Ask callback to load details fragment
        callback.loadDetailFragment();
    }
}
