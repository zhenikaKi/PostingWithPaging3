package ru.kirea.postingwithpaging3.data.entities

import com.google.gson.annotations.SerializedName

data class ResponseMainData (
    @SerializedName("after")
    val after: String,

    @SerializedName("children")
    val children: List<ResponseChildren>
)