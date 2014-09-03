package com.pifactorial.showcase.rest;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;
import com.pifactorial.showcase.domain.Call;
import com.pifactorial.showcase.domain.Ringtone;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.Result;

import retrofit.Callback;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import timber.log.Timber;
import com.squareup.okhttp.Cache;
import retrofit.client.OkClient;
import retrofit.RequestInterceptor;


public class ShowcaseRestClient {

    // public static final String API_URL = "http://stark-savannah-4631.herokuapp.com/";
    public static final String API_URL = "http://citysdk.tagus.ist.utl.pt:9000/";
    public static final String TAG = "ShowcaseRestClient";
    private final Bus mBus;
    final RestAdapter restAdapter;
    final ShowcaseRestClient.Showcase showcase;

    public ShowcaseRestClient( Bus bus , String cacheAbsolutePath , RequestInterceptor requestInterceptor ) {
        mBus = bus;
        bus.register( this );

        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File( cacheAbsolutePath , "HttpCache" );
        try {
            Cache cache = new Cache( cacheDirectory, cacheSize );
            OkHttpClient client = new OkHttpClient();
            client.setCache( cache );

            // Create an instance of our AsynchronousApi interface.
            restAdapter = new RestAdapter.Builder()
            .setEndpoint( ShowcaseRestClient.API_URL )
            .setClient ( new OkClient( client ) )
            .setLogLevel( RestAdapter.LogLevel.FULL ) /* TODO - Remove this line */
            .setRequestInterceptor( requestInterceptor )
            .build();

            // Create an instance of our GitHub API interface.
            showcase = restAdapter.create( ShowcaseRestClient.Showcase.class );
        } catch ( IOException e ) {
            Timber.e( "IOExceptio %s", e );
            throw new RuntimeException( e.toString() );
        }
    }

    static class RingtoneREST {
        String id;
        String name;
        String uri;
        int price;

        private Ringtone toDomain() {
            return new Ringtone( name, uri, price );
        }
    }

    static class ReturnREST {
        int code;
        String desc;
    }

    static class EventREST {

        public EventREST() { }

        public EventREST( Call call ) {
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

    public interface Showcase {
        @GET( "/v1/ringtones" )
        void getRingtones( Callback<ArrayList<RingtoneREST>> callback );

        @POST( "/v1/event/add" )
        void insertEvent( @Body EventREST event, Callback<ReturnREST> callback );
    }

    public void getRingtones( ) {
        Timber.d( "Getting ringtones" );

        Callback<ArrayList<RingtoneREST>> callback = new Callback<ArrayList<RingtoneREST>>() {
            @Override
            public void success( ArrayList<RingtoneREST> list, Response response ) {
                ArrayList<Ringtone> retList = new ArrayList<Ringtone>();
                for( RingtoneREST rest : list )
                    retList.add( rest.toDomain() );

                Timber.d( "Posting %d ringtones to otto", list.size() );
                mBus.post( retList );
            }

            @Override
            public void failure( RetrofitError retrofitError ) {
                Timber.e( "Failed %s", retrofitError.getUrl() );
                mBus.post( retrofitError );
            }
        };

        showcase.getRingtones( callback );
    }


    public void pushCallAsync( Call call ) {
        Timber.d( "Pushing a call to the server" );

        Callback<ReturnREST> callback = new Callback<ReturnREST>() {
            @Override
            public void success( ReturnREST ring, Response response ) {
                Timber.d( "Set event on server %s", ring );
            }

            @Override
            public void failure( RetrofitError retrofitError ) {
                Timber.e( "Failed to upload event %s", retrofitError.getUrl() );
            }
        };

        showcase.insertEvent( new EventREST( call ), callback );
    }
}