package com.pifactorial.showcase.rest;

import com.pifactorial.showcase.domain.Call;
import com.pifactorial.showcase.domain.Thing;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.otto.Bus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.transform.Result;

import retrofit.Callback;
import retrofit.client.Header;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import timber.log.Timber;


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

    static class ThingREST {
        String id;
        String name;
        String text;
        String category;
        String homepage;
        String urlLogo;
        String urlProduct;
        int logoWidth;
        int logoHeight;
        int productWidth;
        int productHeight;

        private Thing toDomain() {
            return new Thing( name, urlLogo, urlProduct, category, text, homepage, logoWidth, logoHeight, productWidth, productHeight );
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
        @GET( "/v1/things" )
        void getThings( Callback<ArrayList<ThingREST>> callback );

        @POST( "/v1/event/add" )
        void insertEvent( @Body EventREST event, Callback<ReturnREST> callback );
    }

    public void getThings( ) {
        Timber.d( "Getting Things" );

        Callback<ArrayList<ThingREST>> callback = new Callback<ArrayList<ThingREST>>() {
            @Override
            public void success( ArrayList<ThingREST> list, Response response ) {
                ArrayList<Thing> retList = new ArrayList<Thing>();
                for( ThingREST rest : list )
                    retList.add( rest.toDomain() );

                Timber.d( "Posting %d Things to otto", list.size() );
                mBus.post( retList );
            }

            @Override
            public void failure( RetrofitError retrofitError ) {
                Timber.e( "Failed %s", retrofitError.getUrl() );
                mBus.post( retrofitError );
            }
        };

        showcase.getThings( callback );
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
