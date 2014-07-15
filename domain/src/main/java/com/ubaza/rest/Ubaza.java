package com.ubaza.rest;

import java.util.List;
import javax.security.auth.callback.Callback;
import retrofit.http.GET;


public class Ubaza {

    private static final String API_URL = "https://localhost:8080";

    static class RingtoneREST {
        String name;
        String url;
        float price;
    }

    interface GitHub {
        @GET("/ringtones/")
        void ringtones(Callback callback);
    }
}
