package com.hcmute.myanime.service;

import com.hcmute.myanime.dto.CategoryDTO;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.model.CategoryEntity;
import com.hcmute.myanime.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> findAll() {
        return categoryRepository.findAll();
    }

    public boolean save(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity(
                categoryDTO.getName()
        );
        try {
            categoryRepository.save(categoryEntity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean deleteById(int categoryId) {
        try {
            categoryRepository.deleteById(categoryId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean updateById(int categoryId, CategoryDTO categoryDTO) {
        Optional<CategoryEntity> cateById = categoryRepository.findById(categoryId);
        if(!cateById.isPresent()) {
            return false;
        }
        CategoryEntity updateCategoryEntity = cateById.get();
        updateCategoryEntity.setName(categoryDTO.getName());
        try {
            categoryRepository.save(updateCategoryEntity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
