package com.gaminidsystems.pagination

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study")
data class Study(
    val nct_id: String,
    val brief_title: String,
    val phase: String,
    val overall_status: String,
    val source: String,
    val study_type: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}