package ru.kirea.postingwithpaging3.data.entities

import com.google.gson.annotations.SerializedName

data class ResponseChildren (
    @SerializedName("kind")
    val kind: String,

    @SerializedName("data")
    val data: PostData
)