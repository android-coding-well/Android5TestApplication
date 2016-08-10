package com.gosuncn.test.rxjava;

import org.json.JSONObject;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by hwj on 2016/2/29.
 */

public interface GithubServer {
   // @FormUrlEncoded
    @POST("common/getserver")
    Call<UrlRootInfo> getServer(@Query("ver") String user,@Query("type") int type);

    @Multipart
    @POST("common/upload_log")
    Call<JSONObject> upload(@Part("file") File file);
}

