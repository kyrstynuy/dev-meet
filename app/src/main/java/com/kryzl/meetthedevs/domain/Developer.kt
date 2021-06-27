package com.kryzl.meetthedevs.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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

) : Parcelable {

    companion object {
        val EmptyResponse = Developer(null, "", null, "", "", "")
    }

}
