package com.kryzl.meetthedevs.domain.remote

import com.google.gson.annotations.SerializedName
import com.kryzl.meetthedevs.domain.Developer

data class DevelopersList(

    @SerializedName("developers")
    val developers: List<Developer>

) {

    companion object {
        val EmptyResponse = DevelopersList(emptyList())
    }

}
