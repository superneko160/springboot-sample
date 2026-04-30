package com.example.springbootsample.service

import com.example.springbootsample.model.Product

interface ProductService {
    fun findAll(): List<Product>
    fun findById(id: String): Product?
    fun findByCategory(category: String): List<Product>
}
