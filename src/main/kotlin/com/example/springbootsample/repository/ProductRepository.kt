package com.example.springbootsample.repository

import com.example.springbootsample.model.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<ProductEntity, String> {
    fun findByCategory(category: String): List<ProductEntity>
}
