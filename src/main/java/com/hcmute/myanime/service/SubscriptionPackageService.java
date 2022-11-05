package com.hcmute.myanime.service;

import com.hcmute.myanime.dto.SubcriptionPackageDTO;
import com.hcmute.myanime.mapper.SubscriptionPackageMapper;
import com.hcmute.myanime.model.SubscriptionPackageEntity;
import com.hcmute.myanime.repository.SubscriptionPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionPackageService {
    @Autowired
    private SubscriptionPackageRepository subcriptionPackageRepository;

    public List<SubcriptionPackageDTO> GetSubcriptionPackageActive()
    {
        List<SubscriptionPackageEntity> subscriptionPackageEntityList = subcriptionPackageRepository.findAllByEnableActive();
        List<SubcriptionPackageDTO> subcriptionPackageDTOList = new ArrayList<>();
        subscriptionPackageEntityList.forEach((subscriptionPackageEntity)->{
            subcriptionPackageDTOList.add(SubscriptionPackageMapper.toDTO(subscriptionPackageEntity));
        });
        return subcriptionPackageDTOList;
    }
}
