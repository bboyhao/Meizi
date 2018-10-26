package com.example.bboyhao.meizi;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyOkHttp {
    public static OkHttpClient client = new OkHttpClient();
    public static String get(String url){
        try{
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful())
                return response.body() != null ? response.body().string() : null;
            else{
                throw new IOException("Unexpected response" + response);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;

    }
}
