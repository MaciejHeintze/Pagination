package com.gaminidsystems.pagination

data class Study(
    val nct_id: String,
    val brief_title: String,
    val phase: String,
    val overall_status: String,
    val source: String,
    val study_type: String
) {
}