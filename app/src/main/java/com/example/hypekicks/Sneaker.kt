package com.example.hypekicks

import java.io.Serializable

data class Sneaker (
    val brand: String = "",
    val imageUrl: String = "",
    val modelName: String = "",
    val releaseYear: Int = 0,
    val resellPrice: Int = 0
) : Serializable