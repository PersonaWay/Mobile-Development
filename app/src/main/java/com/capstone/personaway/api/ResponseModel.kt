package com.capstone.personaway.api

import java.io.Serializable

data class ResponseModel (
    val data: DataModel,
    val image_url: String,
    val prediction: Int,
    val status: String
) : Serializable

data class DataModel(
    val deskripsi: String,
    val hubungan: String,
    val label: String,
    val pekerjaan: String,
    val predik: Int
)
