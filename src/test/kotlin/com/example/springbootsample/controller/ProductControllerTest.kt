package com.example.springbootsample.controller

import com.example.springbootsample.model.Product
import com.example.springbootsample.service.ProductService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(ProductController::class)
class ProductControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockitoBean
    lateinit var productService: ProductService

    @Test
    fun `getAll returns all products`() {
        val products = listOf(
            Product("1", "Product 1", "Category A", 100, 10, "Desc 1", listOf("tag1"), "url1"),
            Product("2", "Product 2", "Category B", 200, 20, "Desc 2", listOf("tag2"), "url2")
        )
        `when`(productService.findAll()).thenReturn(products)

        mockMvc.perform(get("/api/products"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[1].id").value("2"))
    }

    @Test
    fun `getById returns product when found`() {
        val product = Product("1", "Product 1", "Category A", 100, 10, "Desc 1", listOf("tag1"), "url1")
        `when`(productService.findById("1")).thenReturn(product)

        mockMvc.perform(get("/api/products/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.name").value("Product 1"))
    }

    @Test
    fun `getById returns 404 when not found`() {
        `when`(productService.findById("999")).thenReturn(null)

        mockMvc.perform(get("/api/products/999"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `getByCategory returns products in category`() {
        val products = listOf(
            Product("1", "Product 1", "Category A", 100, 10, "Desc 1", listOf("tag1"), "url1")
        )
        `when`(productService.findByCategory("Category A")).thenReturn(products)

        mockMvc.perform(get("/api/products/category/Category A"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].category").value("Category A"))
    }
}
