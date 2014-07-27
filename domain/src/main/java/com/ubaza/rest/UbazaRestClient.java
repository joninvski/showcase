package com.ubaza.rest;

import com.squareup.otto.Bus;

import com.ubaza.domain.Ringtone;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;

import retrofit.client.Response;

import retrofit.http.GET;

import retrofit.RestAdapter;

import retrofit.RetrofitError;
import timber.log.Timber;
import retrofit.http.POST;
import com.ubaza.domain.Call;
import java.util.Arrays;
import retrofit.client.Header;
import retrofit.http.Field;
import retrofit.http.Body;
import retrofit.http.FormUrlEncoded;
import javax.xml.transform.Result;

public class UbazaRestClient {

    // public static final String API_URL = "http://stark-savannah-4631.herokuapp.com/";
    public static final String API_URL = "http://citysdk.tagus.ist.utl.pt:9000/";
    public static final String TAG = "UbazaRestClient";
    private final Bus mBus;
    final RestAdapter restAdapter;
    UbazaRestClient.Ubaza ubaza;

    public UbazaRestClient(Bus bus) {
        mBus = bus;
        bus.register(this);

        // Create an instance of our AsynchronousApi interface.
        restAdapter = new RestAdapter.Builder()
            .setEndpoint(UbazaRestClient.API_URL)
            .setLogLevel(RestAdapter.LogLevel.FULL) /* TODO - Remove this line */
            .build();

        // Create an instance of our GitHub API interface.
        ubaza = restAdapter.create(UbazaRestClient.Ubaza.class);
    }

    static class RingtoneREST {
        String id;
        String name;
        String uri;
        int price;

        private Ringtone toDomain() {
            return new Ringtone(name, uri, price);
        }
    }

    static class ReturnREST {
        int code;
        String desc;
    }

    static class EventREST {

        public EventREST() { }

        public EventREST(Call call) {
            this.duration = call.getDuration();
            this.answered = call.getAnswered();
            this.volume = call.getVolume();
            this.date = call.getDate();
        }

        int duration;
        boolean answered;
        int volume;
        long date;
    }

    public interface Ubaza {
        @GET("/v1/ringtones")
        void getRingtones(Callback<ArrayList<RingtoneREST>> callback);

        @POST("/v1/event/add")
        void insertEvent(@Body EventREST event, Callback<ReturnREST> callback);
    }

    public void getRingtones( ){
        Timber.d("Getting ringtones");

        Callback<ArrayList<RingtoneREST>> callback = new Callback<ArrayList<RingtoneREST>>() {
            @Override
            public void success(ArrayList<RingtoneREST> list, Response response) {
                ArrayList<Ringtone> retList = new ArrayList<Ringtone>();
                for(RingtoneREST rest : list)
                    retList.add(rest.toDomain());

                Timber.d("Posting %d ringtones to otto", list.size());
                mBus.post(retList);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Timber.e("Failed %s", retrofitError.getUrl());
                mBus.post(retrofitError);
            }
        };

        ubaza.getRingtones(callback);
    }


    public void pushCallAsync( Call call ){
        Timber.d("Pushing a call to the server");

        Callback<ReturnREST> callback = new Callback<ReturnREST>() {
            @Override
            public void success(ReturnREST ring, Response response) {
                Timber.d("Set event on server %s", ring);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Timber.e("Failed to upload event %s", retrofitError.getUrl());
            }
        };

        ubaza.insertEvent(new EventREST(call), callback);
    }
}
