package com.example.springbootsample.service

import com.example.springbootsample.model.Product
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import tools.jackson.databind.ObjectMapper

@Service
class ProductService(private val objetMapper: ObjectMapper) {
    private val products: List<Product> by lazy {
        val resource = ClassPathResource("data/products.json")
        val wrapper = objetMapper.readValue(resource.inputStream, ProductWrapper::class.java)
        wrapper.products
    }

    fun findAll(): List<Product> = products

    fun findById(id: String): Product? = products.find { it.id == id }

    fun findByCategory(category: String): List<Product> =
        products.filter { it.category == category }
}

private data class ProductWrapper(val products: List<Product>)
