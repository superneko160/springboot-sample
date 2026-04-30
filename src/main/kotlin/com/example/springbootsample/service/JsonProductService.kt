package com.example.springbootsample.service

import com.example.springbootsample.model.Product
import org.springframework.context.annotation.Primary
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import tools.jackson.databind.ObjectMapper

@Service("jsonProductService")
@Primary
class JsonProductService(private val objectMapper: ObjectMapper) : ProductService {
    private val products: List<Product> by lazy {
        val resource = ClassPathResource("data/products.json")
        val wrapper = objectMapper.readValue(resource.inputStream, ProductWrapper::class.java)
        wrapper.products
    }

    override fun findAll(): List<Product> = products

    override fun findById(id: String): Product? = products.find { it.id == id }

    override fun findByCategory(category: String): List<Product> =
        products.filter { it.category == category }
}

private data class ProductWrapper(val products: List<Product>)
