package com.example.check24challenge.model

data class ProductListModel(
    val header: HeaderModel? = null,
    val filters: ArrayList<String>? = null,
    val products: ArrayList<ProductModel>? = null
)
