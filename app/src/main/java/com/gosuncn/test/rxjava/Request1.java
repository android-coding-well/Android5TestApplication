package com.gosuncn.test.rxjava;

import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hwj on 2016/2/29.
 */
public class Request1 {
    GithubServer service;

    public Request1() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptors())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ip.gosunyun.com:8000/syservice3/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(GithubServer.class);
    }

    public void getServer(Callback<UrlRootInfo> callback) {
        Call<UrlRootInfo> call = service.getServer("3", 2);
        call.enqueue(callback);

        //call.cancel();//取消正在执行的任务
    }

    public void upload(Callback<JSONObject> callback) {
        Call<JSONObject> call = service.upload(new File(""));
        call.enqueue(callback);

        //call.cancel();//取消正在执行的任务
    }

    /* new Request1().getServer(new Callback<UrlRootInfo>(){

         @Override
         public void onResponse(Call<UrlRootInfo> call, Response<UrlRootInfo> response) {
             Log.e("123456", "onResponse:" + response.message() + response.body().help_url);
         }

         @Override
         public void onFailure(Call<UrlRootInfo> call, Throwable t) {

             Log.e("123456",  "  onFailure:" + t.getMessage());
         }
     });*/
    class LoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            okhttp3.Request request = chain.request();
            // Log.i("okhttp","request:"+request.url()+chain.connection()+request.headers());

            Response response = chain.proceed(request);
            Log.i("okhttp", "request: " + response.request().url() + "\nresponse: " +
                    response.body().toString());
            return response;


        }
    }

    static final class LoggingInterceptors implements Interceptor {
        private static final Logger logger = Logger.getLogger(LoggingInterceptors.class.getName());
        @Override
        public Response intercept(Chain chain) throws IOException {
            long t1 = System.nanoTime();
            Request request = chain.request();
            logger.info(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();
            logger.info(String.format("Received response for %s in %.1fms code=%d %n%s",
                    request.url(), (t2 - t1) / 1e6d, response.code(),response.headers()));
            return response;
        }
    }

      /*  public void run() throws Exception {
            Request request = new Request.Builder()
                    .url("https://publicobject.com/helloworld.txt")
                    .build();
            Response response = client.newCall(request).execute();
            response.body().close();
        }

        public static void main(String... args) throws Exception {
            new LoggingInterceptors().run();
        }*/
}

