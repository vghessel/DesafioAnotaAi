package com.hessel.desafio_anota_ai.services;

import com.hessel.desafio_anota_ai.domain.category.Category;
import com.hessel.desafio_anota_ai.domain.category.CategoryDTO;
import com.hessel.desafio_anota_ai.domain.category.exceptions.CategoryNotFoundException;
import com.hessel.desafio_anota_ai.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category insert(CategoryDTO categoryData) {
        Category newCategory = new Category(categoryData);
        this.repository.save(newCategory);
        return newCategory;
    }

    public List<Category> getAll() {
        return this.repository.findAll();
    }

    public Optional<Category> getById(String id) {
        return this.repository.findById(id);
    }

    public Category update(String id, CategoryDTO categoryData) {
        Category category = this.repository.findById(id).orElseThrow(CategoryNotFoundException::new);

        if(!categoryData.title().isEmpty()) category.setTitle((categoryData.title()));
        if(!categoryData.description().isEmpty()) category.setDescription((categoryData.description()));

        this.repository.save(category);

        return category;
    }

    public void delete(String id) {
        Category category = this.repository.findById(id).orElseThrow(CategoryNotFoundException::new);
        this.repository.delete(category);
    }
}
