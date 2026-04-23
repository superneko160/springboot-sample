package com.example.springbootsample.model

data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: Int,
    val stock: Int,
    val description: String,
    val tags: List<String>,
    val imageUrl: String
)
