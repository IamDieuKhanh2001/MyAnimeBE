package com.hcmute.myanime.controller;

import com.hcmute.myanime.model.CategoryEntity;
import com.hcmute.myanime.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class testController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/test/procedure/categories")
    public ResponseEntity<?> cateFindAllTest()
    {
        List<CategoryEntity> allByStoredProcedures = categoryRepository.findAllByStoredProcedures();
        return ResponseEntity.ok(allByStoredProcedures);
    }
    @GetMapping("/test/procedure/categories/{id}")
    public ResponseEntity<?> cateFindAllTest(@PathVariable int id)
    {
        CategoryEntity byIdByStoredProcedures = categoryRepository.findByIdByStoredProcedures(id);
        System.out.println(byIdByStoredProcedures.getName());
        System.out.println(byIdByStoredProcedures.getMovieEntityCollection().size());
        return ResponseEntity.ok(byIdByStoredProcedures);
    }
}