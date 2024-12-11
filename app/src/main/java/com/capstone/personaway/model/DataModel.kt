package com.capstone.personaway.model

import java.io.Serializable

data class DataModel(
    val deskripsi: String,
    val hubungan: String,
    val label: String,
    val pekerjaan: String,
    val predik: Int
) : Serializable