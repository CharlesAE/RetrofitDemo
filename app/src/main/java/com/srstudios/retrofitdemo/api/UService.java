package com.srstudios.retrofitdemo.api;

import com.srstudios.retrofitdemo.models.UCatalog;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by chuck on 2/18/16.
 */
public interface UService {
    /**
     * Every method must have an HTTP annotation that provides the request method and relative URL.
     * There are five built-in annotations: GET, POST, PUT, DELETE, and HEAD.
     * The relative URL of the resource is specified in the annotation.
     */
    @GET("courses")
    Call<UCatalog> listCatalog();
}
