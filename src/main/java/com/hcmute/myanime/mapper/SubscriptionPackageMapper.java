package com.hcmute.myanime.mapper;

import com.hcmute.myanime.dto.SubcriptionPackageDTO;
import com.hcmute.myanime.model.SubscriptionPackageEntity;

public class SubscriptionPackageMapper {
    public static SubcriptionPackageDTO toDTO (SubscriptionPackageEntity subscriptionPackageEntity)
    {
        SubcriptionPackageDTO subcriptionPackageDTO = new SubcriptionPackageDTO();
        subcriptionPackageDTO.setId(subscriptionPackageEntity.getId());
        subcriptionPackageDTO.setDay(subscriptionPackageEntity.getDay());
        subcriptionPackageDTO.setDescription(subscriptionPackageEntity.getDescription());
        subcriptionPackageDTO.setEnable(subscriptionPackageEntity.getEnable());
        subcriptionPackageDTO.setName(subscriptionPackageEntity.getName());
        subcriptionPackageDTO.setPrice(subscriptionPackageEntity.getPrice());
        return subcriptionPackageDTO;
    }
}
