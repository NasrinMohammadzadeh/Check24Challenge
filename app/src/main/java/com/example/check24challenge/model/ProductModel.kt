package com.example.check24challenge.model

data class ProductModel(
    val name: String,
    val type: String,
    val id: Long,
    val color: String,
    val imageURL: String,
    val colorCode: String,
    val available: Boolean,
    val releaseDate: Long,
    val description: String,
    val longDescription: String,
    val rating: Double,
    val price: PriceModel
)
