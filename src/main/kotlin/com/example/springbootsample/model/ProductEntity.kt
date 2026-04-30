package com.example.springbootsample.model

import jakarta.persistence.*
import tools.jackson.databind.ObjectMapper

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val price: Int = 0,
    val stock: Int = 0,
    val description: String = "",
    
    @Convert(converter = TagsConverter::class)
    val tags: List<String> = emptyList(),
    
    @Column(name = "image_url")
    val imageUrl: String = ""
) {
    fun toProduct(): Product {
        return Product(
            id = id,
            name = name,
            category = category,
            price = price,
            stock = stock,
            description = description,
            tags = tags,
            imageUrl = imageUrl
        )
    }
}

@Converter
class TagsConverter : AttributeConverter<List<String>, String> {
    private val mapper = ObjectMapper()

    override fun convertToDatabaseColumn(attribute: List<String>?): String {
        return mapper.writeValueAsString(attribute ?: emptyList<String>())
    }

    @Suppress("UNCHECKED_CAST")
    override fun convertToEntityAttribute(dbData: String?): List<String> {
        return if (dbData.isNullOrEmpty()) emptyList() else {
            mapper.readValue(dbData, List::class.java) as List<String>
        }
    }
}
