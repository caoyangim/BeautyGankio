package com.cy.beautygankio.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable
import java.util.*

@Entity
@TypeConverters(StringConvert::class,DateConverters::class)
data class Girl(
    @PrimaryKey val _id: String,
    val author: String,
    val category: String,
    val createdAt: Date,
    val desc: String,
    @ColumnInfo(name = "String")
    val images: List<String>,
    val likeCounts: Int,
    val publishedAt: String,
    val stars: Int,
    val title: String,
    val type: String,
    val url: String,
    val views: Int
):Serializable