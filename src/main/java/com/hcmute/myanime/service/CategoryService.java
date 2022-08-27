package com.hcmute.myanime.service;

import com.hcmute.myanime.dto.CategoryDTO;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.model.CategoryEntity;
import com.hcmute.myanime.model.MovieEntity;
import com.hcmute.myanime.repository.CategoryRepository;
import com.hcmute.myanime.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private MovieRepository movieRepository;

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

    public List<CategoryEntity> findCategoryByMovieId(int id) {
        Optional<MovieEntity> movieEntityOptional = movieRepository.findById(id);
        if(!movieEntityOptional.isPresent()) {
            throw new BadRequestException("movie id not found");
        }
        MovieEntity movieEntity = movieEntityOptional.get();
        List<CategoryEntity> categoryEntityList = movieEntity.getCategoryEntityCollection().stream().toList();

        return categoryEntityList;
    }

    public boolean saveCategoryMovie(int movieId, List<Integer> categoryId) {

        Collection<CategoryEntity> categoryEntityCollection = categoryRepository.findByIds(categoryId);
        Optional<MovieEntity> movieEntityOptional = movieRepository.findById(movieId);
        if(!movieEntityOptional.isPresent()) {
            throw new BadRequestException("movie id not found");
        }
        MovieEntity movieEntity = movieEntityOptional.get();
        movieEntity.setCategoryEntityCollection(categoryEntityCollection);
        try {
            movieRepository.save(movieEntity);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
