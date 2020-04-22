package com.example.biodata.Biodata

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bio_table")
data class Bio(
    var nama: String,
    var alamat: String,
    var nomer: String,
    var priority: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}