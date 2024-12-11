package com.capstone.personaway.api

data class ResponseModel(
    val data: DataModel,
    val image_url: String,
    val prediction: Int,
    val status: String
)

data class DataModel(
    val deskripsi: String,
    val hubungan: String,
    val label: String,
    val pekerjaan: String,
    val predik: Int
)
