package com.example.facebook_integration.controller;

import com.example.facebook_integration.model.CategoryOfDay;
import com.example.facebook_integration.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    @PostMapping("/posts")
    public ResponseEntity<CategoryOfDay> createPost(@RequestBody CategoryOfDay category) {
        // Implement your service call here
        return new ResponseEntity<>(categoryService.saveCategory(category), HttpStatus.CREATED);
    }

}
