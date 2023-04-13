package com.hcmute.myanime.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmute.myanime.common.GlobalVariable;
import com.hcmute.myanime.dto.MovieSeriesDTO;
import com.hcmute.myanime.exception.BadRequestException;
import com.hcmute.myanime.mapper.MovieSeriesMapper;
import com.hcmute.myanime.model.EpisodeEntity;
import com.hcmute.myanime.model.MovieEntity;
import com.hcmute.myanime.model.MovieSeriesEntity;
import com.hcmute.myanime.repository.MovieRepository;
import com.hcmute.myanime.repository.MovieSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieSeriesService {
    @Autowired
    private MovieSeriesRepository movieSeriesRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private CloudinaryService cloudinaryService;

    public List<MovieSeriesEntity> findAll(String page, String limit, String keywordSearch)
    {
        limit = (limit == null || limit.equals("")
                || !isNumber(limit) || Long.parseLong(limit) < 0) ? GlobalVariable.DEFAULT_LIMIT : limit;

        page = (!isValidPage(page)) ? GlobalVariable.DEFAULT_PAGE : page;
        Pageable pageable = PageRequest.of((Integer.parseInt(page) - 1), Integer.parseInt(limit));

        List<MovieSeriesEntity> movieSeriesEntityList = new ArrayList<>();

        if(keywordSearch != null) {
            //movieSeriesEntityList = movieSeriesRepository.findAll(Sort.by("createAt").descending());
            movieSeriesEntityList = movieSeriesRepository.findByNameContaining(keywordSearch, pageable);
        }else {
            movieSeriesEntityList = movieSeriesRepository.findAllByStoredProcedures(Integer.parseInt(page), Integer.parseInt(limit));
        }
        return movieSeriesEntityList;
    }

    public MovieSeriesEntity findById(int seriesId) {
        return movieSeriesRepository.findById(seriesId).get();
    }

    public List<MovieSeriesEntity> findAllMovieAndSeriesById(int seriesId) {
        MovieSeriesEntity movieSeriesEntity = movieSeriesRepository.findById(seriesId).get();
        List<MovieSeriesEntity> movieSeriesByMovieIdList = movieSeriesEntity.getMovieByMovieId().getMovieSeriesById().stream().toList();
        return movieSeriesByMovieIdList;
    }

    public MovieSeriesEntity save(MovieSeriesDTO movieSeriesDTO, MultipartFile sourceFile)
    {
        MovieSeriesEntity movieSeriesEntity = MovieSeriesMapper.toEntity(movieSeriesDTO);
        Optional<MovieEntity> movieEntityOptional = movieRepository.findById(movieSeriesDTO.getMovieId());
        if(!movieEntityOptional.isPresent()) {
            throw new BadRequestException("can not find movie entity");
        }
        MovieEntity movieEntity = movieEntityOptional.get();
        movieSeriesEntity.setMovieByMovieId(movieEntity);
        try
        {
            MovieSeriesEntity savedEntity = movieSeriesRepository.save(movieSeriesEntity);
            String urlSource = uploadSourceFileToCloudinary(sourceFile, savedEntity.getId());
            if(!urlSource.equals("-1")) {
                savedEntity.setImage(urlSource);
                savedEntity = movieSeriesRepository.save(savedEntity);
            }
            return savedEntity;
        }
        catch (Exception ex)
        {
            throw new BadRequestException("Can not add series");
        }
    }

    public String uploadSourceFileToCloudinary(MultipartFile sourceFile, int seriesId) throws IOException {
        String urlSource = cloudinaryService.uploadFile(
                sourceFile.getBytes(),
                String.valueOf(seriesId),
                "MyAnimeProject_TLCN" + "/" + "movie_series");
        return urlSource;
    }

    public MovieSeriesEntity updateById(int seriesID, MovieSeriesDTO movieSeriesDTO, MultipartFile sourceFile) {
        Optional<MovieSeriesEntity> movieSeriesEntity = movieSeriesRepository.findById(seriesID);
        if(!movieSeriesEntity.isPresent()) {
            throw new BadRequestException("Can not find series with this Id");
        }

        MovieSeriesEntity updateMovieSeriesEntity = movieSeriesEntity.get();
        updateMovieSeriesEntity.setDateAired(movieSeriesDTO.getDateAired());
        updateMovieSeriesEntity.setDescription(movieSeriesDTO.getDescription());
        updateMovieSeriesEntity.setName(movieSeriesDTO.getName());
        updateMovieSeriesEntity.setTotalEpisode(movieSeriesDTO.getTotalEpisode());
        try {
            MovieSeriesEntity savedEntity = movieSeriesRepository.save(updateMovieSeriesEntity);
            if(sourceFile.getSize() > 0) {
                String urlSource = uploadSourceFileToCloudinary(sourceFile, savedEntity.getId());
                if(!urlSource.equals("-1")) {
                    savedEntity.setImage(urlSource);
                    savedEntity = movieSeriesRepository.save(savedEntity);
                }
            }
            return savedEntity;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new BadRequestException("Can not update series");
        }
    }

    public boolean deleteById(int seriesID) {
        Optional<MovieSeriesEntity> movieSeriesEntityOptional = movieSeriesRepository.findById(seriesID);
        if(!movieSeriesEntityOptional.isPresent()) {
            return false;
        }
//        MovieSeriesEntity movieSeriesEntity = movieSeriesEntityOptional.get();
//        for (EpisodeEntity episodeEntity : movieSeriesEntity.getEpisodesById()) {
//            episodeEntity.setMovieSeriesBySeriesId(null);
//        }
        try {
            movieSeriesRepository.deleteById(seriesID);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


    public Long totalViewByMovieSeriesEntity(MovieSeriesEntity movieSeriesEntity) {
        Long totalView = Long.valueOf(0);
        try {
            for (EpisodeEntity episodeEntity : movieSeriesEntity.getEpisodesById()) {
                totalView += episodeEntity.getTotalView();
            }
        } catch (Exception ex) {}
        return totalView;
    }

    public Long countSeriesByKeyword(String keywordSearch) {
        Long totalSeries;
        if(keywordSearch != null) {
            totalSeries = movieSeriesRepository.countByNameContaining(keywordSearch);
        } else {
            totalSeries = movieSeriesRepository.count();
        }
        return totalSeries;
    }

    private Boolean isNumber(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Boolean isValidPage(String page) {
        return page != null && !page.equals("") && isNumber(page) && Long.parseLong(page) >= 0;
    }

    public List<MovieSeriesEntity> getByPageAndLimit(String page, String limit, String keywordSearch) {
        limit = (limit == null || limit.equals("")
                || !isNumber(limit) || Long.parseLong(limit) < 0) ? GlobalVariable.DEFAULT_LIMIT : limit;

        page = (!isValidPage(page)) ? GlobalVariable.DEFAULT_PAGE : page;
        Pageable pageable = PageRequest.of((Integer.parseInt(page) - 1), Integer.parseInt(limit));

        List<MovieSeriesEntity> movieSeriesEntityList = new ArrayList<>();
        if(keywordSearch != null) {
            movieSeriesEntityList = movieSeriesRepository.findByNameContaining(keywordSearch,pageable);
        }
        else{
            //Use spring data jpa
//            Sort sort = Sort.by("createAt").descending();
//            Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
//            movieSeriesEntityList = movieSeriesRepository.findAll(pageableWithSort).stream().toList();

            movieSeriesEntityList = movieSeriesRepository
                    .findAllByStoredProcedures(Integer.parseInt(page), Integer.parseInt(limit)); //Use stored procedures
        }
        return movieSeriesEntityList;
    }

    public List<MovieSeriesEntity> getRecentlyAddedShow(String page, String limit, int numberOfDayAfter) {
        limit = (limit == null || limit.equals("")
                || !isNumber(limit) || Long.parseLong(limit) < 0) ? GlobalVariable.DEFAULT_LIMIT : limit;

        page = (!isValidPage(page)) ? GlobalVariable.DEFAULT_PAGE : page;
        Pageable pageable = PageRequest.of((Integer.parseInt(page) - 1), Integer.parseInt(limit));

        Timestamp dayAfter = new Timestamp(System.currentTimeMillis());
        dayAfter.setDate(dayAfter.getDate() - numberOfDayAfter);

        List<MovieSeriesEntity> movieSeriesEntityList = movieSeriesRepository.findAllByCreateAtAfter(dayAfter, pageable);
        return movieSeriesEntityList;
    }
}
