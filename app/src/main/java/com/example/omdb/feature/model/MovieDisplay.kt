package com.example.omdb.feature.model

import com.example.core.abstraction.RecyclerViewItem

data class MovieDisplay(
    override val id: Int = 0,
    val name: String = "",
    val thumbnail: String = "",
    val genre: String = "",
    val year: String
) : RecyclerViewItem()
