package com.kryzl.meetthedevs.domain.remote

import com.kryzl.meetthedevs.domain.Developer
import retrofit2.Call
import retrofit2.Retrofit
import javax.inject.Inject

class DeveloperApiService @Inject constructor(retrofit: Retrofit) : DeveloperApi {

    private val developerApi: DeveloperApi by lazy {
        retrofit.create(DeveloperApi::class.java)
    }

    override fun getDevelopers(): Call<DevelopersList> =
        developerApi.getDevelopers()

    override fun addDeveloper(developer: Developer): Call<Developer> =
        developerApi.addDeveloper(developer)

    override fun deleteDeveloper(id: String): Call<Unit> =
        developerApi.deleteDeveloper(id)

    override fun updateDeveloper(id: String, developer: Developer): Call<Developer> =
        developerApi.updateDeveloper(id, developer)

}
