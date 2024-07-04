package com.velocityappsdj.newsapp.network.models


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Source(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null
) : Parcelable