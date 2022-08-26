package com.hcmute.myanime.controller;

import com.hcmute.myanime.dto.CategoryDTO;
import com.hcmute.myanime.dto.ResponseDTO;
import com.hcmute.myanime.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategory() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping("/category")
    public ResponseEntity<?> createNewCategory(@RequestBody CategoryDTO categoryDTO) {
        if(categoryService.save(categoryDTO)) {
            return ResponseEntity.ok(
                    new ResponseDTO(
                            HttpStatus.OK,
                            "Create new category success"
                    )
            );
        } else {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.NO_CONTENT, "create fail")
            );
        }
    }

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<?> updateCategoryById(@RequestBody CategoryDTO categoryDTO,
                                                @PathVariable int categoryId) {

        if(categoryService.updateById(categoryId, categoryDTO)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Update category success")
            );
        } else {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "Update fail")
            );
        }
    }


    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable int categoryId) {
        if(categoryService.deleteById(categoryId)) {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.OK, "Delete category success")
            );
        } else {
            return ResponseEntity.ok(
                    new ResponseDTO(HttpStatus.BAD_REQUEST, "Detele Fail")
            );
        }
    }
}
