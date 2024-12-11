package com.capstone.personaway.model

import com.capstone.personaway.model.DataModel
import java.io.Serializable

class ResultModel(
    val data: DataModel,
    val image_url: String,
    val prediction: Int,
    val status: String,
    val date: String,
    val time: String
) : Serializable
