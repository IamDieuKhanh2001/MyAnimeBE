package com.hcmute.myanime.service;

import com.hcmute.myanime.dto.FavoritesDTO;
import com.hcmute.myanime.dto.MovieSeriesDTO;
import com.hcmute.myanime.mapper.FavoritesMapper;
import com.hcmute.myanime.mapper.MovieSeriesMapper;
import com.hcmute.myanime.model.FavoritesEntity;
import com.hcmute.myanime.model.MovieEntity;
import com.hcmute.myanime.model.MovieSeriesEntity;
import com.hcmute.myanime.model.UsersEntity;
import com.hcmute.myanime.repository.FavoritesRepository;
import com.hcmute.myanime.repository.MovieRepository;
import com.hcmute.myanime.repository.MovieSeriesRepository;
import com.hcmute.myanime.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class FavoritesService {
    @Autowired
    private FavoritesRepository favoritesRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private MovieSeriesRepository movieSeriesRepository;

    public List<FavoritesEntity> findAll()
    {
        return favoritesRepository.findAll();
    }

    public boolean save(FavoritesDTO favoritesDTO)
    {
        FavoritesEntity favoritesEntity = FavoritesMapper.toEntity(favoritesDTO);

        Optional<MovieSeriesEntity> movieSeriesRepositoryOptional = movieSeriesRepository.findById(favoritesDTO.getMovieSeriesId());
        Optional<UsersEntity> usersEntityOptional = usersRepository.findById(1);

        if(!movieSeriesRepositoryOptional.isPresent() || !usersEntityOptional.isPresent()) {
            return false;
        }

        MovieSeriesEntity movieSeriesEntity = movieSeriesRepositoryOptional.get();
        UsersEntity usersEntity = usersEntityOptional.get();

        favoritesEntity.setMovieSeries(movieSeriesEntity);
        favoritesEntity.setUser(usersEntity);

        try
        {
            favoritesRepository.save(favoritesEntity);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public boolean deleteById(int favoritesID) {
        try {
            favoritesRepository.deleteById(favoritesID);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
