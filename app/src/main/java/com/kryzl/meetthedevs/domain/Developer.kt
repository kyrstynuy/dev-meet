package com.kryzl.meetthedevs.domain

import com.google.gson.annotations.SerializedName

data class Developer(

    @SerializedName("dev_id")
    val id: String? = null,

    @SerializedName("full_name")
    var name: String,

    @SerializedName("photo_url")
    var photoUrl: String ? = null,

    @SerializedName("email")
    var email: String,

    @SerializedName("mobile_number")
    var mobileNo: String,

    @SerializedName("company_name")
    var companyName: String

) {

    companion object {
        val EmptyResponse = Developer(null, "", null, "", "", "")
    }

}
