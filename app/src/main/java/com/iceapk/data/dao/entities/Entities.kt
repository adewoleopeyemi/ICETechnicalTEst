package com.iceapk.data.dao.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collections_table")
data class Collection(
    @PrimaryKey(autoGenerate = true)
    val ids: Int = 0,
    val id: Int,
    val title: String,
    val description: String,
    val recipeCount: Int,
    val previewImageUrls: List<String> = emptyList(),
    var isFavorite: Boolean = false
)