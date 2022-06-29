package com.test.movieapp.data.model.popular

import com.google.gson.annotations.SerializedName

data class GenreResponse(

    @field:SerializedName("genres")
    val genres: List<GenresItem>
)

data class GenresItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null
)
