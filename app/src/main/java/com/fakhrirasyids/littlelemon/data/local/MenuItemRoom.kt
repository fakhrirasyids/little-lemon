package com.fakhrirasyids.littlelemon.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MenuItemRoom(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val desc: String,
    val category: String,
    val image: String,
)
