package com.example.springbootsample.service

import com.example.springbootsample.model.Product
import com.example.springbootsample.repository.ProductRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("mySqlProductService")
class MySqlProductService(private val productRepository: ProductRepository) : ProductService {
    
    @Transactional(readOnly = true)
    override fun findAll(): List<Product> = 
        productRepository.findAll().map { it.toProduct() }

    @Transactional(readOnly = true)
    override fun findById(id: String): Product? = 
        productRepository.findById(id).map { it.toProduct() }.orElse(null)

    @Transactional(readOnly = true)
    override fun findByCategory(category: String): List<Product> = 
        productRepository.findByCategory(category).map { it.toProduct() }
}
