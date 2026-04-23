package com.example.springbootsample.controller

import com.example.springbootsample.model.Product
import com.example.springbootsample.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/products")
class ProductController(private val productService: ProductService) {

    @GetMapping
    fun getAll(): List<Product> = productService.findAll()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: String): ResponseEntity<Product> =
        productService.findById(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @GetMapping("/category/{category}")
    fun getByCategory(@PathVariable category: String): List<Product> =
        productService.findByCategory(category)
}
