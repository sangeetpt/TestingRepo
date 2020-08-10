package com.infosys.kbcmovies.repo

import com.infosys.kbcmovies.model.MovieModel
import com.infosys.kbcmovies.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("movies.json")
    fun getMovies(): Call<ArrayList<MovieModel>>

    companion object Factory {

        fun create(): ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()

            return retrofit.create(ApiInterface::class.java)

        }

    }

}