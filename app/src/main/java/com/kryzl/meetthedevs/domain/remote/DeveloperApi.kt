package com.kryzl.meetthedevs.domain.remote

import com.kryzl.meetthedevs.domain.Developer
import retrofit2.Call
import retrofit2.http.*

interface DeveloperApi {

    @GET("$ENDPOINT")
    fun getDevelopers(): Call<DevelopersList>

    @POST("${Companion.ENDPOINT}")
    fun addDeveloper(@Body developer: Developer): Call<Developer>

    @PUT("${Companion.ENDPOINT}/{id}")
    fun updateDeveloper(@Path("id") id: String, @Body developer: Developer): Call<Developer>

    @DELETE("${Companion.ENDPOINT}/{id}")
    fun deleteDeveloper(@Path("id") id: String): Call<Unit>

    companion object {
        private const val ENDPOINT = "developers"
    }
}
