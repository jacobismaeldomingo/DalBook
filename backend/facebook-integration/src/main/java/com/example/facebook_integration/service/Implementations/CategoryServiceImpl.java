package com.example.facebook_integration.service.Implementations;

import com.example.facebook_integration.model.CategoryOfDay;
import com.example.facebook_integration.repository.CategoryOfDayRepository;
import com.example.facebook_integration.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryOfDayRepository categoryRepository;

    public CategoryOfDay saveCategory(CategoryOfDay category) {
        return categoryRepository.save(category);
    }

}
