package com.hessel.desafio_anota_ai.services;

import com.hessel.desafio_anota_ai.domain.category.Category;
import com.hessel.desafio_anota_ai.domain.category.exceptions.CategoryNotFoundException;
import com.hessel.desafio_anota_ai.domain.product.exceptions.ProductNotFoundException;
import com.hessel.desafio_anota_ai.domain.product.Product;
import com.hessel.desafio_anota_ai.domain.product.ProductDTO;
import com.hessel.desafio_anota_ai.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private CategoryService categoryService;
    private ProductRepository repository;
    private AwsSnsService snsService;

    public ProductService(CategoryService categoryService, ProductRepository productRepository, AwsSnsService snsService) {
        this.categoryService = categoryService;
        this.repository = productRepository;
        this.snsService = snsService;
    }

    public Product insert(ProductDTO productData) {
        Category category = this.categoryService.getById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);
        Product newProduct = new Product(productData);
        newProduct.setCategory(category);
        this.repository.save(newProduct);
        this.snsService.publish(new MessageDTO(newProduct.getOwnerId()));
        return newProduct;
    }

    public List<Product> getAll() {
        return this.repository.findAll();
    }

    public Product update(String id, ProductDTO productData) {
        Product product = this.repository.findById(id).orElseThrow(ProductNotFoundException::new);
        if (productData.categoryId() != null) {
            this.categoryService.getById(productData.categoryId()).ifPresent(product::setCategory);
        }

        if(!productData.title().isEmpty()) product.setTitle((productData.title()));
        if(!productData.description().isEmpty()) product.setDescription((productData.description()));
        if(!(productData.price() == null)) product.setPrice((productData.price()));

        this.repository.save(product);

        this.snsService.publish(new MessageDTO(product.getOwnerId()));

        return product;
    }

    public void delete(String id) {
        Product product = this.repository.findById(id).orElseThrow(ProductNotFoundException::new);
        this.repository.delete(product);
    }
}
