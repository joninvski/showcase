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

public class UbazaRestClient {

    public static final String API_URL = "http://stark-savannah-4631.herokuapp.com/";
    public static final String TAG = "UbazaRestClient";
    private final Bus mBus;
    final RestAdapter restAdapter;
    UbazaRestClient.Ubaza ubaza;

    public UbazaRestClient(Bus bus) {
        mBus = bus;
        bus.register(this);

        // Create an instance of our AsynchronousApi interface.
        restAdapter = new RestAdapter.Builder().setEndpoint(UbazaRestClient.API_URL).build();

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

    public interface Ubaza {
        @GET("/v1/ringtones")
        void getRingtones(Callback<ArrayList<RingtoneREST>> callback);
    }

    public void getRingtones( ){

        Callback<ArrayList<RingtoneREST>> callback = new Callback<ArrayList<RingtoneREST>>() {
            @Override
            public void success(ArrayList<RingtoneREST> list, Response response) {
                ArrayList<Ringtone> retList = new ArrayList<Ringtone>();
                for(RingtoneREST rest : list)
                    retList.add(rest.toDomain());

                mBus.post(retList);
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        };

        ubaza.getRingtones(callback);
    }
}
