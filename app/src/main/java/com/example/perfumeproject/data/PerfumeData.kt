package com.example.perfumeproject.data

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


data class PerfumeData(
        @SerializedName("p_idx")
        @SerialName("p_idx")
        val p_idx : Int,
        @SerializedName("p_name")
        @SerialName("p_name")
        val p_name : String,
        @SerializedName("brand")
        @SerialName("brand")
        val brand : String,
        @SerializedName("description")
        @SerialName("description")
        val description: String,
        @SerializedName("notes")
        @SerialName("notes")
        var notes : List<String>,
        @SerializedName("image")
        @SerialName("image")
        val image : String,
        @SerializedName("isScrapped")
        @SerialName("isScrapped")
        var likeYn : Boolean = false,
        @SerializedName("similarity")
        @SerialName("similarity")
        val matchNum : Int,
        var matchVisible : Boolean = matchNum != 0,
        var isSelected : Boolean = false
): java.io.Serializable {
    val clickedLikeYn: Boolean
        get() = !(likeYn ?: true)
        val clickedItem: Boolean
                get() = !(isSelected ?: true)
}
